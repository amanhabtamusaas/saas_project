package com.insa.leave_service.service;

import com.insa.leave_service.dto.TenantDto;
import com.insa.leave_service.dto.request.HolidayRequest;
import com.insa.leave_service.dto.response.HolidayResponse;
import com.insa.leave_service.entity.Holiday;
import com.insa.leave_service.exception.ResourceExistsException;
import com.insa.leave_service.exception.ResourceNotFoundException;
import com.insa.leave_service.mapper.HolidayMapper;
import com.insa.leave_service.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HolidayService {

    private final HolidayRepository holidayRepository;
    private final HolidayMapper holidayMapper;
    private final TenantServiceClient tenantServiceClient;

    public HolidayResponse createHoliday(Long tenantId, HolidayRequest holidayRequest) {
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Holiday holiday = holidayMapper.mapToEntity(holidayRequest);
        if (tenantId == null ) {
            throw new IllegalArgumentException("TenantId  must not be null");
        }
        holiday.setTenantId(tenant.getId());

        if (holidayRepository.existsByHolidayNameAndTenantId(holidayRequest.getHolidayName(),tenant.getId())) {
            throw new ResourceExistsException("Holiday with Name " + holidayRequest.getHolidayName() + " already exists");
        }

        Holiday savedHoliday = holidayRepository.save(holiday);
        return holidayMapper.mapToDto(savedHoliday);
    }

    public List<HolidayResponse> getAllHolidays(Long tenantId) {
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        List<Holiday> holidays = holidayRepository.findAll();
        return holidays.stream()
                .map(holidayMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public HolidayResponse getHolidayById(Long tenantId, Long id) {
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Holiday not found with id " + id));
        return holidayMapper.mapToDto(holiday);
    }

    public HolidayResponse updateHoliday(Long tenantId, Long id, HolidayRequest holidayRequest) {
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Holiday not found with id " + id));
        holidayMapper.updateHoliday(holiday, holidayRequest);
        Holiday updatedHoliday = holidayRepository.save(holiday);
        return holidayMapper.mapToDto(updatedHoliday);
    }

    public void deleteHoliday(Long tenantId, Long id) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Holiday not found with id " + id));
        holidayRepository.delete(holiday);
    }
}
