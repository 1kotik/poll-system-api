package by.kotik.analyticsservice.controller;

import by.kotik.analyticsservice.service.CSVService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
public class CSVController {
    private final CSVService csvService;

    @PostMapping("/{pollId}/csv")
    public ResponseEntity<String> getPollResultInCSV(@PathVariable UUID pollId,
                                                @RequestParam(required = false)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                ZonedDateTime startDate,
                                                @RequestParam(required = false)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                ZonedDateTime endDate) {
        String content = csvService.writePollResults(pollId, startDate, endDate);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=poll_result_" + pollId + ".csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(content);
    }
}
