package sonifiedspectra.view;

import javafx.scene.text.TextAlignment;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.XYPlot;
import sonifiedspectra.controllers.*;
import sonifiedspectra.model.*;

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

    private Color buttonHighlightColor;
    private Color buttonBackgroundColor;

    private JPanel tracksPanel;
    private JPanel graphPanel;
    private JPanel notesPanel;
    private JPanel phrasesPanel;
    private JPanel rootPanel;

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

    private JLabel spectrumLabel;
    private JLabel helpTextLabel;
    private JLabel timerLabel;
    private JLabel compoundLabel;
    private JLabel notesLabel;
    private JLabel phrasesLabel;
    private JLabel transposeLabel;
    private JCheckBox multipleSelectionCheckBox;

    private Model model;
    private JFrame frame;
    private Phrase activePhrase;

    private JPanel mainView;
    private EditCompoundView editCompoundView;
    private JPanel fillerNotesView;
    private JPanel loopPanel;

    private ArrayList<NoteView> noteViewArray;
    private ArrayList<PhraseView> phraseViewArray;
    private ArrayList<String> colorsArray;

    public SonifiedSpectra() throws FileNotFoundException, FontFormatException, IOException {

        initialize();

    }

    private void initialize() throws FileNotFoundException, FontFormatException, IOException {
        initializeModel();
        initializeView();
        initializeControllers();
    }

    private void initializeModel() {

        model = new Model();

        colorsArray = new ArrayList<String>();
        colorsArray.add("Red");
        colorsArray.add("Orange");
        colorsArray.add("Yellow");
        colorsArray.add("Green");
        colorsArray.add("Blue");
        colorsArray.add("Magenta");
        colorsArray.add("Cyan");
        colorsArray.add("Pink");

        int i = 0;

        File dir = new File("compounds/");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File dataFile : directoryListing) {
                Compound compound = new Compound(i, dataFile, colorsArray.get(i), "Infrared");
                compound.load();
                compound.getDataChart().createChart();
                compound.setPeaks(compound.getDataChart().process());
                compoundComboBox.addItem(compound.getName());
                model.getCompoundsArray().add(compound);
                i++;
            }
        } else {
        }

        Phrase phrase = new Phrase(0, model.getCompoundsArray().get(0), 600, 1500);
        phrase.initialize();
        model.getPhrasesArray().add(phrase);
        activePhrase = phrase;

        Phrase phrase2 = new Phrase(1, model.getCompoundsArray().get(1), 1234, 2558);
        phrase2.initialize();
        model.getPhrasesArray().add(phrase2);

    }

    private void initializeView() throws FileNotFoundException, FontFormatException, IOException {

        frame = new JFrame();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Sonified Spectra - Musical Spectroscopic Analysis");
        frame.setSize(1280, 800);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setUndecorated(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.decode("#E5E5E5"));
        frame.setLayout(null);
        frame.getRootPane().setBorder(
                BorderFactory.createMatteBorder(4, 4, 4, 4,
                        Color.decode("#2267AA")));

        this.mainView = (JPanel) frame.getContentPane();
        mainView.setPreferredSize(new Dimension(1280, 800));

        buttonBackgroundColor = Color.decode("#F5F5F5");
        buttonHighlightColor = Color.decode("#CAEFFF");

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
        for (Compound c : model.getCompoundsArray()) {
            compoundComboBox.addItem(c.getName());
        }
        compoundComboBox.setBounds(123, 11, 158, 32);
        frame.getContentPane().add(compoundComboBox);

        spectrumLabel = new JLabel("Spectrum");
        Font hnt14 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/HelveticaNeue-Thin.otf"))).deriveFont(Font.PLAIN, 14);
        spectrumLabel.setFont(hnt14);
        spectrumLabel.setText(activePhrase.getCompound().getSpectrumType());
        spectrumLabel.setBounds(289, 18, 75, 20);
        frame.getContentPane().add(spectrumLabel);

        Icon importIcon = new ImageIcon("resources/icons/importcompound.png");
        importCompoundButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        importCompoundButton.setIcon(importIcon);
        importCompoundButton.setBounds(408, 11, 32, 32);
        importCompoundButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        importCompoundButton.setBorderPainted(true);
        importCompoundButton.setFocusPainted(false);
        frame.getContentPane().add(importCompoundButton);

        colorButton = new BetterButton(activePhrase.getCompound().getUnselectedColor(), 32, 32, 6);
        colorButton.setBounds(447, 11, 32, 32);
        colorButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        colorButton.setBorderPainted(true);
        colorButton.setFocusPainted(false);
        frame.getContentPane().add(colorButton);

        Icon editIcon = new ImageIcon("resources/icons/editcompound.png");
        editCompoundButton = new BetterButton(Color.decode("#F5F5F5"), 32, 32, 6);
        editCompoundButton.setIcon(editIcon);
        editCompoundButton.setBounds(486, 11, 32, 32);
        editCompoundButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        editCompoundButton.setBorderPainted(true);
        editCompoundButton.setFocusPainted(false);
        frame.getContentPane().add(editCompoundButton);

        final ChartPanel chPanel = new ChartPanel(activePhrase.getCompound().getDataChart().getDataChart());
        chPanel.setPreferredSize(new Dimension(500, 500));
        chPanel.setVisible(true);
        chPanel.setBounds(0, 0, 500, 400);
        chPanel.setDomainZoomable(true);
        graphPanel.setLayout(new BorderLayout());
        graphPanel.setBounds(20, 52, 500, 400);
        graphPanel.removeAll();
        graphPanel.add(chPanel, BorderLayout.CENTER);
        frame.getContentPane().add(graphPanel);

        notesLabel = new JLabel("Notes:");
        notesLabel.setFont(hnt20);
        notesLabel.setBounds(20, 460, 60, 24);
        frame.getContentPane().add(notesLabel);

        multipleSelectionCheckBox = new JCheckBox();
        Icon selicon;
        if (model.isNotesPanelMultipleSelection()) selicon = new ImageIcon("resources/icons/multseleccheckboxselected.png");
        else selicon = new ImageIcon("resources/icons/multseleccheckbox.png");
        multipleSelectionCheckBox.setIcon(selicon);
        multipleSelectionCheckBox.setSelected(model.isNotesPanelMultipleSelection());
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
        TextPrompt transposePrompt = new TextPrompt(String.valueOf("0"), transposeTextField);
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
            noteView.setBorder(BorderFactory.createLineBorder(activePhrase.getCompound().getBorderColor(), 1, true));
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
        minTextField.setBounds(207, 600, 39, 32);
        minTextField.setText(String.valueOf(activePhrase.getMinPitch()));
        minTextField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        frame.getContentPane().add(minTextField);

        maxTextField = new JTextField();
        maxTextField.setHorizontalAlignment(SwingConstants.CENTER);
        maxTextField.setBackground(Color.decode("#F5F5F5"));
        //maxTextField.setFont(hnt10);
        maxTextField.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        maxTextField.setBounds(251, 600, 39, 32);
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
        instrumentComboBox.setBounds(295, 600, 153, 32);
        frame.getContentPane().add(instrumentComboBox);

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
        phrasesScrollPane.setViewportView(phrasesPanel);
        phrasesScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        phrasesScrollPane
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        phrasesScrollPane.setBounds(20, 639, 500, 100);
        frame.getContentPane().add(phrasesScrollPane);

        phraseViewArray = new ArrayList<PhraseView>();

        int j = 0;

        for (Phrase phrase : model.getPhrasesArray()) {
            PhraseView phraseView = new PhraseView(phrase);
            phraseView.setBackground(phraseView.getPhrase().getCompound().getUnselectedColor());
            phraseView.updatePanel();
            phraseView.setBounds(10 + 110 * j, 5, 100, 70);
            j++;
            phraseViewArray.add(phraseView);
            phrasesPanel.add(phraseView);
        }

    }

    private void initializeControllers() {

        compoundComboBox.addItemListener(new CompoundComboBoxController(this, model, compoundComboBox));

        EditCompoundController editCompoundController = new EditCompoundController(this, model);
        editCompoundButton.addActionListener(editCompoundController);
        editCompoundButton.addMouseListener(editCompoundController);

        ImportCompoundController importCompoundController = new ImportCompoundController(this, model);
        importCompoundButton.addActionListener(importCompoundController);
        importCompoundButton.addMouseListener(importCompoundController);

        multipleSelectionCheckBox.addActionListener(new MultipleNoteSelectionController(this, model, multipleSelectionCheckBox));

        TransposeButtonController transposeUpButtonController = new TransposeButtonController(this, model, 0);
        transposeUpButton.addActionListener(transposeUpButtonController);
        transposeUpButton.addMouseListener(transposeUpButtonController);

        TransposeButtonController transposeDownButtonController = new TransposeButtonController(this, model, 1);
        transposeDownButton.addActionListener(transposeDownButtonController);
        transposeDownButton.addMouseListener(transposeDownButtonController);

        FillerController fillerController = new FillerController(this, model);
        fillerButton.addActionListener(fillerController);
        fillerButton.addMouseListener(fillerController);

        AddPhraseController addPhraseController = new AddPhraseController(this, model);
        addPhraseButton.addActionListener(addPhraseController);
        addPhraseButton.addMouseListener(addPhraseController);

        RemovePhraseController removePhraseController = new RemovePhraseController(this, model);
        removePhraseButton.addActionListener(removePhraseController);
        removePhraseButton.addMouseListener(removePhraseController);

        for (NoteView noteView : noteViewArray) {
            noteView.addMouseListener(new NoteController(model, this, noteView));
        }

        for (PhraseView phraseView : phraseViewArray) {
            phraseView.addMouseListener(new PhraseController(model, this, phraseView));
        }

    }

    public void updateActivePhrase(Phrase phrase) {

        activePhrase = phrase;
        compoundComboBox.setSelectedIndex(activePhrase.getCompound().getId());

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
            noteView.setBorder(BorderFactory.createLineBorder(activePhrase.getCompound().getBorderColor(), 1, true));
            noteView.updatePanel();
            i++;
            noteView.addMouseListener(new NoteController(model, this, noteView));
            noteViewArray.add(noteView);
            notesPanel.add(noteView);
        }

        notesPanel.setPreferredSize(new Dimension(10 + 44 * activePhrase.getNotesArray().size(), 100));

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
        marker.setPaint(activePhrase.getCompound().getSelectedColor());
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

    public static void main( String[] args ) throws FileNotFoundException, FontFormatException, IOException {

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

    public JPanel getTracksPanel() {
        return tracksPanel;
    }

    public void setTracksPanel(JPanel tracksPanel) {
        this.tracksPanel = tracksPanel;
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

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
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
}
