package sonifiedspectra.controllers;

import sonifiedspectra.model.Model;
import sonifiedspectra.view.BetterButton;
import sonifiedspectra.view.SonifiedSpectra;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 5/31/15.
 */
public class TransposeButtonController implements ActionListener, MouseListener {

    private SonifiedSpectra app;
    private Model model;
    private int type;

    public TransposeButtonController(SonifiedSpectra app, Model model, int type) {
        this.model = model;
        this.app = app;
        this.type = type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (type == 0) app.getActivePhrase().transposeSelectedNotesUp();
        else app.getActivePhrase().transposeSelectedNotesDown();
        if (app.getActivePhrase().isValidTransposeSelection()) {
            app.getTransposeTextField().setText(String.valueOf(app.getActivePhrase().getSelectedNotes().get(0).getTranspose()));
            if (app.getActivePhrase().getSelectedNotes().get(0).getTranspose() == 0) app.getTransposeTextField().setForeground(Color.BLACK);
            else app.getTransposeTextField().setForeground(Color.RED);
        }
        else app.getTransposeTextField().setText("-");
        app.refreshSelectedNotes();
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

        if (type == 0) {
            app.getTransposeUpButton().setCol(app.getButtonHighlightColor());
            app.getTransposeUpButton().repaint();
        }
        else if (type == 1) {
            app.getTransposeDownButton().setCol(app.getButtonHighlightColor());
            app.getTransposeDownButton().repaint();
        }

        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {

        if (type == 0) {
            app.getTransposeUpButton().setCol(app.getButtonBackgroundColor());
            app.getTransposeUpButton().repaint();
        }
        else if (type == 1) {
            app.getTransposeDownButton().setCol(app.getButtonBackgroundColor());
            app.getTransposeDownButton().repaint();
        }

        app.getFrame().pack();
    }

}
