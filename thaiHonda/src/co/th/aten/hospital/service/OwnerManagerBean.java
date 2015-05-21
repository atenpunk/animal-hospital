/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.hospital.service;

import co.th.aten.hospital.dao.OwnerDao;
import co.th.aten.hospital.model.OwnerModel;
import java.util.List;

/**
 *
 * @author Atenpunk
 */
public class OwnerManagerBean implements OwnerManager {

    private OwnerDao ownerDao;

    public List<OwnerModel> searchByKeyWord(String word){
        return ownerDao.searchByKeyWord(word);
    }

    public boolean insertOwner(OwnerModel ownerModel){
        return ownerDao.insertOwner(ownerModel);
    }

    public boolean updateOwner(OwnerModel ownerModel){
        return ownerDao.updateOwner(ownerModel);
    }

    public int getMaxOwnerId(){
        return ownerDao.getMaxOwnerId();
    }

    public OwnerDao getOwnerDao() {
        return ownerDao;
    }

    public void setOwnerDao(OwnerDao ownerDao) {
        this.ownerDao = ownerDao;
    }

    

}
