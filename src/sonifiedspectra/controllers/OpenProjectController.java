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
public class OpenProjectController implements ActionListener, MouseListener {

    private Sonify app;
    private Project project;
    private boolean visible;

    public OpenProjectController(Sonify app, Project project) {
        this.project = project;
        this.app = app;
        this.visible = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        visible = !visible;
        app.getFrame().pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        app.getOpenButton().setCol(app.getActivePhrase().getSelectedColor());
        app.getOpenButton().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        app.getOpenButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getOpenButton().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        app.getOpenButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getOpenButton().repaint();
        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        app.getOpenButton().setCol(app.getButtonBackgroundColor());
        app.getOpenButton().repaint();
        app.getFrame().pack();
    }

}
