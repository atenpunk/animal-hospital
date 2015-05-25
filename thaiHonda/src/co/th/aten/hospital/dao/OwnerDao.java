/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.hospital.dao;

import co.th.aten.hospital.model.PlayersModel;
import java.util.List;
import javax.sql.DataSource;
/**
 *
 * @author Atenpunk
 */
public interface OwnerDao {

    public DataSource getDataSource();

    public void setDataSource(DataSource dataSource);

    public boolean insertOwner(PlayersModel playersModel);

    public int getMaxOwnerId();

    public List<PlayersModel> searchByKeyWord(String word);

    public boolean updateOwner(PlayersModel playersModel);
}
