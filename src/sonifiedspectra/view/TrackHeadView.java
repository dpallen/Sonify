package sonifiedspectra.view;

import sonifiedspectra.model.Track;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class TrackHeadView extends JPanel {

    private JComboBox instrumentComboBox;
    private JCheckBox liveCheckbox;
    private JSlider volumeSlider;
    private BetterButton expandButton;

    private Track track;

    public TrackHeadView(Track track) {
        this.track = track;
        this.setLayout(null);

        instrumentComboBox = new JComboBox();
        for (int i = 0; i < 128; i++) instrumentComboBox.addItem(i);
        instrumentComboBox.setSelectedItem(track.getInstrument());
        instrumentComboBox.setBounds(5, 5, 80, 32);
        add(instrumentComboBox);

        liveCheckbox = new JCheckBox();
        liveCheckbox.setSelected(track.isLive());
        liveCheckbox.setBounds(80, 5, 25, 32);
        add(liveCheckbox);

        /*volumeSlider = new JSlider();
        volumeSlider.setBounds(10, 20, 610, 27);
        volumeSlider.setMinimum(0);
        volumeSlider.setMaximum(127);
        volumeSlider.setValue(track);
        add(volumeSlider);*/

        Icon collapsedIcon = new ImageIcon("resources/icons/collapsedicon.png");
        expandButton = new BetterButton(Color.decode("#F5F5F5"), 25, 25, 6);
        expandButton.setIcon(collapsedIcon);
        expandButton.setBounds(110, 7, 25, 25);
        expandButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        expandButton.setBorderPainted(true);
        expandButton.setFocusPainted(false);
        add(expandButton);

        setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));

    }

    public int getExpandedHeight() {

        int expandedHeight = 15 * track.getPhrases().size();
        if (expandedHeight > 70) return expandedHeight;
        else return 70;
    }

    public JComboBox getInstrumentComboBox() {
        return instrumentComboBox;
    }

    public void setInstrumentComboBox(JComboBox instrumentComboBox) {
        this.instrumentComboBox = instrumentComboBox;
    }

    public JCheckBox getLiveCheckbox() {
        return liveCheckbox;
    }

    public void setLiveCheckbox(JCheckBox liveCheckbox) {
        this.liveCheckbox = liveCheckbox;
    }

    public JSlider getVolumeSlider() {
        return volumeSlider;
    }

    public void setVolumeSlider(JSlider volumeSlider) {
        this.volumeSlider = volumeSlider;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public BetterButton getExpandButton() {
        return expandButton;
    }

    public void setExpandButton(BetterButton expandButton) {
        this.expandButton = expandButton;
    }
}
