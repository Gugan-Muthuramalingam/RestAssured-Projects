package Serialization;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

public class BaseCode {
	public static void main(String[] args) {	
	
	RestAssured.baseURI="https://rahulshettyacademy.com";
	AddPlace p = new AddPlace();
	p.setAccuracy(50);
	p.setAddress("29, side layout, cohen 09");
	p.setLangugae("French-IN");
	p.setWebsite("http://google.com");
	p.setPhone_number("+91 7845157502");
	p.setName("Frontline house");
	List<String> list = new ArrayList<String>();
	list.add("shoe park");
	list.add("shop");
	
	p.setTypes(list);
	
	Location l = new Location();
	l.setLat(-38.383494);
	l.setLng(33.427362);
	p.setLocation(l);
	
	Response res = given().log().all().queryParam("key", "qaclick123").body(p)
	.when().post("/maps/api/place/add/json")
	.then().assertThat().statusCode(200).extract().response();
	
	String Stringresponse = res.asString();
	System.out.println(Stringresponse);
	
	}
}
