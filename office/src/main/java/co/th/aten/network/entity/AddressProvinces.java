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
@Table(name = "address_provinces")
@NamedQueries({
    @NamedQuery(name = "AddressProvinces.findAll", query = "SELECT a FROM AddressProvinces a"),
    @NamedQuery(name = "AddressProvinces.findByProvinceId", query = "SELECT a FROM AddressProvinces a WHERE a.provinceId = :provinceId"),
    @NamedQuery(name = "AddressProvinces.findByProvinceCode", query = "SELECT a FROM AddressProvinces a WHERE a.provinceCode = :provinceCode"),
    @NamedQuery(name = "AddressProvinces.findByProvinceName", query = "SELECT a FROM AddressProvinces a WHERE a.provinceName = :provinceName"),
    @NamedQuery(name = "AddressProvinces.findByProvinceNameEng", query = "SELECT a FROM AddressProvinces a WHERE a.provinceNameEng = :provinceNameEng"),
    @NamedQuery(name = "AddressProvinces.findByGeoId", query = "SELECT a FROM AddressProvinces a WHERE a.geoId = :geoId")})
public class AddressProvinces implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PROVINCE_ID")
    private Integer provinceId;
    @Basic(optional = false)
    @Column(name = "PROVINCE_CODE")
    private String provinceCode;
    @Basic(optional = false)
    @Column(name = "PROVINCE_NAME")
    private String provinceName;
    @Basic(optional = false)
    @Column(name = "PROVINCE_NAME_ENG")
    private String provinceNameEng;
    @Basic(optional = false)
    @Column(name = "GEO_ID")
    private int geoId;

    public AddressProvinces() {
    }

    public AddressProvinces(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public AddressProvinces(Integer provinceId, String provinceCode, String provinceName, String provinceNameEng, int geoId) {
        this.provinceId = provinceId;
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
        this.provinceNameEng = provinceNameEng;
        this.geoId = geoId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceNameEng() {
        return provinceNameEng;
    }

    public void setProvinceNameEng(String provinceNameEng) {
        this.provinceNameEng = provinceNameEng;
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
        hash += (provinceId != null ? provinceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AddressProvinces)) {
            return false;
        }
        AddressProvinces other = (AddressProvinces) object;
        if ((this.provinceId == null && other.provinceId != null) || (this.provinceId != null && !this.provinceId.equals(other.provinceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.AddressProvinces[provinceId=" + provinceId + "]";
    }

}
