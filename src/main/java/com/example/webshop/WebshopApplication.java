package com.example.webshop;

import com.example.webshop.tasks.Tasks;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebshopApplication implements CommandLineRunner {



	public static void main(String[] args)  {
		SpringApplication.run(WebshopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Tasks.writeTasksToCsvFiles();
	}
}
