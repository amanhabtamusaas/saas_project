package com.insa.hr_planning_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing
public class HrPlanningServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrPlanningServiceApplication .class, args);
	}

}
