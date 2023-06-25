package com.jwt.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication //(exclude={DataSourceAutoConfiguration.class})
public class JwtExample3Application {

	public static void main(String[] args) {
		SpringApplication.run(JwtExample3Application.class, args);
	}

}
