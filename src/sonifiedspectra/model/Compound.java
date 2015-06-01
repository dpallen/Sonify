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
    private String color;

    private Color unselectedColor;
    private Color borderColor;
    private Color selectedColor;

    private File dataFile;

    private DataChart dataChart;

    private ArrayList<Peak> peaks;

    public Compound(int id, File dataFile, String color, String spectrumType) {

        this.id = id;
        this.name = toName(dataFile.getName());
        this.spectrumType = spectrumType;
        this.color = color;
        this.dataFile = dataFile;

        setBackgroundCol(color);

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

    public void setBackgroundCol( String colorString ) {

        if (colorString.equals("Red")) {
            unselectedColor = new Color(255, 204, 204);
            borderColor = new Color(204, 0, 0);
            selectedColor = new Color(255, 102, 102);

        } else if (colorString.equals("Orange")) {
            unselectedColor = new Color(255, 229, 204);
            borderColor = new Color(204, 102, 0);
            selectedColor = new Color(255, 178, 102);

        } else if (colorString.equals("Yellow")) {
            unselectedColor = new Color(255, 255, 204);
            borderColor = new Color(204, 204, 0);
            selectedColor = new Color(255, 255, 102);

        } else if (colorString.equals("Green")) {
            unselectedColor = new Color(204, 255, 204);
            borderColor = new Color(0, 204, 0);
            selectedColor = new Color(102, 255, 102);

        } else if (colorString.equals("Blue")) {
            unselectedColor = new Color(204, 229, 255);
            borderColor = new Color(0, 102, 204);
            selectedColor = new Color(102, 178, 255);

        } else if (colorString.equals("Magenta")) {
            unselectedColor = new Color(255, 204, 204);
            borderColor = new Color(204, 0, 204);
            selectedColor = new Color(255, 102, 255);

        } else if (colorString.equals("Cyan")) {
            unselectedColor = new Color(204, 255, 255);
            borderColor = new Color(0, 204, 204);
            selectedColor = new Color(102, 255, 255);

        } else if (colorString.equals("Pink")) {
            unselectedColor = new Color(255, 204, 229);
            borderColor = new Color(204, 0, 102);
            selectedColor = new Color(255, 102, 178);

        } else {
            unselectedColor = new Color(0, 0, 0);
            borderColor = new Color(0, 0, 0);
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Color getUnselectedColor() {
        return unselectedColor;
    }

    public void setUnselectedColor(Color unselectedColor) {
        this.unselectedColor = unselectedColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }
}
