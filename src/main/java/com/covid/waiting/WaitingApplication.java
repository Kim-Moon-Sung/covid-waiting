package com.covid.waiting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class WaitingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaitingApplication.class, args);
	}

}
