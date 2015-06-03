package sonifiedspectra.model;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by hvandenberg on 10/24/2014.
 */
public class Compound {

    private int id;

    private String name;
    private String spectrumType;

    private File dataFile;

    private DataChart dataChart;

    private ArrayList<Peak> peaks;

    public Compound(int id, File dataFile, String spectrumType) {

        this.id = id;
        this.name = toName(dataFile.getName());
        this.spectrumType = spectrumType;
        this.dataFile = dataFile;

    }

    public void load() {

        this.dataChart = new DataChart(dataFile);

        // stores input stream
        InputStream is;

        try {

            // sets input stream as given file
            is = new FileInputStream( dataFile );

            Scanner scan = new Scanner(is);
            String[] array;

            // reads each line of file and stores the data in the data series array
            while ( scan.hasNextLine() ) {

                String line = scan.nextLine();

                if (line.contains(",")) {

                    array = line.split(",");

                } else array = line.split("\t");

                Object[] data = new Object[array.length];

                for (int i = 0; i < array.length; i++) data[i] = array[i];

                Double x = Double.parseDouble(array[0]);
                Double y = Double.parseDouble(array[1]);

                dataChart.getDataSeries().add(x, y);

            }

        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

    }

    private String toName(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataChart getDataChart() {
        return dataChart;
    }

    public void setDataChart(DataChart dataChart) {
        this.dataChart = dataChart;
    }

    public ArrayList<Peak> getPeaks() {
        return peaks;
    }

    public void setPeaks(ArrayList<Peak> peaks) {
        this.peaks = peaks;
    }

    public String getSpectrumType() {
        return spectrumType;
    }

    public void setSpectrumType(String spectrumType) {
        this.spectrumType = spectrumType;
    }

    public File getDataFile() {
        return dataFile;
    }

    public void setDataFile(File dataFile) {
        this.dataFile = dataFile;
    }
}
