package sonifiedspectra.controllers;

import sonifiedspectra.model.Note;
import sonifiedspectra.model.Phrase;
import sonifiedspectra.model.Project;
import sonifiedspectra.view.PhraseInTrackView;
import sonifiedspectra.view.Sonify;
import sonifiedspectra.view.TrackView;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

/**
 * Created by Hvandenberg on 6/4/15.
 */
public class NoteVolumeController implements ChangeListener {

    private Sonify app;
    private Project project;

    public NoteVolumeController(Sonify app, Project project) {
        this.app = app;
        this.project = project;
    }

    @Override
    public void stateChanged(ChangeEvent e) {

        ArrayList<Phrase> toUpdate = new ArrayList<Phrase>();
        toUpdate.add(app.getActivePhrase());

        for (TrackView tv : app.getTrackViewArray()) {
            for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray()) {
                if (pitv.getPhrase().getParentPhrase() != null) {
                    if (pitv.getPhrase().getParentPhrase().getId() == app.getActivePhrase().getId())
                        toUpdate.add(pitv.getPhrase());
                }
            }
        }

        for (Phrase p : toUpdate) {
            for (Note note : app.getActivePhrase().getSelectedNotes()) {
                p.getNotesArray().get(app.getActivePhrase().getNotesArray().indexOf(note))
                        .setDynamic(app.getNoteVolumeSlider().getValue() );
            }
        }

        app.getNoteVolumeLabel().setText(String.valueOf(app.getNoteVolumeSlider().getValue()));

        app.refreshSelectedNotes();
        app.getSoundPlayer().updateSoundPlayer();
        app.getFrame().pack();

    }
}
