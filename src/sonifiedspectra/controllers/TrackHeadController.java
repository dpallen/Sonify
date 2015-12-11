package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.Sonify;
import sonifiedspectra.view.TrackHeadView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class TrackHeadController implements MouseListener {

    private Sonify app;
    private Project project;
    private TrackHeadView trackHeadView;

    public TrackHeadController(Sonify app, Project project, TrackHeadView trackHeadView) {
        this.app = app;
        this.project = project;
        this.trackHeadView = trackHeadView;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        trackHeadView.getTrack().toggleSelected();

        if (!project.isTracksPanelMultipleSelection()) {
            for (TrackHeadView thv : app.getTrackHeadViewArray()) {
                if (thv.getTrack().getId() != trackHeadView.getTrack().getId()) {
                    thv.getTrack().setSelected(false);
                    //app.getTrackViewArray().get(app.getTrackHeadViewArray().indexOf(thv)).setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
                    app.getTrackViewArray().get(app.getTrackHeadViewArray().indexOf(thv)).updatePanel();
                }
                thv.updatePanel();
            }
        }

        if (trackHeadView.getTrack().isSelected()) {
            trackHeadView.setBackground(app.getActivePhrase().getUnselectedColor());
            trackHeadView.setBorder(BorderFactory.createLineBorder(app.getActivePhrase().getBorderColor(), 2, false));
            app.getTrackViewArray().get(app.getTrackHeadViewArray().indexOf(trackHeadView)).setBorder(BorderFactory.createLineBorder(app.getActivePhrase().getBorderColor(), 2, false));
            //app.getTrackViewArray().get(app.getTrackHeadViewArray().indexOf(trackHeadView)).setBackground(app.getActivePhrase().getUnselectedColor());
            trackHeadView.repaint();
            app.getTrackViewArray().get(app.getTrackHeadViewArray().indexOf(trackHeadView)).repaint();
        }
        else {
            trackHeadView.setBackground(trackHeadView.getBackColor());
            app.getTrackViewArray().get(app.getTrackHeadViewArray().indexOf(trackHeadView)).setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
            trackHeadView.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
            //app.getTrackViewArray().get(app.getTrackHeadViewArray().indexOf(trackHeadView)).setBackground(trackHeadView.getBackColor());
            app.getTrackViewArray().get(app.getTrackHeadViewArray().indexOf(trackHeadView)).repaint();
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
            trackHeadView.setBackground(app.getActivePhrase().getUnselectedColor());
            trackHeadView.repaint();
            app.getFrame().pack();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!trackHeadView.getTrack().isSelected()) {
            trackHeadView.setBackground(trackHeadView.getBackColor());
            trackHeadView.repaint();
            app.getFrame().pack();
        }
    }
}
