package com.ceos20.spring_boot.comment.service.test;

import com.ceos20.spring_boot.comment.domain.Comment;
import com.ceos20.spring_boot.comment.repository.CommentRepository;
import com.ceos20.spring_boot.comment.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddComment() {
        Comment comment = Comment.builder()
                .commentContent("New Comment")
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .likeNum(0)
                .postId(1L)
                .build();

        // Mocking the repository save method
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment savedComment = commentService.addComment(comment);

        // 검증: 저장된 댓글이 null이 아니어야 함
        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getCommentContent()).isEqualTo("New Comment");

        // Verify that the repository's save method was called
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void testGetCommentById() {
        Comment comment = Comment.builder()
                .commentContent("Test Comment")
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .likeNum(0)
                .postId(1L)
                .build();

        // Mocking the repository findById method
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        Optional<Comment> foundComment = Optional.ofNullable(commentService.getCommentById(1L));

        // 검증: 댓글이 존재해야 함
        assertThat(foundComment).isPresent();
        assertThat(foundComment.get().getCommentContent()).isEqualTo("Test Comment");

        // Verify that the repository's findById method was called
        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllComments() {
        Comment comment1 = Comment.builder().commentContent("Comment 1").build();
        Comment comment2 = Comment.builder().commentContent("Comment 2").build();

        // Mocking the repository findAll method
        when(commentRepository.findAll()).thenReturn(List.of(comment1, comment2));

        // List<Comment> allComments = commentService.getAllComments();

        // 검증: 두 개의 댓글이 조회되어야 함
        // assertThat(allComments).hasSize(2);

        // Verify that the repository's findAll method was called
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void testDeleteComment() {
        Long commentId = 1L;

        // Mocking the repository deleteById method
        doNothing().when(commentRepository).deleteById(commentId);

        commentService.deleteComment(commentId);

        // Verify that the repository's deleteById method was called
        verify(commentRepository, times(1)).deleteById(commentId);
    }
}
