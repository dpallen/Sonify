package sonifiedspectra.controllers;

import sonifiedspectra.view.SonifiedSpectra;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/1/15.
 */
public class HelpTextController extends MouseAdapter {

    private SonifiedSpectra app;
    private String text;

    public HelpTextController(SonifiedSpectra app, String text) {
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
