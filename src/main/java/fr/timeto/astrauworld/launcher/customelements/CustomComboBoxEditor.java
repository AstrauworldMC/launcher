package fr.timeto.astrauworld.launcher.customelements;

    import fr.timeto.astrauworld.launcher.main.Launcher;
    import fr.timeto.timutilslib.CustomFonts;

    import javax.swing.*;
    import javax.swing.plaf.basic.BasicComboBoxEditor;
    import java.awt.*;

public class CustomComboBoxEditor extends BasicComboBoxEditor {
    protected final JLabel label = new JLabel();
    protected final JPanel panel = new JPanel();
    protected Object selectedItem;

    public CustomComboBoxEditor() {

        label.setOpaque(false);
        label.setFont(CustomFonts.robotoBlackFont.deriveFont(14f));
        label.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());

        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 4));
        panel.add(label);
        panel.setBackground(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get());
    }

    public Component getEditorComponent() {
        return this.panel;
    }

    public Object getItem() {
        return this.selectedItem.toString();
    }

    public void setItem(Object item) {
        this.selectedItem = item;
        label.setText(item.toString());
    }

}

