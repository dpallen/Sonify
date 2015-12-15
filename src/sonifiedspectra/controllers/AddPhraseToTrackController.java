package sonifiedspectra.controllers;

import sonifiedspectra.model.HelpStrings;
import sonifiedspectra.model.Phrase;
import sonifiedspectra.model.Project;
import sonifiedspectra.model.Track;
import sonifiedspectra.view.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * Created by Hvandenberg on 6/3/15.
 */
public class AddPhraseToTrackController implements ActionListener, MouseListener {

    private Sonify app;
    private Project project;

    public AddPhraseToTrackController(Sonify app, Project project) {
        this.project = project;
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        boolean done = false;

        for (TrackView tv : app.getTrackViewArray()) {
            if (tv.getTrack().getInstrument() == app.getActivePhrase().getInstrument()) {

                done = true;

                if (app.getSelectedMeasures().size() > 0) {
                    for (int j = 0; j < app.getSelectedMeasures().size(); j++) {
                        int startTime = app.getSelectedMeasures().get(j)-1;
                        if (startTime < 0) startTime = 0;
                        Phrase newPhrase = app.getActivePhrase().copy();
                        //newPhrase.setId(app.getActiveProject().getCurrentPhraseId());
                        newPhrase.setStartTime(startTime);
                        //app.getActiveProject().incrementPhraseId();
                        tv.getTrack().getPhrases().add(newPhrase);
                    }
                }

                else {
                    Phrase newPhrase = app.getActivePhrase().copy();
                    //newPhrase.setId(app.getActiveProject().getCurrentPhraseId());
                    //app.getActiveProject().incrementPhraseId();
                    tv.getTrack().getPhrases().add(newPhrase);
                }

                tv.initialize();
                for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray()) {
                    pitv.getTopPanel().addMouseListener(new PhraseInTrackController(app, app.getActiveProject(), pitv));
                    pitv.getTopPanel().addMouseListener(new HelpTextController(app, HelpStrings.PITV));
                    RemovePhraseFromTrackController removePhraseFromTrackController = new RemovePhraseFromTrackController(app, app.getActiveProject(), pitv, tv);
                    pitv.getRemoveButton().addMouseListener(new HelpTextController(app, HelpStrings.REMOVE_PHRASE_FROM_TRACK));
                    pitv.getRemoveButton().addActionListener(removePhraseFromTrackController);
                    pitv.getRemoveButton().addMouseListener(removePhraseFromTrackController);
                }
                tv.repaint();
            }
        }

        if (!done) {
            Track newTrack = new Track(app.getActiveProject().getCurrentTrackId());
            newTrack.setInstrument(app.getActivePhrase().getInstrument());
            app.getActiveProject().incrementTrackId();
            app.getActiveProject().getTracksArray().add(newTrack);

            Phrase newPhrase = app.getActivePhrase().copy();
            newTrack.getPhrases().add(newPhrase);

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

            app.getTrackHeadPanel().setPreferredSize(new Dimension(app.getTrackHeadPanel().getWidth(),
                    70 * app.getActiveProject().getTracksArray().size()));
            app.getInTracksPanel().setPreferredSize(new Dimension(app.getInTracksPanel().getWidth(),
                    70 * app.getActiveProject().getTracksArray().size()));

            tv.initialize();
            for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray()) {
                pitv.adjustSize(tv.getTrack().isExpanded());
                pitv.getTopPanel().addMouseListener(new PhraseInTrackController(app, app.getActiveProject(), pitv));
                pitv.getTopPanel().addMouseListener(new HelpTextController(app, HelpStrings.PITV));
                RemovePhraseFromTrackController removePhraseFromTrackController = new RemovePhraseFromTrackController(app, app.getActiveProject(), pitv, tv);
                pitv.getRemoveButton().addMouseListener(new HelpTextController(app, HelpStrings.REMOVE_PHRASE_FROM_TRACK));
                pitv.getRemoveButton().addActionListener(removePhraseFromTrackController);
                pitv.getRemoveButton().addMouseListener(removePhraseFromTrackController);
            }
            tv.repaint();
        }

        int j1 = 0;

        for (TrackHeadView thv1 : app.getTrackHeadViewArray()) {
            if (j1 % 2 != 0 && j1 != 0) thv1.setBackColor(Color.decode("#DDDDDD"));
            else thv1.setBackColor(Color.decode("#F5F5F5"));
            thv1.updatePanel();
            j1++;
        }

        int j2 = 0;

        for (TrackView tv1 : app.getTrackViewArray()) {
            if (j2 % 2 != 0 && j2 != 0) tv1.setBackColor(Color.decode("#DDDDDD"));
            else tv1.setBackColor(Color.decode("#F5F5F5"));
            tv1.updatePanel();
            j2++;
        }

        app.updateActivePhrase(app.getActivePhrase());
        app.updateIntervalMarker();
        app.getSoundPlayer().reset();
        app.getSoundPlayer().updateSoundPlayer();
        app.getFrame().pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        app.getAddPhraseToTrackButton().setCol(app.getActivePhrase().getSelectedColor());
        app.getAddPhraseToTrackButton().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        app.getAddPhraseToTrackButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getAddPhraseToTrackButton().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        app.getAddPhraseToTrackButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getAddPhraseToTrackButton().repaint();
        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        app.getAddPhraseToTrackButton().setCol(app.getButtonBackgroundColor());
        app.getAddPhraseToTrackButton().repaint();
        app.getFrame().pack();
    }

}
