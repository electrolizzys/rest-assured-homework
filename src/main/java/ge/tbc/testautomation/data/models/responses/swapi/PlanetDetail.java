package ge.tbc.testautomation.data.models.responses.swapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetDetail {
    private PlanetProperties properties;
    private String description;
    private String uid;
}
