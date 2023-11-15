package com.nassau.checkinservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "checkinAPI",version = "1.0.0", description = "project of Spring Boot for app Back and"),
		servers = {
				@Server(url = "http://127.0.0.1:5000/checkin-service"),
				@Server(url = "https://checkin-service.onrender.com/checkin-service")
		})
public class CheckinServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(CheckinServiceApplication.class);

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication( CheckinServiceApplication.class );
		SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
		Environment env = app.run(args).getEnvironment();
		log.info("Access URLs:\n----------------------------------------------------------\n\t"
						+ "Local: \t\thttp://127.0.0.1:{}\n\t"
						+ "External: \thttp://{}:{}\n----------------------------------------------------------",
				env.getProperty("server.port"),
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"));
	}

}
