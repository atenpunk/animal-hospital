package co.th.aten.network.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CustomerModel implements Serializable {
	
	private int cusId;
    private String customerId;
    private int upperId;
    private int lowerLeftId;
    private int lowerRightId;
    private int directId;
    private int positionId;
    private String positionImage;
    private int score;
    private Date regisDate;
    private String name;
    private String firstName;
    private String lastName;
    private int status;
    private int createBy;
    private Date createDate;
    private int updateBy;
    private Date updateDate;
    private List<DetailModel> detailModelList;
    private String flagImg01;
    private String flagImg02;
    private String flagImg03;
    
	public int getCusId() {
		return cusId;
	}
	public void setCusId(int cusId) {
		this.cusId = cusId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public int getUpperId() {
		return upperId;
	}
	public void setUpperId(int upperId) {
		this.upperId = upperId;
	}
	public int getLowerLeftId() {
		return lowerLeftId;
	}
	public void setLowerLeftId(int lowerLeftId) {
		this.lowerLeftId = lowerLeftId;
	}
	public int getLowerRightId() {
		return lowerRightId;
	}
	public void setLowerRightId(int lowerRightId) {
		this.lowerRightId = lowerRightId;
	}
	public int getDirectId() {
		return directId;
	}
	public void setDirectId(int directId) {
		this.directId = directId;
	}
	public int getPositionId() {
		return positionId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Date getRegisDate() {
		return regisDate;
	}
	public void setRegisDate(Date regisDate) {
		this.regisDate = regisDate;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCreateBy() {
		return createBy;
	}
	public void setCreateBy(int createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(int updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<DetailModel> getDetailModelList() {
		return detailModelList;
	}
	public void setDetailModelList(List<DetailModel> detailModelList) {
		this.detailModelList = detailModelList;
	}
	public String getPositionImage() {
		return positionImage;
	}
	public void setPositionImage(String positionImage) {
		this.positionImage = positionImage;
	}
	public String getFlagImg01() {
		return flagImg01;
	}
	public void setFlagImg01(String flagImg01) {
		this.flagImg01 = flagImg01;
	}
	public String getFlagImg02() {
		return flagImg02;
	}
	public void setFlagImg02(String flagImg02) {
		this.flagImg02 = flagImg02;
	}
	public String getFlagImg03() {
		return flagImg03;
	}
	public void setFlagImg03(String flagImg03) {
		this.flagImg03 = flagImg03;
	}	
	
}
