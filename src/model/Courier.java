package model;

public class Courier extends User{
	private int userId;
	private String vehicleType;
	private String vehiclePlate;
	private String status;
	
	public Courier(int id, String fullName, String email, String password, String phone, String address, 
			String role,
			String gender, int userId, String vehicleType, String vehiclePlate, String status) {
		super(id, fullName, email, password, phone, address, role, gender);
		this.userId = userId;
		this.vehicleType = vehicleType;
		this.vehiclePlate = vehiclePlate;
		this.status = status;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehiclePlate() {
		return vehiclePlate;
	}

	public void setVehiclePlate(String vehiclePlate) {
		this.vehiclePlate = vehiclePlate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
