package com.insa.mapper;

import com.insa.dto.request.TitleNameRequest;
import com.insa.dto.response.TitleNameResponse;
import com.insa.entity.TitleName;
import org.springframework.stereotype.Component;

@Component
public class TitleNameMapper {

    public TitleName mapToEntity(TitleNameRequest request) {

        TitleName titleName = new TitleName();
        titleName.setTitleName(request.getTitleName());
        titleName.setDescription(request.getDescription());

        return titleName;
    }

    public TitleNameResponse mapToDto(TitleName titleName) {

        TitleNameResponse response = new TitleNameResponse();
        response.setId(titleName.getId());
        response.setTitleName(titleName.getTitleName());
        response.setDescription(titleName.getDescription());
        response.setTenantId(titleName.getTenantId());
        response.setCreatedAt(titleName.getCreatedAt());
        response.setUpdatedAt(titleName.getUpdatedAt());
        response.setCreatedBy(titleName.getCreatedBy());
        response.setUpdatedBy(titleName.getUpdatedBy());

        return response;
    }

    public TitleName updateEntity(TitleName titleName,
                                  TitleNameRequest request) {

        if (request.getTitleName() != null) {
            titleName.setTitleName(request.getTitleName());
        }

        if (request.getDescription() != null) {
            titleName.setDescription(request.getDescription());
        }

        return titleName;
    }
}
