package sonifiedspectra.view;

import apple.laf.JRSUIConstants;
import sonifiedspectra.model.SizeConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class MeasureHeadView extends JPanel {

    private JLabel measureNumberLabel;
    private int measureNumber;
    private boolean selected;
    private Color backColor;

    public MeasureHeadView(int measureNumber) {
        this.measureNumber = measureNumber;
        this.selected = false;
        setLayout(null);

        backColor = Color.decode("#F5F5F5");

        measureNumberLabel = new JLabel(String.valueOf(measureNumber));
        measureNumberLabel.setBounds(5, 5, 20, 20);
        add(measureNumberLabel);
        setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.decode("#C9C9C9"));
        g.fillRect(SizeConstants.MEASURE_SCALE, 0, 1, 33);
        g.fillRect(SizeConstants.MEASURE_SCALE * 2, 0, 1, 33);
        g.fillRect(SizeConstants.MEASURE_SCALE * 3, 0, 1, 33);
    }

    public void updatePanel() {
        if (selected) {
            setBackground(Color.decode("#B8B8B8"));
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
            repaint();
        }
        else {
            setBackground(backColor);
            setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, false));
            repaint();
        }
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

    public Color getBackColor() {
        return backColor;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }
}
