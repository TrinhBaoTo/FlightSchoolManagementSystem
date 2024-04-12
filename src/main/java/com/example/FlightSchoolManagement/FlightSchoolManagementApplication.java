package com.example.FlightSchoolManagement;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.example.FlightSchoolManagement.entity"})
public class FlightSchoolManagementApplication {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(FlightSchoolManagementApplication.class, args);
	}
}
