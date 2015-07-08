/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.network.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Atenpunk
 */
@Embeddable
public class TransactionSellDetailPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "trx_detail_id")
    private int trxDetailId;
    @Basic(optional = false)
    @Column(name = "trx_header_id")
    private int trxHeaderId;

    public TransactionSellDetailPK() {
    }

    public TransactionSellDetailPK(int trxDetailId, int trxHeaderId) {
        this.trxDetailId = trxDetailId;
        this.trxHeaderId = trxHeaderId;
    }

    public int getTrxDetailId() {
        return trxDetailId;
    }

    public void setTrxDetailId(int trxDetailId) {
        this.trxDetailId = trxDetailId;
    }

    public int getTrxHeaderId() {
        return trxHeaderId;
    }

    public void setTrxHeaderId(int trxHeaderId) {
        this.trxHeaderId = trxHeaderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) trxDetailId;
        hash += (int) trxHeaderId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionSellDetailPK)) {
            return false;
        }
        TransactionSellDetailPK other = (TransactionSellDetailPK) object;
        if (this.trxDetailId != other.trxDetailId) {
            return false;
        }
        if (this.trxHeaderId != other.trxHeaderId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.TransactionSellDetailPK[trxDetailId=" + trxDetailId + ", trxHeaderId=" + trxHeaderId + "]";
    }

}
