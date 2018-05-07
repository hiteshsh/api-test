package actions;

import io.restassured.response.Response;
import net.minidev.json.JSONObject;

import static io.restassured.RestAssured.given;

/**
 * Created by hiteshs on 5/7/18.
 */
public class Dishes {

    public Response addDish(String name,float price,String accesstoken){
        JSONObject requestParam = new JSONObject();
        requestParam.put("name", name);
        requestParam.put("price", price);

        return given()
                .baseUri("http://localhost:3000/api")
                .contentType("application/json")
                .body(requestParam)
                .queryParam("access_token",accesstoken)
                .when()
                .post("/dishes");
    }
}
