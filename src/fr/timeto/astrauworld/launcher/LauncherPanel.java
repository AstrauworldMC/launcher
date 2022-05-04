package fr.timeto.astrauworld.launcher;


import static fr.theshark34.swinger.Swinger.*;
import static fr.timeto.astrauworld.launcher.NewsArticles.*;
import static fr.timeto.astrauworld.launcher.Changelogs.*;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

@SuppressWarnings("serial")
public class LauncherPanel extends JPanel implements SwingerEventListener {
	
	private Image background = getResource("LauncherPanel.png");
	
	private Saver saver = new Saver(Launcher.awConnectionsData);
	
	String lineSeparator = System.getProperty("line.separator");
	
	// Generals components
	private STexturedButton quitButton = new STexturedButton(getResource("quit.png"));
	private STexturedButton hideButton = new STexturedButton(getResource("hide.png"));
	private JLabel versionLabel = new JLabel("Version: " + Launcher.version);
	private STexturedButton commonNewsButton = new STexturedButton(getResource("commonNewsButton.png"));
	private STexturedButton commonChangelogButton = new STexturedButton(getResource("commonChangelogButton.png"));
	private STexturedButton commonOptionsButton = new STexturedButton(getResource("commonOptionsButton.png"));
	private STexturedButton commonAboutButton = new STexturedButton(getResource("commonAboutButton.png"));
	private STexturedButton commonBackButton = new STexturedButton(getResource("gauche.png"));
	
	// Home page components
	private STexturedButton menuButton = new STexturedButton(getResource("menu.png"));
	private STexturedButton playButton = new STexturedButton(getResource("play.png"));
	private STexturedButton savePassword = new STexturedButton(getResource("garderMdpOui.png"));
	public static SColoredBar progressBar = new SColoredBar(getTransparentWhite(100), Color.RED);
	public static JLabel infoLabel = new JLabel("Clique sur Jouer pour télécharger les fichiers et lancer le jeu", SwingConstants.CENTER);
	public static JLabel percentLabel = new JLabel("", SwingConstants.RIGHT);
	public static JLabel fileLabel = new JLabel("", SwingConstants.LEFT);
	private JTextField usernameField = new JTextField(saver.get("username"));
	private JPasswordField passwordField = new JPasswordField(saver.get("password"));
	private boolean doSavePassword = true;
	private STexturedButton downloadOnlyButton = new STexturedButton(getResource("downloadOnly.png"));
	
	// Menu page components
	private STexturedButton menuBackButton = new STexturedButton(getResource("gauche.png"));
	private STexturedButton menuNewsButton = new STexturedButton(getResource("newsLong.png"));
	private STexturedButton menuChangelogButton = new STexturedButton(getResource("changelogLong.png"));
	private STexturedButton menuOptionsButton = new STexturedButton(getResource("optionsLong.png"));
	private STexturedButton menuAboutButton = new STexturedButton(getResource("contactLong.png"));
	
	// News page components
    private STexturedButton newsArticle1 = new STexturedButton(getResource("NewsHeaderModel.png"));
    private STexturedButton newsArticle2 = new STexturedButton(getResource("NewsHeaderModel.png"));
    private STexturedButton newsArticle3 = new STexturedButton(getResource("NewsHeaderModel.png"));
    private STexturedButton newsArticle4 = new STexturedButton(getResource("NewsHeaderModel.png"));
	private int newsSelectedArticle1 = 0;
    private int newsSelectedArticle2 = 1;
    private int newsSelectedArticle3 = 2;
    private int newsSelectedArticle4 = 3;
    private int newsSelectedPage = 1;
    private Article newsActiveArticle;
    private STexturedButton newsArticleBackButton = new STexturedButton(getResource("gauche.png"));
    private STexturedButton newsLeft = new STexturedButton(getResource("gauche.png"));
    private STexturedButton newsRight = new STexturedButton(getResource("droite.png"));
    
    // Changelog page components
    private int changesSelectedVersion = 0;
    private JLabel changesVersionLabel = new JLabel("Version: " + changesSelectedVersion, SwingConstants.CENTER);
    private STexturedButton changesLeft = new STexturedButton(getResource("gauche.png")); 
    private STexturedButton changesRight = new STexturedButton(getResource("droite.png")); 
    private JTextArea changesTextLabel = new JTextArea("Impossible de trouver le changelog...");
    
