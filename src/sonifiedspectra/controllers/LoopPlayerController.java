package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.SonifiedSpectra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/4/15.
 */
public class LoopPlayerController implements ActionListener, MouseListener {

    private SonifiedSpectra app;
    private Project project;
    private int type;

    public LoopPlayerController(SonifiedSpectra app, Project project, int type) {
        this.app = app;
        this.project = project;
        this.type = type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (app.getLoopDialog().getLoopPlayer() != null) {
            if (type == 0) {
                if (app.getLoopDialog().getLoopPlayer().isPlaying()) app.getLoopDialog().getLoopPlayer().stop();
                else app.getLoopDialog().getLoopPlayer().play();
            }
            else app.getLoopDialog().getLoopPlayer().reset();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (type == 0) {
            app.getLoopDialog().getPlayButton().setCol(app.getActivePhrase().getSelectedColor());
            app.getLoopDialog().getPlayButton().repaint();
        }
        else if (type == 1) {
            app.getLoopDialog().getStopButton().setCol(app.getActivePhrase().getSelectedColor());
            app.getLoopDialog().getStopButton().repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (type == 0) {
            app.getLoopDialog().getPlayButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getLoopDialog().getPlayButton().repaint();
        }
        else if (type == 1) {
            app.getLoopDialog().getStopButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getLoopDialog().getStopButton().repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (type == 0) {
            app.getLoopDialog().getPlayButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getLoopDialog().getPlayButton().repaint();
        }
        else if (type == 1) {
            app.getLoopDialog().getStopButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getLoopDialog().getStopButton().repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (type == 0) {
            app.getLoopDialog().getPlayButton().setCol(app.getButtonBackgroundColor());
            app.getLoopDialog().getPlayButton().repaint();
        }
        else if (type == 1) {
            app.getLoopDialog().getStopButton().setCol(app.getButtonBackgroundColor());
            app.getLoopDialog().getStopButton().repaint();
        }
    }

}
