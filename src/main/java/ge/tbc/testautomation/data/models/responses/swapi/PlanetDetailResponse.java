package ge.tbc.testautomation.data.models.responses.swapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PlanetDetailResponse(
        String message,
        PlanetDetail result,
        String apiVersion,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
        LocalDateTime timestamp
) {
}
