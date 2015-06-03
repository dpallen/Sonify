package sonifiedspectra.controllers;

import sonifiedspectra.model.Project;
import sonifiedspectra.view.PhraseView;
import sonifiedspectra.view.SonifiedSpectra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hvandenberg on 5/31/15.
 */
public class RemovePhraseController implements ActionListener, MouseListener {

    private SonifiedSpectra app;
    private Project project;

    public RemovePhraseController(SonifiedSpectra app, Project project) {
        this.project = project;
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        boolean last = app.getActiveProject().getPhrasesArray().size() == (app.getActiveProject().getPhrasesArray().indexOf(app.getActivePhrase()) + 1);
        int index = app.getActiveProject().getPhrasesArray().indexOf(app.getActivePhrase());

        app.getActiveProject().getPhrasesArray().remove(app.getActivePhrase());
        app.getPhrasesPanel().remove(index);
        app.getPhraseViewArray().remove(index);

        if (!last) {
            for (int i = index; i < app.getPhraseViewArray().size(); i++) {
                app.getPhraseViewArray().get(i).setBounds(app.getPhraseViewArray().get(i).getX() - 110,
                        app.getPhraseViewArray().get(i).getY(), app.getPhraseViewArray().get(i).getWidth(),
                        app.getPhraseViewArray().get(i).getHeight());
                app.getPhraseViewArray().get(i).repaint();
            }
        }

        if (last && app.getActiveProject().getPhrasesArray().size() > 1) {

            app.updateActivePhrase(app.getActiveProject().getPhrasesArray().get(index - 1));

        }

        else if (!last) {
            app.updateActivePhrase(app.getActiveProject().getPhrasesArray().get(index));
        }
        app.getPhrasesPanel().repaint();

        //else app.updateActivePhrase(null);
        app.updateIntervalMarker();
        app.getSoundPlayer().reset();
        app.getSoundPlayer().updateSoundPlayer();
        app.getFrame().pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        app.getRemovePhraseButton().setCol(app.getActivePhrase().getSelectedColor());
        app.getRemovePhraseButton().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        app.getRemovePhraseButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getRemovePhraseButton().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        app.getRemovePhraseButton().setCol(app.getActivePhrase().getUnselectedColor());
        app.getRemovePhraseButton().repaint();
        app.getFrame().pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        app.getRemovePhraseButton().setCol(app.getButtonBackgroundColor());
        app.getRemovePhraseButton().repaint();
        app.getFrame().pack();
    }

}
