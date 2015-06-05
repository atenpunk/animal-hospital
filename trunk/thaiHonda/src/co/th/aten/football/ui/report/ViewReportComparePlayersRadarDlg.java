package co.th.aten.football.ui.report;

import co.th.aten.football.Configuration;
import co.th.aten.football.model.PlayersModel;
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
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ViewReportComparePlayersRadarDlg extends Sw2dDemo {

    private List<PlayersModel> playersModelList;
    private int flag;
    private DecimalFormat df = new DecimalFormat("#,###");
    private final Log logger = LogFactory.getLog(getClass());

    public static void main(String[] args) {

        try {
            Configuration.loadConfig();
            final TemplateReportFrame templateFrame = new TemplateReportFrame();
            List<PlayersModel> playersModelList = new ArrayList<PlayersModel>();
            PlayersModel model = new PlayersModel();
            model.setGc(420000d);
            model.setPlayingTime(1200);
            model.setStarter(14);
            playersModelList.add(model);
            ViewReportComparePlayersRadarDlg report = new ViewReportComparePlayersRadarDlg(playersModelList, 0);
            templateFrame.show(report);
//            templateFrame.setView(report.getView());
//            templateFrame.pack();
            templateFrame.setSize(465, 360);
//            templateFrame.setSize(865, 660);
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

    public ViewReportComparePlayersRadarDlg(List<PlayersModel> playersModelList, int flag) {
        this.playersModelList = playersModelList;
        this.flag = flag;
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
            view.setPlaceHolderAxisEast(150);
            view.setPlaceHolderAxisNorth(2);
            view.setPlaceHolderAxisSouth(2);
            view.setPlaceHolderAxisWest(150);
            Window2D radarWindow2D = new Window2D.Linear(-100, 100, -100, 100);
            radarWindow2D.setName("compatible pie window");
            RadarPlugin radarPlugin = new RadarPlugin();
            radarPlugin.setPriority(100);
            radarWindow2D.registerPlugin(radarPlugin);
            if (flag == 0) {
                radarWindow2D.registerPlugin(new OutlinePlugin(Color.BLACK));
            }

            final Radar radar = new Radar(0, 0, 120);
            radar.setCenterX(-30);
            radar.setCenterY(-27);
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
                java.awt.Font font3 = InputFonts.getFont(InputFonts.NO_MOVE, 10);
                if (playersModelList != null && playersModelList.size() > 0) {
                    com.jensoft.sw2d.core.plugin.radar.RadarSurface radarsurface = RadarToolkit
                            .createSurface("surface1", ColorPalette.alpha(NanoChromatique.BLUE, 240)
                            , ColorPalette.alpha(NanoChromatique.BLUE, 40));
                    radar.addSurface(radarsurface);
                    GlyphFill glyphfill = new GlyphFill(Color.BLUE, NanoChromatique.BLUE);
                    RoundMarker roundmarker = new RoundMarker(NanoChromatique.BLUE,
                            Color.WHITE, 3);
                    RadarSurfaceAnchor radarsurfaceanchor = RadarToolkit.createSurfaceAnchor(
                            radardimension, "", playersModelList.get(0).getGc(), StylePosition.Default, 25, glyphfill,
                            roundmarker, font3);
                    RadarSurfaceAnchor radarsurfaceanchor2 = RadarToolkit.createSurfaceAnchor(
                            radardimension2, "", playersModelList.get(0).getMatch(), StylePosition.Default, 10, glyphfill,
                            roundmarker, font3);
                    RadarSurfaceAnchor radarsurfaceanchor4 = RadarToolkit.createSurfaceAnchor(
                            radardimension4, "", playersModelList.get(0).getPlayingTime(), StylePosition.Default, 10, glyphfill,
                            roundmarker, font3);
                    RadarToolkit.pushAnchors(radarsurface, new RadarSurfaceAnchor[]{
                        radarsurfaceanchor, radarsurfaceanchor2, radarsurfaceanchor4});
                }
                
                if (playersModelList != null && playersModelList.size() > 1) {
                    com.jensoft.sw2d.core.plugin.radar.RadarSurface radarsurface = RadarToolkit
                            .createSurface("surface1", ColorPalette.alpha(NanoChromatique.GRAY, 240)
                            , ColorPalette.alpha(NanoChromatique.GRAY, 40));
                    radar.addSurface(radarsurface);
                    GlyphFill glyphfill = new GlyphFill(Color.GRAY, NanoChromatique.GRAY);
                    RoundMarker roundmarker = new RoundMarker(NanoChromatique.GRAY,
                            Color.WHITE, 3);
                    RadarSurfaceAnchor radarsurfaceanchor = RadarToolkit.createSurfaceAnchor(
                            radardimension, "", playersModelList.get(1).getGc(), StylePosition.Default, 25, glyphfill,
                            roundmarker, font3);
                    RadarSurfaceAnchor radarsurfaceanchor2 = RadarToolkit.createSurfaceAnchor(
                            radardimension2, "", playersModelList.get(1).getMatch(), StylePosition.Default, 10, glyphfill,
                            roundmarker, font3);
                    RadarSurfaceAnchor radarsurfaceanchor4 = RadarToolkit.createSurfaceAnchor(
                            radardimension4, "", playersModelList.get(1).getPlayingTime(), StylePosition.Default, 10, glyphfill,
                            roundmarker, font3);
                    RadarToolkit.pushAnchors(radarsurface, new RadarSurfaceAnchor[]{
                        radarsurfaceanchor, radarsurfaceanchor2, radarsurfaceanchor4});
                }
                
                if (playersModelList != null && playersModelList.size() > 2) {
                    com.jensoft.sw2d.core.plugin.radar.RadarSurface radarsurface = RadarToolkit
                            .createSurface("surface1", ColorPalette.alpha(NanoChromatique.GREEN, 240)
                            , ColorPalette.alpha(NanoChromatique.GREEN, 40));
                    radar.addSurface(radarsurface);
                    GlyphFill glyphfill = new GlyphFill(Color.GREEN, NanoChromatique.GREEN);
                    RoundMarker roundmarker = new RoundMarker(NanoChromatique.GREEN,
                            Color.WHITE, 3);
                    RadarSurfaceAnchor radarsurfaceanchor = RadarToolkit.createSurfaceAnchor(
                            radardimension, "", playersModelList.get(2).getGc(), StylePosition.Default, 25, glyphfill,
                            roundmarker, font3);
                    RadarSurfaceAnchor radarsurfaceanchor2 = RadarToolkit.createSurfaceAnchor(
                            radardimension2, "", playersModelList.get(2).getMatch(), StylePosition.Default, 10, glyphfill,
                            roundmarker, font3);
                    RadarSurfaceAnchor radarsurfaceanchor4 = RadarToolkit.createSurfaceAnchor(
                            radardimension4, "", playersModelList.get(2).getPlayingTime(), StylePosition.Default, 10, glyphfill,
                            roundmarker, font3);
                    RadarToolkit.pushAnchors(radarsurface, new RadarSurfaceAnchor[]{
                        radarsurfaceanchor, radarsurfaceanchor2, radarsurfaceanchor4});
                }
                
                if (playersModelList != null && playersModelList.size() > 3) {
                    com.jensoft.sw2d.core.plugin.radar.RadarSurface radarsurface = RadarToolkit
                            .createSurface("surface1", ColorPalette.alpha(NanoChromatique.ORANGE, 240)
                            , ColorPalette.alpha(NanoChromatique.ORANGE, 40));
                    radar.addSurface(radarsurface);
                    GlyphFill glyphfill = new GlyphFill(Color.ORANGE, NanoChromatique.ORANGE);
                    RoundMarker roundmarker = new RoundMarker(NanoChromatique.ORANGE,
                            Color.WHITE, 3);
                    RadarSurfaceAnchor radarsurfaceanchor = RadarToolkit.createSurfaceAnchor(
                            radardimension, "", playersModelList.get(3).getGc(), StylePosition.Default, 25, glyphfill,
                            roundmarker, font3);
                    RadarSurfaceAnchor radarsurfaceanchor2 = RadarToolkit.createSurfaceAnchor(
                            radardimension2, "", playersModelList.get(3).getMatch(), StylePosition.Default, 10, glyphfill,
                            roundmarker, font3);
                    RadarSurfaceAnchor radarsurfaceanchor4 = RadarToolkit.createSurfaceAnchor(
                            radardimension4, "", playersModelList.get(3).getPlayingTime(), StylePosition.Default, 10, glyphfill,
                            roundmarker, font3);
                    RadarToolkit.pushAnchors(radarsurface, new RadarSurfaceAnchor[]{
                        radarsurfaceanchor, radarsurfaceanchor2, radarsurfaceanchor4});
                }
                
                if (playersModelList != null && playersModelList.size() > 4) {
                    com.jensoft.sw2d.core.plugin.radar.RadarSurface radarsurface = RadarToolkit
                            .createSurface("surface1", ColorPalette.alpha(NanoChromatique.RED, 240)
                            , ColorPalette.alpha(NanoChromatique.RED, 40));
                    radar.addSurface(radarsurface);
                    GlyphFill glyphfill = new GlyphFill(Color.RED, NanoChromatique.RED);
                    RoundMarker roundmarker = new RoundMarker(NanoChromatique.RED,
                            Color.WHITE, 3);
                    RadarSurfaceAnchor radarsurfaceanchor = RadarToolkit.createSurfaceAnchor(
                            radardimension, "", playersModelList.get(4).getGc(), StylePosition.Default, 25, glyphfill,
                            roundmarker, font3);
                    RadarSurfaceAnchor radarsurfaceanchor2 = RadarToolkit.createSurfaceAnchor(
                            radardimension2, "", playersModelList.get(4).getMatch(), StylePosition.Default, 10, glyphfill,
                            roundmarker, font3);
                    RadarSurfaceAnchor radarsurfaceanchor4 = RadarToolkit.createSurfaceAnchor(
                            radardimension4, "", playersModelList.get(4).getPlayingTime(), StylePosition.Default, 10, glyphfill,
                            roundmarker, font3);
                    RadarToolkit.pushAnchors(radarsurface, new RadarSurfaceAnchor[]{
                        radarsurfaceanchor, radarsurfaceanchor2, radarsurfaceanchor4});
                }
                
                com.jensoft.sw2d.core.plugin.radar.RadarSurface radarsurface1 = RadarToolkit
                        .createSurface("surface2", Color.WHITE, ColorPalette.alpha(
                        RosePalette.PLUMWINE, 20));
                radar.addSurface(radarsurface1);
                view.repaintDevice();
                GlyphFill glyphfill = new GlyphFill(Color.WHITE, NanoChromatique.ORANGE);
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
                RoundMarker roundmarker = new RoundMarker(RosePalette.REDWOOD, Color.WHITE, 3);
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
