package fr.timeto.astrauworld.launcher;

import fr.flowarg.flowlogger.ILogger;
import fr.flowarg.flowlogger.Logger;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.DownloadList;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.Step;
import fr.flowarg.flowupdater.download.json.CurseFileInfo;
import fr.flowarg.flowupdater.download.json.OptiFineInfo;
import fr.flowarg.flowupdater.utils.ModFileDeleter;
import fr.flowarg.flowupdater.versions.AbstractForgeVersion;
import fr.flowarg.flowupdater.versions.ForgeVersionBuilder;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.flowarg.openlauncherlib.NewForgeVersionDiscriminator;
import fr.flowarg.openlauncherlib.NoFramework;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.Saver;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

import static fr.timeto.astrauworld.launcher.LauncherPanel.*;
import static fr.timeto.astrauworld.launcher.ProfileSaver.*;

@SuppressWarnings("unused")
public class Launcher {

    public static final String separatorChar = System.getProperty("file.separator");
    static final String userAppDataDir = System.getenv("APPDATA");

    static final String afterMcExitArg = "--afterMcExit";

    // String des les path dont on a besoin
    static final String filesFolder = userAppDataDir + separatorChar + "Astrauworld Launcher";
    static final String crashFolder = filesFolder + separatorChar + "crashes";
    static final String gameFilesFolder = filesFolder + separatorChar + "GameFiles";
    static final String dataFolder = filesFolder + separatorChar + "data";
    static final String logsFile = filesFolder + separatorChar + "logs.txt";

    static final String firstProfileData = dataFolder + separatorChar + "firstProfile.properties";
    static final String firstProfileIcon = dataFolder + separatorChar + "firstProfile.png";
    static final String firstProfileCustomFilesFolder = dataFolder + separatorChar + "firstProfileCustomFiles";

    static final String secondProfileData = dataFolder + separatorChar + "secondProfile.properties";
    static final String secondProfileIcon = dataFolder + separatorChar + "secondProfile.png";
    static final String secondProfileCustomFilesFolder = dataFolder + separatorChar + "secondProfileCustomFiles";

    static final String thirdProfileData = dataFolder + separatorChar + "thirdProfile.properties";
    static final String thirdProfileIcon = dataFolder + separatorChar + "thirdProfile.png";
    static final String thirdProfileCustomFilesFolder = dataFolder + separatorChar + "thirdProfileCustomFiles";

    // Version de Minecraft et de Forge utilisée
    static final String mcVersion = "1.19.2"; // TODO Passer à la 1.19.3 quand les mods seront dispos
    static final String forgeVersion = "43.1.1";
    static final String optifineVersion = "1.19.2_HD_U_H9";

    // Version du launcher
    public static final String version = "Beta2.1.2"; // TODO CHANGER LA VERSION A CHAQUE FOIS

    // File des dont on a besoin
    public static final File AW_DIR = new File(filesFolder);
    public static final File AW_CRASH_FOLDER = new File(crashFolder);
    public static final File AW_GAMEFILES_FOLDER = new File(gameFilesFolder);
    public static final File AW_DATA_FOLDER = new File(dataFolder);

    public static final File AW_FIRSTPROFILE_DATA = new File(firstProfileData);
    public static final File AW_FIRSTPROFILE_ICON = new File(firstProfileIcon);
    public static final File AW_FIRSTPROFILE_CUSTOMFILES_FOLDER = new File(firstProfileCustomFilesFolder);

    public static final File AW_SECONDPROFILE_DATA = new File(secondProfileData);
    public static final File AW_SECONDPROFILE_ICON = new File(secondProfileIcon);
    public static final File AW_SECONDPROFILE_CUSTOMFILES_FOLDER = new File(secondProfileCustomFilesFolder);

    public static final File AW_THIRDPROFILE_DATA= new File(thirdProfileData);
    public static final File AW_THIRDPROFILE_ICON = new File(thirdProfileIcon);
    public static final File AW_THIRDPROFILE_CUSTOMFILES_FOLDER = new File(thirdProfileCustomFilesFolder);

    // Path dont on a besoin
    public static final Path awFilesFolder = Paths.get(filesFolder);
    public static final Path awCrashFolder = Paths.get(crashFolder);
    public static final Path awGameFilesFolder = Paths.get(gameFilesFolder);
    public static final Path awDataFolder = Paths.get(dataFolder);
    public static final Path awLogsFile = Paths.get(logsFile);

    public static final Path awFirstProfileData = Paths.get(firstProfileData);
    public static final Path awFirstProfileIcon = Paths.get(firstProfileIcon);
    public static final Path awFirstProfileCustomFilesFolder = Paths.get(firstProfileCustomFilesFolder);

