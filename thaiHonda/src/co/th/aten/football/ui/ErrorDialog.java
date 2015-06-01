package co.th.aten.football.ui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.springframework.richclient.dialog.ApplicationDialog;

/**
 * Shows the usage of the confirmation dialog.
 *
 * @author Jan Hoskens
 *
 */
public class ErrorDialog extends ApplicationDialog {

    private String message;

    public ErrorDialog( Component parent,String title, String message) {
        super(title, parent);
        this.message = message;
        this.getDialog().setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public ErrorDialog() {
    }

    @Override
    protected JComponent createButtonBar() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        JButton button = new JButton(" Close ");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                onFinish();
            }
        });
        panel.add(button);
        return panel;
    }

    @Override
    protected boolean onFinish() {
        dispose();
        return true;
    }

    @Override
    protected JComponent createDialogContentPane() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        panel.add(label);
        return panel;
    }
}
