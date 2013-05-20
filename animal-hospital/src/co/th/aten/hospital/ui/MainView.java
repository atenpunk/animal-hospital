/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.hospital.ui;

import javax.swing.JComponent;
import org.springframework.richclient.application.support.AbstractView;

/**
 *
 * @author Mai
 */

public class MainView extends AbstractView {

    @Override
    protected JComponent createControl() {
        MainPanel panel = new MainPanel();
        return panel;
    }

}
