package co.th.aten.network.model;

import java.io.Serializable;
import java.util.Date;

public class SalePDFModel implements Serializable {
 
	
	private String customerId;
	private String customerName;
	private String addr;
	private String rfid;
	private String receiveNo;
	private Date   date;
	private String vehiclePlateNo;
	private String productOrServiceName;
	private int itemCount;
	private Double unitPrice;
	private Double totalUnitPrice;
	private Double deposit;
	private Double netAmt;
	private Double vatAmt;
	private Double productAmt;
	private String terminal;
	private String forStr;
	private Double discount;
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
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getRfid() {
		return rfid;
	}
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	public String getReceiveNo() {
		return receiveNo;
	}
	public void setReceiveNo(String receiveNo) {
		this.receiveNo = receiveNo;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getVehiclePlateNo() {
		return vehiclePlateNo;
	}
	public void setVehiclePlateNo(String vehiclePlateNo) {
		this.vehiclePlateNo = vehiclePlateNo;
	}
	public String getProductOrServiceName() {
		return productOrServiceName;
	}
	public void setProductOrServiceName(String productOrServiceName) {
		this.productOrServiceName = productOrServiceName;
	}
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getTotalUnitPrice() {
		return totalUnitPrice;
	}
	public void setTotalUnitPrice(Double totalUnitPrice) {
		this.totalUnitPrice = totalUnitPrice;
	}
	public Double getDeposit() {
		return deposit;
	}
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	public Double getNetAmt() {
		return netAmt;
	}
	public void setNetAmt(Double netAmt) {
		this.netAmt = netAmt;
	}
	public Double getVatAmt() {
		return vatAmt;
	}
	public void setVatAmt(Double vatAmt) {
		this.vatAmt = vatAmt;
	}
	public Double getProductAmt() {
		return productAmt;
	}
	public void setProductAmt(Double productAmt) {
		this.productAmt = productAmt;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String getForStr() {
		return forStr;
	}
	public void setForStr(String forStr) {
		this.forStr = forStr;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	
	
}
