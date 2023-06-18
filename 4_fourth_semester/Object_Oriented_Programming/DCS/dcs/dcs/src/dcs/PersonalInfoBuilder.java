package dcs;

public class PersonalInfoBuilder {
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String password;
	private String email;
	private String identityNumber;
     
    public PersonalInfoBuilder() { }

    public PersonalInfo build() {
        return new PersonalInfo(
            this.firstName,
            this.lastName,
            this.phoneNumber,
            this.password,
            this.email,
            this.identityNumber
        );
    }

	public PersonalInfoBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

	public PersonalInfoBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

	public PersonalInfoBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

	public PersonalInfoBuilder password(String password) {
        this.password = password;
        return this;
    }

	public PersonalInfoBuilder email(String email) {
        this.email = email;
        return this;
    }

	public PersonalInfoBuilder identityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
        return this;
    }
}
