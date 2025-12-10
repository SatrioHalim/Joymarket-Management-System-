package model;

public class Delivery {
	private int id;
	private int orderId;
	private int courierId;
	private String status;
	
	public Delivery(int id, int orderId, int courierId, String status) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.courierId = courierId;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getCourierId() {
		return courierId;
	}
	public void setCourierId(int courierId) {
		this.courierId = courierId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
