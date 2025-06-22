package by.kotik.pollservice.validator;

import by.kotik.pollservice.exception.InvalidPollDuration;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class PollValidator {
    public void validatePollDates(ZonedDateTime startDate, ZonedDateTime endDate) {
        Optional.ofNullable(startDate)
                .ifPresent(start -> {
                    validateDate(start);
                    if (endDate != null && startDate.isAfter(endDate)) {
                        throw new InvalidPollDuration("End date must be after start date");
                    }
                });
        Optional.ofNullable(endDate)
                .ifPresent(this::validateDate);
    }

    private void validateDate(ZonedDateTime date) {
        if (date.isBefore(ZonedDateTime.now())) {
            throw new InvalidPollDuration("Illegal date value");
        }
    }
}
