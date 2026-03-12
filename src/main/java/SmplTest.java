import io.restassured.http.Method;
import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class SmplTest  {
        public static void main(String[] args) throws IOException
        {

            given().
                    headers("","","","","","").
            when()
                    .request(Method.GET, "")
                    .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body("data.id[1]",equalTo("1"))
                    .body("data.firstName", hasItem("john"))
//                     body("data.id[1]",equalTo(8)).

                    .extract()
                    .response();



        }


}









