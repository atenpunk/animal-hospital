/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.football.model;

import java.util.Date;

/**
 *
 * @author Atenpunk
 */
public class UserModel {

    private int userId;
    private String userName;
    private String userLogin;
    private String password;
    private Date lastLogin;
    private short group;
    private String phoneNo;
    private String mobileHome;
    private String email;
    private short status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public short getGroup() {
        return group;
    }

    public void setGroup(short group) {
        this.group = group;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getMobileHome() {
        return mobileHome;
    }

    public void setMobileHome(String mobileHome) {
        this.mobileHome = mobileHome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
