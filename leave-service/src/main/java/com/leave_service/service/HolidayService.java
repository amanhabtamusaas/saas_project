package com.leave_service.service;

import com.leave_service.client.OrganizationServiceClient;
import com.leave_service.dto.TenantDto;
import com.leave_service.dto.request.HolidayRequest;
import com.leave_service.dto.response.HolidayResponse;
import com.leave_service.model.Holiday;
import com.leave_service.exception.ResourceExistsException;
import com.leave_service.exception.ResourceNotFoundException;
import com.leave_service.mapper.HolidayMapper;
import com.leave_service.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HolidayService {

    private final HolidayRepository holidayRepository;
    private final HolidayMapper holidayMapper;
    private final OrganizationServiceClient organizationServiceClient;

    public HolidayResponse createHoliday(UUID tenantId, HolidayRequest holidayRequest) {
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        Holiday holiday = holidayMapper.mapToEntity(holidayRequest);
        holiday.setTenantId(tenant.getId());

        if (holidayRepository.existsByHolidayNameAndTenantId(
                holidayRequest.getHolidayName(), tenant.getId())) {
            throw new ResourceExistsException("Holiday with Name " +
                    holidayRequest.getHolidayName() + " already exists");
        }

        Holiday savedHoliday = holidayRepository.save(holiday);
        return holidayMapper.mapToDto(savedHoliday);
    }

    public List<HolidayResponse> getAllHolidays(UUID tenantId) {
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        List<Holiday> holidays = holidayRepository.findAll();
        return holidays.stream()
                .map(holidayMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public HolidayResponse getHolidayById(UUID tenantId, UUID id) {
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Holiday not found with id " + id));
        return holidayMapper.mapToDto(holiday);
    }

    public HolidayResponse updateHoliday(UUID tenantId, UUID id, HolidayRequest holidayRequest) {
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Holiday not found with id " + id));
        holidayMapper.updateHoliday(holiday, holidayRequest);
        Holiday updatedHoliday = holidayRepository.save(holiday);
        return holidayMapper.mapToDto(updatedHoliday);
    }

    public void deleteHoliday(UUID tenantId, UUID id) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Holiday not found with id " + id));
        holidayRepository.delete(holiday);
    }
}