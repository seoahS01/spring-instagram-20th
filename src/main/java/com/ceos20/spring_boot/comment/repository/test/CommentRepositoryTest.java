package com.ceos20.spring_boot.comment.repository.test;

import com.ceos20.spring_boot.comment.domain.Comment;
import com.ceos20.spring_boot.comment.repository.CommentRepository;
import com.ceos20.spring_boot.comment.repository.test.CommentRepositoryTest;
import com.ceos20.spring_boot.user.domain.User;
import com.ceos20.spring_boot.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest  // JPA 관련 테스트 설정
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveCommentWithUser() {
        // 연관된 User 엔티티 생성
        User user = User.builder()
                .username("testuser")
                .password("password")
                .build();
        userRepository.save(user);

        // Comment 엔티티 저장
        Comment comment = Comment.builder()
                .commentContent("Test comment with user")
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .likeNum(0)
                .postId(1L)
                .user(user)  // User와 연관관계 매핑
                .build();

        Comment savedComment = commentRepository.save(comment);

        // 저장된 Comment가 User와 제대로 매핑되었는지 검증
        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getUser()).isEqualTo(user);
    }

    @Test
    void testNPlusOneProblem() {
        // User 및 Comment 여러 개 생성
        User user1 = User.builder().username("user1").password("password").build();
        User user2 = User.builder().username("user2").password("password").build();
        userRepository.saveAll(List.of(user1, user2));

        Comment comment1 = Comment.builder().commentContent("comment 1").user(user1).postId(1L).build();
        Comment comment2 = Comment.builder().commentContent("comment 2").user(user2).postId(1L).build();
        commentRepository.saveAll(List.of(comment1, comment2));

        // 모든 댓글을 조회할 때 N+1 문제가 발생하는지 확인
        List<Comment> comments = commentRepository.findAll();

        // N+1 문제가 발생하지 않도록 'fetch join'을 사용하는지 검증할 수 있음
        for (Comment comment : comments) {
            assertThat(comment.getUser()).isNotNull();  // Lazy loading 시 N+1 문제 유발 가능
        }
    }
}
