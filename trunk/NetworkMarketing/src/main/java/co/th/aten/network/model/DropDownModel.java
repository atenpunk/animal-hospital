package co.th.aten.network.model;

import java.io.Serializable;

public class DropDownModel implements Serializable {
	
	private int intKey;
	private String thLabel;
	private String enLabel;
	
	public int getIntKey() {
		return intKey;
	}
	public void setIntKey(int intKey) {
		this.intKey = intKey;
	}
	public String getThLabel() {
		return thLabel;
	}
	public void setThLabel(String thLabel) {
		this.thLabel = thLabel;
	}
	public String getEnLabel() {
		return enLabel;
	}
	public void setEnLabel(String enLabel) {
		this.enLabel = enLabel;
	}
	
	
}
