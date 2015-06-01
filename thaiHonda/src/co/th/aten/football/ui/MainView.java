/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.football.ui;

import javax.swing.JComponent;
import org.springframework.richclient.application.support.AbstractView;

/**
 *
 * @author Aten
 */

public class MainView extends AbstractView {

    @Override
    protected JComponent createControl() {
        MainPanel panel = new MainPanel();
        return panel;
    }

}
