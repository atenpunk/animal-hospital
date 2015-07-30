/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.network.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "transaction_money_status")
@NamedQueries({
    @NamedQuery(name = "TransactionMoneyStatus.findAll", query = "SELECT t FROM TransactionMoneyStatus t"),
    @NamedQuery(name = "TransactionMoneyStatus.findByStatusId", query = "SELECT t FROM TransactionMoneyStatus t WHERE t.statusId = :statusId"),
    @NamedQuery(name = "TransactionMoneyStatus.findByStatusDesc", query = "SELECT t FROM TransactionMoneyStatus t WHERE t.statusDesc = :statusDesc"),
    @NamedQuery(name = "TransactionMoneyStatus.findByCreateBy", query = "SELECT t FROM TransactionMoneyStatus t WHERE t.createBy = :createBy"),
    @NamedQuery(name = "TransactionMoneyStatus.findByCreateDate", query = "SELECT t FROM TransactionMoneyStatus t WHERE t.createDate = :createDate"),
    @NamedQuery(name = "TransactionMoneyStatus.findByUpdateBy", query = "SELECT t FROM TransactionMoneyStatus t WHERE t.updateBy = :updateBy"),
    @NamedQuery(name = "TransactionMoneyStatus.findByUpdateDate", query = "SELECT t FROM TransactionMoneyStatus t WHERE t.updateDate = :updateDate")})
public class TransactionMoneyStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "status_id")
    private Integer statusId;
    @Column(name = "status_desc")
    private String statusDesc;
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

    public TransactionMoneyStatus() {
    }

    public TransactionMoneyStatus(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
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
        hash += (statusId != null ? statusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionMoneyStatus)) {
            return false;
        }
        TransactionMoneyStatus other = (TransactionMoneyStatus) object;
        if ((this.statusId == null && other.statusId != null) || (this.statusId != null && !this.statusId.equals(other.statusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.TransactionMoneyStatus[statusId=" + statusId + "]";
    }

}
