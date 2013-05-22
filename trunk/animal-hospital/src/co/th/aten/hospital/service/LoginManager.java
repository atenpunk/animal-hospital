/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.hospital.service;

import co.th.aten.hospital.dao.UserDao;
import co.th.aten.hospital.model.UserModel;

/**
 *
 * @author Atenpunk
 */
public interface LoginManager {

    public UserModel login(String user,String pass);

    public UserDao getUserDao();

    public void setUserDao(UserDao userDao);
}
