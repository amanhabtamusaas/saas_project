package com.insa.leave_service.mapper;
import com.insa.leave_service.dto.request.HolidayManagementRequest;
import com.insa.leave_service.dto.response.HolidayManagementResponse;
import com.insa.leave_service.entity.BudgetYear;
import com.insa.leave_service.entity.Holiday;
import com.insa.leave_service.entity.HolidayManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HolidayManagementMapper {

    /**
     * Maps a HolidayManagementRequest to a HolidayManagement entity.
     *
     * @param request the HolidayManagementRequest to map
     * @return the mapped HolidayManagement entity
     */
    public HolidayManagement mapToEntity(HolidayManagementRequest request) {
        HolidayManagement holidayManagement = new HolidayManagement();
        // Map request fields to entity fields
        BudgetYear budgetYear = new BudgetYear();
        budgetYear.setId(request.getBudgetYearId());// Use setId() instead of getId()
        holidayManagement.setBudgetYear(budgetYear);
        Holiday holiday = new Holiday();
        holiday.setId(request.getHolidayId());
        holidayManagement.setHoliday(holiday);
        //holidayManagement.setHolidayName(request.getHolidayName());
        holidayManagement.setDate(request.getDate());
//        holidayManagement.setTenantId(request.getTenantId()); // Set tenantId if needed
        return holidayManagement;
    }


    /**
     * Maps a HolidayManagement entity to a HolidayManagementResponse.
     *
     * @param entity the HolidayManagement entity to map
     * @return the mapped HolidayManagementResponse
     */
    public HolidayManagementResponse mapToDto(HolidayManagement entity) {
        HolidayManagementResponse response = new HolidayManagementResponse();
        // Map entity fields to response fields
        response.setId(entity.getId());

        if (entity.getBudgetYear() != null) {
            response.setBudgetYearId(entity.getBudgetYear().getId());
        }
        if (entity.getHoliday() != null) {
            response.setHolidayId(entity.getHoliday().getId());
        }

        if (entity.getDate() != null) {
            response.setDate(entity.getDate());
        }
        response.setTenantId(entity.getTenantId());
        return response;
    }

    /**
     * Updates an existing HolidayManagement entity with data from a HolidayManagementRequest.
     *
     * @param holidayManagement the existing HolidayManagement entity to update
     * @param request           the HolidayManagementRequest containing updated data
     */
    public void updateHolidayManagement(HolidayManagement holidayManagement, HolidayManagementRequest request) {
        // Update entity fields with data from request

        if (request.getBudgetYearId() != null) {
            BudgetYear departmentType = new BudgetYear();
            departmentType.setId(request.getBudgetYearId());
            holidayManagement.setBudgetYear(departmentType);
        }
        if (request.getHolidayId() != null) {
            Holiday departmentType = new Holiday();
            departmentType.setId(request.getHolidayId());
            holidayManagement.setHoliday(departmentType);
        }
        if (request.getDate() !=null){
        holidayManagement.setDate(request.getDate());
    }}
}

