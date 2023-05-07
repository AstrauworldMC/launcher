package fr.timeto.astrauworld.launcher.customelements;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.abstractcomponents.AbstractButton;
import fr.timeto.astrauworld.launcher.main.Launcher;

import java.awt.*;

import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;

public class SwitchButton extends AbstractButton {
    private final String saverKeyStr;
    private final KEY saverKey;
    private final boolean global;

    public SwitchButton(KEY saverKey, boolean global) {
        super();
        this.saverKeyStr = saverKey.get();
        this.saverKey = saverKey;
        this.global = global;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Saver saver;
        if (global) {
            saver = globalSettingsSaver;
        } else saver = getSelectedSaver();

        if (Boolean.parseBoolean(saver.get(saverKeyStr))) {
            if (!this.isEnabled()) {
                // true - disabled
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 7, 74, 36);

                g2d.setColor(new Color(77, 77, 77));
                g2d.fillRect(3, 10, 68, 30);

                g2d.setColor(new Color(59, 59, 59));
                g2d.fillRect(5, 12, 64, 26);

                g2d.setColor(new Color(30, 30, 30));
                g2d.fillRect(29, 22, 16, 6);


                g2d.setColor(Color.BLACK);
                g2d.fillRect(46, 0, 28, 50);

                g2d.setColor(new Color(132, 132, 132));
                g2d.fillRect(49, 3, 22, 44);

                g2d.setColor(new Color(74, 74, 74));
                g2d.fillRect(52, 6, 19, 41);

                g2d.setColor(new Color(117, 117, 117));
                g2d.fillRect(52, 6, 16, 38);
            } else if (this.isHover()) {
                // true - hover
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 7, 74, 36);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 154));
                g2d.fillRect(3, 10, 68, 30);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 141));
                g2d.fillRect(5, 12, 64, 26);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 65));
                g2d.fillRect(29, 22, 16, 6);


                g2d.setColor(Color.WHITE);
                g2d.fillRect(46, 0, 28, 50);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 77));
                g2d.fillRect(49, 3, 22, 44);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 141));
                g2d.fillRect(52, 6, 19, 41);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 122));
                g2d.fillRect(52, 6, 16, 38);
            } else {
                // true
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 7, 74, 36);

                g2d.setColor(new Color(57, 57, 57));
                g2d.fillRect(3, 10, 68, 30);

                g2d.setColor(new Color(46, 46, 46));
                g2d.fillRect(5, 12, 64, 26);

                g2d.setColor(new Color(184, 184, 184));
                g2d.fillRect(29, 22, 16, 6);


                g2d.setColor(Color.BLACK);
                g2d.fillRect(46, 0, 28, 50);

                g2d.setColor(Color.WHITE);
                g2d.fillRect(49, 3, 22, 44);

                g2d.setColor(new Color(109, 109, 109));
                g2d.fillRect(52, 6, 19, 41);

                g2d.setColor(new Color(180, 180, 180));
                g2d.fillRect(52, 6, 16, 38);
            }
        } else {
            if (!this.isEnabled()) {
                // false - disabled
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 7, 74, 36);

                g2d.setColor(new Color(77, 77, 77));
                g2d.fillRect(3, 10, 68, 30);

                g2d.setColor(new Color(59, 59, 59));
                g2d.fillRect(5, 12, 64, 26);

                g2d.setColor(new Color(46, 46, 46));
                g2d.fillRect(34, 17, 6, 16);


                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, 28, 50);

                g2d.setColor(new Color(132, 132, 132));
                g2d.fillRect(3, 3, 22, 44);

                g2d.setColor(new Color(74, 74, 74));
                g2d.fillRect(6, 6, 19, 41);

                g2d.setColor(new Color(117, 117, 117));
                g2d.fillRect(6, 6, 16, 38);
            } else if (this.isHover()) {
                // false - hover
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 7, 74, 36);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 154));
                g2d.fillRect(3, 10, 68, 30);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 141));
                g2d.fillRect(5, 12, 64, 26);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 107));
                g2d.fillRect(34, 17, 6, 16);


                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, 28, 50);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 77));
                g2d.fillRect(3, 3, 22, 44);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 141));
                g2d.fillRect(6, 6, 19, 41);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 122));
                g2d.fillRect(6, 6, 16, 38);
            } else {
                // false
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 7, 74, 36);

                g2d.setColor(new Color(57, 57, 57));
                g2d.fillRect(3, 10, 68, 30);

                g2d.setColor(new Color(46, 46, 46));
                g2d.fillRect(5, 12, 64, 26);

                g2d.setColor(new Color(107, 107, 107));
                g2d.fillRect(34, 17, 6, 16);


                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, 28, 50);

                g2d.setColor(Color.WHITE);
                g2d.fillRect(3, 3, 22, 44);

                g2d.setColor(new Color(109, 109, 109));
                g2d.fillRect(6, 6, 19, 41);

                g2d.setColor(new Color(180, 180, 180));
                g2d.fillRect(6, 6, 16, 38);
            }
        }
    }

    public void setBounds(int x, int y) {
        this.setBounds(x, y, 74, 50);
    }

    public String getSaverKey() {
        return saverKeyStr;
    }

    public KEY getKey() {
        return saverKey;
    }

    @Override
    public void setVisible(boolean aFlag) {
        if(aFlag) {
            repaint();
            super.setVisible(aFlag);
        } else {
            super.setVisible(aFlag);
        }
    }

    public void toggleButton() {
        String value;
        Saver saver;
        if (global) {
            saver = globalSettingsSaver;
        } else saver = getSelectedSaver();

        value = saver.get(saverKeyStr);
        saver.set(saverKeyStr, String.valueOf(!Boolean.parseBoolean(value)));
        repaint();
    }

}
