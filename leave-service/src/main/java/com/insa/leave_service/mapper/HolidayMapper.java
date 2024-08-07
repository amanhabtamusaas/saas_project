package com.insa.leave_service.mapper;

import com.insa.leave_service.dto.request.HolidayRequest;
import com.insa.leave_service.dto.response.HolidayResponse;
import com.insa.leave_service.entity.Holiday;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HolidayMapper {



    /**
     * Maps a HolidayRequest to a Holiday entity.
     *
     * @param request the HolidayRequest to map
     * @return the mapped Holiday entity
     */
    public Holiday mapToEntity(HolidayRequest request) {
        Holiday holiday = new Holiday();
        holiday.setHolidayName(request.getHolidayName());
//        holiday.setDate(request.getDate());
//        holiday.setTenantId(request.getTenantId());
//        if (request.getHolidayManagementId() != null) {
//            holiday.setHolidayManagement(holidayManagementRepository.findById(request.getHolidayManagementId())
//                    .orElse(null)); // Handle this appropriately in real use cases
//        }
        return holiday;
    }

    /**
     * Maps a Holiday entity to a HolidayResponse.
     *
     * @param entity the Holiday entity to map
     * @return the mapped HolidayResponse
     */
    public HolidayResponse mapToDto(Holiday entity) {
        HolidayResponse response = new HolidayResponse();
        response.setId(entity.getId());
        response.setHolidayName(entity.getHolidayName());
//        response.setDate(entity.getDate());
        response.setTenantId(entity.getTenantId());
//        if (entity.getHolidayManagement() != null) {
//            response.setHolidayManagementId(entity.getHolidayManagement().getId());
//        }
        return response;
    }

    /**
     * Updates an existing Holiday entity with data from a HolidayRequest.
     *
     * @param holiday the existing Holiday entity to update
     * @param request the HolidayRequest containing updated data
     */
    public void updateHoliday(Holiday holiday, HolidayRequest request) {

        if (request.getHolidayName () != null)
            holiday.setHolidayName(request.getHolidayName ());
//        if(request.getDate()!=null)
//            holiday.setDate(request.getDate());
    }
}

