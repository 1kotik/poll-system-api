package by.kotik.voteservice.validator;

import by.kotik.voteservice.entity.Vote;
import by.kotik.voteservice.exception.ClosedPollException;
import by.kotik.voteservice.exception.InvalidSelectedOptionsException;
import by.kotik.voteservice.exception.UserAlreadyVotedException;
import dto.OptionDto;
import dto.PollDto;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class VoteValidator {
    public void validateVote(PollDto pollDto, List<UUID> selectedOptions) {
        validateVoteTime(pollDto);
        validateVoteOptions(pollDto, selectedOptions);
    }

    public void ifUserAlreadyVoted(List<Vote> userVotesOnCurrentPoll) {
        if (!userVotesOnCurrentPoll.isEmpty()) {
            throw new UserAlreadyVotedException();
        }
    }

    private void validateVoteTime(PollDto pollDto) {
        if (invalidVoteTimeCondition(pollDto)) {
            throw new ClosedPollException();
        }
    }

    private boolean invalidVoteTimeCondition(PollDto pollDto) {
        System.out.println(pollDto.getStartDate() == null);
        System.out.println(pollDto.getStartDate().isAfter(ZonedDateTime.now()));
        System.out.println(pollDto.getEndDate() != null);
        return pollDto.getStartDate() == null || pollDto.getStartDate().isAfter(ZonedDateTime.now())
                || (pollDto.getEndDate() != null && pollDto.getEndDate().isBefore(ZonedDateTime.now()));
    }

    private void validateVoteOptions(PollDto pollDto, List<UUID> selectedOptions) {
        if (invalidSelectedOptionsSizeCondition(pollDto, selectedOptions)) {
            throw new InvalidSelectedOptionsException();
        }
        List<UUID> pollOptions = pollDto.getOptions().stream()
                .map(OptionDto::getId)
                .toList();
        if (!pollOptions.containsAll(selectedOptions)) {
            throw new InvalidSelectedOptionsException();
        }
    }

    private boolean invalidSelectedOptionsSizeCondition(PollDto pollDto, List<UUID> selectedOptions) {
        return (selectedOptions.size() > 1 && !pollDto.isMultipleChoice())
                || selectedOptions.isEmpty();
    }
}
