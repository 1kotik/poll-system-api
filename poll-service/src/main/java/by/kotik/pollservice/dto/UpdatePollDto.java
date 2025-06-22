package by.kotik.pollservice.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Data
public class UpdatePollDto {
    private String title;
    private String description;
    private boolean anonymous;
    private boolean multipleChoice;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private Set<String> tags;
    private List<OptionDto> options;
}
