package restAssured.java.base;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import restAssured.utils.ApplicationProperties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BaseTest {

    public ApplicationProperties applicationProperties = ApplicationProperties.INSTANCE;
    public RequestSpecification requestSpecification ;
    @BeforeMethod
    public void beforeTest(){

        RestAssured.baseURI = applicationProperties.getBaseURL();
        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecification requestSpecification = RestAssured.given();
//        RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.JSON);

    }

    public void verifyStatusCode(Response response, int statusCode){
        assertThat(response.getStatusCode(), is(statusCode));

    }

}
