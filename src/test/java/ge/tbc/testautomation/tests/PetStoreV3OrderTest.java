package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.models.requests.petstore.StoreOrderRequest;
import ge.tbc.testautomation.steps.PetStoreOrderSteps;
import org.testng.annotations.Test;

import java.time.OffsetDateTime;

import static ge.tbc.testautomation.data.ApiConstants.PetStoreV3.PLACED_STATUS;

public class PetStoreV3OrderTest {

    private final PetStoreOrderSteps petStoreOrderSteps = new PetStoreOrderSteps();

    @Test
    public void createStoreOrderWithFluentPojo() {
        StoreOrderRequest order = new StoreOrderRequest()
                .status(PLACED_STATUS).complete(true).id(10L).petId(198772L)
                .quantity(2)
                .shipDate(OffsetDateTime.parse("2026-09-12T00:00:00Z"));
        petStoreOrderSteps.createOrder(order);
    }
}
