package files;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;

public class SerializationTest {

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
				
		
		Response res = given().log().all().queryParam("key", "qaclick123")
				.body(a)
				.when().post("/maps/api/place/add/json")
				.then().assertThat().statusCode(200).extract().response();
		
		String responseString = res.asString();
		System.out.println(responseString);
	}

}
