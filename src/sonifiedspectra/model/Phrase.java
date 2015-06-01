package sonifiedspectra.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Hvandenberg on 5/28/15.
 */
public class Phrase extends jm.music.data.Phrase {

    private int id;
    private int instrument;
    private int minPitch;
    private int maxPitch;
    private int numMeasures;

    private double x1;
    private double x2;
    private double highestWidth;
    private double lowestWidth;

    private boolean quantized;
    private boolean selected;

    private Color color;
    private String key;
    private String quality;

    private Compound compound;

    private ArrayList<Note> notesArray;

    public Phrase(int id, Compound compound, double x1, double x2) {
        this.id = id;
        this.instrument = 0;
        this.minPitch = 36;
        this.maxPitch = 84;
        this.numMeasures = 4;
        this.x1 = x1;
        this.x2 = x2;
        this.quantized = true;
        this.color = Color.RED;
        this.key = null;
        this.quality = null;
        this.compound = compound;
        this.selected = false;
        this.notesArray = new ArrayList<Note>();

    }

    public void initialize() {

        System.out.println("Initializing phrase...");
        System.out.println("    ID: " + id);
        System.out.println("    Compound name: " + compound.getName());
        System.out.println("    x1: " + x1);
        System.out.println("    x2: " + x2);

        int startIndex = 0;
        int endIndex = 0;

        for (Peak p : compound.getDataChart().getPeaksArray()) {
            if (x2 < p.getX1() && x2 > p.getX2() ) {
                startIndex = compound.getDataChart().getPeaksArray().indexOf(p);
                System.out.println("    First peak index: " + startIndex);
            }
            if (x1 < p.getX1() && x1 > p.getX2() ) {
                endIndex = compound.getDataChart().getPeaksArray().indexOf(p);
                System.out.println("    Last peak index: " + endIndex);
            }
        }

        int noteID = 0;
        System.out.println("    Adding notes:");

        for (int i = startIndex; i < endIndex; i ++) {
            Note newNote = new Note(noteID, compound.getDataChart().getPeaksArray().get(i), false, this);
            noteID++;
            newNote.setPitch(scalePitch(newNote.getPeak().getValue(), compound.getDataChart().getHighestY(),
                    compound.getDataChart().getLowestY(), maxPitch, minPitch));
            newNote.setDynamic(100);

            if (i == startIndex) {
                highestWidth = newNote.getPeak().getWidth();
                lowestWidth = newNote.getPeak().getWidth();
            }

            if (newNote.getPeak().getWidth() > highestWidth) highestWidth = newNote.getPeak().getWidth();
            if (newNote.getPeak().getWidth() < lowestWidth) lowestWidth = newNote.getPeak().getWidth();

            notesArray.add(newNote);
        }

        for (Note note : notesArray) {

            note.setRhythmValue(scaleRhythmValue(note.getPeak().getWidth()));
            System.out.println("        Note " + note.getId() + " - pitch: " + note.getPitch() + " (" +
                    note.convertPitchToString() + "), rhythm value: " + note.getRhythmValue() + ", peak width: " +
                    note.getPeak().getWidth() + ", dynamic: " + note.getDynamic());
        }

        System.out.println("    Highest width: " + highestWidth + ", lowest width: " + lowestWidth);
        System.out.println("    Total notes added: " + notesArray.size());
        System.out.println();
        System.out.println("Phrase initialized.");

    }

    public ArrayList<Note> getSelectedNotes() {
        ArrayList<Note> selectedNotes = new ArrayList<Note>();

        for (Note n : notesArray){
            if (n.isSelected()) selectedNotes.add(n);
        }

        return selectedNotes;
    }

    public void transposeSelectedNotesUp() {

        for (Note n : getSelectedNotes()) {
            n.transposeUp();
        }

    }

    public void transposeSelectedNotesDown() {

        for (Note n : getSelectedNotes()) {
            n.transposeDown();
        }

    }

    public boolean isValidTransposeSelection() {
        boolean validtransposeSelection = true;

        if (getSelectedNotes().size() > 0) {

            int transpose = getSelectedNotes().get(0).getTranspose();

            for (Note n : getSelectedNotes()) {
                validtransposeSelection = transpose == n.getTranspose();
                if (!validtransposeSelection) return false;
            }

            return true;
        }

        else return false;
    }

    /**
     * Scales the pitch values to be between specified pitch values (midi, 0-127)
     * @return scaled pitch
     */
    public int scalePitch(double value, double highestY, double lowestY, int min, int max) {

        double scale = (double) (max - min) / (highestY - lowestY);
        int pitch = (int) (min + ((value - lowestY) * scale));
        return pitch;

    }

    /**
     * Scales the rhythm values to be between specified rhythm values: 1.0 for
     * quarter note, 0.5 for eighth note, etc
     * @param value pitch to be scaled
     * @return scaled pitch
     */
    public double scaleRhythmValue(double value) {

        double scale = (2 - 0.25) / (highestWidth - lowestWidth);
        double rhythmValue = (0.25 + ((value - lowestWidth) * scale));
        return Math.round(rhythmValue * 4)/4f;

    }

    public void toggleSelected() {
        selected = !selected;
    }

    @Override
    public String toString() {
        return "Phrase{" +
                "id=" + id +
                ", instrument=" + instrument +
                ", minPitch=" + minPitch +
                ", maxPitch=" + maxPitch +
                ", numMeasures=" + numMeasures +
                ", x1=" + x1 +
                ", x2=" + x2 +
                ", quantized=" + quantized +
                ", color='" + color + '\'' +
                ", key='" + key + '\'' +
                ", quality='" + quality + '\'' +
                ", compound=" + compound +
                ", notesArray=" + notesArray +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getInstrument() {
        return instrument;
    }

    @Override
    public void setInstrument(int instrument) {
        this.instrument = instrument;
    }

    public int getMinPitch() {
        return minPitch;
    }

    public void setMinPitch(int minPitch) {
        this.minPitch = minPitch;
    }

    public int getMaxPitch() {
        return maxPitch;
    }

    public void setMaxPitch(int maxPitch) {
        this.maxPitch = maxPitch;
    }

    public int getNumMeasures() {
        return numMeasures;
    }

    public void setNumMeasures(int numMeasures) {
        this.numMeasures = numMeasures;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public boolean isQuantized() {
        return quantized;
    }

    public void setQuantized(boolean quantized) {
        this.quantized = quantized;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Compound getCompound() {
        return compound;
    }

    public void setCompound(Compound compound) {
        this.compound = compound;
    }

    public ArrayList<Note> getNotesArray() {
        return notesArray;
    }

    public void setNotesArray(ArrayList<Note> notesArray) {
        this.notesArray = notesArray;
    }

    public double getHighestWidth() {
        return highestWidth;
    }

    public void setHighestWidth(double highestWidth) {
        this.highestWidth = highestWidth;
    }

    public double getLowestWidth() {
        return lowestWidth;
    }

    public void setLowestWidth(double lowestWidth) {
        this.lowestWidth = lowestWidth;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
