package com.project.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class PaymentApiApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		String dbUrl = dotenv.get("DB_URL");
		String dbUser = dotenv.get("DB_USER");
		String dbPassword = dotenv.get("DB_PASSWORD");

		System.setProperty("spring.datasource.url", dbUrl);
		System.setProperty("spring.datasource.username", dbUser);
		System.setProperty("spring.datasource.password", dbPassword);

		SpringApplication.run(PaymentApiApplication.class, args);

	}

}
