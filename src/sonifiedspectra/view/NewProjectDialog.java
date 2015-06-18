package sonifiedspectra.view;

import sonifiedspectra.model.Project;
import sonifiedspectra.model.SoundPlayer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class NewProjectDialog extends JDialog {

    private Sonify app;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public NewProjectDialog(Sonify app) {
        this.app = app;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (MidiUnavailableException e1) {
                    e1.printStackTrace();
                } catch (LineUnavailableException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (FontFormatException e1) {
                    e1.printStackTrace();
                } catch (InvalidMidiDataException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedAudioFileException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() throws MidiUnavailableException, LineUnavailableException, IOException, FontFormatException, InvalidMidiDataException, UnsupportedAudioFileException {

        app.getFrame().dispose();
        app = new Sonify();
        app.getFrame().setVisible(true);

        setVisible(false);
    }

    private void onCancel() {

        setVisible(false);
    }
}
