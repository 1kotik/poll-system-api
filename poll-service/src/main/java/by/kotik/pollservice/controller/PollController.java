package by.kotik.pollservice.controller;

import by.kotik.pollservice.dto.PollDto;
import by.kotik.pollservice.dto.PollDurationDto;
import by.kotik.pollservice.dto.UpdatePollDto;
import by.kotik.pollservice.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/polls")
public class PollController {
    @Autowired
    private PollService pollService;

    @PostMapping("/create-poll")
    public ResponseEntity<PollDto> createPoll(@RequestBody PollDto pollDto) {
        PollDto poll = pollService.createPoll(pollDto);
        return ResponseEntity.ok().body(poll);
    }

    @PutMapping("/update-poll/{pollId}")
    public ResponseEntity<PollDto> updatePoll(@PathVariable String pollId, @RequestBody UpdatePollDto pollDto) {
        PollDto poll = pollService.updatePoll(pollId, pollDto);
        return ResponseEntity.ok().body(poll);
    }

    @GetMapping("/get-poll/{pollId}")
    public ResponseEntity<PollDto> getPollById(@PathVariable String pollId) {
        PollDto poll = pollService.getPollById(pollId);
        return ResponseEntity.ok().body(poll);
    }

    @DeleteMapping("/delete-poll/{pollId}")
    public ResponseEntity<PollDto> deletePoll(@PathVariable String pollId) {
        PollDto poll = pollService.deletePoll(pollId);
        return ResponseEntity.ok().body(poll);
    }

    @PatchMapping("/set-poll-duration/{pollId}")
    public ResponseEntity<PollDto> setPollDuration(@PathVariable String pollId,
                                                   @RequestBody PollDurationDto pollDurationDto) {
        PollDto poll = pollService.setPollDuration(pollId, pollDurationDto);
        return ResponseEntity.ok().body(poll);
    }

    @PatchMapping("/toggle-anonymous/{pollId}")
    public ResponseEntity<PollDto> toggleAnonymousParameter(@PathVariable String pollId) {
        PollDto poll = pollService.toggleAnonymousParameter(pollId);
        return ResponseEntity.ok().body(poll);
    }

    @PatchMapping("/toggle-multiple-choice/{pollId}")
    public ResponseEntity<PollDto> toggleMultipleChoiceParameter(@PathVariable String pollId) {
        PollDto poll = pollService.toggleMultipleChoiceParameter(pollId);
        return ResponseEntity.ok().body(poll);
    }
}
