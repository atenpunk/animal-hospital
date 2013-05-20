/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.hospital.dao;

import javax.sql.DataSource;

/**
 *
 * @author Aten
 */
public interface FileVersionDao {

    DataSource getDataSource();

    long getReceiveVersion(int fileId);

    long getVersion(int fileId);

    void setDataSource(DataSource dataSource);

}
