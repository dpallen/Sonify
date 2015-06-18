package sonifiedspectra.controllers;

import org.jfree.chart.plot.XYPlot;
import sonifiedspectra.model.Project;
import sonifiedspectra.view.NoteView;
import sonifiedspectra.view.Sonify;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 5/31/15.
 */
public class NoteController implements MouseListener {

    private Project project;
    private Sonify app;
    private NoteView noteView;

    public NoteController(Project project, Sonify app, NoteView noteView) {
        this.project = project;
        this.app = app;
        this.noteView = noteView;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        noteView.getNote().toggleSelected();

        app.updateActivePhrase(noteView.getNote().getPhrase());

        if (!project.isNotesPanelMultipleSelection()) {
            for (NoteView nv : app.getNoteViewArray()) {
                if (nv.getNote().getId() != noteView.getNote().getId()) nv.getNote().setSelected(false);
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

        if (app.getActivePhrase().getSelectedNotes().size() == 1) app.getNoteVolumeSlider()
                .setValue(app.getActivePhrase().getSelectedNotes().get(0).getDynamic());

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
        if (!noteView.getNote().isSelected()) {
            noteView.setBackground(app.getActivePhrase().getUnselectedColor());
            noteView.repaint();
            if (app.getActivePhrase() != null && !noteView.getNote().isFiller()) {
                XYPlot plot = app.getActivePhrase().getCompound().getDataChart().getDataChart().getXYPlot();
                plot.addDomainMarker(app.addNoteMarker(noteView.getNote()));
            }
            app.getFrame().pack();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!noteView.getNote().isSelected()) {
            if (noteView.getNote().isFiller()) noteView.setBackground(Color.BLACK);
            else noteView.setBackground(app.getButtonBackgroundColor());
            noteView.repaint();

            if (app.getActivePhrase() != null && !noteView.getNote().isFiller()) {
                XYPlot plot = app.getActivePhrase().getCompound().getDataChart().getDataChart().getXYPlot();
                plot.removeDomainMarker(app.addNoteMarker(noteView.getNote()));
            }

            app.getFrame().pack();
        }
    }
}
