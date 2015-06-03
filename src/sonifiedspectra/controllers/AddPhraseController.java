package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.SonifiedSpectra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 5/31/15.
 */
public class AddPhraseController implements ActionListener, MouseListener {

    private SonifiedSpectra app;
    private Project project;
    private boolean visible;

    public AddPhraseController(SonifiedSpectra app, Project project) {
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
        app.getAddPhraseButton().setCol(app.getActivePhrase().getSelectedColor());
        app.getAddPhraseButton().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        app.getAddPhraseButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getAddPhraseButton().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        app.getAddPhraseButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getAddPhraseButton().repaint();
        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        app.getAddPhraseButton().setCol(app.getButtonBackgroundColor());
        app.getAddPhraseButton().repaint();
        app.getFrame().pack();
    }
}
