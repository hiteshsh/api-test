package actions;

import io.restassured.response.Response;
import net.minidev.json.JSONObject;

import static io.restassured.RestAssured.given;

/**
 * Created by hiteshs on 5/1/18.
 */
public class Registration {

    public Response register(String userName, String password, String emailId){
        JSONObject requestParam= new JSONObject();
        requestParam.put("username",userName);
        requestParam.put("email",emailId);
        requestParam.put("password",password);

        return given()
                .baseUri("http://localhost:3000/api")
                .contentType("application/json")
                .body(requestParam)
                .when()
                .post("/Users");

    }
}
