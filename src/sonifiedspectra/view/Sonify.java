package sonifiedspectra.view;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.XYPlot;
import sonifiedspectra.controllers.*;
import sonifiedspectra.model.*;
import sonifiedspectra.model.Track;

import javax.imageio.ImageIO;
import javax.sound.midi.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by Hvandenberg on 5/28/15.
 */
public class Sonify {

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
    private BetterButton measuresButton;
    private JLayeredPane layeredPane;

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
    private BetterButton addPhraseToTrackButton;
    private BetterButton movePitvRightButton;
    private BetterButton movePitvLeftButton;
    private BetterButton duplicatePhraseButton;

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
    private JPanel playbackLinePanel;

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
    private JTextField movePitvTextField;

    private JSlider playbackSlider;
    private JSlider noteVolumeSlider;
    private JSlider measureZoomSlider;

    private JCheckBox quantizeCheckBox;
    private JCheckBox multipleSelectionCheckBox;
    private JCheckBox tracksMultSelectCheckbox;
    private JCheckBox leftOrRightCheckbox;
    private JCheckBox selectAllOrNoneCheckbox;

    private JLabel spectrumLabel;
    private JLabel helpTextLabel;
    private JLabel timerLabel;
    private JLabel compoundLabel;
    private JLabel notesLabel;
    private JLabel phrasesLabel;
    private JLabel transposeLabel;
    private JLabel projectLabel;
    private JLabel playbackLabel;
    private JLabel noteVolumeLabel;

    private JTextPane helpTextPane;

    private JFrame frame;
    private Phrase activePhrase;
    private Project activeProject;

    private JPanel mainView;

    private FillerNotesDialog fillerDialog;
    private EditCompoundDialog editCompoundDialog;
    private EditPhraseDialog editPhraseDialog;
    private NewProjectDialog newProjectDialog;
    private SettingsDialog settingsDialog;
    private LoopDialog loopDialog;
    private MeasuresDialog measuresDialog;

    private ArrayList<NoteView> noteViewArray;
    private ArrayList<PhraseView> phraseViewArray;
    private ArrayList<TrackHeadView> trackHeadViewArray;
    private ArrayList<TrackView> trackViewArray;
    private ArrayList<MeasureHeadView> measureHeadViewArray;

    private Instrument instruments[];

    private ArrayList<String> colorsArray;
    private ArrayList<String> keysArray;
    private ArrayList<String> qualityArray;
    private ArrayList<String> rhythmArray;

    private int currentColorIndex;
    private int measureScale;

    private SoundPlayer soundPlayer;
    private boolean isProject;

    private Line playbackLine;

    // Prevents calling combobox listener infinitely when switching phrases
    private boolean temp;

