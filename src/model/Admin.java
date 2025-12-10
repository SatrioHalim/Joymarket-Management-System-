package model;

public class Admin extends User{
	private int userId;
	private String emergencyContact;
	
	public Admin(int id, String fullName, String email, String password, String phone, String address, String role,
			String gender, int userId, String emergencyContact) {
		super(id, fullName, email, password, phone, address, role, gender);
		this.userId = userId;
		this.emergencyContact = emergencyContact;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	
	
}
