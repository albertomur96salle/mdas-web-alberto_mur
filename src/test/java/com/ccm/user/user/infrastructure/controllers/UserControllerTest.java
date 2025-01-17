package com.ccm.user.user.infrastructure.controllers;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    @Test
    public void shouldAddUser() {
        given()
            .queryParam("name", "tipoDeIncógnito")
            .queryParam("userId", 123456789)
            .post("/user/addUser")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void shouldGetHttpCode403WhenAddingANewUser() {
        given()
            .queryParam("name", "tipoDeIncógnito")
            .queryParam("userId", 1)
            .post("/user/addUser");

        given()
            .queryParam("name", "tipoDeIncógnito")
            .queryParam("userId", 1)
            .post("/user/addUser")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void shouldAddFavouritePokemon() {
        given()
            .queryParam("name", "tipoDeIncógnito")
            .queryParam("userId", 1)
            .post("/user/addUser");

        given()
            .header("id", 1)
            .queryParam("id", 1234)
            .put("/user/addFavouritePokemon")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void shouldGetHttpCode409WhenAddingANewFavouritePokemon() {
        given()
            .queryParam("name", "tipoDeIncógnito")
            .queryParam("userId", 1)
            .post("/user/addUser");

        given()
            .header("id", 1)
            .queryParam("id", 123)
            .put("/user/addFavouritePokemon");

        given()
            .header("id", 1)
            .queryParam("id", 123)
            .put("/user/addFavouritePokemon")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_CONFLICT);
    }

    @Test
    public void shouldGetHttpCode403WhenAddingANewFavouritePokemon() {
        given()
            .header("id", 9)
            .queryParam("id", 123)
            .put("/user/addFavouritePokemon")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
