package sonifiedspectra.view;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class LoopDialog extends JDialog {

    private SonifiedSpectra app;

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

    private ArrayList<Phrase> loopsArray;

    public LoopDialog(SonifiedSpectra app) throws IOException, FontFormatException, MidiUnavailableException, UnsupportedAudioFileException, LineUnavailableException, InvalidMidiDataException {
        this.app = app;
        this.loopsArray = new ArrayList<Phrase>();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        playPanel.setLayout(null);

        Font hnt20 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/HelveticaNeue-Thin.otf"))).deriveFont(Font.PLAIN, 20);

        titleLabel.setFont(hnt20);

        createLoops();

        int i = 0;

        for (Phrase p : loopsArray) {
            loopComboBox.addItem("Loop " + (i + 1));
            i++;
        }

        loopComboBox.addItemListener(new LoopComboBoxController(app, app.getActiveProject(), loopComboBox));

        progress = new JSlider();
        progress.setBounds(10, 5, 250, 27);
        playPanel.add(progress);

        Icon playicon = new ImageIcon("resources/icons/playicon.png");
        playButton = new BetterButton(Color.decode("#F5F5F5"), 40, 40, 16);
        playButton.setIcon(playicon);
        playButton.setBounds(90, 50, 40, 40);
        playButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        playButton.setBorderPainted(true);
        playButton.setFocusPainted(false);
        playPanel.add(playButton);

        Icon stopicon = new ImageIcon("resources/icons/stopicon.png");
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
        Phrase phrase = loopsArray.get(loopComboBox.getSelectedIndex());

        boolean done = false;

        for (TrackView tv : app.getTrackViewArray()) {
            if (tv.getTrack().getInstrument() == phrase.getInstrument()) {

                done = true;

                if (app.getSelectedMeasures().size() > 0) {
                    for (int j = 0; j < app.getSelectedMeasures().size(); j++) {
                        Phrase newPhrase = phrase.copy();
                        newPhrase.setId(app.getActiveProject().getCurrentPhraseId());
                        newPhrase.setStartTime(app.getSelectedMeasures().get(j));
                        app.getActiveProject().incrementPhraseId();
                        tv.getTrack().getPhrases().add(newPhrase);
                    }
                }

                else {
                    Phrase newPhrase = phrase;
                    newPhrase.setId(app.getActiveProject().getCurrentPhraseId());
                    app.getActiveProject().incrementPhraseId();
                    tv.getTrack().getPhrases().add(newPhrase);
                }

                tv.initialize();
                for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray()) {
                    pitv.addMouseListener(new PhraseInTrackController(app, app.getActiveProject(), pitv));
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
            newTrack.setInstrument(phrase.getInstrument());
            app.getActiveProject().incrementTrackId();
            app.getActiveProject().getTracksArray().add(newTrack);

            newTrack.getPhrases().add(phrase);

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

            int u = 0;

            tv.initialize();
            for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray()) {
                pitv.setBounds(pitv.getX(), pitv.getY(), pitv.getAdjustedWidth(), pitv.getHeight());
                pitv.addMouseListener(new PhraseInTrackController(app, app.getActiveProject(), pitv));
                RemovePhraseFromTrackController removePhraseFromTrackController = new RemovePhraseFromTrackController(app, app.getActiveProject(), pitv, tv);
                pitv.getRemoveButton().addMouseListener(new HelpTextController(app, HelpStrings.REMOVE_PHRASE_FROM_TRACK));
                pitv.getRemoveButton().addActionListener(removePhraseFromTrackController);
                pitv.getRemoveButton().addMouseListener(removePhraseFromTrackController);
                u++;
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

        app.updateActivePhrase(app.getActivePhrase());
        app.updateIntervalMarker();
        app.getSoundPlayer().reset();
        app.getSoundPlayer().updateSoundPlayer();
        app.getFrame().pack();

        setVisible(false);

        int k1 = 0;

        for (TrackView tv3 : app.getTrackViewArray()) {
            System.out.println("TrackView: " + k1);
            System.out.println("Size of Pitv Array: " + tv3.getPhraseInTrackViewArray().size());
            for (PhraseInTrackView pitv : tv3.getPhraseInTrackViewArray()) pitv.print();
            k1++;
        }
    }

    private void onCancel() {
        setVisible(false);
    }

    public void createLoops() {

        sonifiedspectra.model.Phrase loop1 = new sonifiedspectra.model.Phrase(-1, null, "Black", 0, 0);

        for (int i = 0; i < 4; i++) {
            Note note = new Note(-1, null, false, loop1);
            note.setPitch(60);
            note.setRhythmValue(2);
            note.setDynamic(100);
            loop1.getNotesArray().add(note);
        }
        loop1.setInstrument(50);
        loop1.setLoop(true);

        loopsArray.add(loop1);

        sonifiedspectra.model.Phrase loop2 = new sonifiedspectra.model.Phrase(-1, null, "Black", 0, 0);

        for (int i = 0; i < 8; i++) {
            Note note = new Note(-1, null, false, loop2);
            note.setPitch(50);
            note.setRhythmValue(1);
            note.setDynamic(100);
            loop2.getNotesArray().add(note);
        }
        loop2.setInstrument(50);
        loop2.setLoop(true);

        loopsArray.add(loop2);

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

    public ArrayList<Phrase> getLoopsArray() {
        return loopsArray;
    }

    public void setLoopsArray(ArrayList<Phrase> loopsArray) {
        this.loopsArray = loopsArray;
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
