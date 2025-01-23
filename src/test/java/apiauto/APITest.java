package apiauto;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class APITest {

    String LatJakartaSelatan = "-6.300641";
    String LongJakartaSelatan = "106.814095";

    @Test
    public void testPredictWeather() {
        PredictWeather(LatJakartaSelatan, LongJakartaSelatan);
    }

    @Test
    public void testAirPolution() {
        AirPolution(LatJakartaSelatan, LongJakartaSelatan);
    }

    public void PredictWeather(String latitude, String longitude) {
        RestAssured.baseURI = "https://api.openweathermap.org/data/2.5/weather";
        String APP_ID = "6f58657977ff9426c92b7b575f573326";

        File jsonWeather = new File("src/test/resources/jsonschemavalidator/validatorcurrentweather.json");

        Response response = given()
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("appid", APP_ID)
                .when()
                .get(baseURI)
                .then()
                .assertThat().body(matchesJsonSchema(jsonWeather)) // json schema validator
                .extract().response();

        System.out.println("Response Body: " + response.getBody().prettyPrint());
    }

    public void AirPolution(String latitude, String longitude) {
        RestAssured.baseURI = "https://api.openweathermap.org/data/2.5/air_pollution";
        File jsonschemaPolution = new File("src/test/resources/jsonschemavalidator/validatorcurrentpolution.json");

        Response response = given()
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("appid", "6f58657977ff9426c92b7b575f573326")
                .when()
                .get(baseURI)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body(matchesJsonSchema(jsonschemaPolution))
                .extract().response();

        System.out.println(response.getBody().prettyPrint());
    }
}