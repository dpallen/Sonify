package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.model.Track;
import sonifiedspectra.view.PhraseInTrackView;
import sonifiedspectra.view.SonifiedSpectra;
import sonifiedspectra.view.TrackHeadView;
import sonifiedspectra.view.TrackView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class RemoveTrackController implements ActionListener, MouseListener {

    private SonifiedSpectra app;
    private Project project;

    public RemoveTrackController(SonifiedSpectra app, Project project) {
        this.project = project;
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (Track t : app.getActiveProject().getSelectedTracks()) {
            indices.add(app.getActiveProject().getTracksArray().indexOf(t));
        }

        if (indices.size() > 0) {

            for (TrackHeadView thv : app.getSelectedTrackHeads()) {
                app.getTrackHeadViewArray().remove(thv);
                app.getTrackHeadPanel().remove(thv);
            }

            for (TrackView tv : app.getSelectedTrackViews()) {
                app.getTrackViewArray().remove(tv);
                app.getInTracksPanel().remove(tv);
            }

            for (Track t : app.getActiveProject().getSelectedTracks()) {
                app.getActiveProject().getTracksArray().remove(t);
            }

            int y1 = 0;

            for (TrackView tv : app.getTrackViewArray()) {
                tv.setBounds(tv.getX(), y1, tv.getWidth(), tv.getHeight());
                tv.repaint();
                y1 += 70;
            }

            int y2 = 0;
            int j1 = 0;

            for (TrackHeadView thv : app.getTrackHeadViewArray()) {
                thv.setBounds(thv.getX(), y2, thv.getWidth(), thv.getHeight());
                thv.getTrackNumberLabel().setText(String.valueOf(j1 + 1));
                if (j1 % 2 != 0 && j1 != 0) thv.setBackColor(Color.decode("#DDDDDD"));
                else thv.setBackColor(Color.decode("#F5F5F5"));
                thv.updatePanel();
                thv.repaint();
                y2 += 70;
                j1++;
            }

            int j2 = 0;

            for (TrackView tv1 : app.getTrackViewArray()) {
                if (j2 % 2 != 0 && j2 != 0) tv1.setBackColor(Color.decode("#DDDDDD"));
                else tv1.setBackColor(Color.decode("#F5F5F5"));
                tv1.updatePanel();
                j2++;
            }

            app.getInTracksPanel().repaint();
            app.getTrackHeadPanel().repaint();

            app.getTrackHeadPanel().setPreferredSize(new Dimension(app.getTrackHeadPanel().getWidth(),
                    70 * app.getActiveProject().getTracksArray().size()));
            app.getInTracksPanel().setPreferredSize(new Dimension(app.getInTracksPanel().getWidth(),
                    70 * app.getActiveProject().getTracksArray().size()));
            app.getFrame().pack();

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        app.getRemoveTrackButton().setCol(app.getActivePhrase().getSelectedColor());
        app.getRemoveTrackButton().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        app.getRemoveTrackButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getRemoveTrackButton().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        app.getRemoveTrackButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getRemoveTrackButton().repaint();
        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        app.getRemoveTrackButton().setCol(app.getButtonBackgroundColor());
        app.getRemoveTrackButton().repaint();
        app.getFrame().pack();
    }

}
