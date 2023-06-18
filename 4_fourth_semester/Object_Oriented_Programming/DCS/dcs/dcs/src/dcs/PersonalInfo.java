package dcs;

import java.sql.ResultSet;

public class PersonalInfo {
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String password;
	private String email;
	private String identityNumber;
	
	public PersonalInfo(String firstName, String lastName, String phoneNumber, String password, String email, String identityNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.email = email;
		this.identityNumber = identityNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstNname) {
		this.firstName = firstNname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

    public static PersonalInfo fromResultSet(ResultSet rs) {
        try {
            rs.next();

            PersonalInfo info = new PersonalInfoBuilder()
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .phoneNumber(rs.getString("phone_number"))
                .password(rs.getString("password"))
                .email(rs.getString("email"))
                .identityNumber(rs.getString("id_number"))
                .build();

            return info;
        } catch (Exception e) {
            System.out.println("Invalid Entry");
            return null;
        }
    }
}
