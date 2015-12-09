package sonifiedspectra.model;

/**
 * Created by Hvandenberg on 2/19/15.
 */

import jm.constants.Scales;
import jm.music.data.Part;
import jm.music.data.Score;
import jm.music.tools.Mod;
import jm.util.Write;
import sonifiedspectra.view.BetterButton;
import sonifiedspectra.view.Sonify;
import sonifiedspectra.view.TrackHeadView;

import javax.sound.midi.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * This class is a Swing component that can load and play a sound clip,
 * displaying progress and controls.  The main( ) method is a test program.
 * This component can play sampled audio or MIDI files, but handles them
 * differently. For sampled audio, time is reported in microseconds, tracked in
 * milliseconds and displayed in seconds and tenths of seconds. For midi
 * files time is reported, tracked, and displayed in MIDI "ticks".
 * This program does no transcoding, so it can only play sound files that use
 * the PCM encoding.
 */
public class SoundPlayer {

    private Sequence sequence;       // The contents of a MIDI file
    private Sequencer sequencer;     // We play MIDI Sequences with a Sequencer
    private boolean playing = false; // whether the sound is currently playing

    // Length and position of the sound are measured in milliseconds for
    // sampled sounds and MIDI "ticks" for MIDI sounds
    private int audioLength;         // Length of the sound.
    private int audioPosition = 0;   // Current position within the sound

    // The following fields are for the GUI
    private BetterButton play;             // The Play/Pause button
    private BetterButton stop;
    private JSlider progress;         // Shows and sets current position in sound
    private JLabel time;              // Displays audioPosition as a number
    private Timer timer;              // Updates slider every 100 milliseconds
    private JSlider tempo;

    private Sonify app;

