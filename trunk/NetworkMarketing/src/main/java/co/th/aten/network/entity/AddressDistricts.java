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
@Table(name = "address_districts")
@NamedQueries({
    @NamedQuery(name = "AddressDistricts.findAll", query = "SELECT a FROM AddressDistricts a"),
    @NamedQuery(name = "AddressDistricts.findByDistrictId", query = "SELECT a FROM AddressDistricts a WHERE a.districtId = :districtId"),
    @NamedQuery(name = "AddressDistricts.findByDistrictCode", query = "SELECT a FROM AddressDistricts a WHERE a.districtCode = :districtCode"),
    @NamedQuery(name = "AddressDistricts.findByDistrictName", query = "SELECT a FROM AddressDistricts a WHERE a.districtName = :districtName"),
    @NamedQuery(name = "AddressDistricts.findByDistrictNameEng", query = "SELECT a FROM AddressDistricts a WHERE a.districtNameEng = :districtNameEng"),
    @NamedQuery(name = "AddressDistricts.findByAmphurId", query = "SELECT a FROM AddressDistricts a WHERE a.amphurId = :amphurId"),
    @NamedQuery(name = "AddressDistricts.findByProvinceId", query = "SELECT a FROM AddressDistricts a WHERE a.provinceId = :provinceId"),
    @NamedQuery(name = "AddressDistricts.findByGeoId", query = "SELECT a FROM AddressDistricts a WHERE a.geoId = :geoId")})
public class AddressDistricts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DISTRICT_ID")
    private Integer districtId;
    @Basic(optional = false)
    @Column(name = "DISTRICT_CODE")
    private String districtCode;
    @Basic(optional = false)
    @Column(name = "DISTRICT_NAME")
    private String districtName;
    @Basic(optional = false)
    @Column(name = "DISTRICT_NAME_ENG")
    private String districtNameEng;
    @Basic(optional = false)
    @Column(name = "AMPHUR_ID")
    private int amphurId;
    @Basic(optional = false)
    @Column(name = "PROVINCE_ID")
    private int provinceId;
    @Basic(optional = false)
    @Column(name = "GEO_ID")
    private int geoId;

    public AddressDistricts() {
    }

    public AddressDistricts(Integer districtId) {
        this.districtId = districtId;
    }

    public AddressDistricts(Integer districtId, String districtCode, String districtName, String districtNameEng, int amphurId, int provinceId, int geoId) {
        this.districtId = districtId;
        this.districtCode = districtCode;
        this.districtName = districtName;
        this.districtNameEng = districtNameEng;
        this.amphurId = amphurId;
        this.provinceId = provinceId;
        this.geoId = geoId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictNameEng() {
        return districtNameEng;
    }

    public void setDistrictNameEng(String districtNameEng) {
        this.districtNameEng = districtNameEng;
    }

    public int getAmphurId() {
        return amphurId;
    }

    public void setAmphurId(int amphurId) {
        this.amphurId = amphurId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getGeoId() {
        return geoId;
    }

    public void setGeoId(int geoId) {
        this.geoId = geoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (districtId != null ? districtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AddressDistricts)) {
            return false;
        }
        AddressDistricts other = (AddressDistricts) object;
        if ((this.districtId == null && other.districtId != null) || (this.districtId != null && !this.districtId.equals(other.districtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.AddressDistricts[districtId=" + districtId + "]";
    }

}
