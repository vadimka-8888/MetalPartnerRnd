package ru.metal_partner_rnd.ecommerce_core;

import java.io.InputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class EcommerceCoreApplication {

	private static final Logger logger = LogManager.getLogger(EcommerceCoreApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(EcommerceCoreApplication.class);
		logger.info("Applicatoin starts...");
		application.run(args);
	}
}