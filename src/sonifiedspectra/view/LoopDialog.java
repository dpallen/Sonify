package sonifiedspectra.view;

import jm.music.data.*;
import jm.music.data.Phrase;
import jm.util.Read;
import sonifiedspectra.controllers.*;
import sonifiedspectra.model.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class LoopDialog extends JDialog {

    private Sonify app;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox loopComboBox;
    private JLabel titleLabel;
    private JLabel selectedLabel;
    private JPanel playPanel;
    private JLabel measureLabel;
    private JLabel time;

    private BetterButton playButton;
    private BetterButton stopButton;
    private JSlider progress;

    private SoundPlayer loopPlayer;

    private ArrayList<File> midiLoops;

    public LoopDialog(Sonify app) throws IOException, FontFormatException, MidiUnavailableException, UnsupportedAudioFileException, LineUnavailableException, InvalidMidiDataException {
        this.app = app;
        this.midiLoops = new ArrayList<File>();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        playPanel.setLayout(null);

        Font hnt20 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/HelveticaNeue-Thin.otf")).deriveFont(Font.PLAIN, 20);

        titleLabel.setFont(hnt20);

        loadLoops();

        for (File f : midiLoops) {
            loopComboBox.addItem(f.getName());
        }

        loopComboBox.addItemListener(new LoopComboBoxController(app, app.getActiveProject(), loopComboBox));

        progress = new JSlider();
        progress.setBounds(10, 5, 250, 27);
        playPanel.add(progress);

        Icon playicon = new ImageIcon(getClass().getResource("/icons/playicon.png"));
        playButton = new BetterButton(Color.decode("#F5F5F5"), 40, 40, 16);
        playButton.setIcon(playicon);
        playButton.setBounds(90, 50, 40, 40);
        playButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        playButton.setBorderPainted(true);
        playButton.setFocusPainted(false);
        playPanel.add(playButton);

        Icon stopicon = new ImageIcon(getClass().getResource("/icons/stopicon.png"));
        stopButton = new BetterButton(Color.decode("#F5F5F5"), 40, 40, 16);
        stopButton.setIcon(stopicon);
        stopButton.setBounds(145, 50, 40, 40);
        stopButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        stopButton.setBorderPainted(true);
        stopButton.setFocusPainted(false);
        playPanel.add(stopButton);

        time = new JLabel("00:00 out of 00:00");
        time.setBounds(60, 23, 150, 24);
        time.setHorizontalAlignment(SwingConstants.CENTER);
        playPanel.add(time);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    private void onOK() {

        sonifiedspectra.model.Phrase phrase = new sonifiedspectra.model.Phrase();
        Read.midi(phrase, String.valueOf(midiLoops.get(loopComboBox.getSelectedIndex())));
        //phrase.setInstrument(127);

        for (jm.music.data.Note n : phrase.getNoteArray()) System.out.println("Note");
        System.out.println("Heeeeeel");

        boolean done = false;

        for (TrackView tv : app.getTrackViewArray()) {
            if (tv.getTrack().isLoop()) {

                done = true;

                if (app.getSelectedMeasures().size() > 0) {
                    for (int j = 0; j < app.getSelectedMeasures().size(); j++) {
                        sonifiedspectra.model.Phrase newPhrase = phrase.copy();
                        newPhrase.setStartTime(app.getSelectedMeasures().get(j));
                        newPhrase.setBackgroundCol("");
                        newPhrase.setLoop(true);
                        tv.getTrack().getPhrases().add(newPhrase);
                    }
                }

                else {
                    sonifiedspectra.model.Phrase newPhrase = phrase;
                    newPhrase.setBackgroundCol("");
                    newPhrase.setLoop(true);
                    newPhrase.setStartTime(0);
                    tv.getTrack().getPhrases().add(newPhrase);
                }

                tv.initialize();
                for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray()) {
                    pitv.getTopPanel().addMouseListener(new PhraseInTrackController(app, app.getActiveProject(), pitv));
                    pitv.getTopPanel().addMouseListener(new HelpTextController(app, HelpStrings.PITV));
                    RemovePhraseFromTrackController removePhraseFromTrackController = new RemovePhraseFromTrackController(app, app.getActiveProject(), pitv, tv);
                    pitv.getRemoveButton().addMouseListener(new HelpTextController(app, HelpStrings.REMOVE_PHRASE_FROM_TRACK));
                    pitv.getRemoveButton().addActionListener(removePhraseFromTrackController);
                    pitv.getRemoveButton().addMouseListener(removePhraseFromTrackController);
                }
                tv.repaint();
            }
        }

        if (!done) {
            Track newTrack = new Track(app.getActiveProject().getCurrentTrackId());
            newTrack.setLoop(true);
            newTrack.setInstrument(phrase.getInstrument());
            app.getActiveProject().incrementTrackId();
            app.getActiveProject().getTracksArray().add(newTrack);

            sonifiedspectra.model.Phrase newPhrase = phrase;
            newPhrase.setBackgroundCol("");
            newPhrase.setLoop(true);
            newPhrase.setStartTime(0);
            newTrack.getPhrases().add(newPhrase);

            TrackView tv = new TrackView(newTrack, app);
            tv.setBounds(0, 70 * (app.getActiveProject().getTracksArray().size() - 1), 100 * app.getActiveProject().getNumMeasures(), 70);

            app.getTrackViewArray().add(tv);
            app.getInTracksPanel().add(tv);
            tv.repaint();

            TrackHeadView thv = null;
            try {
                thv = new TrackHeadView(newTrack, app.getInstruments());
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (FontFormatException e1) {
                e1.printStackTrace();
            }
            thv.setBounds(0, 70 * (app.getActiveProject().getTracksArray().size() - 1), 150, 70);
            thv.addMouseListener(new HelpTextController(app, HelpStrings.TRACK_HEAD));
            thv.addMouseListener(new TrackHeadController(app, app.getActiveProject(), thv));
            thv.getInstrumentComboBox().addItemListener(new TrackInstrumentController(app, app.getActiveProject(), thv));
            thv.getLiveCheckbox().addActionListener(new TrackLiveController(app, app.getActiveProject(), thv));
            ExpandTrackController expandController = new ExpandTrackController(app, app.getActiveProject(), thv);
            thv.getExpandButton().addActionListener(expandController);
            thv.getExpandButton().addMouseListener(expandController);
            app.getTrackHeadViewArray().add(thv);
            app.getTrackHeadPanel().add(thv);
            thv.repaint();

            app.getTrackHeadPanel().setPreferredSize(new Dimension(app.getTrackHeadPanel().getWidth(),
                    70 * app.getActiveProject().getTracksArray().size()));
            app.getInTracksPanel().setPreferredSize(new Dimension(app.getInTracksPanel().getWidth(),
                    70 * app.getActiveProject().getTracksArray().size()));

            tv.initialize();
            System.out.println(tv.getPhraseInTrackViewArray().size());
            for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray()) {
                pitv.adjustSize(false);
                pitv.getTopPanel().addMouseListener(new PhraseInTrackController(app, app.getActiveProject(), pitv));
                pitv.getTopPanel().addMouseListener(new HelpTextController(app, HelpStrings.PITV));
                RemovePhraseFromTrackController removePhraseFromTrackController = new RemovePhraseFromTrackController(app, app.getActiveProject(), pitv, tv);
                pitv.getRemoveButton().addMouseListener(new HelpTextController(app, HelpStrings.REMOVE_PHRASE_FROM_TRACK));
                pitv.getRemoveButton().addActionListener(removePhraseFromTrackController);
                pitv.getRemoveButton().addMouseListener(removePhraseFromTrackController);
            }
            tv.repaint();
        }

        int j1 = 0;

        for (TrackHeadView thv1 : app.getTrackHeadViewArray()) {
            if (j1 % 2 != 0 && j1 != 0) thv1.setBackColor(Color.decode("#DDDDDD"));
            else thv1.setBackColor(Color.decode("#F5F5F5"));
            thv1.getTrackNumberLabel().setText(String.valueOf(j1+1));
            thv1.updatePanel();
            j1++;
        }

        int j2 = 0;

        for (TrackView tv1 : app.getTrackViewArray()) {
            if (j2 % 2 != 0 && j2 != 0) tv1.setBackColor(Color.decode("#DDDDDD"));
            else tv1.setBackColor(Color.decode("#F5F5F5"));
            tv1.updatePanel();
            j2++;
        }

        app.getSoundPlayer().reset();
        app.getSoundPlayer().updateSoundPlayer();
        app.getFrame().pack();

        setVisible(false);
    }

    private void onCancel() {
        loopPlayer.reset();
        setVisible(false);
    }

    public void loadLoops() {

        File[] directoryListing = new File(app.getActiveProject().getDirectoryPath() + "/Midi/Loops").listFiles();
        if (directoryListing != null) {
            for (File dataFile : directoryListing) {
                if (!dataFile.isHidden()) {
                    System.out.println("Added loop: " + dataFile.getName());
                    midiLoops.add(dataFile);
                }
            }
        }
        else System.out.println("Loop directory is null.");

    }

    public JComboBox getLoopComboBox() {
        return loopComboBox;
    }

    public void setLoopComboBox(JComboBox loopComboBox) {
        this.loopComboBox = loopComboBox;
    }

    public JLabel getSelectedLabel() {
        return selectedLabel;
    }

    public void setSelectedLabel(JLabel selectedLabel) {
        this.selectedLabel = selectedLabel;
    }

    public ArrayList<File> getLoopsArray() {
        return midiLoops;
    }

    public void setLoopsArray(ArrayList<File> midiLoops) {
        this.midiLoops = midiLoops;
    }

    public JLabel getMeasureLabel() {
        return measureLabel;
    }

    public void setMeasureLabel(JLabel measureLabel) {
        this.measureLabel = measureLabel;
    }

    public BetterButton getPlayButton() {
        return playButton;
    }

    public void setPlayButton(BetterButton playButton) {
        this.playButton = playButton;
    }

    public BetterButton getStopButton() {
        return stopButton;
    }

    public void setStopButton(BetterButton stopButton) {
        this.stopButton = stopButton;
    }

    public JSlider getProgress() {
        return progress;
    }

    public void setProgress(JSlider progress) {
        this.progress = progress;
    }

    public JLabel getTime() {
        return time;
    }

    public void setTime(JLabel time) {
        this.time = time;
    }

    public SoundPlayer getLoopPlayer() {
        return loopPlayer;
    }

    public void setLoopPlayer(SoundPlayer loopPlayer) {
        this.loopPlayer = loopPlayer;
    }
}
