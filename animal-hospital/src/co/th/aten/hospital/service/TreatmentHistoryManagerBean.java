/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.service;

import co.th.aten.hospital.dao.TreatmentHistoryDao;
import co.th.aten.hospital.model.TreatmentHistoryModel;
import java.util.List;

/**
 *
 * @author Atenpunk
 */
public class TreatmentHistoryManagerBean implements TreatmentHistoryManager {

    private TreatmentHistoryDao treatmentHistoryDao;

    public int getMaxHistoryId() {
        return treatmentHistoryDao.getMaxHistoryId();

    }

    public boolean insertHistory(TreatmentHistoryModel model) {
        return treatmentHistoryDao.insertHistory(model);
    }

    public List<TreatmentHistoryModel> getHistoryListByPetId(int petId) {
        return treatmentHistoryDao.getHistoryListByPetId(petId);
    }

    public TreatmentHistoryDao getTreatmentHistoryDao() {
        return treatmentHistoryDao;
    }

    public void setTreatmentHistoryDao(TreatmentHistoryDao treatmentHistoryDao) {
        this.treatmentHistoryDao = treatmentHistoryDao;
    }
}
