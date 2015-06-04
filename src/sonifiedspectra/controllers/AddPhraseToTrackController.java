package sonifiedspectra.controllers;

import sonifiedspectra.model.HelpStrings;
import sonifiedspectra.model.Phrase;
import sonifiedspectra.model.Project;
import sonifiedspectra.model.Track;
import sonifiedspectra.view.PhraseInTrackView;
import sonifiedspectra.view.PhraseView;
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
public class AddPhraseToTrackController implements ActionListener, MouseListener {

    private SonifiedSpectra app;
    private Project project;

    public AddPhraseToTrackController(SonifiedSpectra app, Project project) {
        this.project = project;
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (TrackView tv : app.getTrackViewArray()) {
            if (tv.getTrack().getInstrument() == app.getActivePhrase().getInstrument()) {
                for (int j = 0; j < app.getSelectedMeasures().size(); j++) {
                    Phrase newPhrase = app.getActivePhrase().copy();
                    newPhrase.setId(app.getActiveProject().getCurrentPhraseId());
                    newPhrase.setStartTime(app.getSelectedMeasures().get(j));
                    app.getActiveProject().incrementPhraseId();
                    tv.getTrack().getPhrases().add(newPhrase);
                }
                tv.initialize();
                for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray()) {
                    pitv.addMouseListener(new PhraseInTrackController(app, app.getActiveProject(), pitv));
                    RemovePhraseFromTrackController removePhraseFromTrackController = new RemovePhraseFromTrackController(app, app.getActiveProject(), pitv, tv);
                    pitv.getRemoveButton().addMouseListener(new HelpTextController(app, HelpStrings.REMOVE_PHRASE_FROM_TRACK));
                    pitv.getRemoveButton().addActionListener(removePhraseFromTrackController);
                    pitv.getRemoveButton().addMouseListener(removePhraseFromTrackController);
                }
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
        app.getAddPhraseToTrackButton().setCol(app.getActivePhrase().getSelectedColor());
        app.getAddPhraseToTrackButton().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        app.getAddPhraseToTrackButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getAddPhraseToTrackButton().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        app.getAddPhraseToTrackButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getAddPhraseToTrackButton().repaint();
        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        app.getAddPhraseToTrackButton().setCol(app.getButtonBackgroundColor());
        app.getAddPhraseToTrackButton().repaint();
        app.getFrame().pack();
    }

}
