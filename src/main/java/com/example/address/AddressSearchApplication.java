package com.example.address;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AddressSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressSearchApplication.class, args);
	}

}
