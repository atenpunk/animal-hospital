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
@Table(name = "member_side")
@NamedQueries({
    @NamedQuery(name = "MemberSide.findAll", query = "SELECT m FROM MemberSide m"),
    @NamedQuery(name = "MemberSide.findBySideId", query = "SELECT m FROM MemberSide m WHERE m.sideId = :sideId"),
    @NamedQuery(name = "MemberSide.findByThName", query = "SELECT m FROM MemberSide m WHERE m.thName = :thName"),
    @NamedQuery(name = "MemberSide.findByEnName", query = "SELECT m FROM MemberSide m WHERE m.enName = :enName")})
public class MemberSide implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "side_id")
    private Integer sideId;
    @Column(name = "th_name")
    private String thName;
    @Column(name = "en_name")
    private String enName;

    public MemberSide() {
    }

    public MemberSide(Integer sideId) {
        this.sideId = sideId;
    }

    public Integer getSideId() {
        return sideId;
    }

    public void setSideId(Integer sideId) {
        this.sideId = sideId;
    }

    public String getThName() {
        return thName;
    }

    public void setThName(String thName) {
        this.thName = thName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sideId != null ? sideId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemberSide)) {
            return false;
        }
        MemberSide other = (MemberSide) object;
        if ((this.sideId == null && other.sideId != null) || (this.sideId != null && !this.sideId.equals(other.sideId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.MemberSide[sideId=" + sideId + "]";
    }

}
