package com.insa.leave_service.service;

import com.insa.leave_service.dto.EmployeeDto;
import com.insa.leave_service.dto.ExperienceDto;
import com.insa.leave_service.dto.TenantDto;
import com.insa.leave_service.dto.response.LeaveBalanceResponse;
import com.insa.leave_service.entity.*;
import com.insa.leave_service.enums.Day;
import com.insa.leave_service.exception.ResourceExistsException;
import com.insa.leave_service.mapper.LeaveBalanceMapper;
import com.insa.leave_service.repository.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class LeaveBalanceService {

    private static final Logger logger = LoggerFactory.getLogger(LeaveBalanceService.class);

    private final LeaveBalanceRepository leaveBalanceRepository;
    private final LeaveSettingRepository leaveSettingRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final TenantServiceClient tenantServiceClient;
    private final EmployeeServiceClient employeeServiceClient;
    private final HolidayManagementRepository holidayManagementRepository;
    private final BudgetYearRepository budgetYearRepository;
    private final LeaveBalanceHistoryRepository leaveBalanceHistoryRepository;
    private final LeaveBalanceMapper leaveBalanceMapper;

    @Transactional
    public LeaveBalanceResponse calculateLeaveBalance(Long tenantId, Long employeeId) {
        try {
            // Fetch tenant details
            TenantDto tenantDto = tenantServiceClient.getTenantById(tenantId);
            if (tenantDto == null) {
                throw new RuntimeException("Tenant not found for ID: " + tenantId);
            }

            // Fetch employee details
            EmployeeDto employee = employeeServiceClient.getEmployeeById(tenantId, employeeId);
            if (employee == null) {
                throw new RuntimeException("Employee not found for ID: " + employeeId);
            }

            // Fetch experience details
            List<ExperienceDto> experienceList = employeeServiceClient.getExperienceByEmployeeId(tenantId, employeeId);
            if (experienceList.isEmpty()) {
                throw new RuntimeException("Employee has no experience records.");
            }

            // Assuming you want to calculate based on the first experience in the list
            ExperienceDto firstExperience = experienceList.get(0);
            LocalDate startDate = firstExperience.getStartDate();

            // Validate startDate
            if (startDate == null) {
                throw new RuntimeException("Employee start date is not available.");
            }

            // Calculate experience period
            LocalDate currentDate = LocalDate.now();
            Period experiencePeriod = Period.between(startDate, currentDate);

            // Check if experience is less than 1 year
            if (experiencePeriod.getYears() < 1) {
                throw new IllegalArgumentException("Experience period must be at least 1 year.");
            }

            // Fetch leave setting and current budget year
            LeaveSetting leaveSetting = leaveSettingRepository.findByTenantId(tenantId);
            BudgetYear currentBudgetYear = budgetYearRepository.findCurrentBudgetYear();

            // Fetch previous leave balance from history
            LeaveBalance previousLeaveBalance = leaveBalanceRepository.findByEmployeeIdAndTenantId(employeeId, tenantId)
                    .orElse(new LeaveBalance(employeeId, 0, 0, tenantId)); // Default to 0 if not found

            // Calculate total leave days
            int baseLeaveDays = 20;
            int totalAvailableLeaveDays = baseLeaveDays + experiencePeriod.getYears() + previousLeaveBalance.getRemainingLeaveDays();

            // Ensure total available leave days do not exceed maximum allowed days
            int maximumAllowedDays = leaveSetting.getMaximumDays();
            totalAvailableLeaveDays = Math.min(totalAvailableLeaveDays, maximumAllowedDays);

            // Fetch holidays
            Set<LocalDate> holidays = new HashSet<>(holidayManagementRepository.findAllHolidays());

            // Calculate used leave days considering the type of day
            List<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployeeId(employeeId);
            double usedLeaveDays = calculateUsedLeaveDays(leaveRequests, holidays);

            // Calculate current leave balance
            int currentRemainingLeaveDays = totalAvailableLeaveDays - (int) usedLeaveDays;

            // Save current leave balance to database
            LeaveBalance currentLeaveBalance = leaveBalanceRepository.findByEmployeeIdAndTenantId(employeeId, tenantId)
                    .orElse(new LeaveBalance(employeeId, 0, 0, tenantId)); // Create new if not found
            currentLeaveBalance.setTotalLeaveDays(totalAvailableLeaveDays);
            currentLeaveBalance.setRemainingLeaveDays(currentRemainingLeaveDays);

            // Ensure to set BudgetYear if available
            if (currentBudgetYear != null) {
                currentLeaveBalance.setBudgetYear(currentBudgetYear);
            }

            leaveBalanceRepository.save(currentLeaveBalance);

            // Save current leave balance to history
            saveCurrentLeaveBalanceToHistory(employeeId, totalAvailableLeaveDays, currentRemainingLeaveDays, currentBudgetYear.getId(), tenantId);

            // Map entity to response DTO using LeaveBalanceMapper
            LeaveBalanceResponse response = leaveBalanceMapper.mapToDto(currentLeaveBalance);

            // Build and return the response
            return response;
        } catch (Exception e) {
            logger.error("Error calculating leave balance for employee ID: {} in tenant ID: {}", employeeId, tenantId, e);
            throw e;
        }
    }

    @Transactional
    public void saveCurrentLeaveBalanceToHistory(Long employeeId, int totalLeaveDays, int remainingLeaveDays, Long budgetYearId, Long tenantId) {
        try {
            // Create a new instance of LeaveBalanceHistory
            LeaveBalanceHistory leaveBalanceHistory = new LeaveBalanceHistory();
            leaveBalanceHistory.setEmployeeId(employeeId);
            leaveBalanceHistory.setTotalLeaveDays(totalLeaveDays);
            leaveBalanceHistory.setRemainingLeaveDays(remainingLeaveDays);
            leaveBalanceHistory.setBudgetYear(budgetYearId); // Set budgetYearId
            leaveBalanceHistory.setTenantId(tenantId);
            leaveBalanceHistory.setCalculationDate(LocalDate.now());

            // Save the leave balance history record
            leaveBalanceHistoryRepository.save(leaveBalanceHistory);
            logger.info("Successfully saved leave balance history for employee ID: {} in tenant ID: {}", employeeId, tenantId);
        } catch (Exception e) {
            // Log the error with appropriate details
            logger.error("Error saving current leave balance to history for employee ID: {} in tenant ID: {}", employeeId, tenantId, e);
            throw new RuntimeException("Failed to save leave balance history", e); // Rethrow as a runtime exception
        }
    }

    @Transactional(readOnly = true)
    public List<LeaveBalanceHistory> getLeaveBalanceHistory(Long employeeId, Long tenantId) {
        return leaveBalanceHistoryRepository.findByEmployeeIdAndTenantIdOrderByCalculationDateDesc(employeeId, tenantId);
    }

    private double calculateUsedLeaveDays(List<LeaveRequest> leaveRequests, Set<LocalDate> holidays) {
        // Calculate total used leave days excluding weekends and holidays
        return leaveRequests.stream()
                .mapToDouble(request -> countWorkingDaysBetween(request.getLeaveStart(), request.getReturnDate(), holidays) * getLeaveDayMultiplier(request.getDay()))
                .sum();
    }

    private int countWorkingDaysBetween(LocalDate startDate, LocalDate endDate, Set<LocalDate> holidays) {
        int workingDays = 0;
        LocalDate date = startDate;

        while (!date.isAfter(endDate)) {
            if (!(date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                    holidays.contains(date))) {
                workingDays++;
            }
            date = date.plusDays(1);
        }

        return workingDays;
    }

    private double getLeaveDayMultiplier(Day day) {
        return switch (day) {
            case HalfDay -> 0.5; // Half day counts as 0.5 day
            case FullDay -> 1; // Full day counts as 1 day
            case On_Off_Day -> 0; // On/Off day counts as 0 days (excluded)
            default -> 0; // Default to 0 if unspecified
        };
    }

}
