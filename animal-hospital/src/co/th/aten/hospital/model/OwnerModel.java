/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.hospital.model;

import java.util.List;

/**
 *
 * @author Atenpunk
 */
public class OwnerModel {

    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private PetModel petModel;
    private List<PetModel> petList;

    public PetModel getPetModel() {
        return petModel;
    }

    public void setPetModel(PetModel petModel) {
        this.petModel = petModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PetModel> getPetList() {
        return petList;
    }

    public void setPetList(List<PetModel> petList) {
        this.petList = petList;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
}
