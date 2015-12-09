package sonifiedspectra.controllers;

import sonifiedspectra.model.HelpStrings;
import sonifiedspectra.model.Project;
import sonifiedspectra.model.Track;
import sonifiedspectra.view.Sonify;
import sonifiedspectra.view.TrackHeadView;
import sonifiedspectra.view.TrackView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class AddTrackController implements ActionListener, MouseListener {

    private Sonify app;
    private Project project;

    public AddTrackController(Sonify app, Project project) {
        this.project = project;
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Track newTrack = new Track(app.getActiveProject().getCurrentTrackId());
        app.getActiveProject().incrementTrackId();
        app.getActiveProject().getTracksArray().add(newTrack);

        TrackView tv = new TrackView(newTrack, app);
        tv.setBounds(0, 70 * (app.getActiveProject().getTracksArray().size() - 1), 100 * app.getActiveProject().getNumMeasures(), 70);

        app.getTrackViewArray().add(tv);
        app.getInTracksPanel().add(tv);
        tv.repaint();

        TrackHeadView thv = null;
        try {
            thv = new TrackHeadView(newTrack, app.getInstruments());
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (FontFormatException e1) {
            e1.printStackTrace();
        }
        thv.setBounds(0, 70 * (app.getActiveProject().getTracksArray().size() - 1), 150, 70);
        thv.getTrackNumberLabel().setText(String.valueOf(app.getActiveProject().getTracksArray().size()));
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

        int j1 = 0;

        for (TrackHeadView thv1 : app.getTrackHeadViewArray()) {
            if (j1 % 2 != 0 && j1 != 0) thv1.setBackColor(Color.decode("#DDDDDD"));
            else thv1.setBackColor(Color.decode("#F5F5F5"));
            if (!thv1.getTrack().isSelected()) thv1.updatePanel();
            j1++;
        }

        int j2 = 0;

        for (TrackView tv1 : app.getTrackViewArray()) {
            if (j2 % 2 != 0 && j2 != 0) tv1.setBackColor(Color.decode("#DDDDDD"));
            else tv1.setBackColor(Color.decode("#F5F5F5"));
            if (!tv1.getTrack().isSelected()) tv1.updatePanel();
            j2++;
        }

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
