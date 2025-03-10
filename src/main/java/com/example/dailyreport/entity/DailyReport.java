package com.example.dailyreport.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "reports")
public class DailyReport {
    @Id
    private String id;
    private String userId;
    private String content;
    private Date createdAt = new Date();
}
