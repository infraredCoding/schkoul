package com.imraninfrared.schkoul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SchkoulApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchkoulApplication.class, args);
	}

}
