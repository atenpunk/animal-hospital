/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.network.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Atenpunk
 */
@Entity
@Table(name = "address_amphures")
@NamedQueries({
    @NamedQuery(name = "AddressAmphures.findAll", query = "SELECT a FROM AddressAmphures a"),
    @NamedQuery(name = "AddressAmphures.findByAmphurId", query = "SELECT a FROM AddressAmphures a WHERE a.amphurId = :amphurId"),
    @NamedQuery(name = "AddressAmphures.findByAmphurCode", query = "SELECT a FROM AddressAmphures a WHERE a.amphurCode = :amphurCode"),
    @NamedQuery(name = "AddressAmphures.findByAmphurName", query = "SELECT a FROM AddressAmphures a WHERE a.amphurName = :amphurName"),
    @NamedQuery(name = "AddressAmphures.findByAmphurNameEng", query = "SELECT a FROM AddressAmphures a WHERE a.amphurNameEng = :amphurNameEng"),
    @NamedQuery(name = "AddressAmphures.findByGeoId", query = "SELECT a FROM AddressAmphures a WHERE a.geoId = :geoId"),
    @NamedQuery(name = "AddressAmphures.findByProvinceId", query = "SELECT a FROM AddressAmphures a WHERE a.provinceId = :provinceId")})
public class AddressAmphures implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AMPHUR_ID")
    private Integer amphurId;
    @Basic(optional = false)
    @Column(name = "AMPHUR_CODE")
    private String amphurCode;
    @Basic(optional = false)
    @Column(name = "AMPHUR_NAME")
    private String amphurName;
    @Basic(optional = false)
    @Column(name = "AMPHUR_NAME_ENG")
    private String amphurNameEng;
    @Basic(optional = false)
    @Column(name = "GEO_ID")
    private int geoId;
    @Basic(optional = false)
    @Column(name = "PROVINCE_ID")
    private int provinceId;

    public AddressAmphures() {
    }

    public AddressAmphures(Integer amphurId) {
        this.amphurId = amphurId;
    }

    public AddressAmphures(Integer amphurId, String amphurCode, String amphurName, String amphurNameEng, int geoId, int provinceId) {
        this.amphurId = amphurId;
        this.amphurCode = amphurCode;
        this.amphurName = amphurName;
        this.amphurNameEng = amphurNameEng;
        this.geoId = geoId;
        this.provinceId = provinceId;
    }

    public Integer getAmphurId() {
        return amphurId;
    }

    public void setAmphurId(Integer amphurId) {
        this.amphurId = amphurId;
    }

    public String getAmphurCode() {
        return amphurCode;
    }

    public void setAmphurCode(String amphurCode) {
        this.amphurCode = amphurCode;
    }

    public String getAmphurName() {
        return amphurName;
    }

    public void setAmphurName(String amphurName) {
        this.amphurName = amphurName;
    }

    public String getAmphurNameEng() {
        return amphurNameEng;
    }

    public void setAmphurNameEng(String amphurNameEng) {
        this.amphurNameEng = amphurNameEng;
    }

    public int getGeoId() {
        return geoId;
    }

    public void setGeoId(int geoId) {
        this.geoId = geoId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (amphurId != null ? amphurId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AddressAmphures)) {
            return false;
        }
        AddressAmphures other = (AddressAmphures) object;
        if ((this.amphurId == null && other.amphurId != null) || (this.amphurId != null && !this.amphurId.equals(other.amphurId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.AddressAmphures[amphurId=" + amphurId + "]";
    }

}
