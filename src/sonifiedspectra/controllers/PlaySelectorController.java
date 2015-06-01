package sonifiedspectra.controllers;

import sonifiedspectra.model.Model;
import sonifiedspectra.view.BetterButton;
import sonifiedspectra.view.SonifiedSpectra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 5/31/15.
 */
public class PlaySelectorController implements ActionListener, MouseListener {

    private SonifiedSpectra app;
    private Model model;
    private boolean visible;
    private BetterButton button;

    public PlaySelectorController(SonifiedSpectra app, Model model, BetterButton button) {
        this.model = model;
        this.app = app;
        this.visible = false;
        this.button = button;
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
        button.setCol(app.getButtonHighlightColor());
        button.repaint();
        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        button.setCol(app.getButtonBackgroundColor());
        button.repaint();
        app.getFrame().pack();
    }

}
