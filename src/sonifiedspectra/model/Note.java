package sonifiedspectra.model;

/**
 * Created by Hvandenberg on 5/28/15.
 */
public class Note extends jm.music.data.Note {

    private int id;
    private int transpose;

    private Peak peak;
    private Phrase phrase;

    private boolean filler;
    private boolean selected;

    public Note(int id, Peak peak, boolean filler, Phrase phrase) {
        this.id = id;
        this.peak = peak;
        this.filler = filler;
        this.phrase = phrase;
        this.selected = false;
        this.transpose = 0;
    }

    public String convertPitchToString() {

        String[] noteString = new String[] { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };

        int octave = ((this.getPitch() + this.getTranspose()) / 12) - 1;
        int noteIndex = ((this.getPitch() + this.getTranspose()) % 12);
        String note = noteString[noteIndex] + octave;

        return note;

    }

    public void transposeUp() {
        transpose++;
    }

    public void transposeDown() {
        transpose--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (filler != note.filler) return false;
        if (id != note.id) return false;
        if (selected != note.selected) return false;
        if (transpose != note.transpose) return false;
        if (peak != null ? !peak.equals(note.peak) : note.peak != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", transpose=" + transpose +
                ", peak=" + peak +
                ", filler=" + filler +
                ", selected=" + selected +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTranspose() {
        return transpose;
    }

    public void setTranspose(int transpose) {
        this.transpose = transpose;
    }

    public Peak getPeak() {
        return peak;
    }

    public void setPeak(Peak peak) {
        this.peak = peak;
    }

    public boolean isFiller() {
        return filler;
    }

    public void setFiller(boolean filler) {
        this.filler = filler;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void toggleSelected() {
        selected = !selected;
    }

    public Phrase getPhrase() {
        return phrase;
    }

    public void setPhrase(Phrase phrase) {
        this.phrase = phrase;
    }
}
