package com.example.dailyreport.repository;

import com.example.dailyreport.entity.DailyReport;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DailyReportRepository extends MongoRepository<DailyReport, String> {
    List<DailyReport> findByUserId(String userId);
}
