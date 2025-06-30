package by.kotik.voteservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "poll_id", nullable = false)
    private UUID pollId;
    @Column(name="option_id", nullable = false)
    private UUID optionId;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(name="created_at", nullable = false)
    private ZonedDateTime createdAt;
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;
}
