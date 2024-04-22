package restAssured.java.starWars_API;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.AssertJUnit.assertEquals;

public class RestAssured_Concepts {


    public void restAssuredCOncetp(){


        Response response = RestAssured.get("/users/eugenp");
        long timeInMS = response.time();
        long timeInS = response.timeIn(TimeUnit.SECONDS);

        assertEquals(timeInS, timeInMS/1000);

//        TODO time() is used to get response time in milliseconds
//             timeIn() is used to get response time in the specified time unit

//     Validate Response Time
        given().when().get("/users/eugenp").then().time(lessThan(5000L));


//        TODO  return all logs
        given().log().all()
                .when().get("/users/eugenp")
                .then().statusCode(200);


//        TODO  Return body log
        given().when().get("/users/eugenp").then().log().body().statusCode(200);



        given().when().get("/users/eugenp")
                .then().log().ifError();
        given().when().get("/users/eugenp")
                .then().log().ifStatusCodeIsEqualTo(500);
        given().when().get("/users/eugenp")
                .then().log().ifStatusCodeMatches(greaterThan(200));


//        TODO  In this example, we want to validate that the status code is 200. Only if this fails,
//         the request and response will be logged
        given().when().get("/users/eugenp")
                .then().log().ifValidationFails().statusCode(200);

        given().given().log().ifValidationFails()
                .when().get("/users/eugenp")
                .then().statusCode(200);




    }
}
