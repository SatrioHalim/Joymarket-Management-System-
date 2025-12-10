package model;

public class Promo {
	private int id;
	private String code;
	private String headline;
	private double discountPercentage;
	public Promo(int id, String code, String headline, double discountPercentage) {
		super();
		this.id = id;
		this.code = code;
		this.headline = headline;
		this.discountPercentage = discountPercentage;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public double getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	
	
}
