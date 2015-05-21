/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.hospital.dao;


import co.th.aten.hospital.model.OwnerModel;
import java.util.List;
import javax.sql.DataSource;
/**
 *
 * @author Atenpunk
 */
public interface OwnerDao {

    public DataSource getDataSource();

    public void setDataSource(DataSource dataSource);

    public boolean insertOwner(OwnerModel ownerModel);

    public int getMaxOwnerId();

    public List<OwnerModel> searchByKeyWord(String word);

    public boolean updateOwner(OwnerModel ownerModel);
}
