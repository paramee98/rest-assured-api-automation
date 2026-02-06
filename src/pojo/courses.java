package pojo;

import java.util.List;

public class courses {
	
	private List<webAutomation> webAutomation;
	private List<Api> api;
	private List<Mobile> mobile;
	
	public List<pojo.webAutomation> getWebAutomation() {
		return webAutomation;
	}
	public void setWebAutomation(List<pojo.webAutomation> webAutomation) {
		this.webAutomation = webAutomation;
	}
	public List<Api> getApi() {
		return api;
	}
	public void setApi(List<Api> api) {
		this.api = api;
	}
	public List<Mobile> getMobile() {
		return mobile;
	}
	public void setMobile(List<Mobile> mobile) {
		this.mobile = mobile;
	}
		
}
