/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.network.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "stock_product_type")
@NamedQueries({
    @NamedQuery(name = "StockProductType.findAll", query = "SELECT s FROM StockProductType s"),
    @NamedQuery(name = "StockProductType.findByTypeId", query = "SELECT s FROM StockProductType s WHERE s.typeId = :typeId"),
    @NamedQuery(name = "StockProductType.findByTypeDescTh", query = "SELECT s FROM StockProductType s WHERE s.typeDescTh = :typeDescTh"),
    @NamedQuery(name = "StockProductType.findByTypeDescEn", query = "SELECT s FROM StockProductType s WHERE s.typeDescEn = :typeDescEn"),
    @NamedQuery(name = "StockProductType.findByCreateBy", query = "SELECT s FROM StockProductType s WHERE s.createBy = :createBy"),
    @NamedQuery(name = "StockProductType.findByCreateDate", query = "SELECT s FROM StockProductType s WHERE s.createDate = :createDate"),
    @NamedQuery(name = "StockProductType.findByUpdateBy", query = "SELECT s FROM StockProductType s WHERE s.updateBy = :updateBy"),
    @NamedQuery(name = "StockProductType.findByUpdateDate", query = "SELECT s FROM StockProductType s WHERE s.updateDate = :updateDate")})
public class StockProductType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "type_id")
    private Integer typeId;
    @Column(name = "type_desc_th")
    private String typeDescTh;
    @Column(name = "type_desc_en")
    private String typeDescEn;
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

    public StockProductType() {
    }

    public StockProductType(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeDescTh() {
        return typeDescTh;
    }

    public void setTypeDescTh(String typeDescTh) {
        this.typeDescTh = typeDescTh;
    }

    public String getTypeDescEn() {
        return typeDescEn;
    }

    public void setTypeDescEn(String typeDescEn) {
        this.typeDescEn = typeDescEn;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (typeId != null ? typeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StockProductType)) {
            return false;
        }
        StockProductType other = (StockProductType) object;
        if ((this.typeId == null && other.typeId != null) || (this.typeId != null && !this.typeId.equals(other.typeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.StockProductType[typeId=" + typeId + "]";
    }

}
