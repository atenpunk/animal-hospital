package co.th.aten.network.model;

import java.io.Serializable;

public class TeamBinaryReportModel implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int index;	
	private int customerId;
	private String customerCode;
	private String customerName;
	private String position;	
	private String recomment;
	private String honor;
	private String side;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getRecomment() {
		return recomment;
	}
	public void setRecomment(String recomment) {
		this.recomment = recomment;
	}
	public String getHonor() {
		return honor;
	}
	public void setHonor(String honor) {
		this.honor = honor;
	}
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}
}
