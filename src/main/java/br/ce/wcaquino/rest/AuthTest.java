package br.ce.wcaquino.rest;

import org.junit.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

public class AuthTest {
	
	@Test
	public void deveAcessarSWAPI() {
		given()
			.log().all()
		.when()
			.get("https://swapi.dev/api/people/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Luke Skywalker"))
		;
	}
	//28d413da9547b8384fb8e0f2b9ee6dd0
	
	@Test
	public void deveObterClima() {
	    given()
	        .log().all()
	        .queryParam("q", "Fortaleza,BR")
	        .queryParam("appid", "28d413da9547b8384fb8e0f2b9ee6dd0")
	        .queryParam("units", "metric")
	    .when()
	    .   get("http://api.openweathermap.org/data/2.5/weather")
	    .then()
	        .log().all()
	        .statusCode(200)
	        .body("name", is("Fortaleza"))
	        .body("coord.lon", is(-38.5247f))
	        .body("main.temp", greaterThan(25f))
	    ;
	}
	
	@Test
	public void deveFazerAutenticacaoBasica() {
		given()
			.log().all()
		.when()
			.get("https://admin:senha@restapi.wcaquino.me/basicauth")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"))
		;
	}
	
	@Test
	public void deveFazerAutenticacaoBasica2() {
		given()
			.log().all()
			.auth().basic("admin", "senha")
		.when()
			.get("https://restapi.wcaquino.me/basicauth")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"))
		;
	}
	
	@Test
	public void deveFazerAutenticacaoBasicaChallege() {
		given()
			.log().all()
			.auth().preemptive().basic("admin", "senha")
		.when()
			.get("https://restapi.wcaquino.me/basicauth2")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"))
		;
	}
	
	// http://barrigarest.wcaquino.me/contas
	// http://seubarriga.wcaquino.me/cadastro
	
	// Email: teste2023@teste.com.br
	// senha: 123456
	
	@Test
	public void deveFazerAutenticacaoBasicaChallenge() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "teste2023@teste.com.br");
		login.put("senha", "123456");
		
		//login na api
		//Receber o token
		String token = given()
			.log().all()
			.body(login)
			.contentType(ContentType.JSON)
		.when()
			.post("http://barrigarest.wcaquino.me/signin")
		.then()
			.log().all()
			.statusCode(200)
			.extract().path("token");
		;
		
		//Obter as contas
		given()
			.log().all()
			.header("Authorization","JWT " + token)
		.when()
			.get("http://barrigarest.wcaquino.me/contas")
		.then()
			.log().all()
			.statusCode(200)
			.body("nome", hasItem("Conta de teste"))
	;
		
	}
}
