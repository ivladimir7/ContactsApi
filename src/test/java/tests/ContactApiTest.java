package tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dto.AuthRequestDto;
import dto.ErrorDto;
import dto.LoginRegResponseDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


//token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3Q3MDAwQGdtYWlsLmNvbSJ9.cL1DMhKJxtxvcVpMB4Vm-y1yyXk0ixsjIeRRn-ymy3I

public class ContactApiTest {

    @Test
    public void loginApiTest() throws IOException {
        String email = "test700@gmail.com";
        String password = "Test700$Test700";

        Response response = Request.Post("https://contacts-telran.herokuapp.com/api/login")
                .bodyString("{\n" +
                        "  \"email\": \"" + email + "\",\n" +
                        "  \"password\": \"" + password + "\"\n" +
                        "}", ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response);
        System.out.println("***************************************");
        String responseJson = response.returnContent().asString();
        System.out.println(responseJson);
        System.out.println("***************************************");
        JsonElement element = JsonParser.parseString(responseJson);
        JsonElement token = element.getAsJsonObject().get("token");
    }
    @Test
    public void loginHttpTest1() throws IOException {
        AuthRequestDto requestDto =AuthRequestDto.builder()
                .email("test7000@gmail.com")
                .password("Test700$Test700").build();

        Gson gson = new Gson();

        Response response= Request.Post("https://contacts-telran.herokuapp.com/api/login")
                .bodyString(gson.toJson(requestDto),ContentType.APPLICATION_JSON).execute();

        String responseJson = response.returnContent().asString();
        LoginRegResponseDto responseDto = gson.fromJson(responseJson,LoginRegResponseDto.class);
        System.out.println(responseDto);
    }
    @Test
    public void loginHttpNegativeTestWithInvalidPassword() throws IOException {
        AuthRequestDto requestDto =AuthRequestDto.builder()
                .email("test7000@gmail.com")
                .password("Test700Test700").build();

        Gson gson = new Gson();

        Response response= Request.Post("https://contacts-telran.herokuapp.com/api/login")
                .bodyString(gson.toJson(requestDto),ContentType.APPLICATION_JSON).execute();

        HttpResponse httpResponse = response.returnResponse();
        System.out.println(httpResponse.getStatusLine().getStatusCode());

        InputStream is = httpResponse.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) !=null) {
        sb.append(line);

        }
        ErrorDto error = gson.fromJson(sb.toString(),ErrorDto.class);
        System.out.println(error.getDetails());
        System.out.println(error.getMessage());
    }
}
