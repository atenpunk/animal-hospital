package co.th.aten.football.ui.report;

import java.awt.BasicStroke;
import java.awt.Color;

import javax.swing.ImageIcon;

import com.jensoft.sw2d.core.democomponent.ImageResource;
import com.jensoft.sw2d.core.democomponent.Sw2dDemo;
import com.jensoft.sw2d.core.palette.Alpha;
import com.jensoft.sw2d.core.palette.ColorPalette;
import com.jensoft.sw2d.core.palette.FilPalette;
import com.jensoft.sw2d.core.palette.InputFonts;
import com.jensoft.sw2d.core.palette.PetalPalette;
import com.jensoft.sw2d.core.palette.RosePalette;
import com.jensoft.sw2d.core.palette.TangoPalette;
import com.jensoft.sw2d.core.plugin.grid.GridPlugin;
import com.jensoft.sw2d.core.plugin.grid.manager.DynamicGridManager;
import com.jensoft.sw2d.core.plugin.grid.manager.FreeGridManager;
import com.jensoft.sw2d.core.plugin.legend.Legend;
import com.jensoft.sw2d.core.plugin.legend.LegendConstraints;
import com.jensoft.sw2d.core.plugin.legend.LegendPlugin;
import com.jensoft.sw2d.core.plugin.legend.painter.fill.LegendFill1;
import com.jensoft.sw2d.core.plugin.metrics.axis.AxisMilliMetrics;
import com.jensoft.sw2d.core.plugin.outline.OutlinePlugin;
import com.jensoft.sw2d.core.plugin.pie.Pie;
import com.jensoft.sw2d.core.plugin.pie.PiePlugin;
import com.jensoft.sw2d.core.plugin.pie.PieSlice;
import com.jensoft.sw2d.core.plugin.pie.PieToolkit;
import com.jensoft.sw2d.core.plugin.pie.painter.effect.AbstractPieEffect;
import com.jensoft.sw2d.core.plugin.pie.painter.effect.PieCompoundEffect;
import com.jensoft.sw2d.core.plugin.pie.painter.effect.PieLinearEffect;
import com.jensoft.sw2d.core.plugin.pie.painter.effect.PieReflectionEffect;
import com.jensoft.sw2d.core.plugin.pie.painter.label.PieBorderLabel;
import com.jensoft.sw2d.core.plugin.stripe.StripePlugin;
import com.jensoft.sw2d.core.plugin.stripe.manager.DynamicStripeManager;
import com.jensoft.sw2d.core.plugin.stripe.painter.StripePalette;
import com.jensoft.sw2d.core.plugin.symbol.BarSymbol;
import com.jensoft.sw2d.core.plugin.symbol.BarSymbolGroup;
import com.jensoft.sw2d.core.plugin.symbol.BarSymbolLayer;
import com.jensoft.sw2d.core.plugin.symbol.PointSymbol;
import com.jensoft.sw2d.core.plugin.symbol.PointSymbolLayer;
import com.jensoft.sw2d.core.plugin.symbol.SymbolComponent;
import com.jensoft.sw2d.core.plugin.symbol.SymbolPlugin;
import com.jensoft.sw2d.core.plugin.symbol.SymbolToolkit;
import com.jensoft.sw2d.core.plugin.symbol.painter.axis.BarDefaultAxisLabel;
import com.jensoft.sw2d.core.plugin.symbol.painter.draw.BarDefaultDraw;
import com.jensoft.sw2d.core.plugin.symbol.painter.effect.BarEffect4;
import com.jensoft.sw2d.core.plugin.symbol.painter.fill.BarFill1;
import com.jensoft.sw2d.core.plugin.symbol.painter.point.PointSymbolImage;
import com.jensoft.sw2d.core.view.View2D;
import com.jensoft.sw2d.core.window.WindowPart;
import java.io.File;

public class ViewReportAccidentAndTrafficDlg extends Sw2dDemo {

