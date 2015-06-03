package sonifiedspectra.view;

import sonifiedspectra.model.Note;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
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
        rhythmLabel.setText(String.valueOf(note.getRhythmValue()));
        dynamicLabel.setText(String.valueOf(note.getDynamic()));

        if (note.isSelected()) {
            setBackground(note.getPhrase().getUnselectedColor());
            if (note.isFiller()) {
                /*Border out = BorderFactory.createLineBorder(note.getPhrase().getBorderColor(), 2, true);
                Border in = new EmptyBorder(10,10,10,10);
                setBorder(new CompoundBorder(out, in));*/
                setBorder(BorderFactory.createLineBorder(note.getPhrase().getBorderColor(), 3, true));
                setBackground(Color.BLACK);
            }
            else setBorder(BorderFactory.createLineBorder(note.getPhrase().getBorderColor(), 2, true));
        }
        else {
            if (!note.isFiller()) setBackground(Color.decode("#F5F5F5"));
            else setBackground(Color.BLACK);
            setBorder(BorderFactory.createLineBorder(note.getPhrase().getBorderColor(), 1, true));
        }

        if (note.isFiller()) {
            rhythmLabel.setForeground(Color.WHITE);
            dynamicLabel.setForeground(Color.WHITE);
        }

        if (note.getTranspose() == 0) {
            if (note.isFiller()) nameLabel.setForeground(Color.WHITE);
            else nameLabel.setForeground(Color.BLACK);
        }
        else {
            nameLabel.setForeground(Color.RED);
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
