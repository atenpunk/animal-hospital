/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.dao;

import co.th.aten.hospital.model.PetModel;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 *
 * @author Atenpunk
 */
public class JdbcPetDao implements PetDao {

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

    public int getMaxPetId() {
        String sql = "select max(pet_id) from pet ";
        return this.simpleJdbcTemplate.queryForInt(sql);
    }

    public boolean insertPet(PetModel petModel) {
        if (logger.isDebugEnabled()) {
            logger.debug("Insert Pet_ID = " + petModel.getId());
        }
        try {
            String sql = " INSERT INTO pet (pet_id,owner_id,pet_name,pet_type,pet_breed,pet_color,pet_sex,pet_image"
                    + " , pet_create_by, pet_create_date, pet_update_by, pet_update_date ) "
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            return (this.simpleJdbcTemplate.update(sql, petModel.getId(), petModel.getOwnerId(), petModel.getName(), petModel.getType(), petModel.getBreed(), petModel.getColor(), petModel.getSex(), petModel.getImage(), petModel.getCreateBy(), petModel.getCreateDate(), petModel.getUpdateBy(), petModel.getUpdateDate()) > 0) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePet(PetModel petModel) {
        if (logger.isDebugEnabled()) {
            logger.debug("Update Pet_ID = " + petModel.getId());
        }
        try {
            String sql = " UPDATE pet SET pet_name = ? "
                    + " , pet_type = ? "
                    + " , pet_breed = ? "
                    + " , pet_color = ? "
                    + " , pet_sex = ? "
                    + " , pet_image = ? "
                    + " , pet_update_by = ? "
                    + " , pet_update_date = ? "
                    + " WHERE pet_id = ? AND owner_id = ? ";
            return (this.simpleJdbcTemplate.update(sql, petModel.getName(), petModel.getType(), petModel.getBreed()
                    , petModel.getColor(), petModel.getSex(), petModel.getImage(), petModel.getUpdateBy()
                    , petModel.getUpdateDate(), petModel.getId(), petModel.getOwnerId()) > 0) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
