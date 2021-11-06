package com.example.serverManager;

import com.example.serverManager.enumeration.Status;
import com.example.serverManager.model.Server;
import com.example.serverManager.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerManagerApplication.class, args);
	}

	@Bean
	CommandLineRunner run (ServerRepository serverRepository) {
		return args -> {
			serverRepository.save(new Server(null,"192.168.1.2",
					"windows","14GB","personal","https://localhost/8080/server/image/server1.png", Status.SERVER_UP));
			serverRepository.save(new Server(null,"192.168.1.4",
					"linux","14GB","personal","https://localhost/8080/server/image/server1.png", Status.SERVER_UP));
			serverRepository.save(new Server(null,"192.168.1.5",
					"kali","14GB","personal","https://localhost/8080/server/image/server1.png", Status.SERVER_UP));
		};
	}
}
