package sonifiedspectra.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hvandenberg on 12/12/15.
 */
public class GlassPanel extends JPanel {

    private float alpha;

    public GlassPanel() {
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
}
