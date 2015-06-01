/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.ui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Aten
 */
public class ImagePanel extends JPanel {

    private Image img;

    public ImagePanel() {
    }

    public ImagePanel(String img) {
        this(new ImageIcon(img).getImage());
    }

    public ImagePanel(Image img) {
        this.img = img;
//        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
//        setPreferredSize(size);
//        setMinimumSize(size);
//        setMaximumSize(size);
//        setSize(size);
//        setLayout(null);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (img != null) {
            double width = this.getSize().getWidth();
            double height = this.getSize().getHeight();
            g.drawImage(img, 0, 0, (int) width, (int) height, this);
        }
    }
}
