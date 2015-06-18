package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.Sonify;
import sonifiedspectra.view.TrackHeadView;
import sonifiedspectra.view.TrackView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class ExpandTrackController implements ActionListener, MouseListener {

    private Sonify app;
    private Project project;
    private TrackHeadView thv;
    private Icon collapsedIcon;
    private Icon expandedIcon;

    public ExpandTrackController(Sonify app, Project project, TrackHeadView thv) {
        this.project = project;
        this.app = app;
        this.thv = thv;

        collapsedIcon = new ImageIcon("resources/icons/collapsedicon.png");
        expandedIcon = new ImageIcon("resources/icons/expandedicon.png");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        thv.getTrack().toggleExpanded();

        if (thv.getTrack().isExpanded()) {
            System.out.println("THV Id: " + thv.getTrack().getId());
            System.out.println("Tv array size: " + app.getTrackViewArray().size());
            thv.getExpandButton().setIcon(expandedIcon);
            thv.setBounds(thv.getX(), thv.getY(), thv.getWidth(), thv.getExpandedHeight());
            TrackView tv = app.getTrackViewArray().get(thv.getTrack().getId());
            tv.setBounds(tv.getX(), tv.getY(), tv.getWidth(), thv.getExpandedHeight());
            thv.repaint();
            tv.repaint();

            for (int i = thv.getTrack().getId() + 1; i < app.getActiveProject().getTracksArray().size(); i++) {
                TrackHeadView temp = app.getTrackHeadViewArray().get(i);
                TrackView trackView = app.getTrackViewArray().get(i);
                trackView.setBounds(trackView.getX(), trackView.getY() + thv.getExpandedHeight() - 70, trackView.getWidth(), trackView.getHeight());
                temp.setBounds(temp.getX(), temp.getY() + thv.getExpandedHeight() - 70, temp.getWidth(), temp.getHeight());
                trackView.repaint();
                temp.repaint();
            }

            app.getTrackHeadPanel().setPreferredSize(new Dimension(app.getTrackHeadPanel().getWidth(),
                    app.getTrackHeadPanel().getHeight() + thv.getExpandedHeight() - 70));
            app.getInTracksPanel().setPreferredSize(new Dimension(app.getInTracksPanel().getWidth(),
                    app.getInTracksPanel().getHeight() + thv.getExpandedHeight() - 70));

        }
        else {
            thv.getExpandButton().setIcon(collapsedIcon);
            thv.setBounds(thv.getX(), thv.getY(), thv.getWidth(), 70);
            TrackView tv = app.getTrackViewArray().get(thv.getTrack().getId());
            tv.setBounds(tv.getX(), tv.getY(), tv.getWidth(), 70);
            thv.repaint();
            tv.repaint();

            for (int i = thv.getTrack().getId() + 1; i < app.getTrackViewArray().size(); i++) {
                TrackHeadView temp = app.getTrackHeadViewArray().get(i);
                TrackView trackView = app.getTrackViewArray().get(i);
                trackView.setBounds(trackView.getX(), trackView.getY() - (thv.getExpandedHeight() - 70), trackView.getWidth(), trackView.getHeight());
                temp.setBounds(temp.getX(), temp.getY() - (thv.getExpandedHeight() - 70), temp.getWidth(), temp.getHeight());
                temp.repaint();
                trackView.repaint();
            }

            app.getTrackHeadPanel().setPreferredSize(new Dimension(app.getTrackHeadPanel().getWidth(),
                    app.getTrackHeadPanel().getHeight() - (thv.getExpandedHeight() - 70)));
            app.getInTracksPanel().setPreferredSize(new Dimension(app.getInTracksPanel().getWidth(),
                    app.getInTracksPanel().getHeight() - (thv.getExpandedHeight() - 70)));

        }

        app.getInTracksPanel().repaint();
        app.getFrame().pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        thv.getExpandButton().setCol(app.getActivePhrase().getSelectedColor());
        thv.getExpandButton().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        thv.getExpandButton().setCol(app.getActivePhrase().getUnselectedColor());
        thv.getExpandButton().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        thv.getExpandButton().setCol(app.getActivePhrase().getUnselectedColor());
        thv.getExpandButton().repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        thv.getExpandButton().setCol(app.getButtonBackgroundColor());
        thv.getExpandButton().repaint();
    }

}
