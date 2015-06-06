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
    private int currentPhraseId;
    private int currentTrackId;
    private int movePitvFactor;

    private boolean notesPanelMultipleSelection;
    private boolean tracksPanelMultipleSelection;

    public Project() {
        compoundsArray = new ArrayList<Compound>();
        phrasesArray = new ArrayList<Phrase>();
        tracksArray = new ArrayList<Track>();
        notesPanelMultipleSelection = true;
        tracksPanelMultipleSelection = true;
        tempo = 120;
        movePitvFactor = 4;
        currentPhraseId = 0;
        currentTrackId = 0;
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
        return 30;
    }

    public void incrementPhraseId() {
        currentPhraseId++;
    }

    public void incrementTrackId() {
        currentTrackId++;
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

    public void toggleTracksMultSelect() {
        tracksPanelMultipleSelection = !tracksPanelMultipleSelection;
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

    public int getCurrentPhraseId() {
        return currentPhraseId;
    }

    public void setCurrentPhraseId(int currentPhraseId) {
        this.currentPhraseId = currentPhraseId;
    }

    public boolean isTracksPanelMultipleSelection() {
        return tracksPanelMultipleSelection;
    }

    public void setTracksPanelMultipleSelection(boolean tracksPanelMultipleSelection) {
        this.tracksPanelMultipleSelection = tracksPanelMultipleSelection;
    }

    public int getMovePitvFactor() {
        return movePitvFactor;
    }

    public void setMovePitvFactor(int movePitvFactor) {
        this.movePitvFactor = movePitvFactor;
    }

    public int getCurrentTrackId() {
        return currentTrackId;
    }

    public void setCurrentTrackId(int currentTrackId) {
        this.currentTrackId = currentTrackId;
    }
}
