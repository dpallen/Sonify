package sonifiedspectra.model;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Hvandenberg on 5/28/15.
 */
public class Phrase extends jm.music.data.Phrase {

    private int id;
    private int instrument;
    private int minPitch;
    private int maxPitch;
    private int currentFillerId;

    private double x1;
    private double x2;
    private double highestWidth;
    private double lowestWidth;

    private boolean quantized;
    private boolean selected;
    private boolean loop;

    private String color;
    private String key;
    private String quality;
    private String qRhythm;

    private Color unselectedColor;
    private Color borderColor;
    private Color selectedColor;

    private Compound compound;
    private Phrase parentPhrase;

    private ArrayList<Note> notesArray;

    private File midiFile;

    public Phrase() {
        this.loop = true;
        this.notesArray = new ArrayList<Note>();
    }

    public Phrase(int id, Compound compound, String color, double x1, double x2) {
        this.id = id;
        this.instrument = 0;
        this.minPitch = 36;
        this.maxPitch = 84;
        this.x1 = x1;
        this.x2 = x2;
        this.quantized = true;
        this.color = color;
        this.key = "C";
        this.quality = "Major";
        this.qRhythm = "1/4";
        this.compound = compound;
        this.currentFillerId = 0;
        this.selected = false;
        this.loop = false;
        this.notesArray = new ArrayList<Note>();

        setBackgroundCol(color);
    }

    public Phrase copy() {
        Phrase newPhrase = new Phrase(id, compound, color, x1, x2);
        newPhrase.setParentPhrase(this);
        newPhrase.setMinPitch(minPitch);
        newPhrase.setMaxPitch(maxPitch);
        newPhrase.setQuantized(quantized);
        newPhrase.setInstrument(instrument);
        newPhrase.setKey(key);
        newPhrase.setQuality(quality);
        newPhrase.setQRhythm(qRhythm);
        newPhrase.setBackgroundCol(color);
        for (Note note : notesArray) {
            Note newNote = new Note(note.getId(), note.getPeak(), note.isFiller(), newPhrase);
            newNote.setPitch(note.getPitch());
            newNote.setTranspose(note.getTranspose());
            newNote.setRhythmValue(note.getRhythmValue());
            newNote.setDynamic(note.getDynamic());
            newPhrase.getNotesArray().add(newNote);

        }
        return newPhrase;
    }

    public Phrase copyLoop() {
        Phrase newPhrase = new Phrase();
        newPhrase.setId(id);
        newPhrase.setBackgroundCol("");
        for (Note note : notesArray) {
            Note newNote = new Note(note.getId(), null, false, newPhrase);
            newNote.setPitch(note.getPitch());
            newNote.setRhythmValue(note.getRhythmValue());
            newNote.setDynamic(note.getDynamic());
            newPhrase.getNotesArray().add(newNote);

        }
        return newPhrase;
    }

    public void initialize() {

        if (!loop) {

            /*System.out.println("Initializing phrase...");
            System.out.println("    ID: " + id);
            System.out.println("    Compound name: " + compound.getName());
            System.out.println("    Total peaks in compound: " + compound.getDataChart().getPeaksArray().size());
            System.out.println("    x1: " + x1);
            System.out.println("    x2: " + x2);*/

            int startIndex = 0;
            int endIndex = 0;

            for (Peak p : compound.getDataChart().getPeaksArray()) {
                //System.out.println(p.toString());
                if (x1 < p.getX1() && x1 > p.getX2()) {
                    startIndex = compound.getDataChart().getPeaksArray().indexOf(p);
                    //System.out.println("    First peak index: " + startIndex);
                }
                if (x2 < p.getX1() && x2 > p.getX2()) {
                    endIndex = compound.getDataChart().getPeaksArray().indexOf(p);
                    //System.out.println("    Last peak index: " + endIndex);
                }
            }

            notesArray = new ArrayList<Note>();

            int noteID = 0;
            //System.out.println("    Adding notes:");

            for (int i = startIndex; i < endIndex; i++) {
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

                //System.out.println(newNote.toString());

                notesArray.add(newNote);
            }

            for (Note note : notesArray) {

                note.setRhythmValue(scaleRhythmValue(note.getPeak().getWidth()));
                /*System.out.println("        Note " + note.getId() + " - pitch: " + note.getPitch() + " (" +
                        note.convertPitchToString() + "), rhythm value: " + note.getRhythmValue() + ", peak width: " +
                        note.getPeak().getWidth() + ", dynamic: " + note.getDynamic());*/
            }

            //setX2(notesArray.get(notesArray.size() - 1).getPeak().getX2());

            /*System.out.println("    Highest width: " + highestWidth + ", lowest width: " + lowestWidth);
            System.out.println("    Total notes added: " + notesArray.size());
            System.out.println();
            System.out.println("Phrase initialized.");*/

        }

    }

