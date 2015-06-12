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
public class UserManagerBean implements UserManager {

    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public int getMaxUserId(){
        return userDao.getMaxUserId();
    }
    
    public boolean insertStaff(UserModel userModel){
        return userDao.insertStaff(userModel);
    }
    
    public boolean changePassword(int userId, String password){
        return userDao.changePassword(userId, password);
    }
}
