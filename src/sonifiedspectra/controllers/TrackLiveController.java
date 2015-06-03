package sonifiedspectra.controllers;

import sonifiedspectra.model.Phrase;
import sonifiedspectra.model.Project;
import sonifiedspectra.view.SonifiedSpectra;
import sonifiedspectra.view.TrackHeadView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class TrackLiveController implements ActionListener {

    private SonifiedSpectra app;
    private Project project;
    private TrackHeadView thv;

    public TrackLiveController(SonifiedSpectra app, Project project, TrackHeadView thv) {
        this.app = app;
        this.project = project;
        this.thv = thv;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        thv.getTrack().setLive(thv.getLiveCheckbox().isSelected());

    }
}
