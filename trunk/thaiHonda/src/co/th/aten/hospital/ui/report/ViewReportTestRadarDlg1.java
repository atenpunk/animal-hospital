package co.th.aten.hospital.ui.report;

import co.th.aten.hospital.ui.report.TemplateReportFrame;
import java.awt.Color;

import com.jensoft.sw2d.core.democomponent.Sw2dDemo;
import com.jensoft.sw2d.core.glyphmetrics.StylePosition;
import com.jensoft.sw2d.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.sw2d.core.glyphmetrics.painter.marker.RoundMarker;
import com.jensoft.sw2d.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.sw2d.core.palette.ColorPalette;
import com.jensoft.sw2d.core.palette.InputFonts;
import com.jensoft.sw2d.core.palette.NanoChromatique;
import com.jensoft.sw2d.core.palette.PetalPalette;
import com.jensoft.sw2d.core.palette.RosePalette;
import com.jensoft.sw2d.core.plugin.outline.OutlinePlugin;
import com.jensoft.sw2d.core.plugin.radar.DimensionMetrics;
import com.jensoft.sw2d.core.plugin.radar.Radar;
import com.jensoft.sw2d.core.plugin.radar.RadarDimension;
import com.jensoft.sw2d.core.plugin.radar.RadarPlugin;
import com.jensoft.sw2d.core.plugin.radar.RadarSurfaceAnchor;
import com.jensoft.sw2d.core.plugin.radar.RadarToolkit;
import com.jensoft.sw2d.core.plugin.radar.painter.dimension.DimensionDefaultPainter;
import com.jensoft.sw2d.core.plugin.radar.painter.radar.RadarDefaultPainter;
import com.jensoft.sw2d.core.view.View2D;
import com.jensoft.sw2d.core.window.Window2D;
import javax.swing.SwingUtilities;

public class ViewReportTestRadarDlg1 extends Sw2dDemo {

