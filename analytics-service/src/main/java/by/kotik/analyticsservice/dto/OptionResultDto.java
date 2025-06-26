package by.kotik.analyticsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionResultDto {
    private UUID optionId;
    private String text;
    private int votes;
    private float percentage;
    private UUID pollId;
}
