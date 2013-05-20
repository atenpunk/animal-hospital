/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.service;

import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.richclient.settings.Settings;

/**
 *
 * @author Mai
 */
public class SessionManagerBean implements Serializable, SessionManager {

    private final Log logger = LogFactory.getLog(getClass());
    private Settings settings;
    private boolean needReaderCheck;
    private boolean waitStaffCard;
    private Thread readStaffWorker;
    private boolean todMode;

    public void createSecurityToken() {
    }

    public SessionManagerBean() {
    }

    /**
     * @return the settings
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * @param settings the settings to set
     */
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    /**
     * @return the needReaderCheck
     */
    public boolean isNeedReaderCheck() {
        return needReaderCheck;
    }

    /**
     * @param needReaderCheck the needReaderCheck to set
     */
    public void setNeedReaderCheck(boolean needReaderCheck) {
        this.needReaderCheck = needReaderCheck;
    }

    /**
     * @return the todMode
     */
    public boolean isTodMode() {
        return todMode;
    }

    /**
     * @param todMode the todMode to set
     */
    public void setTodMode(boolean todMode) {
        this.todMode = todMode;
    }
}
