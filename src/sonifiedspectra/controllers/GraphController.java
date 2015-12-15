package sonifiedspectra.controllers;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.plot.XYPlot;
import sonifiedspectra.model.Phrase;
import sonifiedspectra.model.Project;
import sonifiedspectra.model.Note;
import sonifiedspectra.view.PhraseInTrackView;
import sonifiedspectra.view.Sonify;
import sonifiedspectra.view.TrackView;

import java.util.ArrayList;

/**
 * Created by Hvandenberg on 6/1/15.
 */
public class GraphController implements ChartMouseListener {

    private Sonify app;
    private Project project;

    public GraphController(Sonify app, Project project) {
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

                ArrayList<Phrase> toUpdate = new ArrayList<Phrase>();
                toUpdate.add(app.getActivePhrase());

                for (TrackView tv : app.getTrackViewArray()) {
                    for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray()) {
                        if (pitv.getPhrase().getParentPhrase() != null) {
                            if (pitv.getPhrase().getParentPhrase().getId() == app.getActivePhrase().getId())
                                toUpdate.add(pitv.getPhrase());
                        }
                    }
                }

                for (Phrase p :toUpdate) {

                    ArrayList<Note> selectedNotes = p.getSelectedNotes();

                    System.out.println("Phrase x1: " + p.getX1() + ", x2: " + p.getX2());

                    if (!app.getLeftOrRightCheckbox().isSelected() && x > p.getX2()) {
                        p.setX1(x);
                    } else if (app.getLeftOrRightCheckbox().isSelected() && x < p.getX1())
                        p.setX2(x);

                    p.initialize();
                    System.out.println("Phrase x2: " + p.getX2());
                    System.out.println("Note x2: " + p.getNotesArray().get(p.getNotesArray().size() - 1).getPeak().getX2());
                    p.setSelectedNotes(selectedNotes);

                }

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
