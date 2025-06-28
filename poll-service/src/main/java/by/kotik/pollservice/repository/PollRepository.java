package by.kotik.pollservice.repository;

import by.kotik.pollservice.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PollRepository extends JpaRepository<Poll, UUID> {
    @Query("select p from Poll p left join fetch p.tags where p.id = :id")
    Optional<Poll> findByIdWithTagsAndOptions(UUID id);

}
