package sonifiedspectra.controllers;

import org.jfree.chart.ChartPanel;
import sonifiedspectra.model.Compound;
import sonifiedspectra.model.Phrase;
import sonifiedspectra.model.Project;
import sonifiedspectra.model.Track;
import sonifiedspectra.view.SonifiedSpectra;
import sonifiedspectra.view.TrackHeadView;
import sonifiedspectra.view.TrackView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class TrackInstrumentController implements ItemListener {

    private SonifiedSpectra app;
    private Project project;
    private TrackHeadView thv;

    public TrackInstrumentController(SonifiedSpectra app, Project project, TrackHeadView thv) {
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
