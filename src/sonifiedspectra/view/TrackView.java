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
        initialize();
    }

    public void initialize() {
        setLayout(null);
        this.phraseInTrackViewArray = new ArrayList<PhraseInTrackView>();

        int i = 0;

        removeAll();

        for (Phrase phrase : track.getPhrases()) {
            PhraseInTrackView pitv = new PhraseInTrackView(phrase);
            pitv.getNameLabel().setText(phrase.getCompound().getName());
            pitv.setBounds((int) ((phrase.getStartTime() * 4) * 25), i * 15, pitv.getAdjustedWidth(), 15);
            phraseInTrackViewArray.add(pitv);
            add(pitv);
            i++;
        }

        setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
        setPreferredSize(new Dimension(getX(), getY()));
    }

    public ArrayList<PhraseInTrackView> getSelectedPitvs() {
        ArrayList<PhraseInTrackView> selectedPitvs = new ArrayList<PhraseInTrackView>();

        for (PhraseInTrackView pitv : phraseInTrackViewArray) {
            if (pitv.isSelected()) selectedPitvs.add(pitv);
        }

        return selectedPitvs;
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
