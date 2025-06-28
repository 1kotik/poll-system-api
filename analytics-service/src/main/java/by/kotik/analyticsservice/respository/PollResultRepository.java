package by.kotik.analyticsservice.respository;

import by.kotik.analyticsservice.entity.PollResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PollResultRepository extends JpaRepository<PollResult, UUID> {
    @Query("select p from PollResult p left join p.options where p.pollId = :id")
    Optional<PollResult> findByIdWithOptions(UUID id);
}
