package dcs;

import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;

public class Personnel extends User {
	private List<Request> tasks;
	
	public Personnel(PersonalInfo personalInfo) {
		super(personalInfo);
		this.tasks = new ArrayList<Request>();
	}

	public List<Request> getTasks() {
		return tasks;
	}

	public void setTasks(List<Request> tasks) {
		this.tasks = tasks;
	}
	
    public static String resultSetToString(ResultSet rs) {
        try {
            String request = "";
            request += rs.getString("id");
            request += " | " + rs.getString("task_id");
            request += " | " + rs.getString("id_number");
            request += " | " + rs.getString("first_name");
            request += " | " + rs.getString("last_name");
            request += " | " + rs.getString("phone_number");
            return request;
        } catch (Exception e) {
            System.out.println("[ERROR] Error reading columns of personnel.");
            e.printStackTrace();
            return null;
        }
    }
}
