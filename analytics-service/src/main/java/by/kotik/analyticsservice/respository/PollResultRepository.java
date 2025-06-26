package by.kotik.analyticsservice.respository;

import by.kotik.analyticsservice.entity.PollResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PollResultRepository extends JpaRepository<PollResult, UUID> {

}
