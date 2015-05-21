/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.dao;

import co.th.aten.hospital.model.TreatmentHistoryModel;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author Atenpunk
 */
public interface TreatmentHistoryDao {

    public DataSource getDataSource();

    public void setDataSource(DataSource dataSource);

    public int getMaxHistoryId();

    public boolean insertHistory(TreatmentHistoryModel model);

    public List<TreatmentHistoryModel> getHistoryListByPetId(int petId);
}
