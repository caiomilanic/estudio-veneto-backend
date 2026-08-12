package com.estudioveneto.landing_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LandingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LandingApiApplication.class, args);
	}

}
