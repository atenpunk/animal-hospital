package co.th.aten.network.model;

import java.io.Serializable;
import java.util.Date;

public class DirectLineReportModel implements Serializable {
	
	
	private int index;	
	private String customerId;
	private String customerName;
	private String position;
	private Date regisDate;
	private String upperLineId;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	public Date getRegisDate() {
		return regisDate;
	}
	public void setRegisDate(Date regisDate) {
		this.regisDate = regisDate;
	}
	public String getUpperLineId() {
		return upperLineId;
	}
	public void setUpperLineId(String upperLineId) {
		this.upperLineId = upperLineId;
	}
	
	
	
}
