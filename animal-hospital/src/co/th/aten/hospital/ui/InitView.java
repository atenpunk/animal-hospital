/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.ui;

import co.th.aten.hospital.event.LoginSuccessEvent;
import co.th.aten.hospital.event.LogoutEvent;
import co.th.aten.hospital.event.MessageEvent;
import co.th.aten.hospital.model.MessageModel;
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

//            SessionManager sessionManager = (SessionManager) Application.services().getService(SessionManager.class);
//            PosService posService = (PosService) Application.services().getService(PosService.class);
//            sessionManager.stopReadStaffCard();
//            MessageManager messageManager = (MessageManager) Application.services().getService(MessageManager.class);
//            MessageSource ms = (MessageSource) Application.services().getService(MessageSource.class);
//            messageManager.showMessage("");
//            String[] args = new String[]{sessionManager.getSession().getStaff().getCssfLocalname() + " "
//                + sessionManager.getSession().getStaff().getCssfLocalsurname()
//                + " (" + sessionManager.getSession().getStaff().getStaffPK().getCssfStaffid() + ")"};
//            messageManager.showMessage(ms.getMessage("msg.login.loginSuccess", args, Locale.getDefault()), 5);
//            sessionManager.createSecurityToken();
//            sessionManager.getSession().setJob(posService.beginJob());
            loginPanel.clearScreen();
            loginPanel.setVisible(false);
            loginPanel.setEnabled(false);
            panel.remove(loginPanel);
            if (mainPanel == null) {
                ImageSource is = (ImageSource) Application.services().getService(ImageSource.class);
                mainPanel = new MainPanel(is.getImage("transaction.background"));
                panel.add(mainPanel, BorderLayout.CENTER);
            }
//            sessionManager.getSession().setUserMode((short) 0);
//            if (sessionManager.getSession().getStaff().getCssfStaffgroupid() == Constants.GROUP_TC) {
//                sessionManager.getSession().setUserMode((short) 1);
//            }
//            if (sessionManager.getSession().getStaff().getCssfStaffgroupid() == Constants.GROUP_CTC || sessionManager.getSession().getStaff().getCssfStaffgroupid() == Constants.GROUP_RAD_23) {
//                sessionManager.getSession().setUserMode((short) 2);
//                if (sessionManager.getSession().getChiefOnDuty() == null) {
//                    LocationModel location = sessionManager.getLocation();
//                    HessianManager hessian = (HessianManager) Application.services().getService(HessianManager.class);
//                    TcaApi api = hessian.createApi();
//                    if (api.assignChiefOnDuty(location.getNetworkId(), location.getTsbId(), sessionManager.getSession().getStaff().getStaffPK().getCssfStaffid(), sessionManager.getSecurityToken())) {
//                        sessionManager.getSession().setChiefOnDuty(sessionManager.getSession().getStaff());
//                    } else {
//                        logger.error("assign Chief On Duty Fail....");
//                    }
//                } else if (sessionManager.getSession().getStaff().getStaffPK().getCssfStaffid() != sessionManager.getSession().getChiefOnDuty().getStaffPK().getCssfStaffid()) {
//                    if (chiefOnDutyDialog == null) {
//                        chiefOnDutyDialog = new ChiefOnDutyDialog();
//                    }
//                    chiefOnDutyDialog.showDialog();
//                }
//                if (infoPanel != null) {
//                    infoPanel.updateChiefOnDuty();
//                }
//            }
//            sessionManager.createSecurityToken();
            mainPanel.grantAccess();
            mainPanel.setVisible(true);
            mainPanel.setEnabled(true);
//            logger.info("config Loaded" + sessionManager.getSession().isConfigLoaded());
            panel.validate();
            this.getWindowControl().repaint();
        }

        if (event instanceof LogoutEvent) {

//            String todMode = "";
//            SessionManager sessionManager = (SessionManager) Application.services().getService(SessionManager.class);
//            logger.info("configloaded=" + sessionManager.getSession().isConfigLoaded());
//            if (sessionManager.getSession().isConfigLoaded()) {
//                String terminalType = sessionManager.getSettings().getString("terminal_type");
//                if (terminalType != null && terminalType.equalsIgnoreCase("TOD") || com.qfree.eta.etc.pos.Configuration.isTodMode) {
//                    todMode = " [TOD - สำหรับบันทึกการบันเงิน] ";
//                    isTodMode = true;
//                }
//            }
            String build = Application.instance().getDescriptor().getVersion() + " ( " + Application.instance().getDescriptor().getBuildId() + " )";
            ((DefaultViewDescriptor) getDescriptor()).setTitle(getMessage("header.welcome") + "  v." + build);

//            PosService posService = (PosService) Application.services().getService(PosService.class);
//            if (posService.endJob()) {
//                sessionManager.getSession().setJob(null);
//                sessionManager.getSession().setStaff(null);
//                sessionManager.createSecurityToken();
//                sessionManager.startReadStaffCard();
//                MessageManager messageManager = (MessageManager) Application.services().getService(MessageManager.class);
//                MessageSource ms = (MessageSource) Application.services().getService(MessageSource.class);
//                messageManager.showMessage(ms.getMessage("msg.login.insertStaffCard", null, Locale.getDefault()));
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
//            }
            return;
        }
    }

    private void restoreMainPanel() {
        if (mainPanel == null) {
            ImageSource is = (ImageSource) Application.services().getService(ImageSource.class);
            mainPanel = new MainPanel(is.getImage("transaction.background"));
        }

        mainPanel.setVisible(true);
        mainPanel.setEnabled(true);
//        mainPanel.reRender();
        panel.add(mainPanel, BorderLayout.CENTER);
    }
}