    public static final Path awSecondProfileData = Paths.get(secondProfileData);
    public static final Path awSecondProfileIcon = Paths.get(secondProfileIcon);
    public static final Path awSecondProfileCustomFilesFolder = Paths.get(secondProfileCustomFilesFolder);

    public static final Path awThirdProfileData = Paths.get(thirdProfileData);
    public static final Path awThirdProfileIcon = Paths.get(thirdProfileIcon);
    public static final Path awThirdProfileCustomFilesFolder = Paths.get(thirdProfileCustomFilesFolder);

    public static final GameInfos AW_INFOS = new GameInfos("Astrauworld", awGameFilesFolder, new GameVersion(mcVersion, GameType.V1_13_HIGHER_FORGE.setNFVD(new NewForgeVersionDiscriminator(forgeVersion, mcVersion, "20211210.034407"))), new GameTweak[] {GameTweak.FORGE});

    private static AuthInfos authInfos;

    static boolean maximumSet = false;

    private static final CrashReporter crashReporter = new CrashReporter("Astrauworld Launcher", awCrashFolder);

    public static void saveInfosWhenConnect(MicrosoftAuthResult result){
        initSelectedSaver();
        selectedSaver.set(ProfileSaver.KEY.INFOS_EMAIL, LauncherPanel.profileAccountTextField.getText());
        selectedSaver.set(ProfileSaver.KEY.INFOS_NAME, result.getProfile().getName());
        selectedSaver.set(ProfileSaver.KEY.INFOS_ACCESSTOKEN, result.getAccessToken());
        selectedSaver.set(ProfileSaver.KEY.INFOS_REFRESHTOKEN, result.getRefreshToken());
        selectedSaver.set(ProfileSaver.KEY.INFOS_UUID, result.getProfile().getId());
        selectedSaver.set(ProfileSaver.KEY.SETTINGS_PROFILENAME, result.getProfile().getName());
    }

    public static void microsoftAuth(String email, String password) throws MicrosoftAuthenticationException {
        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        MicrosoftAuthResult result = authenticator.loginWithCredentials(email, password);

        saveInfosWhenConnect(result);

        System.out.println("Compte enregistré " + result.getProfile().getName() + " (compte Microsoft)");
        authInfos = new AuthInfos(result.getProfile().getName(), result.getAccessToken(), result.getProfile().getId(), "", "");
    }

    public static void microsoftAuthWebview() throws MicrosoftAuthenticationException {
        System.out.println("webview?");
        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        MicrosoftAuthResult result = authenticator.loginWithWebview();
        System.out.println("webview");

        saveInfosWhenConnect(result);

        System.out.println("Compte enregistré : " + result.getProfile().getName() + " (compte Microsoft) via la webview");
    }

