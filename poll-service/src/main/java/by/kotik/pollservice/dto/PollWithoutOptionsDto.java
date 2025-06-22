package by.kotik.pollservice.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class PollWithoutOptionsDto {
    private UUID id;
    private String title;
    private String description;
    private boolean anonymous;
    private boolean multipleChoice;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private UUID createdBy;
    private ZonedDateTime createdAt;
    private Set<String> tags;
}
