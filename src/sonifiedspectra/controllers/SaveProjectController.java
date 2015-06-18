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
public class SaveProjectController implements ActionListener, MouseListener {

    private Sonify app;
    private Project project;

    public SaveProjectController(Sonify app, Project project) {
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
