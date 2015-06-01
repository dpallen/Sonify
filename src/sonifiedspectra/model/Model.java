package sonifiedspectra.model;

import java.util.ArrayList;

/**
 * Created by Hvandenberg on 5/28/15.
 */
public class Model {

    private ArrayList<Compound> compoundsArray;
    private ArrayList<Phrase> phrasesArray;

    private boolean notesPanelMultipleSelection;

    public Model() {
        compoundsArray = new ArrayList<Compound>();
        phrasesArray = new ArrayList<Phrase>();
        notesPanelMultipleSelection = true;
    }

    public Model(ArrayList<Compound> compoundsArray, ArrayList<Phrase> phrasesArray) {
        this.compoundsArray = compoundsArray;
        this.phrasesArray = phrasesArray;
    }

    public ArrayList<Compound> getCompoundsArray() {
        return compoundsArray;
    }

    public void setCompoundsArray(ArrayList<Compound> compoundsArray) {
        this.compoundsArray = compoundsArray;
    }

    public ArrayList<Phrase> getPhrasesArray() {
        return phrasesArray;
    }

    public void setPhrasesArray(ArrayList<Phrase> phrasesArray) {
        this.phrasesArray = phrasesArray;
    }

    public boolean isNotesPanelMultipleSelection() {
        return notesPanelMultipleSelection;
    }

    public void setNotesPanelMultipleSelection(boolean notesPanelMultipleSelection) {
        this.notesPanelMultipleSelection = notesPanelMultipleSelection;
    }

    public void toggleMultipleSelection() {
        notesPanelMultipleSelection = !notesPanelMultipleSelection;
    }
}
