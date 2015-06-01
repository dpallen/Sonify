package sonifiedspectra.controllers;

import org.jfree.chart.ChartPanel;
import sonifiedspectra.model.Model;
import sonifiedspectra.model.Phrase;
import sonifiedspectra.view.NoteView;
import sonifiedspectra.view.PhraseView;
import sonifiedspectra.view.SonifiedSpectra;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 5/31/15.
 */
public class PhraseController implements MouseListener {

    private Model model;
    private SonifiedSpectra app;
    private PhraseView phraseView;

    public PhraseController(Model model, SonifiedSpectra app, PhraseView phraseView) {
        this.model = model;
        this.app = app;
        this.phraseView = phraseView;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (!phraseView.getPhrase().isSelected()) {
            phraseView.getPhrase().setSelected(true);
            app.updateActivePhrase(phraseView.getPhrase());

            app.getColorButton().setCol(app.getActivePhrase().getCompound().getUnselectedColor());
            app.getColorButton().repaint();

            app.getSpectrumLabel().setText(app.getActivePhrase().getCompound().getSpectrumType());
            app.getSpectrumLabel().repaint();

            final ChartPanel chPanel = new ChartPanel(app.getActivePhrase().getCompound().getDataChart().getDataChart());
            chPanel.setPreferredSize(new Dimension(500, 500));
            chPanel.setVisible(true);
            chPanel.setBounds(0, 0, 500, 400);
            chPanel.setDomainZoomable(true);
            app.getGraphPanel().setLayout(new BorderLayout());
            app.getGraphPanel().setBounds(20, 52, 500, 400);
            app.getGraphPanel().removeAll();
            app.getGraphPanel().add(chPanel, BorderLayout.CENTER);
            app.getGraphPanel().repaint();
            app.updateIntervalMarker();
        }
        else {
            phraseView.getPhrase().setSelected(false);
            app.setActivePhrase(null);
            phraseView.updatePanel();
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
        if (!phraseView.getPhrase().isSelected()) {
            phraseView.setBackground(phraseView.getPhrase().getCompound().getSelectedColor());
            phraseView.repaint();
            app.getFrame().pack();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!phraseView.getPhrase().isSelected()) {
            phraseView.setBackground(phraseView.getPhrase().getCompound().getUnselectedColor());
            phraseView.repaint();
            app.getFrame().pack();
        }
    }
}
