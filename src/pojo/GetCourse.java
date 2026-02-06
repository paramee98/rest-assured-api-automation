package pojo;

public class GetCourse {
	
	//here we take the JSon body of getCourse
	
	private String url;
	private String services;
	private String expertiese;
	private courses courses;
	private String instructors;
	private String linkedIn;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getExpertiese() {
		return expertiese;
	}
	public void setExpertiese(String expertiese) {
		this.expertiese = expertiese;
	}
	public pojo.courses getCourses() {
		return courses;
	}
	public void setCourses(pojo.courses courses) {
		this.courses = courses;
	}
	public String getInstructors() {
		return instructors;
	}
	public void setInstructors(String instructors) {
		this.instructors = instructors;
	}
	public String getLinkedIn() {
		return linkedIn;
	}
	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

}
