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
 * @author Atenpunk
 */
@Entity
@Table(name = "member_customer")
@NamedQueries({
    @NamedQuery(name = "MemberCustomer.findAll", query = "SELECT m FROM MemberCustomer m"),
    @NamedQuery(name = "MemberCustomer.findByCustomerId", query = "SELECT m FROM MemberCustomer m WHERE m.customerId = :customerId"),
    @NamedQuery(name = "MemberCustomer.findByCustomerMember", query = "SELECT m FROM MemberCustomer m WHERE m.customerMember = :customerMember"),
    @NamedQuery(name = "MemberCustomer.findByUpperId", query = "SELECT m FROM MemberCustomer m WHERE m.upperId = :upperId"),
    @NamedQuery(name = "MemberCustomer.findByLowerLeftId", query = "SELECT m FROM MemberCustomer m WHERE m.lowerLeftId = :lowerLeftId"),
    @NamedQuery(name = "MemberCustomer.findByLowerRightId", query = "SELECT m FROM MemberCustomer m WHERE m.lowerRightId = :lowerRightId"),
    @NamedQuery(name = "MemberCustomer.findByRecommendId", query = "SELECT m FROM MemberCustomer m WHERE m.recommendId = :recommendId"),
    @NamedQuery(name = "MemberCustomer.findByPositionId", query = "SELECT m FROM MemberCustomer m WHERE m.positionId = :positionId"),
    @NamedQuery(name = "MemberCustomer.findByScore", query = "SELECT m FROM MemberCustomer m WHERE m.score = :score"),
    @NamedQuery(name = "MemberCustomer.findByRegisDate", query = "SELECT m FROM MemberCustomer m WHERE m.regisDate = :regisDate"),
    @NamedQuery(name = "MemberCustomer.findByTitleName", query = "SELECT m FROM MemberCustomer m WHERE m.titleName = :titleName"),
    @NamedQuery(name = "MemberCustomer.findByFirstName", query = "SELECT m FROM MemberCustomer m WHERE m.firstName = :firstName"),
    @NamedQuery(name = "MemberCustomer.findByLastName", query = "SELECT m FROM MemberCustomer m WHERE m.lastName = :lastName"),
    @NamedQuery(name = "MemberCustomer.findByStatus", query = "SELECT m FROM MemberCustomer m WHERE m.status = :status"),
    @NamedQuery(name = "MemberCustomer.findByCreateBy", query = "SELECT m FROM MemberCustomer m WHERE m.createBy = :createBy"),
    @NamedQuery(name = "MemberCustomer.findByCreateDate", query = "SELECT m FROM MemberCustomer m WHERE m.createDate = :createDate"),
    @NamedQuery(name = "MemberCustomer.findByUpdateBy", query = "SELECT m FROM MemberCustomer m WHERE m.updateBy = :updateBy"),
    @NamedQuery(name = "MemberCustomer.findByUpdateDate", query = "SELECT m FROM MemberCustomer m WHERE m.updateDate = :updateDate"),
    @NamedQuery(name = "MemberCustomer.findBySex", query = "SELECT m FROM MemberCustomer m WHERE m.sex = :sex"),
    @NamedQuery(name = "MemberCustomer.findByBirthDay", query = "SELECT m FROM MemberCustomer m WHERE m.birthDay = :birthDay"),
    @NamedQuery(name = "MemberCustomer.findByNationality", query = "SELECT m FROM MemberCustomer m WHERE m.nationality = :nationality"),
    @NamedQuery(name = "MemberCustomer.findByPersonalId", query = "SELECT m FROM MemberCustomer m WHERE m.personalId = :personalId"),
    @NamedQuery(name = "MemberCustomer.findByCompanyID", query = "SELECT m FROM MemberCustomer m WHERE m.companyID = :companyID"),
    @NamedQuery(name = "MemberCustomer.findByTelephone", query = "SELECT m FROM MemberCustomer m WHERE m.telephone = :telephone"),
    @NamedQuery(name = "MemberCustomer.findByMobile", query = "SELECT m FROM MemberCustomer m WHERE m.mobile = :mobile"),
    @NamedQuery(name = "MemberCustomer.findByFax", query = "SELECT m FROM MemberCustomer m WHERE m.fax = :fax"),
    @NamedQuery(name = "MemberCustomer.findByEmail", query = "SELECT m FROM MemberCustomer m WHERE m.email = :email"),
    @NamedQuery(name = "MemberCustomer.findByAddressNo", query = "SELECT m FROM MemberCustomer m WHERE m.addressNo = :addressNo"),
    @NamedQuery(name = "MemberCustomer.findByAddressBuilding", query = "SELECT m FROM MemberCustomer m WHERE m.addressBuilding = :addressBuilding"),
    @NamedQuery(name = "MemberCustomer.findByAddressVillage", query = "SELECT m FROM MemberCustomer m WHERE m.addressVillage = :addressVillage"),
    @NamedQuery(name = "MemberCustomer.findByAddressLane", query = "SELECT m FROM MemberCustomer m WHERE m.addressLane = :addressLane"),
    @NamedQuery(name = "MemberCustomer.findByAddressRoad", query = "SELECT m FROM MemberCustomer m WHERE m.addressRoad = :addressRoad"),
    @NamedQuery(name = "MemberCustomer.findByProvinceId", query = "SELECT m FROM MemberCustomer m WHERE m.provinceId = :provinceId"),
    @NamedQuery(name = "MemberCustomer.findByAmphurId", query = "SELECT m FROM MemberCustomer m WHERE m.amphurId = :amphurId"),
    @NamedQuery(name = "MemberCustomer.findByDistrictId", query = "SELECT m FROM MemberCustomer m WHERE m.districtId = :districtId"),
    @NamedQuery(name = "MemberCustomer.findByAddressNoSendDoc", query = "SELECT m FROM MemberCustomer m WHERE m.addressNoSendDoc = :addressNoSendDoc"),
    @NamedQuery(name = "MemberCustomer.findByAddressBuildingSendDoc", query = "SELECT m FROM MemberCustomer m WHERE m.addressBuildingSendDoc = :addressBuildingSendDoc"),
    @NamedQuery(name = "MemberCustomer.findByAddressVillageSendDoc", query = "SELECT m FROM MemberCustomer m WHERE m.addressVillageSendDoc = :addressVillageSendDoc"),
    @NamedQuery(name = "MemberCustomer.findByAddressLaneSendDoc", query = "SELECT m FROM MemberCustomer m WHERE m.addressLaneSendDoc = :addressLaneSendDoc"),
    @NamedQuery(name = "MemberCustomer.findByAddressRoadSendDoc", query = "SELECT m FROM MemberCustomer m WHERE m.addressRoadSendDoc = :addressRoadSendDoc"),
    @NamedQuery(name = "MemberCustomer.findByProvinceIdSendDoc", query = "SELECT m FROM MemberCustomer m WHERE m.provinceIdSendDoc = :provinceIdSendDoc"),
    @NamedQuery(name = "MemberCustomer.findByAmphurIdSendDoc", query = "SELECT m FROM MemberCustomer m WHERE m.amphurIdSendDoc = :amphurIdSendDoc"),
    @NamedQuery(name = "MemberCustomer.findByDistrictIdSendDoc", query = "SELECT m FROM MemberCustomer m WHERE m.districtIdSendDoc = :districtIdSendDoc"),
    @NamedQuery(name = "MemberCustomer.findByChkSameAddress", query = "SELECT m FROM MemberCustomer m WHERE m.chkSameAddress = :chkSameAddress"),
    @NamedQuery(name = "MemberCustomer.findByBankId", query = "SELECT m FROM MemberCustomer m WHERE m.bankId = :bankId"),
    @NamedQuery(name = "MemberCustomer.findByBankBranch", query = "SELECT m FROM MemberCustomer m WHERE m.bankBranch = :bankBranch"),
    @NamedQuery(name = "MemberCustomer.findByBankaccountType", query = "SELECT m FROM MemberCustomer m WHERE m.bankaccountType = :bankaccountType"),
    @NamedQuery(name = "MemberCustomer.findByBankaccountNo", query = "SELECT m FROM MemberCustomer m WHERE m.bankaccountNo = :bankaccountNo"),
    @NamedQuery(name = "MemberCustomer.findByBankaccountName", query = "SELECT m FROM MemberCustomer m WHERE m.bankaccountName = :bankaccountName"),
    @NamedQuery(name = "MemberCustomer.findByRemark", query = "SELECT m FROM MemberCustomer m WHERE m.remark = :remark"),
    @NamedQuery(name = "MemberCustomer.findByReceiveDocument", query = "SELECT m FROM MemberCustomer m WHERE m.receiveDocument = :receiveDocument"),
    @NamedQuery(name = "MemberCustomer.findByDateDocumentFully", query = "SELECT m FROM MemberCustomer m WHERE m.dateDocumentFully = :dateDocumentFully"),
    @NamedQuery(name = "MemberCustomer.findByDateCopyPersonalCard", query = "SELECT m FROM MemberCustomer m WHERE m.dateCopyPersonalCard = :dateCopyPersonalCard"),
    @NamedQuery(name = "MemberCustomer.findByDateCopyBookBank", query = "SELECT m FROM MemberCustomer m WHERE m.dateCopyBookBank = :dateCopyBookBank")})
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
    @Column(name = "position_id")
    private Integer positionId;
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
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "personal_Id")
    private String personalId;
    @Column(name = "company_ID")
    private String companyID;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "fax")
    private String fax;
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

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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
