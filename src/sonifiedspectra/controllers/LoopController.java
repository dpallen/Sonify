package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.BetterButton;
import sonifiedspectra.view.SonifiedSpectra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class LoopController implements ActionListener, MouseListener {

    private SonifiedSpectra app;
    private Project project;
    private boolean visible;

    public LoopController(SonifiedSpectra app, Project project) {
        this.app = app;
        this.project = project;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        visible = !visible;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (visible) {
            app.getLoopButton().setCol(app.getActivePhrase().getSelectedColor());
            app.getLoopButton().repaint();
        }
        else {
            app.getLoopButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getLoopButton().repaint();
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
            app.getLoopButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getLoopButton().repaint();
            app.getFrame().pack();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!visible) {
            app.getLoopButton().setCol(app.getButtonBackgroundColor());
            app.getLoopButton().repaint();
            app.getFrame().pack();
        }
    }
}
