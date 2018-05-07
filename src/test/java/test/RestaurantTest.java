package test;

import actions.Dishes;
import actions.Login;
import actions.Registration;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.testng.annotations.Test;
import resp.DishAddSuccessResponse;
import resp.LoginSuccessResponse;
import resp.RegistrationSuccessResponse;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import static io.restassured.RestAssured.given;

/**
 * Created by hiteshs on 4/30/18.
 */
public class RestaurantTest {

    @Test
    public void canregister(){

        Registration registration= new Registration();
        Response response= registration.register("hitesh54","hitesh@123","hitesh54@gmail.com");
        RegistrationSuccessResponse registrationresponse=response.as(RegistrationSuccessResponse.class);
        Assert.assertEquals( HttpStatus.SC_OK,response.getStatusCode());
        Assert.assertEquals("hitesh54@gmail.com",registrationresponse.getEmail());
        Assert.assertEquals( "hitesh54",registrationresponse.getUsername());

    }

    @Test
    public void canRegisterContractTest(){

        Registration registration= new Registration();
        registration.register("hitesh191","hitesh@123","hitesh191@gmail.com")
                .then().assertThat().body(matchesJsonSchemaInClasspath("registration-schema.json"));

    }

    @Test
    public void canLogin(){

        Registration registration= new Registration();
        Response response= registration.register("hitesh54","hitesh@123","hitesh54@gmail.com");
        Assert.assertEquals( HttpStatus.SC_OK,response.getStatusCode());

        Login login= new Login();
        Response responseLogin=login.login("hitesh51","hitesh@123");
        Assert.assertEquals( HttpStatus.SC_OK,responseLogin.getStatusCode());
        LoginSuccessResponse loginresponse=responseLogin.as(LoginSuccessResponse.class);
        Assert.assertNotNull(loginresponse.getId());

    }

    @Test
    public void canAddDishAfterLogin(){

        Registration registration= new Registration();
        Response response= registration.register("hitesh58","hitesh@123","hitesh58@gmail.com");
        Assert.assertEquals( HttpStatus.SC_OK,response.getStatusCode());

        Login login= new Login();
        Response responseLogin=login.login("hitesh58","hitesh@123");
        Assert.assertEquals( HttpStatus.SC_OK,responseLogin.getStatusCode());
        LoginSuccessResponse loginresponse=responseLogin.as(LoginSuccessResponse.class);
        String accesstoken=loginresponse.getId();

        Dishes dishes= new Dishes();
        Response dishResponse=dishes.addDish("burger",400,accesstoken);
        DishAddSuccessResponse dishsuccessresponse=dishResponse.as(DishAddSuccessResponse.class);
        Assert.assertEquals( HttpStatus.SC_OK,dishResponse.getStatusCode());
        Assert.assertNotNull(dishsuccessresponse.getId());
        Assert.assertEquals( "burger",dishsuccessresponse.getName());
        Assert. assertEquals( 400,dishsuccessresponse.getPrice(),0);

    }
}
