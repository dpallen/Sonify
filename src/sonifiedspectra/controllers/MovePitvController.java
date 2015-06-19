package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.PhraseInTrackView;
import sonifiedspectra.view.Sonify;
import sonifiedspectra.view.TrackView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/3/15.
 */
public class MovePitvController implements ActionListener, MouseListener {

    private Sonify app;
    private Project project;
    private int type;

    public MovePitvController(Sonify app, Project project, int type) {
        this.project = project;
        this.app = app;
        this.type = type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int move = Integer.valueOf(app.getMovePitvTextField().getText());

        if (type == 1) {
            move = move * (-1);
        }

        for (TrackView tv : app.getTrackViewArray()) {
            for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray()) {
                if (pitv.isSelected()) {

                    int x = pitv.getX() + (app.getMeasureScale() * move);
                    if (x < 0) x = 0;

                    double newTime = ((double) move)/((double) 4);
                    double newX = pitv.getPhrase().getStartTime() + newTime;
                    if (newX < 0) newX = 0;

                    System.out.println("New time: " + newTime);

                    pitv.getPhrase().setStartTime(newX);
                    System.out.println("Start time: " + pitv.getPhrase().getStartTime());
                    pitv.setBounds(x, pitv.getY(), pitv.getWidth(), pitv.getHeight());
                    pitv.repaint();
                }
            }
        }

        app.getSoundPlayer().reset();
        app.getSoundPlayer().updateSoundPlayer();

        app.getFrame().pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (type == 0) {
            app.getMovePitvRightButton().setCol(app.getActivePhrase().getSelectedColor());
            app.getMovePitvRightButton().repaint();
        }
        else if (type == 1) {
            app.getMovePitvLeftButton().setCol(app.getActivePhrase().getSelectedColor());
            app.getMovePitvLeftButton().repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (type == 0) {
            app.getMovePitvRightButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getMovePitvRightButton().repaint();
        }
        else if (type == 1) {
            app.getMovePitvLeftButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getMovePitvLeftButton().repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

        if (type == 0) {
            System.out.println("Lasdljfas");
            app.getMovePitvRightButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getMovePitvRightButton().repaint();
        }
        else if (type == 1) {
            app.getMovePitvLeftButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getMovePitvLeftButton().repaint();
        }

        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {

        if (type == 0) {
            app.getMovePitvRightButton().setCol(app.getButtonBackgroundColor());
            app.getMovePitvRightButton().repaint();
        }
        else if (type == 1) {
            app.getMovePitvLeftButton().setCol(app.getButtonBackgroundColor());
            app.getMovePitvLeftButton().repaint();
        }

        app.getFrame().pack();
    }

}
