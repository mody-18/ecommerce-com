package com.modys.BuildingAWebApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//Disabling security
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class} )
public class BuildingAWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuildingAWebAppApplication.class, args);
	}

}
