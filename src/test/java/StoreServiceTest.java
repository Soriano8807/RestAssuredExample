import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class StoreServiceTest {

    @Test
    public void testGetInventory(){
        baseURI = "http://localhost:8080/api/v3";

        given()
        .when()
            .get("/store/inventory")
        .then()
            .statusCode(200);
    }

    @Test
    public void testPostOrder(){
        baseURI = "http://localhost:8080/api/v3";

        given()
                .when()
                .post("/store/order")
                .then()
                .statusCode(500).log().all();
    }

    @Test
    public void testDeleteOrder(){
        baseURI = "http://localhost:8080/api/v3";

        given()
            .pathParam("orderId","11")
        .when()
            .delete("/store/order/{orderId}")
        .then()
            .statusCode(200).log().all();
    }

    @Test
    public void testDeleteOrderNoValidID(){
        baseURI = "http://localhost:8080/api/v3";

        given()
            .pathParam("orderId","ASD")
        .when()
            .delete("/store/order/{orderId}")
        .then()
            .statusCode(400);
    }
}
