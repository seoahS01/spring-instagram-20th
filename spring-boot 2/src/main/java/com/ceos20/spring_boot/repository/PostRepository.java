import com.example.yourprojectname.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}

@SpringBootTest
@Transactional
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void givenPostAndComments_whenSaved_thenFindCommentsByPost() {
        // given
        Post post = new Post();
        post.setContent("This is a post");
        postRepository.save(post);

        Comment comment1 = new Comment();
        comment1.setContent("First comment");
        comment1.setPost(post);
        commentRepository.save(comment1);

        Comment comment2 = new Comment();
        comment2.setContent("Second comment");
        comment2.setPost(post);
        commentRepository.save(comment2);

        Comment comment3 = new Comment();
        comment3.setContent("Third comment");
        comment3.setPost(post);
        commentRepository.save(comment3);

        // when
        List<Comment> comments = commentRepository.findByPostId(post.getId());

        // then
        assertEquals(3, comments.size());
    }
}
