package co.th.aten.network.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class TrxProductModel implements Serializable {
	
	private int headerId;
	private Date trxDateTime;
	private String receiptNo;
	private int productId;
	private String productCode;
	private String productDesc;
	private String memberCode;
	private int memberId;
	private String memberName;
	private String memberCodeKey;
	private double totalPrice;
	private double totalPv;
	private int statusId;
	private String status;
	private List<DropDownModel> trxStatusList;
	private List<ProductModel> productModelList;
	
	public int getHeaderId() {
		return headerId;
	}
	public void setHeaderId(int headerId) {
		this.headerId = headerId;
	}
	public Date getTrxDateTime() {
		return trxDateTime;
	}
	public void setTrxDateTime(Date trxDateTime) {
		this.trxDateTime = trxDateTime;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
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
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public double getTotalPv() {
		return totalPv;
	}
	public void setTotalPv(double totalPv) {
		this.totalPv = totalPv;
	}
	public String getMemberCodeKey() {
		return memberCodeKey;
	}
	public void setMemberCodeKey(String memberCodeKey) {
		this.memberCodeKey = memberCodeKey;
	}
	public List<ProductModel> getProductModelList() {
		return productModelList;
	}
	public void setProductModelList(List<ProductModel> productModelList) {
		this.productModelList = productModelList;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<DropDownModel> getTrxStatusList() {
		return trxStatusList;
	}
	public void setTrxStatusList(List<DropDownModel> trxStatusList) {
		this.trxStatusList = trxStatusList;
	}
	
}
