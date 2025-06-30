package by.kotik.analyticsservice.controller;

import by.kotik.analyticsservice.dto.PollResultDto;
import by.kotik.analyticsservice.service.PollResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/polls")
@RequiredArgsConstructor
public class PollResultController {
    private final PollResultService pollResultService;

    @PostMapping("/{pollId}/results")
    public ResponseEntity<PollResultDto> getPollResult(@PathVariable UUID pollId,
                                        @RequestParam(required = false)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startDate,
                                        @RequestParam(required = false)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endDate) {
        PollResultDto pollResultDto = pollResultService.getPollResult(pollId, startDate, endDate);
        return ResponseEntity.ok(pollResultDto);
    }
}
