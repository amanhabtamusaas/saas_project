package com.organization_service.config;

import com.organization_service.dto.requestDto.AddressRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Example of defining a bean in a configuration class
@Configuration
public class AppConfig {

    @Bean
    public AddressRequest addressRequest() {
        return new AddressRequest();
    }
}
