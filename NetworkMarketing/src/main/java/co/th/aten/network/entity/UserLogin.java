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
@Table(name = "user_login")
@NamedQueries({
    @NamedQuery(name = "UserLogin.findAll", query = "SELECT u FROM UserLogin u"),
    @NamedQuery(name = "UserLogin.findByUserId", query = "SELECT u FROM UserLogin u WHERE u.userId = :userId"),
    @NamedQuery(name = "UserLogin.findByUserName", query = "SELECT u FROM UserLogin u WHERE u.userName = :userName"),
    @NamedQuery(name = "UserLogin.findByLoginName", query = "SELECT u FROM UserLogin u WHERE u.loginName = :loginName"),
    @NamedQuery(name = "UserLogin.findByPassword", query = "SELECT u FROM UserLogin u WHERE u.password = :password"),
    @NamedQuery(name = "UserLogin.findByLastLogin", query = "SELECT u FROM UserLogin u WHERE u.lastLogin = :lastLogin"),
    @NamedQuery(name = "UserLogin.findByPhone", query = "SELECT u FROM UserLogin u WHERE u.phone = :phone"),
    @NamedQuery(name = "UserLogin.findByMobileHone", query = "SELECT u FROM UserLogin u WHERE u.mobileHone = :mobileHone"),
    @NamedQuery(name = "UserLogin.findByEmail", query = "SELECT u FROM UserLogin u WHERE u.email = :email"),
    @NamedQuery(name = "UserLogin.findByStatus", query = "SELECT u FROM UserLogin u WHERE u.status = :status"),
    @NamedQuery(name = "UserLogin.findByIsForceChange", query = "SELECT u FROM UserLogin u WHERE u.isForceChange = :isForceChange")})
public class UserLogin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "login_name")
    private String loginName;
    @Column(name = "password")
    private String password;
    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    @Column(name = "phone")
    private String phone;
    @Column(name = "mobile_hone")
    private String mobileHone;
    @Column(name = "email")
    private String email;
    @Column(name = "status")
    private Integer status;
    @Column(name = "is_force_change")
    private Integer isForceChange;
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ManyToOne
    private Customer customerId;
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    @ManyToOne
    private UserGroup groupId;

    public UserLogin() {
    }

    public UserLogin(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobileHone() {
        return mobileHone;
    }

    public void setMobileHone(String mobileHone) {
        this.mobileHone = mobileHone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsForceChange() {
        return isForceChange;
    }

    public void setIsForceChange(Integer isForceChange) {
        this.isForceChange = isForceChange;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public UserGroup getGroupId() {
        return groupId;
    }

    public void setGroupId(UserGroup groupId) {
        this.groupId = groupId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserLogin)) {
            return false;
        }
        UserLogin other = (UserLogin) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.UserLogin[userId=" + userId + "]";
    }

}
