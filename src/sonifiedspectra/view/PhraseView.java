package sonifiedspectra.view;

import sonifiedspectra.model.Phrase;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Created by Hvandenberg on 5/30/15.
 */
public class PhraseView extends JPanel {

    private JLabel nameLabel;
    private JLabel x1Label;
    private JLabel x2Label;

    private Phrase phrase;

    public PhraseView(Phrase phrase) {
        this.phrase = phrase;
        nameLabel = new JLabel(phrase.getCompound().getName());
        this.add(nameLabel);
        DecimalFormat formatter = new DecimalFormat("#0000.00");
        x1Label = new JLabel("x1: " + String.valueOf(formatter.format(phrase.getX2())));
        this.add(x1Label);
        x2Label = new JLabel("x2: " + String.valueOf(formatter.format(phrase.getX1())));
        this.add(x2Label);
        setBorder(BorderFactory.createLineBorder(phrase.getBorderColor(), 3, true));
    }

    public void updatePanel() {
        if (phrase.isSelected()) {
            setBackground(phrase.getSelectedColor());
            DecimalFormat formatter = new DecimalFormat("#0000.00");
            x1Label.setText("x1: " + String.valueOf(formatter.format(phrase.getX2())));
            x2Label.setText("x2: " + String.valueOf(formatter.format(phrase.getX1())));
        }
        else setBackground(phrase.getUnselectedColor());
        setBorder(BorderFactory.createLineBorder(phrase.getBorderColor(), 3, true));
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public void setName(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    public Phrase getPhrase() {
        return phrase;
    }

    public void setPhrase(Phrase phrase) {
        this.phrase = phrase;
    }

}
