/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.dao;

import co.th.aten.hospital.model.PositionModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 *
 * @author Atenpunk
 */
public class JdbcPositionDao implements PositionDao {

    private final Log logger = LogFactory.getLog(getClass());
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public List<PositionModel> getTypeList() {
        try {
            String sql = " select ID, NAME, LOCAL_NAME "
                    + " from POSITION order by ID ";

            ParameterizedRowMapper<PositionModel> mapper = new ParameterizedRowMapper<PositionModel>() {

                @Override
                public PositionModel mapRow(ResultSet rs, int arg1) throws SQLException {
                    PositionModel model = new PositionModel();
                    model.setId(rs.getInt("ID"));
                    model.setEngName(rs.getString("NAME"));
                    model.setThaiName(rs.getString("LOCAL_NAME"));
                    return model;
                }
            };
            return this.simpleJdbcTemplate.query(sql, mapper);
        } catch (Exception e) {
            logger.info("" + e);
            e.printStackTrace();
            return null;
        }
    }
}
