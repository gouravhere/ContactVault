package com.contractvault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//main controller new -- test
@SpringBootApplication
@ComponentScan(basePackages = {"com.contractvault",
	    "com.contractvault.services",
	    "com.contractvault.services.impl",
	    "com.contractvault.repositories",
	    "com.contractvault.controllers",
	    "com.contractvault.entities"
	})
	@EntityScan(basePackages = "com.contractvault.entities")
	@EnableJpaRepositories(basePackages = "com.contractvault.repositories")
public class ContractVaultApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContractVaultApplication.class, args);
	}

}
