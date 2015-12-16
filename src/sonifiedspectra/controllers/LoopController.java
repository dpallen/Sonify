package sonifiedspectra.controllers;

import jm.music.data.Part;
import sonifiedspectra.model.Project;
import sonifiedspectra.view.PhraseInTrackView;
import sonifiedspectra.view.Sonify;
import sonifiedspectra.view.TrackView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class LoopController implements ActionListener, MouseListener {

    private Sonify app;
    private Project project;
    private boolean visible;

    public LoopController(Sonify app, Project project) {
        this.app = app;
        this.project = project;
        this.visible = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        app.getLoopDialog().getSelectedLabel().setText("Selected: " + app.getActiveProject().getSelectedTracks().size()
                + " tracks, " + app.getSelectedMeasures().size() + " measures");

        for (TrackView tv : app.getTrackViewArray()) {
            if (!tv.getTrack().isLoop()) {
                for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray()) {
                    if (pitv.isSelected()) {
                        if (pitv.getPhrase().isLoop()) app.getLoopDialog().getLoopComboBox()
                                .setSelectedIndex(app.getLoopDialog().getLoopsArray().indexOf(pitv.getPhrase()));
                    }
                }
            }
        }

        //app.getLoopDialog().getMeasureLabel().setText((app.getLoopDialog().getLoopsArray()
        //        .get(app.getLoopDialog().getLoopComboBox().getSelectedIndex()).getBeatLength2() / 4) + " measures");

        app.getLoopDialog().getLoopPlayer().updateLoopPlayer((Part) app.getLoopDialog().getLoopsArray()
                .get(app.getLoopDialog().getLoopComboBox().getSelectedIndex()));

        app.getLoopDialog().setVisible(!app.getLoopDialog().isVisible());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (visible) {
            app.getLoopButton().setCol(app.getActivePhrase().getSelectedColor());
            app.getLoopButton().repaint();
        }
        else {
            app.getLoopButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getLoopButton().repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!visible) {
            app.getLoopButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getLoopButton().repaint();
            app.getFrame().pack();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!visible) {
            app.getLoopButton().setCol(app.getButtonBackgroundColor());
            app.getLoopButton().repaint();
            app.getFrame().pack();
        }
    }
}
