package com.insa.dto.request;

import com.insa.enums.AddressType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {

    @NotNull(message = "Address type cannot be null")
    private String addressType;

    @NotBlank(message = "Location id cannot be blank")
    private Long locationId;

    private String houseNumber;
    private String homeTelephone;
    private String officeTelephone;

    @Pattern(regexp = "\\d{10}", message = "Invalid mobile number")
    private String mobileNumber;

    @Email(message = "Invalid email address")
    private String email;

    private String poBox;
}
