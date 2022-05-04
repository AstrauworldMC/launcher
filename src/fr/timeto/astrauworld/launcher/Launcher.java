package fr.timeto.astrauworld.launcher;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.flowarg.flowlogger.ILogger;
import fr.flowarg.flowlogger.Logger;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.DownloadList;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.Step;
import fr.flowarg.flowupdater.download.json.CurseFileInfo;
import fr.flowarg.flowupdater.download.json.Mod;
import fr.flowarg.flowupdater.utils.ModFileDeleter;
import fr.flowarg.flowupdater.versions.AbstractForgeVersion;
import fr.flowarg.flowupdater.versions.ForgeVersionBuilder;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.flowarg.flowupdater.versions.VersionType;
import fr.flowarg.openlauncherlib.NewForgeVersionDiscriminator;
import fr.flowarg.openlauncherlib.NoFramework;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameType;
import fr.theshark34.openlauncherlib.minecraft.GameVersion;
import fr.theshark34.openlauncherlib.util.CrashReporter;

@SuppressWarnings("unused")
public class Launcher {
	
	
	static final String separatorChar = System.getProperty("file.separator");
	
	static final String userHomeDir = System.getProperty("user.home");
	
	/** System.getProperty("file.separator") | File.separatorChar
	 doc System.getProperty https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
	                        https://koor.fr/Java/API/java/lang/System/getProperties.wp 
	 multiplateforme        https://flylib.com/books/en/1.134.1/general_techniques_for_cross_platform_file_access_code.html
	                        https://www.sghill.net/how-do-i-make-cross-platform-file-paths-in-java.html */
	
	static final String filesFolder = userHomeDir + separatorChar + "AppData" + separatorChar + "Roaming" + separatorChar + "Astrauworld Launcher";
	static final String crashFolder = filesFolder + separatorChar + "crashes";
	static final String gameFilesFolder = filesFolder + separatorChar + "GameFiles";
	static final String dataFolder = filesFolder + separatorChar + "data";
	static final String logsFile = filesFolder + separatorChar + "logs.txt";
	static final String connectionsData = dataFolder + separatorChar + "connections.properties";
	
	static final String mcVersion = "1.18.1";
	static final String forgeVersion = "39.1.2";
	
	public static final String version = "BETA1.1.5";
	
	public static final File AW_DIR = new File(filesFolder); 
	public static final File AW_CRASH_FOLDER = new File(crashFolder);
	public static final File AW_GAMEFILES_FOLDER = new File(gameFilesFolder);
    public static final File AW_DATA_FOLDER = new File(dataFolder);
    public static final File AW_CONNECTIONS_DATA = new File(connectionsData);
    
    public static final Path awFilesFolder = Paths.get(filesFolder);
    public static final Path awCrashFolder = Paths.get(crashFolder);
    public static final Path awGameFilesFolder = Paths.get(gameFilesFolder);
    public static final Path awDataFolder = Paths.get(dataFolder);
    public static final Path awLogsFile = Paths.get(logsFile);
    public static final Path awConnectionsData = Paths.get(connectionsData);
    
    public static final GameInfos AW_INFOS = new GameInfos("Astrauworld", awGameFilesFolder, new GameVersion(mcVersion, GameType.V1_13_HIGHER_FORGE.setNFVD(new NewForgeVersionDiscriminator(forgeVersion, mcVersion, "20211210.034407"))), new GameTweak[] {GameTweak.FORGE});
	
	private static AuthInfos authInfos;
	private static Thread updateThread;
	
	static boolean maximumSetted = false;
	
	private static CrashReporter crashReporter = new CrashReporter("Astrauworld Launcher", awCrashFolder);
	
	public static void microsoftauth(String username, String password) throws MicrosoftAuthenticationException {
	    MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
	    MicrosoftAuthResult result = authenticator.loginWithCredentials(username, password);
	        
	    System.out.printf("Connecté avec '%s'%n", result.getProfile().getName(), " (compte Microsoft)");
	    authInfos = new AuthInfos(result.getProfile().getName(), result.getAccessToken(), result.getProfile().getId(), "", "");
	}
	
