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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
    @NamedQuery(name = "TransactionSellDetail.findAll", query = "SELECT t FROM TransactionSellDetail t"),
    @NamedQuery(name = "TransactionSellDetail.findByTrxDetailId", query = "SELECT t FROM TransactionSellDetail t WHERE t.transactionSellDetailPK.trxDetailId = :trxDetailId"),
    @NamedQuery(name = "TransactionSellDetail.findByTrxHeaderId", query = "SELECT t FROM TransactionSellDetail t WHERE t.transactionSellDetailPK.trxHeaderId = :trxHeaderId"),
    @NamedQuery(name = "TransactionSellDetail.findByProductId", query = "SELECT t FROM TransactionSellDetail t WHERE t.productId = :productId"),
    @NamedQuery(name = "TransactionSellDetail.findByPrice", query = "SELECT t FROM TransactionSellDetail t WHERE t.price = :price"),
    @NamedQuery(name = "TransactionSellDetail.findByPv", query = "SELECT t FROM TransactionSellDetail t WHERE t.pv = :pv"),
    @NamedQuery(name = "TransactionSellDetail.findByBv", query = "SELECT t FROM TransactionSellDetail t WHERE t.bv = :bv"),
    @NamedQuery(name = "TransactionSellDetail.findByTotalPrice", query = "SELECT t FROM TransactionSellDetail t WHERE t.totalPrice = :totalPrice"),
    @NamedQuery(name = "TransactionSellDetail.findByTotalPv", query = "SELECT t FROM TransactionSellDetail t WHERE t.totalPv = :totalPv"),
    @NamedQuery(name = "TransactionSellDetail.findByTotalBv", query = "SELECT t FROM TransactionSellDetail t WHERE t.totalBv = :totalBv"),
    @NamedQuery(name = "TransactionSellDetail.findByQty", query = "SELECT t FROM TransactionSellDetail t WHERE t.qty = :qty"),
    @NamedQuery(name = "TransactionSellDetail.findByQtyAssign", query = "SELECT t FROM TransactionSellDetail t WHERE t.qtyAssign = :qtyAssign"),
    @NamedQuery(name = "TransactionSellDetail.findByTrxDetailStatus", query = "SELECT t FROM TransactionSellDetail t WHERE t.trxDetailStatus = :trxDetailStatus"),
    @NamedQuery(name = "TransactionSellDetail.findByTrxDetailFlag", query = "SELECT t FROM TransactionSellDetail t WHERE t.trxDetailFlag = :trxDetailFlag"),
    @NamedQuery(name = "TransactionSellDetail.findByCreateBy", query = "SELECT t FROM TransactionSellDetail t WHERE t.createBy = :createBy"),
    @NamedQuery(name = "TransactionSellDetail.findByCreateDate", query = "SELECT t FROM TransactionSellDetail t WHERE t.createDate = :createDate"),
    @NamedQuery(name = "TransactionSellDetail.findByUpdateBy", query = "SELECT t FROM TransactionSellDetail t WHERE t.updateBy = :updateBy"),
    @NamedQuery(name = "TransactionSellDetail.findByUpdateDate", query = "SELECT t FROM TransactionSellDetail t WHERE t.updateDate = :updateDate")})
public class TransactionSellDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TransactionSellDetailPK transactionSellDetailPK;
    @Column(name = "product_id")
    private Integer productId;
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

    public TransactionSellDetail(TransactionSellDetailPK transactionSellDetailPK) {
        this.transactionSellDetailPK = transactionSellDetailPK;
    }

    public TransactionSellDetail(TransactionSellDetailPK transactionSellDetailPK, int qtyAssign) {
        this.transactionSellDetailPK = transactionSellDetailPK;
        this.qtyAssign = qtyAssign;
    }

    public TransactionSellDetail(int trxDetailId, int trxHeaderId) {
        this.transactionSellDetailPK = new TransactionSellDetailPK(trxDetailId, trxHeaderId);
    }

    public TransactionSellDetailPK getTransactionSellDetailPK() {
        return transactionSellDetailPK;
    }

    public void setTransactionSellDetailPK(TransactionSellDetailPK transactionSellDetailPK) {
        this.transactionSellDetailPK = transactionSellDetailPK;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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
        hash += (transactionSellDetailPK != null ? transactionSellDetailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionSellDetail)) {
            return false;
        }
        TransactionSellDetail other = (TransactionSellDetail) object;
        if ((this.transactionSellDetailPK == null && other.transactionSellDetailPK != null) || (this.transactionSellDetailPK != null && !this.transactionSellDetailPK.equals(other.transactionSellDetailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.TransactionSellDetail[transactionSellDetailPK=" + transactionSellDetailPK + "]";
    }

}
