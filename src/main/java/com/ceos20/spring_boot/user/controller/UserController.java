package com.ceos20.spring_boot.user.controller;

import com.ceos20.spring_boot.user.domain.User;
import com.ceos20.spring_boot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController  // 이 클래스는 RESTful API의 컨트롤러임을 나타냄
@RequestMapping("/api/users")  // 기본 경로 설정
public class UserController {

    private final UserService userService;

    // 생성자를 통한 의존성 주입
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 사용자 등록 (POST 요청)
    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.registerUser(user);
        return ResponseEntity.ok(createdUser);
    }

    // 사용자 조회 (GET 요청)
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    // 사용자 삭제 (DELETE 요청)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
