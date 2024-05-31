package restAssured.java.starWars_API;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import restAssured.POM.People;
import restAssured.POM.Results;
import restAssured.java.base.BaseTest;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PeopelTest extends BaseTest {


//    TODO verify result array count
    @Test
    public void verifyPeopleCount(){

        Response response = given().when().get("/").then().extract().response();

//        response.time();  to get response time

//        given().when().get("/").then().extract().as(People.class);
//        DataFileInJSON/people.json  response_Data
        System.out.println(response.asString());
        verifyStatusCode(response,200);
//      TODO convert response to JSON Array
        JSONObject array = new JSONObject(response.asString());

        JSONArray jsonArray = (JSONArray) array.get("results");
        assertThat(jsonArray.length(), is(10));

        System.out.println(response.jsonPath().getList("results"));;
    }

    @Test
    public void get_All_Names_From_Result_Array(){

        Response response = getPeopleData();
//      TODO get all Names available in Result array
        response.jsonPath().getList("results.name");
        response.jsonPath().get("next");
        System.out.println(response.jsonPath().getList("results.name"));

//        TODO verify Names contains a Particular name or not
        response.jsonPath().getList("results.name").contains("Luke121");
        response.jsonPath().getList("results.name").contains("Luke Skywalker");

//        TODO Verify Name using Stream API
        System.out.println(response.jsonPath().getList("results.name").stream().anyMatch(a -> a.equals("Luke Skywalker")));

    }


    @Test
    public void get_All_People_And_Create_JavaObject() throws IOException {

        // get response
        Response response = getPeopleData();
        //convert response to JSON Object
        JSONObject jsonObject = new JSONObject(response.asString());
        // create ObjectMapper object which is used to convert JSON to Java Object
        ObjectMapper mapper = new ObjectMapper();
        People people = mapper.readValue(jsonObject.toString(),People.class);

        System.out.println(people.toString());
        System.out.println(people.getResults().get(0).getName());
    }



    @Test
    public void hitURL_Available_In_Response(){
        // get response
        Response response = getPeopleData();
        // get Url from response
        String nextPage_URL = response.jsonPath().getString("results[0].homeworld");
        System.out.println(nextPage_URL);

        // keep old URL
        String old = baseURI;
        // Set BaseURI for rest Assured
       RestAssured.baseURI = nextPage_URL;
        System.out.println(given().get("/").then().extract().response().asString());
        given().get("/").then().extract().response().jsonPath().get("name");
        System.out.println(given().get().jsonPath().getString("name"));
        // Again set old URI as current URI
        RestAssured.baseURI = old;
        Response response1 = getPeopleData();
        System.out.println(response1.asString());

    }


//    Validate JSON Schema
    // If Response is large and not possible to validate each and every Value
@Test
public void validate_People_Json_Schema(){
    given().when().get("/").then().assertThat()
            .body(JsonSchemaValidator.matchesJsonSchema(new File("/Users/chaitanyasilawat/Documents/GitHub/RestAssured_API_Concepts/src/main/java/DataFileInJSON/people_Json_Schema.json")));
    }


    @Test
    public void validate_People_Name_Should_be_Unique(){
        Response response = getPeopleData();
//        Verify Response have Unique name in Result
        List<String> nameList = response.getBody().jsonPath().getList("results.name");
        System.out.println();
        HashSet<String> nameSet = (HashSet<String>) nameList.stream().collect(Collectors.toSet());

        assertThat("List contain Duplicate name for people: ",nameList.size(),is(nameSet.size()));
    }


    @Test
    public void filter_Male_N_Female_From_Response() throws JsonProcessingException {
        Response response = getPeopleData();

        JSONObject jsonObject = new JSONObject(response.asString());
        ObjectMapper  mapper = new ObjectMapper();
        People people = mapper.readValue(jsonObject.toString(),People.class);
        List<Results> resultsList = people.getResults();
        Map<String , String> genderMap = new HashMap<>();
        for (Results results : resultsList){

            if (results.getGender().equalsIgnoreCase("male")){
                genderMap.put(results.getName(),"male");
            }
            else {
                genderMap.put(results.getName(),"Female");
            }
        }
        System.out.println(genderMap);
        System.out.println(genderMap.size());

    }


    @Test
    public void getPeopleData_And_StoreIn_ExcelSheet() throws IOException {
//        Response response = getPeopleData();
//        JSONObject jsonObject = new JSONObject(response.asString());
//        ObjectMapper mapper = new ObjectMapper();
//        People people = mapper.readValue(jsonObject.toString(),People.class);

//        List<Results> resultsList = people.getResults();
//        File file = new File("Store_People_Data_From_API.xlsx");
//        FileInputStream fis = new FileInputStream(file);
//        FileOutputStream fos = new FileOutputStream(file);

        //Create an object of File class to open xlsx file
        File file =    new File("\\TestData\\TestData.xls");

        //Create an object of FileInputStream class to read excel file
        FileInputStream inputStream = new FileInputStream(file);

        //creating workbook instance that refers to .xls file
        HSSFWorkbook wb=new HSSFWorkbook(inputStream);

        //creating a Sheet object using the sheet Name
        HSSFSheet sheet=wb.getSheet("STUDENT_DATA");
//        HSSFSheet sheetwb.createSheet("STUDENT_DATA")

        //Create a row object to retrieve row at index 3
        HSSFRow row2=sheet.createRow(3);

        //create a cell object to enter value in it using cell Index
        row2.createCell(0).setCellValue("Diana");
        row2.createCell(1).setCellValue("Jane");
        row2.createCell(2).setCellValue("djanes@gmail.com");
        row2.createCell(3).setCellValue("Female");
        row2.createCell(4).setCellValue("8786858432");
        row2.createCell(5).setCellValue("Park Lane, Flat C1 , New Jersey");

        //write the data in excel using output stream
        FileOutputStream outputStream = new FileOutputStream("E:\\TestData\\TestData.xls");
        wb.write(outputStream);
        wb.close();





    }


    public Response getPeopleData(){
        Response response = given().when().get("/").then().extract().response();
       return response;
    }


    public void authorizationTypes(){


        given().auth().preemptive().basic("username","Password");
        given().header("authorization","Bearer token");
        given().auth().oauth2("access Token");
        given().auth().oauth("consumerKey", "consumerSecret", "accessToken", "tokenSecret");
    }
}
