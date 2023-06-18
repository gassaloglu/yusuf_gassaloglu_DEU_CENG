package dcs;

import java.sql.ResultSet;

public class Request {
	private String description;
	private Location location;
	private String status;
	private String ownerID;
	private String personnelID;
	
	public Request(String description, Location location, String status, String ownerID, String personnelID) {
		this.description = description;
		this.location = location;
		this.status = status;
		this.ownerID = ownerID;
		this.personnelID = personnelID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}

	public String getPersonnelID() {
		return personnelID;
	}

	public void setPersonnelID(String personnelID) {
		this.personnelID = personnelID;
	}

    public static String resultSetToString(ResultSet rs) {
        try {
            String request = "";
            request += rs.getString("id");
            request += " | " + rs.getString("owner_id");
            request += " | " + rs.getString("status");
            request += " | " + rs.getString("description");
            request += " | " + rs.getString("location"); 
            return request;
        } catch (Exception e) {
            System.out.println("[ERROR] Error reading columns of requests.");
            e.printStackTrace();
            return null;
        }
    }
}
