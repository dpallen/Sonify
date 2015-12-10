package sonifiedspectra.controllers;

import org.jfree.chart.ChartPanel;
import sonifiedspectra.model.Phrase;
import sonifiedspectra.model.Project;
import sonifiedspectra.view.PhraseInTrackView;
import sonifiedspectra.view.Sonify;
import sonifiedspectra.view.TrackView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class PhraseInTrackController implements MouseListener {

    private Sonify app;
    private Project project;
    private PhraseInTrackView pitv;

    public PhraseInTrackController(Sonify app, Project project, PhraseInTrackView pitv) {
        this.app = app;
        this.project = project;
        this.pitv = pitv;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        for (TrackView tv : app.getTrackViewArray()) {
            for (PhraseInTrackView pitv2 : tv.getPhraseInTrackViewArray()) {
                System.out.println(pitv2.getPhrase().getId());
                pitv2.setSelected(false);
                pitv2.setBackground(pitv2.getPhrase().getUnselectedColor());
                pitv2.repaint();
            }
        }

        pitv.toggleSelected();

        if (pitv.isSelected()) {
            pitv.setBackground(pitv.getPhrase().getSelectedColor());
            if (pitv.getPhrase().isLoop()) {
                pitv.getNameLabel().setForeground(Color.BLACK);
                pitv.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, false));
            }
            pitv.repaint();
        }
        else {
            pitv.setBackground(pitv.getPhrase().getUnselectedColor());
            if (pitv.getPhrase().isLoop()) {
                pitv.getNameLabel().setForeground(Color.WHITE);
                pitv.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
            }
            pitv.repaint();
        }

        Phrase tempPhrase;

        tempPhrase = pitv.getPhrase();

        System.out.println("Temp: " + tempPhrase.getId() + ", selected: " + tempPhrase.isSelected());
        if (tempPhrase.getParentPhrase() != null) System.out.println("Temp parent: " + tempPhrase.getParentPhrase().getId() + ", selected: " + tempPhrase.getParentPhrase().isSelected());

        if (!tempPhrase.isSelected() || (tempPhrase.getParentPhrase() != null && !tempPhrase.getParentPhrase().isSelected())) {
            tempPhrase.setSelected(true);

            app.updateActivePhrase(tempPhrase);

            app.getColorButton().setCol(app.getActivePhrase().getUnselectedColor());
            app.getColorButton().repaint();

            app.getSpectrumLabel().setText(app.getActivePhrase().getCompound().getSpectrumType());
            app.getSpectrumLabel().repaint();

            app.setChPanel(new ChartPanel(app.getActivePhrase().getCompound().getDataChart().getDataChart()));
            app.getChPanel().setPreferredSize(new Dimension(500, 500));
            app.getChPanel().setVisible(true);
            app.getChPanel().setBounds(0, 0, 500, 400);
            app.getChPanel().setDomainZoomable(true);
            app.getChPanel().addChartMouseListener(new GraphController(app, project));
            app.getGraphPanel().setLayout(new BorderLayout());
            app.getGraphPanel().setBounds(20, 52, 500, 400);
            app.getGraphPanel().removeAll();
            app.getGraphPanel().add(app.getChPanel(), BorderLayout.CENTER);
            app.getGraphPanel().repaint();
            app.updateIntervalMarker();
            app.getSoundPlayer().reset();
            app.getSoundPlayer().updateSoundPlayer();
            app.getFrame().pack();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!pitv.isSelected()) {
            pitv.setBackground(pitv.getPhrase().getSelectedColor());
            if (pitv.getPhrase().isLoop()) pitv.getNameLabel().setForeground(Color.BLACK);
            pitv.repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!pitv.isSelected()) {
            pitv.setBackground(pitv.getPhrase().getUnselectedColor());
            if (pitv.getPhrase().isLoop()) pitv.getNameLabel().setForeground(Color.WHITE);
            pitv.repaint();
        }
    }
}