    public static void main(String[] args) {

        try {

            final TemplateReportFrame templateFrame = new TemplateReportFrame();
            ViewReportAccidentAndTrafficDlg report = new ViewReportAccidentAndTrafficDlg();
            templateFrame.show(report);
            templateFrame.setView(report.getView());
            templateFrame.pack();
            templateFrame.setSize(900, 650);
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
    private View2D view;
    private boolean chk;

    public ViewReportAccidentAndTrafficDlg() {
    }

    public View2D showReport() {
        view = new View2D();
        chk = true;
        try {
            Runnable rr = new Runnable() {
                @Override
                public void run() {
                    if (chk) {
                        runShow();
                        chk = false;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            new Thread(rr).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    class ClimateMount extends Thread {

        public void run() {
            try {
                Color color = PetalPalette.PETAL1_HC;
                Color color1 = PetalPalette.PETAL2_HC;
                // Color color2 = PetalPalette.PETAL3_HC;
                Color color2 = Color.RED;
                Legend legend = new Legend("Report Yearly Traffic");
                legend.setLegendFill(new LegendFill1(Color.WHITE, FilPalette.YELLOW2));
                legend.setFont(InputFonts.getFont(InputFonts.NO_MOVE, 12));
                legend
                        .setConstraints(new LegendConstraints(
                        com.jensoft.sw2d.core.plugin.legend.LegendConstraints.LegendPosition.North,
                        0.2F,
                        com.jensoft.sw2d.core.plugin.legend.LegendConstraints.LegendAlignment.Rigth));
                Thread.sleep(100L);
                climateView.repaintView();
                com.jensoft.sw2d.core.window.Window2D.Linear linear = new com.jensoft.sw2d.core.window.Window2D.Linear(
                        0.0D, 0.0D, 0.0D, 300D);
                linear.setName("compatible vertical bar window");
                climateView.registerWindow2D(linear);
                LegendPlugin legendplugin = new LegendPlugin();
                legendplugin.addLegend(legend);
                linear.registerPlugin(legendplugin);
                linear.setThemeColor(RosePalette.MELON);
                linear.registerPlugin(new OutlinePlugin());
                AxisMilliMetrics axismillimetrics = new AxisMilliMetrics(0.0D,
                        com.jensoft.sw2d.core.plugin.metrics.axis.AxisMetricsPlugin.Axis.AxisEast);
                axismillimetrics.setMajor(100D);
                axismillimetrics.setMedian(50D);
                axismillimetrics.setMinor(10D);
                axismillimetrics.setMetricsLabelColor(color);
                axismillimetrics.setMetricsMarkerColor(color);
                linear.registerPlugin(axismillimetrics);
                linear.setThemeColor(RosePalette.MELON);
                Thread.sleep(100L);
                climateView.repaintView();
                DynamicStripeManager dynamicstripemanager = new DynamicStripeManager(
                        com.jensoft.sw2d.core.plugin.stripe.StripePlugin.StripeOrientation.Horizontal,
                        0.0D, 30D);
                StripePlugin stripeplugin = new StripePlugin(dynamicstripemanager);
                StripePalette stripepalette = new StripePalette();
                stripepalette.addPaint(new Color(255, 255, 255, 40));
                stripepalette.addPaint(ColorPalette.alpha(TangoPalette.ORANGE3, 40));
                dynamicstripemanager.setBandPalette(stripepalette);
                stripeplugin.setAlpha(0.3F);
                linear.registerPlugin(stripeplugin);
                climateView.repaintDevice();
                Thread.sleep(100L);
                DynamicGridManager dynamicgridmanager = new DynamicGridManager(
                        com.jensoft.sw2d.core.plugin.grid.Grid.GridOrientation.Horizontal, 0.0D,
                        30D);
                dynamicgridmanager.setGridColor(new Color(255, 255, 255, 60));
                GridPlugin gridplugin = new GridPlugin(dynamicgridmanager);
                linear.registerPlugin(gridplugin);
                climateView.repaintDevice();
                Thread.sleep(100L);
                FreeGridManager freegridmanager = new FreeGridManager();
                freegridmanager
                        .setGridOrientation(com.jensoft.sw2d.core.plugin.grid.Grid.GridOrientation.Horizontal);
                freegridmanager.addGrid(60D, "60 l/m.", new Alpha(color, 150), 0.9F);
                freegridmanager.addGrid(120D, "120 l/m.", new Alpha(color, 150), 0.9F);
                freegridmanager.addGrid(180D, "180 l/m", new Alpha(color, 150), 0.9F);
                freegridmanager.setGridStroke(new BasicStroke(0.8F));
                linear.registerPlugin(new GridPlugin(freegridmanager));
                climateView.repaintDevice();
                Thread.sleep(300L);
                Legend legend1 = new Legend("Traffic Value");
                legend1.setLegendFill(new LegendFill1(Color.WHITE, color));
                legend1.setFont(InputFonts.getFont(InputFonts.NO_MOVE, 14));
                legend1
                        .setConstraints(new LegendConstraints(
                        com.jensoft.sw2d.core.plugin.legend.LegendConstraints.LegendPosition.East,
                        0.5F,
                        com.jensoft.sw2d.core.plugin.legend.LegendConstraints.LegendAlignment.Middle));
                legendplugin.addLegend(legend1);
                climateView.repaintDevice();
                Thread.sleep(300L);
                double ad[] = {49.899999999999999D, 71.5D, 106.40000000000001D,
                    129.19999999999999D, 144D, 176D, 135.59999999999999D, 148.5D,
                    216.40000000000001D, 194.09999999999999D, 95.599999999999994D,
                    54.399999999999999D};
                BarSymbol barsymbol = SymbolToolkit.createBarSymbol("January", color,
                        com.jensoft.sw2d.core.plugin.symbol.BarSymbol.SymbolInflate.Ascent,
                        49.899999999999999D);
                BarSymbol barsymbol1 = SymbolToolkit.createBarSymbol("Februry", color,
                        com.jensoft.sw2d.core.plugin.symbol.BarSymbol.SymbolInflate.Ascent, 71.5D);
                BarSymbol barsymbol2 = SymbolToolkit.createBarSymbol("March", color,
                        com.jensoft.sw2d.core.plugin.symbol.BarSymbol.SymbolInflate.Ascent,
                        106.40000000000001D);
                BarSymbol barsymbol3 = SymbolToolkit.createBarSymbol("April", color,
                        com.jensoft.sw2d.core.plugin.symbol.BarSymbol.SymbolInflate.Ascent,
                        129.19999999999999D);
                BarSymbol barsymbol4 = SymbolToolkit.createBarSymbol("May", color,
                        com.jensoft.sw2d.core.plugin.symbol.BarSymbol.SymbolInflate.Ascent, 144D);
                BarSymbol barsymbol5 = SymbolToolkit.createBarSymbol("June", color,
                        com.jensoft.sw2d.core.plugin.symbol.BarSymbol.SymbolInflate.Ascent, 176D);
                BarSymbol barsymbol6 = SymbolToolkit.createBarSymbol("July", color,
                        com.jensoft.sw2d.core.plugin.symbol.BarSymbol.SymbolInflate.Ascent,
                        135.59999999999999D);
                BarSymbol barsymbol7 = SymbolToolkit.createBarSymbol("August", color,
                        com.jensoft.sw2d.core.plugin.symbol.BarSymbol.SymbolInflate.Ascent, 148.5D);
                BarSymbol barsymbol8 = SymbolToolkit.createBarSymbol("September", color,
                        com.jensoft.sw2d.core.plugin.symbol.BarSymbol.SymbolInflate.Ascent,
                        216.40000000000001D);
                BarSymbol barsymbol9 = SymbolToolkit.createBarSymbol("October", color,
                        com.jensoft.sw2d.core.plugin.symbol.BarSymbol.SymbolInflate.Ascent,
                        194.09999999999999D);
                BarSymbol barsymbol10 = SymbolToolkit.createBarSymbol("November", color,
                        com.jensoft.sw2d.core.plugin.symbol.BarSymbol.SymbolInflate.Ascent,
                        95.599999999999994D);
                BarSymbol barsymbol11 = SymbolToolkit.createBarSymbol("December", color,
                        com.jensoft.sw2d.core.plugin.symbol.BarSymbol.SymbolInflate.Ascent,
                        54.399999999999999D);
                BarSymbol abarsymbol[] = {barsymbol, barsymbol1, barsymbol2, barsymbol3,
                    barsymbol4, barsymbol5, barsymbol6, barsymbol7, barsymbol8, barsymbol9,
                    barsymbol10, barsymbol11};
                BarSymbolGroup barsymbolgroup = new BarSymbolGroup("G1");
                barsymbolgroup.setBase(0.0D);
                barsymbolgroup.setThickness(25D);
                barsymbolgroup.setRound(8);
                barsymbolgroup
                        .setMorpheStyle(com.jensoft.sw2d.core.plugin.symbol.BarSymbol.MorpheStyle.Round);
                barsymbolgroup.setBarDraw(new BarDefaultDraw(Color.WHITE));
                barsymbolgroup.setBarFill(new BarFill1());
                barsymbolgroup.setBarEffect(new BarEffect4());
                SymbolPlugin symbolplugin = new SymbolPlugin();
                symbolplugin
                        .setNature(com.jensoft.sw2d.core.plugin.symbol.SymbolPlugin.SymbolNature.Vertical);
                symbolplugin.setPriority(100);
                linear.registerPlugin(symbolplugin);
                BarSymbolLayer barsymbollayer = new BarSymbolLayer();
                symbolplugin.addLayer(barsymbollayer);
                barsymbollayer.addSymbol(SymbolComponent.createGlue(BarSymbol.class));
                barsymbollayer.addSymbol(barsymbolgroup);
                barsymbollayer.addSymbol(SymbolComponent.createGlue(BarSymbol.class));
                for (int i = 0; i < abarsymbol.length; i++) {
                    abarsymbol[i].setAscentValue(0.0D);
                    barsymbolgroup.addSymbol(abarsymbol[i]);
                    if (i < 11) {
                        barsymbolgroup.addSymbol((BarSymbol) SymbolComponent.createStrut(
                                BarSymbol.class, 20D));
                    }
                }

                Thread.sleep(200L);
                climateView.repaintView();
                for (int j = 0; j < abarsymbol.length; j++) {
                    System.out.println((new StringBuilder()).append("inflate value : ").append(
                            ad[j]).toString());
                    abarsymbol[j].inflate(ad[j], 0, 150, 20);
                    Thread.sleep(100L);
                }

                climateView.repaintDevice();
                for (int k = 0; k < abarsymbol.length; k++) {
                    BarDefaultAxisLabel bardefaultaxislabel = new BarDefaultAxisLabel("Jan",
                            Color.WHITE);
                    bardefaultaxislabel.setFont(InputFonts.getNeuropol(12));
                    bardefaultaxislabel.setTextColor(ColorPalette.GRAY);
                    bardefaultaxislabel.setText(abarsymbol[k].getSymbol().substring(0, 3));
                    abarsymbol[k].setAxisLabel(bardefaultaxislabel);
                    Thread.sleep(100L);
                    climateView.repaintPart(WindowPart.South);
                }

                Legend legend2 = new Legend("Accident");
                legend2.setLegendFill(new LegendFill1(Color.WHITE, color2));
                legend2.setFont(InputFonts.getFont(InputFonts.NO_MOVE, 14));
                legend2
                        .setConstraints(new LegendConstraints(
                        com.jensoft.sw2d.core.plugin.legend.LegendConstraints.LegendPosition.West,
                        0.5F,
                        com.jensoft.sw2d.core.plugin.legend.LegendConstraints.LegendAlignment.Middle));
                legendplugin.addLegend(legend2);
                climateView.repaintView();
                Thread.sleep(200L);
                com.jensoft.sw2d.core.window.Window2D.Linear linear1 = new com.jensoft.sw2d.core.window.Window2D.Linear(
                        0.0D, 0.0D, 0.0D, 30D);
                climateView.registerWindow2D(linear1);
                AxisMilliMetrics axismillimetrics1 = new AxisMilliMetrics(0.0D,
                        com.jensoft.sw2d.core.plugin.metrics.axis.AxisMetricsPlugin.Axis.AxisWest);
                axismillimetrics1.setMajor(10D);
                axismillimetrics1.setMedian(5D);
                axismillimetrics1.setMinor(1.0D);
                axismillimetrics1.setMetricsLabelColor(color2);
                axismillimetrics1.setMetricsMarkerColor(color2);
                linear1.registerPlugin(axismillimetrics1);
                climateView.repaintView();
                Thread.sleep(200L);
                SymbolPlugin symbolplugin1 = new SymbolPlugin();
                symbolplugin1
                        .setNature(com.jensoft.sw2d.core.plugin.symbol.SymbolPlugin.SymbolNature.Vertical);
                symbolplugin1.setPriority(100);
                linear1.registerPlugin(symbolplugin1);
                PointSymbolLayer pointsymbollayer = new PointSymbolLayer();
                symbolplugin1.addLayer(pointsymbollayer);
                PointSymbol pointsymbol = new PointSymbol(5D);
                PointSymbol pointsymbol1 = new PointSymbol(4.9000000000000004D);
                PointSymbol pointsymbol2 = new PointSymbol(8.5D);
                PointSymbol pointsymbol3 = new PointSymbol(12.5D);
                PointSymbol pointsymbol4 = new PointSymbol(16.199999999999999D);
                PointSymbol pointsymbol5 = new PointSymbol(19.5D);
                PointSymbol pointsymbol6 = new PointSymbol(23.199999999999999D);
                PointSymbol pointsymbol7 = new PointSymbol(24.5D);
                PointSymbol pointsymbol8 = new PointSymbol(21.300000000000001D);
                PointSymbol pointsymbol9 = new PointSymbol(16.300000000000001D);
                PointSymbol pointsymbol10 = new PointSymbol(11.9D);
                PointSymbol pointsymbol11 = new PointSymbol(7.5999999999999996D);
                PointSymbol apointsymbol[] = {pointsymbol, pointsymbol1, pointsymbol2,
                    pointsymbol3, pointsymbol4, pointsymbol5, pointsymbol6, pointsymbol7,
                    pointsymbol8, pointsymbol9, pointsymbol10, pointsymbol11};
                pointsymbollayer.addSymbol(SymbolComponent.createGlue(PointSymbol.class));
                for (int l = 0; l < apointsymbol.length; l++) {
                    ImageIcon imageicon = new ImageIcon(System.getProperty("user.dir") + "/img" + File.separator + "insident_r.png");
                    PointSymbolImage pointsymbolimage = new PointSymbolImage(imageicon.getImage());
                    apointsymbol[l].setVisible(false);
                    apointsymbol[l].addPointSymbolPainter(pointsymbolimage);
                    pointsymbollayer.addSymbol(apointsymbol[l]);
                    if (l < 11) {
                        pointsymbollayer.addSymbol(SymbolComponent.createStrut(PointSymbol.class,
                                45D));
                    }
                }

                pointsymbollayer.addSymbol(SymbolComponent.createGlue(PointSymbol.class));
                for (int i1 = 0; i1 < apointsymbol.length; i1++) {
                    apointsymbol[i1].setVisible(true);
                    climateView.repaintDevice();
                    Thread.sleep(80L);
                }

                climateView.repaintDevice();
                Thread.sleep(300L);
                Pie pie = PieToolkit.createPie("pie", 30);
                pie.setStartAngleDegree(10D);
                PieSlice pieslice = PieToolkit.createSlice("s1", ColorPalette.alpha(color2, 240),
                        25D, 0);
                PieSlice pieslice1 = PieToolkit.createSlice("s2", color, 75D, 0);
                PieToolkit.pushSlices(pie, new PieSlice[]{pieslice, pieslice1});
                pie.setPieNature(com.jensoft.sw2d.core.plugin.pie.Pie.PieNature.PieDevice);
                pie.setCenterX(50D);
                pie.setCenterY(80D);
                PiePlugin pieplugin = new PiePlugin();
                pieplugin.setPriority(100);
                pieplugin.addPie(pie);
                linear.registerPlugin(pieplugin);
                climateView.repaintDevice();
                Thread.sleep(200L);
                pieslice.setDivergence(10);
                climateView.repaintDevice();
                Thread.sleep(200L);
                PieLinearEffect pielineareffect = new PieLinearEffect();
                pie.setPieEffect(pielineareffect);
                climateView.repaintDevice();
                Thread.sleep(200L);
                PieReflectionEffect piereflectioneffect = new PieReflectionEffect();
                PieCompoundEffect piecompoundeffect = new PieCompoundEffect(
                        new AbstractPieEffect[]{pielineareffect, piereflectioneffect});
                pielineareffect.setOffsetRadius(2);
                pie.setPieEffect(piecompoundeffect);
                climateView.repaintDevice();
                Thread.sleep(200L);
                PieBorderLabel pieborderlabel = new PieBorderLabel();
                pieborderlabel.setLabelFont(InputFonts.getNoMove(10));
                pieborderlabel.setFillColor(ColorPalette.alpha(color2, 240));
                pieborderlabel.setOutlineColor(ColorPalette.alpha(color2, 240));
                pieborderlabel.setLinkColor(ColorPalette.alpha(color2, 240));
                pieborderlabel.setLabelPaddingY(2);
                pieborderlabel.setLabelPaddingX(4);
                pieborderlabel.setOutlineRound(12);
                float af[] = {0.0F, 0.5F, 1.0F};
                Color acolor[] = {Color.DARK_GRAY, Color.BLACK, Color.DARK_GRAY};
                pieborderlabel.setShader(af, acolor);
                pieborderlabel.setLabel("Accident");
                pieslice.addSliceLabel(pieborderlabel);
                climateView.repaintDevice();
                // Thread.sleep(2000L);
                // climateView.unregisterWindow2D(linear);
                // climateView.unregisterWindow2D(linear1);
                // climateView.repaint();
                Thread.sleep(500L);
            } catch (InterruptedException interruptedexception) {
                interruptedexception.printStackTrace();
            }
        }
        private View2D climateView;

        public ClimateMount(View2D view2d) {
            super();
            climateView = view2d;
        }
    }

    public void runShow() {
        try {
            view.setName("ReportTest");
            view.setBackground(Color.BLACK);
            view.getWidgetPlugin().pushMessage("Report Bar", 0, null,
                    com.jensoft.sw2d.core.view.WidgetPlugin.PushingBehavior.VerySlow,
                    InputFonts.getNeuropol(14), Color.WHITE);
            ClimateMount climatemount = new ClimateMount(view);
            climatemount.start();
            // climatemount.join();
            view.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View2D createView2D() {
        return showReport();
    }

    public View2D getView() {
        return view;
    }

    public void setView(View2D view) {
        this.view = view;
    }

    public boolean isChk() {
        return chk;
    }

    public void setChk(boolean chk) {
        this.chk = chk;
    }
}
