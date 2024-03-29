/*
 * Copyright 2002-2004 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package co.th.aten.football;

import co.th.aten.football.service.SessionManager;
import co.th.aten.football.ui.Screen;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.richclient.application.Application;
import org.springframework.richclient.application.ApplicationWindow;
import org.springframework.richclient.application.config.ApplicationWindowConfigurer;
import org.springframework.richclient.application.config.DefaultApplicationLifecycleAdvisor;
import org.springframework.richclient.settings.Settings;
import org.springframework.richclient.settings.SettingsException;
import org.springframework.richclient.settings.SettingsManager;
import org.springframework.richclient.settings.xml.FileSystemXmlSettingsReaderWriter;
import org.springframework.richclient.settings.xml.XmlSettingsFactory;
import org.springframework.richclient.settings.xml.XmlSettingsReaderWriter;

/**
 * Custom application lifecycle implementation that configures the sample app at
 * well defined points within its lifecycle.
 *
 * @author Keith Donald
 */
public class SimpleLifecycleAdvisor extends DefaultApplicationLifecycleAdvisor {

    private final Log logger = LogFactory.getLog(getClass());

    /**
     * This method is called prior to the opening of an application window. Note
     * at this point the window control has not been created. This hook allows
     * programmatic control over the configuration of the window (by setting
     * properties on the configurer) and it provides a hook where code that
     * needs to be executed prior to the window opening can be plugged in (like
     * a startup wizard, for example).
     *
     * @param configurer The application window configurer
     */
    @Override
    public void onPreWindowOpen(ApplicationWindowConfigurer configurer) {

        // If you override this method, it is critical to allow the superclass
        // implementation to run as well.
        super.onPreWindowOpen(configurer);
        configurer.setShowMenuBar(false);
        configurer.setShowToolBar(false);
        configurer.setUndecorated(false);
        String build = Application.instance().getDescriptor().getVersion() + " ( " + Application.instance().getDescriptor().getBuildId() + " )";
        configurer.setTitle(java.util.ResourceBundle.getBundle("ui/messages").getString("header.welcome") + "  v." + build);
        // Uncomment to hide the menubar, toolbar, or alter window size...
        // configurer.setShowMenuBar(false);
        // configurer.setShowToolBar(false);
//        configurer.setInitialSize(new Dimension(1024, 700));
//        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        configurer.setInitialSize(new Dimension((int)env.getMaximumWindowBounds().getWidth(),(int)env.getMaximumWindowBounds().getHeight()));



        SessionManager sessionManager = (SessionManager) Application.services().getService(SessionManager.class);
        try {
            SettingsManager settingsManager = new SettingsManager();
            XmlSettingsFactory settingsFactory = new XmlSettingsFactory();
//            settingsFactory.setLocation("c:/");
            XmlSettingsReaderWriter xmlrw = new FileSystemXmlSettingsReaderWriter("conf/");
            settingsFactory.setReaderWriter(xmlrw);
            if (logger.isInfoEnabled()) {
                logger.info("Config location=" + settingsFactory.getLocation());
            }
            settingsManager.setSettingsFactory(settingsFactory);
            Settings settings = settingsManager.getInternalSettings();
            settings.load();
            if (logger.isInfoEnabled()) {
                logger.info("Loading location info..");
            }
            //settings.setString("server_ip", "192.168.0.104");
            //settings.setString("server_port", "8080");
            if (logger.isInfoEnabled()) {
                logger.info("server_ip=" + settings.getString("server_ip") + ":port=" + settings.getString("server_port"));
            }

        } catch (IOException ex) {
//            ex.printStackTrace();
            logger.error("Load config error", ex);
        } catch (SettingsException ex) {
            logger.error("Load config error", ex);
//            ex.printStackTrace();
        } catch (Exception ex) {
//            ex.printStackTrace();
            logger.error("Load config error", ex);
//            if (location == null) {
//                logger.info("Loading location info .... Fail!");
//                location = new LocationModel();
//                location.setNetworkAbbr("-");
//                location.setNetworkId((short) sessionManager.getSettings().getInt("network"));
//                location.setInterchangeAbbr("-");
//                location.setInterchangeId((short) sessionManager.getSettings().getInt("interchange"));
//                location.setTsbAbbr("-");
//                location.setTsbId((short) sessionManager.getSettings().getInt("tsb"));
//                location.setPosAbbr("-");
//                location.setPosId(sessionManager.getSettings().getInt("terminal"));
//            }
//            sessionManager.setLocation(location);
//            sessionManager.createSecurityToken();
        }

    }

