/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.network.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Atenpunk
 */
@Entity
@Table(name = "stock_product")
@NamedQueries({
    @NamedQuery(name = "StockProduct.findAll", query = "SELECT s FROM StockProduct s")})
public class StockProduct implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StockProductPK stockProductPK;
    @Column(name = "product_code")
    private String productCode;
    @Column(name = "th_desc")
    private String thDesc;
    @Column(name = "en_desc")
    private String enDesc;
    @Column(name = "unit")
    private String unit;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "pv")
    private BigDecimal pv;
    @Column(name = "bv")
    private BigDecimal bv;
    @Column(name = "qty")
    private Integer qty;
    @Column(name = "company_id")
    private Integer companyId;
    @Column(name = "package_id")
    private Integer packageId;
    @Column(name = "create_by")
    private Integer createBy;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "update_by")
    private Integer updateBy;
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Column(name = "image_name")
    private String imageName;
    @JoinColumn(name = "catalog_id", referencedColumnName = "catalog_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private StockCatalog stockCatalog;

    public StockProduct() {
    }

    public StockProduct(StockProductPK stockProductPK) {
        this.stockProductPK = stockProductPK;
    }

    public StockProduct(int productId, int catalogId) {
        this.stockProductPK = new StockProductPK(productId, catalogId);
    }

    public StockProductPK getStockProductPK() {
        return stockProductPK;
    }

    public void setStockProductPK(StockProductPK stockProductPK) {
        this.stockProductPK = stockProductPK;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPv() {
        return pv;
    }

    public void setPv(BigDecimal pv) {
        this.pv = pv;
    }

    public BigDecimal getBv() {
        return bv;
    }

    public void setBv(BigDecimal bv) {
        this.bv = bv;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public StockCatalog getStockCatalog() {
        return stockCatalog;
    }

    public void setStockCatalog(StockCatalog stockCatalog) {
        this.stockCatalog = stockCatalog;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stockProductPK != null ? stockProductPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StockProduct)) {
            return false;
        }
        StockProduct other = (StockProduct) object;
        if ((this.stockProductPK == null && other.stockProductPK != null) || (this.stockProductPK != null && !this.stockProductPK.equals(other.stockProductPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.StockProduct[stockProductPK=" + stockProductPK + "]";
    }

}
