package co.th.aten.network.model;

import java.awt.Image;
import java.io.Serializable;


public class ProductModel implements Serializable {
	
	private int index;
	private int productId;
	private int catalogId;
	private String productCode;
	private String productThDesc;
	private String productEnDesc;
	private String unit;
	private double price;
	private double pv;
	private double bv;
	private String pathImage;
	private int qty;
	private double totalPrice;
	private double totalPv;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(int catalogId) {
		this.catalogId = catalogId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductThDesc() {
		return productThDesc;
	}
	public void setProductThDesc(String productThDesc) {
		this.productThDesc = productThDesc;
	}
	public String getProductEnDesc() {
		return productEnDesc;
	}
	public void setProductEnDesc(String productEnDesc) {
		this.productEnDesc = productEnDesc;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getPv() {
		return pv;
	}
	public void setPv(double pv) {
		this.pv = pv;
	}
	public double getBv() {
		return bv;
	}
	public void setBv(double bv) {
		this.bv = bv;
	}
	public String getPathImage() {
		return pathImage;
	}
	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
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
	
}
