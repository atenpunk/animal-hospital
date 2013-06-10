/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.hospital.service;

import co.th.aten.hospital.dao.PetDao;
import co.th.aten.hospital.model.PetModel;

/**
 *
 * @author Atenpunk
 */
public class PetManagerBean implements PetManager {

    private PetDao petDao;

    public boolean insertOwner(PetModel petModel){
        return petDao.insertPet(petModel);
    }

    public int getMaxOwnerId(){
        return petDao.getMaxPetId();
    }

    public PetDao getPetDao() {
        return petDao;
    }

    public void setPetDao(PetDao petDao) {
        this.petDao = petDao;
    }

}
