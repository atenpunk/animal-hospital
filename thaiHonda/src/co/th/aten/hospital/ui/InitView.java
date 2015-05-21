/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.ui;

import co.th.aten.hospital.event.LoginSuccessEvent;
import co.th.aten.hospital.event.LogoutEvent;
import co.th.aten.hospital.event.MessageEvent;
import co.th.aten.hospital.model.MessageModel;
import co.th.aten.hospital.service.SessionManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.richclient.application.Application;
import org.springframework.richclient.application.support.AbstractView;
import org.springframework.richclient.application.support.DefaultViewDescriptor;
import org.springframework.richclient.image.ImageSource;

/**
 *
 * @author Aten
 */
public class InitView extends AbstractView implements ApplicationListener {

//    @Override
//    public String getDisplayName() {
//        return "Welcome..";
//    }
    private StatusPanel statusPanel;
    private JPanel panel;
    private InfoPanel infoPanel;
    private LoginPanel loginPanel;
    private MainPanel mainPanel;
//    private InitView me;
    private LoginErrorDialog dialog;
    private ProgressGlassPane glassPane;
    private ReversibleGlassPane reversibleGlassPane;
    private static final int MAX_DELAY = 500;

    public void installInLayeredPane(JComponent component) {
        JFrame jFrame = (JFrame) getActiveWindow().getControl();
        JLayeredPane layeredPane = jFrame.getRootPane().getLayeredPane();
        layeredPane.add(component, JLayeredPane.PALETTE_LAYER, 20);
        Dimension size = component.getPreferredSize();
        component.setSize(size);
        component.setLocation((jFrame.getWidth() - size.width) / 2,
                (jFrame.getHeight() - size.height) / 2);
        component.revalidate();
        component.setVisible(true);
    }

    public void showMessageDialog() {
        if (dialog == null) {
            logger.info("new Dialog....");
            dialog = new LoginErrorDialog(Application.instance().getWindowManager().getActiveWindow().getControl());
            dialog.setPreferredSize(new Dimension(350, 150));
            installInLayeredPane(dialog);
        }
        logger.info("Show Dialog....");
        dialog.setVisible(true);
    }

    @Override
    protected JComponent createControl() {
        String build = Application.instance().getDescriptor().getVersion() + " ( " + Application.instance().getDescriptor().getBuildId() + " )";
        ((DefaultViewDescriptor) getDescriptor()).setTitle(getMessage("header.welcome") + "  v." + build);
        if (panel == null) {
            panel = getComponentFactory().createPanel(new BorderLayout());
        }

        if (infoPanel == null) {
            infoPanel = new InfoPanel();
            panel.add(infoPanel, BorderLayout.NORTH);
        }
        if (statusPanel == null) {
            statusPanel = new StatusPanel();
            panel.add(statusPanel, BorderLayout.SOUTH);
        }
        if (loginPanel == null) {
            ImageSource is = (ImageSource) Application.services().getService(ImageSource.class);
            loginPanel = new LoginPanel(is.getImage("transaction.background"));
            panel.add(loginPanel, BorderLayout.CENTER);
        }

        return panel;
    }

    @Override
    public void componentOpened() {
        super.componentOpened();

    }

    @Override
    public void onApplicationEvent(final ApplicationEvent event) {

        if (event instanceof MessageEvent) {
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            MessageModel model = (MessageModel) ((MessageEvent) event).getSource();
            statusPanel.changeMessage(model.getType(), model.getMessage());
            this.getWindowControl().repaint();
            return;
        }

        if (event instanceof LoginSuccessEvent) {
            infoPanel.updateJob();
            loginPanel.clearScreen();
            loginPanel.setVisible(false);
            loginPanel.setEnabled(false);
            panel.remove(loginPanel);
            if (mainPanel == null) {
                ImageSource is = (ImageSource) Application.services().getService(ImageSource.class);
                mainPanel = new MainPanel(is.getImage("transaction.background"));
                panel.add(mainPanel, BorderLayout.CENTER);
            }
            mainPanel.grantAccess();
            mainPanel.setVisible(true);
            mainPanel.setEnabled(true);
            panel.validate();
            this.getWindowControl().repaint();
        }

        if (event instanceof LogoutEvent) {
            SessionManager sessionManager = (SessionManager) Application.services().getService(SessionManager.class);
            sessionManager.setUser(null);
            String build = Application.instance().getDescriptor().getVersion() + " ( " + Application.instance().getDescriptor().getBuildId() + " )";
            ((DefaultViewDescriptor) getDescriptor()).setTitle(getMessage("header.welcome") + "  v." + build);

            infoPanel.updateJob();
//            mainPanel.clearScreen();
            mainPanel.setVisible(false);
            mainPanel.setEnabled(false);

            if (loginPanel == null) {
                ImageSource is = (ImageSource) Application.services().getService(ImageSource.class);
                loginPanel = new LoginPanel(is.getImage("transaction.background"));
            }
            loginPanel.setVisible(true);
            loginPanel.setEnabled(true);
            panel.add(loginPanel, BorderLayout.CENTER);
            panel.validate();
            return;
        }
    }
}
