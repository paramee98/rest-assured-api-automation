import files.payLoad;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(payLoad.CoursePrice());
		
		
		// Print No of Courses returned by API
		
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		
		// Print purchase amount
		
		int purchaseAmount = js.getInt("dashboard. purchaseAmount");
		System.out.println(purchaseAmount);
		
		
		//print title of the first course
		
		String titleFirstCourse = js.getString("courses[0].title");
		System.out.println(titleFirstCourse);
		
				
		
		//print all courses title and price
		
		for(int i=0; i<count; i++)
		{
			String courseTitle = js.get("courses["+i+"].title");
			System.out.println(js.get("courses["+i+"].price").toString());
			
			System.out.println(courseTitle);
		}
		
		
		
		//print no. of copies sold by RPA course
		
		System.out.println("Copies");
		
		for(int i=0; i<count; i++)
		{
			String courseTitle = js.get("courses["+i+"].title");
			if(courseTitle.equalsIgnoreCase("RPA"))
			{
				int copies = js.get("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
			
		}
		
					
	}

}