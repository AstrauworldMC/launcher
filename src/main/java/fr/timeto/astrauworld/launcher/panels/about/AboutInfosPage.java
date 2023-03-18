package fr.timeto.astrauworld.launcher.panels.about;

import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.main.LauncherFrame;
import fr.timeto.astrauworld.launcher.pagesutilities.EasterEggs;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.astrauworld.launcher.panels.PageCreator;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.timeto.timutilslib.CustomFonts.kollektifBoldFont;

public class AboutInfosPage extends PageCreator implements SwingerEventListener {

    public final STexturedButton textLogo = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/logo-texte.png"), getResourceIgnorePath("/assets/launcher/aboutPage/logo-texte.png"));
    public final STexturedButton astrauwolfLogo = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/aboutLogoAstrau.png"), getResourceIgnorePath("/assets/launcher/aboutPage/aboutLogoAstrau.png"));
    public final STexturedButton capitenzoLogo = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/capitenzoPfp.png"), getResourceIgnorePath("/assets/launcher/aboutPage/capitenzoPfp.png"));
    public final STexturedButton timEtOLogo = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/aboutLogoTim.png"), getResourceIgnorePath("/assets/launcher/aboutPage/aboutLogoTim.png"));
    public final STexturedButton githubButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/github-normal.png"), getResourceIgnorePath("/assets/launcher/aboutPage/github-hover.png"));
    public final STexturedButton mailButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/mail-normal.png"), getResourceIgnorePath("/assets/launcher/aboutPage/mail-hover.png"));
    public final STexturedButton discordButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/discord-normal.png"), getResourceIgnorePath("/assets/launcher/aboutPage/discord-hover.png"));
    public final STexturedButton twitterButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/twitter-normal.png"), getResourceIgnorePath("/assets/launcher/aboutPage/twitter-hover.png"));
    public JLabel eastereggsLabel = new JLabel("", SwingConstants.LEFT) {
        @Override
        public void setText(String text) {
            super.setText("Easter eggs: " + text);
        }
    };
    public JTextArea textArea = new JTextArea();

    public AboutInfosPage() {
        super(PageName.ABOUT_INFOS, "À propos", "Infos");

        setLayout(null);
        setOpaque(false);

        textLogo.setBounds(12, 15);
        textLogo.addEventListener(this);
        this.add(textLogo);

        astrauwolfLogo.setBounds(344, 136);
        astrauwolfLogo.addEventListener(this);
        this.add(astrauwolfLogo);

        capitenzoLogo.setBounds(511, 137);
        capitenzoLogo.addEventListener(this);
        this.add(capitenzoLogo);

        timEtOLogo.setBounds(629, 137);
        timEtOLogo.addEventListener(this);
        this.add(timEtOLogo);

        githubButton.setBounds(160, 404);
        githubButton.addEventListener(this);
        this.add(githubButton);

        mailButton.setBounds(220, 407);
        mailButton.addEventListener(this);
        this.add(mailButton);

        discordButton.setBounds(279, 407);
        discordButton.addEventListener(this);
        this.add(discordButton);

        twitterButton.setBounds(340, 408);
        twitterButton.addEventListener(this);
        this.add(twitterButton);

        eastereggsLabel.setBounds(12, 492, 200, 16);
        eastereggsLabel.setForeground(new Color(151, 151, 151));
        eastereggsLabel.setFont(kollektifBoldFont.deriveFont(16f));
        eastereggsLabel.setOpaque(false);
        this.add(eastereggsLabel);

        textArea.setBounds(12, 144, 700, 450);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(CustomFonts.kollektifBoldFont.deriveFont(17f));
        textArea.setSelectionColor(new Color(255, 20, 20, 200));
        textArea.setEditable(false);
        textArea.setOpaque(false);
        this.add(textArea);
        textArea.setText(
                        Launcher.parseUnicode("""
                                Astrauworld a été fondé par Astrauwolf         , Capitenzo974         et Tim&O

                                Les designs, le code et la gestion interne du serveur dédié par Tim&O.
                                Le launcher et le bootstrap sont complétement opensource sur GitHub.

                                Merci à Carnagiul pour l'hosting et cyril_.r pour les builds.

                                Vous pouvez nous contacter via email à astrauworld.minecraft@gmail.com.

                                Si vous avez des problèmes ou des suggestions, rejoignez notre Discord !
                                N'hésitez pas à nous suivre sur les réseaux sociaux !""")
        );

        add(getBg().getPanel());
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        Object src = swingerEvent.getSource();

        if (src == textLogo) {
            try {
                Desktop.getDesktop().browse(new URL(LauncherFrame.launcherProperties.getProperty("blueMapLink")).toURI());
            } catch (IOException | URISyntaxException ignored) {}
        } else if (src == astrauwolfLogo) {
            try {
                Desktop.getDesktop().browse(new URL("https://youtu.be/rRPQs_kM_nw").toURI());
            } catch (IOException | URISyntaxException ignored) {}
            EasterEggs.setEatereggAsFound(EasterEggs.polishCow);
        } else if (src == capitenzoLogo) {
            try {
                Desktop.getDesktop().browse(new URL("https://youtu.be/vyPjz2QbFT4").toURI());
            } catch (IOException | URISyntaxException ignored) {}
            EasterEggs.setEatereggAsFound(EasterEggs.frogWalking);
        } else if (src == timEtOLogo) {
            try {
                Desktop.getDesktop().browse(new URL("https://youtu.be/dQw4w9WgXcQ").toURI());
            } catch (IOException | URISyntaxException ignored) {}
            EasterEggs.setEatereggAsFound(EasterEggs.rickroll);
        } else if (src == githubButton) {
            try {
                Desktop.getDesktop().browse(new URL("https://github.com/AstrauworldMC").toURI());
            } catch (IOException | URISyntaxException ignored) {}
        } else if (src == mailButton) {
            try {
                Desktop.getDesktop().mail(new URL("mailto:astrauworld.minecraft@gmail.com").toURI());
            } catch (IOException | URISyntaxException ignored) {}
        } else if (src == discordButton) {
            try {
                Desktop.getDesktop().browse(new URL("https://discord.gg/GpqB5eES5r").toURI());
            } catch (IOException | URISyntaxException ignored) {}
        } else if (src == twitterButton) {
            try {
                Desktop.getDesktop().browse(new URL("https://twitter.com/AstrauworldMC").toURI());
            } catch (IOException | URISyntaxException ignored) {}
        }

    }
}
