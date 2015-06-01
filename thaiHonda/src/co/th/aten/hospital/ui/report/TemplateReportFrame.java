package co.th.aten.hospital.ui.report;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.jensoft.sw2d.core.democomponent.ImageResource;
import com.jensoft.sw2d.core.democomponent.Sw2dDemo;
import com.jensoft.sw2d.core.view.View2D;

public class TemplateReportFrame extends JFrame {

    private static final long serialVersionUID = 156889765687899L;
    private Sw2dDemo demo;
    private View2D view;

    public void show(Sw2dDemo demo) {
        this.demo = demo;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
//        ImageIcon iconFrame = new ImageIcon(getClass().getResource("/images/jensoft.png"));
//        setIconImage(iconFrame.getImage());
        setTitle("M7");
        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        // setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel masterPane = new JPanel();
        masterPane.setBackground(Color.WHITE);

        masterPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        masterPane.setLayout(new BorderLayout());

        // ComandBar cbar = new ComandBar();
        // cbar.setTitle("Report");
        // ComandGroup c1 = new ComandGroup("Report");
        // c1.setTabColor(Color.DARK_GRAY);
        // ImageIcon icon1 =
        // ImageResource.getInstance().createImageIcon("/icons/jensoft.png",
        // "");
        // c1.setTabIcon(icon1);
        // cbar.addComandTab(c1, demo.createView2D());
        // c1.setSelected(true);

        masterPane.add(demo.createView2D(), BorderLayout.CENTER);
        masterPane.add(new PanelButton(), BorderLayout.NORTH);
        getContentPane().add(masterPane, BorderLayout.CENTER);
        setVisible(true);
    }

    class PanelButton extends JComponent {

        public PanelButton() {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setOpaque(false);

            JButton copy = new JButton("Save");
            copy.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
//                    Display display = new Display();
//                    Shell shell = new Shell(display);
//                    FileDialog dialog = new FileDialog(shell, SWT.SAVE);
//                    dialog.setFilterPath("c:\\"); // Windows path
//                    dialog.setFilterExtensions(new String[]{"*.png"});
//                    System.out.println("Save to: " + dialog.open());
//                    String name = dialog.getFileName();
//                    try {
//                        View2D v = view;
//
//                        int w = (int) view.getBounds().getWidth();
//                        int h = (int) view.getBounds().getHeight();
//
//                        BufferedImage image = v.getImageView(w, h);
//                        String portfolioDirectory = dialog.getFilterPath();
//                        File portfolioDir = new File(portfolioDirectory);
//                        portfolioDir.mkdirs();
//                        try {
//                            FileOutputStream out = new FileOutputStream(portfolioDirectory
//                                    + File.separator + name);
//                            ImageIO.write(image, "png".toLowerCase(), out);
//                            out.close();
//                            display.dispose();
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    } catch (Throwable ex) {
//                        ex.printStackTrace();
//                    }
                    // JOptionPane.showMessageDialog(null, "save image
                    // complete");
                }
            });
            add(Box.createGlue());
            add(copy);
            add(Box.createHorizontalStrut(40));
        }
    }

    public View2D getView() {
        return view;
    }

    public void setView(View2D view) {
        this.view = view;
    }
}
