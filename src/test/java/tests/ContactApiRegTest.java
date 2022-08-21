package tests;

import com.google.gson.Gson;

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


public class ContactApiRegTest {

    @Test
    public void RegistrationHttpTest1() throws IOException {
        AuthRequestDto requestDto =AuthRequestDto.builder()
                .email("test7000@gmail.com")
                .password("Test700$Test55").build();

        Gson gson = new Gson();

        Response response= Request.Post("https://contacts-telran.herokuapp.com/api/registration")
                .bodyString(gson.toJson(requestDto),ContentType.APPLICATION_JSON).execute();

        String responseJson = response.returnContent().asString();
        LoginRegResponseDto responseDto = gson.fromJson(responseJson,LoginRegResponseDto.class);
        System.out.println(responseDto);
    }
    @Test
    public void RegistrationHttpNegativeTestWithInvalidPassword() throws IOException {
        AuthRequestDto requestDto =AuthRequestDto.builder()
                .email("test7000@gmail.com")
                .password("12345678").build();

        Gson gson = new Gson();

        Response response= Request.Post("https://contacts-telran.herokuapp.com/api/registration")
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
