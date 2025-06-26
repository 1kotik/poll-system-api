package by.kotik.analyticsservice.service;

import by.kotik.analyticsservice.dto.PollResultDto;
import by.kotik.analyticsservice.exception.AnalyticsException;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CSVService {
    private final PollResultService pollResultService;
    private static final String[] CSVHeader = {
            "Poll ID",
            "Total Votes",
            "Calculated At",
            "Option ID",
            "Option Text",
            "Votes",
            "Percentage"
    };

    public String writePollResults(UUID pollId, ZonedDateTime startDate,
                                   ZonedDateTime endDate) {
        PollResultDto pollResultDto = pollResultService.getPollResult(pollId, startDate, endDate);
        try (StringWriter stringWriter = new StringWriter();
             CSVWriter csvWriter = new CSVWriter(stringWriter)) {
            csvWriter.writeNext(CSVHeader);
            pollResultDto.getOptions()
                    .forEach(option -> csvWriter.writeNext(new String[]{
                            pollResultDto.getPollId().toString(),
                            String.valueOf(pollResultDto.getTotalVotes()),
                            String.valueOf(pollResultDto.getCalculatedAt()),
                            option.getOptionId().toString(),
                            option.getText(),
                            String.valueOf(option.getVotes()),
                            String.valueOf(option.getPercentage())
                    }));
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AnalyticsException("CSV Error.");
        }
    }
}
