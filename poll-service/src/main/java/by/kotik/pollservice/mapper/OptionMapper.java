package by.kotik.pollservice.mapper;

import by.kotik.pollservice.entity.Option;
import by.kotik.pollservice.entity.Poll;
import dto.OptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OptionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "poll", ignore = true)
    Option optionDtoToOption(OptionDto optionDto);

    @Mapping(source = "poll", target = "pollId", qualifiedByName = "getPollId")
    OptionDto optionToOptionDto(Option option);

    @Named("getPollId")
    default UUID getPollId(Poll poll) {
        return poll.getId();
    }
}
