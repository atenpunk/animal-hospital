package co.th.aten.network.model;

import java.io.Serializable;


public class CategoryModel implements Serializable {
	
	private int catalogId;
	private String code;
	private String thDesc;
	private String enDesc;
	
	public int getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(int catalogId) {
		this.catalogId = catalogId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getThDesc() {
		return thDesc;
	}
	public void setThDesc(String thDesc) {
		this.thDesc = thDesc;
	}
	public String getEnDesc() {
		return enDesc;
	}
	public void setEnDesc(String enDesc) {
		this.enDesc = enDesc;
	}
}
