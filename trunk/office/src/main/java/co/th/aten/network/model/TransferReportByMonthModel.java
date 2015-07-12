package co.th.aten.network.model;

import java.io.Serializable;
import java.util.Date;

public class TransferReportByMonthModel implements Serializable {
	private int index;
	private Date trxDate;
	private Double amount;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Date getTrxDate() {
		return trxDate;
	}
	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
}
