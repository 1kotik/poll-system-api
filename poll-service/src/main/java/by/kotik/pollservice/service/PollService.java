package by.kotik.pollservice.service;

import by.kotik.pollservice.dto.PollDurationDto;
import by.kotik.pollservice.dto.PollWithoutOptionsDto;
import by.kotik.pollservice.dto.RequiredUserCredentialsDto;
import by.kotik.pollservice.dto.UpdatePollDto;
import by.kotik.pollservice.entity.Poll;
import by.kotik.pollservice.exception.InvalidPollDuration;
import by.kotik.pollservice.exception.PollNotFoundException;
import by.kotik.pollservice.mapper.PollMapper;
import by.kotik.pollservice.repository.PollRepository;
import by.kotik.pollservice.util.OptionUtils;
import by.kotik.pollservice.util.UserCredentialsUtils;
import by.kotik.pollservice.validator.PollValidator;
import dto.PollDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PollService {
    private final PollRepository pollRepository;
    private final PollMapper pollMapper;
    private final TagService tagService;
    private final PollValidator pollValidator;

    @Transactional
    public PollDto createPoll(PollDto pollDto, String authHeader) {
        RequiredUserCredentialsDto userDto = UserCredentialsUtils.getUserIdFromAuthHeader(authHeader);
        pollValidator.validatePollDates(pollDto.getStartDate(), pollDto.getEndDate());
        Poll poll = pollMapper.pollDtoToPoll(pollDto);

        Optional.ofNullable(pollDto.getTags())
                .ifPresent(tags -> tags
                        .forEach(tag -> poll.getTags().add(tagService.findByNameOrCreateNew(tag))));
        Optional.ofNullable(poll.getOptions())
                .ifPresent(options -> options
                        .forEach(option -> option.setPoll(poll)));
        poll.setCreatedBy(userDto.getId());

        OptionUtils.sortOptionPositions(poll.getOptions());

        pollRepository.save(poll);
        return pollMapper.pollToPollDto(poll);
    }

    @Transactional
    public PollDto updatePoll(UUID pollId, PollWithoutOptionsDto pollDto, String authHeader) {
        RequiredUserCredentialsDto userDto = UserCredentialsUtils.getUserIdFromAuthHeader(authHeader);
        Poll oldPoll = pollRepository.findById(pollId)
                .orElseThrow(() -> new PollNotFoundException(pollId));
        UserCredentialsUtils.validateUserIds(oldPoll.getCreatedBy(), userDto);
        pollValidator.validatePollDates(pollDto.getStartDate(), pollDto.getEndDate());

        pollMapper.pollWithoutOptionsDtoToPoll(pollDto, oldPoll);

        oldPoll.getTags().clear();
        Optional.ofNullable(pollDto.getTags())
                .ifPresent(tags ->tags
                        .forEach(tag -> oldPoll.getTags().add(tagService.findByNameOrCreateNew(tag))));

        Poll updatedPoll = pollRepository.save(oldPoll);
        return pollMapper.pollToPollDto(updatedPoll);
    }

    @Transactional(readOnly = true)
    public PollDto getPollById(UUID pollId) {
        return pollRepository.findByIdWithTagsAndOptions(pollId)
                .map(pollMapper::pollToPollDto)
                .orElseThrow(() -> new PollNotFoundException(pollId));
    }

    @Transactional
    public PollDto deletePoll(UUID pollId, String authHeader) {
        RequiredUserCredentialsDto userDto = UserCredentialsUtils.getUserIdFromAuthHeader(authHeader);
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new PollNotFoundException(pollId));
        UserCredentialsUtils.validateUserIds(poll.getCreatedBy(), userDto);
        PollDto pollDto = pollMapper.pollToPollDto(poll);
        pollRepository.delete(poll);
        return pollDto;
    }

    @Transactional(readOnly = true)
    public Poll getPollEntity(UUID pollId) {
        return pollRepository.findById(pollId)
                .orElseThrow(() -> new PollNotFoundException(pollId));
    }

    @Transactional
    public PollDto setPollDuration(UUID pollId, PollDurationDto pollDurationDto, String authHeader) {
        RequiredUserCredentialsDto userDto = UserCredentialsUtils.getUserIdFromAuthHeader(authHeader);
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new PollNotFoundException(pollId));
        UserCredentialsUtils.validateUserIds(poll.getCreatedBy(), userDto);
        pollValidator.validatePollDates(
                pollDurationDto.getStartDate() == null ? poll.getStartDate() : pollDurationDto.getStartDate(),
                pollDurationDto.getEndDate() == null ? poll.getEndDate() : pollDurationDto.getEndDate());

        Optional.ofNullable(pollDurationDto.getStartDate())
                .ifPresent(poll::setStartDate);
        Optional.ofNullable(pollDurationDto.getEndDate())
                .ifPresent(poll::setEndDate);

        pollRepository.save(poll);
        return pollMapper.pollToPollDto(poll);
    }

    @Transactional
    public PollDto toggleAnonymousParameter(UUID pollId, String authHeader) {
        RequiredUserCredentialsDto userDto = UserCredentialsUtils.getUserIdFromAuthHeader(authHeader);
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new PollNotFoundException(pollId));
        UserCredentialsUtils.validateUserIds(poll.getCreatedBy(), userDto);

        poll.setAnonymous(!poll.isAnonymous());

        pollRepository.save(poll);
        return pollMapper.pollToPollDto(poll);
    }

    @Transactional
    public PollDto toggleMultipleChoiceParameter(UUID pollId, String authHeader) {
        RequiredUserCredentialsDto userDto = UserCredentialsUtils.getUserIdFromAuthHeader(authHeader);
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new PollNotFoundException(pollId));
        UserCredentialsUtils.validateUserIds(poll.getCreatedBy(), userDto);

        poll.setMultipleChoice(!poll.isMultipleChoice());

        pollRepository.save(poll);
        return pollMapper.pollToPollDto(poll);
    }

    @Transactional
    public PollDto endPoll(UUID pollId, String authHeader) {
        RequiredUserCredentialsDto userDto = UserCredentialsUtils.getUserIdFromAuthHeader(authHeader);
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new PollNotFoundException(pollId));
        UserCredentialsUtils.validateUserIds(poll.getCreatedBy(), userDto);

        if (poll.getEndDate() != null && poll.getEndDate().isBefore(ZonedDateTime.now())) {
            throw new InvalidPollDuration("Poll have already ended.");
        }
        if (poll.getStartDate() == null || poll.getStartDate().isAfter(ZonedDateTime.now())) {
            throw new InvalidPollDuration("Poll have not started yet.");

        }

        poll.setEndDate(ZonedDateTime.now(ZoneId.systemDefault()).truncatedTo(ChronoUnit.SECONDS));

        pollRepository.save(poll);
        return pollMapper.pollToPollDto(poll);
    }

    @Transactional
    public PollDto startPoll(UUID pollId, String authHeader) {
        RequiredUserCredentialsDto userDto = UserCredentialsUtils.getUserIdFromAuthHeader(authHeader);
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new PollNotFoundException(pollId));
        UserCredentialsUtils.validateUserIds(poll.getCreatedBy(), userDto);

        if (poll.getStartDate() != null && poll.getStartDate().isBefore(ZonedDateTime.now())) {
            throw new InvalidPollDuration("Poll have already started.");
        }
        if (poll.getEndDate() != null && poll.getEndDate().isBefore(ZonedDateTime.now())) {
            throw new InvalidPollDuration("Poll have already ended.");
        }

        poll.setStartDate(ZonedDateTime.now(ZoneId.systemDefault()).truncatedTo(ChronoUnit.SECONDS));

        pollRepository.save(poll);
        return pollMapper.pollToPollDto(poll);
    }
}
