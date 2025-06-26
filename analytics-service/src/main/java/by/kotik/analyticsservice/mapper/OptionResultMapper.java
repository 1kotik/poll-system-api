package by.kotik.analyticsservice.mapper;

import by.kotik.analyticsservice.dto.OptionResultDto;
import by.kotik.analyticsservice.entity.OptionResult;
import by.kotik.analyticsservice.entity.PollResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OptionResultMapper {
    @Mapping(source = "pollResult", target = "pollId", qualifiedByName = "getPollId")
    OptionResultDto toDto(OptionResult optionResult);
    @Mapping(target = "pollResult", ignore = true)
    OptionResult toEntity(OptionResultDto optionResultDto);

    @Named("getPollId")
    default UUID getPollId(PollResult pollResult) {
        return pollResult.getPollId();
    }
}