	public static void launch() throws Exception{
		
		NoFramework noFramework = new NoFramework(awGameFilesFolder, authInfos, GameFolder.FLOW_UPDATER);
		
//		noFramework.getAdditionalArgs().addAll(Arrays.asList("--server", "207.180.196.61", "--port", "33542"));
		
		LauncherFrame.getInstance().setVisible(false);
		
		noFramework.launch(mcVersion, forgeVersion);
		System.exit(0);
	}

	public enum StepInfo {
	    
	    INTEGRATION("Chargement de l'intégration..."),
	    MOD_PACK("Téléchargement du pack de mods..."),
	    READ("Lecture du json..."),
	    DL_LIBS("Téléchargement des librairies..."),
	    DL_ASSETS("Téléchargement des assets..."),
	    EXTRACT_NATIVES("Extraction des natives..."),
	    FORGE("Installation de Forge..."),
	    FABRIC("Installation de Fabric..."),
	    MODS("Téléchargement des mods..."),
	    EXTERNAL_FILES("Téléchargement des fichiers externes..."),
	    POST_EXECUTIONS("Running post executions..."),
	    END("Terminé!");

	    String details;

	    StepInfo(String details) {
	        this.details = details;
	    }

	    public String getDetails() {
	        return details;
	    }

	}
	
