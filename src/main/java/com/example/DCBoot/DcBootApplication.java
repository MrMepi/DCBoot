package com.example.DCBoot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.*;

@SpringBootApplication
public class DcBootApplication {

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(DcBootApplication.class, args);
	}

	@Bean
	@ConfigurationProperties(value = "discoed-api")
	public DiscordApi discordApi(){
		String token = env.getProperty("TOKEN");
		DiscordApi api = new DiscordApiBuilder().setToken(token)
				.setAllNonPrivilegedIntents()
				.login()
				.join();
		api.addListener(new MyListener());
		return api;
	}

	public String[] getText(String message){
		return message.split(" ");
	};

}

