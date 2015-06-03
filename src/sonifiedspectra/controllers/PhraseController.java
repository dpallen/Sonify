package sonifiedspectra.controllers;

import org.jfree.chart.ChartPanel;
import sonifiedspectra.model.Project;
import sonifiedspectra.view.PhraseView;
import sonifiedspectra.view.SonifiedSpectra;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 5/31/15.
 */
public class PhraseController implements MouseListener {

    private Project project;
    private SonifiedSpectra app;
    private PhraseView phraseView;

    public PhraseController(Project project, SonifiedSpectra app, PhraseView phraseView) {
        this.project = project;
        this.app = app;
        this.phraseView = phraseView;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (!phraseView.getPhrase().isSelected()) {
            phraseView.getPhrase().setSelected(true);
            app.updateActivePhrase(phraseView.getPhrase());

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
            phraseView.setBackground(phraseView.getPhrase().getSelectedColor());
            phraseView.repaint();
            app.getFrame().pack();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!phraseView.getPhrase().isSelected()) {
            phraseView.setBackground(phraseView.getPhrase().getUnselectedColor());
            phraseView.repaint();
            app.getFrame().pack();
        }
    }
}
