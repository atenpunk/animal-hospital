/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.dao;

import co.th.aten.hospital.model.TypeModel;
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
public class JdbcTypeDao implements TypeDao {

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

    public List<TypeModel> getTypeList() {
        try {
            String sql = " select type_id, type_eng_name, type_thai_name "
                    + " from pet_type order by type_id ";

            ParameterizedRowMapper<TypeModel> mapper = new ParameterizedRowMapper<TypeModel>() {

                @Override
                public TypeModel mapRow(ResultSet rs, int arg1) throws SQLException {
                    TypeModel model = new TypeModel();
                    model.setTypeId(rs.getInt("type_id"));
                    model.setEngName(rs.getString("type_eng_name"));
                    model.setThaiName(rs.getString("type_thai_name"));
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
