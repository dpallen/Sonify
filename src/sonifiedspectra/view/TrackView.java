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

    private Sonify app;

    private JLabel nameLabel;

    private ArrayList<PhraseInTrackView> phraseInTrackViewArray;

    private Track track;

    private Color backColor;

    public TrackView(Track track, Sonify app) {
        this.track = track;
        this.app = app;
        initialize();
    }

    public void initialize() {
        setLayout(null);
        this.phraseInTrackViewArray = new ArrayList<PhraseInTrackView>();
        backColor = Color.decode("#F5F5F5");

        int i = 0;

        removeAll();

        for (Phrase phrase : track.getPhrases()) {
            PhraseInTrackView pitv = new PhraseInTrackView(app, phrase);
            if (!phrase.isLoop()) pitv.getNameLabel().setText(phrase.getCompound().getName());
            else pitv.getNameLabel().setText("Loop");
            pitv.setBounds((int) ((phrase.getStartTime() * 4) * app.getMeasureScale()), 0, pitv.getAdjustedWidth(), 70);
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int i = 0;

        for (MeasureHeadView mhv : app.getMeasureHeadViewArray()) {

            int height = 70;

            TrackHeadView thv = app.getTrackHeadViewArray().get(app.getActiveProject().getTracksArray().indexOf(track));

            if (thv.getTrack().isExpanded()) height = thv.getExpandedHeight();

            g.setColor(Color.decode("#C9C9C9"));

            g.fillRect(app.getMeasureScale() + (app.getMeasureScale() * 4 * i), 0, 1, height);
            g.fillRect(app.getMeasureScale() * 2 + (app.getMeasureScale() * 4 * i), 0, 1, height);
            g.fillRect(app.getMeasureScale() * 3 + (app.getMeasureScale() * 4 * i), 0, 1, height);

            g.setColor(Color.decode("#979797"));
            g.fillRect(app.getMeasureScale() * 4 + (app.getMeasureScale() * 4 * i), 0, 2, height);

            i++;

        }
    }

    public void updatePanel() {
        if (track.isSelected()) {
            setBackground(Color.decode("#B8B8B8"));
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, false));
        }
        else {
            //setBackground(backColor);
            setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
        }
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

    public Color getBackColor() {
        return backColor;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }
}
