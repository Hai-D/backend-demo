package com.example.dailyreport.controller;
//
import com.example.dailyreport.entity.User;
import com.example.dailyreport.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
//
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        if (userRepository.findByUsername(request.get("username")).isPresent()) {
            return ResponseEntity.badRequest().body("用户已存在");
        }
        User user = new User();
        user.setUsername(request.get("username"));
        user.setPassword(request.get("password")); // 生产环境请加密存储
        user.setRole("USER");
        userRepository.save(user);
        return ResponseEntity.ok("用户注册成功");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        Optional<User> userOpt = userRepository.findByUsername(request.get("username"));
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(request.get("password"))) {
            return ResponseEntity.ok("登录成功");
        }
        return ResponseEntity.badRequest().body("用户名或密码错误");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username"); // 从 Session 里获取用户名
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登录");
        }
        return ResponseEntity.ok(Collections.singletonMap("username", username));
    }

}
