package by.kotik.pollservice.controller;

import by.kotik.pollservice.dto.OptionDto;
import by.kotik.pollservice.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/polls")
public class OptionController {
    @Autowired
    private OptionService optionService;

    @PostMapping("/add-options/{pollId}")
    public ResponseEntity<List<OptionDto>> addOptions(@PathVariable String pollId, @RequestBody List<OptionDto> options) {
        List<OptionDto> allOptionsOfPoll = optionService.saveOptions(pollId, options);
        return ResponseEntity.ok(allOptionsOfPoll);
    }

    @GetMapping("/get-options/{pollId}")
    public ResponseEntity<List<OptionDto>> getOptions(@PathVariable String pollId) {
        List<OptionDto> options = optionService.getOptions(pollId);
        return ResponseEntity.ok(options);
    }

    @DeleteMapping("/delete-option/{optionId}")
    public ResponseEntity<OptionDto> deleteOption(@PathVariable String optionId) {
        OptionDto option = optionService.deleteOption(optionId);
        return ResponseEntity.ok(option);
    }
}
