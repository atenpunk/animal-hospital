/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.service;

import co.th.aten.hospital.dao.BreedDao;
import co.th.aten.hospital.dao.TreatmentHistoryDao;
import co.th.aten.hospital.model.BreedModel;
import co.th.aten.hospital.model.TreatmentHistoryModel;
import java.util.List;

/**
 *
 * @author Atenpunk
 */
public interface TreatmentHistoryManager {

    public int getMaxHistoryId();

    public boolean insertHistory(TreatmentHistoryModel model);

    public List<TreatmentHistoryModel> getHistoryListByPetId(int petId);

    public TreatmentHistoryDao getTreatmentHistoryDao();

    public void setTreatmentHistoryDao(TreatmentHistoryDao treatmentHistoryDao);
}
