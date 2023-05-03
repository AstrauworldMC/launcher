package fr.timeto.astrauworld.launcher.eastereggs;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.main.LauncherFrame;
import fr.timeto.astrauworld.launcher.main.LauncherSystemTray;
import fr.timeto.astrauworld.launcher.pagesutilities.EasterEggs;
import fr.timeto.timutilslib.CustomFonts;
import org.imgscalr.Scalr;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;

public class BlackSpace extends JPanel {
    Welcome welcome = new Welcome();
    Door door = new Door();
    AfterDoor afterDoor = new AfterDoor();
    AfterStab afterStab = new AfterStab();

    public BlackSpace() {
        setLayout(null);
        setBackground(Color.BLACK);

        add(welcome);

        add(door);
        door.setVisible(false);

        add(afterDoor);
        afterDoor.setVisible(false);

        add(afterStab);
        afterStab.setVisible(false);

    }

    public void startAnim() {
        welcome.startAnim();
        welcome.setVisible(false);
        door.setVisible(true);
    }

    static class Welcome extends JPanel {

        JLabel welcomeLabel = new JLabel("CONTIENT DES SPOILS SUR OMORI");

        public Welcome() {
            setLayout(null);
            setOpaque(false);
            setBounds(0, 0, 1000, 630);

            welcomeLabel.setBounds(0, 0, 1000, 630);
            welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
            welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
            welcomeLabel.setFont(CustomFonts.minecraftiaFont.deriveFont(30f));
            welcomeLabel.setForeground(Color.WHITE);
            add(welcomeLabel);
        }

