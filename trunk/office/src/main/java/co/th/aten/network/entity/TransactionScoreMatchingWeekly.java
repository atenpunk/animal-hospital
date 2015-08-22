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
@Table(name = "transaction_score_matching_weekly")
@NamedQueries({
    @NamedQuery(name = "TransactionScoreMatchingWeekly.findAll", query = "SELECT t FROM TransactionScoreMatchingWeekly t")})
public class TransactionScoreMatchingWeekly implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TransactionScoreMatchingWeeklyPK transactionScoreMatchingWeeklyPK;
    @Column(name = "old_pv_left")
    private BigDecimal oldPvLeft;
    @Column(name = "old_pv_right")
    private BigDecimal oldPvRight;
    @Column(name = "date_pv_left")
    private BigDecimal datePvLeft;
    @Column(name = "date_pv_right")
    private BigDecimal datePvRight;
    @Column(name = "total_pv_left")
    private BigDecimal totalPvLeft;
    @Column(name = "total_pv_right")
    private BigDecimal totalPvRight;
    @Column(name = "remaining_pv_left")
    private BigDecimal remainingPvLeft;
    @Column(name = "remaining_pv_right")
    private BigDecimal remainingPvRight;
    @Column(name = "matching_pv")
    private BigDecimal matchingPv;
    @Column(name = "self_date_pv")
    private BigDecimal selfDatePv;
    @Column(name = "self_total_pv")
    private BigDecimal selfTotalPv;
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

    public TransactionScoreMatchingWeekly() {
    }

    public TransactionScoreMatchingWeekly(TransactionScoreMatchingWeeklyPK transactionScoreMatchingWeeklyPK) {
        this.transactionScoreMatchingWeeklyPK = transactionScoreMatchingWeeklyPK;
    }

    public TransactionScoreMatchingWeekly(int roundId, Date trxStartDate, Date trxEndDate, int customerId) {
        this.transactionScoreMatchingWeeklyPK = new TransactionScoreMatchingWeeklyPK(roundId, trxStartDate, trxEndDate, customerId);
    }

    public TransactionScoreMatchingWeeklyPK getTransactionScoreMatchingWeeklyPK() {
        return transactionScoreMatchingWeeklyPK;
    }

    public void setTransactionScoreMatchingWeeklyPK(TransactionScoreMatchingWeeklyPK transactionScoreMatchingWeeklyPK) {
        this.transactionScoreMatchingWeeklyPK = transactionScoreMatchingWeeklyPK;
    }

    public BigDecimal getOldPvLeft() {
        return oldPvLeft;
    }

    public void setOldPvLeft(BigDecimal oldPvLeft) {
        this.oldPvLeft = oldPvLeft;
    }

    public BigDecimal getOldPvRight() {
        return oldPvRight;
    }

    public void setOldPvRight(BigDecimal oldPvRight) {
        this.oldPvRight = oldPvRight;
    }

    public BigDecimal getDatePvLeft() {
        return datePvLeft;
    }

    public void setDatePvLeft(BigDecimal datePvLeft) {
        this.datePvLeft = datePvLeft;
    }

    public BigDecimal getDatePvRight() {
        return datePvRight;
    }

    public void setDatePvRight(BigDecimal datePvRight) {
        this.datePvRight = datePvRight;
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

    public BigDecimal getRemainingPvLeft() {
        return remainingPvLeft;
    }

    public void setRemainingPvLeft(BigDecimal remainingPvLeft) {
        this.remainingPvLeft = remainingPvLeft;
    }

    public BigDecimal getRemainingPvRight() {
        return remainingPvRight;
    }

    public void setRemainingPvRight(BigDecimal remainingPvRight) {
        this.remainingPvRight = remainingPvRight;
    }

    public BigDecimal getMatchingPv() {
        return matchingPv;
    }

    public void setMatchingPv(BigDecimal matchingPv) {
        this.matchingPv = matchingPv;
    }

    public BigDecimal getSelfDatePv() {
        return selfDatePv;
    }

    public void setSelfDatePv(BigDecimal selfDatePv) {
        this.selfDatePv = selfDatePv;
    }

    public BigDecimal getSelfTotalPv() {
        return selfTotalPv;
    }

    public void setSelfTotalPv(BigDecimal selfTotalPv) {
        this.selfTotalPv = selfTotalPv;
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
        hash += (transactionScoreMatchingWeeklyPK != null ? transactionScoreMatchingWeeklyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionScoreMatchingWeekly)) {
            return false;
        }
        TransactionScoreMatchingWeekly other = (TransactionScoreMatchingWeekly) object;
        if ((this.transactionScoreMatchingWeeklyPK == null && other.transactionScoreMatchingWeeklyPK != null) || (this.transactionScoreMatchingWeeklyPK != null && !this.transactionScoreMatchingWeeklyPK.equals(other.transactionScoreMatchingWeeklyPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.TransactionScoreMatchingWeekly[transactionScoreMatchingWeeklyPK=" + transactionScoreMatchingWeeklyPK + "]";
    }

}
