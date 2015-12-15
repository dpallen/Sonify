package sonifiedspectra.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;
import sonifiedspectra.controllers.RemovePhraseFromTrackController;
import sonifiedspectra.model.Note;
import sonifiedspectra.model.Phrase;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class PhraseInTrackView extends JPanel {

    private Sonify app;

    private JLabel nameLabel;
    private Phrase phrase;
    private BetterButton removeButton;
    private boolean selected;
    private float alpha;

    private ChartPanel chPanel;
    private GlassPanel graphPanel;
    private JFreeChart dataChart;
    private GlassPanel topPanel;
    private JLayeredPane layeredPane;

    public PhraseInTrackView(Sonify app, Phrase phrase) {
        this.app = app;
        this.phrase = phrase;
        selected = false;
        setLayout(null);
        setAlpha(0.2f);

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 200, 70);
        layeredPane.setLayout(null);
        add(layeredPane);

        topPanel = new GlassPanel();
        topPanel.setLayout(null);
        topPanel.setBounds(0, 0, 200, 70);
        Color unselectedColor = phrase.getUnselectedColor();
        System.out.println(unselectedColor.toString());
        topPanel.setBackground(new Color(unselectedColor.getRed(), unselectedColor.getGreen(), unselectedColor.getBlue()));
        topPanel.setAlpha(0.34f);
        topPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        layeredPane.add(topPanel, 100);

        if (phrase.getCompound() != null) nameLabel = new JLabel(phrase.getCompound().getName());
        else nameLabel = new JLabel("Loop");
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        nameLabel.setBounds(25, -7, 500, 40);
        topPanel.add(nameLabel);

        Icon removephrasefromtrackicon = new ImageIcon(getClass().getResource("/icons/removephrasefromtrackicon.png"));
        removeButton = new BetterButton(Color.decode("#F5F5F5"), 15, 15, 0);
        removeButton.setIcon(removephrasefromtrackicon);
        removeButton.setBounds(5, 5, 15, 15);
        removeButton.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        removeButton.setBorderPainted(true);
        removeButton.setFocusPainted(false);
        topPanel.add(removeButton);

        updateChart();

        layeredPane.moveToFront(topPanel);

    }

    public void updateChart() {
        if (!phrase.isLoop()) {

            chPanel = new ChartPanel(createChart());
            chPanel.setPreferredSize(new Dimension(200, 70));
            chPanel.setVisible(true);
            chPanel.setBounds(0, 0, 200, 70);
            chPanel.setDomainZoomable(true);
            chPanel.setOpaque(false);
            graphPanel = new GlassPanel();
            graphPanel.setLayout(new BorderLayout());
            graphPanel.setBounds(-15, -5, 200, 80);
            graphPanel.setAlpha(0.6f);
            graphPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
            graphPanel.removeAll();
            graphPanel.add(chPanel, BorderLayout.CENTER);
            layeredPane.add(graphPanel, 0);

        }
    }

    public void toggleSelected() {
        selected = !selected;
    }

    public void adjustSize(boolean expanded) {
        layeredPane.removeAll();
        layeredPane.add(topPanel, 100);
        updateChart();
        if (!expanded) {
            setBounds((int) ((phrase.getStartTime() * 4) * app.getMeasureScale()), 0, getAdjustedWidth(), 70);
            if (!phrase.isLoop()) {
                graphPanel.setBounds(-15, -5, getAdjustedWidth() + 30, 80);
                chPanel.setBounds(0, 0, getAdjustedWidth(), 70);
            }
            topPanel.setBounds(0, 0, getAdjustedWidth(), 70);
            layeredPane.setBounds(0, 0, getAdjustedWidth(), 70);
            layeredPane.repaint();
        }
        else {
            setBounds((int) ((phrase.getStartTime() * 4) * app.getMeasureScale()), 0, getAdjustedWidth(), 200);
            if (!phrase.isLoop()) {
                graphPanel.setBounds(-15, -5, getAdjustedWidth() + 30, 210);
                chPanel.setBounds(0, 0, getAdjustedWidth(), 200);
            }
            topPanel.setBounds(0, 0, getAdjustedWidth(), 200);
            layeredPane.setBounds(0, 0, getAdjustedWidth(), 200);
            layeredPane.repaint();
        }
        layeredPane.moveToFront(topPanel);
        //removeButton.setBounds(getAdjustedWidth() - 15, removeButton.getY(), removeButton.getWidth(), removeButton.getHeight());
    }



    public void print() {
        System.out.println("Pitv phrase id: " + phrase.getId());
        System.out.println("    Loop: " + phrase.isLoop());
        System.out.println("    x: " + getX());
        System.out.println("    y: " + getY());
        System.out.println("    width: " + getWidth());
        System.out.println("    height: " + getHeight());
        System.out.println("    name: " + nameLabel.getText());
        System.out.println("    adjusted width: " + getAdjustedWidth());
        System.out.println("    phrase beat length: " + phrase.getBeatLength2());
        System.out.println("    phrase num notes: " + phrase.getNotesArray().size());
    }

    public int getAdjustedWidth() {
        if (!phrase.isLoop()) return (int) (app.getMeasureScale() * phrase.getBeatLength2());
        //else return (int) (app.getMeasureScale() * phrase.getBeatLength());
        else return 200;
    }

    public void setAlpha(float value) {
        if (alpha != value) {
            alpha = Math.min(Math.max(0f, value), 1f);
            setOpaque(alpha == 1.0f);
            repaint();
        }
    }

    public float getAlpha() {
        return alpha;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.SrcOver.derive(getAlpha()));
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();

    }

    /**
     * Creates and customizes the data chart
     * @return the data chart
     */
    public JFreeChart createChart() {

        XYSeriesCollection dataSet = new XYSeriesCollection();
        dataSet.addSeries( phrase.getCompound().getDataChart().getDataSeries() );

        // create the chart...
        JFreeChart chart = ChartFactory.createXYLineChart(
                null,                      // chart title
                null,        // x axis label
                null,          // y axis label
                dataSet,                    // data
                PlotOrientation.VERTICAL,
                false,                      // include legend
                false,                       // tooltips
                false                       // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        //chart.setBackgroundPaint(Color.decode("#F5F5F5"));
        chart.setBorderVisible(false);

        // get a reference to the plot for further customisation...
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(phrase.getUnselectedColor());
        plot.setBackgroundAlpha(0.2f);
        plot.setDomainCrosshairVisible(false);
        plot.setRangeCrosshairVisible(false);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);
        plot.setRenderer(renderer);

        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setTickUnit(new NumberTickUnit(500));
        domain.setRange(phrase.getX2(), phrase.getX1());
        DecimalFormat format = new DecimalFormat("####");
        domain.setNumberFormatOverride(format);
        domain.setUpperMargin(.05);
        domain.setInverted(true);
        domain.setVisible(false);

        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setInverted(true);
        DecimalFormat format2 = new DecimalFormat("#.###");
        range.setNumberFormatOverride(format2);
        range.setVisible(false);

        chart.setPadding(new RectangleInsets(0, 0, 0, 0));

        dataChart = chart;

        return dataChart;

    }

    public Marker addNoteMarker(Note n) {

        Color color = new Color(0, 0, 0);

        System.out.println(n.getPeak().toString());

        double x1 = n.getPeak().getX1();
        double x2 = n.getPeak().getX2();

        if (phrase.getNotesArray().indexOf(n) == 0) x1 = phrase.getX1();

        if (phrase.getNotesArray().indexOf(n) == phrase.getNotesArray().size() - 1)
            x2 = phrase.getX2();

        Marker newMarker = new IntervalMarker(x2, x1);

        newMarker.setStroke(new BasicStroke(50));
        newMarker.setPaint(Color.BLACK);
        newMarker.setAlpha(1);

        return newMarker;

    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    public Phrase getPhrase() {
        return phrase;
    }

    public void setPhrase(Phrase phrase) {
        this.phrase = phrase;
    }

    public BetterButton getRemoveButton() {
        return removeButton;
    }

    public void setRemoveButton(BetterButton removeButton) {
        this.removeButton = removeButton;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ChartPanel getChPanel() {
        return chPanel;
    }

    public void setChPanel(ChartPanel chPanel) {
        this.chPanel = chPanel;
    }

    public GlassPanel getGraphPanel() {
        return graphPanel;
    }

    public void setGraphPanel(GlassPanel graphPanel) {
        this.graphPanel = graphPanel;
    }

    public JFreeChart getDataChart() {
        return dataChart;
    }

    public void setDataChart(JFreeChart dataChart) {
        this.dataChart = dataChart;
    }

    public GlassPanel getTopPanel() {
        return topPanel;
    }

    public void setTopPanel(GlassPanel topPanel) {
        this.topPanel = topPanel;
    }

    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }

    public void setLayeredPane(JLayeredPane layeredPane) {
        this.layeredPane = layeredPane;
    }
}
