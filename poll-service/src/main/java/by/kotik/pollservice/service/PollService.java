package by.kotik.pollservice.service;

import by.kotik.pollservice.dto.PollDto;
import by.kotik.pollservice.dto.PollDurationDto;
import by.kotik.pollservice.dto.RequiredUserCredentialsDto;
import by.kotik.pollservice.dto.UpdatePollDto;
import by.kotik.pollservice.entity.Poll;
import by.kotik.pollservice.exception.PollNotFoundException;
import by.kotik.pollservice.mapper.PollMapper;
import by.kotik.pollservice.repository.PollRepository;
import by.kotik.pollservice.util.OptionUtils;
import by.kotik.pollservice.util.UserCredentialsUtils;
import by.kotik.pollservice.validator.PollValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Service
public class PollService {
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private PollMapper pollMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private PollValidator pollValidator;

    @Transactional
    public PollDto createPoll(PollDto pollDto, RequiredUserCredentialsDto userDto) {
        pollValidator.validatePollDates(pollDto.getStartDate(), pollDto.getEndDate());
        Poll poll = pollMapper.pollDtoToPoll(pollDto);
        Optional.ofNullable(pollDto.getTags())
                .ifPresent(tags -> tags
                        .forEach(name -> poll.getTags().add(tagService.findByNameOrCreateNew(name))));
        Optional.ofNullable(poll.getOptions())
                .ifPresent(options -> options
                        .forEach(option -> option.setPoll(poll)));
        poll.setCreatedBy(userDto.getId());
        OptionUtils.sortOptionPositions(poll.getOptions());
        pollRepository.save(poll);
        return pollMapper.pollToPollDto(poll);
    }

    @Transactional
    public PollDto updatePoll(String pollId, UpdatePollDto pollDto, RequiredUserCredentialsDto userDto) {
        pollValidator.validatePollDates(pollDto.getStartDate(), pollDto.getEndDate());
        Poll newPoll = pollMapper.updatePollDtoToPoll(pollDto);
        Poll oldPoll = pollRepository.findById(UUID.fromString(pollId))
                .orElseThrow(() -> new PollNotFoundException(pollId));
        UserCredentialsUtils.validateUserIds(oldPoll.getCreatedBy(), userDto);
        oldPoll.getOptions().clear();
        oldPoll.getTags().clear();
        Optional.ofNullable(pollDto.getTags())
                .ifPresent(tags -> tags.forEach(name -> newPoll.getTags().add(tagService.findByNameOrCreateNew(name))));
        Optional.ofNullable(newPoll.getOptions())
                .ifPresent(options -> options.forEach(option -> option.setPoll(newPoll)));
        newPoll.setId(oldPoll.getId());
        newPoll.setCreatedBy(oldPoll.getCreatedBy());
        newPoll.setCreatedAt(oldPoll.getCreatedAt());
        OptionUtils.sortOptionPositions(newPoll.getOptions());
        Poll savedPoll = pollRepository.save(newPoll);
        return pollMapper.pollToPollDto(savedPoll);
    }

    @Transactional(readOnly = true)
    public PollDto getPollById(String pollId) {
        return pollRepository.findById(UUID.fromString(pollId))
                .map(poll -> pollMapper.pollToPollDto(poll))
                .orElseThrow(() -> new PollNotFoundException(pollId));
    }

    @Transactional
    public PollDto deletePoll(String pollId, RequiredUserCredentialsDto userDto) {
        Poll poll = pollRepository.findById(UUID.fromString(pollId))
                .orElseThrow(() -> new PollNotFoundException(pollId));
        UserCredentialsUtils.validateUserIds(poll.getCreatedBy(), userDto);
        PollDto pollDto = pollMapper.pollToPollDto(poll);
        pollRepository.delete(poll);
        return pollDto;
    }

    @Transactional(readOnly = true)
    public Poll getPollEntity(String pollId) {
        return pollRepository.findById(UUID.fromString(pollId))
                .orElseThrow(() -> new PollNotFoundException(pollId));
    }

    @Transactional
    public PollDto setPollDuration(String pollId, PollDurationDto pollDurationDto, RequiredUserCredentialsDto userDto) {
        Poll poll = pollRepository.findById(UUID.fromString(pollId))
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
    public PollDto toggleAnonymousParameter(String pollId, RequiredUserCredentialsDto userDto) {
        Poll poll = pollRepository.findById(UUID.fromString(pollId))
                .orElseThrow(() -> new PollNotFoundException(pollId));
        UserCredentialsUtils.validateUserIds(poll.getCreatedBy(), userDto);
        poll.setAnonymous(!poll.isAnonymous());
        pollRepository.save(poll);
        return pollMapper.pollToPollDto(poll);
    }

    @Transactional
    public PollDto toggleMultipleChoiceParameter(String pollId, RequiredUserCredentialsDto userDto) {
        Poll poll = pollRepository.findById(UUID.fromString(pollId))
                .orElseThrow(() -> new PollNotFoundException(pollId));
        UserCredentialsUtils.validateUserIds(poll.getCreatedBy(), userDto);
        poll.setMultipleChoice(!poll.isMultipleChoice());
        pollRepository.save(poll);
        return pollMapper.pollToPollDto(poll);
    }
}
