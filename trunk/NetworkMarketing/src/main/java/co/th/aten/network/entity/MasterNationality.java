/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.network.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Atenpunk
 */
@Entity
@Table(name = "master_nationality")
@NamedQueries({
    @NamedQuery(name = "MasterNationality.findAll", query = "SELECT m FROM MasterNationality m"),
    @NamedQuery(name = "MasterNationality.findByNationId", query = "SELECT m FROM MasterNationality m WHERE m.nationId = :nationId"),
    @NamedQuery(name = "MasterNationality.findByDescTh", query = "SELECT m FROM MasterNationality m WHERE m.descTh = :descTh"),
    @NamedQuery(name = "MasterNationality.findByDescEn", query = "SELECT m FROM MasterNationality m WHERE m.descEn = :descEn"),
    @NamedQuery(name = "MasterNationality.findByDescLao", query = "SELECT m FROM MasterNationality m WHERE m.descLao = :descLao"),
    @NamedQuery(name = "MasterNationality.findByCreateBy", query = "SELECT m FROM MasterNationality m WHERE m.createBy = :createBy"),
    @NamedQuery(name = "MasterNationality.findByCreateDate", query = "SELECT m FROM MasterNationality m WHERE m.createDate = :createDate"),
    @NamedQuery(name = "MasterNationality.findByUpdateBy", query = "SELECT m FROM MasterNationality m WHERE m.updateBy = :updateBy"),
    @NamedQuery(name = "MasterNationality.findByUpdateDate", query = "SELECT m FROM MasterNationality m WHERE m.updateDate = :updateDate")})
public class MasterNationality implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nation_id")
    private Integer nationId;
    @Column(name = "desc_th")
    private String descTh;
    @Column(name = "desc_en")
    private String descEn;
    @Column(name = "desc_lao")
    private String descLao;
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
    @OneToMany(mappedBy = "nationId")
    private Collection<MemberCustomer> memberCustomerCollection;

    public MasterNationality() {
    }

    public MasterNationality(Integer nationId) {
        this.nationId = nationId;
    }

    public Integer getNationId() {
        return nationId;
    }

    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }

    public String getDescTh() {
        return descTh;
    }

    public void setDescTh(String descTh) {
        this.descTh = descTh;
    }

    public String getDescEn() {
        return descEn;
    }

    public void setDescEn(String descEn) {
        this.descEn = descEn;
    }

    public String getDescLao() {
        return descLao;
    }

    public void setDescLao(String descLao) {
        this.descLao = descLao;
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

    public Collection<MemberCustomer> getMemberCustomerCollection() {
        return memberCustomerCollection;
    }

    public void setMemberCustomerCollection(Collection<MemberCustomer> memberCustomerCollection) {
        this.memberCustomerCollection = memberCustomerCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nationId != null ? nationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterNationality)) {
            return false;
        }
        MasterNationality other = (MasterNationality) object;
        if ((this.nationId == null && other.nationId != null) || (this.nationId != null && !this.nationId.equals(other.nationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.MasterNationality[nationId=" + nationId + "]";
    }

}
