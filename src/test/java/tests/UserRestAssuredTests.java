package tests;

import com.jayway.restassured.RestAssured;
import ilcarroDto.RegRequestDto;
import ilcarroDto.UserCreationDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class UserRestAssuredTests {

    String token = "Basic a3VrdWt1a3VAZ21haWwuY29tOjEyMzQ1QWExMjM0NWE="; //already generated token

    @BeforeMethod
    public void ensurePrecondition() {
        RestAssured.baseURI = "https://java-3-ilcarro-team-b.herokuapp.com";

    }

    @Test
    public void registrationPositiveTest() {
        RegRequestDto requestDto = RegRequestDto.builder()
                .first_name("Anakin")
                .second_name("Skywalker")
                .build();

        String firstName = given()
                .header("Authorization", token)
                .contentType("application/json")
                .body(requestDto)
                .post("registration")
                .then()
                .assertThat().statusCode(200)
                .extract().path("first_name");
        System.out.println("registration success, First_name: " + firstName); //Assert Name User
        System.out.println("---------------------------------------------");

    }

    @Test
    public void loginPositiveTest() {
        Object firstName = given().header("Authorization", token)
                .get("user/login")
                .then()
                .assertThat().statusCode(200)
                .extract().path("first_name");
        System.out.println("Login Success, Name: " + "*" + firstName + "*");//assert Name
        System.out.println("----------------------------------------------");

    }

    @Test
    public void userUpdatePositiveTest() {

        UserCreationDto userDto = UserCreationDto.builder()
                .first_name("Dart")
                .second_name("Weider")
                .build();

        String second_name = given().header("Authorization", token)
                .contentType("application/json")
                .body(userDto)
                .put("user")
                .then()
                .assertThat().statusCode(200)
                .extract().path("second_name");//Assert Second_Name Update

        System.out.println("Update Success, Name: " + "*" + second_name + "*");
        System.out.println("----------------------------------------------------");
    }


    @Test
    public void userDeletePositiveTest() {
        String status = String.valueOf(given().header("Authorization", token)
                .delete("user")
                .then()
                .assertThat().statusCode(200)
                .extract().statusCode());

        System.out.println("User was deleted: " + "StatusCode" + status);
        System.out.println("-----------------------------------------");
    }
}