    /**
     * Called just after the command context has been internalized. At this
     * point, all the commands for the window have been created and are
     * available for use. If you need to force the execution of a command prior
     * to the display of an application window (like a login command), this is
     * where you'd do it.
     *
     * @param window The window who's commands have just been created
     */
    @Override
    public void onCommandsCreated(ApplicationWindow window) {
        if (logger.isInfoEnabled()) {
            logger.info("onCommandsCreated( windowNumber=" + window.getNumber() + " )");


        }
    }

    /**
     * Called after the actual window control has been created.
     *
     * @param window The window being processed
     */
    @Override
    public void onWindowCreated(ApplicationWindow window) {
        if (logger.isInfoEnabled()) {
            logger.info("onWindowCreated( windowNumber=" + window.getNumber() + " )");


        }
        //Application.instance().getWindowManager().getActiveWindow().getPage().
    }

    /**
     * Called immediately after making the window visible.
     *
     * @param window The window being processed
     */
    @Override
    public void onWindowOpened(ApplicationWindow window) {
        if (logger.isInfoEnabled()) {
            logger.info("onWindowOpened( windowNumber=" + window.getNumber() + " )");


        }
        window.getControl().setExtendedState(window.getControl().getExtendedState() | JFrame.MAXIMIZED_BOTH);
        window.getControl().getContentPane().addHierarchyBoundsListener(new HierarchyBoundsListener() {
            @Override
            public void ancestorMoved(HierarchyEvent e) {
//				System.out.println(e);
            }

            @Override
            public void ancestorResized(HierarchyEvent e) {
//				System.out.println(e);
                System.out.println("resize");
                System.out.println("width=" + e.getComponent().getSize().getWidth());
                System.out.println("height=" + e.getComponent().getSize().getHeight());
                Screen.width = (int) e.getComponent().getSize().getWidth();
                Screen.height = (int) e.getComponent().getSize().getHeight();
            }
        });
//        MessageManager messageManager = (MessageManager) Application.services().getService(MessageManager.class);
//
//        MessageSource ms = (MessageSource) Application.services().getService(MessageSource.class);
//        messageManager.showMessage(ms.getMessage("msg.login.insertStaffCard", null, Locale.getDefault()));
//        String build = Application.instance().getDescriptor().getVersion() + " ( " + Application.instance().getDescriptor().getBuildId() + " )";
    }

    /**
     * Called when the window is being closed. This hook allows control over
     * whether the window is allowed to close. By returning false from this
     * method, the window will not be closed.
     *
     * @return boolean indicator if window should be closed. <code>true</code>
     * to allow the close, <code>false</code> to prevent the close.
     */
    @Override
    public boolean onPreWindowClose(ApplicationWindow window) {
        if (logger.isInfoEnabled()) {
            logger.info("onPreWindowClose( windowNumber=" + window.getNumber() + " )");


        } //DockingUISettings.getInstance().updateUI();

//        boolean confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the application?",
//                "Confirm Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
//        return confirm;
        return true;


    }

    /**
     * Called when the application has fully started. This is after the initial
     * application window has been made visible.
     */
    @Override
    public void onPostStartup() {
        if (logger.isInfoEnabled()) {
            logger.info("onPostStartup()");
        }
        Configuration.loadConfig();
        final SessionManager sessionManager = (SessionManager) Application.services().getService(SessionManager.class);

        Application.instance().getActiveWindow().getControl().addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseMoved(MouseEvent e) {
//                sessionManager.getSession().setLastActive(Calendar.getInstance());
            }
        });

    }
}
