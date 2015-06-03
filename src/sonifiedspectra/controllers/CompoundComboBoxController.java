package sonifiedspectra.controllers;

import org.jfree.chart.ChartPanel;
import sonifiedspectra.model.Compound;
import sonifiedspectra.model.Phrase;
import sonifiedspectra.model.Project;
import sonifiedspectra.view.SonifiedSpectra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Hvandenberg on 5/30/15.
 */
public class CompoundComboBoxController implements ItemListener {

    private SonifiedSpectra app;
    private Project project;
    private JComboBox compoundComboBox;

    public CompoundComboBoxController(SonifiedSpectra app, Project project, JComboBox compoundComboBox) {
        this.app = app;
        this.project = project;
        this.compoundComboBox = compoundComboBox;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        Compound compound = null;

        for (Compound c : project.getCompoundsArray()) {

            if (String.valueOf(compoundComboBox.getSelectedItem()).equals(c.getName())) compound = c;

        }

        app.getSpectrumLabel().setText(compound.getSpectrumType());
        app.getSpectrumLabel().repaint();

        final ChartPanel chPanel = new ChartPanel(compound.getDataChart().getDataChart());
        compound.getDataChart().getDataChart().getXYPlot().clearDomainMarkers();
        chPanel.setPreferredSize(new Dimension(500, 500));
        chPanel.setVisible(true);
        chPanel.setBounds(0, 0, 500, 400);
        chPanel.setDomainZoomable(true);
        app.getGraphPanel().setLayout(new BorderLayout());
        app.getGraphPanel().setBounds(20, 52, 500, 400);
        app.getGraphPanel().removeAll();
        app.getGraphPanel().add(chPanel, BorderLayout.CENTER);
        app.getGraphPanel().setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        app.getGraphPanel().repaint();

        if (app.isTemp()) {

            for (Phrase p : app.getActiveProject().getPhrasesArray()) {
                if (p.getCompound().getId() == compound.getId()) {
                    app.updateActivePhrase(p);
                    app.updateIntervalMarker();
                    break;
                }
            }

        }

        app.getFrame().pack();

    }
}
