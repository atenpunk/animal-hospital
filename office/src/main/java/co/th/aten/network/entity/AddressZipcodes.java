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
@Table(name = "address_zipcodes")
@NamedQueries({
    @NamedQuery(name = "AddressZipcodes.findAll", query = "SELECT a FROM AddressZipcodes a"),
    @NamedQuery(name = "AddressZipcodes.findById", query = "SELECT a FROM AddressZipcodes a WHERE a.id = :id"),
    @NamedQuery(name = "AddressZipcodes.findByDistrictCode", query = "SELECT a FROM AddressZipcodes a WHERE a.districtCode = :districtCode"),
    @NamedQuery(name = "AddressZipcodes.findByZipcode", query = "SELECT a FROM AddressZipcodes a WHERE a.zipcode = :zipcode")})
public class AddressZipcodes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "district_code")
    private String districtCode;
    @Basic(optional = false)
    @Column(name = "zipcode")
    private String zipcode;

    public AddressZipcodes() {
    }

    public AddressZipcodes(Integer id) {
        this.id = id;
    }

    public AddressZipcodes(Integer id, String districtCode, String zipcode) {
        this.id = id;
        this.districtCode = districtCode;
        this.zipcode = zipcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
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
        if (!(object instanceof AddressZipcodes)) {
            return false;
        }
        AddressZipcodes other = (AddressZipcodes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.AddressZipcodes[id=" + id + "]";
    }

}
