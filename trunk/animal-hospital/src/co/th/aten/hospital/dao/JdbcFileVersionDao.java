/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.dao;

import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 *
 * @author Aten
 */
public class JdbcFileVersionDao implements FileVersionDao {

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

    public long getReceiveVersion(int fileId) {
        if (logger.isDebugEnabled()) {
            logger.debug("fileId=" + fileId);
        }
//        String sql = "select * from cs_obu_file where CSOF_FILE_ID = ?";
//        ParameterizedRowMapper<ObuFile> mapper = new ParameterizedRowMapper<ObuFile>() {
//
//            @Override
//            public ObuFile mapRow(ResultSet rs, int arg1) throws SQLException {
//                return fill(rs);
//            }
//        };
//        List<ObuFile> files = this.simpleJdbcTemplate.query(sql, mapper, fileId);
//        if (files.size() > 0) {
//            ObuFile file = files.get(0);
//            return file.getCsofReceiveVersion();
//        }
        return 0;
    }

    public long getVersion(int fileId) {
        if (logger.isDebugEnabled()) {
            logger.debug("fileId=" + fileId);
        }
//        String sql = "select * from cs_obu_file where CSOF_FILE_ID = ?";
//        ParameterizedRowMapper<ObuFile> mapper = new ParameterizedRowMapper<ObuFile>() {
//
//            @Override
//            public ObuFile mapRow(ResultSet rs, int arg1) throws SQLException {
//                return fill(rs);
//            }
//        };
//        List<ObuFile> files = this.simpleJdbcTemplate.query(sql, mapper, fileId);
//        if (files.size() > 0) {
//            ObuFile file = files.get(0);
//            return file.getCsofVersion();
//        }
        return 0;
    }

}
