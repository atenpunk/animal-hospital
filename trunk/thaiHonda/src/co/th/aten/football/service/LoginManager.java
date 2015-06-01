/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.football.service;

import co.th.aten.football.dao.UserDao;
import co.th.aten.football.model.UserModel;

/**
 *
 * @author Atenpunk
 */
public interface LoginManager {

    public UserModel login(String user,String pass);

    public UserDao getUserDao();

    public void setUserDao(UserDao userDao);
}
