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
public class PositionManagerBean implements PositionManager {

    private PositionDao positionDao;

    public List<PositionModel> getPositionList(){
        return positionDao.getTypeList();
    }

    public PositionDao getPositionDao() {
        return positionDao;
    }

    public void setPositionDao(PositionDao positionDao) {
        this.positionDao = positionDao;
    }
    
}
