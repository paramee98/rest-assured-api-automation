package files;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

public class JiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI = "http://localhost:8080";
		
		//CreateSession
		
		SessionFilter session = new SessionFilter();
		
		String response = given().log().all().header("Content-Type", "application/json")
		.body("{ \"username\": \"kpuweerarathna\", \"password\": \"kl*&tt567WW33##F\" }")
		.filter(session).when().post("/rest/auth/1/session")
		.then().log().all().extract().response().asString();
		
		System.out.println(response);
		
		
		//AddComment
		
		String expectedMessage = "Hey!";
		
		String addCommentResponse = given().log().all().pathParam("key", "10200").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "    \"body\": \""+expectedMessage+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}")
		.filter(session).when().post("/rest/api/2/issue/{key}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js = new JsonPath(addCommentResponse);
		String commentId = js.getString("id"); 
		
		System.out.println(commentId);
		
		
		//AddAtachment
		
		given().pathParam("key", "10200").header("Content-Type", "multipart/form-data")
		.header("X-Atlassian-Token", "no-check").filter(session)
		.multiPart("file", new File("jira.txt"))
		.when().post("/rest/api/2/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		
		//GetAllDetails Of The Issue
		
		String issueDetails = given().pathParam("key", "10200")
		.queryParam("fields", "comment")
		.filter(session).log().all()
		.when().get("rest/api/2/issue/{key}")
		.then().log().all().extract().response().asString();
		
		System.out.println(issueDetails);
		
		
		JsonPath js1 = new JsonPath(issueDetails);
		int commentsCount = js1.getInt("fields.comment.comments.size()");
				
		for(int i=0; i<commentsCount; i++)
		{
			String commentIdIssue = js1.get("fields.comment.comments["+i+"].id").toString();
					
			if(commentIdIssue.equalsIgnoreCase(commentId))
			{
				String message = js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
				
				Assert.assertEquals(message, expectedMessage);
			}
					
		}		
		
	}		
}