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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "member_customer")
@NamedQueries({
    @NamedQuery(name = "MemberCustomer.findAll", query = "SELECT m FROM MemberCustomer m")})
public class MemberCustomer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "customer_member")
    private String customerMember;
    @Column(name = "upper_id")
    private Integer upperId;
    @Column(name = "lower_left_id")
    private Integer lowerLeftId;
    @Column(name = "lower_right_id")
    private Integer lowerRightId;
    @Column(name = "recommend_id")
    private Integer recommendId;
    @Column(name = "score")
    private Integer score;
    @Column(name = "regis_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regisDate;
    @Column(name = "title_name")
    private String titleName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "status")
    private Integer status;
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
    @Column(name = "sex")
    private Integer sex;
    @Column(name = "birthDay")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDay;
    @Column(name = "personal_Id")
    private String personalId;
    @Column(name = "company_ID")
    private String companyID;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "line_id")
    private String lineId;
    @Column(name = "email")
    private String email;
    @Column(name = "address_No")
    private String addressNo;
    @Column(name = "address_Building")
    private String addressBuilding;
    @Column(name = "address_Village")
    private String addressVillage;
    @Column(name = "address_Lane")
    private String addressLane;
    @Column(name = "address_Road")
    private String addressRoad;
    @Column(name = "province_Id")
    private Integer provinceId;
    @Column(name = "amphur_Id")
    private Integer amphurId;
    @Column(name = "district_Id")
    private Integer districtId;
    @Column(name = "address_No_SendDoc")
    private String addressNoSendDoc;
    @Column(name = "address_Building_SendDoc")
    private String addressBuildingSendDoc;
    @Column(name = "address_Village_SendDoc")
    private String addressVillageSendDoc;
    @Column(name = "address_Lane_SendDoc")
    private String addressLaneSendDoc;
    @Column(name = "address_Road_SendDoc")
    private String addressRoadSendDoc;
    @Column(name = "province_Id_SendDoc")
    private Integer provinceIdSendDoc;
    @Column(name = "amphur_Id_SendDoc")
    private Integer amphurIdSendDoc;
    @Column(name = "district_Id_SendDoc")
    private Integer districtIdSendDoc;
    @Column(name = "chk_Same_Address")
    private Integer chkSameAddress;
    @Column(name = "bank_Id")
    private Integer bankId;
    @Column(name = "bank_branch")
    private String bankBranch;
    @Column(name = "bank_account_Type")
    private Integer bankaccountType;
    @Column(name = "bank_account_No")
    private String bankaccountNo;
    @Column(name = "bank_account_Name")
    private String bankaccountName;
    @Column(name = "remark")
    private String remark;
    @Column(name = "receive_Document")
    private Integer receiveDocument;
    @Column(name = "date_Document_Fully")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDocumentFully;
    @Column(name = "date_Copy_Personal_Card")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCopyPersonalCard;
    @Column(name = "date_Copy_Book_Bank")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCopyBookBank;
    @Column(name = "business_name")
    private String businessName;
    @Column(name = "chk_Document_Fully")
    private Integer chkDocumentFully;
    @Column(name = "chk_Copy_Personal_Card")
    private Integer chkCopyPersonalCard;
    @Column(name = "chk_Copy_Book_Bank")
    private Integer chkCopyBookBank;
    @Column(name = "side")
    private Integer side;
    @Column(name = "eMoney")
    private BigDecimal eMoney;
    @Column(name = "matching_use")
    private Integer matchingUse;
    @Column(name = "province_str")
    private String provinceStr;
    @Column(name = "amphur_str")
    private String amphurStr;
    @Column(name = "district_str")
    private String districtStr;
    @Column(name = "post_code_str")
    private String postCodeStr;
    @Lob
    @Column(name = "image_member")
    private byte[] imageMember;
    @Column(name = "image_member_name")
    private String imageMemberName;
    @JoinColumn(name = "nation_id", referencedColumnName = "nation_id")
    @ManyToOne
    private MasterNationality nationId;
    @JoinColumn(name = "position_id", referencedColumnName = "position_id")
    @ManyToOne
    private MemberPosition positionId;

    public MemberCustomer() {
    }

    public MemberCustomer(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerMember() {
        return customerMember;
    }

    public void setCustomerMember(String customerMember) {
        this.customerMember = customerMember;
    }

    public Integer getUpperId() {
        return upperId;
    }

    public void setUpperId(Integer upperId) {
        this.upperId = upperId;
    }

    public Integer getLowerLeftId() {
        return lowerLeftId;
    }

    public void setLowerLeftId(Integer lowerLeftId) {
        this.lowerLeftId = lowerLeftId;
    }

    public Integer getLowerRightId() {
        return lowerRightId;
    }

    public void setLowerRightId(Integer lowerRightId) {
        this.lowerRightId = lowerRightId;
    }

    public Integer getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Integer recommendId) {
        this.recommendId = recommendId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getRegisDate() {
        return regisDate;
    }

    public void setRegisDate(Date regisDate) {
        this.regisDate = regisDate;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAddressNoSendDoc() {
        return addressNoSendDoc;
    }

    public void setAddressNoSendDoc(String addressNoSendDoc) {
        this.addressNoSendDoc = addressNoSendDoc;
    }

    public String getAddressBuildingSendDoc() {
        return addressBuildingSendDoc;
    }

    public void setAddressBuildingSendDoc(String addressBuildingSendDoc) {
        this.addressBuildingSendDoc = addressBuildingSendDoc;
    }

    public String getAddressVillageSendDoc() {
        return addressVillageSendDoc;
    }

    public void setAddressVillageSendDoc(String addressVillageSendDoc) {
        this.addressVillageSendDoc = addressVillageSendDoc;
    }

    public String getAddressLaneSendDoc() {
        return addressLaneSendDoc;
    }

    public void setAddressLaneSendDoc(String addressLaneSendDoc) {
        this.addressLaneSendDoc = addressLaneSendDoc;
    }

    public String getAddressRoadSendDoc() {
        return addressRoadSendDoc;
    }

    public void setAddressRoadSendDoc(String addressRoadSendDoc) {
        this.addressRoadSendDoc = addressRoadSendDoc;
    }

    public Integer getProvinceIdSendDoc() {
        return provinceIdSendDoc;
    }

    public void setProvinceIdSendDoc(Integer provinceIdSendDoc) {
        this.provinceIdSendDoc = provinceIdSendDoc;
    }

    public Integer getAmphurIdSendDoc() {
        return amphurIdSendDoc;
    }

    public void setAmphurIdSendDoc(Integer amphurIdSendDoc) {
        this.amphurIdSendDoc = amphurIdSendDoc;
    }

    public Integer getDistrictIdSendDoc() {
        return districtIdSendDoc;
    }

    public void setDistrictIdSendDoc(Integer districtIdSendDoc) {
        this.districtIdSendDoc = districtIdSendDoc;
    }

    public Integer getChkSameAddress() {
        return chkSameAddress;
    }

    public void setChkSameAddress(Integer chkSameAddress) {
        this.chkSameAddress = chkSameAddress;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public Integer getBankaccountType() {
        return bankaccountType;
    }

    public void setBankaccountType(Integer bankaccountType) {
        this.bankaccountType = bankaccountType;
    }

    public String getBankaccountNo() {
        return bankaccountNo;
    }

    public void setBankaccountNo(String bankaccountNo) {
        this.bankaccountNo = bankaccountNo;
    }

    public String getBankaccountName() {
        return bankaccountName;
    }

    public void setBankaccountName(String bankaccountName) {
        this.bankaccountName = bankaccountName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getReceiveDocument() {
        return receiveDocument;
    }

    public void setReceiveDocument(Integer receiveDocument) {
        this.receiveDocument = receiveDocument;
    }

    public Date getDateDocumentFully() {
        return dateDocumentFully;
    }

    public void setDateDocumentFully(Date dateDocumentFully) {
        this.dateDocumentFully = dateDocumentFully;
    }

    public Date getDateCopyPersonalCard() {
        return dateCopyPersonalCard;
    }

    public void setDateCopyPersonalCard(Date dateCopyPersonalCard) {
        this.dateCopyPersonalCard = dateCopyPersonalCard;
    }

    public Date getDateCopyBookBank() {
        return dateCopyBookBank;
    }

    public void setDateCopyBookBank(Date dateCopyBookBank) {
        this.dateCopyBookBank = dateCopyBookBank;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getChkDocumentFully() {
        return chkDocumentFully;
    }

    public void setChkDocumentFully(Integer chkDocumentFully) {
        this.chkDocumentFully = chkDocumentFully;
    }

    public Integer getChkCopyPersonalCard() {
        return chkCopyPersonalCard;
    }

    public void setChkCopyPersonalCard(Integer chkCopyPersonalCard) {
        this.chkCopyPersonalCard = chkCopyPersonalCard;
    }

    public Integer getChkCopyBookBank() {
        return chkCopyBookBank;
    }

    public void setChkCopyBookBank(Integer chkCopyBookBank) {
        this.chkCopyBookBank = chkCopyBookBank;
    }

    public Integer getSide() {
        return side;
    }

    public void setSide(Integer side) {
        this.side = side;
    }

    public BigDecimal geteMoney() {
        return eMoney;
    }

    public void seteMoney(BigDecimal eMoney) {
        this.eMoney = eMoney;
    }

    public Integer getMatchingUse() {
        return matchingUse;
    }

    public void setMatchingUse(Integer matchingUse) {
        this.matchingUse = matchingUse;
    }

    public MasterNationality getNationId() {
        return nationId;
    }

    public void setNationId(MasterNationality nationId) {
        this.nationId = nationId;
    }

    public MemberPosition getPositionId() {
        return positionId;
    }

    public void setPositionId(MemberPosition positionId) {
        this.positionId = positionId;
    }

    public String getProvinceStr() {
		return provinceStr;
	}

	public void setProvinceStr(String provinceStr) {
		this.provinceStr = provinceStr;
	}

	public String getAmphurStr() {
		return amphurStr;
	}

	public void setAmphurStr(String amphurStr) {
		this.amphurStr = amphurStr;
	}

	public String getDistrictStr() {
		return districtStr;
	}

	public void setDistrictStr(String districtStr) {
		this.districtStr = districtStr;
	}

	public String getPostCodeStr() {
		return postCodeStr;
	}

	public void setPostCodeStr(String postCodeStr) {
		this.postCodeStr = postCodeStr;
	}
	
	public byte[] getImageMember() {
        return imageMember;
    }

    public void setImageMember(byte[] imageMember) {
        this.imageMember = imageMember;
    }

    public String getImageMemberName() {
        return imageMemberName;
    }

    public void setImageMemberName(String imageMemberName) {
        this.imageMemberName = imageMemberName;
    }

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemberCustomer)) {
            return false;
        }
        MemberCustomer other = (MemberCustomer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.MemberCustomer[customerId=" + customerId + "]";
    }

}
