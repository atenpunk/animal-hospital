/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.football.dao;

import co.th.aten.hospital.model.PlayersModel;
import java.util.List;
import javax.sql.DataSource;
/**
 *
 * @author Atenpunk
 */
public interface PlayersDao {

    public DataSource getDataSource();

    public void setDataSource(DataSource dataSource);

    public boolean insertPlayers(PlayersModel playersModel);

    public int getMaxPlayersId();

    public List<PlayersModel> searchByKeyWord(String word);

    public boolean updatePlayers(PlayersModel playersModel);
}
