package sonifiedspectra.model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hvandenberg on 9/17/15.
 */
public class Line extends JPanel {

    private int increment;

    public Line(int increment) {
        this.increment = increment;
        setBounds(40, 0, 2, 495);
    }

    public int getIncrement() {
        return increment;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

}


