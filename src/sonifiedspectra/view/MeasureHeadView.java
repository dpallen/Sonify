package sonifiedspectra.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class MeasureHeadView extends JPanel {

    private JLabel measureNumberLabel;
    private int measureNumber;
    private boolean selected;

    public MeasureHeadView(int measureNumber) {
        this.measureNumber = measureNumber;
        this.selected = false;

        measureNumberLabel = new JLabel(String.valueOf(measureNumber));
        measureNumberLabel.setBounds(40, 5, 20, 20);
        measureNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(measureNumberLabel);
        setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
    }

    public void toggleSelected() {
        selected = !selected;
    }

    public JLabel getMeasureNumberLabel() {
        return measureNumberLabel;
    }

    public void setMeasureNumberLabel(JLabel measureNumberLabel) {
        this.measureNumberLabel = measureNumberLabel;
    }

    public int getMeasureNumber() {
        return measureNumber;
    }

    public void setMeasureNumber(int measureNumber) {
        this.measureNumber = measureNumber;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
