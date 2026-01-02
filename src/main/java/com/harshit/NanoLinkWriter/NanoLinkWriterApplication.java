package com.harshit.NanoLinkWriter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class NanoLinkWriterApplication {

	public static void main(String[] args) {
		SpringApplication.run(NanoLinkWriterApplication.class, args);
	}

}
