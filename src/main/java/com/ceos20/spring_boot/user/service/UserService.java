package com.ceos20.spring_boot.user.service;

import com.ceos20.spring_boot.user.domain.User;
import com.ceos20.spring_boot.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.time.LocalDate;
import java.time.Period;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    @Transactional
    public User registerUser(User user) {
        // 1. 만 14세 이상만 가입 가능
        if (!isValidAge(user.getBirthday())){
            throw new IllegalArgumentException("만 14세 이상만 가입이 가능합니다.");
        }

        // 2. 중복된 이름 허용 안함
        if (userRepository.existsByName(user.getName())) {
            throw new IllegalArgumentException("이미 해당 이름으로 가입된 사용자가 존재합니다.");
        }

        return userRepository.save(user);
    }

    // 만 14세 이상인지 확인
    // 생년월일을 받아 나이가 14세 이상인지 확인하는 메서드
    private boolean isValidAge(LocalDateTime birthday) {
        LocalDate birthDate = birthday.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();
        return age >= 14;
    }

    // 사용자 조회
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    // 사용자 삭제
    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }
}
