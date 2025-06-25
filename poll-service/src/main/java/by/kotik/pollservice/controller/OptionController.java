package by.kotik.pollservice.controller;

import by.kotik.pollservice.dto.RequiredUserCredentialsDto;
import by.kotik.pollservice.service.OptionService;
import by.kotik.pollservice.util.UserCredentialsUtils;
import dto.OptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/options")
public class OptionController {
    @Autowired
    private OptionService optionService;

    @PostMapping("/add/{pollId}")
    public ResponseEntity<List<OptionDto>> addOptions(@PathVariable UUID pollId, @RequestBody List<OptionDto> options,
                                                      @RequestHeader("Authorization") String authHeader) {
        List<OptionDto> allOptionsOfPoll = optionService.saveOptions(pollId, options, authHeader);
        return ResponseEntity.ok(allOptionsOfPoll);
    }

    @GetMapping("/get/{pollId}")
    public ResponseEntity<List<OptionDto>> getOptions(@PathVariable UUID pollId) {
        List<OptionDto> options = optionService.getOptions(pollId);
        return ResponseEntity.ok(options);
    }

    @DeleteMapping("/delete/{optionId}")
    public ResponseEntity<OptionDto> deleteOption(@PathVariable UUID optionId,
                                                  @RequestHeader("Authorization") String authHeader) {
        OptionDto option = optionService.deleteOption(optionId, authHeader);
        return ResponseEntity.ok(option);
    }
}
