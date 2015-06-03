package sonifiedspectra.controllers;

import jm.constants.Scales;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.tools.Mod;
import jm.util.Write;
import sonifiedspectra.model.Note;
import sonifiedspectra.model.Project;
import sonifiedspectra.model.SoundPlayer;
import sonifiedspectra.model.Track;
import sonifiedspectra.view.SonifiedSpectra;

import javax.sound.midi.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by Hvandenberg on 5/31/15.
 */
public class SaveProjectController implements ActionListener, MouseListener {

    private SonifiedSpectra app;
    private Project project;

    public SaveProjectController(SonifiedSpectra app, Project project) {
        this.project = project;
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app.getActivePhrase().setKey((String) app.getKeyComboBox().getSelectedItem());
        app.getActivePhrase().setQuality((String) app.getQualityComboBox().getSelectedItem());
        app.getActivePhrase().setQRhythm((String) app.getQrhythmComboBox().getSelectedItem());
        app.getActiveProject().setName(app.getTitleTextField().getText());

        app.getSoundPlayer().updateSoundPlayer();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        app.getSaveButton().setCol(app.getActivePhrase().getSelectedColor());
        app.getSaveButton().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        app.getSaveButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getSaveButton().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        app.getSaveButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getSaveButton().repaint();
        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        app.getSaveButton().setCol(app.getButtonBackgroundColor());
        app.getSaveButton().repaint();
        app.getFrame().pack();
    }

}
