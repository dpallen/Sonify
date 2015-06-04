package sonifiedspectra.controllers;

import org.jfree.chart.plot.XYPlot;
import sonifiedspectra.model.Project;
import sonifiedspectra.view.NoteView;
import sonifiedspectra.view.SonifiedSpectra;
import sonifiedspectra.view.TrackHeadView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class TrackHeadController implements MouseListener {

    private SonifiedSpectra app;
    private Project project;
    private TrackHeadView trackHeadView;

    public TrackHeadController(SonifiedSpectra app, Project project, TrackHeadView trackHeadView) {
        this.app = app;
        this.project = project;
        this.trackHeadView = trackHeadView;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        trackHeadView.getTrack().toggleSelected();

        if (!project.isTracksPanelMultipleSelection()) {
            for (TrackHeadView thv : app.getTrackHeadViewArray()) {
                if (thv.getTrack().getId() != trackHeadView.getTrack().getId()) thv.getTrack().setSelected(false);
                thv.updatePanel();
            }
        }
        if (trackHeadView.getTrack().isSelected()) {
            trackHeadView.setBackground(Color.decode("#C9C9C9"));
            trackHeadView.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, false));
            trackHeadView.repaint();
        }
        else {
            trackHeadView.setBackground(app.getButtonBackgroundColor());
            trackHeadView.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
        }
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
        if (!trackHeadView.getTrack().isSelected()) {
            trackHeadView.setBackground(Color.decode("#C9C9C9"));
            trackHeadView.repaint();
            app.getFrame().pack();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!trackHeadView.getTrack().isSelected()) {
            trackHeadView.setBackground(app.getButtonBackgroundColor());
            trackHeadView.repaint();
            app.getFrame().pack();
        }
    }
}
