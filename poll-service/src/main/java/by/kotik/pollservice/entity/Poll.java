package by.kotik.pollservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "polls")
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "is_anonymous", nullable = false)
    private boolean anonymous;
    @Column(name = "is_multiple_choice", nullable = false)
    private boolean multipleChoice;
    @Column(name = "start_date")
    private ZonedDateTime startDate;
    @Column(name = "end_date")
    private ZonedDateTime endDate;
    @Column(name = "created_by", nullable = false)
    private UUID createdBy;
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;
    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "polls_tags",
            joinColumns = @JoinColumn(name = "poll_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();
    @OneToMany(orphanRemoval = true, mappedBy = "poll", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Option> options = new ArrayList<>();

}
