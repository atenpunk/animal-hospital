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
@Table(name = "transaction_score_package")
@NamedQueries({
    @NamedQuery(name = "TransactionScorePackage.findAll", query = "SELECT t FROM TransactionScorePackage t"),
    @NamedQuery(name = "TransactionScorePackage.findByRoundId", query = "SELECT t FROM TransactionScorePackage t WHERE t.transactionScorePackagePK.roundId = :roundId"),
    @NamedQuery(name = "TransactionScorePackage.findByCustomerId", query = "SELECT t FROM TransactionScorePackage t WHERE t.transactionScorePackagePK.customerId = :customerId"),
    @NamedQuery(name = "TransactionScorePackage.findBySuggestId", query = "SELECT t FROM TransactionScorePackage t WHERE t.transactionScorePackagePK.suggestId = :suggestId"),
    @NamedQuery(name = "TransactionScorePackage.findByTrxPackageDate", query = "SELECT t FROM TransactionScorePackage t WHERE t.transactionScorePackagePK.trxPackageDate = :trxPackageDate"),
    @NamedQuery(name = "TransactionScorePackage.findByProductId", query = "SELECT t FROM TransactionScorePackage t WHERE t.productId = :productId"),
    @NamedQuery(name = "TransactionScorePackage.findByPvPackage", query = "SELECT t FROM TransactionScorePackage t WHERE t.pvPackage = :pvPackage"),
    @NamedQuery(name = "TransactionScorePackage.findByTrxPackageStatus", query = "SELECT t FROM TransactionScorePackage t WHERE t.trxPackageStatus = :trxPackageStatus"),
    @NamedQuery(name = "TransactionScorePackage.findByTrxPackageFlag", query = "SELECT t FROM TransactionScorePackage t WHERE t.trxPackageFlag = :trxPackageFlag"),
    @NamedQuery(name = "TransactionScorePackage.findByCreateBy", query = "SELECT t FROM TransactionScorePackage t WHERE t.createBy = :createBy"),
    @NamedQuery(name = "TransactionScorePackage.findByCreateDate", query = "SELECT t FROM TransactionScorePackage t WHERE t.createDate = :createDate"),
    @NamedQuery(name = "TransactionScorePackage.findByUpdateBy", query = "SELECT t FROM TransactionScorePackage t WHERE t.updateBy = :updateBy"),
    @NamedQuery(name = "TransactionScorePackage.findByUpdateDate", query = "SELECT t FROM TransactionScorePackage t WHERE t.updateDate = :updateDate")})
public class TransactionScorePackage implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TransactionScorePackagePK transactionScorePackagePK;
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

    public TransactionScorePackage() {
    }

    public TransactionScorePackage(TransactionScorePackagePK transactionScorePackagePK) {
        this.transactionScorePackagePK = transactionScorePackagePK;
    }

    public TransactionScorePackage(int roundId, int customerId, int suggestId, Date trxPackageDate) {
        this.transactionScorePackagePK = new TransactionScorePackagePK(roundId, customerId, suggestId, trxPackageDate);
    }

    public TransactionScorePackagePK getTransactionScorePackagePK() {
        return transactionScorePackagePK;
    }

    public void setTransactionScorePackagePK(TransactionScorePackagePK transactionScorePackagePK) {
        this.transactionScorePackagePK = transactionScorePackagePK;
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
        hash += (transactionScorePackagePK != null ? transactionScorePackagePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionScorePackage)) {
            return false;
        }
        TransactionScorePackage other = (TransactionScorePackage) object;
        if ((this.transactionScorePackagePK == null && other.transactionScorePackagePK != null) || (this.transactionScorePackagePK != null && !this.transactionScorePackagePK.equals(other.transactionScorePackagePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.TransactionScorePackage[transactionScorePackagePK=" + transactionScorePackagePK + "]";
    }

}
