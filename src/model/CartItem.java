package model;

public class CartItem {
	private int id;
	private int customerId;
	private int productId;
	private int count;
	
	public CartItem(int id, int customerId, int productId, int count) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.productId = productId;
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	
}
