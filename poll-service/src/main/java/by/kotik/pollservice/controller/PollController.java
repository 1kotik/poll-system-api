package by.kotik.pollservice.controller;

import by.kotik.pollservice.dto.PollDurationDto;
import by.kotik.pollservice.dto.UpdatePollDto;
import by.kotik.pollservice.service.PollService;
import dto.PollDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/polls")
@RequiredArgsConstructor
public class PollController {
    private final PollService pollService;

    @PostMapping("/create")
    public ResponseEntity<PollDto> createPoll(@RequestBody PollDto pollDto,
                                              @RequestHeader("Authorization") String authHeader) {
        PollDto poll = pollService.createPoll(pollDto, authHeader);
        return ResponseEntity.ok().body(poll);
    }

    @PutMapping("/update/{pollId}")
    public ResponseEntity<PollDto> updatePoll(@PathVariable UUID pollId, @RequestBody UpdatePollDto pollDto,
                                              @RequestHeader("Authorization") String authHeader) {
        PollDto poll = pollService.updatePoll(pollId, pollDto, authHeader);
        return ResponseEntity.ok().body(poll);
    }

    @GetMapping("/get/{pollId}")
    public ResponseEntity<PollDto> getPollById(@PathVariable UUID pollId) {
        PollDto poll = pollService.getPollById(pollId);
        return ResponseEntity.ok().body(poll);
    }

    @DeleteMapping("/delete/{pollId}")
    public ResponseEntity<PollDto> deletePoll(@PathVariable UUID pollId,
                                              @RequestHeader("Authorization") String authHeader) {
        PollDto poll = pollService.deletePoll(pollId, authHeader);
        return ResponseEntity.ok().body(poll);
    }

    @PatchMapping("/set-duration/{pollId}")
    public ResponseEntity<PollDto> setPollDuration(@PathVariable UUID pollId,
                                                   @RequestBody PollDurationDto pollDurationDto,
                                                   @RequestHeader("Authorization") String authHeader) {
        PollDto poll = pollService.setPollDuration(pollId, pollDurationDto, authHeader);
        return ResponseEntity.ok().body(poll);
    }

    @PatchMapping("/toggle-anonymous/{pollId}")
    public ResponseEntity<PollDto> toggleAnonymousParameter(@PathVariable UUID pollId,
                                                            @RequestHeader("Authorization") String authHeader) {
        PollDto poll = pollService.toggleAnonymousParameter(pollId, authHeader);
        return ResponseEntity.ok().body(poll);
    }

    @PatchMapping("/toggle-multiple-choice/{pollId}")
    public ResponseEntity<PollDto> toggleMultipleChoiceParameter(@PathVariable UUID pollId,
                                                                 @RequestHeader("Authorization") String authHeader) {
        PollDto poll = pollService.toggleMultipleChoiceParameter(pollId, authHeader);
        return ResponseEntity.ok().body(poll);
    }

    @PatchMapping("/end/{pollId}")
    public ResponseEntity<PollDto> endPoll(@PathVariable UUID pollId,
                                           @RequestHeader("Authorization") String authHeader) {
        PollDto poll = pollService.endPoll(pollId, authHeader);
        return ResponseEntity.ok().body(poll);
    }

    @PatchMapping("/start/{pollId}")
    public ResponseEntity<PollDto> startPoll(@PathVariable UUID pollId,
                                           @RequestHeader("Authorization") String authHeader) {
        PollDto poll = pollService.startPoll(pollId, authHeader);
        return ResponseEntity.ok().body(poll);
    }
}
