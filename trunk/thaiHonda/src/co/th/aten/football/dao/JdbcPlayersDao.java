/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.dao;

import co.th.aten.football.model.PlayersModel;
import java.io.File;
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
public class JdbcPlayersDao implements PlayersDao {

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

    public int getMaxPlayersId() {
        String sql = "select max(PLAYER_ID) from PLAYERS ";
        return this.simpleJdbcTemplate.queryForInt(sql);
    }
    
    public boolean deletePlayer(int playerId){
        logger.debug("delete Player ID = " + playerId);
        try {
            String sql = " DELETE FROM PLAYERS WHERE PLAYER_ID = ? ";
            return (this.simpleJdbcTemplate.update(sql, playerId) > 0) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertPlayers(PlayersModel playersModel) {
        logger.info("Insert Player_ID = " + playersModel.getPlayerId());
        try {
            String sql = " INSERT INTO PLAYERS (PLAYER_ID,PLAYER_NAME,PLAYER_NUMBER,HEIGHT,WEIGHT "
                    + " , POSITION_ID, BIRTHDAY, CONTRACT_START, CONTRACT_END, IMAGE_NAME "
                    + " , CLUB, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE ) "
                    + " VALUES (?, ?, ?, ?, ?, "
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?) ";
            boolean insert = (this.simpleJdbcTemplate.update(sql, playersModel.getPlayerId(), playersModel.getPlayerName(), playersModel.getPlayerNumber(), playersModel.getHeight(), playersModel.getWeight(), playersModel.getPositionId(), sdfDate.parse(sdfDate.format(playersModel.getBirthday())), sdfDate.parse(sdfDate.format(playersModel.getContractStart())), sdfDate.parse(sdfDate.format(playersModel.getContractEnd())), playersModel.getImage(), playersModel.getClub(), playersModel.getCreateBy(), sdfDate.parse(sdfDate.format(playersModel.getCreateDate())), playersModel.getUpdateBy(), sdfDate.parse(sdfDate.format(playersModel.getUpdateDate()))) > 0) ? true : false;
            return insert;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editPlayers(PlayersModel playersModel) {
        logger.debug("Edit Player ID = " + playersModel.getPlayerId());
        try {
            String sql = " UPDATE PLAYERS SET PLAYER_NAME = ? "
                    + " , PLAYER_NUMBER = ? "
                    + " , HEIGHT = ? "
                    + " , WEIGHT = ? "
                    + " , POSITION_ID = ? "
                    + " , BIRTHDAY = ? "
                    + " , CONTRACT_START = ? "
                    + " , CONTRACT_END = ? "
                    + " , IMAGE_NAME = ? "
                    + " , CLUB = ? "
                    + " , UPDATE_BY = ? "
                    + " , UPDATE_DATE = ? "
                    + " WHERE PLAYER_ID = ? ";
            return (this.simpleJdbcTemplate.update(sql, playersModel.getPlayerName(), playersModel.getPlayerNumber(), playersModel.getHeight(), playersModel.getWeight(), playersModel.getPositionId(), playersModel.getBirthday(), playersModel.getContractStart(), playersModel.getContractEnd(), playersModel.getImage(), playersModel.getClub(), playersModel.getUpdateBy(), sdfDate.parse(sdfDate.format(playersModel.getUpdateDate())), playersModel.getPlayerId()) > 0) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    public boolean updatePlayers(PlayersModel playersModel) {
//        logger.debug("Update Player ID = " + playersModel.getPlayerId());
//        try {
//            String sql = " UPDATE PLAYERS SET GC = ? "
//                    + " , ANNUAL_SALARY = ? "
//                    + " , SIGNING_FEE = ? "
//                    + " , SALARY_MONTH = ? "
//                    + " , GOAL = ? "
//                    + " , PLAYING_TIME = ? "
//                    + " , MATCH_NUMBER = ? "
//                    + " , WIN = ? "
//                    + " , LOSE = ? "
//                    + " , DRAW = ? "
//                    + " , STARTER = ? "
//                    + " , UPDATE_BY = ? "
//                    + " , UPDATE_DATE = ? "
//                    + " WHERE PLAYER_ID = ? ";
//            return (this.simpleJdbcTemplate.update(sql, playersModel.getGc(), playersModel.getAnnualSalary(), playersModel.getSigningFee(), playersModel.getSalaryMonth(), playersModel.getGoal(), playersModel.getPlayingTime(), playersModel.getMatch(), playersModel.getWin(), playersModel.getLose(), playersModel.getDraw(), playersModel.getStarter(), playersModel.getUpdateBy(), sdfDate.parse(sdfDate.format(playersModel.getUpdateDate())), playersModel.getPlayerId()) > 0) ? true : false;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    public List<PlayersModel> searchByKeyWord(String word) {
        try {
            String sql = " select py.PLAYER_ID, py.PLAYER_NAME, py.PLAYER_NUMBER, py.HEIGHT, py.WEIGHT "
                    + " , py.POSITION_ID, ps.NAME, py.BIRTHDAY, py.CONTRACT_START, py.CONTRACT_END, py.IMAGE_NAME "
                    + " , py.CLUB, py.CREATE_BY, py.CREATE_DATE, py.UPDATE_BY, py.UPDATE_DATE "
                    + " from PLAYERS py "
                    + " left join POSITION ps on(ps.ID = py.POSITION_ID) ";
            if (word != null && !word.equals("")) {
                sql += " where py.PLAYER_NAME like '%" + word + "%' "
                        + " or ps.NAME like '%" + word + "%' ";
                try {
                    int number = Integer.parseInt(word);
                    sql += " or py.PLAYER_NUMBER = " + number + " ";
                } catch (Exception e) {
                }
            }
            sql += " order by py.PLAYER_NUMBER ";
            ParameterizedRowMapper<PlayersModel> mapper = new ParameterizedRowMapper<PlayersModel>() {
                @Override
                public PlayersModel mapRow(ResultSet rs, int arg1) throws SQLException {
                    PlayersModel model = new PlayersModel();
                    model.setPlayerId(rs.getInt("PLAYER_ID"));
                    model.setPlayerName(rs.getString("PLAYER_NAME"));
                    model.setPlayerNumber(rs.getInt("PLAYER_NUMBER"));
                    model.setHeight(rs.getInt("HEIGHT"));
                    model.setWeight(rs.getInt("WEIGHT"));
                    model.setPositionId(rs.getInt("POSITION_ID"));
                    model.setPositionStr(rs.getString("NAME"));
                    model.setBirthday(rs.getDate("BIRTHDAY"));
                    model.setContractStart(rs.getDate("CONTRACT_START"));
                    model.setContractEnd(rs.getDate("CONTRACT_END"));
                    model.setImage(rs.getString("IMAGE_NAME"));
                    model.setClub(rs.getString("CLUB"));
//                    model.setGc(rs.getDouble("GC"));
//                    model.setAnnualSalary(rs.getDouble("ANNUAL_SALARY"));
//                    model.setSigningFee(rs.getDouble("SIGNING_FEE"));
//                    model.setSalaryMonth(rs.getDouble("SALARY_MONTH"));
//                    model.setGoal(rs.getInt("GOAL"));
//                    model.setPlayingTime(rs.getInt("PLAYING_TIME"));
//                    model.setMatch(rs.getInt("MATCH_NUMBER"));
//                    model.setWin(rs.getInt("WIN"));
//                    model.setLose(rs.getInt("LOSE"));
//                    model.setDraw(rs.getInt("DRAW"));
//                    model.setStarter(rs.getInt("STARTER"));
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
