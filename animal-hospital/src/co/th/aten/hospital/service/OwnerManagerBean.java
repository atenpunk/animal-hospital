/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.hospital.service;

import co.th.aten.hospital.dao.OwnerDao;
import co.th.aten.hospital.model.OwnerModel;

/**
 *
 * @author Atenpunk
 */
public class OwnerManagerBean implements OwnerManager {

    private OwnerDao ownerDao;

    public boolean insertOwner(OwnerModel ownerModel){
        return ownerDao.insertOwner(ownerModel);
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
