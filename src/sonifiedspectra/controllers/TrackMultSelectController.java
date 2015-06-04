package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.MeasureHeadView;
import sonifiedspectra.view.NoteView;
import sonifiedspectra.view.SonifiedSpectra;
import sonifiedspectra.view.TrackHeadView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Hvandenberg on 6/3/15.
 */
public class TrackMultSelectController implements ActionListener {

    private SonifiedSpectra app;
    private Project project;
    private JCheckBox checkBox;
    private Icon selectedIcon;
    private Icon unselectedIcon;

    public TrackMultSelectController(SonifiedSpectra app, Project project, JCheckBox checkBox) {
        this.app = app;
        this.project = project;
        this.checkBox = checkBox;
        this.unselectedIcon = new ImageIcon("resources/icons/tracksmultselecticon.png");
        this.selectedIcon = new ImageIcon("resources/icons/tracksmultselected.png");
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
                    else thv.getTrack().setSelected(false);
                }
                thv.updatePanel();
            }
        }

        if (!project.isTracksPanelMultipleSelection() && app.getSelectedMeasures().size() > 1) {
            for (MeasureHeadView mhv : app.getMeasureHeadViewArray()) {
                if (mhv.isSelected()) {
                    if (isFirst2) isFirst2 = false;
                    else mhv.setSelected(false);
                }
                mhv.updatePanel();
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
