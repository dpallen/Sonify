package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.BetterButton;
import sonifiedspectra.view.PhraseInTrackView;
import sonifiedspectra.view.SonifiedSpectra;
import sonifiedspectra.view.TrackView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/3/15.
 */
public class RemovePhraseFromTrackController implements ActionListener, MouseListener {

    private SonifiedSpectra app;
    private Project project;
    private BetterButton button;

    public RemovePhraseFromTrackController(SonifiedSpectra app, Project project, BetterButton button) {
        this.project = project;
        this.app = app;
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (TrackView tv : app.getTrackViewArray()) {
            if (tv.getTrack().getInstrument() == app.getActivePhrase().getInstrument()) {
                tv.getTrack().getPhrases().add(app.getActivePhrase());
                tv.initialize();
                for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray())
                    pitv.addMouseListener(new PhraseInTrackController(app, app.getActiveProject(), pitv));
                tv.repaint();
            }
        }

        app.updateActivePhrase(app.getActivePhrase());
        app.updateIntervalMarker();
        app.getSoundPlayer().reset();
        app.getSoundPlayer().updateSoundPlayer();
        app.getFrame().pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        button.setCol(Color.BLACK);
        button.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        button.setCol(Color.decode("#747474"));
        button.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        button.setCol(Color.decode("#747474"));
        button.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        button.setCol(app.getButtonBackgroundColor());
        button.repaint();
    }

}
