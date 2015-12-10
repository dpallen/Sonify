package sonifiedspectra.model;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class displays and provides a UI for manipulating input data in the form
 * of (x,y) coordinate pairs
 *
 * @author Halsey Vandenberg
 */
public class DataChart {

    private File file;

    private XYSeries dataSeries = new XYSeries( "" );

    private JFreeChart dataChart;

    private ArrayList<Peak> peaksArray = new ArrayList<Peak>();
    private ArrayList xValues = new ArrayList();

    private double lowestY = 0;
    private double highestY = 0;
    private double lowestX = 0;
    private double highestX = 0;
    private double highestWidth = 0;
    private double lowestWidth = 0;

    private int peakID = 0;

    /**
     * Constructor
     * @param file Text file containing x,y data pairs (float). The format must be
     *             X.XXX,X.XXX
     */
    public DataChart(File file) {

        this.file = file;

    }

    /**
     * Creates and customizes the data chart
     * @return the data chart
     */
    public JFreeChart createChart() {

        XYSeriesCollection dataSet = new XYSeriesCollection();
        dataSet.addSeries( dataSeries );

        String title;

        if ( file.exists() && !file.isDirectory() ) {

            title = file.getName();
            title = title.substring(0, 1).toUpperCase() + title.substring(1);

        }

        else title = "Unknown Compound";

        // create the chart...
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,                      // chart title
                "Wavenumber (1/cm)",        // x axis label
                "% Transmittance",          // y axis label
                dataSet,                    // data
                PlotOrientation.VERTICAL,
                false,                      // include legend
                true,                       // tooltips
                false                       // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.decode("#F5F5F5"));

        // get a reference to the plot for further customisation...
        XYPlot plot = chart.getXYPlot();
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(false);
        plot.setBackgroundPaint(Color.decode("#E5E5E5"));
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);
        plot.setRenderer(renderer);

        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setTickUnit(new NumberTickUnit(500));
        DecimalFormat format = new DecimalFormat("####");
        domain.setNumberFormatOverride(format);
        domain.setUpperMargin(.05);
        domain.setInverted(true);

        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setInverted(true);
        DecimalFormat format2 = new DecimalFormat("#.###");
        range.setNumberFormatOverride(format2);

        chart.setPadding(new RectangleInsets(10, 10, 10, 10));

        dataChart = chart;

