package sonifiedspectra.view;

import org.apache.commons.io.FileUtils;
import sonifiedspectra.model.Phrase;
import sonifiedspectra.model.Project;
import sonifiedspectra.model.SoundPlayer;
import sonifiedspectra.model.Track;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URISyntaxException;

public class NewProjectDialog extends JDialog {

    private Sonify app;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel titleLabel;
    private JTextField projectNameTextField;

    public NewProjectDialog(Sonify app) throws IOException, FontFormatException {
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
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });

        Font hnt20 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/HelveticaNeue-Thin.otf"))).deriveFont(Font.PLAIN, 20);

        titleLabel.setFont(hnt20);
        projectNameTextField.setText(app.getActiveProject().getName());

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

    private void onOK() throws MidiUnavailableException, LineUnavailableException, IOException, FontFormatException, InvalidMidiDataException, UnsupportedAudioFileException, URISyntaxException {

        Project newProject = new Project(projectNameTextField.getText());

        String name = projectNameTextField.getText();

        File trgDir = new File("projects/");
        File srcDir = new File("resources/projecttemplate.proj");
        FileUtils.copyFileToDirectory(srcDir, trgDir);

        File newSrcFile = new File("projects/" + projectNameTextField.getText() + ".proj");
        File srcFile = new File("projects/projecttemplate.proj");

        newProject.load(srcDir);
        srcFile.renameTo(newSrcFile);

        File saveFile = new File("resources/activeproject.txt");
        FileWriter fw = null;
        try {
            fw = new FileWriter(saveFile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(name + ".proj");
            bw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        app.getFrame().dispose();
        try {
            app = new Sonify();
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        app.getActiveProject().setName(name);
        app.getTitleTextField().setText(app.getActiveProject().getName());
        app.getFrame().setVisible(true);
        dispose();
    }

    private void onCancel() {

        setVisible(false);
    }
}
