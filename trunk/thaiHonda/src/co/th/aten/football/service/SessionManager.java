/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.service;

import co.th.aten.football.model.PlayersModel;
import co.th.aten.football.model.UserModel;


/**
 *
 * @author Aten
 */
public interface SessionManager {
    
    public UserModel getUser();

    public void setUser(UserModel user);

    public PlayersModel getPlayerModel();

    public void setPlayerModel(PlayersModel playerModel);
    
}
