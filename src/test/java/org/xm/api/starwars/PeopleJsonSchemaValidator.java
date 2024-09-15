package org.xm.api.starwars;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PeopleJsonSchemaValidator {
    @Test
    public void validatePeopleResponseAgainstJsonSchema() {
        given()
                .contentType("application/json")
                .when()
                .queryParams("page", 2)
                .get("https://swapi.dev/api/people")
                .then()
                .assertThat().body(matchesJsonSchemaInClasspath("people-json-schema.json"));
    }
}
