/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.dao;

import co.th.aten.football.model.VideoModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import java.util.List;
import java.util.Locale;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/**
 *
 * @author Atenpunk
 */
public class JdbcVideoPlayersDao implements VideoPlayersDao {

    private final Log logger = LogFactory.getLog(getClass());
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private DataSource dataSource;
    private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public int getMaxVideoId() {
        String sql = "select max(VIDEO_ID) from VIDEO_PLAYERS ";
        return this.simpleJdbcTemplate.queryForInt(sql);
    }

    public boolean deleteVideoByPlayerId(int playerId) {
        logger.debug("delete Video by PLAYER_ID = " + playerId);
        try {
            String sql = " DELETE FROM VIDEO_PLAYERS WHERE PLAYER_ID = ? ";
            return (this.simpleJdbcTemplate.update(sql, playerId) > 0) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteVideoByVideoId(int videoId) {
        logger.debug("delete Video by VIDEO_ID = " + videoId);
        try {
            String sql = " DELETE FROM VIDEO_PLAYERS WHERE VIDEO_ID = ? ";
            return (this.simpleJdbcTemplate.update(sql, videoId) > 0) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertVideoPlayer(VideoModel videoModel) {
        logger.info("Insert Video_ID = " + videoModel.getVideoId());
        logger.info("      Player_ID = " + videoModel.getPlayerId());
        try {
            String sql = " INSERT INTO VIDEO_PLAYERS (VIDEO_ID, PLAYER_ID, VIDEO_NAME "
                    + " , CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE ) "
                    + " VALUES (?, ?, ?, ?, ?, ?, ?) ";
            boolean insert = (this.simpleJdbcTemplate.update(sql, videoModel.getVideoId(), videoModel.getPlayerId(), videoModel.getVideoName(), videoModel.getCreateBy(), sdfDate.parse(sdfDate.format(videoModel.getCreateDate())), videoModel.getUpdateBy(), sdfDate.parse(sdfDate.format(videoModel.getUpdateDate()))) > 0) ? true : false;
            return insert;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<VideoModel> searchByKeyWord(int playerId) {
        try {
            String sql = " select vd.VIDEO_ID, vd.PLAYER_ID, vd.VIDEO_NAME, vd.CREATE_BY , vd.CREATE_DATE , vd.UPDATE_BY , vd.UPDATE_DATE "
                    + " from VIDEO_PLAYERS vd "
                    + " where vd.PLAYER_ID = " + playerId + " "
                    + " order by vd.CREATE_BY desc ";
            ParameterizedRowMapper<VideoModel> mapper = new ParameterizedRowMapper<VideoModel>() {
                @Override
                public VideoModel mapRow(ResultSet rs, int arg1) throws SQLException {
                    VideoModel model = new VideoModel();
                    model.setVideoId(rs.getInt("VIDEO_ID"));
                    model.setPlayerId(rs.getInt("PLAYER_ID"));                    
                    model.setVideoName(rs.getString("VIDEO_NAME"));
                    model.setCreateBy(rs.getInt("CREATE_BY"));
                    model.setCreateDate(rs.getDate("CREATE_DATE"));
                    model.setUpdateBy(rs.getInt("UPDATE_BY"));
                    model.setUpdateDate(rs.getDate("UPDATE_DATE"));
                    return model;
                }
            };
            return this.simpleJdbcTemplate.query(sql, mapper);
        } catch (Exception e) {
            logger.info("" + e);
            e.printStackTrace();
        }
        return null;
    }
}
