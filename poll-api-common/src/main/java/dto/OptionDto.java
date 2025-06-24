package dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OptionDto {
    private UUID id;
    private String text;
    private int position;
    private UUID pollId;
}
