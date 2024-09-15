package org.xm.helpers;

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
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri("https://swapi.dev/api/").
                build();
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
                        .contentType("application/json")
                //.spec(requestSpec)
                .when()
                .get("https://swapi.dev/api/films");
        if (response.getStatusCode() == 200) {
            films = parseResponseToList(response, Film.class);
        }
        return films;
    }

    public static Character getCharacter(String characterId) {
        Character character = new Character();
        Response response = given()
                .contentType("application/json")
                //.spec(requestSpec)
                .when()
                .get("https://swapi.dev/api/people/" + characterId);
        if (response.getStatusCode() == 200) {
             character = parseResponseToObject(response, Character.class);
        }
        return character;
    }

    public static Page getCharacters(String pageNumber) {
        Page page = new Page();
        Response response = given()
                .contentType("application/json")
                //.spec(requestSpec)
                .when()
                .queryParams("page", pageNumber)
                .get("https://swapi.dev/api/people");
        if (response.getStatusCode() == 200) {
            page = parseResponseToObject(response, Page.class);
        }
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
}
