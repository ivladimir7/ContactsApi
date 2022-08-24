package tests;

import com.jayway.restassured.RestAssured;
import dto.ContactDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.urlEncodingEnabled;

public class DeleteContactByRestAssuredTests {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3Q3NzdAZ21haWwuY29tIn0.pw-WUJWCZCCjyGrWi_poNK8RaTOmyp0a4J6QA_-NHx4";

int id;
    @BeforeMethod
    public void ensurePrecondition() {
        System.err.close();
        System.setErr(System.out);
        RestAssured.baseURI = "https://contacts-telran.herokuapp.com";
        RestAssured.basePath = "api";
        int i = (int) (System.currentTimeMillis() / 1000 % 3600);

        ContactDto contactDto = ContactDto.builder()
                .address("Stuttgart")
                .description("IT")
                .email("ya" + i + "@yahoo.com")
                .lastName("Soyer")
                .name("Tom")
                .phone("+7777775" + i)
                .build();

        id = given().header("Authorization", token)
                .contentType("application/json")
                .body(contactDto)
                .post("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().path("id");
        System.out.println(id);
        
    }
    @Test
    public void deleteByIdTest() {
        String status = RestAssured.given().header("Authorization", token)
                .delete("contact/" + id)
                .then()
                .assertThat().statusCode(200)
                .extract().path("status");
        System.out.println(status);
    }
}