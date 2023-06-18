package dcs;

import java.util.List;

public class User {
	private List<Request> requests;
	private PersonalInfo personalInfo;
	
	public User(PersonalInfo personalInfo) {
		this.requests = requests;
		this.personalInfo = personalInfo;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	public PersonalInfo getPersonalInfo() {
		return personalInfo;
	}

	public void setPersonalInfo(PersonalInfo personalInfo) {
		this.personalInfo = personalInfo;
	}
}
