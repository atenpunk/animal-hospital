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
public class TransactionScorePackageWeeklyPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "round_id")
    private int roundId;
    @Basic(optional = false)
    @Column(name = "customer_id")
    private int customerId;
    @Basic(optional = false)
    @Column(name = "suggest_id")
    private int suggestId;
    @Basic(optional = false)
    @Column(name = "trx_start_date")
    @Temporal(TemporalType.DATE)
    private Date trxStartDate;
    @Basic(optional = false)
    @Column(name = "trx_end_date")
    @Temporal(TemporalType.DATE)
    private Date trxEndDate;

    public TransactionScorePackageWeeklyPK() {
    }

    public TransactionScorePackageWeeklyPK(int roundId, int customerId, int suggestId, Date trxStartDate, Date trxEndDate) {
        this.roundId = roundId;
        this.customerId = customerId;
        this.suggestId = suggestId;
        this.trxStartDate = trxStartDate;
        this.trxEndDate = trxEndDate;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getSuggestId() {
        return suggestId;
    }

    public void setSuggestId(int suggestId) {
        this.suggestId = suggestId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) roundId;
        hash += (int) customerId;
        hash += (int) suggestId;
        hash += (trxStartDate != null ? trxStartDate.hashCode() : 0);
        hash += (trxEndDate != null ? trxEndDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionScorePackageWeeklyPK)) {
            return false;
        }
        TransactionScorePackageWeeklyPK other = (TransactionScorePackageWeeklyPK) object;
        if (this.roundId != other.roundId) {
            return false;
        }
        if (this.customerId != other.customerId) {
            return false;
        }
        if (this.suggestId != other.suggestId) {
            return false;
        }
        if ((this.trxStartDate == null && other.trxStartDate != null) || (this.trxStartDate != null && !this.trxStartDate.equals(other.trxStartDate))) {
            return false;
        }
        if ((this.trxEndDate == null && other.trxEndDate != null) || (this.trxEndDate != null && !this.trxEndDate.equals(other.trxEndDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.TransactionScorePackageWeeklyPK[roundId=" + roundId + ", customerId=" + customerId + ", suggestId=" + suggestId + ", trxStartDate=" + trxStartDate + ", trxEndDate=" + trxEndDate + "]";
    }

}
