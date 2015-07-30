/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.network.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "transaction_money")
@NamedQueries({
    @NamedQuery(name = "TransactionMoney.findAll", query = "SELECT t FROM TransactionMoney t")})
public class TransactionMoney implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "trx_money_id")
    private Integer trxMoneyId;
    @Column(name = "trx_money_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trxMoneyDatetime;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "balance")
    private BigDecimal balance;
    @Lob
    @Column(name = "remark")
    private String remark;
    @Column(name = "trx_money_status")
    private Integer trxMoneyStatus;
    @Column(name = "trx_money_flag")
    private Integer trxMoneyFlag;
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
    @Column(name = "receive_cumtomer_id")
    private Integer receiveCumtomerId;

    public TransactionMoney() {
    }

    public TransactionMoney(Integer trxMoneyId) {
        this.trxMoneyId = trxMoneyId;
    }

    public Integer getTrxMoneyId() {
        return trxMoneyId;
    }

    public void setTrxMoneyId(Integer trxMoneyId) {
        this.trxMoneyId = trxMoneyId;
    }

    public Date getTrxMoneyDatetime() {
        return trxMoneyDatetime;
    }

    public void setTrxMoneyDatetime(Date trxMoneyDatetime) {
        this.trxMoneyDatetime = trxMoneyDatetime;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getTrxMoneyStatus() {
        return trxMoneyStatus;
    }

    public void setTrxMoneyStatus(Integer trxMoneyStatus) {
        this.trxMoneyStatus = trxMoneyStatus;
    }

    public Integer getTrxMoneyFlag() {
        return trxMoneyFlag;
    }

    public void setTrxMoneyFlag(Integer trxMoneyFlag) {
        this.trxMoneyFlag = trxMoneyFlag;
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

    public Integer getReceiveCumtomerId() {
		return receiveCumtomerId;
	}

	public void setReceiveCumtomerId(Integer receiveCumtomerId) {
		this.receiveCumtomerId = receiveCumtomerId;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (trxMoneyId != null ? trxMoneyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionMoney)) {
            return false;
        }
        TransactionMoney other = (TransactionMoney) object;
        if ((this.trxMoneyId == null && other.trxMoneyId != null) || (this.trxMoneyId != null && !this.trxMoneyId.equals(other.trxMoneyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.TransactionMoney[trxMoneyId=" + trxMoneyId + "]";
    }

}
