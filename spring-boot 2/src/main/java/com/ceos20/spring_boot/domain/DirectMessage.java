import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "DirectMessage")
public class DirectMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @NotNull
    @Size(max = 20)
    @Column(name = "message")
    private String message;

    @NotNull
    @Column(name = "created_at")
    private Timestamp createdAt;

    @NotNull
    @Column(name = "is_read")
    private Boolean isRead;
}
