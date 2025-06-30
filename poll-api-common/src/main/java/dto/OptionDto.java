package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto {
    private UUID id;
    private String text;
    private int position;
    private UUID pollId;
}
