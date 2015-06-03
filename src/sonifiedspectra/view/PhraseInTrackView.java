package sonifiedspectra.view;

import sonifiedspectra.model.Phrase;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class PhraseInTrackView extends JPanel {

    public JLabel nameLabel;
    public Phrase phrase;

    public PhraseInTrackView(Phrase phrase) {
        this.phrase = phrase;
        setLayout(null);
        setBackground(phrase.getUnselectedColor());
        setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));

        nameLabel = new JLabel(phrase.getCompound().getName());
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 10));
        nameLabel.setBounds(2, 0, 75, 15);
        add(nameLabel);

    }

    public void adjustSize(int j4) {
        setBounds((int) ((phrase.getStartTime() * 4) * 25), j4 * 15, getAdjustedWidth(), 15);
    }

    public int getAdjustedWidth() {
        return (int) (25 * phrase.getBeatLength2());
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    public Phrase getPhrase() {
        return phrase;
    }

    public void setPhrase(Phrase phrase) {
        this.phrase = phrase;
    }
}