    // Create a SoundPlayer component for the specified file.
    public SoundPlayer(File f, int programChange, Sonify app, boolean loop)
            throws IOException,
            UnsupportedAudioFileException,
            LineUnavailableException,
            MidiUnavailableException,
            InvalidMidiDataException {
        this.app = app;
        // First, get a Sequencer to play sequences of MIDI events
        // That is, to send events to a Synthesizer at the right time.
        sequencer = MidiSystem.getSequencer();  // Used to play sequences
        sequencer.open();                       // Turn it on.

        // Get a Synthesizer for the Sequencer to send notes to
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();  // acquire whatever resources it needs

        // The Sequencer obtained above may be connected to a Synthesizer
        // by default, or it may not.  Therefore, we explicitly connect it.
        Transmitter transmitter = sequencer.getTransmitter();
        Receiver receiver = synth.getReceiver();
        transmitter.setReceiver(receiver);

        // Read the sequence from the file and tell the sequencer about it
        sequence = MidiSystem.getSequence(f);
        sequencer.setSequence(sequence);
        audioLength = (int) sequence.getTickLength(); // Get sequence length

        if (!loop) {
            // Now create the basic GUI
            play = app.getPlayButton();                // Play/stop button
            progress = app.getPlaybackSlider(); // Shows position in sound
            progress.setMinimum(0);
            progress.setMaximum(audioLength);
            progress.setValue(0);
            stop = app.getStopButton();
            time = app.getTimerLabel();
            time.setText((getTimeString((int) sequencer.getMicrosecondPosition(), (int) sequencer.getMicrosecondLength())));
        }

        else {
            // Now create the basic GUI
            play = app.getLoopDialog().getPlayButton();                // Play/stop button
            progress = app.getLoopDialog().getProgress(); // Shows position in sound
            progress.setMinimum(0);
            progress.setMaximum(audioLength);
            progress.setValue(0);
            stop = app.getLoopDialog().getStopButton();
            time = app.getLoopDialog().getTime();
            time.setText((getTimeString((int) sequencer.getMicrosecondPosition(), (int) sequencer.getMicrosecondLength())));
        }

        // When clicked, start or pause playing the sound
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (playing) {
                    stop();
                    Icon playIcon = new ImageIcon(getClass().getResource("/icons/playicon.png"));
                    play.setIcon(playIcon);
                } else {
                    play();
                    Icon pauseIcon = new ImageIcon(getClass().getResource("/icons/pauseicon.png"));
                    play.setIcon(pauseIcon);
                }
            }
        });

        final Sonify app2 = app;
        final boolean loop2 = loop;
        // When clicked, stop and reset playing the sound
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
                Icon playIcon = new ImageIcon("resources/icons/playicon.png");
                play.setIcon(playIcon);
                if (!loop2) tempo.setValue(app2.getActiveProject().getTempo());
            }
        });

        // Whenever the slider value changes, first update the time label.
        // Next, if we're not already at the new position, skip to it.
        progress.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = progress.getValue();
                // Update the time label
                time.setText(getTimeString((int) sequencer.getMicrosecondPosition(), (int) sequencer.getMicrosecondLength()));
                // If we're not already there, skip there.
                if (value != audioPosition) skip(value);
            }
        });

        // This timer calls the tick( ) method 10 times a second to keep
        // our slider in sync with the music.
        timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });

        // Now add additional controls based on the type of the sound
        if (!loop) {addMidiControls();
        app.getPlaybackPanel().add(time);}
    }

    /** Start playing the sound at the current position */
    public void play( ) {
        sequencer.start();
        timer.start();
        playing = true;
    }

    /** Stop playing the sound, but retain the current position */
    public void stop( ) {
        timer.stop();
        sequencer.stop( );
        playing = false;
    }

    /** Stop playing the sound and reset the position to 0 */
    public void reset( ) {
        stop( );
        sequencer.setTickPosition(0);
        audioPosition = 0;
        progress.setValue(0);
        Icon playIcon = new ImageIcon(getClass().getResource("/icons/playicon.png"));
        app.getPlayButton().setIcon(playIcon);
        app.getPlayButton().repaint();
        app.getLoopDialog().getPlayButton().setIcon(playIcon);
        app.getLoopDialog().getPlayButton().repaint();
    }

    /** Skip to the specified position */
    public void skip(int position) { // Called when user drags the slider
        if (position < 0 || position > audioLength) return;
        audioPosition = position;
        sequencer.setTickPosition(position);
        progress.setValue(position); // in case skip( ) is called from outside
    }

    /** Return the length of the sound in ms or ticks */
    public int getLength( ) { return audioLength; }

    // An internal method that updates the progress bar.
    // The Timer object calls it 10 times a second.
    // If the sound has finished, it resets to the beginning
    public void tick( ) {
        if (sequencer.isRunning( )) {
            audioPosition = (int)sequencer.getTickPosition( );
            progress.setValue(audioPosition);
            app.getPlaybackLine().setX(app.getPlaybackLine().getX() + 5);
            app.getPlaybackLine().repaint();
        }
        else {
            reset();
        }
    }

    public String getTimeString(int microsecondProgress, int microsecondTotal) {
        int progressSec = microsecondProgress / 1000000;
        int progressMin = (int) Math.floor(progressSec / 60);
        int leftSec = progressSec % 60;

        int totalSec = microsecondTotal / 1000000;
        int totalMin = (int) Math.floor(totalSec / 60);
        int leftSec2 = totalSec % 60;

        String timeText = String.format("%02d", progressMin) + ":" + String.format("%02d", leftSec) +
                " out of " + String.format("%02d", totalMin) + ":" + String.format("%02d", leftSec2) ;
        return timeText;
    }

    // For Midi files, create a JSlider to control the tempo,
    // and create JCheckBoxes to mute or solo each MIDI track.
    void addMidiControls( ) {
        // Add a slider to control the tempo
        tempo = new JSlider(40, 255);
        tempo.setValue((int) (app.getActiveProject().getTempo()));
        java.util.Hashtable labels = new java.util.Hashtable( );
        labels.put(new Integer(40), new JLabel("40"));
        labels.put(new Integer(120), new JLabel("120"));
        labels.put(new Integer(255), new JLabel("255"));
        tempo.setLabelTable(labels);
        tempo.setPaintLabels(true);
        // The event listener actually changes the tempo
        tempo.addChangeListener(new ChangeListener( ) {
            public void stateChanged(ChangeEvent e) {
                sequencer.setTempoInBPM(tempo.getValue());
                app.getTempoTextField().setText(String.valueOf(tempo.getValue()));
            }
        });
        tempo.setBounds(507, 75, 168, 50);
        app.getPlaybackPanel().add(tempo);

    }

    public void updateLoopPlayer(Phrase phrase) {

        Score score = new Score();
        Part newPart = new Part();
        jm.music.data.Phrase newPhrase = new jm.music.data.Phrase();
        for (Note note : phrase.getNotesArray()) {
            jm.music.data.Note newNote = new jm.music.data.Note(note.getPitch() + note.getTranspose(), note.getRhythmValue(), note.getDynamic());
            newPhrase.add(newNote);
        }
        newPhrase.setInstrument(phrase.getInstrument());
        newPart.setInstrument(phrase.getInstrument());
        newPart.add(newPhrase);
        score.add(newPart);

        score.setTempo(120);

        Write.midi(score, "midi/loop.mid");

        File midiFile = new File( "midi/loop.mid" );   // This is the file we'll be playing

        phrase.setMidiFile(midiFile);

        // Read the sequence from the file and tell the sequencer about it
        try {
            setSequence(MidiSystem.getSequence(midiFile));
        } catch (InvalidMidiDataException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            getSequencer().setSequence(getSequence());
        } catch (InvalidMidiDataException e1) {
            e1.printStackTrace();
        }
        setAudioLength((int) getSequence().getTickLength()); // Get sequence length

        // Now create the basic GUI
        getProgress().setMinimum(0);
        getProgress().setMaximum(getAudioLength());
        getProgress().setValue(0);
        getTime().setText((getTimeString((int)
                        getSequencer().getMicrosecondPosition(),
                (int) getSequencer().getMicrosecondLength())));

        // This timer calls the tick( ) method 10 times a second to keep
        // our slider in sync with the music.
        setTimer(new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        }));
    }

    public void updateSoundPlayer() {
        System.out.println("Sampling phrase " + app.getActivePhrase().getId() + "...");

        int[] scale = getScale(String.valueOf(app.getQualityComboBox().getSelectedItem()));
        double rhythm = getQuantizeRhythmValue(String.valueOf(app.getQrhythmComboBox().getSelectedItem()));
        int root = getRoot(String.valueOf(app.getKeyComboBox().getSelectedItem()));

        Score score = new Score();
        int channel = 0;
        if (app.isProject()) {
            for (sonifiedspectra.model.Track track : app.getActiveProject().getTracksArray()) {
                Part newPart = new Part("", 0, channel);
                for (sonifiedspectra.model.Phrase phrase : track.getPhrases()) {
                    jm.music.data.Phrase newPhrase = new jm.music.data.Phrase();
                    for (Note note : phrase.getNotesArray()) {
                        jm.music.data.Note newNote = new jm.music.data.Note(note.getPitch() + note.getTranspose(), note.getRhythmValue(), note.getDynamic());
                        newPhrase.add(newNote);
                    }
                    newPhrase.setInstrument(track.getInstrument());
                    newPhrase.setStartTime(phrase.getStartTime() * 4);
                    newPart.setInstrument(track.getInstrument());
                    newPart.add(newPhrase);
                }
                score.add(newPart);
                channel++;
            }
        }

        else {
            Part newPart = new Part();
            jm.music.data.Phrase newPhrase = new jm.music.data.Phrase();
            for (Note note : app.getActivePhrase().getNotesArray()) {
                jm.music.data.Note newNote = new jm.music.data.Note(note.getPitch() + note.getTranspose(), note.getRhythmValue(), note.getDynamic());
                newPhrase.add(newNote);
            }
            newPhrase.setInstrument(app.getActivePhrase().getInstrument());
            newPart.setInstrument(app.getActivePhrase().getInstrument());
            newPart.add(newPhrase);
            score.add(newPart);
        }

        score.setTempo(app.getActiveProject().getTempo());

        if (app.getQuantizeCheckBox().isSelected()) {

            Mod.quantize(score, rhythm, scale, root);

        }

        /*System.out.println("Printing sampled notes...");
        for (Part part : score.getPartArray()) {
            System.out.println("Part: " + part.getInstrument());
            for (jm.music.data.Phrase phrase  : part.getPhraseArray()) {
                System.out.println("    Phrase: " + phrase.getInstrument());
                for (jm.music.data.Note note : phrase.getNoteArray()) {
                    System.out.println("       Note: pitch = " + note.getPitch() + ", rhythm = " + note.getRhythmValue() +
                            ", dynamic = " + note.getDynamic());
                }
            }
        }
        System.out.println("End printing.");*/

        Write.midi(score, app.getActiveProject().getDirectoryPath() + "/Midi/active.mid");

        File midiFile = new File( app.getActiveProject().getDirectoryPath() + "/Midi/active.mid" );   // This is the file we'll be playing

        app.getActivePhrase().setMidiFile(midiFile);

        // Read the sequence from the file and tell the sequencer about it
        try {
            setSequence(MidiSystem.getSequence(midiFile));
        } catch (InvalidMidiDataException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            getSequencer().setSequence(getSequence());
        } catch (InvalidMidiDataException e1) {
            e1.printStackTrace();
        }
        setAudioLength((int) getSequence().getTickLength()); // Get sequence length

        // Now create the basic GUI
        getProgress().setMinimum(0);
        getProgress().setMaximum(getAudioLength());
        getProgress().setValue(0);
        getTime().setText((getTimeString((int)
                        getSequencer().getMicrosecondPosition(),
                (int) getSequencer().getMicrosecondLength())));

        // This timer calls the tick( ) method 10 times a second to keep
        // our slider in sync with the music.
        setTimer(new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        }));

        int j = 0;
        for (TrackHeadView thv : app.getTrackHeadViewArray()) {
            final int trackNum = j;
            final TrackHeadView thv2 = thv;
            thv2.getLiveCheckbox().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (app.isProject()) sequencer.setTrackMute(trackNum + 1, !thv2.getLiveCheckbox().isSelected());
                }
            });
            sequencer.setTrackMute(trackNum + 1, !thv2.getLiveCheckbox().isSelected());
            j++;
        }
    }

    public int[] getScale(String scale) {

        if (scale.equals("Aeolian")) return Scales.AEOLIAN_SCALE;
        if (scale.equals("Blues")) return Scales.BLUES_SCALE;
        if (scale.equals("Chromatic")) return Scales.CHROMATIC_SCALE;
        if (scale.equals("Diatonic Minor")) return Scales.DIATONIC_MINOR_SCALE;
        if (scale.equals("Dorian")) return Scales.DORIAN_SCALE;
        if (scale.equals("Harmonic Minor")) return Scales.HARMONIC_MINOR_SCALE;
        if (scale.equals("Indian")) return Scales.INDIAN_SCALE;
        if (scale.equals("Lydian")) return Scales.LYDIAN_SCALE;
        if (scale.equals("Major")) return Scales.MAJOR_SCALE;
        if (scale.equals("Melodic Minor")) return Scales.MELODIC_MINOR_SCALE;
        if (scale.equals("Minor")) return Scales.MINOR_SCALE;
        if (scale.equals("Mixolydian")) return Scales.MIXOLYDIAN_SCALE;
        if (scale.equals("Natural Minor")) return Scales.NATURAL_MINOR_SCALE;
        if (scale.equals("Pentatonic")) return Scales.PENTATONIC_SCALE;
        else return Scales.TURKISH_SCALE;

    }

    /**
     * Translates Rhythm Values
     *
     * @param root string representing root pitch
     * @return int representing MIDI pitch of root
     */
    public int getRoot(String root) {

        if (root.equals("C")) return 0;
        if (root.equals("C" + "\u266f" + "/D" + "\u266d")) return 1;
        if (root.equals("D")) return 2;
        if (root.equals("D" + "\u266f" + "/E" + "\u266d")) return 3;
        if (root.equals("E/F" + "\u266d")) return 4;
        if (root.equals("F")) return 5;
        if (root.equals("F" + "\u266f" + "/G" + "\u266d")) return 6;
        if (root.equals("G")) return 7;
        if (root.equals("G" + "\u266f" + "/A" + "\u266d")) return 8;
        if (root.equals("A")) return 9;
        if (root.equals("A" + "\u266f" + "/B" + "\u266d")) return 10;
        else return 11;

    }

    /**
     * Translates Rhythm Values
     *
     * @param value string representing rhythm value
     * @return double representing rhythm value
     */
    public double getQuantizeRhythmValue(String value) {

        if (value.equals("1")) return 4.0;
        if (value.equals("1/2")) return 2.0;
        if (value.equals("1/4")) return 1.0;
        if (value.equals("1/8")) return 0.5;
        else return 0.25;

    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Sequencer getSequencer() {
        return sequencer;
    }

    public void setSequencer(Sequencer sequencer) {
        this.sequencer = sequencer;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getAudioLength() {
        return audioLength;
    }

    public void setAudioLength(int audioLength) {
        this.audioLength = audioLength;
    }

    public int getAudioPosition() {
        return audioPosition;
    }

    public void setAudioPosition(int audioPosition) {
        this.audioPosition = audioPosition;
    }

    public BetterButton getPlay() {
        return play;
    }

    public void setPlay(BetterButton play) {
        this.play = play;
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

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public Sonify getApp() {
        return app;
    }

    public void setApp(Sonify app) {
        this.app = app;
    }
}
