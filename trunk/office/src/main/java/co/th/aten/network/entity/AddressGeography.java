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
@Table(name = "address_geography")
@NamedQueries({
    @NamedQuery(name = "AddressGeography.findAll", query = "SELECT a FROM AddressGeography a"),
    @NamedQuery(name = "AddressGeography.findByGeoId", query = "SELECT a FROM AddressGeography a WHERE a.geoId = :geoId"),
    @NamedQuery(name = "AddressGeography.findByGeoName", query = "SELECT a FROM AddressGeography a WHERE a.geoName = :geoName")})
public class AddressGeography implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GEO_ID")
    private Integer geoId;
    @Basic(optional = false)
    @Column(name = "GEO_NAME")
    private String geoName;

    public AddressGeography() {
    }

    public AddressGeography(Integer geoId) {
        this.geoId = geoId;
    }

    public AddressGeography(Integer geoId, String geoName) {
        this.geoId = geoId;
        this.geoName = geoName;
    }

    public Integer getGeoId() {
        return geoId;
    }

    public void setGeoId(Integer geoId) {
        this.geoId = geoId;
    }

    public String getGeoName() {
        return geoName;
    }

    public void setGeoName(String geoName) {
        this.geoName = geoName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geoId != null ? geoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AddressGeography)) {
            return false;
        }
        AddressGeography other = (AddressGeography) object;
        if ((this.geoId == null && other.geoId != null) || (this.geoId != null && !this.geoId.equals(other.geoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.AddressGeography[geoId=" + geoId + "]";
    }

}
