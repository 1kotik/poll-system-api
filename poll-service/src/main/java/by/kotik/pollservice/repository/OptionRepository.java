package by.kotik.pollservice.repository;

import by.kotik.pollservice.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OptionRepository extends JpaRepository<Option, UUID> {
    Optional<List<Option>> findByPollId(UUID pollId);

    @Modifying
    @Query("update Option o set o.position = o.position - 1 where o.poll.id = :pollId and o.position > :position")
    void updatePositionsAfterDelete(UUID pollId, int position);
}
