package fr.timeto.astrauworld.launcher.panels.profile;

import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.model.response.MinecraftProfile;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.main.LauncherPanel;
import fr.timeto.astrauworld.launcher.main.ServerInfosFrame;
import fr.timeto.astrauworld.launcher.pagesutilities.PageChange;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;
import fr.timeto.astrauworld.launcher.panels.PageCreator;
import fr.timeto.astrauworld.launcher.secret.whitelistservers.WhitelistServers;
import fr.timeto.timutilslib.PopUpMessages;

import javax.swing.*;

import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.*;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.updateThread;
import static fr.timeto.astrauworld.launcher.pagesutilities.PageChange.setPage;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.getSelectedSaver;
import static fr.timeto.timutilslib.PopUpMessages.doneMessage;
import static fr.timeto.timutilslib.PopUpMessages.errorMessage;

public class ProfileHomePage extends PageCreator implements SwingerEventListener {

    public final STexturedButton playButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/playButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/playButton-hover.png"), getResourceIgnorePath("/assets/launcher/profilesPage/playButton-disabled.png"));
    public final STexturedButton serverInfosButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/serverInfosButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/serverInfosButton-hover.png"));
    public final STexturedButton newsButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/newsButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/newsButton-hover.png"));
    public final STexturedButton launchToMenuButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/launchToMenuButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/launchToMenuButton-hover.png"), getResourceIgnorePath("/assets/launcher/profilesPage/launchToMenuButton-disabled.png"));
    public final STexturedButton downloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/downloadButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/downloadButton-hover.png"), getResourceIgnorePath("/assets/launcher/profilesPage/downloadButton-disabled.png"));
    public final STexturedButton whitelistedServersButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/whitelistServers/homeButton-normal.png"), getResourceIgnorePath("/assets/launcher/whitelistServers/homeButton-hover.png"));
    public final JPanel diapoPanel = new JPanel();
    public final JLabel textLogo = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/launcher/profilesPage/logo-texte.png")));
    public JLabel diapoImage1 = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/launcher/profilesPage/lake-day.png")));
    public JLabel diapoImage2 = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/launcher/profilesPage/townHall-day.png")));

    public ProfileHomePage() {
        super(PageName.PROFILE_HOME, "Profil " + ProfileSaver.getSelectedProfile(), "Jouer");

        setLayout(null);
        setOpaque(false);

        playButton.setBounds(550, 323);
        playButton.addEventListener(this);
        add(playButton);

        newsButton.setBounds(51, 465);
        newsButton.addEventListener(this);
        add(newsButton);

        serverInfosButton.setBounds(68, 415);
        serverInfosButton.addEventListener(this);
        add(serverInfosButton);

        launchToMenuButton.setBounds(317, 428);
        launchToMenuButton.addEventListener(this);
        add(launchToMenuButton);

        downloadButton.setBounds(586, 440);
        downloadButton.addEventListener(this);
        add(downloadButton);

        whitelistedServersButton.setBounds(433, 349);
        whitelistedServersButton.addEventListener(this);
        add(whitelistedServersButton);
        whitelistedServersButton.setVisible(false);

        textLogo.setBounds(242, 25, 338, 83);
        add(textLogo);

        diapoPanel.setLayout(null);
        add(diapoPanel);
        diapoPanel.setBounds(0, 0, 822, 343);
        diapoImage1.setBounds(0, 0, 822, 343);
        diapoPanel.add(diapoImage1);
        diapoImage2.setBounds(0, 0, 822, 343);
        diapoPanel.add(diapoImage2);

        add(getBg().getPanel());
    }

