//package com.example.dailyreport.controller;
//
//import com.example.dailyreport.entity.DailyReport;
//import com.example.dailyreport.repository.DailyReportRepository;
//import com.example.dailyreport.repository.UserRepository;
//import com.example.dailyreport.entity.User;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/reports")
//public class DailyReportController {
//    private final DailyReportRepository dailyReportRepository;
//    private final UserRepository userRepository;
//
//    public DailyReportController(DailyReportRepository dailyReportRepository, UserRepository userRepository) {
//        this.dailyReportRepository = dailyReportRepository;
//        this.userRepository = userRepository;
//    }
//
//    @PostMapping("/{username}")
//    public String createReport(@PathVariable String username, @RequestBody DailyReport report) {
//        Optional<User> userOpt = userRepository.findByUsername(username);
//        if (userOpt.isPresent()) {
//            report.setUserId(userOpt.get().getId());
//            dailyReportRepository.save(report);
//            return "日报提交成功";
//        }
//        return "用户不存在";
//    }
//
//    @GetMapping("/{username}")
//    public List<DailyReport> getReportsByUser(@PathVariable String username) {
//        Optional<User> userOpt = userRepository.findByUsername(username);
//        return userOpt.map(user -> dailyReportRepository.findByUserId(user.getId())).orElse(null);
//    }
//
//    @GetMapping("/all")
//    public List<DailyReport> getAllReports() {
//        return dailyReportRepository.findAll();
//    }
//}
package com.example.dailyreport.controller;

import com.example.dailyreport.entity.DailyReport;
import com.example.dailyreport.entity.User;
import com.example.dailyreport.repository.DailyReportRepository;
import com.example.dailyreport.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reports")
public class DailyReportController {
    private final DailyReportRepository dailyReportRepository;
    private final UserRepository userRepository;

    public DailyReportController(DailyReportRepository dailyReportRepository, UserRepository userRepository) {
        this.dailyReportRepository = dailyReportRepository;
        this.userRepository = userRepository;
    }

    // 提交日报
    @PostMapping("/{username}")
    public ResponseEntity<?> createReport(@PathVariable String username, @RequestBody DailyReport report) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            report.setUserId(userOpt.get().getId());
            dailyReportRepository.save(report);
            return ResponseEntity.ok("日报提交成功");
        }
        return ResponseEntity.badRequest().body("用户不存在");
    }

    // 获取指定用户的日报
    @GetMapping("/{username}")
    public List<DailyReport> getReportsByUser(@PathVariable String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.map(user -> dailyReportRepository.findByUserId(user.getId())).orElse(null);
    }

    // 管理员获取所有日报
    @GetMapping("/all")
    public List<DailyReport> getAllReports() {
        return dailyReportRepository.findAll();
    }

    // 修改日报（根据日报 id 更新 content）
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReport(@PathVariable String id, @RequestBody DailyReport updatedReport) {
        Optional<DailyReport> reportOpt = dailyReportRepository.findById(id);
        if (reportOpt.isPresent()) {
            DailyReport report = reportOpt.get();
            report.setContent(updatedReport.getContent());
            dailyReportRepository.save(report);
            return ResponseEntity.ok("更新成功");
        }
        return ResponseEntity.badRequest().body("日报不存在");
    }

    // 删除日报（根据日报 id 删除）
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable String id) {
        if (dailyReportRepository.existsById(id)) {
            dailyReportRepository.deleteById(id);
            return ResponseEntity.ok("删除成功");
        }
        return ResponseEntity.badRequest().body("日报不存在");
    }
}
