package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.SonifiedSpectra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class RemoveTrackController implements ActionListener, MouseListener {

    private SonifiedSpectra app;
    private Project project;
    private boolean visible;

    public RemoveTrackController(SonifiedSpectra app, Project project) {
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
        app.getRemoveTrackButton().setCol(app.getActivePhrase().getSelectedColor());
        app.getRemoveTrackButton().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        app.getRemoveTrackButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getRemoveTrackButton().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        app.getRemoveTrackButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getRemoveTrackButton().repaint();
        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        app.getRemoveTrackButton().setCol(app.getButtonBackgroundColor());
        app.getRemoveTrackButton().repaint();
        app.getFrame().pack();
    }

}
