package co.th.aten.hospital.ui;

/*
 * Copyright (c) 2007, Romain Guy
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.
 *   * Neither the name of the TimingFramework project nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import javax.swing.JComponent;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.springframework.richclient.application.Application;
import org.springframework.richclient.image.ImageSource;

/**
 *
 * @author Romain Guy
 */
public class ReversibleGlassPane extends JComponent {

    private static final int BAR_WIDTH = 200;
    private static final int BAR_HEIGHT = 10;
//    private static final Color TEXT_COLOR = new Color(0x333333);
//    private static final Color BORDER_COLOR = new Color(0x333333);
    private static final Color TEXT_COLOR = Color.CYAN;
//    private static final Color BORDER_COLOR = new Color(0x333333);
//    private static final float[] GRADIENT_FRACTIONS = new float[]{
//        0.0f, 0.499f, 0.5f, 1.0f
//    };
//    private static final Color[] GRADIENT_COLORS = new Color[]{
//        Color.GRAY, Color.DARK_GRAY, Color.BLACK, Color.GRAY
//    };
//    private static final Color GRADIENT_COLOR2 = Color.WHITE;
//    private static final Color GRADIENT_COLOR1 = Color.GRAY;
    private String message = "Reversible Mode";
    private int progress = 0;
//    private BufferedImage image = null;
    private BufferedImage image;
    private BufferedImage glow;
    private float alpha = 0.0f;

    public ReversibleGlassPane() {
        setBackground(Color.DARK_GRAY);
        setFont(new Font("Default", Font.BOLD, 30));
        super.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        super.addKeyListener((new KeyAdapter() {
        }));
        super.addMouseListener((new MouseAdapter() {
        }));
        super.addMouseMotionListener((new MouseMotionAdapter() {
        }));
        try {
            image = GraphicsUtilities.loadCompatibleImage(getClass().getResource("/images/reversible_mode.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
//        int oldProgress = this.progress;
//        this.progress = progress;
//
//        // computes the damaged area
//        FontMetrics metrics = null;
//        if (getGraphics() != null) {
//            System.out.println("getGraphics()"+getGraphics().getClipBounds());
//            getGraphics().getFontMetrics(getFont());
//        }else{
//            metrics = getFontMetrics(getFont());
//            System.out.println("metrics.getDescent()="+metrics.getDescent());
//        }
//        int w = (int) (BAR_WIDTH * ((float) oldProgress / 100.0f));
//        int x = w + (getWidth() - BAR_WIDTH) / 2;
//        int y = (getHeight() - BAR_HEIGHT) / 2;
//        if (metrics != null) {
//            y += metrics.getDescent() / 2;
//        }
//
//        w = (int) (BAR_WIDTH * ((float) progress / 100.0f)) - w;
//        int h = BAR_HEIGHT;
//
//        repaint(x, y, w, h);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // enables anti-aliasing
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // gets the current clipping area
        Rectangle clip = g.getClipBounds();

        // sets a 65% translucent composite
        AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.7f);
        Composite composite = g2.getComposite();
        g2.setComposite(alpha);

        // fills the background
        g2.setColor(getBackground());
//        g2.setPaint(new GradientPaint(0.0f, 0.0f, new Color(0x666f7f).darker(),
//                0.0f, getHeight(), new Color(0x262d3d).darker()));
        g2.fillRect(clip.x, clip.y, clip.width, clip.height);
        // centers the progress bar on screen
        FontMetrics metrics = g.getFontMetrics();
        int x = (getWidth() - BAR_WIDTH + 50) / 2;
        int y = (getHeight() - BAR_HEIGHT - metrics.getDescent() - 140) / 2;

        // draws the text
        g2.setColor(TEXT_COLOR);
        g2.drawString(message, x, y);

        g2.drawString("Please trun off from RTM", x, y + 35);

//        // goes to the position of the progress bar
//        y += metrics.getDescent();
//
//        // computes the size of the progress indicator
//        int w = (int) (BAR_WIDTH * ((float) progress / 100.0f));
//        int h = BAR_HEIGHT;
//
//        // draws the content of the progress bar
//        Paint paint = g2.getPaint();
//
//        // bar's background
//        Paint gradient = new GradientPaint(x, y, GRADIENT_COLOR1,
//                x, y + h, GRADIENT_COLOR2);
//        g2.setPaint(gradient);
//        g2.fillRect(x, y, BAR_WIDTH, BAR_HEIGHT);
//
//        // actual progress
//        gradient = new LinearGradientPaint(x, y, x, y + h,
//                GRADIENT_FRACTIONS, GRADIENT_COLORS);
//        g2.setPaint(gradient);
//        g2.fillRect(x, y, w, h);
//
//        g2.setPaint(paint);
//
//        // draws the progress bar border
//        g2.drawRect(x, y, BAR_WIDTH, BAR_HEIGHT);

        g2.setComposite(composite);
        ImageSource is = (ImageSource) Application.services().getService(ImageSource.class);
        Image image2 = is.getImage("reversibleMode.icon");
        g2.drawImage(image2, (getWidth() / 2) - image2.getWidth(null) - 100,
                (getHeight() / 2) - image2.getHeight(null), null);
        image2 = is.getImage("qfree.logo");
        int width = image2.getWidth(null) + 10;
//        int height = image2.getHeight(null) ;

        g2.drawImage(image2, getWidth() - image2.getWidth(null),
                getHeight() - image2.getHeight(null), null);

        image2 = is.getImage("becl.logo");
        g2.drawImage(image2, getWidth() - width - image2.getWidth(null),
                getHeight() - image2.getHeight(null), null);
//
//        if (glow == null) {
//            glow = GraphicsUtilities.createCompatibleImage(image);
//            g2 = glow.createGraphics();
//            g2.drawImage(image, 0, 0, null);
//            g2.dispose();
//
//            BufferedImageOp filter = getGaussianBlurFilter(24, true);
//            glow = filter.filter(glow, null);
//            filter = getGaussianBlurFilter(24, false);
//            glow = filter.filter(glow, null);
//            filter = new ColorTintFilter(Color.WHITE, 1.0f);
//            glow = filter.filter(glow, null);
//
//            startAnimator();
//        }
//
//        x = (getWidth() - image.getWidth() - 370) / 2;
//        y = (getHeight() - image.getHeight() - 160) / 2;
//
//        g2 = (Graphics2D) g.create();
//
//        g2.setComposite(AlphaComposite.SrcOver.derive(getAlpha()));
//        g2.drawImage(glow, x, y, null);
//        g2.setComposite(AlphaComposite.SrcOver);
//        g2.drawImage(image, x, y, null);

        
    }

    private void startAnimator() {
        PropertySetter setter = new PropertySetter(this, "alpha", 0.0f, 0.7f);
        Animator animator = new Animator(1000, Animator.INFINITE,
                Animator.RepeatBehavior.REVERSE, setter);
        animator.start();
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        repaint();
    }

    public static ConvolveOp getGaussianBlurFilter(int radius,
            boolean horizontal) {
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be >= 1");
        }

        int size = radius * 2 + 1;
        float[] data = new float[size];

        float sigma = radius / 3.0f;
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;

        for (int i = -radius; i <= radius; i++) {
            float distance = i * i;
            int index = i + radius;
            data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
            total += data[index];
        }

        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }

        Kernel kernel = null;
        if (horizontal) {
            kernel = new Kernel(size, 1, data);
        } else {
            kernel = new Kernel(1, size, data);
        }
        return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    }
}
