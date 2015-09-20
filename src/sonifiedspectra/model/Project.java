package sonifiedspectra.model;

import java.io.*;
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
    private int numMeasures;
    private int id;

    private File saveFile;

    private boolean notesPanelMultipleSelection;
    private boolean tracksPanelMultipleSelection;

    public Project(String name) {
        this.name = name;
        this.saveFile = new File(String.valueOf(id));
        compoundsArray = new ArrayList<Compound>();
        phrasesArray = new ArrayList<Phrase>();
        tracksArray = new ArrayList<Track>();
        notesPanelMultipleSelection = false;
        tracksPanelMultipleSelection = false;
        tempo = 120;
        movePitvFactor = 4;
        currentPhraseId = 0;
        currentTrackId = 0;
        numMeasures = 30;
    }

    public Project(ArrayList<Compound> compoundsArray, ArrayList<Phrase> phrasesArray, ArrayList<Track> tracksArray) {
        this.compoundsArray = compoundsArray;
        this.phrasesArray = phrasesArray;
        this.tracksArray = tracksArray;
        this.tempo = 120;
    }

    public void save() throws IOException {
        saveFile = new File("projects/" + name + ".proj");

        FileWriter fw = new FileWriter(saveFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(name);
        bw.newLine();
        bw.write(String.valueOf(tempo));
        bw.newLine();
        bw.write(String.valueOf(movePitvFactor));
        bw.newLine();
        bw.write(String.valueOf(currentPhraseId));
        bw.newLine();
        bw.write(String.valueOf(currentTrackId));
        bw.newLine();
        bw.write(String.valueOf(numMeasures));
        bw.newLine();

        for (Phrase p : phrasesArray) {
            bw.write(p.saveString());
            bw.newLine();
            for (Note n : p.getNotesArray()) {
                bw.write(n.saveString());
                bw.newLine();
            }
            bw.write("End Phrase");
            bw.newLine();
        }

        bw.write("Tracks");
        bw.newLine();

        for (Track t : tracksArray) {
            bw.write(t.saveString());
            bw.newLine();
            for (Phrase p : t.getPhrases()) {

                int parentId = -1;
                if (p.getParentPhrase() != null) parentId = p.getParentPhrase().getId();

                bw.write(String.valueOf(p.getId()) + "," + parentId + "," + p.getStartTime());
                bw.newLine();
            }
            bw.write("End Track");
            bw.newLine();
        }

        bw.close();

    }

    public void load(File saveFile) {
        this.saveFile = saveFile;

        String line;
        BufferedReader reader;
        String[] array;

        boolean isReadingPhrases = true;

        try {

            reader = new BufferedReader( new FileReader(saveFile) );

            name = reader.readLine();
            tempo = Integer.valueOf(reader.readLine());
            movePitvFactor = Integer.valueOf(reader.readLine());
            currentPhraseId = Integer.valueOf(reader.readLine());
            currentTrackId = Integer.valueOf(reader.readLine());
            numMeasures = Integer.valueOf(reader.readLine());

            while ( !(( line = reader.readLine() ) == null) ) {

                if (line.equals("Tracks")) {
                    isReadingPhrases = false;
                    line = reader.readLine();
                }

                if (isReadingPhrases) {

                    array = line.split(",");

                    int id = Integer.valueOf(array[0]);
                    int instrument = Integer.valueOf(array[1]);
                    int maxPitch = Integer.valueOf(array[2]);
                    int minPitch = Integer.valueOf(array[3]);
                    int currentFillerId = Integer.valueOf(array[4]);
                    double x1 = Double.parseDouble(String.valueOf(array[5]));
                    double x2 = Double.parseDouble(String.valueOf(array[6]));
                    double highestWidth = Double.parseDouble(String.valueOf(array[7]));
                    double lowestWidth = Double.parseDouble(String.valueOf(array[8]));
                    boolean quantized = Boolean.valueOf(array[9]);
                    boolean selected = Boolean.valueOf(array[10]);
                    boolean loop = Boolean.valueOf(array[11]);
                    String color = String.valueOf(array[12]);
                    String key = String.valueOf(array[13]);
                    String quality = String.valueOf(array[14]);
                    String qRhythm = String.valueOf(array[15]);

                    String compoundName = String.valueOf(array[16]);
                    double startTime = Double.parseDouble(String.valueOf(array[17]));
                    int parentId = Integer.valueOf(array[18]);

                    Compound compound = null;

                    for (Compound c : compoundsArray) {
                        if (c.getName().equals(compoundName)) compound = c;
                        //System.out.println(compound.getName());
                    }

                    Phrase phrase = new Phrase(id, compound, color, x1, x2);
                    phrase.setInstrument(instrument);
                    phrase.setMaxPitch(maxPitch);
                    phrase.setMinPitch(minPitch);
                    phrase.setCurrentFillerId(currentFillerId);
                    phrase.setHighestWidth(highestWidth);
                    phrase.setLowestWidth(lowestWidth);
                    phrase.setQuantized(quantized);
                    phrase.setSelected(selected);
                    phrase.setLoop(loop);
                    phrase.setKey(key);
                    phrase.setQuality(quality);
                    phrase.setQRhythm(qRhythm);
                    phrase.setStartTime(startTime);
                    phrase.setBackgroundCol(phrase.getColor());

                    if (parentId != -1) {
                        Phrase parentPhrase = null;

                        for (Phrase p : phrasesArray) {
                            if (p.getId() == parentId) phrase.setParentPhrase(p);
                        }
                    }

                    while (!((line = reader.readLine()).equals("End Phrase"))) {

                        array = line.split(",");

                        int noteId = Integer.valueOf(array[0]);
                        int transpose = Integer.valueOf(array[1]);
                        int peakId = Integer.valueOf(array[2]);
                        int phraseId = Integer.valueOf(array[3]);
                        boolean filler = Boolean.valueOf(array[4]);
                        boolean noteSelected = Boolean.valueOf(array[5]);
                        int pitch = Integer.valueOf(array[6]);
                        double rhythmValue = Double.parseDouble(String.valueOf(array[7]));
                        int dynamic = Integer.valueOf(array[8]);

                        Peak peak = null;

                        for (Peak p : compound.getPeaks()) {
                            if (peakId == p.getId()) peak = p;
                        }

                        Note note = new Note(id, peak, filler, phrase);
                        note.setPitch(pitch);
                        note.setRhythmValue(rhythmValue);
                        note.setTranspose(transpose);
                        note.setDynamic(dynamic);
                        phrase.getNotesArray().add(note);

                    }

                    phrasesArray.add(phrase);

                }

                else {

                    System.out.println(line);

                    array = line.split(",");

                    int trackId = Integer.valueOf(array[0]);
                    boolean trackSelected = Boolean.valueOf(array[1]);
                    boolean live = Boolean.valueOf(array[2]);
                    boolean expanded = Boolean.valueOf(array[3]);
                    int instrument = Integer.valueOf(array[4]);

                    Track track = new Track(trackId);

                    track.setSelected(trackSelected);
                    track.setLive(live);
                    track.setExpanded(expanded);
                    track.setInstrument(instrument);

                    while (!((line = reader.readLine()).equals("End Track"))) {

                        array = line.split(",");

                        int phraseId = Integer.valueOf(array[0]);
                        int parentId = Integer.valueOf(array[1]);
                        double startTime = Double.valueOf(array[2]);

                        Phrase phrase = null;

                        for (Phrase p : phrasesArray) {
                            if (p.getId() == phraseId || (parentId != -1 && p.getId() == parentId)) phrase = p;
                        }

                        phrase.setStartTime(startTime);

                        track.getPhrases().add(phrase);

                    }

                    tracksArray.add(track);

                }

            }

            reader.close();
        }

        catch ( Exception ex ) {

            ex.printStackTrace();

        }

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
        return numMeasures;
    }

    public void incrementPhraseId() {
        currentPhraseId++;
    }

    public void incrementTrackId() {
        currentTrackId++;
    }

    public void incrementNumMeasures(int measures) {
        numMeasures += measures;
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

    public void setNumMeasures(int numMeasures) {
        this.numMeasures = numMeasures;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public File getSaveFile() {
        return saveFile;
    }

    public void setSaveFile(File saveFile) {
        this.saveFile = saveFile;
    }
}
