package sonifiedspectra.view;

/**
 * Created by Hvandenberg on 5/30/15.
 */
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

public class SSComboBoxRenderer extends JLabel implements ListCellRenderer {

    public SSComboBoxRenderer() throws FileNotFoundException, FontFormatException, IOException {
        setOpaque(true);

        Font hnt2 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/HelveticaNeue-Thin.otf")).deriveFont(Font.PLAIN, 12);
        setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));
        setBounds(123, 11, 158, 32);
        setForeground(Color.decode("#000000"));
        setBackground(Color.decode("#F5F5F5"));
        setFont(hnt2);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());
        return this;
    }

}