/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.network.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Atenpunk
 */
@Entity
@Table(name = "stock_catalog")
@NamedQueries({
    @NamedQuery(name = "StockCatalog.findAll", query = "SELECT s FROM StockCatalog s"),
    @NamedQuery(name = "StockCatalog.findByCatalogId", query = "SELECT s FROM StockCatalog s WHERE s.catalogId = :catalogId"),
    @NamedQuery(name = "StockCatalog.findByCatalogCode", query = "SELECT s FROM StockCatalog s WHERE s.catalogCode = :catalogCode"),
    @NamedQuery(name = "StockCatalog.findByThDesc", query = "SELECT s FROM StockCatalog s WHERE s.thDesc = :thDesc"),
    @NamedQuery(name = "StockCatalog.findByEnDesc", query = "SELECT s FROM StockCatalog s WHERE s.enDesc = :enDesc"),
    @NamedQuery(name = "StockCatalog.findByCreateBy", query = "SELECT s FROM StockCatalog s WHERE s.createBy = :createBy"),
    @NamedQuery(name = "StockCatalog.findByCreateDate", query = "SELECT s FROM StockCatalog s WHERE s.createDate = :createDate"),
    @NamedQuery(name = "StockCatalog.findByUpdateBy", query = "SELECT s FROM StockCatalog s WHERE s.updateBy = :updateBy"),
    @NamedQuery(name = "StockCatalog.findByUpdateDate", query = "SELECT s FROM StockCatalog s WHERE s.updateDate = :updateDate")})
public class StockCatalog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "catalog_id")
    private Integer catalogId;
    @Column(name = "catalog_code")
    private String catalogCode;
    @Column(name = "th_desc")
    private String thDesc;
    @Column(name = "en_desc")
    private String enDesc;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stockCatalog")
    private Collection<StockProduct> stockProductCollection;

    public StockCatalog() {
    }

    public StockCatalog(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
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

    public Collection<StockProduct> getStockProductCollection() {
        return stockProductCollection;
    }

    public void setStockProductCollection(Collection<StockProduct> stockProductCollection) {
        this.stockProductCollection = stockProductCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catalogId != null ? catalogId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StockCatalog)) {
            return false;
        }
        StockCatalog other = (StockCatalog) object;
        if ((this.catalogId == null && other.catalogId != null) || (this.catalogId != null && !this.catalogId.equals(other.catalogId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.StockCatalog[catalogId=" + catalogId + "]";
    }

}
