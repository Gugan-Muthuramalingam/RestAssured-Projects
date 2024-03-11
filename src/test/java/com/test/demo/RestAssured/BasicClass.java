package com.test.demo.RestAssured;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import BaseFiles.PayLoad;

public class BasicClass {

	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = RestAssured.given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json").body(PayLoad.addPlace()).when()
				.post("maps/api/place/add/json").then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

		System.out.println(response);

		JsonPath js = PayLoad.rawToJson(response);
		String placeId = js.getString("place_id");
		System.out.println(placeId);

		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\""+placeId+"\",\r\n"
						+ "\"address\":\"70 Summer walk, USA\",\r\n" + "\"key\":\"qaclick123\"\r\n" + "}\r\n" + "")
				.when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));
	}

}
