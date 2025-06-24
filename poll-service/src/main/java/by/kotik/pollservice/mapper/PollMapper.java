package by.kotik.pollservice.mapper;

import by.kotik.pollservice.dto.PollWithoutOptionsDto;
import by.kotik.pollservice.dto.UpdatePollDto;
import by.kotik.pollservice.entity.Poll;
import by.kotik.pollservice.entity.Tag;
import dto.PollDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {OptionMapper.class})
public interface PollMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.ZonedDateTime.now())")
    @Mapping(target = "anonymous", defaultValue = "false")
    @Mapping(target = "multipleChoice", defaultValue = "false")
    Poll pollDtoToPoll(PollDto pollDto);

    @Mapping(source="tags", target="tags", qualifiedByName = "extractTagNames")
    PollDto pollToPollDto(Poll poll);

    @Mapping(source="tags", target="tags", qualifiedByName = "extractTagNames")
    PollWithoutOptionsDto pollToPollWithoutOptionsDto(Poll poll);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "anonymous", defaultValue = "false")
    @Mapping(target = "multipleChoice", defaultValue = "false")
    Poll updatePollDtoToPoll(UpdatePollDto pollDto);

    @Named("extractTagNames")
    default Set<String> extractTagNames(Set<Tag> tags) {
        return tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
    }
}
