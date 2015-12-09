package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.MeasureHeadView;
import sonifiedspectra.view.Sonify;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class MeasureHeadController implements MouseListener {

    private Sonify app;
    private Project project;
    private MeasureHeadView mhv;

    public MeasureHeadController(Sonify app, Project project, MeasureHeadView mhv) {
        this.app = app;
        this.project = project;
        this.mhv = mhv;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        mhv.toggleSelected();

        if (!project.isTracksPanelMultipleSelection()) {
            for (MeasureHeadView mhv2 : app.getMeasureHeadViewArray()) {
                if (mhv.getMeasureNumber() != mhv2.getMeasureNumber()) mhv2.setSelected(false);
                mhv2.updatePanel();
            }
        }

        if (mhv.isSelected()) {
            mhv.setBackground(app.getActivePhrase().getUnselectedColor());
            mhv.setBorder(BorderFactory.createLineBorder(app.getActivePhrase().getBorderColor(), 2, false));
            mhv.repaint();
        }
        else {
            mhv.setBackground(app.getButtonBackgroundColor());
            mhv.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
        }
        app.getFrame().pack();

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!mhv.isSelected()) {
            mhv.setBackground(app.getActivePhrase().getUnselectedColor());

            mhv.repaint();
            //mhv.paintComponent(mhv.getGraphics());

        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!mhv.isSelected()) {
            mhv.setBackColor(mhv.getBackColor());
            mhv.updatePanel();
        }
    }
}
