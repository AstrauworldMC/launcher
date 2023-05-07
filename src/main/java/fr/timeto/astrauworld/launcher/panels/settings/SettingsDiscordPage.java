package fr.timeto.astrauworld.launcher.panels.settings;

import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.timeto.astrauworld.launcher.customelements.SwitchButtonPanel;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.pagesutilities.DiscordCardExample;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;
import fr.timeto.astrauworld.launcher.panels.PageCreator;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;

public class SettingsDiscordPage extends PageCreator implements SwingerEventListener {

    SwitchButtonPanel allShowThings = new SwitchButtonPanel("Afficher des détails", ProfileSaver.KEY.GLOBALSETTINGS_DISCORD_SHOWTHINGS, true);
    SwitchButtonPanel allShowTime = new SwitchButtonPanel("Afficher le temps", ProfileSaver.KEY.GLOBALSETTINGS_DISCORD_SHOWTIME, true);

    JLabel launcherLabel = new JLabel("Dans le launcher");
    SwitchButtonPanel launcherShowPage = new SwitchButtonPanel("Afficher la page actuelle", ProfileSaver.KEY.GLOBALSETTINGS_DISCORD_SHOWLAUNCHERPAGE, true);

    JLabel gameLabel = new JLabel("En jeu");
    SwitchButtonPanel gameShowAccount = new SwitchButtonPanel("Afficher le compte", ProfileSaver.KEY.GLOBALSETTINGS_DISCORD_SHOWACCOUNT, true);
    SwitchButtonPanel gameShowServer = new SwitchButtonPanel("Afficher le serveur détecté", ProfileSaver.KEY.GLOBALSETTINGS_DISCORD_SHOWDETECTEDSERVER, true);

    DiscordCardExample cardExample = new DiscordCardExample();

    boolean update = false;

    public SettingsDiscordPage() {
        super(PageName.SETTINGS_DISCORD, "Param\u00e8tres", "Int\u00e9gration Discord");

        allShowThings.setBounds(0, 15);
        allShowThings.addEventListener(this);
        add(allShowThings);

        allShowTime.setBounds(0, 90);
        allShowTime.addEventListener(this);
        add(allShowTime);
        allShowTime.setEnabled(false);

        launcherLabel.setBounds(25, 190, 280, 24);
        launcherLabel.setFont(CustomFonts.robotoBoldFont.deriveFont(21f));
        launcherLabel.setForeground(Launcher.CUSTOM_COLORS.SECONDTEXT_COLOR.get());
        add(launcherLabel);

        launcherShowPage.setBounds(0, 215);
        launcherShowPage.addEventListener(this);
        add(launcherShowPage);

        gameLabel.setBounds(25, 315, 280, 24);
        gameLabel.setFont(launcherLabel.getFont());
        gameLabel.setForeground(launcherLabel.getForeground());
        add(gameLabel);

        gameShowAccount.setBounds(0, 340);
        gameShowAccount.addEventListener(this);
        add(gameShowAccount);

        gameShowServer.setBounds(0, 415);
        gameShowServer.addEventListener(this);
        add(gameShowServer);

        cardExample.setBounds(420, 32);
        add(cardExample);

        add(getBg().getPanel());
    }

    public void updateCard() {
        Thread t = new Thread(() -> {
            while (update) {
                cardExample.update(true);
                try {
                    Thread.sleep(900);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();
    }

    @Override
    public void recolor() {
        allShowThings.recolor();
        allShowTime.recolor();
        launcherLabel.setForeground(Launcher.CUSTOM_COLORS.SECONDTEXT_COLOR.get());
        launcherShowPage.recolor();
        gameLabel.setForeground(launcherLabel.getForeground());
        gameShowAccount.recolor();
        gameShowServer.recolor();
    }

    @Override
    public void setVisible(boolean aFlag) {
        update = aFlag;
        if (aFlag) {
            cardExample.update(false);
            updateCard();
        }
        super.setVisible(aFlag);
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        cardExample.update(true);
    }
}
