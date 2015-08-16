/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.network.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "transaction_sell_header")
@NamedQueries({
    @NamedQuery(name = "TransactionSellHeader.findAll", query = "SELECT t FROM TransactionSellHeader t")})
public class TransactionSellHeader implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "trx_header_id")
    private Integer trxHeaderId;
    @Column(name = "trx_header_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trxHeaderDatetime;
    @Column(name = "receipt_no")
    private String receiptNo;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "total_pv")
    private BigDecimal totalPv;
    @Column(name = "total_bv")
    private BigDecimal totalBv;
    @Lob
    @Column(name = "remark")
    private String remark;
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
    @Column(name = "trx_header_status")
    private Integer trxHeaderStatus;
    @Column(name = "trx_header_flag")
    private Integer trxHeaderFlag;
    @Column(name = "send_status")
    private Integer sendStatus;
    @Column(name = "buy_status")
    private Integer buyStatus;
    @Column(name = "nation_id")
    private Integer nationId;
    @Column(name = "address_no")
    private String addressNo;
    @Column(name = "address_building")
    private String addressBuilding;
    @Column(name = "address_village")
    private String addressVillage;
    @Column(name = "address_lane")
    private String addressLane;
    @Column(name = "address_road")
    private String addressRoad;
    @Column(name = "province_id")
    private Integer provinceId;
    @Column(name = "amphur_id")
    private Integer amphurId;
    @Column(name = "district_id")
    private Integer districtId;
    @Column(name = "post_code")
    private String postCode;
    @Column(name = "province_lao")
    private String provinceLao;
    @Column(name = "amphur_lao")
    private String amphurLao;
    @Column(name = "district_lao")
    private String districtLao;

    public TransactionSellHeader() {
    }

    public TransactionSellHeader(Integer trxHeaderId) {
        this.trxHeaderId = trxHeaderId;
    }

    public Integer getTrxHeaderId() {
        return trxHeaderId;
    }

    public void setTrxHeaderId(Integer trxHeaderId) {
        this.trxHeaderId = trxHeaderId;
    }

    public Date getTrxHeaderDatetime() {
        return trxHeaderDatetime;
    }

    public void setTrxHeaderDatetime(Date trxHeaderDatetime) {
        this.trxHeaderDatetime = trxHeaderDatetime;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPv() {
        return totalPv;
    }

    public void setTotalPv(BigDecimal totalPv) {
        this.totalPv = totalPv;
    }

    public BigDecimal getTotalBv() {
        return totalBv;
    }

    public void setTotalBv(BigDecimal totalBv) {
        this.totalBv = totalBv;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getTrxHeaderStatus() {
        return trxHeaderStatus;
    }

    public void setTrxHeaderStatus(Integer trxHeaderStatus) {
        this.trxHeaderStatus = trxHeaderStatus;
    }

    public Integer getTrxHeaderFlag() {
        return trxHeaderFlag;
    }

    public void setTrxHeaderFlag(Integer trxHeaderFlag) {
        this.trxHeaderFlag = trxHeaderFlag;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Integer getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(Integer buyStatus) {
        this.buyStatus = buyStatus;
    }

    public String getAddressNo() {
        return addressNo;
    }

    public void setAddressNo(String addressNo) {
        this.addressNo = addressNo;
    }

    public String getAddressBuilding() {
        return addressBuilding;
    }

    public void setAddressBuilding(String addressBuilding) {
        this.addressBuilding = addressBuilding;
    }

    public String getAddressVillage() {
        return addressVillage;
    }

    public void setAddressVillage(String addressVillage) {
        this.addressVillage = addressVillage;
    }

    public String getAddressLane() {
        return addressLane;
    }

    public void setAddressLane(String addressLane) {
        this.addressLane = addressLane;
    }

    public String getAddressRoad() {
        return addressRoad;
    }

    public void setAddressRoad(String addressRoad) {
        this.addressRoad = addressRoad;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getAmphurId() {
        return amphurId;
    }

    public void setAmphurId(Integer amphurId) {
        this.amphurId = amphurId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getProvinceLao() {
        return provinceLao;
    }

    public void setProvinceLao(String provinceLao) {
        this.provinceLao = provinceLao;
    }

    public String getAmphurLao() {
        return amphurLao;
    }

    public void setAmphurLao(String amphurLao) {
        this.amphurLao = amphurLao;
    }

    public String getDistrictLao() {
        return districtLao;
    }

    public void setDistrictLao(String districtLao) {
        this.districtLao = districtLao;
    }

    public Integer getNationId() {
		return nationId;
	}

	public void setNationId(Integer nationId) {
		this.nationId = nationId;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (trxHeaderId != null ? trxHeaderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionSellHeader)) {
            return false;
        }
        TransactionSellHeader other = (TransactionSellHeader) object;
        if ((this.trxHeaderId == null && other.trxHeaderId != null) || (this.trxHeaderId != null && !this.trxHeaderId.equals(other.trxHeaderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.TransactionSellHeader[trxHeaderId=" + trxHeaderId + "]";
    }

}
