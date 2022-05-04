package fr.timeto.astrauworld.launcher;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

@SuppressWarnings("serial")
public class LauncherFrame extends JFrame{
	
	private static LauncherFrame instance;
	private LauncherPanel launcherPanel;
	private static CrashReporter crashReporter;
	
	public LauncherFrame() {
		this.setTitle("Astrauworld Launcher");
		this.setSize(975, 625);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setIconImage(Swinger.getResource("logo.png"));
		this.setContentPane(launcherPanel = new LauncherPanel()); 
		
		WindowMover mover = new WindowMover(this);
		this.addMouseListener(mover);
		this.addMouseMotionListener(mover);
		
		this.setVisible(true);
		
	} 
	
	public static void main(String[] args) {
		
		Swinger.setSystemLookNFeel();
    	Swinger.setResourcePath("/fr/timeto/astrauworld/launcher/resources/");
    	
    	// Stack Overflow multiplatforme https://stackoverflow.com/questions/585534/what-is-the-best-way-to-find-the-users-home-directory-in-java#comment398768_586917
    	
    	String OS = System.getProperty("os.name");
    	
    /*	System.out.println(System.getProperty("file.separator"));
    	System.out.println(File.separatorChar);
		System.out.println(OS);
		System.out.println(System.getProperty("os.version")); */
		
		if(OS.indexOf("Win") >= 0 || OS.indexOf("win") >= 0) {
			System.out.println("Windows OK");
		}else {
			JOptionPane.showMessageDialog(null, "Désolé, votre système d'expoitation (" + OS + ") n'est pas comaptible pour le moment", "Erreur", JOptionPane.ERROR_MESSAGE);
			System.out.println("Sorry nope");
			System.exit(0);
		}
		
		Launcher.AW_DIR.mkdir(); 
        Launcher.AW_DATA_FOLDER.mkdir();
	    Launcher.AW_GAMEFILES_FOLDER.mkdir();
	    Launcher.AW_CRASH_FOLDER.mkdir();
		
		crashReporter = new CrashReporter("Astrauworld Launcher", Launcher.awCrashFolder);
		
		NewsArticles.setArticlesList();
		Changelogs.setArticlesList();
		
		instance = new LauncherFrame(); // la ca met en route le LauncherFrame()
	}
		
		public static LauncherFrame getInstance() {
			return instance;
		}
		
		public static CrashReporter getCrashReporter() {
			return crashReporter;
		}
		
		public LauncherPanel getLauncherPanel() {
			return this.launcherPanel;
		}
}
