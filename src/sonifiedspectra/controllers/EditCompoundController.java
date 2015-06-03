package sonifiedspectra.controllers;

import sonifiedspectra.model.Compound;
import sonifiedspectra.model.Project;
import sonifiedspectra.view.EditCompoundView;
import sonifiedspectra.view.SonifiedSpectra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 5/30/15.
 */
public class EditCompoundController implements ActionListener, MouseListener {

    private SonifiedSpectra app;
    private Project project;
    private boolean visible;

    public EditCompoundController(SonifiedSpectra app, Project project) {
        this.project = project;
        this.app = app;
        this.visible = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        visible = !visible;
        Compound compound = null;

        for (Compound c : project.getCompoundsArray()) {

            if (String.valueOf(app.getCompoundComboBox().getSelectedItem()).equals(c.getName())) compound = c;

        }

        EditCompoundView editCompoundView = new EditCompoundView(compound);
        if (visible) {
            app.setEditCompoundView(editCompoundView);
            app.getFrame().getContentPane().add(editCompoundView);
        }
        app.getEditCompoundView().repaint();
        app.getEditCompoundView().setVisible(visible);
        app.getFrame().pack();
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
