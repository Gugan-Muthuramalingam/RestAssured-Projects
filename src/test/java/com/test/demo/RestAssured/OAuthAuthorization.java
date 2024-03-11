package com.test.demo.RestAssured;

import static io.restassured.RestAssured.given;

import Pojo.GetCourse;
import io.restassured.path.json.JsonPath;

public class OAuthAuthorization {
	
	public static void main(String[] args) {

		String response = given()
				.formParams("client_id", "692183103107-p0m7ent2hk7suguv"
						+ "4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParams("grant_type", "client_credentials")
				.formParams("scope", "trust").when().log().all()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

		System.out.println(response);
		JsonPath path = new JsonPath(response);
		String pathresponse = path.getString("access_token");

		String gc = given().queryParams("access_token", pathresponse).when().log().all()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails?access_token=\""+pathresponse+"")
				.asString();

		System.out.println(gc);
		
		JsonPath path1 = new JsonPath(response);
		String LinkedIn = path1.getString("linkedIn");
		System.out.println(LinkedIn);

	}

}
