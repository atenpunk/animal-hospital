/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.service;

import co.th.aten.football.dao.PositionDao;
import co.th.aten.football.model.PositionModel;
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
