/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.service;

import co.th.aten.hospital.dao.PositionDao;
import co.th.aten.hospital.model.PositionModel;
import java.util.List;

/**
 *
 * @author Atenpunk
 */
public interface PositionManager {

    public List<PositionModel> getPositionList();
    public PositionDao getPositionDao();
    public void setPositionDao(PositionDao positionDao);
}
