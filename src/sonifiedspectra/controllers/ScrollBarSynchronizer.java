package sonifiedspectra.controllers;

import javax.swing.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * Created by Hvandenberg on 6/2/15.
 */
public class ScrollBarSynchronizer implements AdjustmentListener {

    private JScrollBar b1, b2;

    public ScrollBarSynchronizer(JScrollBar b1, JScrollBar b2) {
        this.b1 = b1;
        this.b2 = b2;
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {

        JScrollBar scrollBar = (JScrollBar) e.getSource();
        int value = scrollBar.getValue();
        JScrollBar target = null;

        if (scrollBar == b1) target = b2;
        if (scrollBar == b2) target = b1;

        target.setValue(value);

    }
}
