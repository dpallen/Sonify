package sonifiedspectra.controllers;

import sonifiedspectra.view.MeasureHeadView;
import sonifiedspectra.view.Sonify;
import sonifiedspectra.view.TrackHeadView;
import sonifiedspectra.view.TrackView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class DeselectController implements MouseListener {

    private Sonify app;

    public DeselectController(Sonify app) {
        this.app = app;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        for (TrackHeadView thv : app.getTrackHeadViewArray()) {
                thv.getTrack().setSelected(false);
                app.getTrackViewArray().get(app.getTrackHeadViewArray().indexOf(thv)).setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
                app.getTrackViewArray().get(app.getTrackHeadViewArray().indexOf(thv)).setBackground(thv.getBackColor());
                thv.updatePanel();
            }

        for (MeasureHeadView mhv : app.getMeasureHeadViewArray()) {
            mhv.setSelected(false);
            app.getMeasureHeadViewArray().get(app.getMeasureHeadViewArray().indexOf(mhv)).setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
            mhv.updatePanel();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public Sonify getApp() {
        return app;
    }

    public void setApp(Sonify app) {
        this.app = app;
    }

}
