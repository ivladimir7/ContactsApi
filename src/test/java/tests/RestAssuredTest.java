package tests;

import com.jayway.restassured.RestAssured;
import dto.AuthRequestDto;
import dto.ContactDto;
import dto.GetAllContactsDto;
import dto.LoginRegResponseDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

//5818

public class RestAssuredTest {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3Q3NzdAZ21haWwuY29tIn0.pw-WUJWCZCCjyGrWi_poNK8RaTOmyp0a4J6QA_-NHx4";
    @BeforeMethod

    public void ensurePrecondition() {
        System.err.close();
        System.setErr(System.out);
        RestAssured.baseURI = "https://contacts-telran.herokuapp.com";
        RestAssured.basePath = "api";
    }

    @Test
    public void loginPositiveTest() {
        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("test777@gmail.com")
                .password("Test700$Test55")
                .build();

        LoginRegResponseDto responseDto = given()
                .contentType("application/json")
                .body(requestDto)
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(LoginRegResponseDto.class);

        System.out.println(responseDto.getToken());

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3Q3NzdAZ21haWwuY29tIn0.pw-WUJWCZCCjyGrWi_poNK8RaTOmyp0a4J6QA_-NHx4";
        String token2 = String.valueOf(given().contentType("application/json")
                .body(requestDto)
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .body(containsString("token"))
                .body("token", equalTo(token)));
        System.out.println(token2);
    }
@Test
    public void loginNegativeTest() {
        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("test777@gmail.com")
                .password("1234567")
                .build();

        String message = given()
                .contentType("application/json")
                .body(requestDto)
                .post("login")
                .then()
                .assertThat().statusCode(400)
                .extract().path("message");

        System.out.println(message);
    }
    @Test
    public  void addNewContactPositiveTest() {

        int i = (int) (System.currentTimeMillis()/1000%3600);

        ContactDto contactDto =ContactDto.builder()
                .address("Stuttgart")
                .description("IT")
                .email("ya" + i + "@yahoo.com")
                .lastName("Soyer")
                .name("Tom")
                .phone("+7777775" + i)
                .build();

        int id = given().header("Authorization", token)
                .contentType("application/json")
                .body(contactDto)
                .post("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().path("id");
        System.out.println(id);
    }
    @Test
    public void AddContactTest () {
        GetAllContactsDto responseDto = given()
                .header("Authorization", token)
                .get("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().body().as(GetAllContactsDto.class);

        for (ContactDto contactDto : responseDto.getContacts()) {
            System.out.println(contactDto.getId() + "***" + contactDto.getLastName() + "***");
            System.out.println("=======================================");
        }
    }
    @Test
    public void deleteContactTest () {
        String status = RestAssured.given().header("Authorization", token)
                .delete("contact/5818")
                .then()
                .assertThat().statusCode(200)
                .extract().path("status");
        System.out.println(status);

    }
}
