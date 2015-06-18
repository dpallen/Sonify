package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.Sonify;
import sonifiedspectra.view.TrackHeadView;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class TrackInstrumentController implements ItemListener {

    private Sonify app;
    private Project project;
    private TrackHeadView thv;

    public TrackInstrumentController(Sonify app, Project project, TrackHeadView thv) {
        this.app = app;
        this.project = project;
        this.thv = thv;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        int instrument = thv.getInstrumentComboBox().getSelectedIndex();
        thv.getTrack().setInstrument(instrument);

        app.getSoundPlayer().reset();
        app.getSoundPlayer().updateSoundPlayer();

    }

}
