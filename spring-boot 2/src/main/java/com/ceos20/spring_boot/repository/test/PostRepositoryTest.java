import com.example.yourprojectname.domain.Post;
import com.example.yourprojectname.domain.Comment;
import com.example.yourprojectname.repository.PostRepository;
import com.example.yourprojectname.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest  // JPA 관련 테스트를 위한 어노테이션
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Post post;

    @BeforeEach
    public void setUp() {
        // Given: 테스트를 위한 Post 엔티티 생성
        post = new Post();
        post.setContent("Test Post Content");
        post.setImageUrl("test_image_url");
        postRepository.save(post);  // Post를 저장하여 영속화
    }

    @Test
    public void givenPostAndComments_whenSaved_thenFindCommentsByPost() {
        // When: Comment 객체 3개를 Post에 추가하여 저장
        Comment comment1 = new Comment();
        comment1.setPost(post);
        comment1.setContent("First comment");
        commentRepository.save(comment1);

        Comment comment2 = new Comment();
        comment2.setPost(post);
        comment2.setContent("Second comment");
        commentRepository.save(comment2);

        Comment comment3 = new Comment();
        comment3.setPost(post);
        comment3.setContent("Third comment");
        commentRepository.save(comment3);

        // Then: Post에 해당하는 댓글을 조회
        List<Comment> comments = commentRepository.findByPostId(post.getId());

        // 3개의 댓글이 저장되었는지 확인
        assertThat(comments).hasSize(3);
        assertThat(comments).extracting("content").contains("First comment", "Second comment", "Third comment");
    }
}
