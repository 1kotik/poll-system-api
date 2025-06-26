package by.kotik.voteservice.controller;

import by.kotik.voteservice.service.VoteService;
import dto.VoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/votes")
public class VoteController {
    private final VoteService voteService;

    @GetMapping("/get/{pollId}")
    public ResponseEntity<List<VoteDto>> getVotesByPollId(@PathVariable UUID pollId,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startDate,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endDate) {
        return ResponseEntity.ok(voteService.getByPollId(pollId, startDate, endDate));
    }

    @PostMapping("/vote/{pollId}")
    public ResponseEntity<List<VoteDto>> vote(@PathVariable UUID pollId,
                                              @RequestHeader("Authorization") String authHeader,
                                              @RequestParam(name = "option") List<UUID> selectedOptions) {
        return ResponseEntity.ok(voteService.vote(pollId, authHeader, selectedOptions));
    }
}
