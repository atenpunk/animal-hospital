/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.football.dao;

import co.th.aten.football.model.PlayersModel;
import co.th.aten.football.model.YearlyModel;
import java.util.List;
import javax.sql.DataSource;
/**
 *
 * @author Atenpunk
 */
public interface YearlyDao {

    public DataSource getDataSource();

    public void setDataSource(DataSource dataSource);

    public boolean insertYearly(YearlyModel yearlyModel);

    public List<YearlyModel> getYearlyList(int playerId);

    public boolean updateYearly(YearlyModel yearlyModel);
    
    public boolean deleteYearly(int playerId);
}
