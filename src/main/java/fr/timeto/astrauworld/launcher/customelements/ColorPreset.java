package fr.timeto.astrauworld.launcher.customelements;

import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.main.LauncherFrame;
import fr.timeto.astrauworld.launcher.main.LauncherPanel;

import java.awt.*;
import java.util.ArrayList;

public class ColorPreset {

    private final String name;
    private final ArrayList<ColorPresetField> colorPresetFields;

    public ColorPreset(String name) {
        this.name = name;
        colorPresetFields = new ArrayList<>();
    }

    public void add(Launcher.CUSTOM_COLORS colorToChange, Color color) {
        colorPresetFields.add(new ColorPresetField(colorToChange, color));
    }

    public Color get(Launcher.CUSTOM_COLORS customColor) {
        int i = 0;
        while (i != colorPresetFields.size()) {
            if (colorPresetFields.get(i).getKey() == customColor) {
                return colorPresetFields.get(i).getColor();
            }
            i++;
        }
        return null;
    }

    public int size() {
        return colorPresetFields.size();
    }

    public void apply() {
        int i = 0;

        while (i != colorPresetFields.size()) {
            Color color = get(Launcher.CUSTOM_COLORS.values()[i]);
            if (color != null) {
                Launcher.CUSTOM_COLORS.values()[i].setWithoutRecolor(color);
            }
            i++;
        }
        try {
            LauncherPanel container = (LauncherPanel) LauncherFrame.getInstance().getContentPane();
            container.recolor();
        } catch (ClassCastException | NullPointerException ignored) {}
    }

    public void applyExculdeMain() {
        int i = 0;

        while (i != colorPresetFields.size()) {
            if (colorPresetFields.get(i).getKey() != Launcher.CUSTOM_COLORS.MAIN_COLOR) {
                Color color = get(Launcher.CUSTOM_COLORS.values()[i]);
                if (color != null) {
                    Launcher.CUSTOM_COLORS.values()[i].setWithoutRecolor(color);
                }
            }
            i++;
        }
        try {
            LauncherPanel container = (LauncherPanel) LauncherFrame.getInstance().getContentPane();
            container.recolor();
        } catch (ClassCastException | NullPointerException ignored) {}
    }

    public String getName() {
        return Launcher.parseUnicode(name);
    }

    public static ColorPreset getDarkPreset() {
        ColorPreset colorPreset = new ColorPreset("Thème sombre");
        colorPreset.add(Launcher.CUSTOM_COLORS.MAIN_COLOR, Launcher.CUSTOM_COLORS.MAIN_COLOR.getDefault());
        colorPreset.add(Launcher.CUSTOM_COLORS.TEXT_COLOR, Launcher.CUSTOM_COLORS.TEXT_COLOR.getDefault());
        colorPreset.add(Launcher.CUSTOM_COLORS.SECONDTEXT_COLOR, Launcher.CUSTOM_COLORS.SECONDTEXT_COLOR.getDefault());
        colorPreset.add(Launcher.CUSTOM_COLORS.DARKER_BACKGROUND_COLOR, Launcher.CUSTOM_COLORS.DARKER_BACKGROUND_COLOR.getDefault());
        colorPreset.add(Launcher.CUSTOM_COLORS.MID_BACKGROUND_COLOR, Launcher.CUSTOM_COLORS.MID_BACKGROUND_COLOR.getDefault());
        colorPreset.add(Launcher.CUSTOM_COLORS.BASE_BACKGROUND_COLOR, Launcher.CUSTOM_COLORS.BASE_BACKGROUND_COLOR.getDefault());
        colorPreset.add(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR, Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.getDefault());

        return colorPreset;
    }

    public static ColorPreset getClassicPreset() {
        ColorPreset colorPreset = new ColorPreset("Classic");
        colorPreset.add(Launcher.CUSTOM_COLORS.MAIN_COLOR, new Color(255, 0, 0));
        colorPreset.add(Launcher.CUSTOM_COLORS.TEXT_COLOR, new Color(255, 255, 255));
        colorPreset.add(Launcher.CUSTOM_COLORS.SECONDTEXT_COLOR, new Color(153, 153, 152));
        colorPreset.add(Launcher.CUSTOM_COLORS.DARKER_BACKGROUND_COLOR, new Color(30, 30, 30));
        colorPreset.add(Launcher.CUSTOM_COLORS.MID_BACKGROUND_COLOR, new Color(33, 33, 33));
        colorPreset.add(Launcher.CUSTOM_COLORS.BASE_BACKGROUND_COLOR, new Color(40, 40, 40));
        colorPreset.add(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR, new Color(33, 33, 33));

        return colorPreset;
    }

    public static ColorPreset getCustomPreset() {
        return new ColorPreset("Personnalisé...");
    }
}

class ColorPresetField {

    private final Launcher.CUSTOM_COLORS customColor;
    private final Color color;

    public ColorPresetField(Launcher.CUSTOM_COLORS customColor, Color color) {
        this.customColor = customColor;
        this.color = color;
    }

    public Launcher.CUSTOM_COLORS getKey() {
        return customColor;
    }

    public Color getColor() {
        return color;
    }
}
