package co.th.aten.hospital.ui.report;

import co.th.aten.hospital.Configuration;
import java.awt.Color;

import com.jensoft.sw2d.core.democomponent.Sw2dDemo;
import com.jensoft.sw2d.core.glyphmetrics.StylePosition;
import com.jensoft.sw2d.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.sw2d.core.glyphmetrics.painter.marker.RoundMarker;
import com.jensoft.sw2d.core.palette.ColorPalette;
import com.jensoft.sw2d.core.palette.InputFonts;
import com.jensoft.sw2d.core.palette.NanoChromatique;
import com.jensoft.sw2d.core.palette.PetalPalette;
import com.jensoft.sw2d.core.palette.RosePalette;
import com.jensoft.sw2d.core.palette.TangoPalette;
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
import java.awt.BasicStroke;
import java.text.DecimalFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ViewReportTestRadarDlg extends Sw2dDemo {

    private double gc;
    private int match;
    private int playingTime;
    private DecimalFormat df = new DecimalFormat("#,###");
    private final Log logger = LogFactory.getLog(getClass());

    public static void main(String[] args) {

        try {
            Configuration.loadConfig();
            final TemplateReportFrame templateFrame = new TemplateReportFrame();
            ViewReportTestRadarDlg report = new ViewReportTestRadarDlg(4000000, 20, 1590);
            templateFrame.show(report);
//            templateFrame.setView(report.getView());
//            templateFrame.pack();
            templateFrame.setSize(465, 425);
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

    public ViewReportTestRadarDlg(double gc, int match, int playingTime) {
        this.gc = gc;
        this.match = match;
        this.playingTime = playingTime;
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

    private void run() {
        try {
            view.setName("ReportRadar");
            view.setBackground(Color.WHITE);
            view.setPlaceHolderAxisEast(1);
            view.setPlaceHolderAxisNorth(1);
            view.setPlaceHolderAxisSouth(1);
            view.setPlaceHolderAxisWest(1);
            Window2D radarWindow2D = new Window2D.Linear(-1, 1, -1, 1);
            radarWindow2D.setName("compatible pie window");
            RadarPlugin radarPlugin = new RadarPlugin();
            radarPlugin.setPriority(100);
            radarWindow2D.registerPlugin(radarPlugin);
            radarWindow2D.registerPlugin(new OutlinePlugin(Color.BLACK));

            final Radar radar = new Radar(0, 0, 100);
            radar.setRadarPainter(new RadarDefaultPainter());
            final RadarDimension radardimension = new RadarDimension("GC", 90.0D, 0.0D, Configuration.getInt("GC"));
            radardimension.setDimensionPainter(new DimensionDefaultPainter());
            final RadarDimension radardimension2 = new RadarDimension("Match", 210D, 0.0D, Configuration.getInt("Match"));
            radardimension2.setDimensionPainter(new DimensionDefaultPainter());
            final RadarDimension radardimension4 = new RadarDimension("Playing Time", 330D, 0.0D, Configuration.getInt("PlayingTime"));
            radardimension4.setDimensionPainter(new DimensionDefaultPainter());

            RadarToolkit.pushDimensions(radar, new RadarDimension[]{radardimension, radardimension2, radardimension4});
            java.awt.Font font = InputFonts.getFont(InputFonts.NO_MOVE, 12);
            float af[] = {0.0F, 0.3F, 0.7F, 1.0F};
            Color acolor[] = {new Color(0, 0, 0, 100), new Color(0, 0, 0, 255),
                new Color(0, 0, 0, 255), new Color(0, 0, 0, 100)};
            BasicStroke basicstroke = new BasicStroke(2.0F);
            com.jensoft.sw2d.core.plugin.radar.painter.label.RadarDimensionDefaultLabel radardimensiondefaultlabel = RadarToolkit
                    .createDimensionDefaultLabel("GC", font, ColorPalette.WHITE,
                    RosePalette.REDWOOD, af, acolor, 20);
            radardimensiondefaultlabel.setOutlineStroke(basicstroke);
            com.jensoft.sw2d.core.plugin.radar.painter.label.RadarDimensionDefaultLabel radardimensiondefaultlabel2 = RadarToolkit
                    .createDimensionDefaultLabel("Match", font, ColorPalette.WHITE,
                    RosePalette.REDWOOD, af, acolor, 20);
            radardimensiondefaultlabel2.setOutlineStroke(basicstroke);
            com.jensoft.sw2d.core.plugin.radar.painter.label.RadarDimensionDefaultLabel radardimensiondefaultlabel4 = RadarToolkit
                    .createDimensionDefaultLabel("Playing Time", font, ColorPalette.WHITE,
                    RosePalette.REDWOOD, af, acolor, 20);
            radardimensiondefaultlabel4.setOutlineStroke(basicstroke);
            radardimension.setDimensionLabel(radardimensiondefaultlabel);
            radardimension2.setDimensionLabel(radardimensiondefaultlabel2);
            radardimension4.setDimensionLabel(radardimensiondefaultlabel4);
            radarPlugin.addRadar(radar);
            view.registerWindow2D(radarWindow2D);
            try {
                com.jensoft.sw2d.core.plugin.radar.RadarSurface radarsurface = RadarToolkit
                        .createSurface("surface1", NanoChromatique.BLUE, ColorPalette.alpha(
                        RosePalette.LEAFGREEN, 80));
                radar.addSurface(radarsurface);
                java.awt.Font font3 = InputFonts.getFont(InputFonts.NO_MOVE, 10);
                GlyphFill glyphfill = new GlyphFill(ColorPalette.alpha(TangoPalette.BUTTER2, 240));
                RoundMarker roundmarker = new RoundMarker(NanoChromatique.BLUE,
                        Color.WHITE, 3);
                RadarSurfaceAnchor radarsurfaceanchor = RadarToolkit.createSurfaceAnchor(
                        radardimension, df.format(gc), gc, StylePosition.Default, 25, glyphfill,
                        roundmarker, font3);
                RadarSurfaceAnchor radarsurfaceanchor2 = RadarToolkit.createSurfaceAnchor(
                        radardimension2, df.format(match), match, StylePosition.Default, 10, glyphfill,
                        roundmarker, font3);
                RadarSurfaceAnchor radarsurfaceanchor4 = RadarToolkit.createSurfaceAnchor(
                        radardimension4, df.format(playingTime), playingTime, StylePosition.Default, 10, glyphfill,
                        roundmarker, font3);
                RadarToolkit.pushAnchors(radarsurface, new RadarSurfaceAnchor[]{
                    radarsurfaceanchor, radarsurfaceanchor2, radarsurfaceanchor4});

                com.jensoft.sw2d.core.plugin.radar.RadarSurface radarsurface1 = RadarToolkit
                        .createSurface("surface2", Color.WHITE, ColorPalette.alpha(
                        RosePalette.PLUMWINE, 20));
                radar.addSurface(radarsurface1);
                view.repaintDevice();
                glyphfill = new GlyphFill(Color.WHITE, NanoChromatique.ORANGE);
                RadarSurfaceAnchor radarsurfaceanchor6 = RadarToolkit.createSurfaceAnchor(
                        radardimension, "", Configuration.getDouble("GC"), StylePosition.Default, 10, glyphfill,
                        null, font3);
                RadarSurfaceAnchor radarsurfaceanchor8 = RadarToolkit.createSurfaceAnchor(
                        radardimension2, "", Configuration.getDouble("Match"), StylePosition.Default, 10, glyphfill,
                        null, font3);
                RadarSurfaceAnchor radarsurfaceanchor10 = RadarToolkit.createSurfaceAnchor(
                        radardimension4, "", Configuration.getDouble("PlayingTime"), StylePosition.Default, 10, glyphfill,
                        null, font3);
                RadarToolkit.pushAnchors(radarsurface1, new RadarSurfaceAnchor[]{radarsurfaceanchor6, radarsurfaceanchor8, radarsurfaceanchor10});
                view.repaintDevice();

                java.awt.Font font1 = InputFonts.getFont(InputFonts.SANSATION_REGULAR, 12);
                glyphfill = new GlyphFill(RosePalette.MANDARIN, RosePalette.CHOCOLATE);
                roundmarker = new RoundMarker(RosePalette.REDWOOD, Color.WHITE, 3);
                DimensionMetrics dimensionmetrics = RadarToolkit.createDimensionMetrics(
                        "", Configuration.getDouble("GC"), StylePosition.Default, 20, glyphfill, roundmarker,
                        font1);
                DimensionMetrics dimensionmetrics2 = RadarToolkit.createDimensionMetrics(
                        "", Configuration.getDouble("Match"), StylePosition.Default, 20, glyphfill, roundmarker,
                        font1);
                DimensionMetrics dimensionmetrics4 = RadarToolkit.createDimensionMetrics(
                        "", Configuration.getDouble("PlayingTime"), StylePosition.Default, 20, glyphfill, roundmarker,
                        font1);
                radardimension.addMetrics(dimensionmetrics);
                radardimension2.addMetrics(dimensionmetrics2);
                radardimension4.addMetrics(dimensionmetrics4);
                view.repaintDevice();

            } catch (Exception e) {
                e.printStackTrace();
            }

            view.repaintDevice();
        } catch (Exception e) {
            logger.info("" + e);
            e.printStackTrace();
        }
    }
}
