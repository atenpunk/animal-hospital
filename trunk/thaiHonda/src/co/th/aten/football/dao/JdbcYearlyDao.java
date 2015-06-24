/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.dao;

import co.th.aten.football.model.PlayersModel;
import co.th.aten.football.model.YearlyModel;
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
public class JdbcYearlyDao implements YearlyDao {

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

    public boolean deleteYearly(int yearlyId,int playerId){
        logger.debug("delete Yearly ID = " + yearlyId);
        logger.debug("delete Player ID = " + playerId);
        try {
            String sql = " DELETE FROM YEARLY WHERE YEARLY_ID = ? AND PLAYER_ID = ? ";
            return (this.simpleJdbcTemplate.update(sql, yearlyId, playerId) > 0) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertYearly(YearlyModel yearlyModel) {
        logger.info("Insert Yearly_ID = " + yearlyModel.getYearlyId());
        logger.info("Insert Player_ID = " + yearlyModel.getPlayerId());
        try {
            String sql = " INSERT INTO YEARLY (YEARLY_ID, PLAYER_ID, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE ) "
                    + " VALUES (?, ?, ?, ?, ?, ?) ";
            boolean insert = (this.simpleJdbcTemplate.update(sql, yearlyModel.getYearlyId(), yearlyModel.getPlayerId()
                    , yearlyModel.getCreateBy(), sdfDate.parse(sdfDate.format(yearlyModel.getCreateDate())), yearlyModel.getUpdateBy(), sdfDate.parse(sdfDate.format(yearlyModel.getUpdateDate()))) > 0) ? true : false;
            return insert;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateYearly(YearlyModel yearlyModel) {
        logger.debug("Update Yearly ID = " + yearlyModel.getYearlyId());
        logger.debug("Update Player ID = " + yearlyModel.getPlayerId());
        try {
            String sql = " UPDATE YEARLY SET GC = ? "
                    + " , ANNUAL_SALARY = ? "
                    + " , SIGNING_FEE = ? "
                    + " , SALARY_MONTH = ? "
                    + " , GOAL = ? "
                    + " , PLAYING_TIME = ? "
                    + " , MATCH_NUMBER = ? "
                    + " , WIN = ? "
                    + " , LOSE = ? "
                    + " , DRAW = ? "
                    + " , STARTER = ? "
                    + " , CLEAN_SHEET = ? "
                    + " , SUBSTITUTION = ? "
                    + " , UPDATE_BY = ? "
                    + " , UPDATE_DATE = ? "
                    + " WHERE PLAYER_ID = ? AND YEARLY_ID = ? ";
            return (this.simpleJdbcTemplate.update(sql, yearlyModel.getGc(), yearlyModel.getAnnualSalary(), yearlyModel.getSigningFee()
                    , yearlyModel.getSalaryMonth(), yearlyModel.getGoal(), yearlyModel.getPlayingTime(), yearlyModel.getMatch()
                    , yearlyModel.getWin(), yearlyModel.getLose(), yearlyModel.getDraw(), yearlyModel.getStarter(),
                    yearlyModel.getCleanSheet(), yearlyModel.getSubstitution(), yearlyModel.getUpdateBy(), sdfDate.parse(sdfDate.format(yearlyModel.getUpdateDate())), yearlyModel.getPlayerId(), yearlyModel.getYearlyId()) > 0) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<YearlyModel> getYearlyList(int playerId) {
        try {
            String sql = " select * from YEARLY "
                    + " where PLAYER_ID = "+playerId+" "
                    + " order by YEARLY_ID desc ";
            ParameterizedRowMapper<YearlyModel> mapper = new ParameterizedRowMapper<YearlyModel>() {
                @Override
                public YearlyModel mapRow(ResultSet rs, int arg1) throws SQLException {
                    YearlyModel yearlyModel = new YearlyModel();
                    yearlyModel.setYearlyId(rs.getInt("YEARLY_ID"));
                    yearlyModel.setPlayerId(rs.getInt("PLAYER_ID"));
                    yearlyModel.setGc(rs.getDouble("GC"));
                    yearlyModel.setAnnualSalary(rs.getDouble("ANNUAL_SALARY"));
                    yearlyModel.setSigningFee(rs.getDouble("SIGNING_FEE"));
                    yearlyModel.setSalaryMonth(rs.getDouble("SALARY_MONTH"));
                    yearlyModel.setGoal(rs.getInt("GOAL"));
                    yearlyModel.setPlayingTime(rs.getInt("PLAYING_TIME"));
                    yearlyModel.setMatch(rs.getInt("MATCH_NUMBER"));
                    yearlyModel.setWin(rs.getInt("WIN"));
                    yearlyModel.setLose(rs.getInt("LOSE"));
                    yearlyModel.setDraw(rs.getInt("DRAW"));
                    yearlyModel.setStarter(rs.getInt("STARTER"));
                    yearlyModel.setCleanSheet(rs.getInt("CLEAN_SHEET"));
                    yearlyModel.setSubstitution(rs.getInt("SUBSTITUTION"));
                    yearlyModel.setCreateBy(rs.getInt("CREATE_BY"));
                    yearlyModel.setCreateDate(rs.getDate("CREATE_DATE"));
                    yearlyModel.setUpdateBy(rs.getInt("UPDATE_BY"));
                    yearlyModel.setUpdateDate(rs.getDate("UPDATE_DATE"));
                    return yearlyModel;
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
