package by.kotik.analyticsservice.client;

import dto.PollDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient("poll-service")
public interface PollServiceClient {
    @GetMapping("/polls/{pollId}")
    PollDto getPoll(@PathVariable("pollId") UUID pollId);
}
