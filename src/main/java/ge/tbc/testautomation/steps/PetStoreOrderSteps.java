package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.api.client.PetStoreV3Api;
import ge.tbc.testautomation.data.models.requests.petstore.StoreOrderRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PetStoreOrderSteps {

    private final PetStoreV3Api petStoreV3Api = new PetStoreV3Api();
    private StoreOrderRequest expectedOrder;

    @Step("Create store order with fluent POJO")
    public PetStoreOrderSteps createOrder(StoreOrderRequest order) {
        expectedOrder = order;
        Response response = petStoreV3Api.createOrder(order);
        response.then().statusCode(200);
        StoreOrderRequest actual = response.as(StoreOrderRequest.class);
        validateOrder(actual);
        return this;
    }

    private void validateOrder(StoreOrderRequest actual) {
        assertThat(actual.id(), equalTo(expectedOrder.id()));
        assertThat(actual.petId(), equalTo(expectedOrder.petId()));
        assertThat(actual.quantity(), equalTo(expectedOrder.quantity()));
        assertThat(actual.status(), equalTo(expectedOrder.status()));
        assertThat(actual.complete(), equalTo(expectedOrder.complete()));
        assertThat(actual.shipDate(), notNullValue());
        assertThat(actual.shipDate().toInstant(), equalTo(expectedOrder.shipDate().toInstant()));
    }
}
