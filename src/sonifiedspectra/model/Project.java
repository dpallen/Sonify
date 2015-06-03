package sonifiedspectra.model;

import java.util.ArrayList;

/**
 * Created by Hvandenberg on 5/28/15.
 */
public class Project {

    private ArrayList<Compound> compoundsArray;
    private ArrayList<Phrase> phrasesArray;
    private ArrayList<Track> tracksArray;
    private String name;
    private int tempo;

    private boolean notesPanelMultipleSelection;

    public Project() {
        compoundsArray = new ArrayList<Compound>();
        phrasesArray = new ArrayList<Phrase>();
        tracksArray = new ArrayList<Track>();
        notesPanelMultipleSelection = true;
        this.tempo = 120;
    }

    public Project(ArrayList<Compound> compoundsArray, ArrayList<Phrase> phrasesArray, ArrayList<Track> tracksArray) {
        this.compoundsArray = compoundsArray;
        this.phrasesArray = phrasesArray;
        this.tracksArray = tracksArray;
        this.tempo = 120;
    }

    public ArrayList<Track> getSelectedTracks() {
        ArrayList<Track> selectedTracks = new ArrayList<Track>();

        for (Track t : tracksArray) {
            if (t.isSelected()) selectedTracks.add(t);
        }

        return selectedTracks;
    }

        public ArrayList<Compound> getCompoundsArray() {
        return compoundsArray;
    }

    public int getNumMeasures() {
        return 10;
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

    public ArrayList<Track> getTracksArray() {
        return tracksArray;
    }

    public void setTracksArray(ArrayList<Track> tracksArray) {
        this.tracksArray = tracksArray;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
}
