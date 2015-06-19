package sonifiedspectra.view;

import apple.laf.JRSUIConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class MeasureHeadView extends JPanel {

    private Sonify app;

    private JLabel measureNumberLabel;
    private int measureNumber;
    private boolean selected;
    private Color backColor;

    public MeasureHeadView(Sonify app, int measureNumber) {
        this.app = app;
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
        g.fillRect(app.getMeasureScale(), 0, 1, 33);
        g.fillRect(app.getMeasureScale() * 2, 0, 1, 33);
        g.fillRect(app.getMeasureScale() * 3, 0, 1, 33);
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
