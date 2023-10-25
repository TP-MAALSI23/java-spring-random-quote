package com.testCICD.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class Api {
    public static void main(String[] args) {
        SpringApplication.run(Api.class, args);
    }
}

@RestController
class CitationController {

    private static final String FILE_PATH = "citations.txt";
    private final Random random = new Random();

    @GetMapping("/citation")
    public String getRandomCitation() {
        try {
            Resource resource = new ClassPathResource(FILE_PATH);
            InputStream is = resource.getInputStream();
            List<String> lines = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines().toList();

            if (lines.isEmpty()) {
                return "No citations found!";
            }
            return lines.get(random.nextInt(lines.size()));
        } catch (IOException e) {
            // Log the error if you have a logger. For now, just returning the error message.
            return "Error reading citations: " + e.getMessage();
        }
    }
}
