package sonifiedspectra.view;

import sonifiedspectra.controllers.HelpTextController;
import sonifiedspectra.controllers.MeasureHeadController;
import sonifiedspectra.model.HelpStrings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MeasuresDialog extends JDialog {

    private Sonify app;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel titleLabel;
    private JTextField addMeasuresTextField;
    private JLabel totalMeasuresLabel;

    public MeasuresDialog(Sonify app) throws IOException, FontFormatException {
        this.app = app;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        Font hnt20 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/HelveticaNeue-Thin.otf"))).deriveFont(Font.PLAIN, 20);

        titleLabel.setFont(hnt20);
        totalMeasuresLabel.setText(String.valueOf(app.getActiveProject().getNumMeasures()));
        addMeasuresTextField.setText("10");

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

        int startIndex = app.getMeasureHeadViewArray().size();

        int endIndex = startIndex + Integer.valueOf(addMeasuresTextField.getText());

        int i = 0;

        for (int k = startIndex; k < endIndex; k++) {
            MeasureHeadView mhv = new MeasureHeadView(app, app.getActiveProject().getNumMeasures() + i + 1);
            mhv.setBackground(app.getButtonBackgroundColor());
            mhv.setBounds(k * app.getMeasureScale() * 4, 0, app.getMeasureScale() * 4, 33);
            if (k % 2 != 0 && k != 0) mhv.setBackColor(Color.decode("#DDDDDD"));
            else mhv.setBackColor(Color.decode("#F5F5F5"));
            i++;

            MeasureHeadController measureHeadController = new MeasureHeadController(app, app.getActiveProject(), mhv);
            mhv.addMouseListener(new HelpTextController(app, HelpStrings.MEASURE_HEAD));
            mhv.addMouseListener(measureHeadController);
            mhv.updatePanel();
            app.getMeasureHeadViewArray().add(mhv);
            app.getMeasureHeadPanel().add(mhv);
        }

        for (TrackView tv : app.getTrackViewArray()) {
            tv.setBounds(tv.getX(), tv.getY(), app.getMeasureHeadViewArray().size() * app.getMeasureScale() * 4, tv.getHeight());
            tv.repaint();
        }

        app.getActiveProject().incrementNumMeasures(Integer.valueOf(addMeasuresTextField.getText()));

        app.getInTracksPanel().setPreferredSize(new Dimension(4 * app.getMeasureScale() * app.getActiveProject().getNumMeasures(),
                70 * app.getTrackViewArray().size()));

        app.getMeasureHeadPanel().setPreferredSize(new Dimension(2 + 4 * app.getMeasureScale() * app.getMeasureHeadViewArray().size() - 1, 33));

        setVisible(false);

        app.getFrame().pack();
    }

    private void onCancel() {
        setVisible(false);
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(JLabel titleLabel) {
        this.titleLabel = titleLabel;
    }

    public JTextField getAddMeasuresTextField() {
        return addMeasuresTextField;
    }

    public void setAddMeasuresTextField(JTextField addMeasuresTextField) {
        this.addMeasuresTextField = addMeasuresTextField;
    }

    public JLabel getTotalMeasuresLabel() {
        return totalMeasuresLabel;
    }

    public void setTotalMeasuresLabel(JLabel totalMeasuresLabel) {
        this.totalMeasuresLabel = totalMeasuresLabel;
    }
}
