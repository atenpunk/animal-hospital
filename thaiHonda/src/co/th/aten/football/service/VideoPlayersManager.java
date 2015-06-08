/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.service;

import co.th.aten.football.model.PlayersModel;
import co.th.aten.football.model.VideoModel;
import java.util.List;

/**
 *
 * @author Atenpunk
 */
public interface VideoPlayersManager {

    public boolean insertVideoPlayer(VideoModel videoModel);

    public int getMaxVideoId();

    public List<VideoModel> searchByKeyWord(int playerId);
    
    public boolean deleteVideoByPlayerId(int playerId);
    
    public boolean deleteVideoByVideoId(int videoId);
}
