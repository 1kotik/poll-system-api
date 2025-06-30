package by.kotik.analyticsservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "poll_results")
public class PollResult {
    @Id
    @Column(name = "poll_id", nullable = false)
    private UUID pollId;
    @Column(name = "total_votes", nullable = false)
    private int totalVotes;
    @Column(name = "calculated_at", nullable = false)
    @CreationTimestamp
    private ZonedDateTime calculatedAt;
    @OneToMany(mappedBy = "pollResult", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    @ToString.Exclude
    private List<OptionResult> options = new ArrayList<>();
}
