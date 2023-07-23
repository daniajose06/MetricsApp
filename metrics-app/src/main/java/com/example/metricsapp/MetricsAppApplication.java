/**
 * @author dania.jose
 *
 */
package com.example.metricsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
 * This is the main class that starts up the SpringApplicationContext
 */


@SpringBootApplication
@ComponentScan(basePackages = "com.example.metricsapp")
@EntityScan(basePackages = "com.example.metricsapp.model")
@EnableJpaRepositories(basePackages = "com.*")
public class MetricsAppApplication{

	//Method that starts up the SpringApplicationContext
	public static void main(String[] args) {
		
		SpringApplication.run(MetricsAppApplication.class, args);

	}
}
