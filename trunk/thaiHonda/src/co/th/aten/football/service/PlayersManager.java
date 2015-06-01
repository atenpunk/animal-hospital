/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.service;

import co.th.aten.football.model.PlayersModel;
import java.util.List;

/**
 *
 * @author Atenpunk
 */
public interface PlayersManager {

    public boolean insertPlayers(PlayersModel playersModel);

    public int getMaxPlayersId();

    public List<PlayersModel> searchByKeyWord(String word);

    public boolean updatePlayers(PlayersModel playersModel);
}
