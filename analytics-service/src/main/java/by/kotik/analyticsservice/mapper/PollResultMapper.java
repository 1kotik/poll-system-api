package by.kotik.analyticsservice.mapper;

import by.kotik.analyticsservice.dto.PollResultDto;
import by.kotik.analyticsservice.entity.PollResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OptionResultMapper.class})
public interface PollResultMapper {
    PollResultDto toDto(PollResult pollResult);
    PollResult toEntity(PollResultDto pollResultDto);
}
