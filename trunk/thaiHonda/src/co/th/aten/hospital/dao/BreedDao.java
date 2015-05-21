/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.hospital.dao;

import co.th.aten.hospital.model.BreedModel;
import java.util.List;
import javax.sql.DataSource;
/**
 *
 * @author Atenpunk
 */
public interface BreedDao {

    public DataSource getDataSource();

    public void setDataSource(DataSource dataSource);

    public List<BreedModel> getBreedListOrderByEngName(int typeId);
}
