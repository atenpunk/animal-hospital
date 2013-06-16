/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.dao;

import co.th.aten.hospital.model.BreedModel;
import co.th.aten.hospital.model.PetModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 *
 * @author Atenpunk
 */
public class JdbcBreedDao implements BreedDao {

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

    public List<BreedModel> getBreedListOrderByEngName(int typeId) {
        try {
            String sqlId = "";
            if (typeId != -1) {
                sqlId = " Where type_id = "+typeId+" ";
            }
            String sql = " select breed_id, breed_eng_name, breed_thai_name, type_id "
                    + " from pet_breed " + sqlId + " order by breed_eng_name ";

            ParameterizedRowMapper<BreedModel> mapper = new ParameterizedRowMapper<BreedModel>() {

                @Override
                public BreedModel mapRow(ResultSet rs, int arg1) throws SQLException {
                    BreedModel model = new BreedModel();
                    model.setBreedId(rs.getInt("breed_id"));
                    model.setEngName(rs.getString("breed_eng_name"));
                    model.setThaiName(rs.getString("breed_thai_name"));
                    model.setTypeId(rs.getInt("type_id"));
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
