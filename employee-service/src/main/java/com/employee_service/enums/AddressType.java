package com.employee_service.enums;

import lombok.Getter;

@Getter
public enum AddressType {
    PERMANENT("Permanent"),
    TEMPORARY("Temporary");

    private final String addressType;

    AddressType(String addressType) {
        this.addressType = addressType;
    }
}
