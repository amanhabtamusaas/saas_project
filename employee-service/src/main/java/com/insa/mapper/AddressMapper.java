package com.insa.mapper;

import com.insa.dto.request.AddressRequest;
import com.insa.dto.response.AddressResponse;
import com.insa.entity.Address;
import com.insa.enums.AddressType;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address mapToEntity(AddressRequest request) {
        Address address = new Address ();
        address.setAddressType (AddressType.valueOf(request.getAddressType ().toUpperCase()));
        address.setHouseNumber (request.getHouseNumber ());
        address.setHomeTelephone (request.getHomeTelephone ());
        address.setOfficeTelephone (request.getOfficeTelephone ());
        address.setMobileNumber (request.getMobileNumber ());
        address.setEmail (request.getEmail ());
        address.setPoBox (request.getPoBox ());

        return address;
    }

    public AddressResponse mapToDto(Address address) {
        AddressResponse response = new AddressResponse ();
        response.setId (address.getId ());
        response.setTenantId (address.getTenantId ());
        response.setAddressType (address.getAddressType ().getAddressType());
        response.setLocationId (address.getLocationId ());
        response.setHouseNumber (address.getHouseNumber ());
        response.setHomeTelephone (address.getHomeTelephone ());
        response.setOfficeTelephone (address.getOfficeTelephone ());
        response.setMobileNumber (address.getMobileNumber ());
        response.setEmail (address.getEmail ());
        response.setPoBox (address.getPoBox ());
        response.setCreatedAt (address.getCreatedAt ());
        response.setUpdatedAt (address.getUpdatedAt ());
        response.setCreatedBy (address.getCreatedBy ());
        response.setUpdatedBy (address.getUpdatedBy ());
        response.setEmployeeId (address.getEmployee ().getId ());

        return response;
    }

    public Address updateAddress(Address address, AddressRequest request) {
        if (request.getAddressType () != null)
            address.setAddressType (AddressType.valueOf(request.getAddressType ().toUpperCase()));
        if (request.getHouseNumber () != null)
            address.setHouseNumber (request.getHouseNumber ());
        if (request.getHomeTelephone () != null)
            address.setHomeTelephone (request.getHomeTelephone ());
        if (request.getOfficeTelephone () != null)
            address.setOfficeTelephone (request.getOfficeTelephone ());
        if(request.getMobileNumber () != null)
            address.setMobileNumber (request.getMobileNumber ());
        if (request.getEmail () != null)
            address.setEmail (request.getEmail ());
        if (request.getPoBox () != null)
            address.setPoBox (request.getPoBox ());

        return address;
    }
}
