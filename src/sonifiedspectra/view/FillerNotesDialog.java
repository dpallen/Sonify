package sonifiedspectra.view;

import sonifiedspectra.model.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FillerNotesDialog extends JDialog {

    private Sonify app;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField pitchTextField;
    private JComboBox rhythmComboBox;
    private JTextField dynamicTextField;
    private JLabel titleLabel;
    private JLabel selectedLabel;
    private JButton removeSelectedFillerNotesButton;

    public FillerNotesDialog(Sonify app) throws IOException, FontFormatException {
        this.app = app;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        String[] rhythmValueOptions = {"1", "1/2", "1/4", "1/8", "1/16"};
        for (String rhythmValueOption : rhythmValueOptions) {

            rhythmComboBox.addItem(rhythmValueOption);

        }

        setBackground(Color.decode("#F5F5F5"));
        contentPane.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));

        Font hnt20 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/HelveticaNeue-Thin.otf")).deriveFont(Font.PLAIN, 20);

        titleLabel.setFont(hnt20);
        pitchTextField.setText("60");
        rhythmComboBox.setSelectedItem("1/8");
        dynamicTextField.setText("100");

        int numFiller = 0;
        int numNonFiller = 0;

        for (Note n : app.getActivePhrase().getNotesArray()) {
            if (n.isSelected()) {
                if (n.isFiller()) numFiller++;
                else numNonFiller++;
            }
        }

        selectedLabel.setText("Selected: " + numFiller + " filler, " + numNonFiller + " non-filler");

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        removeSelectedFillerNotesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRemove();
            }
        });
    }

    private void onOK() {

        for (Note n : app.getActivePhrase().getSelectedNotes()) {
            Note fillerNote = new Note((app.getActivePhrase().getCurrentFillerId() * -1), null, true, app.getActivePhrase());
            app.getActivePhrase().incrementCurrentFillerId();
            fillerNote.setPitch(Integer.valueOf(pitchTextField.getText()));
            fillerNote.setRhythmValue(getQuantizeRhythmValue((String) rhythmComboBox.getSelectedItem()));
            fillerNote.setDynamic(Integer.valueOf(dynamicTextField.getText()));
            app.getActivePhrase().getNotesArray().add(app.getActivePhrase().getNotesArray().indexOf(n) + 1, fillerNote);
        }

        app.updateActivePhrase(app.getActivePhrase());
        app.updateIntervalMarker();
        app.getSoundPlayer().reset();
        app.getSoundPlayer().updateSoundPlayer();
        setVisible(false);
        app.getFrame().pack();
    }

    private void onRemove() {
        for (Note n : app.getActivePhrase().getSelectedNotes()) {
            if (n.isFiller()) app.getActivePhrase().getNotesArray().remove(n);
        }
        app.updateActivePhrase(app.getActivePhrase());
        app.updateIntervalMarker();
        app.getSoundPlayer().reset();
        app.getSoundPlayer().updateSoundPlayer();
        setVisible(false);
        app.getFrame().pack();
    }

    /**
     * Translates Rhythm Values
     *
     * @param value string representing rhythm value
     * @return double representing rhythm value
     */
    public double getQuantizeRhythmValue(String value) {

        if (value.equals("1")) return 4.0;
        if (value.equals("1/2")) return 2.0;
        if (value.equals("1/4")) return 1.0;
        if (value.equals("1/8")) return 0.5;
        else return 0.25;

    }

    private void onCancel() {
        setVisible(false);
    }

    public JLabel getSelectedLabel() {
        return selectedLabel;
    }

    public void setSelectedLabel(JLabel selectedLabel) {
        this.selectedLabel = selectedLabel;
    }
}
