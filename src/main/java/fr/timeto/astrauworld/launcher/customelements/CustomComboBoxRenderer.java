package fr.timeto.astrauworld.launcher.customelements;

    import fr.timeto.astrauworld.launcher.main.Launcher;
    import fr.timeto.timutilslib.CustomFonts;

    import java.awt.*;

    import javax.swing.JLabel;
    import javax.swing.JList;
    import javax.swing.ListCellRenderer;
    import javax.swing.border.EmptyBorder;

public class CustomComboBoxRenderer extends JLabel implements ListCellRenderer {

    public CustomComboBoxRenderer() {
        setOpaque(true);
        setFont(CustomFonts.robotoBlackFont.deriveFont(14f));
        setBackground(new Color(30, 30, 30));
        setForeground(new Color(205, 205, 205));

        setBorder(new EmptyBorder(0, 8, 0, 8));
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());

        if (isSelected) {
            setBackground(new Color(20, 20, 20));
            setForeground(Launcher.MAIN_COLOR);
        } else {
            setBackground(new Color(30, 30, 30));
            setForeground(new Color(205, 205, 205));
        }

        return this;
    }

}
