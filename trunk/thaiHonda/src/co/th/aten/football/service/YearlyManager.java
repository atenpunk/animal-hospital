/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.service;

import co.th.aten.football.model.YearlyModel;
import java.util.List;

/**
 *
 * @author Atenpunk
 */
public interface YearlyManager {

    public boolean insertYearly(YearlyModel yearlyModel);

    public List<YearlyModel> getYearlyList(int playerId);

    public boolean updateYearly(YearlyModel yearlyModel);
    
    public boolean deleteYearly(int playerId);
}
