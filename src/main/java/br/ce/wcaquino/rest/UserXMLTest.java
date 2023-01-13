package br.ce.wcaquino.rest;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

import org.junit.Test;

public class UserXMLTest {
	
	@Test
	public void devoTrabalharComXML() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/usersXML/3")
		.then()
			.statusCode(200)
			.body("user.name", is("Ana Julia"))
			.body("user.@id", is("3"))
			.body("user.filhos.name.size()", is(2))
			.body("user.filhos.name[0]", is("Zezinho"))
			.body("user.filhos.name[1]", is("Luizinho"))
			.body("user.filhos.name", hasItem("Luizinho"))
			.body("user.filhos.name", hasItems("Luizinho", "Zezinho"))
		;
	}
	
	@Test
	public void devoTrabalharComXMLTwo() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/usersXML/3")
		.then()
			.statusCode(200)
			
			.rootPath("user")
			.body("name", is("Ana Julia"))
			.body("@id", is("3"))
			
			.rootPath("user.filhos")
			.body("name.size()", is(2))
			
			.detachRootPath("filhos")
			.body("filhos.name[0]", is("Zezinho"))
			.body("filhos.name[1]", is("Luizinho"))
			.appendRootPath("filhos")
			.body("name", hasItem("Luizinho"))
			.body("name", hasItems("Luizinho", "Zezinho"))
		;
	}

}
