package restAssured;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.json.JSONObject;
import restAssured.POM.People;

import java.util.ArrayList;

public class Serialization_Deserialization {

//            Serialization = Java Object ➡️ JSON
//            Deserialization = JSON ➡️ Java Object


    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Response response = null;
        JSONObject jsonObject = new JSONObject(response.asString());

//       TODO Serialization = Java Object ➡️ JSON
        People people = new People(1,"Name1","Name2",new ArrayList<>());
        mapper.writeValueAsString(people);

//      TODO  Deserialization = JSON ➡️ Java Object
        People p1 =  mapper.readValue(jsonObject.toString(),People.class);
    }
}
