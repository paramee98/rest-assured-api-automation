package files;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;

public class SpecBuilderTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//Create Object for AddPlace and Set Values
		
		AddPlace a = new AddPlace();
		a.setAccuracy(50);
		a.setAddress("29, side layout, cohen 09");
		a.setLanguage("French-IN");
		a.setName("Frontline house");
		a.setPhone_number("(+91) 983 893 3937");
		a.setWebsite("http://google.com");
		
		//Array
		ArrayList<String> x = new ArrayList<String>();        
		x.add("shoe park");
		x.add("shop");
		a.setTypes(x);
		
		 //NestedJson
		Location l = new Location();		                 
		l.setLat(-38.383494);
		l.setLng(33.427362);
		a.setLocation(l);
				
		//RequestSpecBuilder
		
		RequestSpecification req = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON)
				.build();
		
		//ResponseSpecBuilder
		
		ResponseSpecification resspec = new ResponseSpecBuilder()
			.expectStatusCode(200)
			.expectContentType(ContentType.JSON)
			.build();
		
	
		RequestSpecification res = given().spec(req)
		.body(a); //break the request separately
		
		//break the response separately & combine the request with res object
		
		Response response = res.when().post("/maps/api/place/add/json")
		.then().spec(resspec).extract().response();
		
		String responseString = response.asString();
		System.out.println(responseString);
		
		
	}

}
