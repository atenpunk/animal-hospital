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
    private SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

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
                    + " , pet_create_by, pet_create_date, pet_update_by, pet_update_date, pet_birthdate ) "
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            return (this.simpleJdbcTemplate.update(sql, petModel.getId(), petModel.getOwnerId(), petModel.getName(), petModel.getType(), petModel.getBreed(), petModel.getColor(), petModel.getSex(), petModel.getImage(), petModel.getCreateBy(), sdfDateTime.parse(sdfDateTime.format(petModel.getCreateDate())), petModel.getUpdateBy(), sdfDateTime.parse(sdfDateTime.format(petModel.getUpdateDate())), sdfDate.parse(sdfDate.format(petModel.getBirthdayPet()))) > 0) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePet(PetModel petModel) {
        logger.info("Update Pet_ID = " + petModel.getId());
        logger.info("Update Owner_ID = " + petModel.getOwnerId());
        try {
            String sql = " UPDATE pet SET pet_name = ? "
                    + " , pet_type = ? "
                    + " , pet_breed = ? "
                    + " , pet_color = ? "
                    + " , pet_sex = ? "
                    + " , pet_image = ? "
                    + " , pet_update_by = ? "
                    + " , pet_update_date = ? "
                    + " , pet_birthdate = ? "
                    + " WHERE pet_id = ? AND owner_id = ? ";
            return (this.simpleJdbcTemplate.update(sql, petModel.getName(), petModel.getType(), petModel.getBreed(), petModel.getColor()
                    , petModel.getSex(), petModel.getImage(), petModel.getUpdateBy()
                    , sdfDateTime.parse(sdfDateTime.format(petModel.getUpdateDate())),sdfDate.parse(sdfDate.format(petModel.getBirthdayPet()))
                    , petModel.getId(), petModel.getOwnerId()) > 0) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
