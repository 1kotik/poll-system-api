package by.kotik.analyticsservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "option_results")
public class OptionResult {
    @Id
    @Column(name = "option_id", nullable = false)
    private UUID optionId;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "votes", nullable = false)
    private int votes;
    @Column(name = "percentage", nullable = false)
    private float percentage;
    @ManyToOne
    @JoinColumn(name = "poll_id", nullable = false)
    private PollResult pollResult;
}
