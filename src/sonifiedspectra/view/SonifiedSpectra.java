package sonifiedspectra.view;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.XYPlot;
import sonifiedspectra.controllers.*;
import sonifiedspectra.model.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Hvandenberg on 5/28/15.
 */
public class SonifiedSpectra {

    private BetterButton importCompoundButton;
    private JButton importCompoundButton2;
    private BetterButton colorButton;
    private JButton colorButton2;
    private BetterButton editCompoundButton;
    private JButton editCompoundButton2;
    private BetterButton newButton;
    private JButton newButton2;
    private BetterButton openButton;
    private JButton openButton2;
    private BetterButton saveButton;
    private JButton saveButton2;
    private BetterButton settingsButton;
    private JButton settingsButton2;
    private BetterButton transposeUpButton;
    private JButton transposeUpButton2;
    private BetterButton transposeDownButton;
    private JButton transposeDownButton2;
    private BetterButton fillerButton;
    private JButton fillerButton2;
    private BetterButton addPhraseButton;
    private JButton addPhraseButton2;
    private BetterButton editPhraseButton;
    private BetterButton removePhraseButton;
    private JButton removePhraseButton2;
    private BetterButton playProjectButton;
    private JButton playProjectButton2;
    private BetterButton playPhraseButton;
    private JButton playPhraseButton2;
    private BetterButton playButton;
    private JButton playButton2;
    private BetterButton pauseButton;
    private JButton pauseButton2;
    private BetterButton stopButton;
    private JButton stopButton2;
    private BetterButton loopButton;
    private BetterButton addTrackButton;
    private BetterButton removeTrackButton;

    private Color buttonHighlightColor;
    private Color buttonBackgroundColor;

    private JPanel outTracksPanel;
    private JPanel measureHeadPanel;
    private JPanel trackHeadPanel;
    private JPanel inTracksPanel;
    private JPanel graphPanel;
    private JPanel notesPanel;
    private JPanel phrasesPanel;
    private JPanel rootPanel;
    private JPanel playbackPanel;

    private JScrollPane trackHeadScrollPane;
    private JScrollPane measureHeadScrollPane;
    private JScrollPane tracksScrollPane;

    private ChartPanel chPanel;

    private JComboBox compoundComboBox;
    private JComboBox instrumentComboBox;
    private JComboBox keyComboBox;
    private JComboBox qualityComboBox;
    private JComboBox qrhythmComboBox;

    private JTextField titleTextField;
    private JTextField transposeTextField;
    private JTextField minTextField;
    private JTextField maxTextField;
    private JTextField tempoTextField;

    private JSlider playbackSlider;

    private JCheckBox quantizeCheckBox;
    private JCheckBox multipleSelectionCheckBox;
    private JCheckBox leftOrRightCheckbox;

    private JLabel spectrumLabel;
    private JLabel helpTextLabel;
    private JLabel timerLabel;
    private JLabel compoundLabel;
    private JLabel notesLabel;
    private JLabel phrasesLabel;
    private JLabel transposeLabel;
    private JLabel projectLabel;
    private JLabel playbackLabel;

    private JTextPane helpTextPane;

    private JFrame frame;
    private Phrase activePhrase;
    private Project activeProject;

    private JPanel mainView;
    private EditCompoundView editCompoundView;
    private JPanel fillerNotesView;
    private JPanel loopPanel;

    private ArrayList<NoteView> noteViewArray;
    private ArrayList<PhraseView> phraseViewArray;
    private ArrayList<TrackHeadView> trackHeadViewArray;
    private ArrayList<TrackView> trackViewArray;
    private ArrayList<MeasureHeadView> measureHeadViewArray;

    private ArrayList<String> colorsArray;
    private ArrayList<String> keysArray;
    private ArrayList<String> qualityArray;
    private ArrayList<String> rhythmArray;

    private SoundPlayer soundPlayer;
    private boolean isProject;

    // Prevents calling combobox listener infinitely when switching phrases
    private boolean temp;

    public SonifiedSpectra() throws FileNotFoundException, FontFormatException, IOException, MidiUnavailableException,
            UnsupportedAudioFileException, LineUnavailableException, InvalidMidiDataException {

        initialize();

    }

    private void initialize() throws FileNotFoundException, FontFormatException,
            IOException, MidiUnavailableException, UnsupportedAudioFileException,
            LineUnavailableException, InvalidMidiDataException {
        initializeModel();
        initializeView();
        initializeControllers();

        soundPlayer = new SoundPlayer(new File( "midi/starwars.mid" ), activePhrase.getInstrument(), this);

        updateActivePhrase(activePhrase);
        updateIntervalMarker();

    }

