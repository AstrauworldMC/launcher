package fr.timeto.astrauworld.launcher.panels.settings;

import fr.timeto.astrauworld.launcher.customelements.*;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.astrauworld.launcher.panels.PageCreator;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import java.util.Objects;

public class SettingsColorsPage extends PageCreator {

    ColorChooserPanel mainColorChooser = new ColorChooserPanel("Couleur principale", Launcher.CUSTOM_COLORS.MAIN_COLOR);
    ColorChooserPanel textColorChooser = new ColorChooserPanel("Couleur du texte", Launcher.CUSTOM_COLORS.TEXT_COLOR);
    ColorChooserPanel secondTextColorChooser = new ColorChooserPanel("Couleur du texte secondaire", Launcher.CUSTOM_COLORS.SECONDTEXT_COLOR);
    ColorChooserPanel baseBackgroundColorChooser = new ColorChooserPanel("Arri\u00e8re-plan principal", Launcher.CUSTOM_COLORS.BASE_BACKGROUND_COLOR);
    ColorChooserPanel midBackgroundColorChooser = new ColorChooserPanel("Arri\u00e8re-plan interm\u00e9diaire", Launcher.CUSTOM_COLORS.MID_BACKGROUND_COLOR);
    ColorChooserPanel darkerBackgroundColorChooser = new ColorChooserPanel("Arri\u00e8re-plan fonc\u00e9", Launcher.CUSTOM_COLORS.DARKER_BACKGROUND_COLOR);
    ColorChooserPanel elementsColorChooser = new ColorChooserPanel("Couleur des \u00e9l\u00e9ments", Launcher.CUSTOM_COLORS.ELEMENTS_COLOR);

    String[] colorPresetsString = new String[] {
            ColorPreset.getDarkPreset().getName(),
            ColorPreset.getClassicPreset().getName(),
            ColorPreset.getCustomPreset().getName()
    };
    ColorPreset[] colorPresets = new ColorPreset[] {
            ColorPreset.getDarkPreset(),
            ColorPreset.getClassicPreset(),
            ColorPreset.getCustomPreset()
    };
    JLabel presetLabel = new JLabel("Preset");
    AWComboBox<String> presetComboBox = new AWComboBox<>(colorPresetsString);

    public SettingsColorsPage() {
        super(PageName.SETTINGS_COLORS, "Param\u00e8tres", "Couleurs personalis\u00e9es");

        setLayout(null);

        mainColorChooser.setBounds(25, 20);
        add(mainColorChooser);

        textColorChooser.setBounds(435, 20);
        add(textColorChooser);

        secondTextColorChooser.setBounds(25, 100);
        add(secondTextColorChooser);

        elementsColorChooser.setBounds(435, 100);
        add(elementsColorChooser);

        baseBackgroundColorChooser.setBounds(25, 180);
        add(baseBackgroundColorChooser);

        midBackgroundColorChooser.setBounds(435, 180);
        add(midBackgroundColorChooser);

        darkerBackgroundColorChooser.setBounds(25, 260);
        add(darkerBackgroundColorChooser);

        presetLabel.setBounds(25, 350, 280, 24);
        presetLabel.setFont(CustomFonts.robotoBlackFont.deriveFont(22f));
        presetLabel.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        add(presetLabel);

        presetComboBox.setBounds(235, 350, 150, 24);
        presetComboBox.addActionListener(e -> colorPresets[getSelectedPresetIndex()].apply());
        add(presetComboBox);

        add(getBg().getPanel());
    }

    public void verifySelectedPreset() {
        int i = 0;
        ColorPreset foundColorPreset = ColorPreset.getCustomPreset();
        ColorPreset[] testedColorPresets = new ColorPreset[] {
                ColorPreset.getDarkPreset(),
                ColorPreset.getClassicPreset(),
        };
        boolean foundBool = false;
        while (i != testedColorPresets.length) {
            if (!foundBool) {
                ColorPreset testedColorPreset = testedColorPresets[i];

                String found = null;
                int ii = 0;
                while (ii != testedColorPreset.size()) {
                    if (found == null || Boolean.parseBoolean(found)) {
                        Launcher.CUSTOM_COLORS testedColor = Launcher.CUSTOM_COLORS.values()[ii];
                        System.out.println();
                        System.out.println("Couleur actuelle pour " + testedColor + " : " + testedColor.get());
                        System.out.println("Couleur voulue pour le preset " + testedColorPreset.getName() + " : " + testedColorPreset.get(testedColor));
                        if (Launcher.colorsEquals(testedColorPreset.get(testedColor), testedColor.get())) {
                            found = "true";
                        } else {
                            found = "false";
                        }
                    }
                    ii++;
                }

                if (found != null) {
                    if (Boolean.parseBoolean(found)) {
                        foundBool = true;
                        foundColorPreset = testedColorPreset;
                    }
                }
            }

            i++;
        }

        presetComboBox.setSelectedItem(foundColorPreset.getName());
        presetComboBox.getEditor().setItem(foundColorPreset.getName());
        System.out.println("Preset trouvé: " + foundColorPreset.getName());
    }

    public int getSelectedPresetIndex() {
        String val = Objects.requireNonNull(presetComboBox.getSelectedItem()).toString();
        String[] T = colorPresetsString;

        int i;
        for(i = 0; i<T.length;i++){
            val = val.replaceAll("\\[", "").replaceAll("]", "");
            presetComboBox.setSelectedItem(val);
            if(val.contains(T[i]))
                //retourner la position courante
                return i;
        }
        return i-1;
    }

    @Override
    public void recolor() {
        mainColorChooser.recolor();
        textColorChooser.recolor();
        secondTextColorChooser.recolor();
        baseBackgroundColorChooser.recolor();
        midBackgroundColorChooser.recolor();
        darkerBackgroundColorChooser.recolor();
        elementsColorChooser.recolor();
        presetLabel.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        presetComboBox.recolor();
        verifySelectedPreset();
    }

    @Override
    public void setVisible(boolean aFlag) {
        if (aFlag) {
            verifySelectedPreset();
        }
        super.setVisible(aFlag);
    }
}
