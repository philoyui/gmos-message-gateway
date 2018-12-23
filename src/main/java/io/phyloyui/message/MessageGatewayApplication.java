package io.phyloyui.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MessageGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageGatewayApplication.class, args);
	}

}
