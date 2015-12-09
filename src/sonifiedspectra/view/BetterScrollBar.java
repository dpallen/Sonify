package sonifiedspectra.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class BetterScrollBar extends BasicScrollBarUI {

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
    	 g.setColor(trackColor);
         g.fillRect(0, 5, 700, 10);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds)
    {
        if(thumbBounds.isEmpty() || !scrollbar.isEnabled())     {
            return;
        }

        int w = thumbBounds.width;
        int h = thumbBounds.height;

        g.translate(thumbBounds.x, thumbBounds.y);

        g.setColor(thumbColor);
        g.drawRoundRect(0, 5, w-1, 10, 10, 10);
        g.fillRoundRect(0, 5, w-1, 10, 10, 10);

        g.translate(-thumbBounds.x, -thumbBounds.y);
    }

    protected void configureScrollBarColors(){
        this.thumbColor = Color.decode("#979797");
        this.trackColor = Color.decode("#F5F5F5");

    }

    protected JButton createZeroButton() {
        JButton button = new JButton("zero button");
        Dimension zeroDim = new Dimension(0,0);
        button.setPreferredSize(zeroDim);
        button.setMinimumSize(zeroDim);
        button.setMaximumSize(zeroDim);
        return button;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }
}