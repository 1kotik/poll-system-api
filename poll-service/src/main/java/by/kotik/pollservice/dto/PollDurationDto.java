package by.kotik.pollservice.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class PollDurationDto {
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
}