    // About page components
    private STexturedButton aboutDiscordLogo = new STexturedButton(getResource("aboutDiscord.png"));
    private STexturedButton aboutGithubLogo = new STexturedButton(getResource("aboutGithub.png"));
    private STexturedButton aboutLogoAstrau = new STexturedButton(getResource("aboutLogoAstrau.png"), getResource("aboutLogoAstrau.png"));
    private STexturedButton aboutLogoTim = new STexturedButton(getResource("aboutLogoTim.png"), getResource("aboutLogoTim.png"));
    private STexturedButton aboutModsButton = new STexturedButton(getResource("aboutModsButton.png"));
    
    // Mods page components
    public String modsList = new String(
    		"- [1.18.1] SecurityCraft v1.9.0.2-beta1" + lineSeparator + 
    		"- Backpacked 2.1.2-1.18.1" + lineSeparator + 
    		"- Calemi's Core 1.0.12" + lineSeparator + 
            "- Calemi's Economy 1.0.0" + lineSeparator + 
            "- MrCrayfish's Furniture Mod 7.0.0-pre28-1.18.1" + lineSeparator + 
            "- Chisels and bits 1.18.1-1.2.82-universal" + lineSeparator + 
            "- Cosmetic Armor Reworked 1.18.1-v1a" + lineSeparator + 
            "- EmoteCraft for MC1.18.1-2.1-forge" + lineSeparator + 
            "- JEI 1.18.1-9.4.1.172" + lineSeparator + 
            "- Serene Seasons 1.18.1-6.0.0.1" + lineSeparator + 
            "- Simple Voice Chat 1.18.1-2.2.27 [FORGE]" + lineSeparator + 
            "- Joy of Painting 1.18.1-0.7.1" + lineSeparator + 
            "- Better Animals Plus 1.18.1-11.0.5 [Forge]" + lineSeparator + 
            "- Architectury API (Forge) v3.7.31" + lineSeparator + 
            "- ItemPhysic v1.4.22_mc1.18.1" + lineSeparator + 
            "- CreativeCore v2.5.0_mc1.18.1" + lineSeparator + 
            "- ParCool! 1.18.1-1.1.0.1" + lineSeparator + 
            "- Oxidized Forge 1.18.1-1.1" + lineSeparator + 
            "- Macaw's Fences and Walls v1.03 mc1.18.1 - Forge" + lineSeparator + 
            "- Macaw's Trapdoors v1.0.5 mc 1.18.1 - Forge" + lineSeparator + 
            "- Macaw's Roofs v2.1.1 mc 1.18.1 - Forge" + lineSeparator + 
            "- Macaw's Bridges v2.0.2 mc1.18.1 - Forge" + lineSeparator + 
            "- Macaw's Doors v1.0.6 mc1.18.1 - Forge" + lineSeparator + 
            "- Macaw's Paintings v1.0.3 mc1.18.1 - Forge" + lineSeparator +
            "- Macaw's Windows v2.0.3 mc1.18.1 - Forge" + lineSeparator +
   		    "- Macaw's Lights and Lamps v1.0.3 mc1.18.1 - Forge" + lineSeparator + 
            "- Storage drawers 1.18.1-10.1.1" + lineSeparator +
            "- Carry On 1.18.1-1.17.0.7" + lineSeparator + 
            "- Tinkers' Construct 3.4.2.60 for 1.18.1" + lineSeparator);
    public String modsList2 = new String(
             "- TwerkItMeal 1.18.1-2.1.0" + lineSeparator + 
             "- Mantle 1.18.1-1.8.37" + lineSeparator +
             "- MmmMmmMmmMmm 1.18-1.5.1" + lineSeparator + 
             "- Mannequins [Forge 1.18.x] 2.1.0" + lineSeparator + 
             "- Pollen [Forge 1.18(.1)] 1.3.1" + lineSeparator + 
             "- Controllable 0.15.1" + lineSeparator + 
             "- Effective for Forge 1.001");
    private JTextArea modsListText = new JTextArea(modsList);
    private JTextArea modsListText2 = new JTextArea(modsList2);
	
