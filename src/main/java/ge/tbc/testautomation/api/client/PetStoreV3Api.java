package ge.tbc.testautomation.api.client;

import ge.tbc.testautomation.data.models.requests.petstore.StoreOrderRequest;
import io.restassured.response.Response;

import static ge.tbc.testautomation.data.ApiConstants.PetStoreV3.ORDER;
import static io.restassured.RestAssured.given;

public class PetStoreV3Api extends BaseApi {

    public Response createOrder(StoreOrderRequest order) {
        return given()
                .spec(PETSTORE_V3_SPEC).body(order).when().post(ORDER);
    }
}
