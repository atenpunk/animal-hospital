/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.hospital.dao;

import co.th.aten.hospital.model.PetModel;
import javax.sql.DataSource;
/**
 *
 * @author Atenpunk
 */
public interface PetDao {

    public DataSource getDataSource();

    public void setDataSource(DataSource dataSource);

    public int getMaxPetId();

    public boolean insertPet(PetModel petModel);

    public boolean updatePet(PetModel petModel);
}