	public LauncherPanel() {
		this.setLayout(null);
		
		// Home page components
		usernameField.setForeground(Color.WHITE);
		usernameField.setFont(usernameField.getFont().deriveFont(30F));
		usernameField.setCaretColor(Color.WHITE);
		usernameField.setOpaque(false);
		usernameField.setBorder(null);
		usernameField.setBounds(525, 238, 395, 61);
		this.add(usernameField);
		
		passwordField.setForeground(Color.WHITE);
		passwordField.setFont(usernameField.getFont());
		passwordField.setCaretColor(Color.WHITE);
		passwordField.setOpaque(false);
		passwordField.setBorder(null);
		passwordField.setBounds(525, 379, 395, 61);
		this.add(passwordField);
		
		menuButton.setBounds(15, 12);
		menuButton.addEventListener(this);
		this.add(menuButton);
		
		playButton.setBounds(521, 480);
		playButton.addEventListener(this);
		this.add(playButton);
		
		savePassword.setBounds(521, 447);
		savePassword.addEventListener(this);
		this.add(savePassword);
		
		downloadOnlyButton.setBounds(575, 548);
		downloadOnlyButton.addEventListener(this);
		this.add(downloadOnlyButton);
		
		fileLabel.setForeground(Color.WHITE);
		fileLabel.setFont(fileLabel.getFont().deriveFont(18F));
		fileLabel.setBounds(5, 572, 915, 28);
		this.add(fileLabel);
		
		percentLabel.setForeground(Color.WHITE);
		percentLabel.setFont(fileLabel.getFont().deriveFont(18F));
		percentLabel.setBounds(5, 572, 965, 28);
		this.add(percentLabel);
		
		infoLabel.setForeground(Color.WHITE);
		infoLabel.setFont(fileLabel.getFont().deriveFont(17F));
		infoLabel.setBounds(0, 601, 975, 23);
		this.add(infoLabel);
		
		progressBar.setBounds(0,600, 975, 25);
		this.add(progressBar);
		
		// Menu page components
		menuBackButton.setBounds(15, 12);
		menuBackButton.addEventListener(this);
		this.add(menuBackButton);
		menuBackButton.setVisible(false);
		
		menuNewsButton.setBounds(0, 83);
		menuNewsButton.addEventListener(this);
		this.add(menuNewsButton);
		menuNewsButton.setVisible(false);
		
		menuChangelogButton.setBounds(0, 150);
		menuChangelogButton.addEventListener(this);
		this.add(menuChangelogButton);
		menuChangelogButton.setVisible(false);
		
		menuOptionsButton.setBounds(0, 222);
		menuOptionsButton.addEventListener(this);
		this.add(menuOptionsButton);
		menuOptionsButton.setVisible(false);
		
		menuAboutButton.setBounds(0, 295);
		menuAboutButton.addEventListener(this);
		this.add(menuAboutButton);
		menuAboutButton.setVisible(false);
		
		// News page components
		newsArticle1.setBounds(50, 100, 415, 205);
		newsArticle1.addEventListener(this);
		this.add(newsArticle1);
		newsArticle1.setVisible(false);
		
		newsArticle2.setBounds(484, 100, 415, 205);
		newsArticle2.addEventListener(this);
		this.add(newsArticle2);
		newsArticle2.setVisible(false);
		
		newsArticle3.setBounds(50, 325, 415, 205);
		newsArticle3.addEventListener(this);
		this.add(newsArticle3);
		newsArticle3.setVisible(false);
		
		newsArticle4.setBounds(484, 325, 415, 205);
		newsArticle4.addEventListener(this);
		this.add(newsArticle4);
		newsArticle4.setVisible(false);
		
		newsArticleBackButton.setBounds(15, 12);
		newsArticleBackButton.addEventListener(this);
		this.add(newsArticleBackButton);
		newsArticleBackButton.setVisible(false);
		
		newsLeft.setBounds(18, 555);
		newsLeft.addEventListener(this);
		this.add(newsLeft);
		newsLeft.setVisible(false);
		
		newsRight.setBounds(893, 555);
		newsRight.addEventListener(this);
		this.add(newsRight);
		newsRight.setVisible(false);
		
		// Changelog page components
		changesVersionLabel.setForeground(Color.WHITE);
		changesVersionLabel.setFont(changesVersionLabel.getFont().deriveFont(25F));
		changesVersionLabel.setBounds(360, 561, 532, 63);
		this.add(changesVersionLabel);
		changesVersionLabel.setVisible(false);
		
		changesLeft.setBounds(289, 561);
		changesLeft.addEventListener(this);
		this.add(changesLeft);
		changesLeft.setVisible(false);
		
		changesRight.setBounds(897, 561);
		changesRight.addEventListener(this);
		this.add(changesRight);
		changesRight.setVisible(false);
		
		changesTextLabel.setForeground(Color.WHITE);
		changesTextLabel.setFont(changesVersionLabel.getFont().deriveFont(20F));
		changesTextLabel.setBounds(289, 152, 607, 395);
		changesTextLabel.setEditable(false);
		changesTextLabel.setOpaque(false);
		this.add(changesTextLabel);
		changesTextLabel.setVisible(false);
		
		// About page components	
		aboutDiscordLogo.setBounds(720, 296);
		aboutDiscordLogo.addEventListener(this);
		this.add(aboutDiscordLogo);
		aboutDiscordLogo.setVisible(false);
		
		aboutGithubLogo.setBounds(444, 364);
		aboutGithubLogo.addEventListener(this);
		this.add(aboutGithubLogo);
		aboutGithubLogo.setVisible(false);
		
		aboutLogoAstrau.setBounds(813, 223);
		aboutLogoAstrau.addEventListener(this);
		this.add(aboutLogoAstrau);
		aboutLogoAstrau.setVisible(false);
		
		aboutLogoTim.setBounds(469, 185);
		aboutLogoTim.addEventListener(this);
		this.add(aboutLogoTim);
		aboutLogoTim.setVisible(false);
		
		aboutModsButton.setBounds(777, 96);
		aboutModsButton.addEventListener(this);
		this.add(aboutModsButton);
		aboutModsButton.setVisible(false);
		
		// Mods page components
		modsListText.setForeground(Color.WHITE);
		modsListText.setFont(changesVersionLabel.getFont().deriveFont(13F));
		modsListText.setBounds(289, 152, 303, 500);
		modsListText.setEditable(false);
		modsListText.setOpaque(false);
		this.add(modsListText);
		modsListText.setVisible(false);
		
		modsListText2.setForeground(Color.WHITE);
		modsListText2.setFont(changesVersionLabel.getFont().deriveFont(13F));
		modsListText2.setBounds(592, 152, 303, 500);
		modsListText2.setEditable(false);
		modsListText2.setOpaque(false);
		this.add(modsListText2);
		modsListText2.setVisible(false);
		
		// General components
		quitButton.setBounds(900, 11);
		quitButton.addEventListener(this);
		this.add(quitButton);
		
		hideButton.setBounds(816, 11);
		hideButton.addEventListener(this);
		this.add(hideButton);
		
		versionLabel.setForeground(Color.WHITE);
		versionLabel.setFont(infoLabel.getFont().deriveFont(20F));
		versionLabel.setBounds(15, 595, 240, 15);
		this.add(versionLabel);
		versionLabel.setVisible(false); 
		
		commonNewsButton.setBounds(0, 86);
		commonNewsButton.addEventListener(this);
		this.add(commonNewsButton);
		commonNewsButton.setVisible(false);
		
		commonChangelogButton.setBounds(0, 153);
		commonChangelogButton.addEventListener(this);
		this.add(commonChangelogButton);
		commonChangelogButton.setVisible(false);
		
		commonOptionsButton.setBounds(0, 225);
		commonOptionsButton.addEventListener(this);
		this.add(commonOptionsButton);
		commonOptionsButton.setVisible(false);
		
		commonAboutButton.setBounds(0, 298);
		commonAboutButton.addEventListener(this);
		this.add(commonAboutButton);
		commonAboutButton.setVisible(false);
		
		commonBackButton.setBounds(15, 12);
		commonBackButton.addEventListener(this);
		this.add(commonBackButton);
		commonBackButton.setVisible(false);
		
		/** modele boutons
		component.setBounds(nb, nb);
		component.addEventListener(this);
		this.add(component);
		*( component.setVisible(boolean) )*
		*/

	}
	
