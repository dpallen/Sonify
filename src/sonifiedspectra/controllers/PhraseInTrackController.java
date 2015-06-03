package sonifiedspectra.controllers;

import org.jfree.chart.plot.XYPlot;
import sonifiedspectra.model.Project;
import sonifiedspectra.view.NoteView;
import sonifiedspectra.view.PhraseInTrackView;
import sonifiedspectra.view.SonifiedSpectra;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class PhraseInTrackController implements MouseListener {

    private SonifiedSpectra app;
    private Project project;
    private PhraseInTrackView pitv;

    public PhraseInTrackController(SonifiedSpectra app, Project project, PhraseInTrackView pitv) {
        this.app = app;
        this.project = project;
        this.pitv = pitv;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        pitv.getPhrase().toggleSelected();

        app.updateActivePhrase(pitv.getPhrase());

        if (!project.isNotesPanelMultipleSelection()) {
            for (NoteView nv : app.getNoteViewArray()) {
                if (nv.getNote().getId() != pitv.getPhrase().getId()) nv.getNote().setSelected(false);
                nv.updatePanel();
            }
        }

        if (app.getActivePhrase().isValidTransposeSelection()) {
            app.getTransposeTextField().setText(String.valueOf(app.getActivePhrase().getSelectedNotes().get(0).getTranspose()));
            if (app.getActivePhrase().getSelectedNotes().get(0).getTranspose() == 0)
                app.getTransposeTextField().setForeground(Color.BLACK);
            else app.getTransposeTextField().setForeground(Color.RED);
        } else {
            app.getTransposeTextField().setText("-");
            app.getTransposeTextField().setForeground(Color.BLACK);
        }

        app.updateIntervalMarker();
        app.getGraphPanel().repaint();
        app.getFrame().pack();

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        pitv.setBackground(pitv.getPhrase().getSelectedColor());
        pitv.repaint();
        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        pitv.setBackground(pitv.getPhrase().getUnselectedColor());
        pitv.repaint();
        app.getFrame().pack();
    }
}
