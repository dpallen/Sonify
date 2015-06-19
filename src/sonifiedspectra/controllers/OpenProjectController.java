package sonifiedspectra.controllers;

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

        JFileChooser fc = new JFileChooser();
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
            }
            app.getTitleTextField().setText(app.getActiveProject().getName());
            app.getFrame().setVisible(true);

            System.out.println("File: " + f.getName() + ".");

        } else {

            System.out.println("Open command cancelled by user.");

        }

        System.out.println(returnVal);
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
