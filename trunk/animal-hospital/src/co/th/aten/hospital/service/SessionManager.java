/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.service;


/**
 *
 * @author Aten
 */
public interface SessionManager {

    public void createSecurityToken();

    public boolean isNeedReaderCheck();
    public void setNeedReaderCheck(boolean needReaderCheck);
    public boolean isTodMode();
    public void setTodMode(boolean todMode);
}
