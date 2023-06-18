package dcs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.function.Function;

public class DB {
    static final String URL = "jdbc:postgresql://localhost:5432/DCS";
	static final String ADMIN = "postgres";
	static final String PASSWORD = "343008";

    static private DB instance = null;

    private DB() { }

    public static DB getInstance() {
        if (instance == null) instance = new DB();
        return instance;
    }

    private Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(URL, ADMIN, PASSWORD);
        return connection;
    }

    private boolean update(String update) {
        try (
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
        ) {
            int rows = statement.executeUpdate(update);
            System.out.println("[OK] Update '" + update + "' executed on " + rows + " rows successfully.");
            return true;
        } catch (Exception e) {
            System.out.println("[ERROR] Update '" + update + "' failed.");
            e.printStackTrace();
            return false;
        }
    }

    private boolean contains(String table, String condition) {
        Function<ResultSet, Boolean> exists = (rs) -> {
            try {
                return rs.next();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        };

        return this.executeOnQuery(table, condition, exists);
    }

    private <T> T executeOnQuery(String table, String condition, Function<ResultSet, T> function) {
        String query = String.format("SELECT * FROM %s WHERE %s", table, condition);

		try (
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
        ) {
            return function.apply(resultSet);
		} catch (Exception e) {
            System.out.println("[ERROR] Executing query '" + query + "' failed.");
			System.out.println(e);
			return null;
		}
    }
	
    public String[] print(String table, String condition, Function<ResultSet, String> toString) {
		ArrayList<String> strings = new ArrayList<String>();

        String query = (condition == null)
            ? String.format("SELECT * FROM %s", table)
            : String.format("SELECT * FROM %s WHERE %s", table, condition);

		try (
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                strings.add(toString.apply(resultSet));
            }
		} catch (Exception e) {
            System.out.println("[ERROR] Executing query '" + query + "' failed.");
			System.out.println(e);
		}

        return strings.toArray(new String[] {});
    }

	public boolean InsertUser(String firstName, String lastName, String password, String phoneNumber, String email, String idNumber) {
        if (this.isUserExist(idNumber)) {
            return false;
        } else {
			String values = String.format("VALUES('%s', '%s', '%s', '%s', '%s', '%s')", firstName, lastName, password, phoneNumber, email, idNumber);
			String update = "INSERT INTO USERS ( first_name, last_name, password, phone_number, email, id_number )" + values;
            this.update(update);
            return true;
        }
	}
	
	public void deleteUser(String id) {
        String update = "DELETE from USERS WHERE id_number = '" + id + "'";
        this.update(update);
	}
	
	public void UpdateUserInfo(String id, String column, String value) {
        String update = "UPDATE USERS set "+column+" = '" + value +"' WHERE id_number = '" + id + "'";
        this.update(update);
	}
	
	public boolean login(String idNumber, String password) {
        String condition = String.format("id_number = '%s' AND password = '%s'", idNumber, password);
        PersonalInfo info = this.executeOnQuery("USERS", condition, PersonalInfo::fromResultSet);
        
        if (info == null) {
        	System.out.println("User does not exist");
            return false;
        } else {
            Dcs.currentUser.setPersonalInfo(info);
            return true;
        }
	}
	
	public boolean isCoordinationUnit(String idNumber, String password) {
        String condition = String.format("id_number = '%s' AND password = '%s' AND is_coordination_unit = TRUE", idNumber, password);
        return this.contains("USERS", condition);
	}
	
	public boolean isPersonnel(String idNumber, String password) {
        String condition = String.format("id_number = '%s' AND password = '%s' AND is_personnel = TRUE", idNumber, password);
        return this.contains("USERS", condition);
    }

	public PersonalInfo getPersonalInfo(String idNumber) {
        String condition = String.format("id_number = '%s'", idNumber);
        return this.executeOnQuery("USERS", condition, PersonalInfo::fromResultSet);
	}
	
	public boolean isUserExist(String idNumber) {
        String condition = String.format("id_number = '%s'", idNumber);
        return this.contains("USERS", condition);
	}
	
	public boolean InsertRequest(String description, String location, String status, String owner_id, String personnel_id) {
        String values = String.format("VALUES('%s', '%s', '%s', '%s', '%s'); ", description, location, status, owner_id, personnel_id);
        String update = "INSERT INTO REQUESTS ( description, location, status, owner_id, personnel_id)"+values;
        return this.update(update);
	}

	public String[] printRequestsList(String ownerId) {
        String condition = String.format("owner_id = '%s'", ownerId);
        return this.print("REQUESTS", condition, Request::resultSetToString);
	}
	
	public void UpdateRequesInfo(String id, String column, String value) {
        String update = "UPDATE REQUESTS set "+column+" = '" + value +"' WHERE id = '" + id + "'";
        this.update(update);
	}
	
	public String[] printUnfullfilledRequests() {
        return this.print("REQUESTS", "status = 'WAITING'", Request::resultSetToString);
    }
	
	public String[] printAllRequests() {
        return this.print("REQUESTS", null, Request::resultSetToString);
	}
	
	// create initial users table
	public void createUsersTable() {
        String update = """
            CREATE TABLE IF NOT EXISTS USERS (
                    id SERIAL PRIMARY KEY,
                    first_name VARCHAR(20) NOT NULL,
                    last_name VARCHAR(30) NOT NULL,
                    password VARCHAR(64) NOT NULL,
                    phone_number VARCHAR(10) NOT NULL,
                    email VARCHAR(50) NOT NULL,
                    id_number VARCHAR(11) NOT NULL,
                    is_personnel BOOLEAN NOT NULL DEFAULT FALSE,
                    is_coordinaiton_unit BOOLEAN  NOT NULL DEFAULT FALSE
                    );
                    """;

        this.update(update);
	}

	public void createRequestsTable() {
        String update = """
                  CREATE TABLE IF NOT EXISTS REQUESTS (
                  id SERIAL PRIMARY KEY,
                  description VARCHAR(200) NOT NULL,
                  location VARCHAR(50) NOT NULL,
                  status VARCHAR(10) NOT NULL,
                  owner_id VARCHAR(11) NOT NULL,
                  personnel_id VARCHAR(11) NOT NULL
                );
                """;

        this.update(update);
	}
	
	// Create personnel table
	public void createPersonnelTable() {
        String update = """
                  CREATE TABLE IF NOT EXISTS PERSONNEL (
                  id SERIAL PRIMARY KEY,
                  task_id VARCHAR(5) NOT NULL,
                  first_name VARCHAR(20) NOT NULL,
                  last_name VARCHAR(30) NOT NULL,
                  phone_number VARCHAR(10) NOT NULL,
                  id_number VARCHAR(11) NOT NULL
                );
                """;
        this.update(update);
	}
	
	public boolean InsertPersonnel(String firstName, String lastName, String phoneNumber, String idNumber) {
        if (this.isPersonnelExist(idNumber)) {
            return false;
        } else {
			String values = String.format("VALUES('NaN','%s', '%s', '%s', '%s'); ", firstName, lastName, phoneNumber, idNumber);
			String update = "INSERT INTO PERSONNEL (task_id, first_name, last_name, phone_number, id_number )" + values;
            this.update(update);
            return true;
        }
	}
	
	public void UpdatePersonnelInfo(String id, String column, String value) {
        String update = "UPDATE PERSONNEL set "+column+" = '" + value +"' WHERE id_number = '" + id + "'";
        this.update(update);
	}
	
	public boolean isTaskAssigned(String id) {
        String condition = String.format("id = '%s' AND personnel_id != 'NaN'", id);
        return this.contains("REQUESTS", condition);
	}
	
	public String[] printAllFreePersonnel() {
        return this.print("PERSONNEL", "task_id = 'NaN'", Personnel::resultSetToString);
	}
	
	public boolean userValidation(String id, String phoneNumber) {
        String condition = String.format("id_number = '%s' AND phone_number = '%s'", id, phoneNumber);
        return this.contains("USERS", condition);
	}
	
	public boolean isPersonnelExist(String id) {
        String condition = String.format("id_number = '%s'", id);
        return this.contains("PERSONNEL", condition);
	}
	
	public String[] printAllTasks(String id) {
        String condition = String.format("personnel_id = '%s' AND status = 'PENDING'", id);
        return this.print("REQUESTS", condition, Request::resultSetToString);
	}
	
	public boolean isRequestExist(String id) {
        String condition = String.format("id = '%s'", id);
        return this.contains("REQUESTS", condition);
	}
	
	public boolean isValidTask(String personnelId, String taskId) {
        String condition = String.format("id_number = '%s' AND task_id = '%s'", personnelId, taskId);
        return this.contains("PERSONNEL", condition);
	}
}
