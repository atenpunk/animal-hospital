/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.service;

import co.th.aten.hospital.model.PlayersModel;
import co.th.aten.hospital.model.UserModel;


/**
 *
 * @author Aten
 */
public interface SessionManager {
    
    public UserModel getUser();

    public void setUser(UserModel user);

    public PlayersModel getPetModel();

    public void setPetModel(PlayersModel petModel);
    
}
