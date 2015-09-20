package sonifiedspectra.model;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class PlaybackLine extends JPanel {

    /*private static final int D_HEIGHT = 500;
    private static final int D_WIDTH = 400;
    private static final int INCREMENT = 8;
    private List<Line> lines;
    private Timer timer = null;

    public PlaybackLine() {
        lines = createShapeList();

        timer = new Timer(30, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Line shape : shapes) {
                    shape.move();
                    shape.decreaseDelay();
                    repaint();
                }
            }
        });
        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                timer.start();
            }
        });
        JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                shapes = createShapeList();
                timer.restart();
            }
        });

        JPanel panel = new JPanel();
        panel.add(start);
        panel.add(reset);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.PAGE_START);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape shape : shapes) {
            shape.drawShape(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(D_WIDTH, D_HEIGHT);
    }

    private List<Color> createColorList() {
        List<Color> list = new ArrayList<>();
        list.add(Color.BLUE);
        list.add(Color.GREEN);
        list.add(Color.ORANGE);
        list.add(Color.MAGENTA);
        list.add(Color.CYAN);
        list.add(Color.PINK);
        return list;
    }

    private List<Shape> createShapeList() {
        List<Shape> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(D_WIDTH);
            int y = random.nextInt(100);
            int colorIndex = random.nextInt(colors.size());
            Color color = colors.get(colorIndex);
            list.add(new Line(x, y, color));
        }

        return list;
    }

    class Shape {

        int randXLoc;
        int y = D_HEIGHT;
        int randomDelayedStart;
        boolean draw = false;
        boolean down = false;
        Color color;



    }*/
}