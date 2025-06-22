package by.kotik.pollservice.service;

import by.kotik.pollservice.dto.OptionDto;
import by.kotik.pollservice.entity.Option;
import by.kotik.pollservice.entity.Poll;
import by.kotik.pollservice.exception.OptionNotFoundException;
import by.kotik.pollservice.mapper.OptionMapper;
import by.kotik.pollservice.repository.OptionRepository;
import by.kotik.pollservice.util.OptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OptionService {
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private OptionMapper optionMapper;
    @Autowired
    private PollService pollService;

    @Transactional
    public List<OptionDto> saveOptions(String pollId, List<OptionDto> optionDtos) {
        Poll poll = pollService.getPollEntity(pollId);
        optionDtos.addAll(poll.getOptions().stream()
                .map(option -> optionMapper.optionToOptionDto(option)).toList());
        poll.getOptions().clear();
        List<Option> options = optionDtos.stream()
                .map(dto -> optionMapper.optionDtoToOption(dto))
                .toList();
        OptionUtils.sortOptionPositions(options);
        poll.getOptions().addAll(options);
        options.forEach(option -> option.setPoll(poll));
        return optionRepository.saveAll(options).stream()
                .map(option -> optionMapper.optionToOptionDto(option))
                .toList();
    }

    @Transactional
    public List<OptionDto> getOptions(String pollId) {
        return optionRepository.findByPollId(UUID.fromString(pollId))
                .filter(list -> !list.isEmpty())
                .orElseThrow(OptionNotFoundException::new)
                .stream()
                .map(option -> optionMapper.optionToOptionDto(option))
                .toList();
    }

    @Transactional
    public OptionDto deleteOption(String optionId) {
        Option option = optionRepository.findById(UUID.fromString(optionId))
                .orElseThrow(OptionNotFoundException::new);
        OptionDto optionDto = optionMapper.optionToOptionDto(option);
        optionRepository.delete(option);
        Optional<List<Option>> optionsToReorder = optionRepository.findByPollId(optionDto.getPollId());
        optionsToReorder.ifPresent(options -> {
            OptionUtils.sortOptionPositions(options);
            optionRepository.saveAll(options);
        });
        return optionDto;
    }
}
