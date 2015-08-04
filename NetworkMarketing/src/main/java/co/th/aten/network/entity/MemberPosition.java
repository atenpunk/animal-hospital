/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.network.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Atenpunk
 */
@Entity
@Table(name = "member_position")
@NamedQueries({
    @NamedQuery(name = "MemberPosition.findAll", query = "SELECT m FROM MemberPosition m"),
    @NamedQuery(name = "MemberPosition.findByPositionId", query = "SELECT m FROM MemberPosition m WHERE m.positionId = :positionId"),
    @NamedQuery(name = "MemberPosition.findByThName", query = "SELECT m FROM MemberPosition m WHERE m.thName = :thName"),
    @NamedQuery(name = "MemberPosition.findByEnName", query = "SELECT m FROM MemberPosition m WHERE m.enName = :enName"),
    @NamedQuery(name = "MemberPosition.findByImageName", query = "SELECT m FROM MemberPosition m WHERE m.imageName = :imageName"),
    @NamedQuery(name = "MemberPosition.findByMatching", query = "SELECT m FROM MemberPosition m WHERE m.matching = :matching")})
public class MemberPosition implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "position_id")
    private Integer positionId;
    @Column(name = "th_name")
    private String thName;
    @Column(name = "en_name")
    private String enName;
    @Column(name = "image_name")
    private String imageName;
    @Column(name = "matching")
    private Integer matching;
    @OneToMany(mappedBy = "positionId")
    private Collection<MemberCustomer> memberCustomerCollection;

    public MemberPosition() {
    }

    public MemberPosition(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Integer getMatching() {
        return matching;
    }

    public void setMatching(Integer matching) {
        this.matching = matching;
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
        hash += (positionId != null ? positionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemberPosition)) {
            return false;
        }
        MemberPosition other = (MemberPosition) object;
        if ((this.positionId == null && other.positionId != null) || (this.positionId != null && !this.positionId.equals(other.positionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.MemberPosition[positionId=" + positionId + "]";
    }

}
