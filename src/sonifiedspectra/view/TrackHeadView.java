package sonifiedspectra.view;

import sonifiedspectra.model.Track;

import javax.sound.midi.Instrument;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class TrackHeadView extends JPanel {

    private JComboBox instrumentComboBox;
    private JCheckBox liveCheckbox;
    private JSlider volumeSlider;
    private BetterButton expandButton;
    private Color backColor;
    private JLabel trackNumberLabel;

    private Track track;

    public TrackHeadView(Track track, Instrument instruments[]) throws IOException, FontFormatException {
        this.track = track;
        this.setLayout(null);

        backColor = Color.decode("#F5F5F5");

        instrumentComboBox = new JComboBox();
        for (int i = 0; i < instruments.length; i++) instrumentComboBox.addItem(instruments[i].getName());
        instrumentComboBox.setSelectedIndex(track.getInstrument());
        instrumentComboBox.setBounds(5, 5, 140, 32);
        instrumentComboBox.setFont(new Font(instrumentComboBox.getFont().getName(), Font.PLAIN, 10));
        add(instrumentComboBox);

        Font hnt20 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/HelveticaNeue-Thin.otf")).deriveFont(Font.PLAIN, 20);
        trackNumberLabel = new JLabel("0");
        trackNumberLabel.setFont(hnt20);
        trackNumberLabel.setBounds(0, 30, 40, 40);
        trackNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(trackNumberLabel);

        liveCheckbox = new JCheckBox();
        liveCheckbox.setSelected(track.isLive());
        liveCheckbox.setBounds(80, 35, 25, 32);
        add(liveCheckbox);

        /*volumeSlider = new JSlider();
        volumeSlider.setBounds(10, 20, 610, 27);
        volumeSlider.setMinimum(0);
        volumeSlider.setMaximum(127);
        volumeSlider.setValue(track);
        add(volumeSlider);*/

        Icon collapsedIcon = new ImageIcon(getClass().getResource("/icons/collapsedicon.png"));
        expandButton = new BetterButton(Color.decode("#F5F5F5"), 25, 25, 6);
        expandButton.setIcon(collapsedIcon);
        expandButton.setBounds(110, 38, 25, 25);
        expandButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        expandButton.setBorderPainted(true);
        expandButton.setFocusPainted(false);
        add(expandButton);

        setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));

    }

    public void updatePanel() {
        if (track.isSelected()) {
            setBackground(Color.decode("#B8B8B8"));
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, false));
        }
        else {
            setBackground(backColor);
            setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
        }
    }

    public int getExpandedHeight() {

        if (track.isExpanded()) return 200;
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

    public Color getBackColor() {
        return backColor;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }

    public JLabel getTrackNumberLabel() {
        return trackNumberLabel;
    }

    public void setTrackNumberLabel(JLabel trackNumberLabel) {
        this.trackNumberLabel = trackNumberLabel;
    }
}
