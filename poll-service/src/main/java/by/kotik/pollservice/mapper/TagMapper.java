package by.kotik.pollservice.mapper;

import by.kotik.pollservice.dto.TagDto;
import by.kotik.pollservice.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper {
    @Mapping(target = "id", ignore = true)
    Tag tagDtoToTag(TagDto tagDto);

    TagDto tagToTagDto(Tag tag);
}
