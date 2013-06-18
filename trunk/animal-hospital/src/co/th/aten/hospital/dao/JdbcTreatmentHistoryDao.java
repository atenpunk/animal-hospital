/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.dao;

import co.th.aten.hospital.model.PetModel;
import co.th.aten.hospital.model.TreatmentHistoryModel;
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
public class JdbcTreatmentHistoryDao implements TreatmentHistoryDao {

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

    public int getMaxHistoryId() {
        String sql = "select max(history_id) from treatment_history ";
        return this.simpleJdbcTemplate.queryForInt(sql);
    }

    public List<TreatmentHistoryModel> getHistoryListByPetId(int petId) {
        try {
            String sqlId = "";
            if (petId != -1) {
                sqlId = " Where pet_id = " + petId + " ";
            }
            String sql = " select history_id, pet_id, history_detail, history_create_by "
                    + " , history_create_date,history_update_by, history_update_date "
                    + " from treatment_history " + sqlId + " order by history_create_date ";

            ParameterizedRowMapper<TreatmentHistoryModel> mapper = new ParameterizedRowMapper<TreatmentHistoryModel>() {

                @Override
                public TreatmentHistoryModel mapRow(ResultSet rs, int arg1) throws SQLException {
                    TreatmentHistoryModel model = new TreatmentHistoryModel();
                    model.setHistoryId(rs.getInt("history_id"));
                    model.setPetId(rs.getInt("pet_id"));
                    model.setDetail(rs.getString("history_detail"));
                    model.setCreateBy(rs.getString("history_create_by"));
                    model.setCreateDate(rs.getTimestamp("history_create_date"));
                    model.setUpdateBy(rs.getString("history_update_by"));
                    model.setUpdateDate(rs.getTimestamp("history_update_date"));
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

    public boolean insertHistory(TreatmentHistoryModel model) {
        logger.info("Insert History_id = " + model);
        try {
            String sql = " INSERT INTO treatment_history (history_id,pet_id,history_detail "
                    + " , history_create_by, history_create_date, history_update_by, history_update_date ) "
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            return (this.simpleJdbcTemplate.update(sql, model.getHistoryId(), model.getPetId(), model.getDetail(), model.getCreateBy(), sdfDate.parse(sdfDate.format(model.getCreateDate())), model.getUpdateBy(), sdfDate.parse(sdfDate.format(model.getUpdateDate()))) > 0) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
//    public boolean updatePet(PetModel petModel) {
//        logger.info("Update Pet_ID = " + petModel.getId());
//        logger.info("Update Owner_ID = " + petModel.getOwnerId());
//        try {
//            String sql = " UPDATE pet SET pet_name = ? "
//                    + " , pet_type = ? "
//                    + " , pet_breed = ? "
//                    + " , pet_color = ? "
//                    + " , pet_sex = ? "
//                    + " , pet_image = ? "
//                    + " , pet_update_by = ? "
//                    + " , pet_update_date = ? "
//                    + " WHERE pet_id = ? AND owner_id = ? ";
//            return (this.simpleJdbcTemplate.update(sql, petModel.getName(), petModel.getType(), petModel.getBreed()
//                    , petModel.getColor(), petModel.getSex(), petModel.getImage(), petModel.getUpdateBy()
//                    , sdfDate.parse(sdfDate.format(petModel.getUpdateDate())), petModel.getId(), petModel.getOwnerId()) > 0) ? true : false;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
