package sonifiedspectra.controllers;

import sonifiedspectra.model.Note;
import sonifiedspectra.model.Project;
import sonifiedspectra.view.SonifiedSpectra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Hvandenberg on 6/1/15.
 */
public class EditPhraseController implements ActionListener, MouseListener {

    private SonifiedSpectra app;
    private Project project;

    public EditPhraseController(SonifiedSpectra app, Project project) {
        this.app = app;
        this.project = project;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app.getActivePhrase().setMinPitch(Integer.valueOf(app.getMinTextField().getText()));
        app.getActivePhrase().setMaxPitch(Integer.valueOf(app.getMaxTextField().getText()));
        app.getActivePhrase().setInstrument(app.getInstrumentComboBox().getSelectedIndex());

        ArrayList<Note> selectedNotes = app.getActivePhrase().getSelectedNotes();
        app.getActivePhrase().initialize();
        app.getActivePhrase().setSelectedNotes(selectedNotes);
        app.updateActivePhrase(app.getActivePhrase());
        app.updateIntervalMarker();
        app.getFrame().pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        app.getEditPhraseButton().setCol(app.getActivePhrase().getSelectedColor());
        app.getEditPhraseButton().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        app.getEditPhraseButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getEditPhraseButton().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        app.getEditPhraseButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getEditPhraseButton().repaint();
        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        app.getEditPhraseButton().setCol(app.getButtonBackgroundColor());
        app.getEditPhraseButton().repaint();
        app.getFrame().pack();
    }
}
