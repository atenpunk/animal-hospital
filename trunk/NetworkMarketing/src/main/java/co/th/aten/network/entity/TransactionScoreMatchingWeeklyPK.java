/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.network.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Atenpunk
 */
@Embeddable
public class TransactionScoreMatchingWeeklyPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "round_id")
    private int roundId;
    @Basic(optional = false)
    @Column(name = "trx_start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trxStartDate;
    @Basic(optional = false)
    @Column(name = "trx_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trxEndDate;
    @Basic(optional = false)
    @Column(name = "customer_id")
    private int customerId;

    public TransactionScoreMatchingWeeklyPK() {
    }

    public TransactionScoreMatchingWeeklyPK(int roundId, Date trxStartDate, Date trxEndDate, int customerId) {
        this.roundId = roundId;
        this.trxStartDate = trxStartDate;
        this.trxEndDate = trxEndDate;
        this.customerId = customerId;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) roundId;
        hash += (trxStartDate != null ? trxStartDate.hashCode() : 0);
        hash += (trxEndDate != null ? trxEndDate.hashCode() : 0);
        hash += (int) customerId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionScoreMatchingWeeklyPK)) {
            return false;
        }
        TransactionScoreMatchingWeeklyPK other = (TransactionScoreMatchingWeeklyPK) object;
        if (this.roundId != other.roundId) {
            return false;
        }
        if ((this.trxStartDate == null && other.trxStartDate != null) || (this.trxStartDate != null && !this.trxStartDate.equals(other.trxStartDate))) {
            return false;
        }
        if ((this.trxEndDate == null && other.trxEndDate != null) || (this.trxEndDate != null && !this.trxEndDate.equals(other.trxEndDate))) {
            return false;
        }
        if (this.customerId != other.customerId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.TransactionScoreMatchingWeeklyPK[roundId=" + roundId + ", trxStartDate=" + trxStartDate + ", trxEndDate=" + trxEndDate + ", customerId=" + customerId + "]";
    }

}
