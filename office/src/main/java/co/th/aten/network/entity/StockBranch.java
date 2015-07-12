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
@Table(name = "stock_branch")
@NamedQueries({
    @NamedQuery(name = "StockBranch.findAll", query = "SELECT s FROM StockBranch s"),
    @NamedQuery(name = "StockBranch.findByBranchId", query = "SELECT s FROM StockBranch s WHERE s.branchId = :branchId"),
    @NamedQuery(name = "StockBranch.findByBranchCode", query = "SELECT s FROM StockBranch s WHERE s.branchCode = :branchCode"),
    @NamedQuery(name = "StockBranch.findByThDesc", query = "SELECT s FROM StockBranch s WHERE s.thDesc = :thDesc"),
    @NamedQuery(name = "StockBranch.findByEnDesc", query = "SELECT s FROM StockBranch s WHERE s.enDesc = :enDesc"),
    @NamedQuery(name = "StockBranch.findByProvinceId", query = "SELECT s FROM StockBranch s WHERE s.provinceId = :provinceId"),
    @NamedQuery(name = "StockBranch.findByCreateBy", query = "SELECT s FROM StockBranch s WHERE s.createBy = :createBy"),
    @NamedQuery(name = "StockBranch.findByCreateDate", query = "SELECT s FROM StockBranch s WHERE s.createDate = :createDate"),
    @NamedQuery(name = "StockBranch.findByUpdateBy", query = "SELECT s FROM StockBranch s WHERE s.updateBy = :updateBy"),
    @NamedQuery(name = "StockBranch.findByUpdateDate", query = "SELECT s FROM StockBranch s WHERE s.updateDate = :updateDate")})
public class StockBranch implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "branch_id")
    private Integer branchId;
    @Column(name = "branch_code")
    private String branchCode;
    @Column(name = "th_desc")
    private String thDesc;
    @Column(name = "en_desc")
    private String enDesc;
    @Column(name = "province_id")
    private Integer provinceId;
    @Lob
    @Column(name = "remark")
    private String remark;
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

    public StockBranch() {
    }

    public StockBranch(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getThDesc() {
        return thDesc;
    }

    public void setThDesc(String thDesc) {
        this.thDesc = thDesc;
    }

    public String getEnDesc() {
        return enDesc;
    }

    public void setEnDesc(String enDesc) {
        this.enDesc = enDesc;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        hash += (branchId != null ? branchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StockBranch)) {
            return false;
        }
        StockBranch other = (StockBranch) object;
        if ((this.branchId == null && other.branchId != null) || (this.branchId != null && !this.branchId.equals(other.branchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.StockBranch[branchId=" + branchId + "]";
    }

}
