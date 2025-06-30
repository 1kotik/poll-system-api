package by.kotik.voteservice.mapper;

import by.kotik.voteservice.entity.Vote;
import dto.VoteDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoteMapper {
    VoteDto voteToVoteDto(Vote vote);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Vote voteDtoToVote(VoteDto voteDto);

}
