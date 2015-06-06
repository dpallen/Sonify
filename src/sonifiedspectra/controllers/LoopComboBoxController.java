package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.SonifiedSpectra;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Hvandenberg on 6/4/15.
 */
public class LoopComboBoxController implements ItemListener {

    private SonifiedSpectra app;
    private Project project;
    private JComboBox loopComboBox;

    public LoopComboBoxController(SonifiedSpectra app, Project project, JComboBox loopComboBox) {
        this.app = app;
        this.project = project;
        this.loopComboBox = loopComboBox;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        app.getLoopDialog().getLoopPlayer().reset();
        app.getLoopDialog().getLoopPlayer().updateLoopPlayer(app.getLoopDialog().getLoopsArray()
                .get(loopComboBox.getSelectedIndex()));
    }
}
