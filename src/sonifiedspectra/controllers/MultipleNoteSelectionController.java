package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.NoteView;
import sonifiedspectra.view.Sonify;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Hvandenberg on 5/31/15.
 */
public class MultipleNoteSelectionController implements ActionListener {

    private Sonify app;
    private Project project;
    private JCheckBox checkBox;
    private Icon selectedIcon;
    private Icon unselectedIcon;

    public MultipleNoteSelectionController(Sonify app, Project project, JCheckBox checkBox) {
        this.app = app;
        this.project = project;
        this.checkBox = checkBox;
        this.selectedIcon = new ImageIcon("resources/icons/multseleccheckboxselected.png");
        this.unselectedIcon = new ImageIcon("resources/icons/multseleccheckbox.png");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        project.toggleMultipleSelection();

        boolean isFirst = true;

        if (!project.isNotesPanelMultipleSelection() && app.getActivePhrase().getSelectedNotes().size() > 1) {
            for (NoteView nv : app.getNoteViewArray()) {
                if (nv.getNote().isSelected()) {
                    if (isFirst) isFirst = false;
                    else nv.getNote().setSelected(false);
                }
                nv.updatePanel();
            }
            app.updateIntervalMarker();
        }

        updateIcon();
        checkBox.repaint();
        app.getFrame().pack();
    }

    public void updateIcon() {
        if (project.isNotesPanelMultipleSelection()) {
            checkBox.setIcon(selectedIcon);
        }
        else {
            checkBox.setIcon(unselectedIcon);
        }
    }
}
