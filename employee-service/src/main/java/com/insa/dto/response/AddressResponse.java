package com.insa.dto.response;

import com.insa.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse  extends BaseResponse{
    private String addressType;
    private Long locationId;
    private String houseNumber;
    private String homeTelephone;
    private String officeTelephone;
    private String mobileNumber;
    private String email;
    private String poBox;
    private Long employeeId;
}