    public Sonify() throws FileNotFoundException, FontFormatException, IOException, MidiUnavailableException,
            UnsupportedAudioFileException, LineUnavailableException, InvalidMidiDataException, URISyntaxException {

        /*final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
        }

        Graphics2D g = splash.createGraphics();
        if (g == null) {
            System.out.println("g is null");
            return;
        }
        for(int i = 0; i < 100; i++) {
            renderSplashFrame(g, i);
            splash.update();
            try {
                Thread.sleep(30);
            }
            catch (InterruptedException e) {
            }
        }
        splash.close();*/

        try {
            initialize();
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    private void initialize() throws FileNotFoundException, FontFormatException,
            IOException, MidiUnavailableException, UnsupportedAudioFileException,
            LineUnavailableException, InvalidMidiDataException, URISyntaxException {

        initializeModel();

        try {
            initializeView();
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        initializeControllers();

        System.out.println(activeProject.getDirectoryPath());

        soundPlayer = new SoundPlayer(new File(activeProject.getDirectoryPath() + "/Midi/starwars.mid"), activePhrase.getInstrument(), this, false);
        loopDialog.setLoopPlayer(new SoundPlayer(new File(activeProject.getDirectoryPath() + "/Midi/Loops/090 S01 Intro.mid"), 50, this, true));

        updateActivePhrase(activePhrase);
        updateIntervalMarker();

    }

    public void initializeModel() throws MidiUnavailableException {

        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        instruments = synthesizer.getDefaultSoundbank().getInstruments();

        activeProject = new Project("My Project");

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

        Compound compound;
        int i = 0;

        File projectFolder = new File(System.getProperty("user.home") + "/Documents/Sonify/Demo");
        if (projectFolder == null) {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose Project Folder");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                System.out.println("getCurrentDirectory(): "
                        + fileChooser.getCurrentDirectory());
                System.out.println("getSelectedFile(): "
                        + fileChooser.getSelectedFile());

                projectFolder = fileChooser.getSelectedFile();

            }

        }

        File[] directoryListing = new File(projectFolder + "/Compounds").listFiles();
        if (directoryListing != null) {
            for (File dataFile : directoryListing) {
                if (!dataFile.isHidden()) {
                    compound = new Compound(i, dataFile, "Infrared");
                    compound.load();
                    compound.getDataChart().createChart();
                    ArrayList<Peak> peaks = compound.getDataChart().process();
                    if (peaks != null) {
                        compound.setPeaks(peaks);
                        compoundComboBox.addItem(compound.getName());
                        activeProject.getCompoundsArray().add(compound);
                        i++;
                        System.out.println("Added compound: " + compound.getName());
                    }
                }
            }
        }

        activeProject.setDirectoryPath(projectFolder.getPath());
        activeProject.load(new File(projectFolder + "/project.son"));
        activeProject.setSaveFile(new File(projectFolder + "/project.son"));

        currentColorIndex = 2;
        measureScale = 25;

        activePhrase = activeProject.getPhrasesArray().get(0);

    }

    public void initializeView() throws FontFormatException, IOException, MidiUnavailableException, UnsupportedAudioFileException, LineUnavailableException, InvalidMidiDataException, URISyntaxException {

        frame = new JFrame();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Sonified Spectra - Musical Spectroscopic Analysis");
        frame.setSize(1280, 780);
        frame.setPreferredSize(new Dimension(1280, 780));
        frame.setLocationRelativeTo(null);
        //frame.setResizable(false);
        frame.setUndecorated(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getRootPane().setBorder(
                BorderFactory.createMatteBorder(4, 4, 4, 4,
                        Color.decode("#979797")));
        frame.getContentPane().setBackground(Color.decode("#E5E5E5"));

        this.mainView = (JPanel) frame.getContentPane();
        mainView.setPreferredSize(new Dimension(1280, 780));

        buttonBackgroundColor = Color.decode("#F5F5F5");
        buttonHighlightColor = Color.decode("#CAEFFF");
        isProject = true;

        compoundLabel = new JLabel("Compound:");
        Font hnt20 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/HelveticaNeue-Thin.otf")).deriveFont(Font.PLAIN, 20);
        compoundLabel.setFont(hnt20);
        compoundLabel.setBounds(10, 15, 101, 24);
        frame.getContentPane().add(compoundLabel);

        compoundComboBox = new JComboBox();
        for (Compound c : activeProject.getCompoundsArray()) {
            compoundComboBox.addItem(c.getName());
        }
        compoundComboBox.setBounds(113, 15, 158, 32);
        frame.getContentPane().add(compoundComboBox);

        spectrumLabel = new JLabel("Spectrum");
        spectrumLabel.setBounds(279, 18, 75, 20);
        frame.getContentPane().add(spectrumLabel);

        leftOrRightCheckbox = new JCheckBox();
        Icon leftIcon = new ImageIcon(getClass().getResource("/icons/leftcheckbox.png"));
        Icon rightIcon = new ImageIcon(getClass().getResource("/icons/rightcheckbox.png"));
        leftOrRightCheckbox.setIcon(leftIcon);
        leftOrRightCheckbox.setSelectedIcon(rightIcon);
        leftOrRightCheckbox.setSelected(false);
        leftOrRightCheckbox.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        leftOrRightCheckbox.setBounds(396, 11, 32, 32);
        frame.getContentPane().add(leftOrRightCheckbox);

        Icon importIcon = new ImageIcon(getClass().getResource("/icons/importcompound.png"));
        importCompoundButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        importCompoundButton.setIcon(importIcon);
        importCompoundButton.setBounds(435, 11, 32, 32);
        importCompoundButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        importCompoundButton.setBorderPainted(true);
        importCompoundButton.setFocusPainted(false);
        frame.getContentPane().add(importCompoundButton);

        Icon editIcon = new ImageIcon(getClass().getResource("/icons/editcompound.png"));
        editCompoundButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        editCompoundButton.setIcon(editIcon);
        editCompoundButton.setBounds(476, 11, 32, 32);
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
        graphPanel.setBounds(10, 52, 500, 400);
        graphPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        graphPanel.removeAll();
        graphPanel.add(chPanel, BorderLayout.CENTER);
        frame.getContentPane().add(graphPanel);

        notesLabel = new JLabel("Notes:");
        notesLabel.setFont(hnt20);
        notesLabel.setBounds(10, 460, 60, 24);
        frame.getContentPane().add(notesLabel);

        selectAllOrNoneCheckbox = new JCheckBox();
        Icon selicon = new ImageIcon(getClass().getResource("/icons/multseleccheckbox.png"));
        selectAllOrNoneCheckbox.setIcon(selicon);
        selectAllOrNoneCheckbox.setSelected(false);
        selectAllOrNoneCheckbox.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        selectAllOrNoneCheckbox.setBounds(190, 457, 32, 32);
        frame.getContentPane().add(selectAllOrNoneCheckbox);

        multipleSelectionCheckBox = new JCheckBox();
        if (activeProject.isNotesPanelMultipleSelection()) selicon = new ImageIcon(getClass().getResource("/icons/multseleccheckboxselected.png"));
        else selicon = new ImageIcon(getClass().getResource("/icons/multseleccheckbox.png"));
        multipleSelectionCheckBox.setIcon(selicon);
        multipleSelectionCheckBox.setSelected(activeProject.isNotesPanelMultipleSelection());
        multipleSelectionCheckBox.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        multipleSelectionCheckBox.setBounds(225, 457, 32, 32);
        frame.getContentPane().add(multipleSelectionCheckBox);

        transposeLabel = new JLabel("Transpose:");
        transposeLabel.setBounds(260, 468, 75, 20);
        frame.getContentPane().add(transposeLabel);

        transposeTextField = new JTextField();
        transposeTextField.setHorizontalAlignment(SwingConstants.CENTER);
        transposeTextField.setBackground(Color.decode("#F5F5F5"));
        transposeTextField.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        transposeTextField.setBounds(335, 457, 64, 32);
        TextPrompt transposePrompt = new TextPrompt(String.valueOf("-"), transposeTextField);
        transposePrompt.setHorizontalAlignment(TextPrompt.CENTER);
        frame.getContentPane().add(transposeTextField);

        Icon plusIcon = new ImageIcon(getClass().getResource("/icons/plusicon.png"));
        transposeUpButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        transposeUpButton.setIcon(plusIcon);
        transposeUpButton.setBounds(408, 457, 32, 32);
        transposeUpButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        transposeUpButton.setBorderPainted(true);
        transposeUpButton.setFocusPainted(false);
        frame.getContentPane().add(transposeUpButton);

        Icon minusIcon = new ImageIcon(getClass().getResource("/icons/minusicon.png"));
        transposeDownButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        transposeDownButton.setIcon(minusIcon);
        transposeDownButton.setBounds(443, 457, 32, 32);
        transposeDownButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        transposeDownButton.setBorderPainted(true);
        transposeDownButton.setFocusPainted(false);
        frame.getContentPane().add(transposeDownButton);

        Icon fillerIcon = new ImageIcon(getClass().getResource("/icons/fillericon.png"));
        fillerButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        fillerButton.setIcon(fillerIcon);
        fillerButton.setBounds(479, 457, 32, 32);
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
        notesScrollPane.getHorizontalScrollBar().setUI(new BetterScrollBarH());
        notesScrollPane.setViewportView(notesPanel);
        notesScrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        notesScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(450, 10));
        notesScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        notesScrollPane
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        notesScrollPane.setBounds(10, 493, 450, 95);
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

        noteVolumeSlider = new JSlider();
        noteVolumeSlider.setBounds(462, 490, 55, 100);
        noteVolumeSlider.setUI(new coloredThumbSliderUI1(noteVolumeSlider, activePhrase.getSelectedColor()));
        noteVolumeSlider.setOrientation(SwingConstants.VERTICAL);
        noteVolumeSlider.setMinimum(0);
        noteVolumeSlider.setMaximum(127);
        noteVolumeSlider.setValue(100);
        java.util.Hashtable labels = new java.util.Hashtable( );
        labels.put(new Integer(0), new JLabel("0"));
        labels.put(new Integer(127), new JLabel("127"));
        noteVolumeSlider.setLabelTable(labels);
        noteVolumeSlider.setPaintLabels(true);
        frame.getContentPane().add(noteVolumeSlider);

        noteVolumeLabel = new JLabel(String.valueOf(noteVolumeSlider.getValue()));
        noteVolumeLabel.setBounds(485, 530, 30, 20);
        frame.getContentPane().add(noteVolumeLabel);

        phrasesLabel = new JLabel("Phrases:");
        phrasesLabel.setFont(hnt20);
        phrasesLabel.setBounds(10, 599, 100, 24);
        frame.getContentPane().add(phrasesLabel);

        instrumentComboBox = new JComboBox();
        for (int j = 0; j < instruments.length; j++) {
            instrumentComboBox.addItem(instruments[j].getName());
        }
        instrumentComboBox.setSelectedIndex(activePhrase.getInstrument());
        instrumentComboBox.setBounds(164, 600, 200, 32);
        frame.getContentPane().add(instrumentComboBox);

        colorButton = new BetterButton(activePhrase.getUnselectedColor(), 32, 32, 6);
        colorButton.setBounds(367, 600, 32, 32);
        colorButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        colorButton.setBorderPainted(true);
        colorButton.setFocusPainted(false);
        frame.getContentPane().add(colorButton);

        editPhraseButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        editPhraseButton.setIcon(editIcon);
        editPhraseButton.setBounds(404, 600, 32, 32);
        editPhraseButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        editPhraseButton.setBorderPainted(true);
        editPhraseButton.setFocusPainted(false);
        frame.getContentPane().add(editPhraseButton);

        addPhraseButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        addPhraseButton.setIcon(plusIcon);
        addPhraseButton.setBounds(440, 600, 32, 32);
        addPhraseButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        addPhraseButton.setBorderPainted(true);
        addPhraseButton.setFocusPainted(false);
        frame.getContentPane().add(addPhraseButton);

        removePhraseButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        removePhraseButton.setIcon(minusIcon);
        removePhraseButton.setBounds(476, 600, 32, 32);
        removePhraseButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        removePhraseButton.setBorderPainted(true);
        removePhraseButton.setFocusPainted(false);
        frame.getContentPane().add(removePhraseButton);

        phrasesPanel = new JPanel();
        phrasesPanel.setLayout(null);
        phrasesPanel.setBorder(null);
        phrasesPanel.setBounds(10, 639, 500, 100);
        phrasesPanel.setBackground(Color.decode("#F5F5F5"));

        JScrollPane phrasesScrollPane = new JScrollPane();
        phrasesScrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        phrasesScrollPane.setBackground(Color.decode("#000000"));
        phrasesScrollPane.getHorizontalScrollBar().setUI(new BetterScrollBarH());
        phrasesScrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        phrasesScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(450, 10));
        phrasesScrollPane.setViewportView(phrasesPanel);
        phrasesScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        phrasesScrollPane
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        phrasesScrollPane.setBounds(10, 639, 450, 92);
        frame.getContentPane().add(phrasesScrollPane);

        phraseViewArray = new ArrayList<PhraseView>();

        int j = 0;

        for (Phrase phrase : activeProject.getPhrasesArray()) {
            PhraseView phraseView = new PhraseView(phrase);
            phraseView.setBackground(phraseView.getPhrase().getUnselectedColor());
            phraseView.updatePanel();
            phraseView.setBounds(10 + 130 * j, 5, 120, 70);
            j++;
            phraseViewArray.add(phraseView);
            phrasesPanel.add(phraseView);
        }

        phrasesPanel.setPreferredSize(new Dimension(10 + 110 * activeProject.getPhrasesArray().size(), 100));

        Icon addphrasetotrackicon = new ImageIcon(getClass().getResource("/icons/addphrasetotrackicon.png"));
        addPhraseToTrackButton = new BetterButton(Color.decode("#F5F5F5"), 40, 40, 6);
        addPhraseToTrackButton.setIcon(addphrasetotrackicon);
        addPhraseToTrackButton.setBounds(465, 669, 40, 40);
        addPhraseToTrackButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        addPhraseToTrackButton.setBorderPainted(true);
        addPhraseToTrackButton.setFocusPainted(false);
        frame.getContentPane().add(addPhraseToTrackButton);

        projectLabel = new JLabel("Project:");
        projectLabel.setFont(hnt20);
        projectLabel.setBounds(525, 15, 70, 24);
        frame.getContentPane().add(projectLabel);

        titleTextField = new JTextField();
        titleTextField.setHorizontalAlignment(SwingConstants.CENTER);
        titleTextField.setBackground(Color.decode("#F5F5F5"));
        titleTextField.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        titleTextField.setBounds(599, 11, 140, 32);
        titleTextField.setText(activeProject.getName());
        titleTextField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        frame.getContentPane().add(titleTextField);

        Icon newIcon = new ImageIcon(getClass().getResource("/icons/newicon.png"));
        newButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        newButton.setIcon(newIcon);
        newButton.setBounds(748, 11, 32, 32);
        newButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        newButton.setBorderPainted(true);
        newButton.setFocusPainted(false);
        frame.getContentPane().add(newButton);

        Icon openIcon = new ImageIcon(getClass().getResource("/icons/openicon.png"));
        openButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        openButton.setIcon(openIcon);
        openButton.setBounds(790, 11, 32, 32);
        openButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        openButton.setBorderPainted(true);
        openButton.setFocusPainted(false);
        frame.getContentPane().add(openButton);

        Icon saveIcon = new ImageIcon(getClass().getResource("/icons/saveicon.png"));
        saveButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        saveButton.setIcon(saveIcon);
        saveButton.setBounds(832, 11, 32, 32);
        saveButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        saveButton.setBorderPainted(true);
        saveButton.setFocusPainted(false);
        frame.getContentPane().add(saveButton);

        Icon settingsIcon = new ImageIcon(getClass().getResource("/icons/settingsicon.png"));
        settingsButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        settingsButton.setIcon(settingsIcon);
        settingsButton.setBounds(874, 11, 32, 32);
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
        helpTextPane.setBounds(914, 11, 350, 32);
        frame.getContentPane().add(helpTextPane);

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(525, 52, 700, 510);
        layeredPane.setLayout(null);
        frame.getContentPane().add(layeredPane);

        outTracksPanel = new JPanel();
        outTracksPanel.setLayout(null);
        outTracksPanel.setBorder(null);
        outTracksPanel.setBounds(0, 0, 700, 510);
        outTracksPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        outTracksPanel.setBackground(Color.decode("#F5F5F5"));
        layeredPane.add(outTracksPanel, 0);

        JLabel trackOptionsLabel = new JLabel("Tracks:");
        trackOptionsLabel.setBounds(2, 8, 50, 14);
        outTracksPanel.add(trackOptionsLabel);

        Icon tracksmultselecticon;
        tracksMultSelectCheckbox = new JCheckBox();
        if (activeProject.isTracksPanelMultipleSelection()) tracksmultselecticon = new ImageIcon(getClass().getResource("/icons/tracksmultselected.png"));
        else tracksmultselecticon = new ImageIcon(getClass().getResource("/icons/tracksmultselecticon.png"));
        tracksMultSelectCheckbox.setIcon(tracksmultselecticon);
        tracksMultSelectCheckbox.setSelected(activeProject.isTracksPanelMultipleSelection());
        tracksMultSelectCheckbox.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        tracksMultSelectCheckbox.setBounds(49, 5, 21, 21);
        outTracksPanel.add(tracksMultSelectCheckbox);

        Icon loopicon = new ImageIcon(getClass().getResource("/icons/loopicon.png"));
        loopButton = new BetterButton(Color.decode("#F5F5F5"), 21, 21, 6);
        loopButton.setIcon(loopicon);
        loopButton.setBounds(74, 5, 21, 21);
        loopButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        loopButton.setBorderPainted(true);
        loopButton.setFocusPainted(false);
        outTracksPanel.add(loopButton);

        Icon addtrackicon = new ImageIcon(getClass().getResource("/icons/addtrackicon.png"));
        addTrackButton = new BetterButton(Color.decode("#F5F5F5"), 21, 21, 6);
        addTrackButton.setIcon(addtrackicon);
        addTrackButton.setBounds(100, 5, 21, 21);
        addTrackButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        addTrackButton.setBorderPainted(true);
        addTrackButton.setFocusPainted(false);
        outTracksPanel.add(addTrackButton);

        Icon removetrackicon = new ImageIcon(getClass().getResource("/icons/removetrackicon.png"));
        removeTrackButton = new BetterButton(Color.decode("#F5F5F5"), 21, 21, 6);
        removeTrackButton.setIcon(removetrackicon);
        removeTrackButton.setBounds(125, 5, 21, 21);
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
        trackHeadScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        trackHeadScrollPane.getHorizontalScrollBar().setUI(new BetterScrollBarH());
        trackHeadScrollPane.setViewportView(trackHeadPanel);
        trackHeadScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        trackHeadScrollPane
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        trackHeadScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        trackHeadScrollPane.setBounds(0, 33, 150, 465);
        outTracksPanel.add(trackHeadScrollPane);

        trackHeadViewArray = new ArrayList<TrackHeadView>();

        int j1 = 0;

        for (Track t : activeProject.getTracksArray()) {
            TrackHeadView thv = new TrackHeadView(t, instruments);
            thv.setBounds(0, 0 + j1 * 70, 150, 70);
            thv.getTrackNumberLabel().setText(String.valueOf(j1 + 1));
            if (j1 % 2 != 0 && j1 != 0) thv.setBackColor(Color.decode("#DDDDDD"));
            else thv.setBackColor(Color.decode("#F5F5F5"));
            thv.updatePanel();
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
        measureHeadScrollPane.getHorizontalScrollBar().setUI(new BetterScrollBarH());
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
            MeasureHeadView mhv = new MeasureHeadView(this, k + 1);
            mhv.setBackground(buttonBackgroundColor);
            mhv.setBounds(0 + k * measureScale * 4, 0, measureScale * 4, 33);
            if (k % 2 != 0 && k != 0) mhv.setBackColor(Color.decode("#DDDDDD"));
            else mhv.setBackColor(Color.decode("#F5F5F5"));
            mhv.updatePanel();
            measureHeadViewArray.add(mhv);
            measureHeadPanel.add(mhv);
        }

        measureHeadPanel.setPreferredSize(new Dimension(2 + measureScale * 4 * measureHeadViewArray.size(), 33));

        inTracksPanel = new JPanel();
        inTracksPanel.setLayout(null);
        inTracksPanel.setBorder(null);
        inTracksPanel.setBackground(Color.decode("#F5F5F5"));

        tracksScrollPane = new JScrollPane();
        tracksScrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        tracksScrollPane.setBackground(Color.decode("#F5F5F5"));
        tracksScrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        tracksScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        tracksScrollPane.getHorizontalScrollBar().setUI(new BetterScrollBarH());
        tracksScrollPane.getVerticalScrollBar().setUI(new BetterScrollBarV());
        tracksScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 700));
        tracksScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(700, 10));
        tracksScrollPane.setViewportView(inTracksPanel);
        tracksScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tracksScrollPane
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        tracksScrollPane.setBounds(150, 33, 550, 477);
        outTracksPanel.add(tracksScrollPane);

        trackViewArray = new ArrayList<TrackView>();

        int j3 = 0;

        for (Track t : activeProject.getTracksArray()) {
            TrackView tv = new TrackView(t, this);
            tv.setBounds(0, 70 * j3, 100 * activeProject.getNumMeasures(), 70);
            if (j3 % 2 != 0 && j3 != 0) tv.setBackColor(Color.decode("#DDDDDD"));
            else tv.setBackColor(Color.decode("#F5F5F5"));
            tv.updatePanel();
            j3++;
            trackViewArray.add(tv);
            inTracksPanel.add(tv);
        }

        inTracksPanel.setPreferredSize(new Dimension(100 * activeProject.getNumMeasures(),
                70 * trackViewArray.size()));

        playbackLinePanel = new JPanel();
        playbackLinePanel.setBackground(new Color(0, 0, 0, 0));
        playbackLinePanel.setLayout(null);
        playbackLinePanel.setPreferredSize(new Dimension(550, 510));
        playbackLinePanel.setBounds(150, 0, 550, 510);
        playbackLinePanel.setOpaque(false);
        layeredPane.add(playbackLinePanel, 100);
        layeredPane.moveToFront(playbackLinePanel);

        playbackLine = new Line(5);
        playbackLine.setBackground(activePhrase.getBorderColor());
        playbackLine.repaint();
        playbackLinePanel.add(playbackLine);

        playbackLabel = new JLabel("Playback:");
        playbackLabel.setFont(hnt20);
        playbackLabel.setBounds(525, 566, 81, 24);
        frame.getContentPane().add(playbackLabel);

        playbackPanel = new JPanel();
        playbackPanel.setLayout(null);
        playbackPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        playbackPanel.setBounds(525, 600, 700, 132);
        playbackPanel.setBackground(Color.decode("#F5F5F5"));
        frame.getContentPane().add(playbackPanel);

        playbackSlider = new JSlider();
        playbackSlider.setUI(new coloredThumbSliderUI2(noteVolumeSlider, activePhrase.getSelectedColor()));
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

        Icon playicon = new ImageIcon(getClass().getResource("/icons/playicon.png"));
        playButton = new BetterButton(Color.decode("#F5F5F5"), 40, 40, 16);
        playButton.setIcon(playicon);
        playButton.setBounds(83, 51, 40, 40);
        playButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        playButton.setBorderPainted(true);
        playButton.setFocusPainted(false);
        playbackPanel.add(playButton);

        Icon stopicon = new ImageIcon(getClass().getResource("/icons/stopicon.png"));
        stopButton = new BetterButton(Color.decode("#F5F5F5"), 40, 40, 16);
        stopButton.setIcon(stopicon);
        stopButton.setBounds(129, 51, 40, 40);
        stopButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        stopButton.setBorderPainted(true);
        stopButton.setFocusPainted(false);
        playbackPanel.add(stopButton);

        quantizeCheckBox = new JCheckBox();
        quantizeCheckBox.setSelected(false);
        quantizeCheckBox.setText("Quantize");
        quantizeCheckBox.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        quantizeCheckBox.setBounds(83, 99, 105, 20);
        playbackPanel.add(quantizeCheckBox);

        keyComboBox = new JComboBox();
        keysArray.forEach(keyComboBox::addItem);
        keyComboBox.setBounds(163, 96, 95, 28);
        keyComboBox.setSelectedItem("C");
        playbackPanel.add(keyComboBox);

        qualityComboBox = new JComboBox();

        qualityArray.forEach(qualityComboBox::addItem);
        qualityComboBox.setBounds(258, 96, 150, 28);
        qualityComboBox.setSelectedItem( "Major" );
        playbackPanel.add(qualityComboBox);

        qrhythmComboBox = new JComboBox();
        for (String s : rhythmArray) {
            qrhythmComboBox.addItem(s);
        }
        qrhythmComboBox.setBounds(410, 96, 95, 28);
        qrhythmComboBox.setSelectedItem("1/16");
        playbackPanel.add(qrhythmComboBox);

        JLabel tempoLabel = new JLabel("Tempo:");
        tempoLabel.setBounds(524, 57, 60, 17);
        playbackPanel.add(tempoLabel);

        tempoTextField = new JTextField();
        tempoTextField.setHorizontalAlignment(SwingConstants.CENTER);
        tempoTextField.setBackground(Color.decode("#F5F5F5"));
        tempoTextField.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        tempoTextField.setBounds(582, 50, 51, 28);
        tempoTextField.setText(String.valueOf(activeProject.getTempo()));
        tempoTextField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        playbackPanel.add(tempoTextField);

        JLabel bpmLabel = new JLabel("bpm");
        bpmLabel.setBounds(645, 57, 40, 17);
        playbackPanel.add(bpmLabel);

        Icon movepitvrighticon = new ImageIcon(getClass().getResource("/icons/movepitvrighticon.png"));
        movePitvRightButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        movePitvRightButton.setIcon(movepitvrighticon);
        movePitvRightButton.setBounds(1230, 52, 32, 32);
        movePitvRightButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        movePitvRightButton.setBorderPainted(true);
        movePitvRightButton.setFocusPainted(false);
        frame.getContentPane().add(movePitvRightButton);

        movePitvTextField = new JTextField();
        movePitvTextField.setHorizontalAlignment(SwingConstants.CENTER);
        movePitvTextField.setBackground(Color.decode("#F5F5F5"));
        movePitvTextField.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        movePitvTextField.setBounds(1230, 85, 32, 32);
        movePitvTextField.setText(String.valueOf(activeProject.getMovePitvFactor()));
        movePitvTextField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        frame.getContentPane().add(movePitvTextField);

        Icon movepitvlefticon = new ImageIcon(getClass().getResource("/icons/movepitvlefticon.png"));
        movePitvLeftButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        movePitvLeftButton.setIcon(movepitvlefticon);
        movePitvLeftButton.setBounds(1230, 119, 32, 32);
        movePitvLeftButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        movePitvLeftButton.setBorderPainted(true);
        movePitvLeftButton.setFocusPainted(false);
        frame.getContentPane().add(movePitvLeftButton);

        Icon duplicatephraseicon = new ImageIcon(getClass().getResource("/icons/duplicatephraseicon.png"));
        duplicatePhraseButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        duplicatePhraseButton.setIcon(duplicatephraseicon);
        duplicatePhraseButton.setBounds(1230, 153, 32, 32);
        duplicatePhraseButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        duplicatePhraseButton.setBorderPainted(true);
        duplicatePhraseButton.setFocusPainted(false);
        frame.getContentPane().add(duplicatePhraseButton);

        Icon measuresIcon = new ImageIcon(getClass().getResource("/icons/measuresicon.png"));
        measuresButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        measuresButton.setIcon(measuresIcon);
        measuresButton.setBounds(1230, 188, 32, 32);
        measuresButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        measuresButton.setBorderPainted(true);
        measuresButton.setFocusPainted(false);
        frame.getContentPane().add(measuresButton);

        measureZoomSlider = new JSlider();
        measureZoomSlider.setBounds(1230, 225, 32, 100);
        measureZoomSlider.setUI(new coloredThumbSliderUI1(noteVolumeSlider, activePhrase.getSelectedColor()));
        measureZoomSlider.setOrientation(SwingConstants.VERTICAL);
        measureZoomSlider.setMinimum(5);
        measureZoomSlider.setMaximum(80);
        measureZoomSlider.setValue(25);
        frame.getContentPane().add(measureZoomSlider);

        fillerDialog = new FillerNotesDialog(this);
        fillerDialog.pack();
        final int width = fillerDialog.getWidth();
        final int height = fillerDialog.getHeight();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width / 2) - (width / 2);
        int y = (screenSize.height / 2) - (height / 2);
        fillerDialog.setLocation( x, y );
        fillerDialog.setVisible(false);

        editCompoundDialog = new EditCompoundDialog(this);
        editCompoundDialog.pack();
        final int width2 = editCompoundDialog.getWidth();
        final int height2 = editCompoundDialog.getHeight();
        int x2 = (screenSize.width / 2) - (width2 / 2);
        int y2 = (screenSize.height / 2) - (height2 / 2);
        editCompoundDialog.setLocation( x2, y2 );
        editCompoundDialog.setVisible(false);

        editPhraseDialog = new EditPhraseDialog(this);
        editPhraseDialog.pack();
        final int width4 = editPhraseDialog.getWidth();
        final int height4 = editPhraseDialog.getHeight();
        int x4 = (screenSize.width / 2) - (width4 / 2);
        int y4 = (screenSize.height / 2) - (height4 / 2);
        editPhraseDialog.setLocation( x4, y4 );
        editPhraseDialog.setVisible(false);

        newProjectDialog = new NewProjectDialog(this);
        newProjectDialog.pack();
        final int width5 = newProjectDialog.getWidth();
        final int height5 = newProjectDialog.getHeight();
        int x5 = (screenSize.width / 2) - (width5 / 2);
        int y5 = (screenSize.height / 2) - (height5 / 2);
        newProjectDialog.setLocation(x5, y5);
        newProjectDialog.setVisible(false);

        settingsDialog = new SettingsDialog(this);
        settingsDialog.pack();
        final int width6 = settingsDialog.getWidth();
        final int height6 = settingsDialog.getHeight();
        int x6 = (screenSize.width / 2) - (width6 / 2);
        int y6 = (screenSize.height / 2) - (height6 / 2);
        settingsDialog.setLocation(x6, y6);
        settingsDialog.setVisible(false);

        loopDialog = new LoopDialog(this);
        loopDialog.pack();
        final int width3 = loopDialog.getWidth();
        final int height3 = loopDialog.getHeight();
        int x3 = (screenSize.width / 2) - (width3 / 2);
        int y3 = (screenSize.height / 2) - (height3 / 2);
        loopDialog.setLocation(x3, y3);
        loopDialog.setVisible(false);

        measuresDialog = new MeasuresDialog(this);
        measuresDialog.pack();
        final int width7 = measuresDialog.getWidth();
        final int height7 = measuresDialog.getHeight();
        int x7 = (screenSize.width / 2) - (width7 / 2);
        int y7 = (screenSize.height / 2) - (height7 / 2);
        measuresDialog.setLocation(x7, y7);
        measuresDialog.setVisible(false);

    }

    public void initializeControllers() {

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
        chPanel.addMouseListener(new HelpTextController(this, HelpStrings.CHART_PANEL));
        graphPanel.addMouseListener(new HelpTextController(this, HelpStrings.GRAPH_PANEL));

        selectAllOrNoneCheckbox.addActionListener(new SelectAllOrNoneController(this, activeProject, selectAllOrNoneCheckbox));
        selectAllOrNoneCheckbox.addMouseListener(new HelpTextController(this, HelpStrings.SELECT_ALL_NONE));

        multipleSelectionCheckBox.addActionListener(new MultipleNoteSelectionController(this, activeProject, multipleSelectionCheckBox));
        multipleSelectionCheckBox.addMouseListener(new HelpTextController(this, HelpStrings.MULT_SELEC));

        transposeTextField.addMouseListener(new HelpTextController(this, HelpStrings.TRANSPOSE_TEXT));
        transposeTextField.addActionListener(new TransposeTextFieldController(this));

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

        noteVolumeSlider.addChangeListener(new NoteVolumeController(this, activeProject));
        noteVolumeSlider.addMouseListener(new HelpTextController(this, HelpStrings.NOTE_VOLUME));

        instrumentComboBox.addItemListener(new PhraseInstrumentController(this));

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

        AddPhraseToTrackController addPhraseToTrackController = new AddPhraseToTrackController(this, activeProject);
        addPhraseToTrackButton.addMouseListener(new HelpTextController(this, HelpStrings.ADD_PHRASE_TO_TRACK));
        addPhraseToTrackButton.addActionListener(addPhraseToTrackController);
        addPhraseToTrackButton.addMouseListener(addPhraseToTrackController);

        trackHeadPanel.addMouseListener(new DeselectController(this));
        inTracksPanel.addMouseListener(new DeselectController(this));

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
            tv.addMouseListener(new DeselectController(this));
            for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray()) {
                pitv.getTopPanel().addMouseListener(new PhraseInTrackController(this, activeProject, pitv));
                pitv.getTopPanel().addMouseListener(new HelpTextController(this, HelpStrings.PITV));
                RemovePhraseFromTrackController removePhraseFromTrackController = new RemovePhraseFromTrackController(this, activeProject, pitv, tv);
                pitv.getRemoveButton().addMouseListener(new HelpTextController(this, HelpStrings.REMOVE_PHRASE_FROM_TRACK));
                pitv.getRemoveButton().addActionListener(removePhraseFromTrackController);
                pitv.getRemoveButton().addMouseListener(removePhraseFromTrackController);
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

        MovePitvController movePitvRightController = new MovePitvController(this, activeProject, 0);
        movePitvRightButton.addMouseListener(new HelpTextController(this, HelpStrings.MOVE_PITV_RIGHT));
        movePitvRightButton.addActionListener(movePitvRightController);
        movePitvRightButton.addMouseListener(movePitvRightController);

        MovePitvController movePitvLeftController = new MovePitvController(this, activeProject, 1);
        movePitvLeftButton.addMouseListener(new HelpTextController(this, HelpStrings.MOVE_PITV_LEFT));
        movePitvLeftButton.addActionListener(movePitvLeftController);
        movePitvLeftButton.addMouseListener(movePitvLeftController);

        DuplicatePhraseController duplicatePhraseController = new DuplicatePhraseController(this);
        duplicatePhraseButton.addMouseListener(new HelpTextController(this, HelpStrings.DUPLICATE_PHRASE));
        duplicatePhraseButton.addMouseListener(duplicatePhraseController);

        tracksMultSelectCheckbox.addActionListener(new TrackMultSelectController(this, activeProject, tracksMultSelectCheckbox));
        tracksMultSelectCheckbox.addMouseListener(new HelpTextController(this, HelpStrings.TRACKS_MULT_SELECT));

        LoopController loopController = new LoopController(this, activeProject);
        loopButton.addMouseListener(new HelpTextController(this, HelpStrings.LOOP));
        loopButton.addActionListener(loopController);
        loopButton.addMouseListener(loopController);

        measuresButton.addMouseListener(new HelpTextController(this, HelpStrings.MEASURES));
        measuresButton.addMouseListener(new MeasuresController(this));

        measureZoomSlider.addChangeListener(new MeasureScaleController(this, activeProject));
        measureZoomSlider.addMouseListener(new HelpTextController(this, HelpStrings.MEASURE_SCALE));

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
        playButton.addMouseListener(playPauseController);

        SoundPlayerController stopController = new SoundPlayerController(this, activeProject, 1);
        stopButton.addMouseListener(new HelpTextController(this, HelpStrings.STOP));
        stopButton.addActionListener(stopController);
        stopButton.addMouseListener(stopController);

        LoopPlayerController loopPlayPauseController = new LoopPlayerController(this, activeProject, 0);
        loopDialog.getPlayButton().addMouseListener(loopPlayPauseController);

        LoopPlayerController loopStopController = new LoopPlayerController(this, activeProject, 1);
        loopDialog.getStopButton().addActionListener(loopStopController);
        loopDialog.getStopButton().addMouseListener(loopStopController);

        quantizeCheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                soundPlayer.reset();
                soundPlayer.updateSoundPlayer();
            }
        });

        keyComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                soundPlayer.reset();
                soundPlayer.updateSoundPlayer();
            }
        });

        qualityComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {soundPlayer.reset();
                soundPlayer.updateSoundPlayer();
            }
        });

        qrhythmComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                soundPlayer.reset();
                soundPlayer.updateSoundPlayer();
            }
        });

    }

    public void updateActivePhrase(Phrase phrase) {

        activePhrase = phrase;

        temp = false;
        if (!phrase.isLoop()) compoundComboBox.setSelectedIndex(activePhrase.getCompound().getId());
        temp = true;

        for (PhraseView pv : phraseViewArray) {
            if (pv.getPhrase().getParentPhrase() == null) {
                if (pv.getPhrase().getId() != activePhrase.getId()) pv.getPhrase().setSelected(false);
                else activePhrase.setSelected(true);
            }
            else {
                if (pv.getPhrase().getParentPhrase().getId() != activePhrase.getId()) pv.getPhrase().getParentPhrase().setSelected(false);
                else activePhrase.setSelected(true);
            }
            pv.updatePanel();
        }

        notesPanel.removeAll();
        notesPanel.setLayout(null);

        noteViewArray = new ArrayList<NoteView>();

        int i = 0;

        if (!activePhrase.isLoop()) {

            for (Note note : activePhrase.getNotesArray()) {
                NoteView noteView = new NoteView(note);
                noteView.setBounds(10 + 44 * i, 10, 34, 67);
                noteView.setBorder(BorderFactory.createLineBorder(activePhrase.getBorderColor(), 1, true));
                noteView.updatePanel();
                i++;
                if (!noteView.getNote().isFiller())
                    noteView.addMouseListener(new HelpTextController(this, HelpStrings.NOTE_VIEW));
                else noteView.addMouseListener(new HelpTextController(this, HelpStrings.FILLER_NOTE_VIEW));
                noteView.addMouseListener(new NoteController(activeProject, this, noteView));
                noteViewArray.add(noteView);
                notesPanel.add(noteView);
            }

        }

        notesPanel.setPreferredSize(new Dimension(10 + 44 * activePhrase.getNotesArray().size(), 100));

        minTextField.setText(String.valueOf(activePhrase.getMinPitch()));
        maxTextField.setText(String.valueOf(activePhrase.getMaxPitch()));
        instrumentComboBox.setSelectedIndex(activePhrase.getInstrument());

        colorButton.setCol(activePhrase.getUnselectedColor());
        colorButton.repaint();

        if (!phrase.isLoop()) {
            activePhrase.getCompound().getDataChart().getDataChart().getTitle().setText(
                    activePhrase.getCompound().getName());
        }

        for (TrackView tv : trackViewArray) {
            if (tv.getTrack().isSelected()) {
                tv.setBorder(BorderFactory.createLineBorder(activePhrase.getBorderColor(), 3, false));
            }
            for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray()) {
                pitv.adjustSize(tv.getTrack().isExpanded());
                if (pitv.getPhrase().isLoop()) pitv.getNameLabel().setForeground(Color.BLACK);
                if (activePhrase.getId() == pitv.getPhrase().getId() || (pitv.getPhrase().getParentPhrase() != null && activePhrase.getId() == pitv.getPhrase().getParentPhrase().getId())) {
                    if (!pitv.getPhrase().isLoop()) {
                        pitv.getNameLabel().setText(activePhrase.getCompound().getName());
                        pitv.getTopPanel().setBorder(BorderFactory.createLineBorder(activePhrase.getBorderColor(), 2, false));
                    }
                    pitv.repaint();
                }
                else {
                    pitv.getTopPanel().setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
                    pitv.repaint();
                }
            }
        }

        for (TrackHeadView thv : trackHeadViewArray) {
            if (thv.getTrack().isSelected()) {
                thv.setBackground(activePhrase.getUnselectedColor());
                thv.setBorder(BorderFactory.createLineBorder(activePhrase.getBorderColor(), 3, false));
            }
        }

        for (MeasureHeadView mhv : measureHeadViewArray) {
            if (mhv.isSelected()) {
                mhv.setBackground(activePhrase.getUnselectedColor());
                mhv.setBorder(BorderFactory.createLineBorder(activePhrase.getBorderColor(), 2, false));
            }
        }

        if (isProject) {
            playProjectButton.setCol(activePhrase.getUnselectedColor());
            playProjectButton.repaint();
        }
        else {
            playPhraseButton.setCol(activePhrase.getUnselectedColor());
            playPhraseButton.repaint();
        }

        soundPlayer.reset();
        soundPlayer.updateSoundPlayer();

        noteVolumeSlider.setUI(new coloredThumbSliderUI1(noteVolumeSlider, activePhrase.getSelectedColor()));
        measureZoomSlider.setUI(new coloredThumbSliderUI1(noteVolumeSlider, activePhrase.getSelectedColor()));
        soundPlayer.getProgress().setUI(new coloredThumbSliderUI2(noteVolumeSlider, activePhrase.getSelectedColor()));
        soundPlayer.getTempo().setUI(new coloredThumbSliderUI2(noteVolumeSlider, activePhrase.getSelectedColor()));

        playbackLine.setBackground(activePhrase.getBorderColor());

        frame.pack();

    }

    public void updateIntervalMarker() {
        Marker marker;

        if (activePhrase.getX2() != 0.0 && activePhrase.getX1() != 0.0) {

            marker = new IntervalMarker(activePhrase.getX2(), activePhrase.getX1());

        } else {

            marker = new IntervalMarker(0, 0);

        }

        marker.setStroke(new BasicStroke(50));
        marker.setPaint(activePhrase.getSelectedColor());
        marker.setAlpha(0.25f);

        if (activePhrase.getCompound().getDataChart() != null) {

            XYPlot plot = activePhrase.getCompound().getDataChart().getDataChart().getXYPlot();

            plot.clearDomainMarkers();

            for ( Note n : activePhrase.getSelectedNotes() ) {

                if (!n.isFiller()) plot.addDomainMarker(addNoteMarker(n));

            }

            plot.addDomainMarker(marker);

        }
    }

    public Marker addNoteMarker(Note n) {

        Color color = new Color(0, 0, 0);

        double x1 = n.getPeak().getX1();
        double x2 = n.getPeak().getX2();

        if (activePhrase.getNotesArray().indexOf(n) == 0) x1 = activePhrase.getX1();

        if (activePhrase.getNotesArray().indexOf(n) == activePhrase.getNotesArray().size() - 1)
            x2 = activePhrase.getX2();

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

    public void incrementColorIndex() {
        currentColorIndex++;
        if (currentColorIndex >= 8) currentColorIndex = 0;
    }

    public ArrayList<Integer> getSelectedMeasures() {

        ArrayList<Integer> selectedMeasures = new ArrayList<Integer>();

        for (MeasureHeadView mhv : measureHeadViewArray) {
            if (mhv.isSelected()) selectedMeasures.add(mhv.getMeasureNumber());
        }

        return selectedMeasures;

    }

    public ArrayList<TrackHeadView> getSelectedTrackHeads() {

        ArrayList<TrackHeadView> selectedTrackHeads = new ArrayList<TrackHeadView>();

        for (TrackHeadView thv : trackHeadViewArray) {
            if (thv.getTrack().isSelected()) selectedTrackHeads.add(thv);
        }

        return selectedTrackHeads;

    }

    public ArrayList<TrackView> getSelectedTrackViews() {

        ArrayList<TrackView> selectedTrackViews = new ArrayList<TrackView>();

        for (TrackView tv : trackViewArray) {
            if (tv.getTrack().isSelected()) selectedTrackViews.add(tv);
        }

        return selectedTrackViews;

    }

    public static void main(String[] args) throws FileNotFoundException, FontFormatException, IOException,
            MidiUnavailableException, UnsupportedAudioFileException, LineUnavailableException,
            InvalidMidiDataException, URISyntaxException {

        Sonify app = new Sonify();
        app.frame.setVisible(true);

    }

    static void renderSplashFrame(Graphics2D g, int frame) {
        final String[] comps = {"foo", "bar", "baz"};
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(120, 140, 200, 40);
        g.setPaintMode();
        g.setColor(Color.BLACK);
    }

    class coloredThumbSliderUI1 extends BasicSliderUI {

        Color thumbColor;
        coloredThumbSliderUI1(JSlider s, Color tColor) {
            super(s);
            thumbColor=tColor;
        }

        public void paint( Graphics g, JComponent c ) {
            recalculateIfInsetsChanged();
            recalculateIfOrientationChanged();
            Rectangle clip = g.getClipBounds();

            if ( slider.getPaintTrack() && clip.intersects( trackRect ) ) {
                paintTrack( g );
            }
            if ( slider.getPaintTicks() && clip.intersects( tickRect ) ) {
                paintTicks( g );
            }
            if ( slider.getPaintLabels() && clip.intersects( labelRect ) ) {
                paintLabels( g );
            }
            if ( slider.hasFocus() && clip.intersects( focusRect ) ) {
                paintFocus( g );
            }
            if ( clip.intersects( thumbRect ) ) {
                Color savedColor = slider.getBackground();
                slider.setBackground(thumbColor);
                paintThumb( g );
                slider.setBackground(new Color(229, 229, 229));
            }
        }
    }

    class coloredThumbSliderUI2 extends BasicSliderUI {

        Color thumbColor;
        coloredThumbSliderUI2(JSlider s, Color tColor) {
            super(s);
            thumbColor=tColor;
        }

        public void paint( Graphics g, JComponent c ) {
            recalculateIfInsetsChanged();
            recalculateIfOrientationChanged();
            Rectangle clip = g.getClipBounds();

            if ( slider.getPaintTrack() && clip.intersects( trackRect ) ) {
                paintTrack( g );
            }
            if ( slider.getPaintTicks() && clip.intersects( tickRect ) ) {
                paintTicks( g );
            }
            if ( slider.getPaintLabels() && clip.intersects( labelRect ) ) {
                paintLabels( g );
            }
            if ( slider.hasFocus() && clip.intersects( focusRect ) ) {
                paintFocus( g );
            }
            if ( clip.intersects( thumbRect ) ) {
                Color savedColor = slider.getBackground();
                slider.setBackground(thumbColor);
                paintThumb( g );
                slider.setBackground(new Color(245, 245, 245));
            }
        }
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

    public FillerNotesDialog getFillerDialog() {
        return fillerDialog;
    }

    public void setFillerDialog(FillerNotesDialog fillerDialog) {
        this.fillerDialog = fillerDialog;
    }

    public EditCompoundDialog getEditCompoundDialog() {
        return editCompoundDialog;
    }

    public void setEditCompoundDialog(EditCompoundDialog editCompoundDialog) {
        this.editCompoundDialog = editCompoundDialog;
    }

    public int getCurrentColorIndex() {
        return currentColorIndex;
    }

    public void setCurrentColorIndex(int currentColorIndex) {
        this.currentColorIndex = currentColorIndex;
    }

    public BetterButton getAddPhraseToTrackButton() {
        return addPhraseToTrackButton;
    }

    public void setAddPhraseToTrackButton(BetterButton addPhraseToTrackButton) {
        this.addPhraseToTrackButton = addPhraseToTrackButton;
    }

    public BetterButton getMovePitvRightButton() {
        return movePitvRightButton;
    }

    public void setMovePitvRightButton(BetterButton movePitvRightButton) {
        this.movePitvRightButton = movePitvRightButton;
    }

    public BetterButton getMovePitvLeftButton() {
        return movePitvLeftButton;
    }

    public void setMovePitvLeftButton(BetterButton movePitvLeftButton) {
        this.movePitvLeftButton = movePitvLeftButton;
    }

    public JTextField getMovePitvTextField() {
        return movePitvTextField;
    }

    public void setMovePitvTextField(JTextField movePitvTextField) {
        this.movePitvTextField = movePitvTextField;
    }

    public JCheckBox getTracksMultSelectCheckbox() {
        return tracksMultSelectCheckbox;
    }

    public void setTracksMultSelectCheckbox(JCheckBox tracksMultSelectCheckbox) {
        this.tracksMultSelectCheckbox = tracksMultSelectCheckbox;
    }

    public JSlider getNoteVolumeSlider() {
        return noteVolumeSlider;
    }

    public void setNoteVolumeSlider(JSlider noteVolumeSlider) {
        this.noteVolumeSlider = noteVolumeSlider;
    }

    public JLabel getNoteVolumeLabel() {
        return noteVolumeLabel;
    }

    public void setNoteVolumeLabel(JLabel noteVolumeLabel) {
        this.noteVolumeLabel = noteVolumeLabel;
    }

    public LoopDialog getLoopDialog() {
        return loopDialog;
    }

    public void setLoopDialog(LoopDialog loopDialog) {
        this.loopDialog = loopDialog;
    }

    public Instrument[] getInstruments() {
        return instruments;
    }

    public void setInstruments(Instrument[] instruments) {
        this.instruments = instruments;
    }

    public EditPhraseDialog getEditPhraseDialog() {
        return editPhraseDialog;
    }

    public void setEditPhraseDialog(EditPhraseDialog editPhraseDialog) {
        this.editPhraseDialog = editPhraseDialog;
    }

    public BetterButton getDuplicatePhraseButton() {
        return duplicatePhraseButton;
    }

    public void setDuplicatePhraseButton(BetterButton duplicatePhraseButton) {
        this.duplicatePhraseButton = duplicatePhraseButton;
    }

    public NewProjectDialog getNewProjectDialog() {
        return newProjectDialog;
    }

    public void setNewProjectDialog(NewProjectDialog newProjectDialog) {
        this.newProjectDialog = newProjectDialog;
    }

    public SettingsDialog getSettingsDialog() {
        return settingsDialog;
    }

    public void setSettingsDialog(SettingsDialog settingsDialog) {
        this.settingsDialog = settingsDialog;
    }

    public int getMeasureScale() {
        return measureScale;
    }

    public void setMeasureScale(int measureScale) {
        this.measureScale = measureScale;
    }

    public BetterButton getMeasuresButton() {
        return measuresButton;
    }

    public void setMeasuresButton(BetterButton measuresButton) {
        this.measuresButton = measuresButton;
    }

    public MeasuresDialog getMeasuresDialog() {
        return measuresDialog;
    }

    public void setMeasuresDialog(MeasuresDialog measuresDialog) {
        this.measuresDialog = measuresDialog;
    }

    public Line getPlaybackLine() {
        return playbackLine;
    }

    public void setPlaybackLine(Line playbackLine) {
        this.playbackLine = playbackLine;
    }

    public JPanel getPlaybackLinePanel() {
        return playbackLinePanel;
    }

    public void setPlaybackLinePanel(JPanel playbackLinePanel) {
        this.playbackLinePanel = playbackLinePanel;
    }

    public JSlider getMeasureZoomSlider() {
        return measureZoomSlider;
    }

    public void setMeasureZoomSlider(JSlider measureZoomSlider) {
        this.measureZoomSlider = measureZoomSlider;
    }

    public JCheckBox getSelectAllOrNoneCheckbox() {
        return selectAllOrNoneCheckbox;
    }

    public void setSelectAllOrNoneCheckbox(JCheckBox selectAllOrNoneCheckbox) {
        this.selectAllOrNoneCheckbox = selectAllOrNoneCheckbox;
    }

    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }

    public void setLayeredPane(JLayeredPane layeredPane) {
        this.layeredPane = layeredPane;
    }
}
