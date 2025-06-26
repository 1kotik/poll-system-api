package by.kotik.analyticsservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "poll_results")
public class PollResult {
    @Id
    @Column(name = "poll_id", nullable = false)
    private UUID pollId;
    @Column(name = "total_votes", nullable = false)
    private int totalVotes;
    @Column(name = "calculated_at", nullable = false)
    private ZonedDateTime calculatedAt;
    @OneToMany(mappedBy = "pollResult", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OptionResult> options = new ArrayList<>();
}
