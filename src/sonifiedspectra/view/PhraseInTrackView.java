package sonifiedspectra.view;

import sonifiedspectra.controllers.RemovePhraseFromTrackController;
import sonifiedspectra.model.Phrase;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class PhraseInTrackView extends JPanel {

    private JLabel nameLabel;
    private Phrase phrase;
    private BetterButton removeButton;
    private boolean selected;

    public PhraseInTrackView(Phrase phrase) {
        this.phrase = phrase;
        selected = false;
        setLayout(null);
        setBackground(phrase.getUnselectedColor());
        setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));

        nameLabel = new JLabel(phrase.getCompound().getName());
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 10));
        nameLabel.setBounds(2, 0, 75, 15);
        add(nameLabel);

        Icon removephrasefromtrackicon = new ImageIcon("resources/icons/removephrasefromtrackicon.png");
        removeButton = new BetterButton(Color.decode("#F5F5F5"), 10, 10, 0);
        removeButton.setIcon(removephrasefromtrackicon);
        removeButton.setBounds(50, 2, 10, 10);
        removeButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        removeButton.setBorderPainted(true);
        removeButton.setFocusPainted(false);
        add(removeButton);

    }

    public void toggleSelected() {
        selected = !selected;
    }

    public void adjustSize(int j4) {
        setBounds((int) ((phrase.getStartTime() * 4) * 25), j4 * 15, getAdjustedWidth(), 15);
        removeButton.setBounds(getAdjustedWidth() - 15, removeButton.getY(), removeButton.getWidth(), removeButton.getHeight());
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

    public BetterButton getRemoveButton() {
        return removeButton;
    }

    public void setRemoveButton(BetterButton removeButton) {
        this.removeButton = removeButton;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
