package co.th.aten.football.ui.report;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import com.jensoft.sw2d.core.democomponent.Sw2dDemo;
import com.jensoft.sw2d.core.palette.ColorPalette;
import com.jensoft.sw2d.core.palette.InputFonts;
import com.jensoft.sw2d.core.palette.NanoChromatique;
import com.jensoft.sw2d.core.palette.RosePalette;
import com.jensoft.sw2d.core.palette.TangoPalette;
import com.jensoft.sw2d.core.plugin.pie.Pie;
import com.jensoft.sw2d.core.plugin.pie.PiePlugin;
import com.jensoft.sw2d.core.plugin.pie.PieSlice;
import com.jensoft.sw2d.core.plugin.pie.PieToolkit;
import com.jensoft.sw2d.core.plugin.pie.painter.effect.PieLinearEffect;
import com.jensoft.sw2d.core.plugin.pie.painter.label.PieBoundLabel;
import com.jensoft.sw2d.core.view.View2D;

public class ViewReportPieDlg extends Sw2dDemo {

    private View2D view;
    private Pie pie;
    private PieSlice pieslice01;
    private PieSlice pieslice02;
    private int data01;
    private int data02;
    public static void main(String[] args) {
        try {
            final TemplateReportFrame templateFrame = new TemplateReportFrame();
            ViewReportPieDlg report = new ViewReportPieDlg(75,25);
            templateFrame.show(report);
            templateFrame.setSize(220, 255);
            templateFrame.setLocation(250, 120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ViewReportPieDlg(int data01, int data02) {
        this.data01 = data01;
        this.data02 = data02;
    }

    public void runShow() {
        try {
            view.setName("ReportPie");
            view.setBackground(Color.WHITE);
            view.setPlaceHolderAxisEast(1);
            view.setPlaceHolderAxisNorth(1);
            view.setPlaceHolderAxisSouth(1);
            view.setPlaceHolderAxisWest(1);
            com.jensoft.sw2d.core.window.Window2D.Linear linear = new com.jensoft.sw2d.core.window.Window2D.Linear(
                    -1D, 1.0D, -1D, 1.0D);
            view.registerWindow2D(linear);
            pie = PieToolkit.createPie("pie", 60);
            PieLinearEffect pielineareffect = new PieLinearEffect();
            pielineareffect.setOffsetRadius(5);
            pie.setPieEffect(pielineareffect);
            pieslice01 = PieToolkit.createSlice("s1", ColorPalette.alpha(TangoPalette.BUTTER2, 200), data01);
            pieslice02 = PieToolkit.createSlice("s2", ColorPalette.alpha(NanoChromatique.BLUE, 200), data02);
            PieToolkit
                    .pushSlices(pie, new PieSlice[]{pieslice01, pieslice02});
            PiePlugin pieplugin = new PiePlugin();
            linear.registerPlugin(pieplugin);
            pieplugin.addPie(pie);
            linear.getView2D().repaintDevice();
            view.repaintDevice();
            float af[] = {0.0F, 0.5F, 1.0F};
            Color acolor[] = {new Color(0, 0, 0, 100), new Color(0, 0, 0, 255),
                new Color(0, 0, 0, 255)};
            BasicStroke basicstroke = new BasicStroke(2.0F);
            pie.setPassiveLabelAtMinPercent(0.0D);
            view.repaintDevice();
            Font font = InputFonts.getNoMove(12);
            PieBoundLabel pieborderlabel1 = PieToolkit.createBoundLabel(data02+"%",
                    ColorPalette.WHITE, font);
            pieborderlabel1
                    .setStyle(com.jensoft.sw2d.core.plugin.pie.painter.label.AbstractPieSliceLabel.Style.Both);
            pieborderlabel1.setOutlineStroke(basicstroke);
            pieborderlabel1.setShader(af, acolor);
            pieborderlabel1.setOutlineColor(RosePalette.REDWOOD);
            pieborderlabel1.setOutlineRound(20);
            pieslice02.addSliceLabel(pieborderlabel1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View2D createView2D() {
        view = new View2D();
        runShow();
        return view;
    }

    public View2D getView() {
        return view;
    }

    public void setView(View2D view) {
        this.view = view;
    }
}