    public static void main(String[] args) {

        try {

            final TemplateReportFrame templateFrame = new TemplateReportFrame();
            ViewReportTestRadarDlg1 report = new ViewReportTestRadarDlg1();
            templateFrame.show(report);
            templateFrame.setView(report.getView());
            templateFrame.pack();
            templateFrame.setSize(400, 250);
            templateFrame.setLocation(250, 120);

//            final TemplateReportFrame templateFrame = new TemplateReportFrame();
//            SwingUtilities.invokeLater(new Runnable() {
//                @Override
//                public void run() {
//                    ViewReportTestRadarDlg report = new ViewReportTestRadarDlg();
//                    templateFrame.show(report);
//                    templateFrame.setView(report.getView());
//                }
//            });
//            templateFrame.pack();
//            templateFrame.setSize(800, 500);
//            templateFrame.setLocation(250, 120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ViewReportTestRadarDlg1() {
    }
    private View2D view;

    public View2D getView() {
        return view;
    }

    public void setView(View2D view) {
        this.view = view;
    }

    @Override
    public View2D createView2D() {
        view = new View2D();
        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    class ShiftStartAngleAnimator extends Thread {

        public void run() {
            try {
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
        private int sleep;

        public ShiftStartAngleAnimator() {
            sleep = 20;
        }
    }

    class AnimatorRadar extends Thread {

        public void run() {
            view.repaint();
            try {
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }

        }
        int sleep;

        AnimatorRadar() {
            sleep = 20;
        }
    }

    private void run() {
        try {
            view.setName("ReportRadar");
            view.setBackground(Color.BLACK);
            Window2D radarWindow2D = new Window2D.Linear(-1, 1, -1, 1);
            radarWindow2D.setName("compatible pie window");
            RadarPlugin radarPlugin = new RadarPlugin();
            radarPlugin.setPriority(100);
            radarWindow2D.registerPlugin(radarPlugin);
            radarWindow2D.registerPlugin(new OutlinePlugin(Color.WHITE));

            final Radar radar = new Radar(0, 0, 80);
            radar.setRadarPainter(new RadarDefaultPainter());
            final RadarDimension radardimension = new RadarDimension("d1", 0.0D, 0.0D, 100D);
            radardimension.setDimensionPainter(new DimensionDefaultPainter());
            final RadarDimension radardimension1 = new RadarDimension("d2", 60D, 0.0D, 100D);
            radardimension1.setDimensionPainter(new DimensionDefaultPainter());
            final RadarDimension radardimension2 = new RadarDimension("d3", 120D, 0.0D, 100D);
            radardimension2.setDimensionPainter(new DimensionDefaultPainter());
            final RadarDimension radardimension3 = new RadarDimension("d4", 180D, 0.0D, 100D);
            radardimension3.setDimensionPainter(new DimensionDefaultPainter());
            final RadarDimension radardimension4 = new RadarDimension("d5", 240D, 0.0D, 100D);
            radardimension4.setDimensionPainter(new DimensionDefaultPainter());
            final RadarDimension radardimension5 = new RadarDimension("d6", 300D, 0.0D, 100D);
            radardimension5.setDimensionPainter(new DimensionDefaultPainter());

            RadarToolkit.pushDimensions(radar, new RadarDimension[]{radardimension,
                radardimension1, radardimension2, radardimension3, radardimension4,
                radardimension5});
            java.awt.Font font = InputFonts.getFont(InputFonts.NO_MOVE, 12);
            float af[] = {0.0F, 0.3F, 0.7F, 1.0F};
            Color acolor[] = {new Color(0, 0, 0, 20), new Color(0, 0, 0, 150),
                new Color(0, 0, 0, 150), new Color(0, 0, 0, 20)};
            com.jensoft.sw2d.core.plugin.radar.painter.label.RadarDimensionDefaultLabel radardimensiondefaultlabel = RadarToolkit
                    .createDimensionDefaultLabel("Type 1", font, ColorPalette.WHITE,
                    RosePalette.REDWOOD, af, acolor, 20);
            com.jensoft.sw2d.core.plugin.radar.painter.label.RadarDimensionDefaultLabel radardimensiondefaultlabel1 = RadarToolkit
                    .createDimensionDefaultLabel("Type 2", font, ColorPalette.WHITE,
                    RosePalette.EMERALD, af, acolor, 20);
            com.jensoft.sw2d.core.plugin.radar.painter.label.RadarDimensionDefaultLabel radardimensiondefaultlabel2 = RadarToolkit
                    .createDimensionDefaultLabel("Type 3", font, ColorPalette.WHITE,
                    RosePalette.MELON, af, acolor, 20);
            com.jensoft.sw2d.core.plugin.radar.painter.label.RadarDimensionDefaultLabel radardimensiondefaultlabel3 = RadarToolkit
                    .createDimensionDefaultLabel("Type 4", font, ColorPalette.WHITE,
                    RosePalette.CORALRED, af, acolor, 20);
            com.jensoft.sw2d.core.plugin.radar.painter.label.RadarDimensionDefaultLabel radardimensiondefaultlabel4 = RadarToolkit
                    .createDimensionDefaultLabel("Type 5", font, ColorPalette.WHITE,
                    RosePalette.AZALEA, af, acolor, 20);
            com.jensoft.sw2d.core.plugin.radar.painter.label.RadarDimensionDefaultLabel radardimensiondefaultlabel5 = RadarToolkit
                    .createDimensionDefaultLabel("Type 6", font, ColorPalette.WHITE,
                    RosePalette.INDIGO, af, acolor, 20);
            radardimension.setDimensionLabel(radardimensiondefaultlabel);
            radardimension1.setDimensionLabel(radardimensiondefaultlabel1);
            radardimension2.setDimensionLabel(radardimensiondefaultlabel2);
            radardimension3.setDimensionLabel(radardimensiondefaultlabel3);
            radardimension4.setDimensionLabel(radardimensiondefaultlabel4);
            radardimension5.setDimensionLabel(radardimensiondefaultlabel5);
            radarPlugin.addRadar(radar);
            view.registerWindow2D(radarWindow2D);
            Runnable r1 = new Runnable() {
                @Override
                public void run() {
                    System.out.println("RUN RUN");
                    try {
                        Thread.sleep(500);
                        com.jensoft.sw2d.core.plugin.radar.RadarSurface radarsurface = RadarToolkit
                                .createSurface("surface1", Color.WHITE, ColorPalette.alpha(
                                RosePalette.AZALEA, 80));
                        radar.addSurface(radarsurface);
                        java.awt.Font font3 = InputFonts.getFont(InputFonts.NEUROPOL, 10);
                        GlyphFill glyphfill = new GlyphFill(Color.WHITE, NanoChromatique.GREEN);
                        RoundMarker roundmarker = new RoundMarker(NanoChromatique.GREEN,
                                Color.WHITE, 3);
                        RadarSurfaceAnchor radarsurfaceanchor = RadarToolkit.createSurfaceAnchor(
                                radardimension, "A1", 67D, StylePosition.Default, 10, glyphfill,
                                roundmarker, font3);
                        RadarSurfaceAnchor radarsurfaceanchor1 = RadarToolkit.createSurfaceAnchor(
                                radardimension1, "A2", 70D, StylePosition.Default, 10, glyphfill,
                                roundmarker, font3);
                        RadarSurfaceAnchor radarsurfaceanchor2 = RadarToolkit.createSurfaceAnchor(
                                radardimension2, "A3", 17D, StylePosition.Default, 10, glyphfill,
                                roundmarker, font3);
                        RadarSurfaceAnchor radarsurfaceanchor3 = RadarToolkit.createSurfaceAnchor(
                                radardimension3, "A4", 44D, StylePosition.Default, 10, glyphfill,
                                roundmarker, font3);
                        RadarSurfaceAnchor radarsurfaceanchor4 = RadarToolkit.createSurfaceAnchor(
                                radardimension4, "A5", 58D, StylePosition.Default, 10, glyphfill,
                                roundmarker, font3);
                        RadarSurfaceAnchor radarsurfaceanchor5 = RadarToolkit.createSurfaceAnchor(
                                radardimension5, "A6", 36D, StylePosition.Default, 10, glyphfill,
                                roundmarker, font3);
                        RadarToolkit.pushAnchors(radarsurface, new RadarSurfaceAnchor[]{
                            radarsurfaceanchor, radarsurfaceanchor1, radarsurfaceanchor2,
                            radarsurfaceanchor3, radarsurfaceanchor4, radarsurfaceanchor5});
                        com.jensoft.sw2d.core.plugin.radar.RadarSurface radarsurface1 = RadarToolkit
                                .createSurface("surface2", Color.WHITE, ColorPalette.alpha(
                                RosePalette.EMERALD, 80));
                        radar.addSurface(radarsurface1);
                        view.repaintDevice();
                        Thread.sleep(500);
                        glyphfill = new GlyphFill(Color.WHITE, NanoChromatique.ORANGE);
                        roundmarker = new RoundMarker(NanoChromatique.ORANGE, Color.WHITE, 3);
                        RadarSurfaceAnchor radarsurfaceanchor6 = RadarToolkit.createSurfaceAnchor(
                                radardimension, "A1", 12D, StylePosition.Default, 10, glyphfill,
                                roundmarker, font3);
                        RadarSurfaceAnchor radarsurfaceanchor7 = RadarToolkit.createSurfaceAnchor(
                                radardimension1, "A2", 25D, StylePosition.Default, 10, glyphfill,
                                roundmarker, font3);
                        RadarSurfaceAnchor radarsurfaceanchor8 = RadarToolkit.createSurfaceAnchor(
                                radardimension2, "A3", 80D, StylePosition.Default, 10, glyphfill,
                                roundmarker, font3);
                        RadarSurfaceAnchor radarsurfaceanchor9 = RadarToolkit.createSurfaceAnchor(
                                radardimension3, "A4", 30D, StylePosition.Default, 10, glyphfill,
                                roundmarker, font3);
                        RadarSurfaceAnchor radarsurfaceanchor10 = RadarToolkit.createSurfaceAnchor(
                                radardimension4, "A5", 19D, StylePosition.Default, 10, glyphfill,
                                roundmarker, font3);
                        RadarSurfaceAnchor radarsurfaceanchor11 = RadarToolkit.createSurfaceAnchor(
                                radardimension5, "A6", 80D, StylePosition.Default, 10, glyphfill,
                                roundmarker, font3);
                        RadarToolkit.pushAnchors(radarsurface1, new RadarSurfaceAnchor[]{
                            radarsurfaceanchor6, radarsurfaceanchor7, radarsurfaceanchor8,
                            radarsurfaceanchor9, radarsurfaceanchor10, radarsurfaceanchor11});
                        view.repaintDevice();
                        Thread.sleep(500);
                        java.awt.Font font1 = InputFonts.getFont(InputFonts.SANSATION_REGULAR, 12);
                        glyphfill = new GlyphFill(RosePalette.MANDARIN, RosePalette.MELON);
                        roundmarker = new RoundMarker(PetalPalette.PETAL1_HC, Color.WHITE, 3);
                        DimensionMetrics dimensionmetrics = RadarToolkit.createDimensionMetrics(
                                "100", 100D, StylePosition.Default, 20, glyphfill, roundmarker,
                                font1);
                        DimensionMetrics dimensionmetrics1 = RadarToolkit.createDimensionMetrics(
                                "100", 100D, StylePosition.Default, 20, glyphfill, roundmarker,
                                font1);
                        DimensionMetrics dimensionmetrics2 = RadarToolkit.createDimensionMetrics(
                                "100", 100D, StylePosition.Default, 20, glyphfill, roundmarker,
                                font1);
                        DimensionMetrics dimensionmetrics3 = RadarToolkit.createDimensionMetrics(
                                "100", 100D, StylePosition.Default, 20, glyphfill, roundmarker,
                                font1);
                        DimensionMetrics dimensionmetrics4 = RadarToolkit.createDimensionMetrics(
                                "100", 100D, StylePosition.Default, 20, glyphfill, roundmarker,
                                font1);
                        DimensionMetrics dimensionmetrics5 = RadarToolkit.createDimensionMetrics(
                                "100", 100D, StylePosition.Default, 20, glyphfill, roundmarker,
                                font1);
                        radardimension.addMetrics(dimensionmetrics);
                        radardimension1.addMetrics(dimensionmetrics1);
                        radardimension2.addMetrics(dimensionmetrics2);
                        radardimension3.addMetrics(dimensionmetrics3);
                        radardimension4.addMetrics(dimensionmetrics4);
                        radardimension5.addMetrics(dimensionmetrics5);
                        view.repaintDevice();
                        Thread.sleep(500);
                        java.awt.Font font2 = InputFonts.getFont(InputFonts.PHOENIX, 12);
                        GlyphFill glyphfill1 = new GlyphFill(ColorPalette.WHITE,
                                RosePalette.REDWOOD);
                        GlyphFill glyphfill2 = new GlyphFill(ColorPalette.WHITE,
                                RosePalette.AEGEANBLUE);
                        TicTacMarker tictacmarker = new TicTacMarker(RosePalette.REDWOOD, 2, 4);
                        TicTacMarker tictacmarker1 = new TicTacMarker(RosePalette.AEGEANBLUE, 2, 4);
                        DimensionMetrics dimensionmetrics6 = RadarToolkit.createDimensionMetrics(
                                "20", 20D, StylePosition.Default, 10, glyphfill1, tictacmarker,
                                font2);
                        DimensionMetrics dimensionmetrics7 = RadarToolkit.createDimensionMetrics(
                                "40", 40D, StylePosition.Default, 10, glyphfill1, tictacmarker,
                                font2);
                        DimensionMetrics dimensionmetrics8 = RadarToolkit.createDimensionMetrics(
                                "60", 60D, StylePosition.Default, 10, glyphfill1, tictacmarker,
                                font2);
                        DimensionMetrics dimensionmetrics9 = RadarToolkit.createDimensionMetrics(
                                "80", 80D, StylePosition.Default, 10, glyphfill1, tictacmarker,
                                font2);
                        DimensionMetrics dimensionmetrics10 = RadarToolkit.createDimensionMetrics(
                                "20", 20D, StylePosition.Default, 10, glyphfill2, tictacmarker1,
                                font2);
                        DimensionMetrics dimensionmetrics11 = RadarToolkit.createDimensionMetrics(
                                "40", 40D, StylePosition.Default, 10, glyphfill2, tictacmarker1,
                                font2);
                        DimensionMetrics dimensionmetrics12 = RadarToolkit.createDimensionMetrics(
                                "60", 60D, StylePosition.Default, 10, glyphfill2, tictacmarker1,
                                font2);
                        DimensionMetrics dimensionmetrics13 = RadarToolkit.createDimensionMetrics(
                                "80", 80D, StylePosition.Default, 10, glyphfill2, tictacmarker1,
                                font2);
                        radardimension.addMetrics(dimensionmetrics6);
                        Thread.sleep(100);
                        view.repaintDevice();
                        radardimension.addMetrics(dimensionmetrics7);
                        Thread.sleep(100);
                        view.repaintDevice();
                        radardimension.addMetrics(dimensionmetrics8);
                        Thread.sleep(100);
                        view.repaintDevice();
                        radardimension.addMetrics(dimensionmetrics9);
                        Thread.sleep(100);
                        view.repaintDevice();
                        Thread.sleep(100);
                        RadarToolkit.pushMetricsDimensions(radardimension1, new DimensionMetrics[]{
                            dimensionmetrics10, dimensionmetrics11, dimensionmetrics12,
                            dimensionmetrics13});
                        view.repaintDevice();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            new Thread(r1).start();

            view.repaintDevice();
        } catch (Exception e) {
        }
    }
}
