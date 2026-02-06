import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import files.ReUsableMethods;
import files.payLoad;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// validate if Add Place API is working as expected
		
		// Add Place

		// given - input all details
		// when - submit API (HTTP method(get/post), resource)
		// Then - validate response
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String response = given().log().all().queryParam("key", "qaclick123")
		.header("Content-Type", "application/json")
		.body(payLoad.AddPlace()).when().post("/maps/api/place/add/json")
		.then().log().all().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.41 (Ubuntu)")
		.extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response); //for passing JSon
		String placeId = js.getString("place_id");
		
		System.out.println(placeId);
		
		String getResponse = RestAssured.given().log().all().queryParam("place_id", placeId).queryParam("key", "qaclick123")
		.when().get("/maps/api/place/get/json").then().extract().response().asString();
		
		System.out.println(getResponse);


		//update place with new address
		
		String newAddress = "Summer Walk, Africa";
				
		given().log().all().queryParam("key", "qaclick123")
		.header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+",\r\n" 
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("/maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200)
		.body("msg", equalTo ("Address successfully updated"));
				
				
				
		// get place to validate if the new address is present on the response
		// 1. put updated address into a variable
		// 2. Also change body address into variable newAddress
				


		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
		.when().get("/maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
				
		System.out.println(getPlaceResponse);
				
		JsonPath js1 = ReUsableMethods.rawToJason(getPlaceResponse);
		String actualAddress = js1.getString("address");
				
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
				
	}
}