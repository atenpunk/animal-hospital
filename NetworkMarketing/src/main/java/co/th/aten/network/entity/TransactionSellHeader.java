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
@Table(name = "transaction_sell_header")
@NamedQueries({
    @NamedQuery(name = "TransactionSellHeader.findAll", query = "SELECT t FROM TransactionSellHeader t")})
public class TransactionSellHeader implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "trx_header_id")
    private Integer trxHeaderId;
    @Column(name = "trx_header_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trxHeaderDatetime;
    @Column(name = "receipt_no")
    private String receiptNo;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "total_pv")
    private BigDecimal totalPv;
    @Column(name = "total_bv")
    private BigDecimal totalBv;
    @Lob
    @Column(name = "remark")
    private String remark;
    @Column(name = "trx_header_status")
    private Character trxHeaderStatus;
    @Column(name = "trx_header_flag")
    private Character trxHeaderFlag;
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

    public TransactionSellHeader() {
    }

    public TransactionSellHeader(Integer trxHeaderId) {
        this.trxHeaderId = trxHeaderId;
    }

    public Integer getTrxHeaderId() {
        return trxHeaderId;
    }

    public void setTrxHeaderId(Integer trxHeaderId) {
        this.trxHeaderId = trxHeaderId;
    }

    public Date getTrxHeaderDatetime() {
        return trxHeaderDatetime;
    }

    public void setTrxHeaderDatetime(Date trxHeaderDatetime) {
        this.trxHeaderDatetime = trxHeaderDatetime;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Character getTrxHeaderStatus() {
        return trxHeaderStatus;
    }

    public void setTrxHeaderStatus(Character trxHeaderStatus) {
        this.trxHeaderStatus = trxHeaderStatus;
    }

    public Character getTrxHeaderFlag() {
        return trxHeaderFlag;
    }

    public void setTrxHeaderFlag(Character trxHeaderFlag) {
        this.trxHeaderFlag = trxHeaderFlag;
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
        hash += (trxHeaderId != null ? trxHeaderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionSellHeader)) {
            return false;
        }
        TransactionSellHeader other = (TransactionSellHeader) object;
        if ((this.trxHeaderId == null && other.trxHeaderId != null) || (this.trxHeaderId != null && !this.trxHeaderId.equals(other.trxHeaderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.TransactionSellHeader[trxHeaderId=" + trxHeaderId + "]";
    }

}
