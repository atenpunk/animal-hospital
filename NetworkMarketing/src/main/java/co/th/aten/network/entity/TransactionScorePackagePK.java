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
public class TransactionScorePackagePK implements Serializable {
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
    @Column(name = "trx_package_date")
    @Temporal(TemporalType.DATE)
    private Date trxPackageDate;

    public TransactionScorePackagePK() {
    }

    public TransactionScorePackagePK(int roundId, int customerId, int suggestId, Date trxPackageDate) {
        this.roundId = roundId;
        this.customerId = customerId;
        this.suggestId = suggestId;
        this.trxPackageDate = trxPackageDate;
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

    public Date getTrxPackageDate() {
        return trxPackageDate;
    }

    public void setTrxPackageDate(Date trxPackageDate) {
        this.trxPackageDate = trxPackageDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) roundId;
        hash += (int) customerId;
        hash += (int) suggestId;
        hash += (trxPackageDate != null ? trxPackageDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionScorePackagePK)) {
            return false;
        }
        TransactionScorePackagePK other = (TransactionScorePackagePK) object;
        if (this.roundId != other.roundId) {
            return false;
        }
        if (this.customerId != other.customerId) {
            return false;
        }
        if (this.suggestId != other.suggestId) {
            return false;
        }
        if ((this.trxPackageDate == null && other.trxPackageDate != null) || (this.trxPackageDate != null && !this.trxPackageDate.equals(other.trxPackageDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.TransactionScorePackagePK[roundId=" + roundId + ", customerId=" + customerId + ", suggestId=" + suggestId + ", trxPackageDate=" + trxPackageDate + "]";
    }

}