        return dataChart;

    }

    /**
     * Processes the data file and generates an array of Note objects
     */
    public ArrayList<Peak> process() {

        String line;
        BufferedReader reader;
        String[] array;
        String end = "eof";

        int direction = 0;

        double peakStart = 0;
        double peakMax = 0;
        double peakEnd = 0;

        double x;
        double y;
        double lastY;

        try {

            reader = new BufferedReader(new FileReader( file ));

            line = reader.readLine();
            if (line.contains(",")) array = line.split(",");
            else if (line.contains(" ")) array = line.split(" ");
            else if (line.contains("\t")) array = line.split("\t");
            else {
                System.out.println("Invalid compound file - " + file.getName());
                return null;
            }

            String xString = String.valueOf( array[0] );
            String yString = String.valueOf( array[1] );

            if (!xString.contains("."))  xString += ".0";
            if (!yString.contains("."))  yString += ".0";

            x = Double.parseDouble( xString );
            y = Double.parseDouble( yString );

            highestX = x;
            lowestX = x;
            highestY = y;
            lowestY = y;

            lastY = y;

            peakStart = x;

            while( (line = reader.readLine()) != null ) {

                if (line.contains(",")) array = line.split(",");
                else if (line.contains(" ")) array = line.split(" ");
                else array = line.split("\t");

                if ( String.valueOf( array[0] ).equals( "eof" ) ) {

                    Peak p = new Peak(peakID, peakMax, peakStart, peakEnd);
                    if (p.getWidth() < lowestWidth) lowestWidth = p.getWidth();
                    if (p.getWidth() > highestWidth) highestWidth = p.getWidth();
                    peaksArray.add(p);
                    peakID++;
                    break;

                }

                xString = String.valueOf( array[0] );
                yString = String.valueOf( array[1] );

                if (!xString.contains("."))  xString += ".0";
                if (!yString.contains("."))  yString += ".0";

                x = Double.parseDouble( xString );
                y = Double.parseDouble( yString );

                if ( x > highestX ) highestX = x;
                if ( x < lowestX ) lowestX = x;
                if ( y > highestY ) highestY = y;
                if ( y < lowestY ) lowestY = y;

                if ( direction == 0 && y < lastY ) {

                    peakMax = y;
                    direction = 1;

                }

                else if ( direction == 1 && y > lastY ) {

                    peakEnd = x;

                    Peak p = new Peak( peakID, peakMax, peakStart, peakEnd );

                    if (peakID == 0) {
                        lowestWidth = p.getWidth();
                        highestWidth = p.getWidth();
                    }

                    if (p.getWidth() < lowestWidth) lowestWidth = p.getWidth();
                    if (p.getWidth() > highestWidth) highestWidth = p.getWidth();

                    peakID++;

                    peakStart = peakEnd;

                    direction = 0;

                    peaksArray.add( p );

                }

                else {

                    peakEnd = x;
                    lastY = y;

                }

            }
            reader.close();
        }
        catch (Exception ex) {

            ex.printStackTrace();

        }

        System.out.println( highestX + " + " + lowestX );
        System.out.println( highestY + " + " + lowestY );

        int id = 0;
        if (peaksArray.size() > 1) {
            if (peaksArray.get(0).getX1() < peaksArray.get(1).getX1()) {
                ArrayList<Peak> rightOrder = new ArrayList<Peak>();
                for (int i = peaksArray.size() - 1; i > 0; i--) {
                    double temp = peaksArray.get(i).getX1();
                    peaksArray.get(i).setX1(peaksArray.get(i).getX2());
                    peaksArray.get(i).setX2(temp);
                    rightOrder.add(peaksArray.get(i));
                    peaksArray.get(i).setId(id);
                    id++;
                }
                peaksArray = rightOrder;
            }
        }

        for (Peak p : peaksArray) System.out.println(p.toString());

        return peaksArray;

    }

    /**
     * Getter and Setter Functions
     * @return
     */

    public JFreeChart getDataChart() {
        return dataChart;
    }

    public void setDataChart(JFreeChart dataChart) {
        this.dataChart = dataChart;
    }

    public ArrayList<Peak> getPeaksArray() {
        return peaksArray;
    }

    public void setPeaksArray(ArrayList<Peak> peaksArray) {
        this.peaksArray = peaksArray;
    }

    public ArrayList getxValues() {
        return xValues;
    }

    public void setxValues(ArrayList xValues) {
        this.xValues = xValues;
    }

    /**
     * Prints information of a note
     */
    public void printNotes() {

        for (int i = 0; i < peaksArray.size(); i++ ) peaksArray.get( i ).toString();
        System.out.println("Notes Size: " + peaksArray.size());

    }

    public double getHighestX() {
        return highestX;
    }

    public void setHighestX(double highestX) {
        this.highestX = highestX;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public XYSeries getDataSeries() {
        return dataSeries;
    }

    public void setDataSeries(XYSeries dataSeries) {
        this.dataSeries = dataSeries;
    }

    public double getLowestY() {
        return lowestY;
    }

    public void setLowestY(double lowestY) {
        this.lowestY = lowestY;
    }

    public double getHighestY() {
        return highestY;
    }

    public void setHighestY(double highestY) {
        this.highestY = highestY;
    }

    public double getLowestX() {
        return lowestX;
    }

    public void setLowestX(double lowestX) {
        this.lowestX = lowestX;
    }

    public double getHighestWidth() {
        return highestWidth;
    }

    public void setHighestWidth(double highestWidth) {
        this.highestWidth = highestWidth;
    }

    public double getLowestWidth() {
        return lowestWidth;
    }

    public void setLowestWidth(double lowestWidth) {
        this.lowestWidth = lowestWidth;
    }
}
