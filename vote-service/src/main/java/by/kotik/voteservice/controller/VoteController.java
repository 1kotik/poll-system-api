package by.kotik.voteservice.controller;

import by.kotik.voteservice.dto.VoteDto;
import by.kotik.voteservice.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/votes")
public class VoteController {
    private final VoteService voteService;

    @GetMapping("/get/{pollId}")
    public ResponseEntity<List<VoteDto>> getVotesByPollId(@PathVariable UUID pollId) {
        return ResponseEntity.ok(voteService.getByPollId(pollId));
    }

    @PostMapping("/vote/{pollId}")
    public ResponseEntity<List<VoteDto>> vote(@PathVariable UUID pollId,
                                              @RequestHeader("Authorization") String authHeader,
                                              @RequestParam(name = "option") List<UUID> selectedOptions) {
        return ResponseEntity.ok(voteService.vote(pollId, authHeader, selectedOptions));
    }
}
