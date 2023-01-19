package br.ce.wcaquino.rest;

import org.junit.Test;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;


public class EnvioDadosTest {
	
	@Test
	public void deveEnviarValorViaQuery() {
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users?format=json")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.JSON)
			;
	}

}
