package edu.uca.sdc.schedulebuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ScheduleBuilderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduleBuilderApplication.class, args);
	}

	@Bean
	public WebConfig webConfig() {
		return new WebConfig();
	}
}
