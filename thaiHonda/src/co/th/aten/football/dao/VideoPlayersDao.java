/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.football.dao;

import co.th.aten.football.model.VideoModel;
import java.util.List;
import javax.sql.DataSource;
/**
 *
 * @author Atenpunk
 */
public interface VideoPlayersDao {

    public DataSource getDataSource();

    public void setDataSource(DataSource dataSource);

    public boolean insertVideoPlayer(VideoModel videoModel);

    public int getMaxVideoId();

    public List<VideoModel> searchByKeyWord(int playerId);
    
    public boolean deleteVideoByPlayerId(int playerId);
    
    public boolean deleteVideoByVideoId(int videoId);
}
