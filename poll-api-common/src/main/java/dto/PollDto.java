package dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class PollDto {
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
    private List<OptionDto> options;
}
