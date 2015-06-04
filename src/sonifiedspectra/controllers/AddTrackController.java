package sonifiedspectra.controllers;

import sonifiedspectra.model.HelpStrings;
import sonifiedspectra.model.Project;
import sonifiedspectra.model.Track;
import sonifiedspectra.view.SonifiedSpectra;
import sonifiedspectra.view.TrackHeadView;
import sonifiedspectra.view.TrackView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class AddTrackController implements ActionListener, MouseListener {

    private SonifiedSpectra app;
    private Project project;

    public AddTrackController(SonifiedSpectra app, Project project) {
        this.project = project;
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Track newTrack = new Track(app.getActiveProject().getCurrentTrackId());
        app.getActiveProject().incrementTrackId();
        app.getActiveProject().getTracksArray().add(newTrack);

        TrackView tv = new TrackView(newTrack);
        tv.setBounds(0, 70 * (app.getActiveProject().getTracksArray().size() - 1), 100 * app.getActiveProject().getNumMeasures(), 70);

        app.getTrackViewArray().add(tv);
        app.getInTracksPanel().add(tv);
        tv.repaint();

        TrackHeadView thv = new TrackHeadView(newTrack);
        thv.setBounds(0, 70 * (app.getActiveProject().getTracksArray().size() - 1), 150, 70);
        thv.addMouseListener(new HelpTextController(app, HelpStrings.TRACK_HEAD));
        thv.addMouseListener(new TrackHeadController(app, app.getActiveProject(), thv));
        thv.getInstrumentComboBox().addItemListener(new TrackInstrumentController(app, app.getActiveProject(), thv));
        thv.getLiveCheckbox().addActionListener(new TrackLiveController(app, app.getActiveProject(), thv));
        ExpandTrackController expandController = new ExpandTrackController(app, app.getActiveProject(), thv);
        thv.getExpandButton().addActionListener(expandController);
        thv.getExpandButton().addMouseListener(expandController);
        app.getTrackHeadViewArray().add(thv);
        app.getTrackHeadPanel().add(thv);
        thv.repaint();

        app.getTrackHeadPanel().setPreferredSize(new Dimension(app.getTrackHeadPanel().getWidth(),
                70 * app.getActiveProject().getTracksArray().size()));
        app.getInTracksPanel().setPreferredSize(new Dimension(app.getInTracksPanel().getWidth(),
                70 * app.getActiveProject().getTracksArray().size()));

        app.getSoundPlayer().reset();
        app.getSoundPlayer().updateSoundPlayer();

        app.getFrame().pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        app.getAddTrackButton().setCol(app.getActivePhrase().getSelectedColor());
        app.getAddTrackButton().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        app.getAddTrackButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getAddTrackButton().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        app.getAddTrackButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getAddTrackButton().repaint();
        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        app.getAddTrackButton().setCol(app.getButtonBackgroundColor());
        app.getAddTrackButton().repaint();
        app.getFrame().pack();
    }

}
