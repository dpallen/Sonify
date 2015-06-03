package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.BetterButton;
import sonifiedspectra.view.SonifiedSpectra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/1/15.
 */
public class SoundPlayerController implements ActionListener, MouseListener {

    private SonifiedSpectra app;
    private Project project;
    private int type;

    public SoundPlayerController(SonifiedSpectra app, Project project, int type) {
        this.app = app;
        this.project = project;
        this.type = type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (app.getSoundPlayer() != null) {
            if (type == 0) {
                if (app.getSoundPlayer().isPlaying()) app.getSoundPlayer().stop();
                else app.getSoundPlayer().play();
            }
            else app.getSoundPlayer().reset();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (type == 0) {
            app.getPlayButton().setCol(app.getActivePhrase().getSelectedColor());
            app.getPlayButton().repaint();
        }
        else if (type == 1) {
            app.getStopButton().setCol(app.getActivePhrase().getSelectedColor());
            app.getStopButton().repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (type == 0) {
            app.getPlayButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getPlayButton().repaint();
        }
        else if (type == 1) {
            app.getStopButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getStopButton().repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (type == 0) {
            app.getPlayButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getPlayButton().repaint();
        }
        else if (type == 1) {
            app.getStopButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getStopButton().repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (type == 0) {
            app.getPlayButton().setCol(app.getButtonBackgroundColor());
            app.getPlayButton().repaint();
        }
        else if (type == 1) {
            app.getStopButton().setCol(app.getButtonBackgroundColor());
            app.getStopButton().repaint();
        }
    }
}
