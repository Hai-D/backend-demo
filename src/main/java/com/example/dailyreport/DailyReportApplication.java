package com.example.dailyreport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import com.example.dailyreport.repository.UserRepository;
import com.example.dailyreport.entity.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DailyReportApplication {
    public static void main(String[] args) {
        SpringApplication.run(DailyReportApplication.class, args);
    }
    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin123"); // 明文存储，仅用于 Demo
                admin.setRole("ADMIN");
                userRepository.save(admin);
            }
        };
    }
}
