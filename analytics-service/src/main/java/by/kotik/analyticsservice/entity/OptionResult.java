package by.kotik.analyticsservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
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
    @ToString.Exclude
    private PollResult pollResult;
}
