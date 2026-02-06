package files;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.EcomLoginRequest;
import pojo.EcomLoginResponse;
import pojo.Order;
import pojo.OrderDetail;
import static io.restassured.RestAssured.*;
import java.io.File;
import java.util.ArrayList;

import org.testng.Assert;

public class EcomApiTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Login with AccessToek
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		
		EcomLoginRequest lr = new EcomLoginRequest();
		lr.setUserEmail("parameeweerarathna@gmail.com");
		lr.setUserPassword("gw@L9YYVEXZ6zc");
		
		RequestSpecification loginReq = given().log().all().spec(req).body(lr);
				
		EcomLoginResponse lresponse = loginReq.when().post("/api/ecom/auth/login")
				.then().log().all().extract().as(EcomLoginResponse.class);
		
		System.out.println(lresponse.getToken());
		String token = lresponse.getToken();
		
		System.out.println(lresponse.getUserId());
		String userId = lresponse.getUserId();
		
		
		//AddProduct
		
		RequestSpecification addProductReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();
		
		RequestSpecification reqAddProduct = given().log().all().spec(addProductReq)
				.param("productName", "Laptop")
				.param("productAddedBy", userId)
				.param("productCategory", "Electronic")
				.param("productSubCategory", "Computers")
				.param("productPrice", "12500")
				.param("productDescription", "Originals")
				.param("productFor", "AllAges")
				.param("productImage", new File("C:\\Users\\lahiru weerasinghe\\OneDrive\\Documents\\Zoom\\uuu.png"));
		
		String addproductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
				.then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(addproductResponse);
		String productId = js.get("productId");
		
		
		//Create Order
		
		RequestSpecification orderReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCountry("Sri Lanka");
		orderDetail.setProductOrderId(productId);
		
		ArrayList<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		orderDetailList.add(orderDetail);
		
		Order order = new Order();
		order.setOrders(orderDetailList);
		
		RequestSpecification addOrderRequest = given().log().all().spec(orderReq).body(order);
		
		String addOrderResponse = addOrderRequest.when().post("/api/ecom/order/create-order")
				.then().log().all().extract().response().asString();
		
		System.out.println(addOrderResponse);
		
		
		//DeleteOrder
		
		RequestSpecification  deleteOrderBaseUri = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON).build();
		
		RequestSpecification deleteOrderReq = given().log().all().spec(deleteOrderBaseUri).pathParam("productId", productId);
		
		String deleteOrderResponse = deleteOrderReq.when().delete("/api/ecom/product/delete-product/{productId}")
				.then().log().all().extract().response().asString();
		
		JsonPath js1 = new JsonPath (deleteOrderResponse);
		Assert.assertEquals("Product Delete Sucsessfully", js1.get("message"));		
		
		
	}

}