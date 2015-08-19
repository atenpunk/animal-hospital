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
@Table(name = "master_configkey")
@NamedQueries({
    @NamedQuery(name = "MasterConfigkey.findAll", query = "SELECT m FROM MasterConfigkey m"),
    @NamedQuery(name = "MasterConfigkey.findByKeyName", query = "SELECT m FROM MasterConfigkey m WHERE m.keyName = :keyName"),
    @NamedQuery(name = "MasterConfigkey.findByValue01", query = "SELECT m FROM MasterConfigkey m WHERE m.value01 = :value01"),
    @NamedQuery(name = "MasterConfigkey.findByValue02", query = "SELECT m FROM MasterConfigkey m WHERE m.value02 = :value02"),
    @NamedQuery(name = "MasterConfigkey.findByCreateBy", query = "SELECT m FROM MasterConfigkey m WHERE m.createBy = :createBy"),
    @NamedQuery(name = "MasterConfigkey.findByCreateDate", query = "SELECT m FROM MasterConfigkey m WHERE m.createDate = :createDate"),
    @NamedQuery(name = "MasterConfigkey.findByUpdateBy", query = "SELECT m FROM MasterConfigkey m WHERE m.updateBy = :updateBy"),
    @NamedQuery(name = "MasterConfigkey.findByUpdateDate", query = "SELECT m FROM MasterConfigkey m WHERE m.updateDate = :updateDate")})
public class MasterConfigkey implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "key_name")
    private String keyName;
    @Column(name = "value_01")
    private String value01;
    @Column(name = "value_02")
    private String value02;
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

    public MasterConfigkey() {
    }

    public MasterConfigkey(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getValue01() {
        return value01;
    }

    public void setValue01(String value01) {
        this.value01 = value01;
    }

    public String getValue02() {
        return value02;
    }

    public void setValue02(String value02) {
        this.value02 = value02;
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
        hash += (keyName != null ? keyName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterConfigkey)) {
            return false;
        }
        MasterConfigkey other = (MasterConfigkey) object;
        if ((this.keyName == null && other.keyName != null) || (this.keyName != null && !this.keyName.equals(other.keyName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.MasterConfigkey[keyName=" + keyName + "]";
    }

}
