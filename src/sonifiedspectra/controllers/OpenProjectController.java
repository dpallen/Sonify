package sonifiedspectra.controllers;

import sonifiedspectra.model.Compound;
import sonifiedspectra.model.Project;
import sonifiedspectra.view.Sonify;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.URISyntaxException;

/**
 * Created by Hvandenberg on 5/31/15.
 */
public class OpenProjectController implements ActionListener, MouseListener {

    private Sonify app;
    private Project project;
    private boolean visible;

    public OpenProjectController(Sonify app, Project project) {
        this.project = project;
        this.app = app;
        this.visible = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Compound compound;
        int i = 0;

        File projectFolder = new File(System.getProperty("user.home") + "/Documents/Sonify/Demo");
        if (projectFolder == null) {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose Project Folder");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                System.out.println("getCurrentDirectory(): "
                        + fileChooser.getCurrentDirectory());
                System.out.println("getSelectedFile(): "
                        + fileChooser.getSelectedFile());

                projectFolder = fileChooser.getSelectedFile();

            }

        }

        app.setCompoundComboBox(new JComboBox());
        for (Compound c : app.getActiveProject().getCompoundsArray()) {
            app.getCompoundComboBox().addItem(c.getName());
        }
        app.getCompoundComboBox().setBounds(123, 11, 158, 32);

        File[] directoryListing = new File(projectFolder + "/Compounds").listFiles();
        if (directoryListing != null) {
            for (File dataFile : directoryListing) {
                if (!dataFile.isHidden()) {
                    compound = new Compound(i, dataFile, "Infrared");
                    compound.load();
                    System.out.println("Added compound: " + compound.getName());
                    compound.getDataChart().createChart();
                    compound.setPeaks(compound.getDataChart().process());
                    app.getCompoundComboBox().addItem(compound.getName());
                    app.getActiveProject().getCompoundsArray().add(compound);
                    i++;
                }
            }
        } else {

        }

        app.getActiveProject().setDirectoryPath(projectFolder.getPath());
        app.getActiveProject().load(new File(projectFolder + "/project.son"));
        app.getActiveProject().setSaveFile(new File(projectFolder + "/project.son"));

        app.setActivePhrase(app.getActiveProject().getPhrasesArray().get(0));

        /*JFileChooser fc = new JFileChooser();
        File directory = new File("projects/");
        fc.setCurrentDirectory(directory);

        File f = null;

        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            f = fc.getSelectedFile();

            File saveFile = new File("resources/activeproject.txt");
            FileWriter fw = null;
            try {
                fw = new FileWriter(saveFile);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(f.getName());
                bw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            app.getFrame().dispose();
            try {
                app = new Sonify();
            } catch (FontFormatException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (MidiUnavailableException e1) {
                e1.printStackTrace();
            } catch (UnsupportedAudioFileException e1) {
                e1.printStackTrace();
            } catch (LineUnavailableException e1) {
                e1.printStackTrace();
            } catch (InvalidMidiDataException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
            app.getTitleTextField().setText(app.getActiveProject().getName());
            app.getFrame().setVisible(true);

            System.out.println("File: " + f.getName() + ".");

        } else {

            System.out.println("Open command cancelled by user.");

        }

        System.out.println(returnVal);*/
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        app.getOpenButton().setCol(app.getActivePhrase().getSelectedColor());
        app.getOpenButton().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        app.getOpenButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getOpenButton().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        app.getOpenButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getOpenButton().repaint();
        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        app.getOpenButton().setCol(app.getButtonBackgroundColor());
        app.getOpenButton().repaint();
        app.getFrame().pack();
    }

}
