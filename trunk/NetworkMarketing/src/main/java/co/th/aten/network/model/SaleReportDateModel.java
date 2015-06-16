package co.th.aten.network.model;

import java.io.Serializable;
import java.util.Date;

public class SaleReportDateModel implements Serializable {
	
	
	private int index;
	private Date date;
	private String receipt;
	private String subscriber;
	private String customerName;
	private Double vat;
	private Double tax_dit;
	private Double tax;
	private Double amount;
	private Double sumamount;
	private Double sumvat;
	private Double sumtax_dit;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public String getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(String subscriber) {
		this.subscriber = subscriber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Double getVat() {
		return vat;
	}
	public void setVat(Double vat) {
		this.vat = vat;
	}
	public Double getTax_dit() {
		return tax_dit;
	}
	public void setTax_dit(Double tax_dit) {
		this.tax_dit = tax_dit;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getSumamount() {
		return sumamount;
	}
	public void setSumamount(Double sumamount) {
		this.sumamount = sumamount;
	}
	public Double getSumvat() {
		return sumvat;
	}
	public void setSumvat(Double sumvat) {
		this.sumvat = sumvat;
	}
	public Double getSumtax_dit() {
		return sumtax_dit;
	}
	public void setSumtax_dit(Double sumtax_dit) {
		this.sumtax_dit = sumtax_dit;
	}
	
	
	
}
