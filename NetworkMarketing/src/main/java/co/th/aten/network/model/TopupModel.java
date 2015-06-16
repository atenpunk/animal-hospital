package co.th.aten.network.model;

import java.io.Serializable;

public class TopupModel implements Serializable {
   private boolean itemSelect;
   private String name;
   private String lastName;
   private String idCardNo;
   private String telNo;
   private String vehiclePlateNo;
   private String rfid;
   private double topupAmount;
   private String subscriberId;
   private String addr;
   private String msg;
   private String check;
   
public String getAddr() {
	return addr;
}
public void setAddr(String addr) {
	this.addr = addr;
}
public boolean isItemSelect() {
	return itemSelect;
}
public void setItemSelect(boolean itemSelect) {
	this.itemSelect = itemSelect;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getIdCardNo() {
	return idCardNo;
}
public void setIdCardNo(String idCardNo) {
	this.idCardNo = idCardNo;
}
public String getTelNo() {
	return telNo;
}
public void setTelNo(String telNo) {
	this.telNo = telNo;
}
public String getVehiclePlateNo() {
	return vehiclePlateNo;
}
public void setVehiclePlateNo(String vehiclePlateNo) {
	this.vehiclePlateNo = vehiclePlateNo;
}
public String getRfid() {
	return rfid;
}
public void setRfid(String rfid) {
	this.rfid = rfid;
}
public double getTopupAmount() {
	return topupAmount;
}
public void setTopupAmount(double topupAmount) {
	this.topupAmount = topupAmount;
}
public String getSubscriberId() {
	return subscriberId;
}
public void setSubscriberId(String subscriberId) {
	this.subscriberId = subscriberId;
}
public String getMsg() {
	return msg;
}
public void setMsg(String msg) {
	this.msg = msg;
}
public String getCheck() {
	return check;
}
public void setCheck(String check) {
	this.check = check;
}

   
}