        public void startAnim() {
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                welcomeLabel.setText("");
                welcomeLabel.setIcon(new ImageIcon(getResourceIgnorePath("/assets/launcher/eastereggs/BlackSpace/Keys.png")));
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                welcomeLabel.setIcon(new ImageIcon(getResourceIgnorePath("/assets/launcher/eastereggs/BlackSpace/WelcomeToBlackSpace.png")));
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stab() {
        door.stab();
    }

    class Door extends JPanel implements SwingerEventListener{

        JLabel omori = new JLabel();
        STexturedButton doorButton = new STexturedButton(Swinger.getResourceIgnorePath("/assets/launcher/eastereggs/BlackSpace/Door-closed.png"), Swinger.getResourceIgnorePath("/assets/launcher/eastereggs/BlackSpace/Door-closed.png"), Swinger.getResourceIgnorePath("/assets/launcher/eastereggs/BlackSpace/Door-open.png"));
        BufferedImage omoriSprites = Swinger.getResourceIgnorePath("/assets/launcher/eastereggs/BlackSpace/omoriSprites.png");

        public Door() {
            setLayout(null);
            setOpaque(false);
            setBounds(0, 0, 1000, 630);

            omori.setBounds(485, 450, 32, 32);
            omori.setIcon(new ImageIcon(omoriSprites.getSubimage(34, 114, 32, 32)));
            add(omori);

            doorButton.setBounds(465, 285);
            doorButton.addEventListener(this);
            add(doorButton);

        }

        @Override
        public void setVisible(boolean aFlag) {
            if (aFlag) LauncherSystemTray.initBlackSpaceSystemTray();
            super.setVisible(aFlag);
        }

        boolean anim = false;

        public void stab() {
            if (!anim) {
                anim = true;

                ImageIcon stab0 = new ImageIcon(omoriSprites.getSubimage(34, 81, 32, 32));
                ImageIcon stab1 = new ImageIcon(omoriSprites.getSubimage(34, 15, 32, 32));
                ImageIcon stab2 = new ImageIcon(omoriSprites.getSubimage(366, 15, 32, 32));
                ImageIcon stab3 = new ImageIcon(omoriSprites.getSubimage(399, 15, 32, 32));
                ImageIcon stab4 = new ImageIcon(omoriSprites.getSubimage(432, 15, 32, 32));
                ImageIcon stab5 = new ImageIcon(omoriSprites.getSubimage(366, 48, 32, 32));
                ImageIcon stab6 = new ImageIcon(omoriSprites.getSubimage(399, 48, 32, 32));
                ImageIcon stab7 = new ImageIcon(omoriSprites.getSubimage(432, 48, 32, 32));
                ImageIcon stab8 = new ImageIcon(omoriSprites.getSubimage(366, 81, 32, 32));
                ImageIcon stab9 = new ImageIcon(omoriSprites.getSubimage(399, 81, 32, 32));
                ImageIcon stab10 = new ImageIcon(omoriSprites.getSubimage(432, 81, 32, 32));
                ImageIcon stab11 = new ImageIcon(omoriSprites.getSubimage(366, 114, 32, 32));
                ImageIcon stab12 = new ImageIcon(omoriSprites.getSubimage(399, 114, 32, 32));
                ImageIcon stab13 = new ImageIcon(omoriSprites.getSubimage(432, 114, 32, 32));

                Thread t = new Thread(() -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    omori.setIcon(stab0);
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    omori.setIcon(stab1);
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    omori.setIcon(stab2);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    omori.setIcon(stab3);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    omori.setIcon(stab4);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    omori.setIcon(stab5);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    omori.setIcon(stab6);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    omori.setIcon(stab7);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    omori.setIcon(stab8);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    omori.setIcon(stab9);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    omori.setIcon(stab10);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    omori.setIcon(stab11);
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    omori.setIcon(stab12);
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    omori.setIcon(stab13);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    doorButton.setVisible(false);
                    omori.setVisible(false);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    BlackSpace.this.afterStab();
                });
                t.start();
            }
        }

        @Override
        public void onEvent(SwingerEvent e) {
            if (e.getSource() == doorButton && !anim) {
                anim = true;
                Thread t = new Thread(() -> {
                    ImageIcon stay = new ImageIcon(omoriSprites.getSubimage(34, 114, 32, 32));
                    ImageIcon left = new ImageIcon(omoriSprites.getSubimage(1, 114, 32, 32));
                    ImageIcon right = new ImageIcon(omoriSprites.getSubimage(67, 114, 32, 32));
                    long betweenSteps = 500;

                    int i = 0;

                    while (i != 14) {
                        try {
                            Thread.sleep(betweenSteps);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        omori.setLocation(omori.getX(), omori.getY() - 4);
                        omori.setIcon(left);

                        try {
                            Thread.sleep(betweenSteps);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        omori.setLocation(omori.getX(), omori.getY() - 4);
                        omori.setIcon(right);

                        i++;
                    }

                    omori.setIcon(stay);
                    i = 0;

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                    doorButton.setEnabled(false);

                    while (i != 4) {
                        try {
                            Thread.sleep(betweenSteps);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        omori.setLocation(omori.getX(), omori.getY() - 4);
                        omori.setIcon(left);

                        try {
                            Thread.sleep(betweenSteps);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        omori.setLocation(omori.getX(), omori.getY() - 4);
                        omori.setIcon(right);

                        i++;
                    }

                    omori.setVisible(false);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                    doorButton.setEnabled(true);

                    try {
                        Thread.sleep(1300);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                    BlackSpace.this.afterDoor();

                });
                t.start();
            }

        }
    }

    public void afterDoor() {
        door.setVisible(false);
        setBackground(Color.WHITE);
        afterDoor.setVisible(true);
        afterDoor.startAnim();
    }

    static class AfterDoor extends JPanel {
        public AfterDoor() {
            setLayout(null);
            setOpaque(false);
        }

        public void startAnim() {
            // TODO ANIM APRES LA PORTE
            EasterEggs.setEatereggAsFound(EasterEggs.blackSpace);
        }
    }

    public void afterStab() {
        door.setVisible(false);
        LauncherSystemTray.initLauncherSystemTray();
        afterStab.setVisible(true);
        afterStab.startAnim();
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        LauncherFrame.getInstance().setSize(width, height);
    }

    class AfterStab extends JPanel {

        JLabel imageLabel = new JLabel();
        BufferedImage images = Swinger.getResourceIgnorePath("/assets/launcher/eastereggs/BlackSpace/stressedOut.png");
        BufferedImage pictures = Swinger.getResourceIgnorePath("/assets/launcher/eastereggs/BlackSpace/pictures.png");

        public AfterStab() {
            setLayout(null);
            setOpaque(false);
            setBounds(0, 0, 840, 630);

            imageLabel.setBounds(0, 0, 840, 630);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.CENTER);
            add(imageLabel);
        }

        public void startAnim() {
            ImageIcon f0 = new ImageIcon(Scalr.resize(images.getSubimage(0, 0, 640, 480), 840, 630)),
                    f1 = new ImageIcon(Scalr.resize(images.getSubimage(640, 0, 640, 480), 840, 630)),
                    f2 = new ImageIcon(Scalr.resize(images.getSubimage(1280, 0, 640, 480), 840, 630)),

                    f3 = new ImageIcon(Scalr.resize(images.getSubimage(1920, 0, 640, 480), 840, 630)),
                    f4 = new ImageIcon(Scalr.resize(images.getSubimage(2560, 0, 640, 480), 840, 630)),
                    f5 = new ImageIcon(Scalr.resize(images.getSubimage(0, 480, 640, 480), 840, 630)),

                    f6 = new ImageIcon(Scalr.resize(images.getSubimage(640, 480, 640, 480), 840, 630)),
                    f7 = new ImageIcon(Scalr.resize(images.getSubimage(1280, 480, 640, 480), 840, 630)),
                    f8 = new ImageIcon(Scalr.resize(images.getSubimage(1920, 480, 640, 480), 840, 630)),

                    f9 = new ImageIcon(Scalr.resize(images.getSubimage(2560, 480, 640, 480), 840, 630)),
                    f10 = new ImageIcon(Scalr.resize(images.getSubimage(0, 960, 640, 480), 840, 630)),
                    f11 = new ImageIcon(Scalr.resize(images.getSubimage(640, 960, 640, 480), 840, 630)),

                    f12 = new ImageIcon(Scalr.resize(images.getSubimage(1280, 960, 640, 480), 840, 630)),
                    f13 = new ImageIcon(Scalr.resize(images.getSubimage(1920, 960, 640, 480), 840, 630)),
                    f14 = new ImageIcon(Scalr.resize(images.getSubimage(2560, 960, 640, 480), 840, 630)),

                    f15 = new ImageIcon(Scalr.resize(images.getSubimage(0, 1440, 640, 480), 840, 630)),
                    f16 = new ImageIcon(Scalr.resize(images.getSubimage(640, 1440, 640, 480), 840, 630)),
                    f17 = new ImageIcon(Scalr.resize(images.getSubimage(1280, 1440, 640, 480), 840, 630)),
                    f18 = new ImageIcon(Scalr.resize(images.getSubimage(1920, 1440, 640, 480), 840, 630)),
                    f19 = new ImageIcon(Scalr.resize(images.getSubimage(2560, 1440, 640, 480), 840, 630)),
                    f20 = new ImageIcon(Scalr.resize(images.getSubimage(0, 1920, 640, 480), 840, 630)),
                    f21 = new ImageIcon(Scalr.resize(images.getSubimage(640, 1920, 640, 480), 840, 630)),
                    f22 = new ImageIcon(Scalr.resize(images.getSubimage(1280, 1920, 640, 480), 840, 630)),
                    f23 = new ImageIcon(Scalr.resize(images.getSubimage(1920, 1920, 640, 480), 840, 630)),
                    f24 = new ImageIcon(Scalr.resize(images.getSubimage(2560, 1920, 640, 480), 840, 630));

            ImageIcon f25 = new ImageIcon(pictures.getSubimage(705, 2128, 351, 422)),
                    f26 = new ImageIcon(pictures.getSubimage(1057, 2128, 351, 422)),
                    f27 = new ImageIcon(pictures.getSubimage(1, 2563, 351, 422)),
                    f28 = new ImageIcon(pictures.getSubimage(353, 2563, 351, 422)),
                    f29 = new ImageIcon(pictures.getSubimage(705, 2563, 351, 422)),
                    f30 = new ImageIcon(pictures.getSubimage(1057, 2563, 351, 422)),
                    f31 = new ImageIcon(pictures.getSubimage(1, 2986, 351, 422)),
                    f32 = new ImageIcon(pictures.getSubimage(353, 2986, 351, 422));

            Thread t = new Thread(() -> {
                BlackSpace.this.setSize(840, 630);

                int i = 0;
                while (i != 5) {
                    imageLabel.setIcon(f0);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    imageLabel.setIcon(f1);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    imageLabel.setIcon(f2);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    i++;
                }

                i = 0;
                while (i != 5) {
                    imageLabel.setIcon(f3);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    imageLabel.setIcon(f4);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    imageLabel.setIcon(f5);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    i++;
                }

                i = 0;
                while (i != 5) {
                    imageLabel.setIcon(f6);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    imageLabel.setIcon(f7);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    imageLabel.setIcon(f8);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    i++;
                }

                i = 0;
                while (i != 5) {
                    imageLabel.setIcon(f9);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    imageLabel.setIcon(f10);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    imageLabel.setIcon(f11);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    i++;
                }

                i = 0;
                while (i != 5) {
                    imageLabel.setIcon(f12);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    imageLabel.setIcon(f13);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    imageLabel.setIcon(f14);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    i++;
                }

                imageLabel.setIcon(f15);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                imageLabel.setIcon(f16);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                imageLabel.setIcon(f17);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                imageLabel.setIcon(f18);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                imageLabel.setIcon(f19);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                imageLabel.setIcon(f20);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                imageLabel.setIcon(f21);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                imageLabel.setIcon(f22);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                imageLabel.setIcon(f23);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                imageLabel.setIcon(f24);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                imageLabel.setIcon(f25);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                imageLabel.setIcon(f26);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                imageLabel.setIcon(f27);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                imageLabel.setIcon(f28);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                imageLabel.setIcon(f29);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                imageLabel.setIcon(f30);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                imageLabel.setIcon(f31);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                imageLabel.setIcon(f32);

                EasterEggs.setEatereggAsFound(EasterEggs.blackSpaceStab);
                System.exit(0);

                this.setVisible(false);
                BlackSpace.this.setSize(1000, 630);
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
