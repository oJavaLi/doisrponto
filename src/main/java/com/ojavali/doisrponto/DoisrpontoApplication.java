package com.ojavali.doisrponto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DoisrpontoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoisrpontoApplication.class, args);
	}

	@GetMapping("/")
	public ResponseEntity<Object> index() {
		Map<String, String> data = new HashMap<>();
		data.put("message", "Bem vindo a API 2RPonto.");
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
