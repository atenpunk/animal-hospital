/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.football.dao;


import co.th.aten.football.model.UserModel;
import javax.sql.DataSource;
/**
 *
 * @author Atenpunk
 */
public interface UserDao {

    public DataSource getDataSource();

    public void setDataSource(DataSource dataSource);

    public UserModel login(String user,String pass);
}
