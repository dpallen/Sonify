package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.NoteView;
import sonifiedspectra.view.Sonify;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Hvandenberg on 12/9/15.
 */
public class SelectAllOrNoneController implements ActionListener {

    private Sonify app;
    private Project project;
    private JCheckBox checkBox;
    private Icon selectedIcon;
    private Icon unselectedIcon;

    public SelectAllOrNoneController(Sonify app, Project project, JCheckBox checkBox) {
        this.app = app;
        this.project = project;
        this.checkBox = checkBox;
        this.selectedIcon = new ImageIcon(getClass().getResource("/icons/multseleccheckboxselected.png"));
        this.unselectedIcon = new ImageIcon(getClass().getResource("/icons/multseleccheckbox.png"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (checkBox.isSelected()) {
            for (NoteView nv : app.getNoteViewArray()) {
                nv.getNote().setSelected(true);
                nv.updatePanel();
            }
            app.updateIntervalMarker();
        }

        else {
            for (NoteView nv : app.getNoteViewArray()) {
                nv.getNote().setSelected(false);
                nv.updatePanel();
            }
            app.updateIntervalMarker();
        }

        updateIcon();
        checkBox.repaint();
        app.getFrame().pack();
    }

    public void updateIcon() {
        if (checkBox.isSelected()) {
            checkBox.setIcon(selectedIcon);
        }
        else {
            checkBox.setIcon(unselectedIcon);
        }
    }

}
