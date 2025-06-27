package by.kotik.analyticsservice.service;

import by.kotik.analyticsservice.client.PollServiceClient;
import by.kotik.analyticsservice.client.VoteServiceClient;
import by.kotik.analyticsservice.dto.OptionResultDto;
import by.kotik.analyticsservice.dto.PollResultDto;
import by.kotik.analyticsservice.entity.PollResult;
import by.kotik.analyticsservice.exception.AnalyticsException;
import by.kotik.analyticsservice.mapper.PollResultMapper;
import by.kotik.analyticsservice.respository.PollResultRepository;
import dto.OptionDto;
import dto.PollDto;
import dto.VoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PollResultService {
    private final PollResultRepository pollResultRepository;
    private final PollResultMapper pollResultMapper;
    private final PollServiceClient pollServiceClient;
    private final VoteServiceClient voteServiceClient;

    @Transactional
    public PollResultDto getPollResult(UUID pollId, ZonedDateTime startDate,
                                       ZonedDateTime endDate) {
        return getPollFromDbIfDatesAreNull(pollId, startDate, endDate)
                .map(pollResultMapper::toDto)
                .orElseGet(() -> getPollResultFromVoteService(pollId, startDate, endDate));
    }

    private Optional<PollResult> getPollFromDbIfDatesAreNull(UUID pollId, ZonedDateTime startDate,
                                                             ZonedDateTime endDate) {
        return startDate == null && endDate == null ? pollResultRepository.findById(pollId) : Optional.empty();
    }

    private PollResultDto getPollResultFromVoteService(UUID pollId, ZonedDateTime startDate,
                                                       ZonedDateTime endDate) {
        List<VoteDto> votes = voteServiceClient.getVotes(pollId, startDate, endDate);
        PollDto pollDto = pollServiceClient.getPoll(pollId);
        PollResultDto pollResultDto = countVotes(votes, pollId, pollDto);
        if (savePollResultInDbCondition(pollDto, startDate, endDate)) {
            PollResult pollResult = pollResultMapper.toEntity(pollResultDto);
            pollResult.getOptions().forEach(option -> option.setPollResult(pollResult));
            pollResultRepository.save(pollResult);
        }
        return pollResultDto;
    }

    private PollResultDto countVotes(List<VoteDto> votes, UUID pollId, PollDto pollDto) {
        if (votes.isEmpty()) {
            throw new AnalyticsException("Can't count votes because no votes were found");
        }
        if (pollDto.getOptions().isEmpty()) {
            throw new AnalyticsException("Can't count votes because no options were found");
        }

        PollResultDto pollResultDto = new PollResultDto();
        pollResultDto.setPollId(pollId);
        pollResultDto.setTotalVotes(votes.size());
        Map<UUID, List<VoteDto>> votesGroupedByOptions = votes
                .stream()
                .collect(Collectors.groupingBy(VoteDto::getOptionId));
        pollDto.getOptions()
                .forEach(optionDto -> pollResultDto.getOptions()
                        .add(getOptionResultDto(pollResultDto, optionDto, votesGroupedByOptions)));
        pollResultDto.setCalculatedAt(ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return pollResultDto;
    }

    private OptionResultDto getOptionResultDto(PollResultDto pollResultDto, OptionDto optionDto,
                                               Map<UUID, List<VoteDto>> votesGroupedByOptions) {
        List<VoteDto> votes = votesGroupedByOptions.get(optionDto.getId());
        int optionVotes = votes == null ? 0 : votes.size();
        return new OptionResultDto(
                optionDto.getId(),
                optionDto.getText(),
                optionVotes,
                calculatePercentage(optionVotes, pollResultDto.getTotalVotes()), pollResultDto.getPollId());
    }

    private float calculatePercentage(int optionVotes, int totalVotes) {
        return (float) optionVotes / (float) totalVotes * 100;
    }

    private boolean savePollResultInDbCondition(PollDto pollDto, ZonedDateTime startDate, ZonedDateTime endDate) {
        return pollDto.getEndDate() != null && pollDto.getStartDate() != null
                && (startDate == null || startDate.isBefore(pollDto.getStartDate()))
                && (endDate == null || endDate.isAfter(pollDto.getEndDate()));
    }
}
