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
public interface PetManager {
    public boolean insertOwner(PetModel petModel);

    public int getMaxOwnerId();

    public PetDao getPetDao();

    public void setPetDao(PetDao petDao);
}
