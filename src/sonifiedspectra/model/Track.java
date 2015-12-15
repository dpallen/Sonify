package sonifiedspectra.model;

import jm.music.data.Part;

import java.util.ArrayList;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class Track extends Part{

    private ArrayList<Phrase> phrases;
    private boolean selected;
    private boolean live;
    private boolean expanded;
    private int id;
    private boolean loop;

    public Track(int id) {
        this.id = id;
        this.phrases = new ArrayList<Phrase>();
        this.selected = false;
        this.live = true;
        this.expanded = false;
        this.loop = false;
    }

    public String saveString() {
        String saveString = id + "," + selected + "," + live + "," + expanded + "," + getInstrument() + ", " + loop;
        return saveString;
    }

    public void toggleSelected() {
        selected = !selected;
    }

    public void toggleExpanded() {
        expanded = !expanded;
    }

    public ArrayList<Phrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(ArrayList<Phrase> phrases) {
        this.phrases = phrases;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}
