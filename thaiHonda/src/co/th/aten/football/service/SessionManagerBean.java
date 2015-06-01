/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.service;

import co.th.aten.football.model.PlayersModel;
import co.th.aten.football.model.UserModel;
import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Aten
 */
public class SessionManagerBean implements Serializable, SessionManager {

    private final Log logger = LogFactory.getLog(getClass());
    private UserModel user;
    private PlayersModel playerModel;

    public SessionManagerBean() {
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public PlayersModel getPlayerModel() {
        return playerModel;
    }

    public void setPlayerModel(PlayersModel playerModel) {
        this.playerModel = playerModel;
    }


}
