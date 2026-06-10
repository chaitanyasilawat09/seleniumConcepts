package restAssured.java.base;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import restAssured.utils.ApplicationProperties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BaseTest {

    public ApplicationProperties applicationProperties = ApplicationProperties.INSTANCE;
    //TODO In REST Assured, RequestSpecification is used to define reusable request settings —
    // like base URI, headers, authentication, query params, body, etc.
    public RequestSpecification requestSpecification ;
    @BeforeMethod
    public void beforeTest(){

        RestAssured.baseURI = applicationProperties.getBaseURL();
        RestAssured.useRelaxedHTTPSValidation();
//        RequestSpecification requestSpecification = RestAssured.given();
        RequestSpecification requestSpecification = RestAssured.given()
                                         .contentType(ContentType.JSON);

    }
//TODO,
// a ResponseSpecification is used to define reusable response expectations —
// such as status codes, headers, response time, content type, and body structure.
    public ResponseSpecification responseSpec;

    @BeforeClass
    public void setupResponseSpec() {
        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json")
                .expectHeader("Server", "nginx")
                .expectBody("status", equalTo("success"))
                .build();
    }

    public void verifyStatusCode(Response response, int statusCode){
        assertThat(response.getStatusCode(), is(statusCode));

    }

}
