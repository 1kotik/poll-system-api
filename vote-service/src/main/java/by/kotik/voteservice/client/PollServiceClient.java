package by.kotik.voteservice.client;

import dto.PollDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient("poll-service")
public interface PollServiceClient {
    @GetMapping("/polls/get-poll/{pollId}")
    ResponseEntity<PollDto> getPollDto(@PathVariable UUID pollId);
}
