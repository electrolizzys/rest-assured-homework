package ge.tbc.testautomation.data.models.requests.petstore;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true, fluent = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "petId", "quantity", "shipDate", "status", "complete"})
public class StoreOrderRequest {

    @JsonProperty("status")
    private String status;

    @JsonProperty("complete")
    private Boolean complete;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("petId")
    private Long petId;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("shipDate")
    private OffsetDateTime shipDate;
}
