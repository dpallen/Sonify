package sonifiedspectra.view;

import sonifiedspectra.model.Note;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hvandenberg on 5/30/15.
 */
public class NoteView extends JPanel {

    private JLabel nameLabel;
    private JLabel rhythmLabel;
    private JLabel dynamicLabel;

    private Note note;

    public NoteView(Note note) {
        this.note = note;
        nameLabel = new JLabel(note.convertPitchToString());
        this.add(nameLabel);
        rhythmLabel = new JLabel(String.valueOf(note.getRhythmValue()));
        this.add(rhythmLabel);
        dynamicLabel = new JLabel(String.valueOf(note.getDynamic()));
        this.add(dynamicLabel);
    }

    public void updatePanel() {

        nameLabel.setText(note.convertPitchToString());
        if (note.getTranspose() == 0) {
            nameLabel.setForeground(Color.BLACK);
        }
        else {
            nameLabel.setForeground(Color.RED);
        }
        rhythmLabel.setText(String.valueOf(note.getRhythmValue()));
        dynamicLabel.setText(String.valueOf(note.getDynamic()));

        if (note.isSelected()) {
            setBackground(note.getPhrase().getUnselectedColor());
            setBorder(BorderFactory.createLineBorder(note.getPhrase().getBorderColor(), 2, true));
        }
        else {
            setBackground(Color.decode("#F5F5F5"));
            setBorder(BorderFactory.createLineBorder(note.getPhrase().getBorderColor(), 1, true));
        }
        repaint();
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public void setName(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    public JLabel getRhythmLabel() {
        return rhythmLabel;
    }

    public void setRhythmLabel(JLabel rhythmLabel) {
        this.rhythmLabel = rhythmLabel;
    }

    public JLabel getDynamicLabel() {
        return dynamicLabel;
    }

    public void setDynamicLabel(JLabel dynamicLabel) {
        this.dynamicLabel = dynamicLabel;
    }
}
