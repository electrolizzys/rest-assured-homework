package ge.tbc.testautomation.data.models.responses.swapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PlanetsResponse(
        String message,
        @JsonProperty("total_records") int totalRecords,
        @JsonProperty("total_pages") int totalPages,
        String previous,
        String next,
        List<PlanetResult> results,
        String apiVersion,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
        LocalDateTime timestamp
) {
}
