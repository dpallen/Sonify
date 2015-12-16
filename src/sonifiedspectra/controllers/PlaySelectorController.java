package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.Sonify;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 5/31/15.
 */
public class PlaySelectorController implements ActionListener, MouseListener {

    private Sonify app;
    private Project project;
    private int type;

    public PlaySelectorController(Sonify app, Project project, int type) {
        this.project = project;
        this.app = app;
        this.type = type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (app.isProject() && type == 1) {
            app.setProject(false);
            app.getPlayProjectButton().setCol(app.getButtonBackgroundColor());
            app.getPlayPhraseButton().setCol(app.getActivePhrase().getUnselectedColor());
            //app.getPhrasesPanel().setBorder(BorderFactory.createLineBorder(app.getActivePhrase().getBorderColor(), 3, true));
            //app.getOutTracksPanel().setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        }
        else if (!app.isProject() && type == 0) {
            app.setProject(true);
            app.getPlayProjectButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getPlayPhraseButton().setCol(app.getButtonBackgroundColor());
            //app.getOutTracksPanel().setBorder(BorderFactory.createLineBorder(app.getActivePhrase().getBorderColor(), 3, true));
            //app.getPhrasesPanel().setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        }

        app.getPlayProjectButton().repaint();
        app.getPlayPhraseButton().repaint();
        app.getOutTracksPanel().repaint();
        app.getPhrasesPanel().repaint();
        app.getSoundPlayer().reset();
        app.getSoundPlayer().updateSoundPlayer();
        app.getFrame().pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (type == 0 && !app.isProject()) {
            app.getPlayProjectButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getPlayProjectButton().repaint();
        }
        else if (type == 1 && app.isProject()) {
            app.getPlayPhraseButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getPlayPhraseButton().repaint();
        }
        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (type == 0 && !app.isProject()) {
            app.getPlayProjectButton().setCol(app.getButtonBackgroundColor());
            app.getPlayProjectButton().repaint();
        }
        else if (type == 1 && app.isProject()) {
            app.getPlayPhraseButton().setCol(app.getButtonBackgroundColor());
            app.getPlayPhraseButton().repaint();
        }
        app.getFrame().pack();
    }

}
