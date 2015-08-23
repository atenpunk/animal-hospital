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
@Table(name = "transaction_score_package_weekly")
@NamedQueries({
    @NamedQuery(name = "TransactionScorePackageWeekly.findAll", query = "SELECT t FROM TransactionScorePackageWeekly t")})
public class TransactionScorePackageWeekly implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TransactionScorePackageWeeklyPK transactionScorePackageWeeklyPK;
    @Column(name = "trx_start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trxStartDate;
    @Column(name = "trx_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trxEndDate;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "pv_package")
    private BigDecimal pvPackage;
    @Column(name = "trx_package_status")
    private Integer trxPackageStatus;
    @Column(name = "trx_package_flag")
    private Integer trxPackageFlag;
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

    public TransactionScorePackageWeekly() {
    }

    public TransactionScorePackageWeekly(TransactionScorePackageWeeklyPK transactionScorePackageWeeklyPK) {
        this.transactionScorePackageWeeklyPK = transactionScorePackageWeeklyPK;
    }

    public TransactionScorePackageWeekly(int roundId, int customerId, int suggestId) {
        this.transactionScorePackageWeeklyPK = new TransactionScorePackageWeeklyPK(roundId, customerId, suggestId);
    }

    public TransactionScorePackageWeeklyPK getTransactionScorePackageWeeklyPK() {
        return transactionScorePackageWeeklyPK;
    }

    public void setTransactionScorePackageWeeklyPK(TransactionScorePackageWeeklyPK transactionScorePackageWeeklyPK) {
        this.transactionScorePackageWeeklyPK = transactionScorePackageWeeklyPK;
    }

    public Date getTrxStartDate() {
        return trxStartDate;
    }

    public void setTrxStartDate(Date trxStartDate) {
        this.trxStartDate = trxStartDate;
    }

    public Date getTrxEndDate() {
        return trxEndDate;
    }

    public void setTrxEndDate(Date trxEndDate) {
        this.trxEndDate = trxEndDate;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getPvPackage() {
        return pvPackage;
    }

    public void setPvPackage(BigDecimal pvPackage) {
        this.pvPackage = pvPackage;
    }

    public Integer getTrxPackageStatus() {
        return trxPackageStatus;
    }

    public void setTrxPackageStatus(Integer trxPackageStatus) {
        this.trxPackageStatus = trxPackageStatus;
    }

    public Integer getTrxPackageFlag() {
        return trxPackageFlag;
    }

    public void setTrxPackageFlag(Integer trxPackageFlag) {
        this.trxPackageFlag = trxPackageFlag;
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
        hash += (transactionScorePackageWeeklyPK != null ? transactionScorePackageWeeklyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionScorePackageWeekly)) {
            return false;
        }
        TransactionScorePackageWeekly other = (TransactionScorePackageWeekly) object;
        if ((this.transactionScorePackageWeeklyPK == null && other.transactionScorePackageWeeklyPK != null) || (this.transactionScorePackageWeeklyPK != null && !this.transactionScorePackageWeeklyPK.equals(other.transactionScorePackageWeeklyPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.TransactionScorePackageWeekly[transactionScorePackageWeeklyPK=" + transactionScorePackageWeeklyPK + "]";
    }

}
