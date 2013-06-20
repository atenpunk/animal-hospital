/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.model;

import java.util.Date;

/**
 *
 * @author Atenpunk
 */
public class PetModel {

    private int id;
    private int ownerId;
    private String name;
    private String type;
    private String breed;
    private String sex;
    private String color;
    private byte[] image;
    private String createBy;
    private Date createDate;
    private String updateBy;
    private Date updateDate;
    private Date birthdayPet;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getBirthdayPet() {
        return birthdayPet;
    }

    public void setBirthdayPet(Date birthdayPet) {
        this.birthdayPet = birthdayPet;
    }
    

}
