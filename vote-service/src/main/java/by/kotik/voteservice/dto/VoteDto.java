package by.kotik.voteservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    private UUID id;
    private UUID pollId;
    private UUID optionId;
    private UUID userId;
    private ZonedDateTime createdAt;
    private String ipAddress;

    public VoteDto(UUID pollId, UUID optionId, UUID userId, String ipAddress) {
        this.pollId = pollId;
        this.optionId = optionId;
        this.userId = userId;
        this.ipAddress = ipAddress;
    }
}
