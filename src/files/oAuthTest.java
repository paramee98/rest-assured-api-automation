package files;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.webAutomation;

public class oAuthTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		//Section10Lecture
		
		//CreateAnArray
		
		String [] courseTitles = {"Selenium", "Cypress", "Protractor"};
		
		System.setProperty("webdriver.chrome.driver", 
				"D:\\Udemy-Selenium Docs\\Section 1\\chromedriver_win32\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth/identifier?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&hl=en-GB&service=lso&o2v=2&flowName=GeneralOAuthFlow");
		driver.findElement(By.cssSelector("input[type-'email']")).sendKeys("parameeweerarathna@gmail.com");
		driver.findElement(By.cssSelector("input[type-'email']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input[type-'password']")).sendKeys("kl*&tt567WW33##F");
		driver.findElement(By.cssSelector("input[type-'password']")).sendKeys(Keys.ENTER);
		Thread.sleep(4000);
		
		String url = driver.getCurrentUrl();
		String partialcode = url.split("code=")[1];
		String code = partialcode.split("&scope")[0];
		
		System.out.println(code);
		
		//End of SeleniumPart
		
		
		String accessTokenResponse = given()
				.queryParams("code","")
				.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type","authorization_code")
				.when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token")
				.asString();
		
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.get("access_token");
		
		GetCourse gc = given().queryParam("accessToken", accessToken)
		.when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		//System.out.println(getCoursesResponse);
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructors());
		
		
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		//GetPrice
		
		List<Api> apiCourses = gc.getCourses().getApi();
		
		for(int i=0; i<apiCourses.size(); i++)
		{
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Web Services testing"))
			{
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		
		//Get Course Names of WebAutomation
		
		ArrayList<String> a = new ArrayList<String>();
		
		
		List<webAutomation> getAutomationCourses = gc.getCourses().getWebAutomation();
		
		for(int j=0; j<getAutomationCourses.size(); j++)
		{
			a.add((getAutomationCourses.get(j).getCourseTitle()));
		}
		
		List<String> expectedCourseTitleList = Arrays.asList(courseTitles);
		
		Assert.assertTrue(a.equals(expectedCourseTitleList));
		
	}

}
