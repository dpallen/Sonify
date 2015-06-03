package sonifiedspectra.view;

import sonifiedspectra.model.Phrase;
import sonifiedspectra.model.Track;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class TrackView extends JPanel {

    private JLabel nameLabel;

    private ArrayList<PhraseInTrackView> phraseInTrackViewArray;

    private Track track;

    public TrackView(Track track) {
        this.track = track;
        setLayout(null);
        this.phraseInTrackViewArray = new ArrayList<PhraseInTrackView>();

        /*this.nameLabel = new JLabel("Track 1");
        nameLabel.setBounds(10, 10, 100, 20);
        add(nameLabel);*/

        int i = 0;

        for (Phrase phrase : track.getPhrases()) {
            PhraseInTrackView pitv = new PhraseInTrackView(phrase);
            pitv.getNameLabel().setText(phrase.getCompound().getName());
            pitv.setBounds((int) ((phrase.getStartTime() * 4) * 25), i * 15, pitv.getAdjustedWidth(), 15);
            phraseInTrackViewArray.add(pitv);
            add(pitv);
            i++;
        }

        setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public ArrayList<PhraseInTrackView> getPhraseInTrackViewArray() {
        return phraseInTrackViewArray;
    }

    public void setPhraseInTrackViewArray(ArrayList<PhraseInTrackView> phraseInTrackViewArray) {
        this.phraseInTrackViewArray = phraseInTrackViewArray;
    }
}
