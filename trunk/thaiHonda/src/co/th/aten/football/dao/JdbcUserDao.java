/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.dao;

import co.th.aten.football.model.UserModel;
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
            String sql = "select user_id, user_name, group_id, status , login_name from staff where login_name ='"+user+"' and password = '"+hash(pass)+"' ";
            ParameterizedRowMapper<UserModel> mapper = new ParameterizedRowMapper<UserModel>() {
                public UserModel mapRow(ResultSet rs, int arg1) throws SQLException {
                    UserModel um = new UserModel();
                    um.setUserId(rs.getInt(1));
                    um.setUserName(rs.getString(2));
                    um.setGroup(rs.getShort(3));
                    um.setStatus(rs.getShort(4));
                    um.setUserLogin(rs.getString(5));
                    return um;
                }
            };
            return this.simpleJdbcTemplate.query(sql, mapper).get(0);
        } catch (Exception e) {
            logger.info("Login : "+user+", Error : "+e.getMessage());
        }
        return null;
    }
    
    public int getMaxUserId() {
        String sql = "select max(USER_ID) from STAFF ";
        return this.simpleJdbcTemplate.queryForInt(sql);
    }
    
    public boolean insertStaff(UserModel userModel) {
        logger.info("Insert Staff UserLogin = " + userModel.getUserLogin());
        try {
            String sql = " INSERT INTO STAFF (USER_ID,USER_NAME,LOGIN_NAME,PASSWORD,GROUP_ID "
                    + " , LAST_LOGIN, PHONE, MOBILE_HONE, EMAIL, STATUS) "
                    + " VALUES (?, ?, ?, ?, ?, "
                    + " ?, ?, ?, ?, ?) ";
            boolean insert = (this.simpleJdbcTemplate.update(sql, userModel.getUserId(), userModel.getUserName(),userModel.getUserLogin()
                    ,hash(userModel.getPassword()),userModel.getGroup(),userModel.getLastLogin(),userModel.getPhoneNo(), userModel.getMobileHome(),userModel.getEmail(),userModel.getStatus()) > 0) ? true : false;
            return insert;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean changePassword(int userId, String password) {
        logger.debug("Change Password userId = " + userId);
        try {
            String sql = " UPDATE STAFF SET PASSWORD = ? "
                    + " WHERE USER_ID = ? ";
            return (this.simpleJdbcTemplate.update(sql, hash(password), userId) > 0) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
