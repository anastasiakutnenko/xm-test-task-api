package org.xm.helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.xm.models.Character;
import org.xm.models.Film;
import org.xm.models.Page;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class RestApiHelper {
    static RequestSpecification requestSpec;
    static ResponseSpecification responseSpec;

    static {
        createRequestSpecification();
        createResponseSpecification();
    }

    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://swapi.dev/api/")
                .setContentType(ContentType.JSON)
                .build();
    }

    public static void createResponseSpecification() {
        responseSpec = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                build();
    }

    public static List<Film> getFilms() {
        List<Film> films = new ArrayList<>();
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("films/")
                .then()
                .spec(responseSpec)
                .extract()
                .response();
        if (response.getStatusCode() == 200) {
            films = parseResponseToList(response, Film.class);
        }
        return films;
    }

    public static Character getCharacter(String characterId) {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("people/" + characterId)
                .then()
                .spec(responseSpec)
                .extract()
                .response();

        return parseResponseToObject(response, Character.class);
    }

    public static Page getCharacters(String pageNumber) {
        Response response = given()
                .spec(requestSpec)
                .when()
                .queryParams("page", pageNumber)
                .get("people")
                .then()
                .spec(responseSpec)
                .extract()
                .response();
        Page<Character> page = parseResponseToPage(response, new TypeReference<>() {});
        return page;
    }

    public static <T> List<T> parseResponseToList(Response response, Class<T> classType) {
        return response
                .then()
                .extract()
                .jsonPath()
                .getList("results", classType);
    }

    public static <T> T parseResponseToObject(Response response, Class<T> classType) {
        return response
                .then()
                .extract()
                .as(classType);
    }

    public static <T> T parseResponseToPage(Response response, TypeReference<T> typeReference) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(response.asString(), typeReference);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response", e);
        }
    }
}
