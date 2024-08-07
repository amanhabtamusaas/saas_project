package com.insa.mapper;

import com.insa.dto.requestDto.AddressRequest;
import com.insa.dto.responseDto.AddressResponse;
import com.insa.entity.Address;
import com.insa.entity.Department;
import com.insa.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {



    public Address mapToEntity(AddressRequest addressRequest) {
        Address address = new Address ();
       // address.setLocation (addressRequest.getLocation ());
        address.setBlockNo (addressRequest.getBlockNo ());
        address.setFloor (addressRequest.getFloor ());
        address.setOfficeNumber (addressRequest.getOfficeNumber ());
        address.setOfficeTelephone (addressRequest.getOfficeTelephone ());
        address.setMobileNumber (addressRequest.getMobileNumber ());
        address.setEmail (addressRequest.getEmail ());
        address.setWebsite (addressRequest.getWebsite ());
        address.setPoBox (addressRequest.getPoBox ());
        Location location = new Location();
        location.setId(addressRequest.getLocationId());
        address.setLocation(location);
        Department department = new Department();
        department.setId(addressRequest.getDepartmentId());
        address.setDepartment(department);

        return address;
    }

    public AddressResponse mapToDto(Address address) {
        AddressResponse response = new AddressResponse ();
        response.setId (address.getId ());

        response.setBlockNo (address.getBlockNo ());
        response.setFloor (address.getFloor ());
        response.setOfficeNumber (address.getOfficeNumber ());
        response.setOfficeTelephone (address.getOfficeTelephone ());
        response.setMobileNumber (address.getMobileNumber ());
        response.setEmail (address.getEmail ());
        response.setWebsite (address.getWebsite ());
        response.setPoBox (address.getPoBox ());
        response.setCreatedAt (address.getCreatedAt ());
        response.setUpdatedAt (address.getUpdatedAt ());
        response.setCreatedBy (address.getCreatedBy ());
        response.setUpdatedBy (address.getUpdatedBy ());
        response.setLocationId(address.getLocation().getId());
        response.setTenantId (address.getTenant ().getId ());
        response.setDepartmentId(address.getDepartment().getId());

        return response;
    }

    public Address updateAddress(Address address, AddressRequest addressRequest) {

        if (addressRequest.getBlockNo () != null)
            address.setBlockNo (addressRequest.getBlockNo ());
        if (addressRequest.getFloor () != null)
            address.setFloor (addressRequest.getFloor ());
        if (addressRequest.getOfficeNumber () != null)
            address.setOfficeNumber (addressRequest.getOfficeNumber ());
        if (addressRequest.getOfficeTelephone () != null)
            address.setOfficeTelephone (addressRequest.getOfficeTelephone ());
        if (addressRequest.getMobileNumber () != null)
            address.setMobileNumber (addressRequest.getMobileNumber ());
        if (addressRequest.getEmail () != null)
            address.setEmail (addressRequest.getEmail ());
        if (addressRequest.getWebsite () != null)
            address.setWebsite (addressRequest.getWebsite ());
        if (addressRequest.getPoBox () != null)
            address.setPoBox (addressRequest.getPoBox ());

        return address;
    }
}
