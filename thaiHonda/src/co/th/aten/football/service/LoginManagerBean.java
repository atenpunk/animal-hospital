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
public class LoginManagerBean implements LoginManager {

    private UserDao userDao;

    public UserModel login(String user,String pass){
        return userDao.login(user, pass);
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}
