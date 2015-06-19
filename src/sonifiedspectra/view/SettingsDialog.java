package sonifiedspectra.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SettingsDialog extends JDialog {
    private Sonify app;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField measureScaleTextView;
    private JLabel titleLabel;

    public SettingsDialog(Sonify app) throws IOException, FontFormatException {
        this.app = app;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        Font hnt20 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/HelveticaNeue-Thin.otf"))).deriveFont(Font.PLAIN, 20);

        titleLabel.setFont(hnt20);
        measureScaleTextView.setText(String.valueOf(app.getMeasureScale()));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {

        app.setMeasureScale(Integer.valueOf(measureScaleTextView.getText()));

        app.updateActivePhrase(app.getActivePhrase());
        app.updateIntervalMarker();

        for (TrackView tv : app.getTrackViewArray()) {
            tv.setBounds(tv.getX(), tv.getY(), app.getMeasureHeadViewArray().size() * app.getMeasureScale() * 4, tv.getHeight());
            tv.repaint();
        }

        int i = 0;

        for (MeasureHeadView mhv : app.getMeasureHeadViewArray()) {
            mhv.setBounds(i * 4 * app.getMeasureScale(), mhv.getY(), 4 * app.getMeasureScale(), mhv.getHeight());
            i++;
            mhv.repaint();
        }

        app.getInTracksPanel().setPreferredSize(new Dimension(4 * app.getMeasureScale() * app.getActiveProject().getNumMeasures(),
                70 * app.getTrackViewArray().size()));

        app.getMeasureHeadPanel().setPreferredSize(new Dimension(2 + 4 * app.getMeasureScale() * app.getMeasureHeadViewArray().size(), 33));

        setVisible(false);
        app.getFrame().pack();

    }

    private void onCancel() {
        setVisible(false);
    }
}
