package com.insa.dto.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    @NotBlank(message = "Block number cannot be blank")
    private String blockNo;

    @NotBlank(message = "Floor cannot be blank")
    private String floor;

    @NotBlank(message = "Office number cannot be blank")
    private String officeNumber;

    @NotBlank(message = "Office telephone cannot be blank")
    private String officeTelephone;

    @NotBlank(message = "Mobile number cannot be blank")
    private String mobileNumber;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    private String website;

    private String poBox;

    private Long locationId;
    private Long departmentId;
}
