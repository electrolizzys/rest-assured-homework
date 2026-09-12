package ge.tbc.testautomation.data.models.responses.swapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanetResult {
    private String uid;
    private String name;
    private String url;
}
