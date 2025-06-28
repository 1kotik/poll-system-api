package by.kotik.pollservice.service;

import by.kotik.pollservice.dto.RequiredUserCredentialsDto;
import by.kotik.pollservice.entity.Option;
import by.kotik.pollservice.entity.Poll;
import by.kotik.pollservice.exception.OptionNotFoundException;
import by.kotik.pollservice.mapper.OptionMapper;
import by.kotik.pollservice.repository.OptionRepository;
import by.kotik.pollservice.util.OptionUtils;
import by.kotik.pollservice.util.UserCredentialsUtils;
import dto.OptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OptionService {
    private final OptionRepository optionRepository;
    private final OptionMapper optionMapper;
    private final PollService pollService;

    @Transactional
    public List<OptionDto> saveOptions(UUID pollId, List<OptionDto> optionDtos, String authHeader) {
        RequiredUserCredentialsDto userDto = UserCredentialsUtils.getUserIdFromAuthHeader(authHeader);
        Poll poll = pollService.getPollEntity(pollId);
        UserCredentialsUtils.validateUserIds(poll.getCreatedBy(), userDto);

        List<Option> options = new ArrayList<>(poll.getOptions());
        options.addAll(optionDtos.stream()
                .map(optionMapper::optionDtoToOption)
                .peek(option -> option.setPoll(poll))
                .toList());

        OptionUtils.sortOptionPositions(options);

        poll.getOptions().clear();
        poll.getOptions().addAll(options);

        return optionRepository.saveAll(options).stream()
                .map(optionMapper::optionToOptionDto)
                .toList();
    }

    @Transactional
    public List<OptionDto> getOptions(UUID pollId) {
        return optionRepository.findByPollId(pollId)
                .filter(list -> !list.isEmpty())
                .orElseThrow(OptionNotFoundException::new)
                .stream()
                .map(optionMapper::optionToOptionDto)
                .toList();
    }

    @Transactional
    public OptionDto deleteOption(UUID optionId, String authHeader) {
        RequiredUserCredentialsDto userDto = UserCredentialsUtils.getUserIdFromAuthHeader(authHeader);
        Option option = optionRepository.findById(optionId)
                .orElseThrow(OptionNotFoundException::new);
        UserCredentialsUtils.validateUserIds(option.getPoll().getCreatedBy(), userDto);

        OptionDto optionDto = optionMapper.optionToOptionDto(option);
        optionRepository.delete(option);
        optionRepository.updatePositionsAfterDelete(optionDto.getPollId(), optionDto.getPosition());

        return optionDto;
    }
}