    public String saveString() {

        int parentId = -1;

        if (parentPhrase != null ) parentId = parentPhrase.getId();

        String saveString = id + "," + instrument + "," + maxPitch + "," + minPitch + "," + currentFillerId + "," + x1 + "," + x2 + "," +
                highestWidth + "," + lowestWidth + "," + quantized + "," + selected + "," + loop + "," + color + "," + key + "," + quality
                + "," + qRhythm + "," + compound.getName() + "," + getStartTime() + "," + parentId;

        return saveString;
    }

    public void addFillerNote(Note fillerNote) {
        for (Note n : getSelectedNotes()) {
            notesArray.add(notesArray.indexOf(n) + 1, fillerNote);
        }
    }

    public void incrementCurrentFillerId() {
        currentFillerId++;
    }

    public double getBeatLength2() {
        double length = 0;

        for (Note n : notesArray) length += n.getRhythmValue();

        return length;
    }

    public ArrayList<Note> getSelectedNotes() {
        ArrayList<Note> selectedNotes = new ArrayList<Note>();

        for (Note n : notesArray){
            if (n.isSelected()) selectedNotes.add(n);
        }

        return selectedNotes;
    }

    public void setSelectedNotes(ArrayList<Note> selectedNotes) {
        for (Note n : selectedNotes){
            for (Note note : notesArray) {
                if (note.getPeak().getId() == n.getPeak().getId()) note.setSelected(true);
            }
        }
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

    public void transposeSelectedNotes(int val) {

        for (Note n : getSelectedNotes()) {
            n.setTranspose(val);
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

    public int getNumMeasures() {
        double number = 0;

        for (Note n : notesArray) number += n.getRhythmValue();

        int num = (int) Math.ceil(number / 4);

        return num;
    }

    public void toggleSelected() {
        selected = !selected;
    }



    public void setBackgroundCol( String colorString ) {

        if (colorString.equals("Red")) {
            unselectedColor = new Color(255, 204, 204);
            borderColor = new Color(204, 0, 0);
            selectedColor = new Color(255, 102, 102);

        } else if (colorString.equals("Orange")) {
            unselectedColor = new Color(255, 229, 204);
            borderColor = new Color(204, 102, 0);
            selectedColor = new Color(255, 178, 102);

        } else if (colorString.equals("Yellow")) {
            unselectedColor = new Color(255, 255, 204);
            borderColor = new Color(204, 204, 0);
            selectedColor = new Color(255, 255, 102);

        } else if (colorString.equals("Green")) {
            unselectedColor = new Color(204, 255, 204);
            borderColor = new Color(0, 204, 0);
            selectedColor = new Color(102, 255, 102);

        } else if (colorString.equals("Blue")) {
            unselectedColor = new Color(204, 229, 255);
            borderColor = new Color(0, 102, 204);
            selectedColor = new Color(102, 178, 255);

        } else if (colorString.equals("Magenta")) {
            unselectedColor = new Color(255, 204, 204);
            borderColor = new Color(204, 0, 204);
            selectedColor = new Color(255, 102, 255);

        } else if (colorString.equals("Cyan")) {
            unselectedColor = new Color(204, 255, 255);
            borderColor = new Color(0, 204, 204);
            selectedColor = new Color(102, 255, 255);

        } else if (colorString.equals("Pink")) {
            unselectedColor = new Color(255, 204, 229);
            borderColor = new Color(204, 0, 102);
            selectedColor = new Color(255, 102, 178);

        } else {
            unselectedColor = new Color(0, 0, 0);
            borderColor = new Color(0, 0, 0);
            selectedColor = new Color(163, 163, 163);
        }
    }

    @Override
    public String toString() {
        return "Phrase{" +
                "id=" + id +
                ", instrument=" + instrument +
                ", minPitch=" + minPitch +
                ", maxPitch=" + maxPitch +
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
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

    public String getQRhythm() {
        return qRhythm;
    }

    public void setQRhythm(String qRhythm) {
        this.qRhythm = qRhythm;
    }

    public File getMidiFile() {
        return midiFile;
    }

    public void setMidiFile(File midiFile) {
        this.midiFile = midiFile;
    }

    public String getqRhythm() {
        return qRhythm;
    }

    public void setqRhythm(String qRhythm) {
        this.qRhythm = qRhythm;
    }

    public Color getUnselectedColor() {
        return unselectedColor;
    }

    public void setUnselectedColor(Color unselectedColor) {
        this.unselectedColor = unselectedColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getCurrentFillerId() {
        return currentFillerId;
    }

    public void setCurrentFillerId(int currentFillerId) {
        this.currentFillerId = currentFillerId;
    }

    public Phrase getParentPhrase() {
        return parentPhrase;
    }

    public void setParentPhrase(Phrase parentPhrase) {
        this.parentPhrase = parentPhrase;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}
