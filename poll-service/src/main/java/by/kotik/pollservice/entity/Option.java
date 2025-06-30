package by.kotik.pollservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "options")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "position", nullable = false)
    private int position;
    @ManyToOne
    @JoinColumn(name = "poll_id", nullable = false)
    @ToString.Exclude
    private Poll poll;
}
