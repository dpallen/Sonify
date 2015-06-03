package sonifiedspectra.controllers;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.plot.XYPlot;
import sonifiedspectra.model.Project;
import sonifiedspectra.model.Note;
import sonifiedspectra.view.SonifiedSpectra;

import java.util.ArrayList;

/**
 * Created by Hvandenberg on 6/1/15.
 */
public class GraphController implements ChartMouseListener {

    private SonifiedSpectra app;
    private Project project;

    public GraphController(SonifiedSpectra app, Project project) {
        this.app = app;
        this.project = project;
    }

    @Override
    public void chartMouseClicked(final ChartMouseEvent chartMouseEvent) {
        //Crosshair values are not valid until after the chart has been updated
        //that is why call repaint() now and post Crosshair value retrieval on the
        //awt thread queue to get them when repaint() is finished
        app.getChPanel().repaint();

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                JFreeChart chart = chartMouseEvent.getChart();
                XYPlot plot = chart.getXYPlot();

                double x = plot.getDomainCrosshairValue();

                ArrayList<Note> selectedNotes = app.getActivePhrase().getSelectedNotes();

                System.out.println("Active phrase x1: " + app.getActivePhrase().getX1() + ", x2: " + app.getActivePhrase().getX2());

                if (!app.getLeftOrRightCheckbox().isSelected() && x > app.getActivePhrase().getX1()) {
                    app.getActivePhrase().setX2(x);
                }
                else if (app.getLeftOrRightCheckbox().isSelected() && x < app.getActivePhrase().getX2()) app.getActivePhrase().setX1(x);

                app.getActivePhrase().initialize();
                app.getActivePhrase().setSelectedNotes(selectedNotes);
                app.updateActivePhrase(app.getActivePhrase());
                app.updateIntervalMarker();
                app.getFrame().pack();

            }
        });
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {
        int x = chartMouseEvent.getTrigger().getX();
        int y = chartMouseEvent.getTrigger().getY();

        ChartEntity entity = chartMouseEvent.getEntity();
        if (entity != null) {
            /*double diff1 = x - app.getActivePhrase().getX1();
            if (diff1 < 0) diff1 = -1*diff1;
            double diff2 = x - app.getActivePhrase().getX2();
            if (diff1 < 0) diff2 = -1*diff2;
            double diff = diff1;
            if (diff2 < diff) diff = diff2;

            if (diff < 10) {
                IntervalMarker im;
                if (!app.getLeftOrRightCheckbox().isSelected()) {
                    im = new IntervalMarker(app.getActivePhrase().getX1() - 10,
                            app.getActivePhrase().getX1() + 10);
                }
                else im = new IntervalMarker(app.getActivePhrase().getX2() - 10,
                        app.getActivePhrase().getX2() + 10);
                chartMouseEvent.getChart().getXYPlot().addDomainMarker(im);
            }*/

        }
        else {
            System.out.println("Mouse moved: " + x + ", " + y + ": null entity.");
        }
    }
}