	@Override
	public void onEvent(SwingerEvent e) {
		if(e.getSource() == playButton) {
			setFieldsEnabled(false);
			
			if(usernameField.getText().replaceAll(" ", "").length() == 0 || passwordField.getPassword().length == 0) {
				JOptionPane.showMessageDialog(this, "Erreur, veuillez entrer un pseudo et un mot de passe valides", "Erreur", JOptionPane.ERROR_MESSAGE, null);
				setFieldsEnabled(true);
				return;
			}
			
			Thread play = new Thread() {
				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					try {
						System.out.println("Connexion...");
						Launcher.microsoftauth(usernameField.getText(), new String(passwordField.getPassword()));
					} catch (MicrosoftAuthenticationException e) {
						JOptionPane.showMessageDialog(LauncherPanel.this, "Erreur, impossible de se connecter", null, JOptionPane.ERROR_MESSAGE);
						System.out.println("Erreur, impossible de se connecter");
						setFieldsEnabled(true);
						return;
					}
					
					saver.set("password", "");
					saver.set("username", usernameField.getText());
					
					if(doSavePassword) {
						saver.set("savedUsername1", usernameField.getText());
						saver.set("savedPassword1", passwordField.getText());					
						saver.set("password", passwordField.getText());
						System.out.println("Identifiants enregistrés");
						infoLabel.setText("Identifiants enregistrés");
					}

					try {
						System.out.println("Mise a jour...");
						infoLabel.setText("Mise à jour...");
						Launcher.update();
						infoLabel.setText("Mise à jour terminée");
					} catch (Exception e) {
						Launcher.getCrashReporter().catchError(e, "Erreur, impossible de mettre à jour Astrauworld.");
						infoLabel.setText("Mise à jour échouée");
					}
					
					try {
						System.out.println("Lancement...");
						infoLabel.setText("Lancement...");
						fileLabel.setText("");
						percentLabel.setText("");
						Launcher.launch();
					} catch (Exception e) {
						Launcher.getCrashReporter().catchError(e, "Erreur, impossible de lancer Astrauworld.");
						fileLabel.setText("");
					}  
				}
			};
			play.start();
		}
		else if(e.getSource() == quitButton)
			System.exit(0);
		else if(e.getSource() == hideButton)
			LauncherFrame.getInstance().setState(JFrame.ICONIFIED);
		else if(e.getSource() == menuButton || e.getSource() == commonBackButton) {
			setHomePage(false);  // TODO false les autres pages
			setNewsPage(false); 
			setChangelogPage(false);
			setAboutPage(false);
			setModsPage(false);
			setMenuPage(true);
			}
		else if(e.getSource() == savePassword)
			if(doSavePassword) {
				savePassword.setTexture(getResource("garderMdpNon.png"));
			    savePassword.setTextureDisabled(getResource("garderMdpNon.png"));
			 	doSavePassword = false;
			}else{
				savePassword.setTexture(getResource("garderMdpOui.png"));
			    savePassword.setTextureDisabled(getResource("garderMdpOui.png"));
			 	doSavePassword = true;
			}
		else if(e.getSource() == menuBackButton) {
			setMenuPage(false);
			setHomePage(true);
		}
		else if(e.getSource() == menuNewsButton || e.getSource() == commonNewsButton) {
			setMenuPage(false); // TODO false les autres pages ou on peut passer
			setChangelogPage(false);
			setAboutPage(false);
			setModsPage(false);
			setNewsPage(true);
		}
		else if(e.getSource() == menuChangelogButton || e.getSource() == commonChangelogButton) {
			setMenuPage(false); // TODO false les autres pages par où on peut passer
			setAboutPage(false);
			setModsPage(false);
			setChangelogPage(true);
		}
		else if(e.getSource() == menuOptionsButton || e.getSource() == commonOptionsButton)
			excuseMessage();
		else if(e.getSource() == menuAboutButton || e.getSource() == commonAboutButton) {
			setMenuPage(false); // TODO false les autres pages par où on peut passer
			setChangelogPage(false);
			setModsPage(false);
		    setAboutPage(true);
		}
		else if(e.getSource() == aboutDiscordLogo) {
			try {
                Desktop.getDesktop().browse(new URI("http://discord.gg/GpqB5eES5r")); // Invit Discord
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
		}
		else if(e.getSource() == aboutGithubLogo) {
			try {
                Desktop.getDesktop().browse(new URI("https://github.com/AstrauworldMC/AstrauworldLauncher")); // Github
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
		}
		else if(e.getSource() == aboutLogoAstrau) {
			try {
                Desktop.getDesktop().browse(new URI("https://youtu.be/dQw4w9WgXcQ")); // Rick roll
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
		}
		else if(e.getSource() == aboutLogoTim) {
			try {
                Desktop.getDesktop().browse(new URI("https://youtu.be/4W0aj7uUvJk")); // Polish cow 10 hours
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
		}
		else if(e.getSource() == aboutModsButton) {
			setAboutPage(false);
		    setModsPage(true);
		}
		else if(e.getSource() == newsArticle1) {
			setNewsPage(false);
			newsActiveArticle = articlesList.get(newsSelectedArticle1);
			setArticlePage(true, newsSelectedArticle1, newsSelectedPage);
		}
		else if(e.getSource() == newsArticle2) {
			setNewsPage(false);
			newsActiveArticle = articlesList.get(newsSelectedArticle2);
			setArticlePage(true, newsSelectedArticle2, newsSelectedPage);
		}
		else if(e.getSource() == newsArticle3) {
			setNewsPage(false);
			newsActiveArticle = articlesList.get(newsSelectedArticle3);
			setArticlePage(true, newsSelectedArticle3, newsSelectedPage);
		}
		else if(e.getSource() == newsArticle4) {
			setNewsPage(false);
			newsActiveArticle = articlesList.get(newsSelectedArticle4);
			setArticlePage(true, newsSelectedArticle4, newsSelectedPage);
		}
		else if(e.getSource() == newsArticleBackButton) {
			setArticlePage(false);
			newsActiveArticle = null;
			newsSelectedPage = 1;
			setNewsPage(true);
		}
		else if(e.getSource() == newsLeft) {
			if(newsActiveArticle == null) {
				if(newsSelectedArticle1 == 0) {
					
				}else {
					newsSelectedArticle1 -= 4;
					newsSelectedArticle2 -= 4;
					newsSelectedArticle3 -= 4;
					newsSelectedArticle4 -= 4;
					
					setNewsPage(true);
				}
			}else if(newsSelectedPage >= getNumberOfPage(newsActiveArticle)){
				newsSelectedPage -= 1;
				
				setArticlePage(true, newsActiveArticle, newsSelectedPage);
			}
		}
		else if(e.getSource() == newsRight) {
			if(newsActiveArticle == null) {
				if((newsSelectedArticle4 + 1) >= nombreArticles) {
					
				}else {
					newsSelectedArticle1 += 4;
					newsSelectedArticle2 += 4;
					newsSelectedArticle3 += 4;
					newsSelectedArticle4 += 4;
					
					setNewsPage(true);
				}
			}else if(newsSelectedPage <= getNumberOfPage(newsActiveArticle)){
				newsSelectedPage += 1;
				
				setArticlePage(true, newsActiveArticle, newsSelectedPage);
			}
		}
		else if(e.getSource() == changesLeft) {
			if(changesSelectedVersion == 0) {
				
			}else {
				changesSelectedVersion -= 1;
				setChangelogPage(true);
			}
		}
		else if(e.getSource() == changesRight) {
			if((changesSelectedVersion + 1) >= nombreChangelogs) {
				
			}else {
				changesSelectedVersion += 1;
				
				setChangelogPage(true);
			}
		}
		else if(e.getSource() == downloadOnlyButton) {
			
			Thread update = new Thread() {
				@Override
				public void run() {
					setFieldsEnabled(false);
					try {
						System.out.println("Mise a jour...");
						infoLabel.setText("Mise à jour...");
						Launcher.update();
						infoLabel.setText("Mise à jour terminée");
						setFieldsEnabled(true);
					} catch (Exception e) {
						Launcher.getCrashReporter().catchError(e, "Erreur, impossible de mettre à jour Astrauworld.");
						infoLabel.setText("Mise à jour échouée");
						setFieldsEnabled(true);
					}
				}
			};
			update.start();
		}
	}
	
	// Début des méthodes pour changer la page
	public void setHomePage(boolean enabled) {
		usernameField.setVisible(enabled);
		passwordField.setVisible(enabled);
		menuButton.setVisible(enabled);
		playButton.setVisible(enabled);
		savePassword.setVisible(enabled);
		progressBar.setVisible(enabled);
		infoLabel.setVisible(enabled);
		percentLabel.setVisible(enabled);
		fileLabel.setVisible(enabled);
		downloadOnlyButton.setVisible(enabled);
		
		if(enabled) {
			background = getResource("LauncherPanel.png");
		}
	}
	
	public void setMenuPage(boolean enabled) {
		menuBackButton.setVisible(enabled);
		menuNewsButton.setVisible(enabled);
		menuChangelogButton.setVisible(enabled);
		menuOptionsButton.setVisible(enabled);
		menuAboutButton.setVisible(enabled);
		versionLabel.setVisible(enabled);
		
		if(enabled) {
			background = getResource("MenuPage.png");
		}
	}
	
	public void setNewsPage(boolean enabled) {		
		commonBackButton.setVisible(enabled);
		
		if(enabled) {
			try {
	        newsArticle1.setTexture(getArticleHeader(articlesList.get(newsSelectedArticle1)));
	        newsArticle1.setTextureHover(getArticleHover(articlesList.get(newsSelectedArticle1)));
	        newsArticle1.setEnabled(true);
	        if(articlesList.get(newsSelectedArticle1) == defaultArticle) {
	    		newsArticle1.setEnabled(false);
			}
			    } catch(Exception e) {
			    	if(articlesList.get(newsSelectedArticle1) == defaultArticle) {
			    		newsArticle1.setEnabled(false);
					}
			    }
			try {
				newsArticle2.setTexture(getArticleHeader(articlesList.get(newsSelectedArticle2)));
				newsArticle2.setTextureHover(getArticleHover(articlesList.get(newsSelectedArticle2)));
				newsArticle2.setEnabled(true);
				if(articlesList.get(newsSelectedArticle2) == defaultArticle) {
					newsArticle2.setEnabled(false);
				}
				} catch(Exception e) {
					if(articlesList.get(newsSelectedArticle2) == defaultArticle) {
						newsArticle2.setEnabled(false);
					}
				}
			try {
				newsArticle3.setTexture(getArticleHeader(articlesList.get(newsSelectedArticle3)));
				newsArticle3.setTextureHover(getArticleHover(articlesList.get(newsSelectedArticle3)));
				newsArticle3.setEnabled(true);
				if(articlesList.get(newsSelectedArticle3) == defaultArticle) {
					newsArticle3.setEnabled(false);
				}
				} catch(Exception e) {
					if(articlesList.get(newsSelectedArticle3) == defaultArticle) {
						newsArticle3.setEnabled(false);
					}
				}
			try {
				newsArticle4.setTexture(getArticleHeader(articlesList.get(newsSelectedArticle4)));
				newsArticle4.setTextureHover(getArticleHover(articlesList.get(newsSelectedArticle4)));
				newsArticle4.setEnabled(true);
				if(articlesList.get(newsSelectedArticle4) == defaultArticle) {
					newsArticle4.setEnabled(false);
				}
				} catch(Exception e) {
					if(articlesList.get(newsSelectedArticle4) == defaultArticle) {
						newsArticle4.setEnabled(false);
					}
				}
		}
		
		newsArticle1.setVisible(enabled);
		newsArticle2.setVisible(enabled);
		newsArticle3.setVisible(enabled);
		newsArticle4.setVisible(enabled);
		newsLeft.setVisible(enabled);
		newsRight.setVisible(enabled); 
		
		if(enabled) {
			background = getResource("NewsPage.png");
		} 
	}
	
	public void setArticlePage(boolean enabled) {
		newsArticleBackButton.setVisible(enabled);
		newsLeft.setVisible(enabled);
		newsRight.setVisible(enabled);
	} 
	
	public void setArticlePage(boolean enabled, int article, int pageNumber) {
		newsArticleBackButton.setVisible(enabled);
		
		if(getNumberOfPage(articlesList.get(article)) == 1) {
			
		}else {
			if(enabled) {
				newsLeft.setVisible(enabled);
				newsRight.setVisible(enabled);
			}
		}
		
	    background = getPageArticle(articlesList.get(article), pageNumber);
	//	getPageArticle(articlesList.get(article), 1) == null
	} 
	
	public void setArticlePage(boolean enabled, Article article, int pageNumber) {
		newsArticleBackButton.setVisible(enabled);
		
	    background = getPageArticle(article, pageNumber);
	//	getPageArticle(articlesList.get(article), 1) == null
	} 
	
	public void setChangelogPage(boolean enabled) {
		boolean disabled;
		
		if(enabled) {
		    disabled = false;
		} else {
			disabled = true;
		}
		
		commonNewsButton.setVisible(enabled);
		commonChangelogButton.setVisible(enabled);
		commonOptionsButton.setVisible(enabled);
		commonAboutButton.setVisible(enabled);
		commonChangelogButton.setEnabled(disabled);
		versionLabel.setVisible(enabled);
		
		changesVersionLabel.setVisible(enabled);
		commonBackButton.setVisible(enabled);
		changesLeft.setVisible(enabled);
		changesRight.setVisible(enabled);
		changesTextLabel.setVisible(enabled);
		
		if(enabled) {
			changesTextLabel.setText(getChangesText(changelogsList.get(changesSelectedVersion)));
			changesVersionLabel.setText("Version: " + getChangesVersion(changelogsList.get(changesSelectedVersion)));
			background = getResource("ChangelogPage.png");
		}else {
			changesSelectedVersion = 0;
		}
	}
	
	public void setAboutPage(boolean enabled) {
		boolean disabled;
		
		if(enabled) {
		    disabled = false;
		} else {
			disabled = true;
		}
		
		commonNewsButton.setVisible(enabled);
		commonChangelogButton.setVisible(enabled);
		commonOptionsButton.setVisible(enabled);
		commonAboutButton.setVisible(enabled);
		commonAboutButton.setEnabled(disabled);
		versionLabel.setVisible(enabled);
		
		commonBackButton.setVisible(enabled);
		aboutDiscordLogo.setVisible(enabled);
		aboutGithubLogo.setVisible(enabled);
		aboutLogoAstrau.setVisible(enabled); 
		aboutLogoTim.setVisible(enabled);
		aboutModsButton.setVisible(enabled);
		
		if(enabled) {
			background = getResource("AboutPage.png");
		}	
	}
	
	public void setModsPage(boolean enabled) {
		
		commonNewsButton.setVisible(enabled);
		commonChangelogButton.setVisible(enabled);
		commonOptionsButton.setVisible(enabled);
		commonAboutButton.setVisible(enabled);
		versionLabel.setVisible(enabled);
		
		modsListText.setVisible(enabled);
		modsListText2.setVisible(enabled);
		commonBackButton.setVisible(enabled);
		
		if(enabled) {
			background = getResource("ModsPage.png");
		}	
	} 
	
	// Fin des méthodes pour changer la page
	
	public void excuseMessage() {
		JOptionPane.showMessageDialog(LauncherPanel.this, "Désolé, cette page n'est pas encore disponible", "En developpement", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
	}

	private void setFieldsEnabled(boolean enabled) {
		usernameField.setEnabled(enabled);
		passwordField.setEnabled(enabled);
		playButton.setEnabled(enabled);
		savePassword.setEnabled(enabled);
		downloadOnlyButton.setEnabled(enabled);
	}
	
	public SColoredBar getProgressBar() {
		return progressBar;
	}
	
	public void setInfoText(String text) {
		infoLabel.setText(text);
	}
}