    public void setVisible(boolean aFlag) {
        if (aFlag) {
            setTitle("Profil " + ProfileSaver.getSelectedProfile());
            MinecraftProfile mcProfile = new MinecraftProfile(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_UUID.get()), ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()), null);
            if (WhitelistServers.getPlayerWhitelistedServers(mcProfile).length > 0) {
                whitelistedServersButton.setVisible(true);
            }
        } else {
            whitelistedServersButton.setVisible(false);
        }
        profileDiaporama(aFlag);
        super.setVisible(aFlag);
    }

    public void enablePlayButtons(boolean b) {
        playButton.setEnabled(b);
        launchToMenuButton.setEnabled(b);
        downloadButton.setEnabled(b);
    }

    @Override
    public void onEvent(SwingerEvent e) {
        Object src = e.getSource();
        if (src == playButton) {
            Saver saver = getSelectedSaver();
            LauncherPanel.enablePlayButtons(false);

            if (inDownload) {
                inDownloadError();
                return;
            }
            //     if (profilePlayButtonIsPlayStatus) {
            launchThread = new Thread(() -> {
                try {
                    //               togglePlayButtonStatus(false);
                    loadingBar.setVisible(true);
                    infosLabel.setVisible(true);
                    infosLabel.setText("Connexion...");
                    try {
                        Launcher.connect(saver);
                    } catch (MicrosoftAuthenticationException m) {
                        LauncherPanel.enablePlayButtons(true);
                        errorMessage("Erreur de connexion", "Erreur, impossible de se connecter");
                        infosLabel.setText("Connexion \u00e9chou\u00e9e");
                        loadingBar.setVisible(false);
                        infosLabel.setVisible(false);
                        return;
                    }
                    infosLabel.setText("Connect\u00e9 avec " + getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()));

                    try {
                        Launcher.update(saver);
                    } catch (Exception ex) {
                        LauncherPanel.enablePlayButtons(true);
                        throw new RuntimeException(ex);
                    }
                    updatePostExecutions();

                    try {
                        Launcher.launch(true, saver);
                    } catch (Exception ex) {
                        LauncherPanel.enablePlayButtons(true);
                        throw new RuntimeException(ex);
                    }
                    LauncherPanel.enablePlayButtons(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Thread t = new Thread(() -> {
                        System.exit(1);
                    });
                    PopUpMessages.errorMessage("Erreur", ex.getLocalizedMessage(), t);
                }

            });
            launchThread.start();

            //          togglePlayButtonStatus(false);
            //     } else {
            //     stopUpdate();

            //          togglePlayButtonStatus(true);
            //     }

        } else if (src == serverInfosButton) {
            ServerInfosFrame.openServerInfos();
        } else if (src == newsButton) {
            setPage(true, PageName.NEWS);
        } else if (src == launchToMenuButton) {
            Saver saver = getSelectedSaver();
            LauncherPanel.enablePlayButtons(false);

            if (inDownload) {
                inDownloadError();
                return;
            }
            launchThread = new Thread(() -> {
                try {
                    //          togglePlayButtonStatus(false);

                    try {
                        Launcher.connect(saver);
                    } catch (MicrosoftAuthenticationException m) {
                        LauncherPanel.enablePlayButtons(true);
                        errorMessage("Erreur de connexion", "Erreur, impossible de se connecter");
                        infosLabel.setText("Connexion \u00e9chou\u00e9e");
                        loadingBar.setVisible(false);
                        infosLabel.setVisible(false);
                        return;
                    }

                    try {
                        Launcher.update(saver);
                    } catch (Exception ex) {
                        LauncherPanel.enablePlayButtons(true);
                        throw new RuntimeException(ex);
                    }
                    updatePostExecutions();

                    try {
                        Launcher.launch(false, saver);
                    } catch (Exception ex) {
                        LauncherPanel.enablePlayButtons(true);
                        throw new RuntimeException(ex);
                    }
                    LauncherPanel.enablePlayButtons(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Thread t = new Thread(() -> {
                        System.exit(1);
                    });
                    PopUpMessages.errorMessage("Erreur", ex.getLocalizedMessage(), t);
                }

            });
            launchThread.start();

        } else if (src == downloadButton) {
            Saver saver = getSelectedSaver();
            LauncherPanel.enablePlayButtons(false);

            if (inDownload) {
                inDownloadError();
                return;
            }
            updateThread = new Thread(() -> {
                try {
                    //          togglePlayButtonStatus(false);
                    try {
                        Launcher.update(saver);
                    } catch (InterruptedException e1) {
                        LauncherPanel.enablePlayButtons(true);
                        updatePostExecutions();
                    } catch (Exception ex) {
                        LauncherPanel.enablePlayButtons(true);
                        throw new RuntimeException(ex);
                    }
                    LauncherPanel.enablePlayButtons(true);
                    updatePostExecutions();
                    doneMessage("T\u00e9l\u00e9chargement", "T\u00e9l\u00e9chargement termin\u00e9");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Thread t = new Thread(() -> {
                        System.exit(1);
                    });
                    PopUpMessages.errorMessage("Erreur", ex.getLocalizedMessage(), t);
                }

            });

            updateThread.start();

        } else if (src == whitelistedServersButton) {
            PageChange.setPage(true, PageName.PROFILE_WHITELIST_SERVERS);
        }
    }

    private boolean inDiaporama;
    private Thread tDiapo = null;

    public void profileDiaporama(boolean active) {
        if (active) {
            inDiaporama = true;

            corner.setVisible(true);
            corner.setVisible(false);

            if (tDiapo == null) {
                tDiapo = new Thread(() -> {
                    BufferedImage lakeImage;
                    BufferedImage townHallImage;
                    BufferedImage churchImage;
                    BufferedImage villageImage;

                    String check = "dayCycle";
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                        Date date_from = formatter.parse("20:00");
                        Date date_to = formatter.parse("08:00");
                        Date dateNow = formatter.parse(formatter.format(new Date()));

                        if (date_from.before(date_to)) { // they are on the same day
                            if (dateNow.after(date_from) && dateNow.before(date_to)) {
                                check = "nightCycle";
                            } else {
                                check = "dayCycle";
                            }
                        } else { // interval crossing midnight
                            if (dateNow.before(date_to) || dateNow.after(date_from)) {
                                check = "nightCycle";
                            } else {
                                check = "dayCycle";
                            }
                        }
                    } catch (ParseException ignored) {
                    }
                    if (check.equals("nightCycle")) {
                        lakeImage = getResourceIgnorePath("/assets/launcher/profilesPage/lake-night.png");
                        townHallImage = getResourceIgnorePath("/assets/launcher/profilesPage/townHall-night.png");
                        churchImage = getResourceIgnorePath("/assets/launcher/profilesPage/church-night.png");
                        villageImage = getResourceIgnorePath("/assets/launcher/profilesPage/village-night.png");
                    } else {
                        lakeImage = getResourceIgnorePath("/assets/launcher/profilesPage/lake-day.png");
                        townHallImage = getResourceIgnorePath("/assets/launcher/profilesPage/townHall-day.png");
                        churchImage = getResourceIgnorePath("/assets/launcher/profilesPage/church-day.png");
                        villageImage = getResourceIgnorePath("/assets/launcher/profilesPage/village-day.png");
                    }

                    final int xAllView = 0;
                    final int xAllLeft = -822;
                    final int xALlRight = 822;

                    int x1;
                    int x2;

                    while (inDiaporama) {
                        diapoImage1.setIcon(new ImageIcon(lakeImage));
                        diapoImage2.setIcon(new ImageIcon(townHallImage));

                        if (!inDiaporama) {
                            return;
                        }

                        x2 = xALlRight;
                        x1 = xAllView;
                        diapoImage2.setLocation(x2, 0);
                        diapoImage1.setLocation(x1, 0);

                        for(int i = 0; i == 1; i++) {
                            if (!inDiaporama) {
                                return;
                            }

                            if (i==0) {
                                x1 -= 3;
                                x2 -= 3;
                            } else {
                                x1 = xALlRight;
                                x2 = xAllLeft;
                            }
                            corner.setVisible(true);
                            diapoImage1.setLocation(x1, 0);
                            diapoImage2.setLocation(x2, 0);
                            corner.setVisible(false);

                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        corner.setVisible(true);
                        corner.setVisible(false);
                        if (!inDiaporama) {
                            return;
                        }

                        while (x1 != xAllLeft && x2 != xAllView) {
                            if (!inDiaporama) {
                                return;
                            }

                            x1 -= 3;
                            x2 -= 3;
                            corner.setVisible(true);
                            diapoImage1.setLocation(x1, 0);
                            diapoImage2.setLocation(x2, 0);
                            corner.setVisible(false);
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        x1 = xALlRight;
                        diapoImage1.setLocation(x1, 0);
                        if (!inDiaporama) {
                            return;
                        }
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        corner.setVisible(true);
                        corner.setVisible(false);
                        if (!inDiaporama) {
                            return;
                        }

                        diapoImage1.setIcon(new ImageIcon(churchImage));

                        while (x2 != xAllLeft && x1 != xAllView) {
                            if (!inDiaporama) {
                                return;
                            }

                            x1 -= 3;
                            x2 -= 3;
                            corner.setVisible(true);
                            diapoImage1.setLocation(x1, 0);
                            diapoImage2.setLocation(x2, 0);
                            corner.setVisible(false);
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        diapoImage2.setIcon(new ImageIcon(villageImage));

                        x2 = xALlRight;
                        diapoImage2.setLocation(x2, 0);
                        if (!inDiaporama) {
                            return;
                        }
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (!inDiaporama) {
                            return;
                        }
                        corner.setVisible(true);
                        corner.setVisible(false);

                        while (x1 != xAllLeft && x2 != xAllView) {
                            if (!inDiaporama) {
                                return;
                            }
                            x1 -= 3;
                            x2 -= 3;
                            corner.setVisible(true);
                            diapoImage1.setLocation(x1, 0);
                            diapoImage2.setLocation(x2, 0);
                            corner.setVisible(false);
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        x1 = xALlRight;
                        diapoImage1.setLocation(x1, 0);
                        if (!inDiaporama) {
                            return;
                        }
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        corner.setVisible(true);
                        corner.setVisible(false);
                        if (!inDiaporama) {
                            return;
                        }

                        diapoImage1.setIcon(new ImageIcon(lakeImage));

                        while (x2 != xAllLeft && x1 != xAllView) {
                            if (!inDiaporama) {
                                return;
                            }
                            x1 -= 3;
                            x2 -= 3;
                            corner.setVisible(true);
                            diapoImage1.setLocation(x1, 0);
                            diapoImage2.setLocation(x2, 0);
                            corner.setVisible(false);
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        corner.setVisible(true);
                        corner.setVisible(false);
                    }

                });
                tDiapo.start();

                Thread t = new Thread(() -> {
                    try {
                        tDiapo.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    tDiapo = null;
                });
                t.start();
            }

        } else {
            inDiaporama = false;
        }
    }
}
