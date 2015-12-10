package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.MeasureHeadView;
import sonifiedspectra.view.Sonify;
import sonifiedspectra.view.TrackHeadView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Hvandenberg on 6/3/15.
 */
public class TrackMultSelectController implements ActionListener {

    private Sonify app;
    private Project project;
    private JCheckBox checkBox;
    private Icon selectedIcon;
    private Icon unselectedIcon;

    public TrackMultSelectController(Sonify app, Project project, JCheckBox checkBox) {
        this.app = app;
        this.project = project;
        this.checkBox = checkBox;
        this.selectedIcon = new ImageIcon(getClass().getResource("/icons/tracksmultselected.png"));
        this.unselectedIcon = new ImageIcon(getClass().getResource("/icons/tracksmultselecticon.png"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        project.toggleTracksMultSelect();

        boolean isFirst1 = true;
        boolean isFirst2 = true;

        if (!project.isTracksPanelMultipleSelection() && app.getActiveProject().getSelectedTracks().size() > 1) {
            for (TrackHeadView thv : app.getTrackHeadViewArray()) {
                if (thv.getTrack().isSelected()) {
                    if (isFirst1) isFirst1 = false;
                    else {
                        thv.getTrack().setSelected(false);
                        app.getTrackViewArray().get(app.getTrackHeadViewArray().indexOf(thv)).setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
                        app.getTrackViewArray().get(app.getTrackHeadViewArray().indexOf(thv)).setBackground(thv.getBackColor());
                    }
                }
                if (!thv.getTrack().isSelected()) thv.updatePanel();
            }
        }

        if (!project.isTracksPanelMultipleSelection() && app.getSelectedMeasures().size() > 1) {
            for (MeasureHeadView mhv : app.getMeasureHeadViewArray()) {
                if (mhv.isSelected()) {
                    if (isFirst2) isFirst2 = false;
                    else {
                        mhv.setSelected(false);
                        app.getMeasureHeadViewArray().get(app.getMeasureHeadViewArray().indexOf(mhv)).setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
                    }
                }
                if (!mhv.isSelected()) mhv.updatePanel();
            }
        }

        updateIcon();
        checkBox.repaint();
    }

    public void updateIcon() {
        if (project.isTracksPanelMultipleSelection()) {
            checkBox.setIcon(selectedIcon);
        }
        else {
            checkBox.setIcon(unselectedIcon);
        }
    }

}
