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
        setBackground(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get());
        setForeground(Launcher.CUSTOM_COLORS.SECONDTEXT_COLOR.get());

        setBorder(new EmptyBorder(0, 8, 0, 8));
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());

        if (isSelected) {
            setBackground(getSelectedColor());
            setForeground(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        } else {
            setBackground(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get());
            setForeground(Launcher.CUSTOM_COLORS.SECONDTEXT_COLOR.get());
        }

        return this;
    }

    private Color getSelectedColor() {
        Color color = Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get();

        return new Color(color.getRed() - 10, color.getGreen() - 10, color.getBlue() - 10);
    }

}
