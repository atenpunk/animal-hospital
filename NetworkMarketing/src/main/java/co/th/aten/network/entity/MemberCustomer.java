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
@Table(name = "member_customer")
@NamedQueries({
    @NamedQuery(name = "MemberCustomer.findAll", query = "SELECT m FROM MemberCustomer m")})
public class MemberCustomer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "customer_member")
    private String customerMember;
    @Column(name = "upper_id")
    private Integer upperId;
    @Column(name = "lower_left_id")
    private Integer lowerLeftId;
    @Column(name = "lower_right_id")
    private Integer lowerRightId;
    @Column(name = "recommend_id")
    private Integer recommendId;
    @Column(name = "position_id")
    private Integer positionId;
    @Column(name = "score")
    private Integer score;
    @Column(name = "regis_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regisDate;
    @Column(name = "title_name")
    private String titleName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "status")
    private Integer status;
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
    @OneToMany(mappedBy = "customerId")
    private Collection<UserLogin> userLoginCollection;

    public MemberCustomer() {
    }

    public MemberCustomer(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerMember() {
        return customerMember;
    }

    public void setCustomerMember(String customerMember) {
        this.customerMember = customerMember;
    }

    public Integer getUpperId() {
        return upperId;
    }

    public void setUpperId(Integer upperId) {
        this.upperId = upperId;
    }

    public Integer getLowerLeftId() {
        return lowerLeftId;
    }

    public void setLowerLeftId(Integer lowerLeftId) {
        this.lowerLeftId = lowerLeftId;
    }

    public Integer getLowerRightId() {
        return lowerRightId;
    }

    public void setLowerRightId(Integer lowerRightId) {
        this.lowerRightId = lowerRightId;
    }

    public Integer getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Integer recommendId) {
        this.recommendId = recommendId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getRegisDate() {
        return regisDate;
    }

    public void setRegisDate(Date regisDate) {
        this.regisDate = regisDate;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Collection<UserLogin> getUserLoginCollection() {
        return userLoginCollection;
    }

    public void setUserLoginCollection(Collection<UserLogin> userLoginCollection) {
        this.userLoginCollection = userLoginCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemberCustomer)) {
            return false;
        }
        MemberCustomer other = (MemberCustomer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.MemberCustomer[customerId=" + customerId + "]";
    }

}
