package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.Sonify;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 5/30/15.
 */
public class EditCompoundController implements ActionListener, MouseListener {

    private Sonify app;
    private Project project;
    private boolean visible;

    public EditCompoundController(Sonify app, Project project) {
        this.project = project;
        this.app = app;
        this.visible = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        app.getEditCompoundDialog().getNameTextField().setText(app.getActivePhrase().getCompound().getName());
        app.getEditCompoundDialog().getTypeTextField().setText(app.getActivePhrase().getCompound().getSpectrumType());
        app.getEditCompoundDialog().getDataLabel().setText(app.getActivePhrase().getCompound().getDataFile().getName() + ".ss");

        app.getEditCompoundDialog().setVisible(!app.getEditCompoundDialog().isVisible());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (visible) {
            app.getEditCompoundButton().setCol(app.getActivePhrase().getSelectedColor());
            app.getEditCompoundButton().repaint();
        }
        else {
            app.getEditCompoundButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getEditCompoundButton().repaint();
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
            app.getEditCompoundButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getEditCompoundButton().repaint();
            app.getFrame().pack();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!visible) {
            app.getEditCompoundButton().setCol(app.getButtonBackgroundColor());
            app.getEditCompoundButton().repaint();
            app.getFrame().pack();
        }
    }
}
