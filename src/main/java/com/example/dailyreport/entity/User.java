package com.example.dailyreport.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String password; // 明文存储（仅作 Demo，生产环境请加密）
    private String role; // "USER" 或 "ADMIN"
}
