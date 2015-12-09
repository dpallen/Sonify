package sonifiedspectra.controllers;

import sonifiedspectra.model.Note;
import sonifiedspectra.view.NoteView;
import sonifiedspectra.view.Sonify;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Hvandenberg on 12/9/15.
 */
public class TransposeTextFieldController implements ActionListener {

    private Sonify app;

    public TransposeTextFieldController(Sonify app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app.getActivePhrase().transposeSelectedNotes(Integer.parseInt(app.getTransposeTextField().getText()));
        app.refreshSelectedNotes();
        if (app.getActivePhrase().isValidTransposeSelection()) {
            app.getTransposeTextField().setText(String.valueOf(app.getActivePhrase().getSelectedNotes().get(0).getTranspose()));
            if (app.getActivePhrase().getSelectedNotes().get(0).getTranspose() == 0) app.getTransposeTextField().setForeground(Color.BLACK);
            else app.getTransposeTextField().setForeground(Color.RED);
        }
        else app.getTransposeTextField().setText("-");
        app.getFrame().pack();
    }
}
