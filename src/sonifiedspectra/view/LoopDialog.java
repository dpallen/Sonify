package sonifiedspectra.view;

import jm.constants.RhythmValues;
import jm.music.data.*;
import jm.music.data.Note;
import jm.music.data.Phrase;
import jm.music.tools.Mod;
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

    private ArrayList<Part> loopsArray;

    public LoopDialog(Sonify app) throws IOException, FontFormatException, MidiUnavailableException, UnsupportedAudioFileException, LineUnavailableException, InvalidMidiDataException {
        this.app = app;
        this.loopsArray = new ArrayList();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        playPanel.setLayout(null);

        Font hnt20 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/HelveticaNeue-Thin.otf")).deriveFont(Font.PLAIN, 20);

        titleLabel.setFont(hnt20);

        loadLoops(10, 1);

        for (int i = 0; i < 10; i++) {
            loopComboBox.addItem(i + 1);
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

        Part part = loopsArray.get(loopComboBox.getSelectedIndex());

        boolean done = false;

        for (TrackView tv : app.getTrackViewArray()) {
            if (tv.getTrack().isLoop()) {

                done = true;

                if (app.getSelectedMeasures().size() > 0) {
                    for (int j = 0; j < app.getSelectedMeasures().size(); j++) {
                        int index = -1;
                        for (Phrase phrase : part.getPhraseArray()) {
                            sonifiedspectra.model.Phrase newPhrase = new sonifiedspectra.model.Phrase();
                            newPhrase.setId(index);
                            if (app.getSelectedMeasures().get(j) > 0) newPhrase.setStartTime(app.getSelectedMeasures().get(j) - 1);
                            newPhrase.setBackgroundCol("");
                            int i = 0;
                            for (Note note : phrase.getNoteArray()) {
                                sonifiedspectra.model.Note newNote = new sonifiedspectra.model.Note(i, null, false, newPhrase);
                                newNote.setPitch(note.getPitch());
                                newNote.setRhythmValue(note.getRhythmValue());
                                newNote.setDynamic(note.getDynamic());
                                if (newNote.getDynamic() > 0) newPhrase.getNotesArray().add(newNote);
                                i++;
                            }
                            tv.getTrack().getPhrases().add(newPhrase);
                            index--;
                        }
                    }
                }

                else {
                    int index = -1;
                    for (Phrase phrase : part.getPhraseArray()) {
                        sonifiedspectra.model.Phrase newPhrase = new sonifiedspectra.model.Phrase();
                        newPhrase.setId(index);
                        newPhrase.setBackgroundCol("");
                        int i = 0;
                        for (Note note : phrase.getNoteArray()) {
                            sonifiedspectra.model.Note newNote = new sonifiedspectra.model.Note(i, null, false, newPhrase);
                            newNote.setPitch(note.getPitch());
                            newNote.setRhythmValue(note.getRhythmValue());
                            newNote.setDynamic(note.getDynamic());
                            if (newNote.getDynamic() > 0) newPhrase.getNotesArray().add(newNote);
                            i++;
                        }
                        tv.getTrack().getPhrases().add(newPhrase);
                        index--;
                    }
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
            Track newTrack = new Track(app.getActiveProject().getTracksArray().size());
            newTrack.setLoop(true);
            newTrack.setInstrument(128);
            newTrack.setChannel(part.getChannel());
            app.getActiveProject().incrementTrackId();
            app.getActiveProject().getTracksArray().add(newTrack);

            if (app.getSelectedMeasures().size() > 0) {
                for (int j = 0; j < app.getSelectedMeasures().size(); j++) {
                    int index = -1;
                    for (Phrase phrase : part.getPhraseArray()) {
                        sonifiedspectra.model.Phrase newPhrase = new sonifiedspectra.model.Phrase();
                        newPhrase.setId(index);
                        if (app.getSelectedMeasures().get(j) > 0) newPhrase.setStartTime(app.getSelectedMeasures().get(j) - 1);
                        newPhrase.setInstrument(newTrack.getInstrument());
                        newPhrase.setBackgroundCol("");
                        int i = 0;
                        for (Note note : phrase.getNoteArray()) {
                            sonifiedspectra.model.Note newNote = new sonifiedspectra.model.Note(i, null, false, newPhrase);
                            newNote.setPitch(note.getPitch());
                            newNote.setRhythmValue(note.getRhythmValue());
                            newNote.setDynamic(note.getDynamic());
                            if (newNote.getDynamic() > 0) newPhrase.getNotesArray().add(newNote);
                            i++;
                        }
                        newTrack.getPhrases().add(newPhrase);
                        index--;
                    }
                }
            }

            else {
                int index = -1;
                sonifiedspectra.model.Phrase lastPhrase = null;
                for (Phrase phrase : part.getPhraseArray()) {
                    sonifiedspectra.model.Phrase newPhrase = new sonifiedspectra.model.Phrase();
                    if (index < -1) lastPhrase.setParentPhrase(newPhrase);
                    newPhrase.setId(index);
                    newPhrase.setBackgroundCol("");
                    newPhrase.setInstrument(newTrack.getInstrument());
                    int i = 0;
                    for (Note note : phrase.getNoteArray()) {
                        sonifiedspectra.model.Note newNote = new sonifiedspectra.model.Note(i, null, false, newPhrase);
                        newNote.setPitch(note.getPitch());
                        newNote.setRhythmValue(note.getRhythmValue());
                        newNote.setDynamic(note.getDynamic());
                        if (newNote.getDynamic() > 0) newPhrase.getNotesArray().add(newNote);
                        i++;
                    }
                    newTrack.getPhrases().add(newPhrase);
                    lastPhrase = newPhrase;
                    index--;
                }
            }

            for (Phrase p : newTrack.getPhrases()) for (Note n : p.getNoteArray()) System.out.println(n.toString());

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

    public void loadLoops(int num, int repeat) {

        for (int j = 0; j < num; j++) {

            int length = 32;

            // 25 = TR808 General MIDI kit. 9 = MIDI channel 10.
            Part drums = new Part("Drums", 25, 9);

            //create the appropriate length phrases filled up with note objects
            int pitch = 36;
            jm.music.data.Phrase phrBD = phraseFill(length, pitch);
            pitch = 38;
            jm.music.data.Phrase phrSD = phraseFill(length, pitch);
            pitch = 42;
            jm.music.data.Phrase phrHH = phraseFill(length, pitch);
            CPhrase phrEnd = EndPattern();

            // get the on-off values
            for (int i = 0; i < length; i++) {
                if (Math.random() > 0.3) {
                    phrBD.getNote(i).setPitch(sonifiedspectra.model.Note.REST);
                } else phrBD.getNote(i).setPitch(36);
                if (Math.random() > 0.5) {
                    phrSD.getNote(i).setPitch(sonifiedspectra.model.Note.REST);
                } else phrSD.getNote(i).setPitch(38);
                if (Math.random() > 0.8) {
                    phrHH.getNote(i).setPitch(sonifiedspectra.model.Note.REST);
                } else phrHH.getNote(i).setPitch(42);
            }

            // loop the drum pattern
            Mod.repeat(phrBD, repeat);
            Mod.repeat(phrSD, repeat);
            Mod.repeat(phrHH, repeat);

            // add phrases to the instrument (part)
            drums.addPhrase(phrBD);
            drums.addPhrase(phrSD);
            drums.addPhrase(phrHH);
            //drums.addCPhrase(phrEnd);

            loopsArray.add(drums);

        }

    }

    private jm.music.data.Phrase phraseFill(int length, int pitch) {
        jm.music.data.Phrase phrase = new jm.music.data.Phrase(0.0);
        for(int i=0;i<length;i++){
            jm.music.data.Note note = new jm.music.data.Note(pitch, 0.5, (int)(Math.random() * 70 + 50));
            phrase.addNote(note);
        }
        return phrase;
    }

    private CPhrase EndPattern() {
        // make crash ending
        CPhrase cphrase = new CPhrase();
        int[] pitchArray1a = {36,49}; // kick and crash cymbal
        cphrase.addChord(pitchArray1a, RhythmValues.SB);

        return cphrase;
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

    public ArrayList getLoopsArray() {
        return loopsArray;
    }

    public void setLoopsArray(ArrayList midiLoops) {
        this.loopsArray = midiLoops;
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
