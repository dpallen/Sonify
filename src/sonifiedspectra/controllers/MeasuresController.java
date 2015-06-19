package sonifiedspectra.controllers;

import sonifiedspectra.view.Sonify;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/18/15.
 */
public class MeasuresController implements MouseListener {

    private Sonify app;

    public MeasuresController(Sonify app) {
        this.app = app;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        app.getMeasuresDialog().getTotalMeasuresLabel().setText(String.valueOf(app.getActiveProject().getNumMeasures()));

        app.getMeasuresDialog().setVisible(!app.getMeasuresDialog().isVisible());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        app.getMeasuresButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getMeasuresButton().repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        app.getMeasuresButton().setCol(app.getButtonBackgroundColor());
        app.getMeasuresButton().repaint();
    }
}
