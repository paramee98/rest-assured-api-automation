package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	@Test(dataProvider="BooksData")
	
	public void addBook(String isbn, String aisle)
	{
		RestAssured.baseURI = "http://216.10.245.166";
		
		String response = given().log().all().headers("Content-Type", "application/json")
		.body(payLoad.Addbook(isbn, aisle))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJason(response);
		String id = js.get("ID");
		
		System.out.println(id);
		
	}
	
	@DataProvider(name="BooksData")
	
	public Object[][] getData()
	{
		return new Object[][] {{"uuu","121"},{"jjj","632"},{"liy","888"}};
	}
		
}
