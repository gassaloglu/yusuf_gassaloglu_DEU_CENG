package dcs;

import java.util.ArrayList;

import Gui.LoginWindow;

public class Dcs {
	public static User currentUser = new User(new PersonalInfo("","","","","",""));
	public static CoordinationUnit currentUnit = new CoordinationUnit();
	public static Personnel currentPersonnel = new Personnel(new PersonalInfo("","","","","",""));
	
	public Dcs() {
        DB database = DB.getInstance();
		database.createPersonnelTable();
		database.createRequestsTable();
		database.createUsersTable();
		loginWindow();
	}
	
	@SuppressWarnings("static-access")
	public void loginWindow() {
		 LoginWindow loginWindow = new LoginWindow();
    }
}

