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
 * @author KENG
 */
@Entity
@Table(name = "system_log")
@NamedQueries({
    @NamedQuery(name = "SystemLog.findAll", query = "SELECT s FROM SystemLog s"),
    @NamedQuery(name = "SystemLog.findBySystemLogId", query = "SELECT s FROM SystemLog s WHERE s.systemLogId = :systemLogId"),
    @NamedQuery(name = "SystemLog.findByLogDatetime", query = "SELECT s FROM SystemLog s WHERE s.logDatetime = :logDatetime"),
    @NamedQuery(name = "SystemLog.findByDescription", query = "SELECT s FROM SystemLog s WHERE s.description = :description"),
    @NamedQuery(name = "SystemLog.findByAction", query = "SELECT s FROM SystemLog s WHERE s.action = :action"),
    @NamedQuery(name = "SystemLog.findByApplication", query = "SELECT s FROM SystemLog s WHERE s.application = :application"),
    @NamedQuery(name = "SystemLog.findByUserId", query = "SELECT s FROM SystemLog s WHERE s.userId = :userId")})
public class SystemLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "system_log_id", nullable = false)
    private Integer systemLogId;
    @Basic(optional = false)
    @Column(name = "log_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date logDatetime;
    @Column(name = "description", length = 45)
    private String description;
    @Column(name = "action", length = 45)
    private String action;
    @Column(name = "application", length = 45)
    private String application;
    @Column(name = "user_id")
    private Integer userId;

    public SystemLog() {
    }

    public SystemLog(Integer systemLogId) {
        this.systemLogId = systemLogId;
    }

    public SystemLog(Integer systemLogId, Date logDatetime) {
        this.systemLogId = systemLogId;
        this.logDatetime = logDatetime;
    }

    public Integer getSystemLogId() {
        return systemLogId;
    }

    public void setSystemLogId(Integer systemLogId) {
        this.systemLogId = systemLogId;
    }

    public Date getLogDatetime() {
        return logDatetime;
    }

    public void setLogDatetime(Date logDatetime) {
        this.logDatetime = logDatetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (systemLogId != null ? systemLogId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SystemLog)) {
            return false;
        }
        SystemLog other = (SystemLog) object;
        if ((this.systemLogId == null && other.systemLogId != null) || (this.systemLogId != null && !this.systemLogId.equals(other.systemLogId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.SystemLog[systemLogId=" + systemLogId + "]";
    }

}
