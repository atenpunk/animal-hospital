/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.network.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "transaction_sell_detail")
@NamedQueries({
    @NamedQuery(name = "TransactionSellDetail.findAll", query = "SELECT t FROM TransactionSellDetail t")})
public class TransactionSellDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "trx_detail_id")
    private Integer trxDetailId;
    @Basic(optional = false)
    @Column(name = "trx_header_id")
    private int trxHeaderId;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "catalog_id")
    private Integer catalogId;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "pv")
    private BigDecimal pv;
    @Column(name = "bv")
    private BigDecimal bv;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "total_pv")
    private BigDecimal totalPv;
    @Column(name = "total_bv")
    private BigDecimal totalBv;
    @Column(name = "qty")
    private Integer qty;
    @Basic(optional = false)
    @Column(name = "qty_assign")
    private int qtyAssign;
    @Lob
    @Column(name = "remark")
    private String remark;
    @Column(name = "trx_detail_status")
    private Character trxDetailStatus;
    @Column(name = "trx_detail_flag")
    private Character trxDetailFlag;
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

    public TransactionSellDetail() {
    }

    public TransactionSellDetail(Integer trxDetailId) {
        this.trxDetailId = trxDetailId;
    }

    public TransactionSellDetail(Integer trxDetailId, int trxHeaderId, int qtyAssign) {
        this.trxDetailId = trxDetailId;
        this.trxHeaderId = trxHeaderId;
        this.qtyAssign = qtyAssign;
    }

    public Integer getTrxDetailId() {
        return trxDetailId;
    }

    public void setTrxDetailId(Integer trxDetailId) {
        this.trxDetailId = trxDetailId;
    }

    public int getTrxHeaderId() {
        return trxHeaderId;
    }

    public void setTrxHeaderId(int trxHeaderId) {
        this.trxHeaderId = trxHeaderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPv() {
        return totalPv;
    }

    public void setTotalPv(BigDecimal totalPv) {
        this.totalPv = totalPv;
    }

    public BigDecimal getTotalBv() {
        return totalBv;
    }

    public void setTotalBv(BigDecimal totalBv) {
        this.totalBv = totalBv;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public int getQtyAssign() {
        return qtyAssign;
    }

    public void setQtyAssign(int qtyAssign) {
        this.qtyAssign = qtyAssign;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Character getTrxDetailStatus() {
        return trxDetailStatus;
    }

    public void setTrxDetailStatus(Character trxDetailStatus) {
        this.trxDetailStatus = trxDetailStatus;
    }

    public Character getTrxDetailFlag() {
        return trxDetailFlag;
    }

    public void setTrxDetailFlag(Character trxDetailFlag) {
        this.trxDetailFlag = trxDetailFlag;
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
        hash += (trxDetailId != null ? trxDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionSellDetail)) {
            return false;
        }
        TransactionSellDetail other = (TransactionSellDetail) object;
        if ((this.trxDetailId == null && other.trxDetailId != null) || (this.trxDetailId != null && !this.trxDetailId.equals(other.trxDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.TransactionSellDetail[trxDetailId=" + trxDetailId + "]";
    }

}
