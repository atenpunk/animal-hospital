/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.service;

import co.th.aten.football.dao.YearlyDao;
import co.th.aten.football.model.YearlyModel;
import java.util.List;

/**
 *
 * @author Atenpunk
 */
public class YearlyManagerBean implements YearlyManager {

    private YearlyDao yearlyDao;

    public boolean insertYearly(YearlyModel yearlyModel) {
        return yearlyDao.insertYearly(yearlyModel);
    }

    public List<YearlyModel> getYearlyList(int playerId) {
        return yearlyDao.getYearlyList(playerId);
    }

    public boolean updateYearly(YearlyModel yearlyModel) {
        return yearlyDao.updateYearly(yearlyModel);
    }

    public boolean deleteYearly(int playerId) {
        return yearlyDao.deleteYearly(playerId);
    }

    public YearlyDao getYearlyDao() {
        return yearlyDao;
    }

    public void setYearlyDao(YearlyDao yearlyDao) {
        this.yearlyDao = yearlyDao;
    }
    
    public List<Integer> getYearlyIdList(){
        return yearlyDao.getYearlyIdList();
    }
}
