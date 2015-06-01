/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.dao;

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
public class JdbcUserDao implements UserDao {

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

    public UserModel login(String user, String pass) {
        if (logger.isDebugEnabled()) {
            logger.debug("user=" + user);
        }
//        logger.info("Password : "+hash(pass));
        try {
            user = user.replace(" ", "");
            String sql = "select user_id, user_name, group_id, status from staff where login_name ='"+user+"' and password = '"+hash(pass)+"' ";
            ParameterizedRowMapper<UserModel> mapper = new ParameterizedRowMapper<UserModel>() {
                public UserModel mapRow(ResultSet rs, int arg1) throws SQLException {
                    UserModel um = new UserModel();
                    um.setUserId(rs.getInt(1));
                    um.setUserName(rs.getString(2));
                    um.setGroup(rs.getShort(3));
                    um.setStatus(rs.getShort(4));
                    return um;
                }
            };
            return this.simpleJdbcTemplate.query(sql, mapper).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[]args){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update("0069".getBytes("UTF-8"));
            byte[] raw = md.digest();
            System.out.println(new String(Hex.encodeHex(raw)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes("UTF-8"));
            byte[] raw = md.digest();
            return new String(Hex.encodeHex(raw));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
