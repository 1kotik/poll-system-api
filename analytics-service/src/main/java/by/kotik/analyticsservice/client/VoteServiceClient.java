package by.kotik.analyticsservice.client;

import dto.VoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@FeignClient("vote-service")
public interface VoteServiceClient {
    @GetMapping("/polls/{pollId}/votes")
    List<VoteDto> getVotes(@PathVariable UUID pollId,
                           @RequestParam(required = false)
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startDate,
                           @RequestParam(required = false)
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endDate);
}
