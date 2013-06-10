/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.dao;

import co.th.aten.hospital.model.OwnerModel;
import co.th.aten.hospital.model.UserModel;
import java.security.MessageDigest;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/**
 *
 * @author Atenpunk
 */
public class JdbcOwnerDao implements OwnerDao {

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

    public int getMaxOwnerId() {
        String sql = "select max(owner_id) from owner ";
        return this.simpleJdbcTemplate.queryForInt(sql);
    }

    public boolean insertOwner(OwnerModel ownerModel) {
        if (logger.isDebugEnabled()) {
            logger.debug("Owner Name = " + ownerModel.getName());
        }
        try {
            String sql = " INSERT INTO owner (owner_id,owner_name,owner_phone,owner_email,owner_address) "
                    + " VALUES (?, ?, ?, ?, ?)";
            return (this.simpleJdbcTemplate.update(sql, ownerModel.getId(),ownerModel.getName(),ownerModel.getPhoneNumber()
                    ,ownerModel.getEmail(),ownerModel.getAddress())>0)?true:false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
