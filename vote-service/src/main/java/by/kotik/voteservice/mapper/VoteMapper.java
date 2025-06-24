package by.kotik.voteservice.mapper;

import by.kotik.voteservice.dto.VoteDto;
import by.kotik.voteservice.entity.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoteMapper {
    VoteDto voteToVoteDto(Vote vote);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.ZonedDateTime.now())")
    Vote voteDtoToVote(VoteDto voteDto);

}
