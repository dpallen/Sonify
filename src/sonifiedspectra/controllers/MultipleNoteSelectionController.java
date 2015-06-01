package sonifiedspectra.controllers;

import sonifiedspectra.model.Model;
import sonifiedspectra.view.NoteView;
import sonifiedspectra.view.SonifiedSpectra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Hvandenberg on 5/31/15.
 */
public class MultipleNoteSelectionController implements ActionListener {

    private SonifiedSpectra app;
    private Model model;
    private JCheckBox checkBox;
    private Icon selectedIcon;
    private Icon unselectedIcon;

    public MultipleNoteSelectionController(SonifiedSpectra app, Model model, JCheckBox checkBox) {
        this.app = app;
        this.model = model;
        this.checkBox = checkBox;
        this.selectedIcon = new ImageIcon("resources/icons/multseleccheckboxselected.png");
        this.unselectedIcon = new ImageIcon("resources/icons/multseleccheckbox.png");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.toggleMultipleSelection();

        boolean isFirst = true;

        if (!model.isNotesPanelMultipleSelection() && app.getActivePhrase().getSelectedNotes().size() > 1) {
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
    }

    public void updateIcon() {
        if (model.isNotesPanelMultipleSelection()) {
            checkBox.setIcon(selectedIcon);
        }
        else {
            checkBox.setIcon(unselectedIcon);
        }
    }
}
