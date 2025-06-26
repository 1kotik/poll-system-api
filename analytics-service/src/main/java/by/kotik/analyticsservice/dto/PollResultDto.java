package by.kotik.analyticsservice.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class PollResultDto {
    private UUID pollId;
    private int totalVotes;
    private ZonedDateTime calculatedAt;
    private List<OptionResultDto> options = new ArrayList<>();
}
