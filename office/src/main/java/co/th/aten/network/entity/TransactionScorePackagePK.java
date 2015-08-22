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
    @Column(name = "trx_package_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trxPackageDate;
    @Basic(optional = false)
    @Column(name = "customer_id")
    private int customerId;
    @Basic(optional = false)
    @Column(name = "suggest_id")
    private int suggestId;

    public TransactionScorePackagePK() {
    }

    public TransactionScorePackagePK(Date trxPackageDate, int customerId, int suggestId) {
        this.trxPackageDate = trxPackageDate;
        this.customerId = customerId;
        this.suggestId = suggestId;
    }

    public Date getTrxPackageDate() {
        return trxPackageDate;
    }

    public void setTrxPackageDate(Date trxPackageDate) {
        this.trxPackageDate = trxPackageDate;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trxPackageDate != null ? trxPackageDate.hashCode() : 0);
        hash += (int) customerId;
        hash += (int) suggestId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionScorePackagePK)) {
            return false;
        }
        TransactionScorePackagePK other = (TransactionScorePackagePK) object;
        if ((this.trxPackageDate == null && other.trxPackageDate != null) || (this.trxPackageDate != null && !this.trxPackageDate.equals(other.trxPackageDate))) {
            return false;
        }
        if (this.customerId != other.customerId) {
            return false;
        }
        if (this.suggestId != other.suggestId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.TransactionScorePackagePK[trxPackageDate=" + trxPackageDate + ", customerId=" + customerId + ", suggestId=" + suggestId + "]";
    }

}