	public static void update() throws Exception {
			Logger logger = new Logger("[Astrauworld Launcher]", awLogsFile);
			
		    IProgressCallback callback = new IProgressCallback() {
			    private final DecimalFormat decimalFormat = new DecimalFormat("#.#");
			
			    @Override
			    public void init(ILogger logger) {}
			
			    @Override
			    public void step(Step step) {
				    LauncherPanel.infoLabel.setText(StepInfo.valueOf(step.name()).getDetails());
				
			    }
			
			    public void onFileDownloaded(Path path) {
				    LauncherPanel.fileLabel.setText(path.getFileName().toString());
		  	    }
			
			    @Override
			    public void update(DownloadList.DownloadInfo  info) {
				    int progress = (int) info.getDownloadedBytes();
				    int maximum = (int) info.getTotalToDownloadBytes();
				
				    LauncherPanel.percentLabel.setText(decimalFormat.format((progress / maximum) * 100) + "%");
			  	    LauncherPanel.progressBar.setValue(progress);
			  	    LauncherPanel.progressBar.setMaximum(maximum);
			    }
		    };
		
	        final VanillaVersion vanillaVersion = new VanillaVersion.VanillaVersionBuilder()
	            .withName("1.18.1")
	            .withVersionType(VersionType.FORGE)
	            .build(); 
	        final List<CurseFileInfo> modInfos = new ArrayList<>();
	            modInfos.add(new CurseFileInfo(64760, 3577168));  // [1.18.1] SecurityCraft v1.9.0.2-beta1            1 
	            modInfos.add(new CurseFileInfo(352835, 3692822)); // Backpacked 2.1.2-1.18.1                          2
	            modInfos.add(new CurseFileInfo(573646, 3645343)); // Calemi's Core 1.0.12                             3
	            modInfos.add(new CurseFileInfo(579403, 3645633)); // Calemi's Economy 1.0.0                           4
	            modInfos.add(new CurseFileInfo(55438, 3616801));  // MrCrayfish's Furniture Mod 7.0.0-pre28-1.18.1    5
	            modInfos.add(new CurseFileInfo(231095, 3673005)); // Chisels and bits 1.18.1-1.2.82-universal         6 
	            modInfos.add(new CurseFileInfo(237307, 3595626)); // Cosmetic Armor Reworked 1.18.1-v1a               7
	            modInfos.add(new CurseFileInfo(403422, 3656406)); // EmoteCraft for MC1.18.1-2.1-forge                8 
	            modInfos.add(new CurseFileInfo(238222, 3723162)); // JEI 1.18.1-9.4.1.172                             9
	            modInfos.add(new CurseFileInfo(291874, 3599020)); // Serene Seasons 1.18.1-6.0.0.1                    10
	            modInfos.add(new CurseFileInfo(416089, 3680713)); // Simple Voice Chat 1.18.1-2.2.27 [FORGE]          11
	            modInfos.add(new CurseFileInfo(350727, 3631954)); // Joy of Painting 1.18.1-0.7.1                     12
	            modInfos.add(new CurseFileInfo(303557, 3680860)); // Better Animals Plus 1.18.1-11.0.5 [Forge]        13
	            modInfos.add(new CurseFileInfo(419699, 3671605)); // Architectury API (Forge) v3.7.31                 14
                modInfos.add(new CurseFileInfo(258587, 3660868)); // ItemPhysic v1.4.22_mc1.18.1                      15
                modInfos.add(new CurseFileInfo(257814, 3650213)); // CreativeCore v2.5.0_mc1.18.1                     16
                modInfos.add(new CurseFileInfo(482378, 3711571)); // ParCool! 1.18.1-1.1.0.1                          17
                modInfos.add(new CurseFileInfo(568282, 3611313)); // Oxidized Forge 1.18.1-1.1                        18
                modInfos.add(new CurseFileInfo(453925, 3682819)); // Macaw's Fences and Walls v1.03 mc1.18.1 - Forge  19
                modInfos.add(new CurseFileInfo(400933, 3682900)); // Macaw's Trapdoors v1.0.5 mc 1.18.1 - Forge       20
                modInfos.add(new CurseFileInfo(352039, 3683190)); // Macaw's Roofs v2.1.1 mc 1.18.1 - Forge           21
                modInfos.add(new CurseFileInfo(351725, 3683687)); // Macaw's Bridges v2.0.2 mc1.18.1 - Forge          22
                modInfos.add(new CurseFileInfo(378646, 3683707)); // Macaw's Doors v1.0.6 mc1.18.1 - Forge            23
                modInfos.add(new CurseFileInfo(438116, 3696703)); // Macaw's Paintings v1.0.3 mc1.18.1 - Forge        24
                modInfos.add(new CurseFileInfo(363569, 3712017)); // Macaw's Windows v2.0.3 mc1.18.1 - Forge          25
                modInfos.add(new CurseFileInfo(502372, 3683039)); // Macaw's Lights and Lamps v1.0.3 mc1.18.1 - Forge|26
                modInfos.add(new CurseFileInfo(223852, 3611505)); // Storage drawers 1.18.1-10.1.1                    27
                modInfos.add(new CurseFileInfo(274259, 3601938)); // Carry On 1.18.1-1.17.0.7                         28
                modInfos.add(new CurseFileInfo(74072, 3693992));  // Tinkers' Construct 3.4.2.60 for 1.18.1           29
                modInfos.add(new CurseFileInfo(414640, 3618311)); // TwerkItMeal 1.18.1-2.1.0                         30
                modInfos.add(new CurseFileInfo(74924, 3693897));  // Mantle 1.18.1-1.8.37                             31
                modInfos.add(new CurseFileInfo(225738, 3641681)); // MmmMmmMmmMmm (target dummy) 1.18-1.5.1           32
                modInfos.add(new CurseFileInfo(488506, 3717750)); // Mannequins [Forge 1.18.x] 2.1.0                  33
                modInfos.add(new CurseFileInfo(544350, 3717722)); // Pollen [Forge 1.18(.1)] 1.3.1                    34

                modInfos.add(new CurseFileInfo(317269, 3546297)); // Controllable 0.15.1 | Client ONLY
                modInfos.add(new CurseFileInfo(557796, 3575266)); // Effective for Forge 1.001 | Client ONLY 
                
            final List<Mod> mods = new ArrayList<>();
                mods.add(new Mod("OptiFine_1.18.1_HD_U_H6.jar", "", 0, "https://optifine.net/downloadx?f=OptiFine_1.18.1_HD_U_H6.jar&x=0fd84dde29106a90ad419d4b6f97993e"));
	    
	        final AbstractForgeVersion forge = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW)
                .withForgeVersion(mcVersion + "-" + forgeVersion)
                .withCurseMods(modInfos)
            //    .withMods(mods) FIXME a remettre qd j'aurai trouvé pour optifine
                .withFileDeleter(new ModFileDeleter(true))
                .build();
	        final FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder()
	            .withVanillaVersion(vanillaVersion)
	            .withLogger(logger)
	            .withProgressCallback(callback)
	            .withForgeVersion(forge)
	            .build();
	        updater.update(awGameFilesFolder); 
	} 
	
	public static void installOtherFiles(String wantedResource) {
		final FlowUpdater installer = new FlowUpdater.FlowUpdaterBuilder()
				.build();
	//	installer.update();
	}
	
	public static void interruptThread() {
		updateThread.interrupt();
	}
	
	public static CrashReporter getCrashReporter() {
		return crashReporter;
	}

}
