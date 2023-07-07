package com.example.software;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class SoftwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftwareApplication.class, args);
	}

	@Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info()
        .title("Server Applications")
        .version("1.0.0")
        .description("Api Servers with Applications")
        .termsOfService("http://swagger.io/terms/")
        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
