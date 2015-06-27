/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.network.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Atenpunk
 */
@Entity
@Table(name = "master_bank")
@NamedQueries({
    @NamedQuery(name = "MasterBank.findAll", query = "SELECT m FROM MasterBank m"),
    @NamedQuery(name = "MasterBank.findByBankCode", query = "SELECT m FROM MasterBank m WHERE m.bankCode = :bankCode"),
    @NamedQuery(name = "MasterBank.findByBankName", query = "SELECT m FROM MasterBank m WHERE m.bankName = :bankName"),
    @NamedQuery(name = "MasterBank.findByCompanyId", query = "SELECT m FROM MasterBank m WHERE m.companyId = :companyId")})
public class MasterBank implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "bank_code")
    private Integer bankCode;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "company_id")
    private Integer companyId;

    public MasterBank() {
    }

    public MasterBank(Integer bankCode) {
        this.bankCode = bankCode;
    }

    public Integer getBankCode() {
        return bankCode;
    }

    public void setBankCode(Integer bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bankCode != null ? bankCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterBank)) {
            return false;
        }
        MasterBank other = (MasterBank) object;
        if ((this.bankCode == null && other.bankCode != null) || (this.bankCode != null && !this.bankCode.equals(other.bankCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.MasterBank[bankCode=" + bankCode + "]";
    }

}
