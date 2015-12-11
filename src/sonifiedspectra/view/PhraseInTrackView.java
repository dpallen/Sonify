package sonifiedspectra.view;

import sonifiedspectra.controllers.RemovePhraseFromTrackController;
import sonifiedspectra.model.Phrase;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class PhraseInTrackView extends JPanel {

    private Sonify app;

    private JLabel nameLabel;
    private Phrase phrase;
    private BetterButton removeButton;
    private boolean selected;
    private float alpha;

    public PhraseInTrackView(Sonify app, Phrase phrase) {
        this.app = app;
        this.phrase = phrase;
        selected = false;
        setLayout(null);
        Color unselectedColor = phrase.getUnselectedColor();
        setBackground(new Color(unselectedColor.getRed(), unselectedColor.getGreen(), unselectedColor.getBlue()));
        setAlpha(0.4f);
        setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));

        if (phrase.getCompound() != null) nameLabel = new JLabel(phrase.getCompound().getName());
        else nameLabel = new JLabel("Loop");
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 10));
        nameLabel.setBounds(18, 0, 75, 15);
        add(nameLabel);

        Icon removephrasefromtrackicon = new ImageIcon(getClass().getResource("/icons/removephrasefromtrackicon.png"));
        removeButton = new BetterButton(Color.decode("#F5F5F5"), 10, 10, 0);
        removeButton.setIcon(removephrasefromtrackicon);
        removeButton.setBounds(3, 2, 10, 10);
        removeButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        removeButton.setBorderPainted(true);
        removeButton.setFocusPainted(false);
        add(removeButton);

    }

    public void toggleSelected() {
        selected = !selected;
    }

    public void adjustSize(int j4) {
        setBounds((int) ((phrase.getStartTime() * 4) * app.getMeasureScale()), j4 * 70, getAdjustedWidth(), 70);
        //removeButton.setBounds(getAdjustedWidth() - 15, removeButton.getY(), removeButton.getWidth(), removeButton.getHeight());
    }

    public void print() {
        System.out.println("Pitv phrase id: " + phrase.getId());
        System.out.println("    Loop: " + phrase.isLoop());
        System.out.println("    x: " + getX());
        System.out.println("    y: " + getY());
        System.out.println("    width: " + getWidth());
        System.out.println("    height: " + getHeight());
        System.out.println("    name: " + nameLabel.getText());
        System.out.println("    adjusted width: " + getAdjustedWidth());
        System.out.println("    phrase beat length: " + phrase.getBeatLength2());
        System.out.println("    phrase num notes: " + phrase.getNotesArray().size());
    }

    public int getAdjustedWidth() {
        if (!phrase.isLoop()) return (int) (app.getMeasureScale() * phrase.getBeatLength2());
        else return (int) (app.getMeasureScale() * phrase.getBeatLength());
    }

    public void setAlpha(float value) {
        if (alpha != value) {
            alpha = Math.min(Math.max(0f, value), 1f);
            setOpaque(alpha == 1.0f);
            repaint();
        }
    }

    public float getAlpha() {
        return alpha;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.SrcOver.derive(getAlpha()));
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();

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
