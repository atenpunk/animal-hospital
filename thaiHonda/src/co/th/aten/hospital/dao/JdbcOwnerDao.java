/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.dao;

import co.th.aten.hospital.model.PlayersModel;
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
public class JdbcOwnerDao implements OwnerDao {

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

    public int getMaxOwnerId() {
        String sql = "select max(owner_id) from owner ";
        return this.simpleJdbcTemplate.queryForInt(sql);
    }

    public boolean insertOwner(PlayersModel playersModel) {
//        if (logger.isDebugEnabled()) {
//            logger.debug("Insert Owner_ID = " + playersModel.getId());
//        }
//        try {
//            String sql = " INSERT INTO owner (owner_id,owner_name,owner_phone,owner_email,owner_address "
//                    + " , owner_create_by, owner_create_date, owner_update_by, owner_update_date ) "
//                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//            return (this.simpleJdbcTemplate.update(sql, ownerModel.getId(), ownerModel.getName(), ownerModel.getPhoneNumber(), ownerModel.getEmail(), ownerModel.getAddress(), ownerModel.getCreateBy(), sdfDate.parse(sdfDate.format(ownerModel.getCreateDate())), ownerModel.getUpdateBy(), sdfDate.parse(sdfDate.format(ownerModel.getUpdateDate()))) > 0) ? true : false;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return false;
    }

    public boolean updateOwner(PlayersModel playersModel) {
//        if (logger.isDebugEnabled()) {
//            logger.debug("Update Owner_ID = " + ownerModel.getId());
//        }
//        try {
//            String sql = " UPDATE owner SET owner_name = ? "
//                    + " , owner_phone = ? "
//                    + " , owner_email = ? "
//                    + " , owner_address = ? "
//                    + " , owner_update_by = ? "
//                    + " , owner_update_date = ? "
//                    + " WHERE owner_id = ? ";
//            return (this.simpleJdbcTemplate.update(sql, ownerModel.getName(), ownerModel.getPhoneNumber(), ownerModel.getEmail(), ownerModel.getAddress(), ownerModel.getUpdateBy(), sdfDate.parse(sdfDate.format(ownerModel.getUpdateDate())), ownerModel.getId()) > 0) ? true : false;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return false;
    }

    public List<PlayersModel> searchByKeyWord(String word) {
//        try {
//            String sql = " select ow.owner_id, ow.owner_name, ow.owner_phone, ow.owner_email, ow.owner_address "
//                    + " , pe.pet_id, pe.pet_name, pe.pet_type, pe.pet_breed, pe.pet_color, pe.pet_sex, pe.pet_image, pe.pet_birthdate "
//                    + " from owner ow "
//                    + " left join pet pe on(pe.owner_id = ow.owner_id) ";
//            if (word != null && !word.equals("")) {
//                sql += " where owner_name like '%" + word + "%' "
//                        + " or owner_phone like '%" + word + "%' "
//                        + " or owner_email like '%" + word + "%' "
//                        + " or pet_name like '%" + word + "%' "
//                        + " or pet_type like '%" + word + "%' "
//                        + " or pet_breed like '%" + word + "%' "
//                        + " or pet_color like '%" + word + "%' "
//                        + " or pet_sex like '%" + word + "%' ";
//            }
//            ParameterizedRowMapper<OwnerModel> mapper = new ParameterizedRowMapper<OwnerModel>() {
//                @Override
//                public OwnerModel mapRow(ResultSet rs, int arg1) throws SQLException {
//                    OwnerModel model = new OwnerModel();
//                    model.setId(rs.getInt("owner_id"));
//                    model.setName(rs.getString("owner_name"));
//                    model.setPhoneNumber(rs.getString("owner_phone"));
//                    model.setAddress(rs.getString("owner_address"));
//                    model.setEmail(rs.getString("owner_email"));
//                    PlayersModel pet = new PlayersModel();
//                    pet.setId(rs.getInt("pet_id"));
//                    pet.setName(rs.getString("pet_name"));
//                    pet.setType(rs.getString("pet_type"));
//                    pet.setBreed(rs.getString("pet_breed"));
//                    pet.setColor(rs.getString("pet_color"));
//                    pet.setSex(rs.getString("pet_sex"));
//                    pet.setImage(rs.getBytes("pet_image"));
//                    pet.setBirthdayPet(rs.getDate("pet_birthdate"));
//                    model.setPetModel(pet);
//                    return model;
//                }
//            };
//            return this.simpleJdbcTemplate.query(sql, mapper);
//        } catch (Exception e) {
//            logger.info("" + e);
//            e.printStackTrace();
//        }
        return null;
    }
}
