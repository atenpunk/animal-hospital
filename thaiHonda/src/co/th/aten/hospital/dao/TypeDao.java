/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.hospital.dao;

import co.th.aten.hospital.model.TypeModel;
import java.util.List;
import javax.sql.DataSource;
/**
 *
 * @author Atenpunk
 */
public interface TypeDao {

    public DataSource getDataSource();

    public void setDataSource(DataSource dataSource);

    public List<TypeModel> getTypeList();
}
