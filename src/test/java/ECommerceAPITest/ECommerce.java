package ECommerceAPITest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ECommerce {

	public static void main(String[] args) {
		//login
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		PojoLoginRequest loginRequest = new PojoLoginRequest();

		loginRequest.setUserEmail("guganmk27@gmail.com");
		loginRequest.setUserPassword("NandhuG@714");

		RequestSpecification reqlogin = given().log().all().spec(req).body(loginRequest);
		PojoLoginResponse reslogin = reqlogin.when().post("/api/ecom/auth/login").then().log().all().extract()
				.response().as(PojoLoginResponse.class);

		String token = reslogin.getToken();
		String userId = reslogin.getUserId();
		System.out.println("Token: " + token);
		System.out.println("UserID: " + userId);

		// CreateProduct
		RequestSpecification createProductReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();

		RequestSpecification productReq = given().log().all().param("productName", "qwerty")
				.param("productAddedBy", userId).param("productCategory", "fashion")
				.param("productSubCategory", "shirts").param("productPrice", "11500")
				.param("productDescription", "Addias Originals").param("productFor", "women")
				.multiPart("productImage", new File("C:\\Users\\DELL\\Desktop"));

		String createProductRes = productReq.when().post("/api/ecom/product/add-product").then().log().all().extract()
				.response().asString();
		JsonPath js = new JsonPath(createProductRes);
		String productId = js.get("productId");

		// createOrder

		RequestSpecification createOrderReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).addHeader("Authorization", token).build();

		PojoOrderDetails orderDetails = new PojoOrderDetails();
		orderDetails.setCountry("India");
		orderDetails.setProductOrderedId(productId);

		List<PojoOrderDetails> orderdetail = new ArrayList<PojoOrderDetails>();
		orderdetail.add(orderDetails);

		PojoCreateOrder order = new PojoCreateOrder();
		order.setOrders(orderdetail);

		RequestSpecification orderreq = given().log().all().spec(createOrderReq).body(order);
		String orderReponse = orderreq.when().post("/api/ecom/order/create-order").then().log().all().extract()
				.response().asString();
		System.out.println(orderReponse);

		// DeleteOrder

		RequestSpecification deleteOrderReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).addHeader("Authorization", token).build();
		
		RequestSpecification deletProdReq = given().log().all().spec(deleteOrderReq).pathParam("productId", productId);
		String deleteProdRes = deletProdReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all()
		.extract().response().asString();
		JsonPath js1 = new JsonPath(deleteProdRes);
		String message = js1.get("message");
		
		Assert.assertEquals("Product deleted successfuly", message);
	}

}
