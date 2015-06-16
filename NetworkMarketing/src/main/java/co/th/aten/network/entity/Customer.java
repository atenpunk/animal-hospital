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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "customer")
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findById", query = "SELECT c FROM Customer c WHERE c.id = :id"),
    @NamedQuery(name = "Customer.findByCustomerId", query = "SELECT c FROM Customer c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "Customer.findByUpperId", query = "SELECT c FROM Customer c WHERE c.upperId = :upperId"),
    @NamedQuery(name = "Customer.findByLowerLeftId", query = "SELECT c FROM Customer c WHERE c.lowerLeftId = :lowerLeftId"),
    @NamedQuery(name = "Customer.findByLowerRightId", query = "SELECT c FROM Customer c WHERE c.lowerRightId = :lowerRightId"),
    @NamedQuery(name = "Customer.findByDirectId", query = "SELECT c FROM Customer c WHERE c.directId = :directId"),
    @NamedQuery(name = "Customer.findByPositionId", query = "SELECT c FROM Customer c WHERE c.positionId = :positionId"),
    @NamedQuery(name = "Customer.findByScore", query = "SELECT c FROM Customer c WHERE c.score = :score"),
    @NamedQuery(name = "Customer.findByRegisDate", query = "SELECT c FROM Customer c WHERE c.regisDate = :regisDate"),
    @NamedQuery(name = "Customer.findByTitleName", query = "SELECT c FROM Customer c WHERE c.titleName = :titleName"),
    @NamedQuery(name = "Customer.findByFirstName", query = "SELECT c FROM Customer c WHERE c.firstName = :firstName"),
    @NamedQuery(name = "Customer.findByLastName", query = "SELECT c FROM Customer c WHERE c.lastName = :lastName"),
    @NamedQuery(name = "Customer.findByStatus", query = "SELECT c FROM Customer c WHERE c.status = :status"),
    @NamedQuery(name = "Customer.findByCreateBy", query = "SELECT c FROM Customer c WHERE c.createBy = :createBy"),
    @NamedQuery(name = "Customer.findByCreateDate", query = "SELECT c FROM Customer c WHERE c.createDate = :createDate"),
    @NamedQuery(name = "Customer.findByUpdateBy", query = "SELECT c FROM Customer c WHERE c.updateBy = :updateBy"),
    @NamedQuery(name = "Customer.findByUpdateDate", query = "SELECT c FROM Customer c WHERE c.updateDate = :updateDate")})
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "upper_id")
    private Integer upperId;
    @Column(name = "lower_left_id")
    private Integer lowerLeftId;
    @Column(name = "lower_right_id")
    private Integer lowerRightId;
    @Column(name = "direct_id")
    private Integer directId;
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
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private UserLogin userId;

    public Customer() {
    }

    public Customer(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public Integer getDirectId() {
        return directId;
    }

    public void setDirectId(Integer directId) {
        this.directId = directId;
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

    public UserLogin getUserId() {
        return userId;
    }

    public void setUserId(UserLogin userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.Customer[id=" + id + "]";
    }

}
