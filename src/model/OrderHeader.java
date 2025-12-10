package model;

public class OrderHeader {
	private int id;
	private int cutomerId;
	private int promoId;
	private String status;
	private double totalAmount;
	public OrderHeader(int id, int cutomerId, int promoId, String status, double totalAmount) {
		super();
		this.id = id;
		this.cutomerId = cutomerId;
		this.promoId = promoId;
		this.status = status;
		this.totalAmount = totalAmount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCutomerId() {
		return cutomerId;
	}
	public void setCutomerId(int cutomerId) {
		this.cutomerId = cutomerId;
	}
	public int getPromoId() {
		return promoId;
	}
	public void setPromoId(int promoId) {
		this.promoId = promoId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
	
}
