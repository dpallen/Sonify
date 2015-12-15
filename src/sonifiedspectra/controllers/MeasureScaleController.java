package sonifiedspectra.controllers;

import sonifiedspectra.model.Note;
import sonifiedspectra.model.Phrase;
import sonifiedspectra.model.Project;
import sonifiedspectra.view.MeasureHeadView;
import sonifiedspectra.view.PhraseInTrackView;
import sonifiedspectra.view.Sonify;
import sonifiedspectra.view.TrackView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Hvandenberg on 12/14/15.
 */
public class MeasureScaleController implements ChangeListener {

    private Sonify app;
    private Project project;

    public MeasureScaleController(Sonify app, Project project) {
        this.app = app;
        this.project = project;
    }

    @Override
    public void stateChanged(ChangeEvent e) {

        app.setMeasureScale(Integer.valueOf(app.getMeasureZoomSlider().getValue()));

        for (TrackView tv : app.getTrackViewArray()) {
            if (tv.getTrack().isSelected()) {
                //tv.setBackground(activePhrase.getUnselectedColor());
                tv.setBorder(BorderFactory.createLineBorder(app.getActivePhrase().getBorderColor(), 3, false));
            }
            for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray()) {
                pitv.adjustSize(tv.getTrack().isExpanded());
                if (pitv.getPhrase().isLoop()) pitv.getNameLabel().setForeground(Color.WHITE);
                if (app.getActivePhrase().getId() == pitv.getPhrase().getId() || (pitv.getPhrase().getParentPhrase() != null && app.getActivePhrase().getId() == pitv.getPhrase().getParentPhrase().getId())) {
                    if (!pitv.getPhrase().isLoop()) {
                        pitv.getNameLabel().setText(app.getActivePhrase().getCompound().getName());
                    }
                    pitv.getTopPanel().setBorder(BorderFactory.createLineBorder(app.getActivePhrase().getBorderColor(), 2, false));
                    pitv.repaint();
                }
                else {
                    pitv.getTopPanel().setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
                    pitv.repaint();
                }
            }
        }

        for (TrackView tv : app.getTrackViewArray()) {
            tv.setBounds(tv.getX(), tv.getY(), app.getMeasureHeadViewArray().size() * app.getMeasureScale() * 4, tv.getHeight());
            tv.repaint();
        }

        int i = 0;

        for (MeasureHeadView mhv : app.getMeasureHeadViewArray()) {
            mhv.setBounds(i * 4 * app.getMeasureScale(), mhv.getY(), 4 * app.getMeasureScale(), mhv.getHeight());
            i++;
            mhv.repaint();
        }

        app.getInTracksPanel().setPreferredSize(new Dimension(4 * app.getMeasureScale() * app.getActiveProject().getNumMeasures(),
                70 * app.getTrackViewArray().size()));

        app.getMeasureHeadPanel().setPreferredSize(new Dimension(2 + 4 * app.getMeasureScale() * app.getMeasureHeadViewArray().size(), 33));

        app.getFrame().pack();

    }

}
