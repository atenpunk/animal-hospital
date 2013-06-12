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
public interface BreedManager {

    public List<BreedModel> getBreedListOrderByEngName(int typeId);

    public BreedDao getBreedDao();

    public void setBreedDao(BreedDao breedDao);
}
