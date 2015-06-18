package sonifiedspectra.controllers;

import sonifiedspectra.view.Sonify;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Hvandenberg on 6/1/15.
 */
public class HelpTextController extends MouseAdapter {

    private Sonify app;
    private String text;

    public HelpTextController(Sonify app, String text) {
        this.app = app;
        this.text = text;
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
        app.getHelpTextPane().setText(text);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        app.getHelpTextPane().setText("");
    }
}
