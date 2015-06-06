package sonifiedspectra.controllers;

import sonifiedspectra.view.SonifiedSpectra;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Hvandenberg on 6/5/15.
 */
public class PhraseInstrumentController implements ItemListener {

    private SonifiedSpectra app;

    public PhraseInstrumentController(SonifiedSpectra app) {
        this.app = app;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        app.getActivePhrase().setInstrument(app.getInstrumentComboBox().getSelectedIndex());
    }
}
