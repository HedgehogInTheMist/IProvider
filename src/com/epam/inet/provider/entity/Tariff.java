package com.epam.inet.provider.entity;

/**
 * tariff
 */
public class Tariff extends Entity{

	/**
	 * tariff name
	 */
	private String tariffname;

	/**
	 * tariff details
	 */
	private String details;

	/**
	 * tariff price
	 */
	private double price;

	/**
	 * is hot
	 */
	private boolean hot;

	/**
	 * discount for regular clients
	 */
	private int regularDiscount;

	/**
	 * tariff type
	 */
	private TariffType type = TariffType.CELLPHONE;

	public TariffType getType() {
		return type;
	}

	public void setType(TariffType type) {
		this.type = type;
	}

	public String getTariffname() {
		return tariffname;
	}

	public void setTariffname(String tariffname) {
		this.tariffname = tariffname;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isHot() {
		return hot;
	}

	public void setHot(boolean hot) {
		this.hot = hot;
	}

	public int getRegularDiscount() {
		return regularDiscount;
	}

	public void setRegularDiscount(int regularDiscount) {
		this.regularDiscount = regularDiscount;
	}
}
