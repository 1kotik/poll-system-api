package by.kotik.pollservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "options")
@Data
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
    private Poll poll;
}
