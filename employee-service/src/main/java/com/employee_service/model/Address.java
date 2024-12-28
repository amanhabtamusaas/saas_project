package com.employee_service.model;

import com.employee_service.enums.AddressType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address extends Base {

    @NotNull(message = "Address type cannot be null")
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @NotNull(message = "Location ID cannot be null")
    private UUID locationId;

    private String houseNumber;
    private String homeTelephone;
    private String officeTelephone;
    private String mobileNumber;

    @Email(message = "Invalid email address")
    private String email;

    private String poBox;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false, updatable = false)
    private Employee employee;
}
