package by.kotik.voteservice.service;

import by.kotik.voteservice.client.PollServiceClient;
import by.kotik.voteservice.entity.Vote;
import by.kotik.voteservice.mapper.VoteMapper;
import by.kotik.voteservice.repository.VoteRepository;
import by.kotik.voteservice.validator.VoteValidator;
import dto.PollDto;
import dto.VoteDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import util.JwtUtils;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;
    private final PollServiceClient pollServiceClient;
    private final VoteValidator voteValidator;
    @Value("${jwt.secret}")
    private String secret;

    @Transactional(readOnly = true)
    public List<VoteDto> getByPollId(UUID pollId, ZonedDateTime startDate, ZonedDateTime endDate) {
        List<VoteDto> votes = voteRepository.findByPollId(pollId).stream()
                .map(voteMapper::voteToVoteDto)
                .sorted(Comparator.comparing(VoteDto::getCreatedAt))
                .toList();
        return votes.size() > 1 ? filterVotesByDate(votes, startDate, endDate) : votes;
    }

    @Transactional
    public List<VoteDto> vote(UUID pollId, String authHeader, List<UUID> selectedOptions) {
        UUID userId = JwtUtils.getId(JwtUtils.extractTokenFromHeader(authHeader), secret);
        List<Vote> userVotesOnCurrentPoll = voteRepository.findByPollIdAndUserId(pollId, userId);
        voteValidator.ifUserAlreadyVoted(userVotesOnCurrentPoll);
        PollDto pollDto = pollServiceClient.getPollDto(pollId).getBody();
        voteValidator.validateVote(pollDto, selectedOptions);
        String ipAddress = getClientIpAddress();
        List<Vote> votes = selectedOptions.stream()
                .distinct()
                .map(optionId -> voteMapper.voteDtoToVote(new VoteDto(pollId, optionId, userId, ipAddress)))
                .toList();
        return voteRepository.saveAll(votes)
                .stream()
                .map(voteMapper::voteToVoteDto)
                .toList();
    }

    private String getClientIpAddress() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        HttpServletRequest request = attributes.getRequest();
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    private List<VoteDto> filterVotesByDate(List<VoteDto> votes, ZonedDateTime startDate, ZonedDateTime endDate) {
        startDate = startDate == null ? votes.get(0).getCreatedAt() : startDate;
        endDate = endDate == null ? votes.get(votes.size() - 1).getCreatedAt() : endDate;
        ZonedDateTime finalEndDate = endDate;
        ZonedDateTime finalStartDate = startDate;
        Predicate<VoteDto> dateFilter = vote ->
                (vote.getCreatedAt().isBefore(finalEndDate) || vote.getCreatedAt().equals(finalEndDate))
                && (vote.getCreatedAt().isAfter(finalStartDate) || vote.getCreatedAt().equals(finalStartDate));
        return votes.stream()
                .filter(dateFilter)
                .toList();
    }


}
