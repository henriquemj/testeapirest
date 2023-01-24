package br.ce.wcaquino.rest;

import static org.hamcrest.Matchers.*;

import java.io.File;

import static io.restassured.RestAssured.given;

import org.junit.Test;

public class FileTest {
	
	@Test
	public void deveObrigarEnvioArquivo() {
		given()
			.log().all()
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404)
			.body("error", is("Arquivo não enviado"))
			;
	}
	
	@Test
	public void devoFazerUploadArquivo() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/users.pdf"))
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("users.pdf"))
			;
	}
	
	@Test
	public void nãoDevoFazerUploadArquivoGrande() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/teste10.jar"))
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.time(lessThan(10000L))
			.statusCode(413)
			;
	}


}
