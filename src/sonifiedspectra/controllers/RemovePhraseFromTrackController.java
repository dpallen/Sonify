package sonifiedspectra.controllers;

import sonifiedspectra.model.Phrase;
import sonifiedspectra.model.Project;
import sonifiedspectra.view.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/3/15.
 */
public class RemovePhraseFromTrackController implements ActionListener, MouseListener {

    private Sonify app;
    private Project project;
    private PhraseInTrackView pitv;
    private TrackView tv;

    public RemovePhraseFromTrackController(Sonify app, Project project, PhraseInTrackView pitv, TrackView tv) {
        this.project = project;
        this.app = app;
        this.pitv = pitv;
        this.tv = tv;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!tv.getTrack().isLoop()) {

            int index = tv.getTrack().getPhrases().indexOf(pitv.getPhrase());

            tv.getTrack().getPhrases().remove(index);
            tv.getPhraseInTrackViewArray().remove(index);
            tv.remove(index);

        }

        else {

            Phrase temp = pitv.getPhrase();

            int index = tv.getPhraseInTrackViewArray().indexOf(pitv) * 3;

            System.out.println("Removing phrase: " + index + ", Id: " + temp.getId());
            tv.getPhraseInTrackViewArray().remove(pitv);
            tv.remove(pitv);

            while (temp != null) {
                tv.getTrack().getPhrases().remove(index);
                temp = temp.getParentPhrase();
            }

        }

        app.getInTracksPanel().repaint();
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
        pitv.getRemoveButton().setCol(Color.BLACK);
        pitv.getRemoveButton().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pitv.getRemoveButton().setCol(Color.decode("#747474"));
        pitv.getRemoveButton().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        pitv.getRemoveButton().setCol(Color.decode("#747474"));
        pitv.getRemoveButton().repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        pitv.getRemoveButton().setCol(app.getButtonBackgroundColor());
        pitv.getRemoveButton().repaint();
    }

}
