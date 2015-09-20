package sonifiedspectra.model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hvandenberg on 9/17/15.
 */
public class Line extends JPanel {

   private int x,y;
    private int INCREMENT = 5;
    private Color color;
    boolean draw = true;

    public Line(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void drawShape(Graphics g) {
        if (draw) {
            g.setColor(color);
            g.fillOval(x, y, 2, 30);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawShape(g);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getINCREMENT() {
        return INCREMENT;
    }

    public void setINCREMENT(int INCREMENT) {
        this.INCREMENT = INCREMENT;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }
}


