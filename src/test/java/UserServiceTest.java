import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserServiceTest {

    @Test
    public void testGetUserLogin(){
        baseURI = "http://localhost:8080/api/v3";

        Map<String, Object> request = new HashMap<>();

        request.put("username","user");
        request.put("password","pass");

        given()
            .pathParams(request)
        .when()
            .get("/user/login?username={username}&password={password}")
        .then()
            .statusCode(200);
    }

    @Test
    public void testGetUserLogout(){
        baseURI = "http://localhost:8080/api/v3";

        given()
        .when()
            .get("/user/logout")
        .then()
            .statusCode(200);
    }

    @Test
    public void testPutUserByUserName(){
        baseURI = "http://localhost:8080/api/v3";

        given()
            .pathParam("username","theUser").log().all()
        .when()
            .put("/user/{username}")
        .then()
            .statusCode(400).log().all();
                //.body("firstName",equalTo("first name 1"));
    }

    @Test
    public void testDeleteUserByUserName(){
        baseURI = "http://localhost:8080/api/v3";

        given()
            .pathParam("username","theUser")
        .when()
            .put("/user/{username}")
        .then()
            .statusCode(400).log().all();
        //.body("firstName",equalTo("first name 1"));
    }
}
