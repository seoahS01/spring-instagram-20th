package com.ceos20.spring_boot.user.service;

import com.ceos20.spring_boot.user.domain.User;
import com.ceos20.spring_boot.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service  // 이 클래스는 서비스 계층임을 나타냄
public class UserService {

    private final UserRepository userRepository;

    // 생성자를 통한 의존성 주입
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 사용자 등록 (회원가입)
    @Transactional  // 데이터베이스에 변경이 생기는 메서드에 트랜잭션 적용
    public User registerUser(User user) {
        if (userRepository.existsByName(user.getName())) {
            throw new IllegalArgumentException("User with this name already exists");
        }

        // 비즈니스 로직 추가 가능 (예: 비밀번호 암호화)
        return userRepository.save(user);
    }

    // 사용자 조회
    @Transactional(readOnly = true)  // 읽기 전용 트랜잭션 (성능 최적화)
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
