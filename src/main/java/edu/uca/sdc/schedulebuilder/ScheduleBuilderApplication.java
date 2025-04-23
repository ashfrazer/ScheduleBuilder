package edu.uca.sdc.schedulebuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import edu.uca.sdc.schedulebuilder.config.WebConfig;

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
