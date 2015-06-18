package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.Sonify;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/1/15.
 */
public class  EditPhraseController implements ActionListener, MouseListener {

    private Sonify app;
    private Project project;

    public EditPhraseController(Sonify app, Project project) {
        this.app = app;
        this.project = project;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        app.getEditPhraseDialog().getMinPitchTextField().setText(String.valueOf(app.getActivePhrase().getMinPitch()));
        app.getEditPhraseDialog().getMaxPitchTextField().setText(String.valueOf(app.getActivePhrase().getMaxPitch()));
        app.getEditPhraseDialog().getX1TextField().setText(String.valueOf(app.getActivePhrase().getX1()));
        app.getEditPhraseDialog().getX2TextField().setText(String.valueOf(app.getActivePhrase().getX2()));
        app.getEditPhraseDialog().getCompoundNameLabel().setText(app.getActivePhrase().getCompound().getName());

        app.getEditPhraseDialog().setVisible(!app.getEditPhraseDialog().isVisible());

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
