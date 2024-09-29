package com.ceos20.spring_boot.comment.repository;

import com.ceos20.spring_boot.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글에 달린 댓글 수 조회
    Long countByPostId(Long postId);

}
