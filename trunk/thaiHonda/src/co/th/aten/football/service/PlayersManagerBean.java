/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.service;

import co.th.aten.football.dao.PlayersDao;
import co.th.aten.football.model.PlayersModel;
import java.util.List;

/**
 *
 * @author Atenpunk
 */
public class PlayersManagerBean implements PlayersManager {

    private PlayersDao playersDao;

    public boolean insertPlayers(PlayersModel playersModel){
        return playersDao.insertPlayers(playersModel);
    }

    public int getMaxPlayersId(){
        return playersDao.getMaxPlayersId();
    }

    public List<PlayersModel> searchByKeyWord(String word){
        return playersDao.searchByKeyWord(word);
    }

    public boolean updatePlayers(PlayersModel playersModel){
        return playersDao.updatePlayers(playersModel);
    }

    public PlayersDao getPlayersDao() {
        return playersDao;
    }

    public void setPlayersDao(PlayersDao playersDao) {
        this.playersDao = playersDao;
    }
    
    public boolean editPlayers(PlayersModel playersModel){
        return playersDao.editPlayers(playersModel);
    }
    
}
