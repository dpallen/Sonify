package sonifiedspectra.controllers;

import sonifiedspectra.model.Phrase;
import sonifiedspectra.model.Project;
import sonifiedspectra.view.PhraseInTrackView;
import sonifiedspectra.view.Sonify;
import sonifiedspectra.view.TrackView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Hvandenberg on 5/31/15.
 */
public class TransposeButtonController implements ActionListener, MouseListener {

    private Sonify app;
    private Project project;
    private int type;

    public TransposeButtonController(Sonify app, Project project, int type) {
        this.project = project;
        this.app = app;
        this.type = type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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

            if (type == 0) p.transposeSelectedNotesUp();
            else p.transposeSelectedNotesDown();

        }
        if (app.getActivePhrase().isValidTransposeSelection()) {
            app.getTransposeTextField().setText(String.valueOf(app.getActivePhrase().getSelectedNotes().get(0).getTranspose()));
            if (app.getActivePhrase().getSelectedNotes().get(0).getTranspose() == 0) app.getTransposeTextField().setForeground(Color.BLACK);
            else app.getTransposeTextField().setForeground(Color.RED);
        }
        else app.getTransposeTextField().setText("-");
        app.refreshSelectedNotes();
        app.getFrame().pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (type == 0) {
            app.getTransposeUpButton().setCol(app.getActivePhrase().getSelectedColor());
            app.getTransposeUpButton().repaint();
        }
        else if (type == 1) {
            app.getTransposeDownButton().setCol(app.getActivePhrase().getSelectedColor());
            app.getTransposeDownButton().repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (type == 0) {
            app.getTransposeUpButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getTransposeUpButton().repaint();
        }
        else if (type == 1) {
            app.getTransposeDownButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getTransposeDownButton().repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

        if (type == 0) {
            app.getTransposeUpButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getTransposeUpButton().repaint();
        }
        else if (type == 1) {
            app.getTransposeDownButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getTransposeDownButton().repaint();
        }

        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {

        if (type == 0) {
            app.getTransposeUpButton().setCol(app.getButtonBackgroundColor());
            app.getTransposeUpButton().repaint();
        }
        else if (type == 1) {
            app.getTransposeDownButton().setCol(app.getButtonBackgroundColor());
            app.getTransposeDownButton().repaint();
        }

        app.getFrame().pack();
    }

}
