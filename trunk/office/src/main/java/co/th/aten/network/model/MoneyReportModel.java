package co.th.aten.network.model;

import java.io.Serializable;
import java.util.Date;


public class MoneyReportModel implements Serializable {
	
	private String memberCode;
	private String memberName;
	private Date date;
	private double deduct;
	private double add;
	private double balance;
	private String remark;
	private String trxBy;
	private Date trxTime;
	
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getDeduct() {
		return deduct;
	}
	public void setDeduct(double deduct) {
		this.deduct = deduct;
	}
	public double getAdd() {
		return add;
	}
	public void setAdd(double add) {
		this.add = add;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTrxBy() {
		return trxBy;
	}
	public void setTrxBy(String trxBy) {
		this.trxBy = trxBy;
	}
	public Date getTrxTime() {
		return trxTime;
	}
	public void setTrxTime(Date trxTime) {
		this.trxTime = trxTime;
	}
	
	
}
