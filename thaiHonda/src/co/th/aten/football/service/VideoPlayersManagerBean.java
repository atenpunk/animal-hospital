/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.service;

import co.th.aten.football.dao.VideoPlayersDao;
import co.th.aten.football.model.VideoModel;
import java.util.List;

/**
 *
 * @author Atenpunk
 */
public class VideoPlayersManagerBean implements VideoPlayersManager {

    private VideoPlayersDao videoPlayersDao;

    public boolean insertVideoPlayer(VideoModel videoModel) {
        return videoPlayersDao.insertVideoPlayer(videoModel);
    }

    public int getMaxVideoId() {
        return videoPlayersDao.getMaxVideoId();
    }

    public List<VideoModel> searchByKeyWord(int playerId) {
        return videoPlayersDao.searchByKeyWord(playerId);
    }

    public boolean deleteVideoByPlayerId(int playerId) {
        return videoPlayersDao.deleteVideoByPlayerId(playerId);
    }

    public boolean deleteVideoByVideoId(int videoId) {
        return videoPlayersDao.deleteVideoByVideoId(videoId);
    }

    public VideoPlayersDao getVideoPlayersDao() {
        return videoPlayersDao;
    }

    public void setVideoPlayersDao(VideoPlayersDao videoPlayersDao) {
        this.videoPlayersDao = videoPlayersDao;
    }
}
