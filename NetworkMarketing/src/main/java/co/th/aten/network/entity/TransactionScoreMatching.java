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
@Table(name = "transaction_score_matching")
@NamedQueries({
    @NamedQuery(name = "TransactionScoreMatching.findAll", query = "SELECT t FROM TransactionScoreMatching t"),
    @NamedQuery(name = "TransactionScoreMatching.findByTrxMatchingDate", query = "SELECT t FROM TransactionScoreMatching t WHERE t.transactionScoreMatchingPK.trxMatchingDate = :trxMatchingDate"),
    @NamedQuery(name = "TransactionScoreMatching.findByCustomerId", query = "SELECT t FROM TransactionScoreMatching t WHERE t.transactionScoreMatchingPK.customerId = :customerId"),
    @NamedQuery(name = "TransactionScoreMatching.findByTotalPvLeft", query = "SELECT t FROM TransactionScoreMatching t WHERE t.totalPvLeft = :totalPvLeft"),
    @NamedQuery(name = "TransactionScoreMatching.findByTotalPvRight", query = "SELECT t FROM TransactionScoreMatching t WHERE t.totalPvRight = :totalPvRight"),
    @NamedQuery(name = "TransactionScoreMatching.findByTotalPvMatching", query = "SELECT t FROM TransactionScoreMatching t WHERE t.totalPvMatching = :totalPvMatching"),
    @NamedQuery(name = "TransactionScoreMatching.findByMatchingUse", query = "SELECT t FROM TransactionScoreMatching t WHERE t.matchingUse = :matchingUse"),
    @NamedQuery(name = "TransactionScoreMatching.findByMatchingBalance", query = "SELECT t FROM TransactionScoreMatching t WHERE t.matchingBalance = :matchingBalance"),
    @NamedQuery(name = "TransactionScoreMatching.findByTrxMatchingStatus", query = "SELECT t FROM TransactionScoreMatching t WHERE t.trxMatchingStatus = :trxMatchingStatus"),
    @NamedQuery(name = "TransactionScoreMatching.findByTrxMatchingFlag", query = "SELECT t FROM TransactionScoreMatching t WHERE t.trxMatchingFlag = :trxMatchingFlag"),
    @NamedQuery(name = "TransactionScoreMatching.findByCreateBy", query = "SELECT t FROM TransactionScoreMatching t WHERE t.createBy = :createBy"),
    @NamedQuery(name = "TransactionScoreMatching.findByCreateDate", query = "SELECT t FROM TransactionScoreMatching t WHERE t.createDate = :createDate"),
    @NamedQuery(name = "TransactionScoreMatching.findByUpdateBy", query = "SELECT t FROM TransactionScoreMatching t WHERE t.updateBy = :updateBy"),
    @NamedQuery(name = "TransactionScoreMatching.findByUpdateDate", query = "SELECT t FROM TransactionScoreMatching t WHERE t.updateDate = :updateDate")})
public class TransactionScoreMatching implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TransactionScoreMatchingPK transactionScoreMatchingPK;
    @Column(name = "total_pv_left")
    private BigDecimal totalPvLeft;
    @Column(name = "total_pv_right")
    private BigDecimal totalPvRight;
    @Column(name = "total_pv_matching")
    private BigDecimal totalPvMatching;
    @Column(name = "matching_use")
    private Integer matchingUse;
    @Column(name = "matching_balance")
    private Integer matchingBalance;
    @Column(name = "trx_matching_status")
    private Integer trxMatchingStatus;
    @Column(name = "trx_matching_flag")
    private Integer trxMatchingFlag;
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

    public TransactionScoreMatching() {
    }

    public TransactionScoreMatching(TransactionScoreMatchingPK transactionScoreMatchingPK) {
        this.transactionScoreMatchingPK = transactionScoreMatchingPK;
    }

    public TransactionScoreMatching(Date trxMatchingDate, int customerId) {
        this.transactionScoreMatchingPK = new TransactionScoreMatchingPK(trxMatchingDate, customerId);
    }

    public TransactionScoreMatchingPK getTransactionScoreMatchingPK() {
        return transactionScoreMatchingPK;
    }

    public void setTransactionScoreMatchingPK(TransactionScoreMatchingPK transactionScoreMatchingPK) {
        this.transactionScoreMatchingPK = transactionScoreMatchingPK;
    }

    public BigDecimal getTotalPvLeft() {
        return totalPvLeft;
    }

    public void setTotalPvLeft(BigDecimal totalPvLeft) {
        this.totalPvLeft = totalPvLeft;
    }

    public BigDecimal getTotalPvRight() {
        return totalPvRight;
    }

    public void setTotalPvRight(BigDecimal totalPvRight) {
        this.totalPvRight = totalPvRight;
    }

    public BigDecimal getTotalPvMatching() {
        return totalPvMatching;
    }

    public void setTotalPvMatching(BigDecimal totalPvMatching) {
        this.totalPvMatching = totalPvMatching;
    }

    public Integer getMatchingUse() {
        return matchingUse;
    }

    public void setMatchingUse(Integer matchingUse) {
        this.matchingUse = matchingUse;
    }

    public Integer getMatchingBalance() {
        return matchingBalance;
    }

    public void setMatchingBalance(Integer matchingBalance) {
        this.matchingBalance = matchingBalance;
    }

    public Integer getTrxMatchingStatus() {
        return trxMatchingStatus;
    }

    public void setTrxMatchingStatus(Integer trxMatchingStatus) {
        this.trxMatchingStatus = trxMatchingStatus;
    }

    public Integer getTrxMatchingFlag() {
        return trxMatchingFlag;
    }

    public void setTrxMatchingFlag(Integer trxMatchingFlag) {
        this.trxMatchingFlag = trxMatchingFlag;
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
        hash += (transactionScoreMatchingPK != null ? transactionScoreMatchingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionScoreMatching)) {
            return false;
        }
        TransactionScoreMatching other = (TransactionScoreMatching) object;
        if ((this.transactionScoreMatchingPK == null && other.transactionScoreMatchingPK != null) || (this.transactionScoreMatchingPK != null && !this.transactionScoreMatchingPK.equals(other.transactionScoreMatchingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.TransactionScoreMatching[transactionScoreMatchingPK=" + transactionScoreMatchingPK + "]";
    }

}
