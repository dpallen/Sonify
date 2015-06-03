package sonifiedspectra.controllers;

import sonifiedspectra.model.Note;
import sonifiedspectra.model.Phrase;
import sonifiedspectra.model.Project;
import sonifiedspectra.view.SonifiedSpectra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 5/31/15.
 */
public class FillerController implements ActionListener, MouseListener {

    private SonifiedSpectra app;
    private Project project;
    private boolean visible;

    public FillerController(SonifiedSpectra app, Project project) {
        this.project = project;
        this.app = app;
        this.visible = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int numFiller = 0;
        int numNonFiller = 0;

        for (Note n : app.getActivePhrase().getNotesArray()) {
            if (n.isSelected()) {
                if (n.isFiller()) numFiller++;
                else numNonFiller++;
            }
        }

        app.getFillerDialog().getSelectedLabel().setText("Selected: " + numFiller + " filler, " + numNonFiller + " non-filler");

        app.getFillerDialog().setVisible(!app.getFillerDialog().isVisible());

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        app.getFillerButton().setCol(app.getActivePhrase().getSelectedColor());
        app.getFillerButton().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        app.getFillerButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getFillerButton().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        app.getFillerButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getFillerButton().repaint();
        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        app.getFillerButton().setCol(app.getButtonBackgroundColor());
        app.getFillerButton().repaint();
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

}
