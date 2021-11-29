package com.marionete.rest.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marionete.backends.AccountInfoMock;
import com.marionete.backends.UserInfoMock;
import com.marionete.rest.rest.RestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = RestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testUserInfoMock() throws Exception {
        String url = "http://localhost:8898/marionete/user/";
        String header = "Authorization";
        String payload = "{\"username\":\"regi\", \"password\":\"adf\"}";

        String expectedResponse = "{\"name\":\"John\", \"surname\":\"Doe\", \"sex\":\"male\", \"age\": 32}";
        JsonNode expectedResponseJson = convertToJsonNode(expectedResponse);

        UserInfoMock.start();

        for (int i=0; i<10; i++) {
            ResponseEntity<String> response = makeHeaderCall(url, header, "Token-"+i, payload);

            System.out.println("RESPONSE =====> "+response.getStatusCodeValue());

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                JsonNode receivedResponseJson = convertToJsonNode(response.getBody());
                assertEquals(expectedResponseJson.asText(), receivedResponseJson.asText());
            }
        }
    }

    @Test
    void testAccountInfoMock() throws Exception {
        String url = "http://localhost:8899/marionete/account/";
        String header = "Authorization";
        String payload = "{\"username\":\"regi\", \"password\":\"adf\"}";

        String expectedResponse = "{\"accountNumber\":\"12345-3346-3335-4456\"}";
        JsonNode expectedResponseJson = convertToJsonNode(expectedResponse);

        AccountInfoMock.start();

        for (int i=0; i<10; i++) {
            ResponseEntity<String> response = makeHeaderCall(url, header, "Token-"+i, payload);

            System.out.println("RESPONSE =====> "+response.getStatusCodeValue());

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                JsonNode receivedResponseJson = convertToJsonNode(response.getBody());
                assertEquals(expectedResponseJson.asText(), receivedResponseJson.asText());
            }
        }
    }

    // The following commented integrated tests can be uncommented and run, if the Spring Boot Application is running.
    /*
    @Test
    void successCallTest() throws Exception {
        String url = "http://localhost:8080//marionete/useraccount/";
        String payload = "{\"username\":\"regi\", \"password\":\"adf\"}";

        ResponseEntity<String> response = makeCall(url, payload);
        JsonNode receivedResponseJson = convertToJsonNode(response.getBody());

        String expectedResponse = "{\"accountInfo\": {\"accountNumber\": \"45321-6433-5333-6544\"}, \"userInfo\": {\"name\": \"Regina\", \"surname\": \"Cassandra\", \"sex\": \"female\", \"age\": 28}}";
        JsonNode expectedResponseJson = convertToJsonNode(expectedResponse);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(expectedResponseJson.asText(), receivedResponseJson.asText());
    }

    @Test
    void invalidUserNameTest() throws Exception {
        String url = "http://localhost:8080//marionete/useraccount/";
        String payload = "{\"username\":\"abcd\", \"password\":\"abc\"}";

        ResponseEntity<String> response = makeCall(url, payload);
        String expectedResponse = "Invalid Username";

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void invalidRequestNoUserNameTest() throws Exception {
        String url = "http://localhost:8080//marionete/useraccount/";
        String payload = "{\"password\":\"abc\"}";

        ResponseEntity<String> response = makeCall(url, payload);
        String expectedResponse = "Invalid Request";

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void invalidRequestNoPasswordTest() throws Exception {
        String url = "http://localhost:8080//marionete/useraccount/";
        String payload = "{\"username\":\"abc\"}";

        ResponseEntity<String> response = makeCall(url, payload);
        String expectedResponse = "Invalid Request";

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void wrongJsonRequestTest() throws Exception {
        String url = "http://localhost:8080//marionete/useraccount/";
        String payload = "{\"abcd\":\"1234\", \"cdef\":\"5678\"}";

        ResponseEntity<String> response = makeCall(url, payload);
        String expectedResponse = "Invalid Request";

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void emptyJsonRequestTest() throws Exception {
        String url = "http://localhost:8080//marionete/useraccount/";
        String payload = "{\"\":\"\", \"\":\"\"}";

        ResponseEntity<String> response = makeCall(url, payload);
        String expectedResponse = "Invalid Request";

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }
*/

    private ResponseEntity<String> makeHeaderCall(String url, String header, String value, String payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(header, value);
        JsonNode body = convertToJsonNode(payload);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<JsonNode> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response;
    }

    private ResponseEntity<String> makeCall(String url, String payload) {
        JsonNode body = convertToJsonNode(payload);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<JsonNode> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response;
    }

    private JsonNode convertToJsonNode(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            return node;
        } catch (Exception e) {
            return null;
        }
    }

}
