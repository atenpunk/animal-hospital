/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.service;

import co.th.aten.hospital.dao.BreedDao;
import co.th.aten.hospital.model.BreedModel;
import java.util.List;

/**
 *
 * @author Atenpunk
 */
public class BreedManagerBean implements BreedManager {

    private BreedDao breedDao;

    public List<BreedModel> getBreedListOrderByEngName(int typeId){
        return breedDao.getBreedListOrderByEngName(typeId);
    }

    public BreedDao getBreedDao() {
        return breedDao;
    }

    public void setBreedDao(BreedDao breedDao) {
        this.breedDao = breedDao;
    }

    

}
