package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.Sonify;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 5/31/15.
 */
public class NewProjectController implements ActionListener, MouseListener {

    private Sonify app;
    private Project project;
    private boolean visible;

    public NewProjectController(Sonify app, Project project) {
        this.project = project;
        this.app = app;
        this.visible = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app.getNewProjectDialog().setVisible(!app.getNewProjectDialog().isVisible());
        app.getFrame().pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        app.getNewButton().setCol(app.getActivePhrase().getSelectedColor());
        app.getNewButton().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        app.getNewButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getNewButton().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        app.getNewButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getNewButton().repaint();
        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        app.getNewButton().setCol(app.getButtonBackgroundColor());
        app.getNewButton().repaint();
        app.getFrame().pack();
    }

}
