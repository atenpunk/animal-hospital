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
public class TransactionScoreMatchingPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "round_id")
    private int roundId;
    @Basic(optional = false)
    @Column(name = "trx_matching_date")
    @Temporal(TemporalType.DATE)
    private Date trxMatchingDate;
    @Basic(optional = false)
    @Column(name = "customer_id")
    private int customerId;

    public TransactionScoreMatchingPK() {
    }

    public TransactionScoreMatchingPK(int roundId, Date trxMatchingDate, int customerId) {
        this.roundId = roundId;
        this.trxMatchingDate = trxMatchingDate;
        this.customerId = customerId;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public Date getTrxMatchingDate() {
        return trxMatchingDate;
    }

    public void setTrxMatchingDate(Date trxMatchingDate) {
        this.trxMatchingDate = trxMatchingDate;
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
        hash += (trxMatchingDate != null ? trxMatchingDate.hashCode() : 0);
        hash += (int) customerId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionScoreMatchingPK)) {
            return false;
        }
        TransactionScoreMatchingPK other = (TransactionScoreMatchingPK) object;
        if (this.roundId != other.roundId) {
            return false;
        }
        if ((this.trxMatchingDate == null && other.trxMatchingDate != null) || (this.trxMatchingDate != null && !this.trxMatchingDate.equals(other.trxMatchingDate))) {
            return false;
        }
        if (this.customerId != other.customerId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.TransactionScoreMatchingPK[roundId=" + roundId + ", trxMatchingDate=" + trxMatchingDate + ", customerId=" + customerId + "]";
    }

}
