package sonifiedspectra.controllers;

import sonifiedspectra.model.Model;
import sonifiedspectra.view.SonifiedSpectra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 5/31/15.
 */
public class SettingsController implements ActionListener, MouseListener {

    private SonifiedSpectra app;
    private Model model;
    private boolean visible;

    public SettingsController(SonifiedSpectra app, Model model) {
        this.model = model;
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

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        app.getSettingsButton().setCol(app.getButtonHighlightColor());
        app.getSettingsButton().repaint();
        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        app.getSettingsButton().setCol(app.getButtonBackgroundColor());
        app.getSettingsButton().repaint();
        app.getFrame().pack();
    }

}