    /**
     * À utiliser seulement lorsque le jeu se lance après
     * @throws MicrosoftAuthenticationException quand la connexion échoue
     */
    public static void connect() throws MicrosoftAuthenticationException {
        infosLabel.setText("Connexion...");

        initSelectedSaver();

        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        if (Objects.equals(selectedSaver.get(ProfileSaver.KEY.INFOS_REFRESHTOKEN), null)) {
            throw new MicrosoftAuthenticationException("Aucun compte connecté");
        } else {
            MicrosoftAuthResult result = authenticator.loginWithRefreshToken(selectedSaver.get(ProfileSaver.KEY.INFOS_REFRESHTOKEN));
        }

        authInfos = new AuthInfos(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), selectedSaver.get(ProfileSaver.KEY.INFOS_ACCESSTOKEN), selectedSaver.get(ProfileSaver.KEY.INFOS_UUID), "", "");
        System.out.println("Connecté avec " + selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
        infosLabel.setText("Connect\u00e9 avec " + selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));

    }

    public static void launch() throws Exception{
        initSelectedSaver();

        NoFramework noFramework= new NoFramework(awGameFilesFolder, authInfos, GameFolder.FLOW_UPDATER);
        noFramework.getAdditionalArgs().addAll(Arrays.asList("--Xmx", selectedSaver.get(ProfileSaver.KEY.SETTINGS_RAM) + "G", "--server", "207.180.196.61", "--port", "33542"));

        LauncherFrame.getInstance().setVisible(false);

        noFramework.launch(mcVersion, forgeVersion, NoFramework.ModLoader.FORGE);

        ProfileSaver.saveCustomFiles(selectedSaver);
        System.exit(0);

    }

    public static void localLaunch() throws Exception {
        initSelectedSaver();

        NoFramework noFramework= new NoFramework(awGameFilesFolder, authInfos, GameFolder.FLOW_UPDATER);
        noFramework.getAdditionalArgs().addAll(Arrays.asList("--Xmx", selectedSaver.get(ProfileSaver.KEY.SETTINGS_RAM) + "G"));

        LauncherFrame.getInstance().setVisible(false);

        ProfileSaver.saveCustomFiles(selectedSaver);

        Process process = noFramework.launch(mcVersion, forgeVersion, NoFramework.ModLoader.FORGE);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String s;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        String[] args = new String[] {afterMcExitArg, selectedProfile};
        LauncherFrame.main(args);

    }

    public enum StepInfo {

        INTEGRATION("Chargement de l'int\u00e9gration..."),
        MOD_PACK("T\u00e9l\u00e9chargement du pack de mods..."),
        READ("Lecture du json..."),
        DL_LIBS("T\u00e9l\u00e9chargement des librairies..."),
        DL_ASSETS("T\u00e9l\u00e9chargement des assets..."),
        EXTRACT_NATIVES("Extraction des natives..."),
        MOD_LOADER("T\u00e9l\u00e9chargement du mod loader..."),
        MODS("T\u00e9l\u00e9chargement des mods..."),
        EXTERNAL_FILES("T\u00e9l\u00e9chargement des fichiers externes..."),
        POST_EXECUTIONS("Running post executions..."),
        END("Termin\u00e9!");

        final String details;

        StepInfo(String details) {
            this.details = details;
        }

        public String getDetails() {
            return details;
        }

    }

    public static Runnable postExecutions = () -> {
        loadingBar.setValue(0);
        loadingBar.setVisible(false);
        barLabel.setText("");
        percentLabel.setText("");
        infosLabel.setText("");

        initSelectedSaver();
        Saver saver = selectedSaver;
        ProfileSaver.loadCustomFiles(saver);
        ProfileSaver.saveCustomFiles(saver);
    };


    public static void update() throws Exception {

        initSelectedSaver();
        Saver saver = selectedSaver;

        Logger logger = new Logger("[Astrauworld Launcher]", awLogsFile);
        loadingBar.setVisible(true);

        IProgressCallback callback = new IProgressCallback() {
            private final DecimalFormat decimalFormat = new DecimalFormat("#.#");

            @Override
            public void init(ILogger logger) {
            }

            @Override
            public void step(Step step) {
                infosLabel.setText(StepInfo.valueOf(step.name()).getDetails());

            }

            public void onFileDownloaded(Path path) {
                barLabel.setText(path.getFileName().toString());
            }

            @Override
            public void update(DownloadList.DownloadInfo info) {

                long progressLong = info.getDownloadedBytes();
                long maximumLong = info.getTotalToDownloadBytes();
                long result = (progressLong * 100) / maximumLong;

                int progress = (int) info.getDownloadedBytes();
                int maximum = (int) info.getTotalToDownloadBytes();

                percentLabel.setText(result + "%");
                loadingBar.setValue(progress);
                loadingBar.setMaximum(maximum);
            }
        };

        final VanillaVersion vanillaVersion = new VanillaVersion.VanillaVersionBuilder()
                .withName(mcVersion)
                .build();

        final List<CurseFileInfo> modInfos = new ArrayList<>();

        // Liste des mods serveurs
        modInfos.add(new CurseFileInfo(352835, 3923045)); // Backpacked 2.1.10
        modInfos.add(new CurseFileInfo(416811, 4032100)); // Medieval Craft 1.19.2 weapons only pre-pre-alpha
        modInfos.add(new CurseFileInfo(509041, 4019310)); // Epic Knights [...] v6.7
        modInfos.add(new CurseFileInfo(237307, 4016732)); // Cosmetic Armor Reworked 1.19.2-v1a
        modInfos.add(new CurseFileInfo(238222, 4087658)); // JEI forge 11.4.0.286
        modInfos.add(new CurseFileInfo(416089, 4104024)); // Simple Voice Chat [FORGE] 1.19.2-2.3.16
        modInfos.add(new CurseFileInfo(274259, 3885508)); // Carry On 1.19-1.18.1.2
        modInfos.add(new CurseFileInfo(426558, 4071154)); // Alex's Mobs 1.21.0
        modInfos.add(new CurseFileInfo(331936, 4087465)); //    |_> Citadel 2.1.0-1.19
        modInfos.add(new CurseFileInfo(421377, 3907904)); // HT's TreeChop 1.19.1-0.15.8
        modInfos.add(new CurseFileInfo(316582, 3920717)); // Corpse 1.19.2-1.0.0
        modInfos.add(new CurseFileInfo(225738, 3913301)); // MmmMmmMmmMmm (Target Dummy) 1.19.2-1.5.5
        modInfos.add(new CurseFileInfo(350727, 3947216)); // Joy of Painting (xerapaint) 1.19.2-1.0.0
        modInfos.add(new CurseFileInfo(341448, 3947219)); // Music Maker Mod (xeramusic) 1.19.2-1.0.0
        modInfos.add(new CurseFileInfo(328085, 4011414)); // Create v0.5.0f
        modInfos.add(new CurseFileInfo(360203, 3970122)); // Guard Villagers 1.15.2
        modInfos.add(new CurseFileInfo(581854, 3905172)); // InvMove v0.8.1
        modInfos.add(new CurseFileInfo(348521, 3972423)); //    |_> Cloth Config API v8.2.88
        modInfos.add(new CurseFileInfo(441647, 3919861)); // FramedBlocks 6.6.2
        modInfos.add(new CurseFileInfo(558126, 4064323)); // This Rocks! 1.19.2-1.2.2
        modInfos.add(new CurseFileInfo(377051, 3943018)); // Bed Benefits 1.19.2-9.1.2
        modInfos.add(new CurseFileInfo(228525, 4052856)); //    |_> Bookshelf 1.19.2-16.1.11
        modInfos.add(new CurseFileInfo(608235, 3919439)); // YDM's Weapon Master 1.19.x-v3.0.3
        modInfos.add(new CurseFileInfo(260262, 3872707)); // ToolBelt 1.19-1.19.6
        modInfos.add(new CurseFileInfo(634062, 4082456)); // No Chat Reports FORGE-1.19.2-v1.5.1
        modInfos.add(new CurseFileInfo(570319, 4036347)); // Human Companions 1.19.2-1.5.1
        modInfos.add(new CurseFileInfo(450659, 3926824)); // Small Ships 1.19.2-2.0.0-Alpha-0.4
        modInfos.add(new CurseFileInfo(223852, 3884263)); // Storage Drawers 1.19-11.1.2
        modInfos.add(new CurseFileInfo(542110, 3877554)); // Jumpy Boats 1.19.2-0.1.0.5
        modInfos.add(new CurseFileInfo(64760 , 3921272)); // SecurityCraft v1.9.3.1
        modInfos.add(new CurseFileInfo(398521, 3999157)); // Farmer's Delight 1.2
        modInfos.add(new CurseFileInfo(55438 , 3907425)); // MrCrayfish's Furniture Mod 7.0.0-pre34
        modInfos.add(new CurseFileInfo(482378, 3969415)); // ParCool! 1.19.2-1.0.0.2-R
        modInfos.add(new CurseFileInfo(60028 , 4074684)); // Aquaculture2 1.19.2-2.4.8
        modInfos.add(new CurseFileInfo(243121, 4102873)); // Quark 3.3-373
        modInfos.add(new CurseFileInfo(250363, 4100299)); //   |_> AutoRegLib 1.8.2-55
        modInfos.add(new CurseFileInfo(351725, 4018295)); // Macaw's Bridges v2.0.5
        modInfos.add(new CurseFileInfo(359540, 4018184)); //   "     Furniture v3.0.2
        modInfos.add(new CurseFileInfo(352039, 4028405)); //   "     Roofs v2.2.1
        modInfos.add(new CurseFileInfo(378646, 3930976)); //   "     Doors v1.0.7
        modInfos.add(new CurseFileInfo(629153, 3923436)); //   "     Path and Pavings v1.0.1
        modInfos.add(new CurseFileInfo(502372, 3923131)); //   "     Lights and Lamps v1.0.4
        modInfos.add(new CurseFileInfo(453925, 3923128)); //   "     Fences and Walls v1.0.6
        modInfos.add(new CurseFileInfo(400933, 3923124)); //   "     Trapdoors v1.0.7
        modInfos.add(new CurseFileInfo(438116, 3922999)); //   "     Paintings v1.0.4
        modInfos.add(new CurseFileInfo(363569, 3830460)); //   "     Windows v2.0.3
        modInfos.add(new CurseFileInfo(373774, 3909206)); // Rare Ice v0.5.1

        initClientMods(saver, modInfos);

        AbstractForgeVersion forge;

        if (Objects.equals(saver.get(KEY.MOD_OPTIFINE), "true")) {
            forge = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW)
                    .withForgeVersion(mcVersion + "-" + forgeVersion)
                    .withOptiFine(new OptiFineInfo(optifineVersion))
                    .withCurseMods(modInfos)
                    .withFileDeleter(new ModFileDeleter(true))
                    .build();
        } else {
            forge = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW)
                    .withForgeVersion(mcVersion + "-" + forgeVersion)
                    .withCurseMods(modInfos)
                    .withFileDeleter(new ModFileDeleter(true))
                    .build();
        }

        final FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder()
                .withVanillaVersion(vanillaVersion)
                .withLogger(logger)
                .withProgressCallback(callback)
                .withModLoaderVersion(forge)
                .withPostExecutions(Collections.singletonList(postExecutions))
                .build();
        updater.update(awGameFilesFolder);
    }

    public static CrashReporter getCrashReporter() {
        return crashReporter;
    }

}
