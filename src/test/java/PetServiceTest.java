import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PetServiceTest {

    @Test
    public void testGetFindByStatus(){
        baseURI = "http://localhost:8080/api/v3";

        given()
            .pathParam("status", "available")
        .when()
            .get("/pet/findByStatus?status={status}")
        .then()
            .statusCode(200)
            .body("status",hasItem("available"));
    }

    @Test
    public void testGetFindByStatusNoAllowedValue(){
        baseURI = "http://localhost:8080/api/v3";

        given()
            .pathParam("status", "new").log().all()
        .when()
            .get("/pet/findByStatus?status={status}")
        .then()
            .statusCode(400).log().all()
            .body("message",containsString("Input error"));
    }

    @Test
    public void testGetFindByTags(){
        baseURI = "http://localhost:8080/api/v3";

        given()
            .pathParam("tags", "tag1")
        .when()
            .get("/pet/findByTags?tags={tags}")
        .then()
            .statusCode(200)
            .body("[0].tags[0].name",equalTo("tag1"));
    }

    @Test
    public void testGetFindByTagsMultipleTags(){
        baseURI = "http://localhost:8080/api/v3";

        Map<String, Object> request = new HashMap<>();

        request.put("tags1","tag1");
        request.put("tags2","tag3");

        given()
            .pathParams(request)
        .when()
            .get("/pet/findByTags?tags={tags1}&tags={tags2}")
        .then()
            .statusCode(200)
            .body("[0].tags[0].name",equalTo("tag1"));
    }

    @Test
    public void testGetFindByTagsNoValidTag(){
        baseURI = "http://localhost:8080/api/v3";

        given()
            .pathParam("tags", "NoTag")
        .when()
            .get("/pet/findByTags?tags={tags}")
        .then()
            .statusCode(400)
            .body("[0].tags[0].name",equalTo("tag1"));
    }

    @Test
    public void testGetPetByID(){
        baseURI = "http://localhost:8080/api/v3";

        given()
            .pathParam("petID", "1")
        .when()
            .get("/pet/{petID}")
        .then()
            .statusCode(200)
            .body("name",equalTo("Cat 1"));
    }

    @Test
    public void testGetPetByIDNoPetFound(){
        baseURI = "http://localhost:8080/api/v3";

        given()
            .pathParam("petID", "100")
        .when()
            .get("/pet/{petID}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testGetPetByIDNoValidID(){
        baseURI = "http://localhost:8080/api/v3";

        given()
                .pathParam("petID", "10000")
                .when()
                .get("/pet/{petID}")
                .then()
                .statusCode(404).log().all();
    }

    @Test
    public void testPostUpdateStatusPet(){
        baseURI = "http://localhost:8080/api/v3";

        Map<String, Object> request = new HashMap<>();

        request.put("petId","1");
        request.put("name","Cat 1");
        request.put("status","sold");

        given()
            .pathParams(request)
        .when()
            .post("/pet/{petId}?name={name}&status={status}")
        .then()
            .statusCode(200)
            .body("status",equalTo("sold"));
    }

    @Test
    public void testPostUpdateStatusPetNoValidID(){
        baseURI = "http://localhost:8080/api/v3";

        Map<String, Object> request = new HashMap<>();

        request.put("petId","10000");
        request.put("name","Cat 1");
        request.put("status","sold");

        given()
            .pathParams(request)
        .when()
            .post("/pet/{petId}?name={name}&status={status}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testDeletePet(){
        baseURI = "http://localhost:8080/api/v3";

        given()
            .header("api_key","1234")
            .pathParam("petId","3")
        .when()
            .delete("/pet/{petId}")
        .then()
            .statusCode(200);
    }

    @Test
    public void testDeletePetNoValidID(){
        baseURI = "http://localhost:8080/api/v3";

        given()
            .header("api_key","1234")
            .pathParam("petId","ASD")
        .when()
            .delete("/pet/{petId}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testPostUploadImage(){

        File upload = new File("src/test/resources/cat.png");

        baseURI = "http://localhost:8080/api/v3";

        given()
            .pathParam("petId","1")
            .queryParam("additionalMetadata", "NewImage")
            .multiPart("image",upload, "image/png").log().all()
        .when()
            .post("/pet/{petId}/uploadImage")
        .then()
            .statusCode(415).log().all();
    }
}
