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
package co.th.aten.hospital;

import java.awt.Font;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.richclient.application.ApplicationLauncher;

/**
 * This is an archetype application using the Spring Richclient platform.
 * <p>
 * The Spring Rich platform relies on the <a href="http://www.springframework.org/">Spring</a>
 * project to manage the application context with all the associated benefits it offers.
 * <p>
 * A start at the Spring Rich Client documentation can be found on the <a
 * href="http://opensource.atlassian.com/confluence/spring/display/RCP/Home">wiki</a>.
 * </p>
 * 
 * @author Larry Streepy
 * @see The <a
 *      href="http://www.springframework.org/">Spring project</a>
 * @see The <a
 *      href="http://opensource.atlassian.com/confluence/spring/display/RCP/Home">Spring
 *      Rich Wiki</a>
 */
public class SimpleApp {

    private static final Log logger = LogFactory.getLog(SimpleApp.class);

    /**
     * Main routine for the simple sample application.
     * 
     * @param args
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure("conf/pos-jog4j.properties");
        logger.info("Program starting up");


        UIDefaults defaults = UIManager.getDefaults();
        logger.info("Default Label Font=" + defaults.get("Label.font"));
        logger.info("Default Button Font=" + defaults.get("Button.font"));
        logger.info("Default Panel Font=" + defaults.get("Panel.font"));

        String os = System.getProperties().getProperty("os.name");
        logger.info("OS=" + os);
        if (os.equalsIgnoreCase("windows")) {
            defaults.put("Label.font", new Font("Tahoma", Font.PLAIN, 14));
            defaults.put("Button.font", new Font("Tahoma", Font.PLAIN, 12));
            defaults.put("Panel.font", new Font("Tahoma", Font.PLAIN, 14));
        } else {
            defaults.put("Label.font", new Font(Font.DIALOG, Font.PLAIN, 14));
            defaults.put("Button.font", new Font(Font.DIALOG, Font.PLAIN, 12));
            defaults.put("Panel.font", new Font(Font.DIALOG, Font.PLAIN, 14));
        }
        // In order to launch the platform, we have to construct an
        // application context that defines the beans (services) and
        // wiring. This is pretty much straight Spring.
        //
        // Part of this configuration will indicate the initial page to be
        // displayed.

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                System.out.println("look=" + info.getName());
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }

        logger.info("args="+args.length);
        if (args != null && args.length > 0) {
            logger.info("mode="+args[0]);
            if (args[0].equalsIgnoreCase("TOD")) {
                Configuration.isTodMode = true;
            }
        }

        
        String rootContextDirectoryClassPath = "/ctx";

        // The startup context defines elements that should be available
        // quickly such as a splash screen image.

        String startupContextPath = rootContextDirectoryClassPath + "/richclient-startup-context.xml";

        String richclientApplicationContextPath = rootContextDirectoryClassPath + "/richclient-application-context.xml";

        String bussinessContext = rootContextDirectoryClassPath + "/business-layer-context.xml";
        // The ApplicationLauncher is responsible for loading the contexts,
        // presenting the splash screen, initializing the Application
        // singleton instance, creating the application window to display
        // the initial page.
        try {
            new ApplicationLauncher(startupContextPath,
                    new String[]{richclientApplicationContextPath, bussinessContext});
        } catch (RuntimeException e) {
            logger.error("RuntimeException during startup", e);
        }

    }
}
