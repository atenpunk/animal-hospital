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
public interface OwnerManager {
    
    public List<OwnerModel> searchByKeyWord(String word);

    public OwnerDao getOwnerDao();

    public void setOwnerDao(OwnerDao ownerDao);

    public boolean insertOwner(OwnerModel ownerModel);

    public int getMaxOwnerId();
}
