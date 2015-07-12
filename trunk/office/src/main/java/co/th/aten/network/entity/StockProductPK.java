/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.network.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Atenpunk
 */
@Embeddable
public class StockProductPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "product_id")
    private int productId;
    @Basic(optional = false)
    @Column(name = "catalog_id")
    private int catalogId;

    public StockProductPK() {
    }

    public StockProductPK(int productId, int catalogId) {
        this.productId = productId;
        this.catalogId = catalogId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) productId;
        hash += (int) catalogId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StockProductPK)) {
            return false;
        }
        StockProductPK other = (StockProductPK) object;
        if (this.productId != other.productId) {
            return false;
        }
        if (this.catalogId != other.catalogId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.StockProductPK[productId=" + productId + ", catalogId=" + catalogId + "]";
    }

}
