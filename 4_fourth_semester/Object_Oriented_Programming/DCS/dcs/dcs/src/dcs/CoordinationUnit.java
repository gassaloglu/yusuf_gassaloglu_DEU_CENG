package dcs;

import java.util.List;
import java.util.ArrayList;

public class CoordinationUnit {
	private List<Request> tasks;
	private List<Personnel> personnels;

	public CoordinationUnit() {
		this.tasks = new ArrayList<Request>();
		this.personnels = new ArrayList<Personnel>();
	}
	
	public List<Request> getTasks() {
		return tasks;
	}
	public void setTasks(List<Request> tasks) {
		this.tasks = tasks;
	}
	public List<Personnel> getPersonnels() {
		return personnels;
	}
	public void setPersonnels(List<Personnel> personnels) {
		this.personnels = personnels;
	}
	
	public void employ(Personnel personnel) {
		//TODO
	}
	public void disemploy(Personnel personnel) {
		//TODO
	}
	public void addPersonnel(User user) {
		//TODO
	}
	public void removePersonnel(Personnel personnel) {
		//TODO
	}
}