    private void initializeModel() {

        activeProject = new Project();
        activeProject.setName("My Project");

        colorsArray = new ArrayList<String>();
        colorsArray.add("Red");
        colorsArray.add("Orange");
        colorsArray.add("Yellow");
        colorsArray.add("Green");
        colorsArray.add("Blue");
        colorsArray.add("Magenta");
        colorsArray.add("Cyan");
        colorsArray.add("Pink");

        keysArray = new ArrayList<String>();
        qualityArray = new ArrayList<String>();
        rhythmArray = new ArrayList<String>();

        String[] keyOptions = { "None", "C", "C" + "\u266f" + "/D" + "\u266d", "D", "D" + "\u266f" + "/E" + "\u266d", "E/F" + "\u266d", "F", "F" + "\u266f" + "/G" + "\u266d", "G", "G" + "\u266f" + "/A" + "\u266d", "A", "A" + "\u266f" + "/B" + "\u266d", "B/C" + "\u266d" };
        for (String keyOption : keyOptions) {

            keysArray.add(keyOption);

        }

        String[] qualityOptions = {"Aeolian", "Blues", "Chromatic", "Diatonic Minor", "Dorian", "Harmonic Minor", "Indian", "Lydian", "Major", "Melodic Minor", "Minor", "Mixolydian", "Natural Minor", "Pentatonic", "Turkish"};
        for (String qualityOption : qualityOptions) {

            qualityArray.add(qualityOption);

        }

        String[] rhythmValueOptions = {"1", "1/2", "1/4", "1/8", "1/16"};
        for (String rhythmValueOption : rhythmValueOptions) {

            rhythmArray.add(rhythmValueOption);

        }

        int i = 0;

        File dir = new File("compounds/");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File dataFile : directoryListing) {
                Compound compound = new Compound(i, dataFile, "Infrared");
                compound.load();
                compound.getDataChart().createChart();
                compound.setPeaks(compound.getDataChart().process());
                compoundComboBox.addItem(compound.getName());
                activeProject.getCompoundsArray().add(compound);
                i++;
            }
        } else {
        }

        Phrase phrase = new Phrase(0, activeProject.getCompoundsArray().get(0), colorsArray.get(0), 600, 1500);
        phrase.initialize();
        activeProject.getPhrasesArray().add(phrase);
        activePhrase = phrase;

        Phrase phrase2 = new Phrase(1, activeProject.getCompoundsArray().get(1), colorsArray.get(1), 1234, 2558);
        phrase2.initialize();
        phrase2.setInstrument(47);
        phrase2.setStartTime(5);
        activeProject.getPhrasesArray().add(phrase2);

        Track track1 = new Track(0);
        track1.setInstrument(0);
        activeProject.getTracksArray().add(track1);
        track1.getPhrases().add(phrase);

        Track track2 = new Track(1);
        track2.setInstrument(47);
        track2.setLive(false);
        track2.getPhrases().add(phrase2);
        activeProject.getTracksArray().add(track2);

        Track track3 = new Track(2);
        track3.setInstrument(2);
        activeProject.getTracksArray().add(track3);

    }

    private void initializeView() throws FileNotFoundException, FontFormatException, IOException {

        frame = new JFrame();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Sonified Spectra - Musical Spectroscopic Analysis");
        frame.setSize(1185, 800);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setUndecorated(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getRootPane().setBorder(
                BorderFactory.createMatteBorder(4, 4, 4, 4,
                        Color.decode("#979797")));
        frame.getContentPane().setBackground(Color.decode("#E5E5E5"));

        this.mainView = (JPanel) frame.getContentPane();
        mainView.setPreferredSize(new Dimension(1280, 800));

        buttonBackgroundColor = Color.decode("#F5F5F5");
        buttonHighlightColor = Color.decode("#CAEFFF");
        isProject = true;

        compoundLabel = new JLabel("Compound:");
        Font hnt20 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/HelveticaNeue-Thin.otf"))).deriveFont(Font.PLAIN, 20);
        compoundLabel.setFont(hnt20);
        compoundLabel.setBounds(20, 15, 101, 24);
        frame.getContentPane().add(compoundLabel);

        compoundComboBox = new JComboBox();
        /*Font hnt12 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/HelveticaNeue-Thin.otf"))).deriveFont(Font.PLAIN, 12);
        compoundComboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        compoundComboBox.setForeground(Color.decode("#000000"));
        compoundComboBox.setBackground(Color.decode("#F5F5F5"));
        compoundComboBox.setFont(hnt12);*/
        //compoundComboBox.setRenderer(new SSComboBoxRenderer());
        for (Compound c : activeProject.getCompoundsArray()) {
            compoundComboBox.addItem(c.getName());
        }
        compoundComboBox.setBounds(123, 11, 158, 32);
        frame.getContentPane().add(compoundComboBox);

        spectrumLabel = new JLabel("Spectrum");
        Font hnt14 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/HelveticaNeue-Thin.otf"))).deriveFont(Font.PLAIN, 14);
        spectrumLabel.setText(activePhrase.getCompound().getSpectrumType());
        spectrumLabel.setBounds(289, 18, 75, 20);
        frame.getContentPane().add(spectrumLabel);

        leftOrRightCheckbox = new JCheckBox();
        Icon leftIcon = new ImageIcon("resources/icons/leftcheckbox.png");
        Icon rightIcon = new ImageIcon("resources/icons/rightcheckbox.png");
        leftOrRightCheckbox.setIcon(leftIcon);
        leftOrRightCheckbox.setSelectedIcon(rightIcon);
        leftOrRightCheckbox.setSelected(false);
        leftOrRightCheckbox.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        leftOrRightCheckbox.setBounds(406, 11, 32, 32);
        frame.getContentPane().add(leftOrRightCheckbox);

        Icon importIcon = new ImageIcon("resources/icons/importcompound.png");
        importCompoundButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        importCompoundButton.setIcon(importIcon);
        importCompoundButton.setBounds(445, 11, 32, 32);
        importCompoundButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        importCompoundButton.setBorderPainted(true);
        importCompoundButton.setFocusPainted(false);
        frame.getContentPane().add(importCompoundButton);

        Icon editIcon = new ImageIcon("resources/icons/editcompound.png");
        editCompoundButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        editCompoundButton.setIcon(editIcon);
        editCompoundButton.setBounds(486, 11, 32, 32);
        editCompoundButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        editCompoundButton.setBorderPainted(true);
        editCompoundButton.setFocusPainted(false);
        frame.getContentPane().add(editCompoundButton);

        chPanel = new ChartPanel(activePhrase.getCompound().getDataChart().getDataChart());
        chPanel.setPreferredSize(new Dimension(500, 500));
        chPanel.setVisible(true);
        chPanel.setBounds(0, 0, 500, 400);
        chPanel.setDomainZoomable(true);
        graphPanel.setLayout(new BorderLayout());
        graphPanel.setBounds(20, 52, 500, 400);
        graphPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        graphPanel.removeAll();
        graphPanel.add(chPanel, BorderLayout.CENTER);
        frame.getContentPane().add(graphPanel);

        notesLabel = new JLabel("Notes:");
        notesLabel.setFont(hnt20);
        notesLabel.setBounds(20, 460, 60, 24);
        frame.getContentPane().add(notesLabel);

        multipleSelectionCheckBox = new JCheckBox();
        Icon selicon;
        if (activeProject.isNotesPanelMultipleSelection()) selicon = new ImageIcon("resources/icons/multseleccheckboxselected.png");
        else selicon = new ImageIcon("resources/icons/multseleccheckbox.png");
        multipleSelectionCheckBox.setIcon(selicon);
        multipleSelectionCheckBox.setSelected(activeProject.isNotesPanelMultipleSelection());
        multipleSelectionCheckBox.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        multipleSelectionCheckBox.setBounds(235, 457, 32, 32);
        frame.getContentPane().add(multipleSelectionCheckBox);

        transposeLabel = new JLabel("Transpose:");
        //transposeLabel.setFont(hnt14);
        transposeLabel.setBounds(270, 468, 75, 20);
        frame.getContentPane().add(transposeLabel);

        transposeTextField = new JTextField();
        transposeTextField.setHorizontalAlignment(SwingConstants.CENTER);
        Font hnt10 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/HelveticaNeue-Thin.otf"))).deriveFont(Font.PLAIN, 10);
        transposeTextField.setBackground(Color.decode("#F5F5F5"));
        //transposeTextField.setFont(hnt10);
        transposeTextField.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        transposeTextField.setBounds(345, 457, 64, 32);
        TextPrompt transposePrompt = new TextPrompt(String.valueOf("-"), transposeTextField);
        transposePrompt.setHorizontalAlignment(TextPrompt.CENTER);
        frame.getContentPane().add(transposeTextField);

        Icon plusIcon = new ImageIcon("resources/icons/plusicon.png");
        transposeUpButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        transposeUpButton.setIcon(plusIcon);
        transposeUpButton.setBounds(418, 457, 32, 32);
        transposeUpButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        transposeUpButton.setBorderPainted(true);
        transposeUpButton.setFocusPainted(false);
        frame.getContentPane().add(transposeUpButton);

        Icon minusIcon = new ImageIcon("resources/icons/minusicon.png");
        transposeDownButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        transposeDownButton.setIcon(minusIcon);
        transposeDownButton.setBounds(453, 457, 32, 32);
        transposeDownButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        transposeDownButton.setBorderPainted(true);
        transposeDownButton.setFocusPainted(false);
        frame.getContentPane().add(transposeDownButton);

        Icon fillerIcon = new ImageIcon("resources/icons/fillericon.png");
        fillerButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        fillerButton.setIcon(fillerIcon);
        fillerButton.setBounds(489, 457, 32, 32);
        fillerButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        fillerButton.setBorderPainted(true);
        fillerButton.setFocusPainted(false);
        frame.getContentPane().add(fillerButton);

        notesPanel = new JPanel();
        notesPanel.setLayout(null);
        notesPanel.setBorder(null);
        notesPanel.setBackground(Color.decode("#F5F5F5"));

        JScrollPane notesScrollPane = new JScrollPane();
        notesScrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        notesScrollPane.getHorizontalScrollBar().setUI(new BetterScrollBar());
        notesScrollPane.setViewportView(notesPanel);
        notesScrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        notesScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        notesScrollPane
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        notesScrollPane.setBounds(20, 493, 500, 100);
        frame.getContentPane().add(notesScrollPane);

        noteViewArray = new ArrayList<NoteView>();

        int i = 0;

        for (Note note : activePhrase.getNotesArray()) {
            NoteView noteView = new NoteView(note);
            noteView.setBounds(10 + 44 * i, 10, 34, 67);
            noteView.setBorder(BorderFactory.createLineBorder(activePhrase.getBorderColor(), 1, true));
            i++;
            noteViewArray.add(noteView);
            notesPanel.add(noteView);
        }

        notesPanel.setPreferredSize(new Dimension(10 + 44 * activePhrase.getNotesArray().size(), 100));

        phrasesLabel = new JLabel("Phrases:");
        phrasesLabel.setFont(hnt20);
        phrasesLabel.setBounds(20, 599, 100, 24);
        frame.getContentPane().add(phrasesLabel);

        minTextField = new JTextField();
        minTextField.setHorizontalAlignment(SwingConstants.CENTER);
        minTextField.setBackground(Color.decode("#F5F5F5"));
        //minTextField.setFont(hnt10);
        minTextField.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        minTextField.setBounds(133, 600, 39, 32);
        minTextField.setText(String.valueOf(activePhrase.getMinPitch()));
        minTextField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        frame.getContentPane().add(minTextField);

        maxTextField = new JTextField();
        maxTextField.setHorizontalAlignment(SwingConstants.CENTER);
        maxTextField.setBackground(Color.decode("#F5F5F5"));
        //maxTextField.setFont(hnt10);
        maxTextField.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        maxTextField.setBounds(177, 600, 39, 32);
        maxTextField.setText(String.valueOf(activePhrase.getMaxPitch()));
        maxTextField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        frame.getContentPane().add(maxTextField);

        instrumentComboBox = new JComboBox();
        /*Font hnt12 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/HelveticaNeue-Thin.otf"))).deriveFont(Font.PLAIN, 12);
        compoundComboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        compoundComboBox.setForeground(Color.decode("#000000"));
        compoundComboBox.setBackground(Color.decode("#F5F5F5"));
        compoundComboBox.setFont(hnt12);*/
        //compoundComboBox.setRenderer(new SSComboBoxRenderer());
        for (int j = 0; j < 128; j++) {
            instrumentComboBox.addItem(j);
        }
        instrumentComboBox.setSelectedIndex(activePhrase.getInstrument());
        instrumentComboBox.setBounds(221, 600, 153, 32);
        frame.getContentPane().add(instrumentComboBox);

        colorButton = new BetterButton(activePhrase.getUnselectedColor(), 32, 32, 6);
        colorButton.setBounds(377, 600, 32, 32);
        colorButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        colorButton.setBorderPainted(true);
        colorButton.setFocusPainted(false);
        frame.getContentPane().add(colorButton);

        editPhraseButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        editPhraseButton.setIcon(editIcon);
        editPhraseButton.setBounds(414, 600, 32, 32);
        editPhraseButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        editPhraseButton.setBorderPainted(true);
        editPhraseButton.setFocusPainted(false);
        frame.getContentPane().add(editPhraseButton);

        addPhraseButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        addPhraseButton.setIcon(plusIcon);
        addPhraseButton.setBounds(450, 600, 32, 32);
        addPhraseButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        addPhraseButton.setBorderPainted(true);
        addPhraseButton.setFocusPainted(false);
        frame.getContentPane().add(addPhraseButton);

        removePhraseButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        removePhraseButton.setIcon(minusIcon);
        removePhraseButton.setBounds(486, 600, 32, 32);
        removePhraseButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        removePhraseButton.setBorderPainted(true);
        removePhraseButton.setFocusPainted(false);
        frame.getContentPane().add(removePhraseButton);

        phrasesPanel = new JPanel();
        phrasesPanel.setLayout(null);
        phrasesPanel.setBorder(null);
        phrasesPanel.setBounds(20, 639, 500, 100);
        phrasesPanel.setBackground(Color.decode("#F5F5F5"));

        JScrollPane phrasesScrollPane = new JScrollPane();
        phrasesScrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        phrasesScrollPane.setBackground(Color.decode("#000000"));
        phrasesScrollPane.getHorizontalScrollBar().setUI(new BetterScrollBar());
        phrasesScrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        phrasesScrollPane.setViewportView(phrasesPanel);
        phrasesScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        phrasesScrollPane
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        phrasesScrollPane.setBounds(20, 639, 500, 100);
        frame.getContentPane().add(phrasesScrollPane);

        phraseViewArray = new ArrayList<PhraseView>();

        int j = 0;

        for (Phrase phrase : activeProject.getPhrasesArray()) {
            PhraseView phraseView = new PhraseView(phrase);
            phraseView.setBackground(phraseView.getPhrase().getUnselectedColor());
            phraseView.updatePanel();
            phraseView.setBounds(10 + 110 * j, 5, 100, 70);
            j++;
            phraseViewArray.add(phraseView);
            phrasesPanel.add(phraseView);
        }

        phrasesPanel.setPreferredSize(new Dimension(10 + 110 * activeProject.getPhrasesArray().size(), 100));

        projectLabel = new JLabel("Project:");
        projectLabel.setFont(hnt20);
        projectLabel.setBounds(535, 15, 70, 24);
        frame.getContentPane().add(projectLabel);

        titleTextField = new JTextField();
        titleTextField.setHorizontalAlignment(SwingConstants.CENTER);
        titleTextField.setBackground(Color.decode("#F5F5F5"));
        //titleTextField.setFont(hnt10);
        titleTextField.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        titleTextField.setBounds(609, 11, 140, 32);
        titleTextField.setText(activeProject.getName());
        titleTextField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        frame.getContentPane().add(titleTextField);

        Icon newIcon = new ImageIcon("resources/icons/newicon.png");
        newButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        newButton.setIcon(newIcon);
        newButton.setBounds(758, 11, 32, 32);
        newButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        newButton.setBorderPainted(true);
        newButton.setFocusPainted(false);
        frame.getContentPane().add(newButton);

        Icon openIcon = new ImageIcon("resources/icons/openicon.png");
        openButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        openButton.setIcon(openIcon);
        openButton.setBounds(800, 11, 32, 32);
        openButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        openButton.setBorderPainted(true);
        openButton.setFocusPainted(false);
        frame.getContentPane().add(openButton);

        Icon saveIcon = new ImageIcon("resources/icons/saveicon.png");
        saveButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        saveButton.setIcon(saveIcon);
        saveButton.setBounds(842, 11, 32, 32);
        saveButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        saveButton.setBorderPainted(true);
        saveButton.setFocusPainted(false);
        frame.getContentPane().add(saveButton);

        Icon settingsIcon = new ImageIcon("resources/icons/settingsicon.png");
        settingsButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        settingsButton.setIcon(settingsIcon);
        settingsButton.setBounds(884, 11, 32, 32);
        settingsButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        settingsButton.setBorderPainted(true);
        settingsButton.setFocusPainted(false);
        frame.getContentPane().add(settingsButton);

        helpTextPane = new JTextPane();
        helpTextPane.setText("Helpful information.");
        helpTextPane.setBackground(frame.getContentPane().getBackground());
        Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
        helpTextPane.setFont(f);
        helpTextPane.setAlignmentX(JTextArea.RIGHT_ALIGNMENT);
        helpTextPane.setAlignmentY(JTextArea.BOTTOM_ALIGNMENT);
        helpTextPane.setBounds(924, 11, 320, 32);
        frame.getContentPane().add(helpTextPane);

        outTracksPanel = new JPanel();
        outTracksPanel.setLayout(null);
        outTracksPanel.setBorder(null);
        outTracksPanel.setBounds(535, 52, 700, 477);
        outTracksPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        outTracksPanel.setBackground(Color.decode("#F5F5F5"));
        frame.getContentPane().add(outTracksPanel);

        JLabel trackOptionsLabel = new JLabel("Tracks:");
        trackOptionsLabel.setBounds(8, 8, 50, 14);
        outTracksPanel.add(trackOptionsLabel);

        Icon loopicon = new ImageIcon("resources/icons/loopicon.png");
        loopButton = new BetterButton(Color.decode("#F5F5F5"), 21, 21, 6);
        loopButton.setIcon(loopicon);
        loopButton.setBounds(72, 5, 21, 21);
        loopButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        loopButton.setBorderPainted(true);
        loopButton.setFocusPainted(false);
        outTracksPanel.add(loopButton);

        Icon addtrackicon = new ImageIcon("resources/icons/addtrackicon.png");
        addTrackButton = new BetterButton(Color.decode("#F5F5F5"), 21, 21, 6);
        addTrackButton.setIcon(addtrackicon);
        addTrackButton.setBounds(98, 5, 21, 21);
        addTrackButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        addTrackButton.setBorderPainted(true);
        addTrackButton.setFocusPainted(false);
        outTracksPanel.add(addTrackButton);

        Icon removetrackicon = new ImageIcon("resources/icons/removetrackicon.png");
        removeTrackButton = new BetterButton(Color.decode("#F5F5F5"), 21, 21, 6);
        removeTrackButton.setIcon(removetrackicon);
        removeTrackButton.setBounds(123, 5, 21, 21);
        removeTrackButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        removeTrackButton.setBorderPainted(true);
        removeTrackButton.setFocusPainted(false);
        outTracksPanel.add(removeTrackButton);

        trackHeadPanel = new JPanel();
        trackHeadPanel.setLayout(null);
        trackHeadPanel.setBorder(null);
        trackHeadPanel.setBackground(Color.decode("#F5F5F5"));

        trackHeadScrollPane = new JScrollPane();
        trackHeadScrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        trackHeadScrollPane.setBackground(Color.decode("#000000"));
        trackHeadScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        trackHeadScrollPane.getHorizontalScrollBar().setUI(new BetterScrollBar());
        trackHeadScrollPane.setViewportView(trackHeadPanel);
        trackHeadScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        trackHeadScrollPane
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        trackHeadScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        trackHeadScrollPane.setBounds(0, 33, 150, 444);
        outTracksPanel.add(trackHeadScrollPane);

        trackHeadViewArray = new ArrayList<TrackHeadView>();

        int j1 = 0;

        for (Track t : activeProject.getTracksArray()) {
            TrackHeadView thv = new TrackHeadView(t);
            thv.setBackground(buttonBackgroundColor);
            thv.setBounds(0, 0 + j1 * 70, 150, 70);
            trackHeadViewArray.add(thv);
            trackHeadPanel.add(thv);
            j1++;
        }

        trackHeadPanel.setPreferredSize(new Dimension(150, 0 + 70 * trackHeadViewArray.size()));

        measureHeadPanel = new JPanel();
        measureHeadPanel.setLayout(null);
        measureHeadPanel.setBorder(null);
        measureHeadPanel.setBackground(Color.decode("#F5F5F5"));

        measureHeadScrollPane = new JScrollPane();
        measureHeadScrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        measureHeadScrollPane.setBackground(Color.decode("#000000"));
        measureHeadScrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        measureHeadScrollPane.getHorizontalScrollBar().setUI(new BetterScrollBar());
        measureHeadScrollPane.setViewportView(measureHeadPanel);
        measureHeadScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        measureHeadScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        measureHeadScrollPane
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        measureHeadScrollPane.setBounds(150, 0, 550, 33);
        outTracksPanel.add(measureHeadScrollPane);

        measureHeadViewArray = new ArrayList<MeasureHeadView>();

        for (int k = 0; k < activeProject.getNumMeasures(); k++) {
            MeasureHeadView mhv = new MeasureHeadView(k);
            mhv.setBackground(buttonBackgroundColor);
            mhv.setBounds(0 + k * 100, 0, 100, 33);
            measureHeadViewArray.add(mhv);
            measureHeadPanel.add(mhv);
        }

        measureHeadPanel.setPreferredSize(new Dimension(102 * measureHeadViewArray.size(), 33));

        inTracksPanel = new JPanel();
        inTracksPanel.setLayout(null);
        inTracksPanel.setBorder(null);
        inTracksPanel.setBackground(Color.decode("#F5F5F5"));

        tracksScrollPane = new JScrollPane();
        tracksScrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        tracksScrollPane.setBackground(Color.decode("#000000"));
        tracksScrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        tracksScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        tracksScrollPane.getHorizontalScrollBar().setUI(new BetterScrollBar());
        tracksScrollPane.getVerticalScrollBar().setUI(new BetterScrollBar());
        tracksScrollPane.setViewportView(inTracksPanel);
        tracksScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tracksScrollPane
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        tracksScrollPane.setBounds(150, 33, 550, 444);
        outTracksPanel.add(tracksScrollPane);

        trackViewArray = new ArrayList<TrackView>();

        int j3 = 0;

        for (Track t : activeProject.getTracksArray()) {
            TrackView tv = new TrackView(t);
            tv.setBackground(buttonBackgroundColor);
            tv.setBounds(0, 70 * j3, 100 * activeProject.getNumMeasures(), 70);
            j3++;
            trackViewArray.add(tv);
            inTracksPanel.add(tv);
        }

        inTracksPanel.setPreferredSize(new Dimension(100 * activeProject.getNumMeasures(),
                70 * trackViewArray.size()));

        playbackLabel = new JLabel("Playback:");
        playbackLabel.setFont(hnt20);
        playbackLabel.setBounds(535, 536, 81, 24);
        frame.getContentPane().add(playbackLabel);

        playbackPanel = new JPanel();
        playbackPanel.setLayout(null);
        playbackPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        playbackPanel.setBounds(535, 569, 700, 170);
        playbackPanel.setBackground(Color.decode("#F5F5F5"));
        frame.getContentPane().add(playbackPanel);

        playbackSlider = new JSlider();
        playbackSlider.setBounds(10, 15, 680, 27);
        playbackPanel.add(playbackSlider);

        timerLabel = new JLabel("00:00 out of " + activePhrase.getNumMeasures());
        timerLabel.setBounds(290, 37, 120, 24);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playbackPanel.add(timerLabel);

        playProjectButton = new BetterButton(Color.decode("#F5F5F5"), 52, 28, 6);
        playProjectButton.setText("Project");
        playProjectButton.setCol(activePhrase.getUnselectedColor());
        playProjectButton.setBounds(13, 51, 52, 28);
        playProjectButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        playProjectButton.setBorderPainted(true);
        playProjectButton.setFocusPainted(false);
        playbackPanel.add(playProjectButton);

        playPhraseButton = new BetterButton(Color.decode("#F5F5F5"), 52, 28, 6);
        playPhraseButton.setText("Phrase");
        playPhraseButton.setBounds(13, 85, 52, 28);
        playPhraseButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        playPhraseButton.setBorderPainted(true);
        playPhraseButton.setFocusPainted(false);
        playbackPanel.add(playPhraseButton);

        Icon playicon = new ImageIcon("resources/icons/playicon.png");
        playButton = new BetterButton(Color.decode("#F5F5F5"), 40, 40, 16);
        playButton.setIcon(playicon);
        playButton.setBounds(88, 63, 40, 40);
        playButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        playButton.setBorderPainted(true);
        playButton.setFocusPainted(false);
        playbackPanel.add(playButton);

        Icon stopicon = new ImageIcon("resources/icons/stopicon.png");
        stopButton = new BetterButton(Color.decode("#F5F5F5"), 40, 40, 16);
        stopButton.setIcon(stopicon);
        stopButton.setBounds(134, 63, 40, 40);
        stopButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        stopButton.setBorderPainted(true);
        stopButton.setFocusPainted(false);
        playbackPanel.add(stopButton);

        quantizeCheckBox = new JCheckBox();
        quantizeCheckBox.setSelected(activePhrase.isQuantized());
        quantizeCheckBox.setText("Quantize");
        quantizeCheckBox.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        quantizeCheckBox.setBounds(13, 132, 105, 20);
        playbackPanel.add(quantizeCheckBox);

        keyComboBox = new JComboBox();
        /*Font hnt12 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/HelveticaNeue-Thin.otf"))).deriveFont(Font.PLAIN, 12);
        compoundComboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        compoundComboBox.setForeground(Color.decode("#000000"));
        compoundComboBox.setBackground(Color.decode("#F5F5F5"));
        compoundComboBox.setFont(hnt12);*/
        //compoundComboBox.setRenderer(new SSComboBoxRenderer());
        for (String s : keysArray) {
            keyComboBox.addItem(s);
        }
        keyComboBox.setBounds(97, 125, 95, 28);
        keyComboBox.setSelectedItem("C");
        playbackPanel.add(keyComboBox);

        qualityComboBox = new JComboBox();
        /*Font hnt12 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/HelveticaNeue-Thin.otf"))).deriveFont(Font.PLAIN, 12);
        compoundComboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        compoundComboBox.setForeground(Color.decode("#000000"));
        compoundComboBox.setBackground(Color.decode("#F5F5F5"));
        compoundComboBox.setFont(hnt12);*/
        //compoundComboBox.setRenderer(new SSComboBoxRenderer());
        for (String s : qualityArray) {
            qualityComboBox.addItem(s);
        }
        qualityComboBox.setBounds(207, 125, 95, 28);
        qualityComboBox.setSelectedItem( "Major" );
        playbackPanel.add(qualityComboBox);

        qrhythmComboBox = new JComboBox();
        /*Font hnt12 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/HelveticaNeue-Thin.otf"))).deriveFont(Font.PLAIN, 12);
        compoundComboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        compoundComboBox.setForeground(Color.decode("#000000"));
        compoundComboBox.setBackground(Color.decode("#F5F5F5"));
        compoundComboBox.setFont(hnt12);*/
        //compoundComboBox.setRenderer(new SSComboBoxRenderer());
        for (String s : rhythmArray) {
            qrhythmComboBox.addItem(s);
        }
        qrhythmComboBox.setBounds(317, 125, 95, 28);
        qrhythmComboBox.setSelectedItem("1/4");
        playbackPanel.add(qrhythmComboBox);

        JLabel tempoLabel = new JLabel("Tempo:");
        tempoLabel.setBounds(517, 57, 60, 17);
        playbackPanel.add(tempoLabel);

        tempoTextField = new JTextField();
        tempoTextField.setHorizontalAlignment(SwingConstants.CENTER);
        tempoTextField.setBackground(Color.decode("#F5F5F5"));
        tempoTextField.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        tempoTextField.setBounds(575, 50, 51, 28);
        tempoTextField.setText(String.valueOf(activeProject.getTempo()));
        tempoTextField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        playbackPanel.add(tempoTextField);

        JLabel bpmLabel = new JLabel("bpm");
        bpmLabel.setBounds(638, 57, 40, 17);
        playbackPanel.add(bpmLabel);

        editCompoundView = new EditCompoundView(activePhrase.getCompound());
        editCompoundView.setVisible(false);
        frame.getContentPane().add(editCompoundView);

    }

    private void initializeControllers() {

        compoundComboBox.addItemListener(new CompoundComboBoxController(this, activeProject, compoundComboBox));
        compoundComboBox.addMouseListener(new HelpTextController(this, HelpStrings.COMPOUND_BOX));

        EditCompoundController editCompoundController = new EditCompoundController(this, activeProject);
        editCompoundButton.addMouseListener(new HelpTextController(this, HelpStrings.EDIT_COMPOUND));
        editCompoundButton.addActionListener(editCompoundController);
        editCompoundButton.addMouseListener(editCompoundController);

        leftOrRightCheckbox.addMouseListener(new HelpTextController(this, HelpStrings.LEFT_OR_RIGHT));

        ImportCompoundController importCompoundController = new ImportCompoundController(this, activeProject);
        importCompoundButton.addMouseListener(new HelpTextController(this, HelpStrings.IMPORT_COMPOUND));
        importCompoundButton.addActionListener(importCompoundController);
        importCompoundButton.addMouseListener(importCompoundController);

        chPanel.addChartMouseListener(new GraphController(this, activeProject));
        chPanel.addMouseListener(new HelpTextController(this, HelpStrings.GRAPH_PANEL));

        multipleSelectionCheckBox.addActionListener(new MultipleNoteSelectionController(this, activeProject, multipleSelectionCheckBox));
        multipleSelectionCheckBox.addMouseListener(new HelpTextController(this, HelpStrings.MULT_SELEC));

        TransposeButtonController transposeUpButtonController = new TransposeButtonController(this, activeProject, 0);
        transposeUpButton.addMouseListener(new HelpTextController(this, HelpStrings.TRANSPOSE_UP));
        transposeUpButton.addActionListener(transposeUpButtonController);
        transposeUpButton.addMouseListener(transposeUpButtonController);

        TransposeButtonController transposeDownButtonController = new TransposeButtonController(this, activeProject, 1);
        transposeDownButton.addMouseListener(new HelpTextController(this, HelpStrings.TRANSPOSE_DOWN));
        transposeDownButton.addActionListener(transposeDownButtonController);
        transposeDownButton.addMouseListener(transposeDownButtonController);

        FillerController fillerController = new FillerController(this, activeProject);
        fillerButton.addMouseListener(new HelpTextController(this, HelpStrings.FILLER));
        fillerButton.addActionListener(fillerController);
        fillerButton.addMouseListener(fillerController);

        EditPhraseController editPhraseController = new EditPhraseController(this, activeProject);
        editPhraseButton.addMouseListener(new HelpTextController(this, HelpStrings.EDIT_PHRASE));
        editPhraseButton.addActionListener(editPhraseController);
        editPhraseButton.addMouseListener(editPhraseController);

        AddPhraseController addPhraseController = new AddPhraseController(this, activeProject);
        addPhraseButton.addMouseListener(new HelpTextController(this, HelpStrings.ADD_PHRASE));
        addPhraseButton.addActionListener(addPhraseController);
        addPhraseButton.addMouseListener(addPhraseController);

        RemovePhraseController removePhraseController = new RemovePhraseController(this, activeProject);
        removePhraseButton.addMouseListener(new HelpTextController(this, HelpStrings.REMOVE_PHRASE));
        removePhraseButton.addActionListener(removePhraseController);
        removePhraseButton.addMouseListener(removePhraseController);

        for (NoteView noteView : noteViewArray) {
            noteView.addMouseListener(new HelpTextController(this, HelpStrings.NOTE_VIEW));
            noteView.addMouseListener(new NoteController(activeProject, this, noteView));
        }

        for (PhraseView phraseView : phraseViewArray) {
            phraseView.addMouseListener(new HelpTextController(this, HelpStrings.PHRASE_VIEW));
            phraseView.addMouseListener(new PhraseController(activeProject, this, phraseView));
        }

        for (TrackHeadView thv : trackHeadViewArray) {
            thv.addMouseListener(new HelpTextController(this, HelpStrings.TRACK_HEAD));
            thv.addMouseListener(new TrackHeadController(this, activeProject, thv));
            thv.getInstrumentComboBox().addItemListener(new TrackInstrumentController(this, activeProject, thv));
            thv.getLiveCheckbox().addActionListener(new TrackLiveController(this, activeProject, thv));
            ExpandTrackController expandController = new ExpandTrackController(this, activeProject, thv);
            thv.getExpandButton().addActionListener(expandController);
            thv.getExpandButton().addMouseListener(expandController);
        }

        for (MeasureHeadView mhv : measureHeadViewArray) {
            MeasureHeadController measureHeadController = new MeasureHeadController(this, activeProject, mhv);
            mhv.addMouseListener(new HelpTextController(this, HelpStrings.MEASURE_HEAD));
            mhv.addMouseListener(measureHeadController);
        }

        for (TrackView tv : trackViewArray) {
            for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray()) {
                pitv.addMouseListener(new PhraseInTrackController(this, activeProject, pitv));
            }
        }

        ScrollBarSynchronizer verticalSynchronizer = new ScrollBarSynchronizer(trackHeadScrollPane.getVerticalScrollBar(),
                tracksScrollPane.getVerticalScrollBar());
        ScrollBarSynchronizer horizontalSynchronizer = new ScrollBarSynchronizer(measureHeadScrollPane.getHorizontalScrollBar(),
                tracksScrollPane.getHorizontalScrollBar());
        trackHeadScrollPane.getVerticalScrollBar().addAdjustmentListener(verticalSynchronizer);
        tracksScrollPane.getVerticalScrollBar().addAdjustmentListener(verticalSynchronizer);
        measureHeadScrollPane.getHorizontalScrollBar().addAdjustmentListener(horizontalSynchronizer);
        tracksScrollPane.getHorizontalScrollBar().addAdjustmentListener(horizontalSynchronizer);

        NewProjectController newProjectController = new NewProjectController(this, activeProject);
        newButton.addMouseListener(new HelpTextController(this, HelpStrings.NEW_PROJECT));
        newButton.addActionListener(newProjectController);
        newButton.addMouseListener(newProjectController);

        OpenProjectController openProjectController = new OpenProjectController(this, activeProject);
        openButton.addMouseListener(new HelpTextController(this, HelpStrings.OPEN_PROJECT));
        openButton.addActionListener(openProjectController);
        openButton.addMouseListener(openProjectController);

        SaveProjectController saveProjectController = new SaveProjectController(this, activeProject);
        saveButton.addMouseListener(new HelpTextController(this, HelpStrings.SAVE_PROJECT));
        saveButton.addActionListener(saveProjectController);
        saveButton.addMouseListener(saveProjectController);

        SettingsController settingsController = new SettingsController(this, activeProject);
        settingsButton.addMouseListener(new HelpTextController(this, HelpStrings.SETTINGS));
        settingsButton.addActionListener(settingsController);
        settingsButton.addMouseListener(settingsController);

        LoopController loopController = new LoopController(this, activeProject);
        loopButton.addMouseListener(new HelpTextController(this, HelpStrings.LOOP));
        loopButton.addActionListener(loopController);
        loopButton.addMouseListener(loopController);

        AddTrackController addTrackController = new AddTrackController(this, activeProject);
        addTrackButton.addMouseListener(new HelpTextController(this, HelpStrings.ADD_TRACK));
        addTrackButton.addActionListener(addTrackController);
        addTrackButton.addMouseListener(addTrackController);

        RemoveTrackController removeTrackController = new RemoveTrackController(this, activeProject);
        removeTrackButton.addMouseListener(new HelpTextController(this, HelpStrings.REMOVE_TRACK));
        removeTrackButton.addActionListener(removeTrackController);
        removeTrackButton.addMouseListener(removeTrackController);

        PlaySelectorController playProjectController = new PlaySelectorController(this, activeProject, 0);
        playProjectButton.addMouseListener(new HelpTextController(this, HelpStrings.PLAY_PROJECT));
        playProjectButton.addActionListener(playProjectController);
        playProjectButton.addMouseListener(playProjectController);

        PlaySelectorController playPhraseController = new PlaySelectorController(this, activeProject, 1);
        playPhraseButton.addMouseListener(new HelpTextController(this, HelpStrings.PLAY_PHRASE));
        playPhraseButton.addActionListener(playPhraseController);
        playPhraseButton.addMouseListener(playPhraseController);

        SoundPlayerController playPauseController = new SoundPlayerController(this, activeProject, 0);
        playButton.addMouseListener(new HelpTextController(this, HelpStrings.PLAY_PAUSE));
        //playButton.addActionListener(playPauseController);
        playButton.addMouseListener(playPauseController);

        SoundPlayerController stopController = new SoundPlayerController(this, activeProject, 1);
        stopButton.addMouseListener(new HelpTextController(this, HelpStrings.STOP));
        stopButton.addActionListener(stopController);
        stopButton.addMouseListener(stopController);

    }

    public void updateActivePhrase(Phrase phrase) {

        activePhrase = phrase;
        temp = false;
        compoundComboBox.setSelectedIndex(activePhrase.getCompound().getId());
        temp = true;

        for (PhraseView pv : phraseViewArray) {
            if (pv.getPhrase().getId() != phrase.getId()) pv.getPhrase().setSelected(false);
            else phrase.setSelected(true);
            pv.updatePanel();
        }

        notesPanel.removeAll();
        notesPanel.setLayout(null);

        noteViewArray = new ArrayList<NoteView>();

        int i = 0;

        for (Note note : phrase.getNotesArray()) {
            NoteView noteView = new NoteView(note);
            noteView.setBounds(10 + 44 * i, 10, 34, 67);
            noteView.setBorder(BorderFactory.createLineBorder(activePhrase.getBorderColor(), 1, true));
            noteView.updatePanel();
            i++;
            noteView.addMouseListener(new HelpTextController(this, HelpStrings.NOTE_VIEW));
            noteView.addMouseListener(new NoteController(activeProject, this, noteView));
            noteViewArray.add(noteView);
            notesPanel.add(noteView);
        }

        notesPanel.setPreferredSize(new Dimension(10 + 44 * activePhrase.getNotesArray().size(), 100));

        minTextField.setText(String.valueOf(activePhrase.getMinPitch()));
        maxTextField.setText(String.valueOf(activePhrase.getMaxPitch()));
        instrumentComboBox.setSelectedIndex(activePhrase.getInstrument());

        colorButton.setCol(activePhrase.getUnselectedColor());
        colorButton.repaint();

        int j4 = 0;
        for (TrackView tv : trackViewArray) {
            for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray()) {
                pitv.adjustSize(j4);
                if (activePhrase.getId() == pitv.getPhrase().getId()) {
                    pitv.setBorder(BorderFactory.createLineBorder(activePhrase.getBorderColor(), 2, false));
                    pitv.repaint();
                }
                else {
                    pitv.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
                    pitv.repaint();
                }
                j4++;
            }
            j4 = 0;
        }

        if (isProject) {
            playProjectButton.setCol(activePhrase.getUnselectedColor());
            playProjectButton.repaint();
        }
        else {
            playPhraseButton.setCol(activePhrase.getUnselectedColor());
            playPhraseButton.repaint();
        }

        /*if (quantizeCheckBox.isSelected()) {

            qrhythmComboBox.setSelectedItem(activePhrase.getQRhythm());
            keyComboBox.setSelectedItem(activePhrase.getKey());
            qualityComboBox.setSelectedItem(activePhrase.getQuality());

        }*/

        soundPlayer.reset();
        soundPlayer.updateSoundPlayer();

        frame.pack();

    }

    public void updateIntervalMarker() {
        Marker marker;

        if (activePhrase.getX2() != 0.0 && activePhrase.getX1() != 0.0) {

            marker = new IntervalMarker(activePhrase.getX1(), activePhrase.getX2());

        } else {

            marker = new IntervalMarker(0, 0);

        }

        marker.setStroke(new BasicStroke(50));
        marker.setPaint(activePhrase.getSelectedColor());
        marker.setAlpha(0.25f);

        if (activePhrase.getCompound().getDataChart() != null) {

            System.out.println("Adding interval marker to graph...");

            XYPlot plot = activePhrase.getCompound().getDataChart().getDataChart().getXYPlot();

            plot.clearDomainMarkers();

            System.out.println(activePhrase.getSelectedNotes().size() + " notes to add.");

            for ( Note n : activePhrase.getSelectedNotes() ) {

                plot.addDomainMarker(addNoteMarker(n));

            }

            plot.addDomainMarker(marker);

        }
    }

    public Marker addNoteMarker(Note n) {

        Color color = new Color(0, 0, 0);

        double x1 = n.getPeak().getX1();
        double x2 = n.getPeak().getX2();

        if (activePhrase.getNotesArray().indexOf(n) == 0) x1 = activePhrase.getX2();

        if (activePhrase.getNotesArray().indexOf(n) == activePhrase.getNotesArray().size() - 1)
            x2 = activePhrase.getX1();

        Marker newMarker = new IntervalMarker(x2, x1);

        newMarker.setStroke(new BasicStroke(50));
        newMarker.setPaint(Color.BLACK);
        newMarker.setAlpha(1);

        return newMarker;

    }

    public void refreshSelectedNotes() {
        for (NoteView nv : noteViewArray) {
            nv.updatePanel();
            nv.repaint();
        }
    }

    public static void main( String[] args ) throws FileNotFoundException, FontFormatException, IOException,
            MidiUnavailableException, UnsupportedAudioFileException, LineUnavailableException,
            InvalidMidiDataException {

        SonifiedSpectra app = new SonifiedSpectra();
        app.frame.setVisible(true);

    }

    //Getters and Setters

    public JButton getImportCompoundButton2() {
        return importCompoundButton2;
    }

    public void setImportCompoundButton2(JButton importCompoundButton2) {
        this.importCompoundButton2 = importCompoundButton2;
    }

    public JButton getColorButton2() {
        return colorButton2;
    }

    public void setColorButton2(BetterButton colorButton2) {
        this.colorButton2 = colorButton2;
    }

    public JButton getEditCompoundButton2() {
        return editCompoundButton2;
    }

    public void setEditCompoundButton2(JButton editCompoundButton2) {
        this.editCompoundButton2 = editCompoundButton2;
    }

    public JButton getNewButton2() {
        return newButton2;
    }

    public void setNewButton2(JButton newButton2) {
        this.newButton2 = newButton2;
    }

    public JButton getOpenButton2() {
        return openButton2;
    }

    public void setOpenButton2(JButton openButton2) {
        this.openButton2 = openButton2;
    }

    public JButton getSaveButton2() {
        return saveButton2;
    }

    public void setSaveButton2(JButton saveButton2) {
        this.saveButton2 = saveButton2;
    }

    public JButton getSettingsButton2() {
        return settingsButton2;
    }

    public void setSettingsButton2(JButton settingsButton2) {
        this.settingsButton2 = settingsButton2;
    }

    public JButton getTransposeUpButton2() {
        return transposeUpButton2;
    }

    public void setTransposeUpButton2(JButton transposeUpButton2) {
        this.transposeUpButton2 = transposeUpButton2;
    }

    public JButton getTransposeDownButton2() {
        return transposeDownButton2;
    }

    public void setTransposeDownButton2(JButton transposeDownButton2) {
        this.transposeDownButton2 = transposeDownButton2;
    }

    public JButton getFillerButton2() {
        return fillerButton2;
    }

    public void setFillerButton2(JButton fillerButton2) {
        this.fillerButton2 = fillerButton2;
    }

    public JButton getAddPhraseButton2() {
        return addPhraseButton2;
    }

    public void setAddPhraseButton2(JButton addPhraseButton2) {
        this.addPhraseButton2 = addPhraseButton2;
    }

    public BetterButton getEditPhraseButton() {
        return editPhraseButton;
    }

    public void setEditPhraseButton(BetterButton editPhraseButton) {
        this.editPhraseButton = editPhraseButton;
    }

    public JButton getRemovePhraseButton2() {
        return removePhraseButton2;
    }

    public void setRemovePhraseButton2(JButton removePhraseButton2) {
        this.removePhraseButton2 = removePhraseButton2;
    }

    public JButton getPlayProjectButton2() {
        return playProjectButton2;
    }

    public void setPlayProjectButton2(JButton playProjectButton2) {
        this.playProjectButton2 = playProjectButton2;
    }

    public JButton getPlayPhraseButton2() {
        return playPhraseButton2;
    }

    public void setPlayPhraseButton2(JButton playPhraseButton2) {
        this.playPhraseButton2 = playPhraseButton2;
    }

    public JButton getPlayButton2() {
        return playButton2;
    }

    public void setPlayButton2(JButton playButton2) {
        this.playButton2 = playButton2;
    }

    public JButton getPauseButton2() {
        return pauseButton2;
    }

    public void setPauseButton2(JButton pauseButton2) {
        this.pauseButton2 = pauseButton2;
    }

    public JButton getStopButton2() {
        return stopButton2;
    }

    public void setStopButton2(JButton stopButton2) {
        this.stopButton2 = stopButton2;
    }

    public JPanel getOutTracksPanel() {
        return outTracksPanel;
    }

    public void setOutTracksPanel(JPanel outTracksPanel) {
        this.outTracksPanel = outTracksPanel;
    }

    public JPanel getGraphPanel() {
        return graphPanel;
    }

    public void setGraphPanel(JPanel graphPanel) {
        this.graphPanel = graphPanel;
    }

    public JPanel getNotesPanel() {
        return notesPanel;
    }

    public void setNotesPanel(JPanel notesPanel) {
        this.notesPanel = notesPanel;
    }

    public JPanel getPhrasesPanel() {
        return phrasesPanel;
    }

    public void setPhrasesPanel(JPanel phrasesPanel) {
        this.phrasesPanel = phrasesPanel;
    }

    public JComboBox getCompoundComboBox() {
        return compoundComboBox;
    }

    public void setCompoundComboBox(JComboBox compoundComboBox) {
        this.compoundComboBox = compoundComboBox;
    }

    public JComboBox getInstrumentComboBox() {
        return instrumentComboBox;
    }

    public void setInstrumentComboBox(JComboBox instrumentComboBox) {
        this.instrumentComboBox = instrumentComboBox;
    }

    public JComboBox getKeyComboBox() {
        return keyComboBox;
    }

    public void setKeyComboBox(JComboBox keyComboBox) {
        this.keyComboBox = keyComboBox;
    }

    public JComboBox getQualityComboBox() {
        return qualityComboBox;
    }

    public void setQualityComboBox(JComboBox qualityComboBox) {
        this.qualityComboBox = qualityComboBox;
    }

    public JComboBox getQrhythmComboBox() {
        return qrhythmComboBox;
    }

    public void setQrhythmComboBox(JComboBox qrhythmComboBox) {
        this.qrhythmComboBox = qrhythmComboBox;
    }

    public JTextField getTitleTextField() {
        return titleTextField;
    }

    public void setTitleTextField(JTextField titleTextField) {
        this.titleTextField = titleTextField;
    }

    public JTextField getTransposeTextField() {
        return transposeTextField;
    }

    public void setTransposeTextField(JTextField transposeTextField) {
        this.transposeTextField = transposeTextField;
    }

    public JTextField getMinTextField() {
        return minTextField;
    }

    public void setMinTextField(JTextField minTextField) {
        this.minTextField = minTextField;
    }

    public JTextField getMaxTextField() {
        return maxTextField;
    }

    public void setMaxTextField(JTextField maxTextField) {
        this.maxTextField = maxTextField;
    }

    public JTextField getTempoTextField() {
        return tempoTextField;
    }

    public void setTempoTextField(JTextField tempoTextField) {
        this.tempoTextField = tempoTextField;
    }

    public JSlider getPlaybackSlider() {
        return playbackSlider;
    }

    public void setPlaybackSlider(JSlider playbackSlider) {
        this.playbackSlider = playbackSlider;
    }

    public JCheckBox getQuantizeCheckBox() {
        return quantizeCheckBox;
    }

    public void setQuantizeCheckBox(JCheckBox quantizeCheckBox) {
        this.quantizeCheckBox = quantizeCheckBox;
    }

    public JLabel getSpectrumLabel() {
        return spectrumLabel;
    }

    public void setSpectrumLabel(JLabel spectrumLabel) {
        this.spectrumLabel = spectrumLabel;
    }

    public JLabel getHelpTextLabel() {
        return helpTextLabel;
    }

    public void setHelpTextLabel(JLabel helpTextLabel) {
        this.helpTextLabel = helpTextLabel;
    }

    public JLabel getTimerLabel() {
        return timerLabel;
    }

    public void setTimerLabel(JLabel timerLabel) {
        this.timerLabel = timerLabel;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public void setRootPanel(JPanel rootPanel) {
        this.rootPanel = rootPanel;
    }

    public JLabel getCompoundLabel() {
        return compoundLabel;
    }

    public void setCompoundLabel(JLabel compoundLabel) {
        this.compoundLabel = compoundLabel;
    }

    public JLabel getNotesLabel() {
        return notesLabel;
    }

    public void setNotesLabel(JLabel notesLabel) {
        this.notesLabel = notesLabel;
    }

    public JLabel getPhrasesLabel() {
        return phrasesLabel;
    }

    public void setPhrasesLabel(JLabel phrasesLabel) {
        this.phrasesLabel = phrasesLabel;
    }

    public JLabel getTransposeLabel() {
        return transposeLabel;
    }

    public void setTransposeLabel(JLabel transposeLabel) {
        this.transposeLabel = transposeLabel;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getMainView() {
        return mainView;
    }

    public void setMainView(JPanel mainView) {
        this.mainView = mainView;
    }

    public EditCompoundView getEditCompoundView() {
        return editCompoundView;
    }

    public void setEditCompoundView(EditCompoundView editCompoundView) {
        this.editCompoundView = editCompoundView;
    }

    public JPanel getFillerNotesView() {
        return fillerNotesView;
    }

    public void setFillerNotesView(JPanel fillerNotesView) {
        this.fillerNotesView = fillerNotesView;
    }

    public JPanel getLoopPanel() {
        return loopPanel;
    }

    public void setLoopPanel(JPanel loopPanel) {
        this.loopPanel = loopPanel;
    }

    public ArrayList<NoteView> getNoteViewArray() {
        return noteViewArray;
    }

    public void setNoteViewArray(ArrayList<NoteView> noteViewArray) {
        this.noteViewArray = noteViewArray;
    }

    public ArrayList<PhraseView> getPhraseViewArray() {
        return phraseViewArray;
    }

    public void setPhraseViewArray(ArrayList<PhraseView> phraseViewArray) {
        this.phraseViewArray = phraseViewArray;
    }

    public BetterButton getColorButton() {
        return colorButton;
    }

    public void setColorButton(BetterButton colorButton) {
        this.colorButton = colorButton;
    }

    public void setColorButton2(JButton colorButton2) {
        this.colorButton2 = colorButton2;
    }

    public ArrayList<String> getColorsArray() {
        return colorsArray;
    }

    public void setColorsArray(ArrayList<String> colorsArray) {
        this.colorsArray = colorsArray;
    }

    public BetterButton getEditCompoundButton() {
        return editCompoundButton;
    }

    public void setEditCompoundButton(BetterButton editCompoundButton) {
        this.editCompoundButton = editCompoundButton;
    }

    public BetterButton getImportCompoundButton() {
        return importCompoundButton;
    }

    public void setImportCompoundButton(BetterButton importCompoundButton) {
        this.importCompoundButton = importCompoundButton;
    }

    public BetterButton getNewButton() {
        return newButton;
    }

    public void setNewButton(BetterButton newButton) {
        this.newButton = newButton;
    }

    public BetterButton getOpenButton() {
        return openButton;
    }

    public void setOpenButton(BetterButton openButton) {
        this.openButton = openButton;
    }

    public BetterButton getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(BetterButton saveButton) {
        this.saveButton = saveButton;
    }

    public BetterButton getSettingsButton() {
        return settingsButton;
    }

    public void setSettingsButton(BetterButton settingsButton) {
        this.settingsButton = settingsButton;
    }

    public BetterButton getTransposeUpButton() {
        return transposeUpButton;
    }

    public void setTransposeUpButton(BetterButton transposeUpButton) {
        this.transposeUpButton = transposeUpButton;
    }

    public BetterButton getTransposeDownButton() {
        return transposeDownButton;
    }

    public void setTransposeDownButton(BetterButton transposeDownButton) {
        this.transposeDownButton = transposeDownButton;
    }

    public BetterButton getFillerButton() {
        return fillerButton;
    }

    public void setFillerButton(BetterButton fillerButton) {
        this.fillerButton = fillerButton;
    }

    public BetterButton getAddPhraseButton() {
        return addPhraseButton;
    }

    public void setAddPhraseButton(BetterButton addPhraseButton) {
        this.addPhraseButton = addPhraseButton;
    }

    public BetterButton getRemovePhraseButton() {
        return removePhraseButton;
    }

    public void setRemovePhraseButton(BetterButton removePhraseButton) {
        this.removePhraseButton = removePhraseButton;
    }

    public BetterButton getPlayProjectButton() {
        return playProjectButton;
    }

    public void setPlayProjectButton(BetterButton playProjectButton) {
        this.playProjectButton = playProjectButton;
    }

    public BetterButton getPlayPhraseButton() {
        return playPhraseButton;
    }

    public void setPlayPhraseButton(BetterButton playPhraseButton) {
        this.playPhraseButton = playPhraseButton;
    }

    public BetterButton getPlayButton() {
        return playButton;
    }

    public void setPlayButton(BetterButton playButton) {
        this.playButton = playButton;
    }

    public BetterButton getPauseButton() {
        return pauseButton;
    }

    public void setPauseButton(BetterButton pauseButton) {
        this.pauseButton = pauseButton;
    }

    public BetterButton getStopButton() {
        return stopButton;
    }

    public void setStopButton(BetterButton stopButton) {
        this.stopButton = stopButton;
    }

    public Color getButtonHighlightColor() {
        return buttonHighlightColor;
    }

    public void setButtonHighlightColor(Color buttonHighlightColor) {
        this.buttonHighlightColor = buttonHighlightColor;
    }

    public Color getButtonBackgroundColor() {
        return buttonBackgroundColor;
    }

    public void setButtonBackgroundColor(Color buttonBackgroundColor) {
        this.buttonBackgroundColor = buttonBackgroundColor;
    }

    public JCheckBox getMultipleSelectionCheckBox() {
        return multipleSelectionCheckBox;
    }

    public void setMultipleSelectionCheckBox(JCheckBox multipleSelectionCheckBox) {
        this.multipleSelectionCheckBox = multipleSelectionCheckBox;
    }

    public Phrase getActivePhrase() {
        return activePhrase;
    }

    public void setActivePhrase(Phrase activePhrase) {
        this.activePhrase = activePhrase;
    }

    public JTextPane getHelpTextPane() {
        return helpTextPane;
    }

    public void setHelpTextPane(JTextPane helpTextPane) {
        this.helpTextPane = helpTextPane;
    }

    public ChartPanel getChPanel() {
        return chPanel;
    }

    public void setChPanel(ChartPanel chPanel) {
        this.chPanel = chPanel;
    }

    public JCheckBox getLeftOrRightCheckbox() {
        return leftOrRightCheckbox;
    }

    public void setLeftOrRightCheckbox(JCheckBox leftOrRightCheckbox) {
        this.leftOrRightCheckbox = leftOrRightCheckbox;
    }

    public JLabel getProjectLabel() {
        return projectLabel;
    }

    public void setProjectLabel(JLabel projectLabel) {
        this.projectLabel = projectLabel;
    }

    public JLabel getPlaybackLabel() {
        return playbackLabel;
    }

    public void setPlaybackLabel(JLabel playbackLabel) {
        this.playbackLabel = playbackLabel;
    }

    public JPanel getPlaybackPanel() {
        return playbackPanel;
    }

    public void setPlaybackPanel(JPanel playbackPanel) {
        this.playbackPanel = playbackPanel;
    }

    public Project getActiveProject() {
        return activeProject;
    }

    public void setActiveProject(Project activeProject) {
        this.activeProject = activeProject;
    }

    public ArrayList<String> getKeysArray() {
        return keysArray;
    }

    public void setKeysArray(ArrayList<String> keysArray) {
        this.keysArray = keysArray;
    }

    public ArrayList<String> getQualityArray() {
        return qualityArray;
    }

    public void setQualityArray(ArrayList<String> qualityArray) {
        this.qualityArray = qualityArray;
    }

    public ArrayList<String> getRhythmArray() {
        return rhythmArray;
    }

    public void setRhythmArray(ArrayList<String> rhythmArray) {
        this.rhythmArray = rhythmArray;
    }

    public SoundPlayer getSoundPlayer() {
        return soundPlayer;
    }

    public void setSoundPlayer(SoundPlayer soundPlayer) {
        this.soundPlayer = soundPlayer;
    }

    public boolean isProject() {
        return isProject;
    }

    public void setProject(boolean isProject) {
        this.isProject = isProject;
    }

    public boolean isTemp() {
        return temp;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    public BetterButton getLoopButton() {
        return loopButton;
    }

    public void setLoopButton(BetterButton loopButton) {
        this.loopButton = loopButton;
    }

    public BetterButton getAddTrackButton() {
        return addTrackButton;
    }

    public void setAddTrackButton(BetterButton addTrackButton) {
        this.addTrackButton = addTrackButton;
    }

    public BetterButton getRemoveTrackButton() {
        return removeTrackButton;
    }

    public void setRemoveTrackButton(BetterButton removeTrackButton) {
        this.removeTrackButton = removeTrackButton;
    }

    public JPanel getMeasureHeadPanel() {
        return measureHeadPanel;
    }

    public void setMeasureHeadPanel(JPanel measureHeadPanel) {
        this.measureHeadPanel = measureHeadPanel;
    }

    public JPanel getTrackHeadPanel() {
        return trackHeadPanel;
    }

    public void setTrackHeadPanel(JPanel trackHeadPanel) {
        this.trackHeadPanel = trackHeadPanel;
    }

    public JPanel getInTracksPanel() {
        return inTracksPanel;
    }

    public void setInTracksPanel(JPanel inTracksPanel) {
        this.inTracksPanel = inTracksPanel;
    }

    public JScrollPane getTrackHeadScrollPane() {
        return trackHeadScrollPane;
    }

    public void setTrackHeadScrollPane(JScrollPane trackHeadScrollPane) {
        this.trackHeadScrollPane = trackHeadScrollPane;
    }

    public JScrollPane getMeasureHeadScrollPane() {
        return measureHeadScrollPane;
    }

    public void setMeasureHeadScrollPane(JScrollPane measureHeadScrollPane) {
        this.measureHeadScrollPane = measureHeadScrollPane;
    }

    public JScrollPane getTracksScrollPane() {
        return tracksScrollPane;
    }

    public void setTracksScrollPane(JScrollPane tracksScrollPane) {
        this.tracksScrollPane = tracksScrollPane;
    }

    public ArrayList<TrackHeadView> getTrackHeadViewArray() {
        return trackHeadViewArray;
    }

    public void setTrackHeadViewArray(ArrayList<TrackHeadView> trackHeadViewArray) {
        this.trackHeadViewArray = trackHeadViewArray;
    }

    public ArrayList<TrackView> getTrackViewArray() {
        return trackViewArray;
    }

    public void setTrackViewArray(ArrayList<TrackView> trackViewArray) {
        this.trackViewArray = trackViewArray;
    }

    public ArrayList<MeasureHeadView> getMeasureHeadViewArray() {
        return measureHeadViewArray;
    }

    public void setMeasureHeadViewArray(ArrayList<MeasureHeadView> measureHeadViewArray) {
        this.measureHeadViewArray = measureHeadViewArray;
    }
}
