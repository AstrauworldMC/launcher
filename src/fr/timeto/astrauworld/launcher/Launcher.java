package fr.timeto.astrauworld.launcher;

import fr.flowarg.flowlogger.ILogger;
import fr.flowarg.flowlogger.Logger;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.DownloadList;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.Step;
import fr.flowarg.flowupdater.download.json.CurseFileInfo;
import fr.flowarg.flowupdater.download.json.Mod;
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

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Launcher {

    static final String separatorChar = System.getProperty("file.separator");
    static final String userHomeDir = System.getProperty("user.home");

    // String des les path dont on a besoin
    static final String filesFolder = userHomeDir + separatorChar + "AppData" + separatorChar + "Roaming" + separatorChar + "Astrauworld Launcher";
    static final String crashFolder = filesFolder + separatorChar + "crashes";
    static final String gameFilesFolder = filesFolder + separatorChar + "GameFiles";
    static final String dataFolder = filesFolder + separatorChar + "data";
    static final String logsFile = filesFolder + separatorChar + "logs.txt";
    static final String firstProfileData = dataFolder + separatorChar + "firstProfile.properties";
    static final String secondProfileData = dataFolder + separatorChar + "secondProfile.properties";
    static final String thirdProfileData = dataFolder + separatorChar + "thirdProfile.properties";

    // Version de Minecraft et de Forge utilisée
    static final String mcVersion = "1.18.2";
    static final String forgeVersion = "40.1.73";

    // Version du launcher
    public static final String version = "BETA2.0.0";

    // File des dont on a besoin
    public static final File AW_DIR = new File(filesFolder);
    public static final File AW_CRASH_FOLDER = new File(crashFolder);
    public static final File AW_GAMEFILES_FOLDER = new File(gameFilesFolder);
    public static final File AW_DATA_FOLDER = new File(dataFolder);
    public static final File AW_FIRSTPROFILE_DATA = new File(firstProfileData);
    public static final File AW_SECONDPROFILE_DATA = new File(secondProfileData);
    public static final File AW_THIRDPROFILE_DATA = new File(thirdProfileData);

    // Path dont on a besoin
    public static final Path awFilesFolder = Paths.get(filesFolder);
    public static final Path awCrashFolder = Paths.get(crashFolder);
    public static final Path awGameFilesFolder = Paths.get(gameFilesFolder);
    public static final Path awDataFolder = Paths.get(dataFolder);
    public static final Path awLogsFile = Paths.get(logsFile);
    public static final Path awFirstProfileData = Paths.get(firstProfileData);
    public static final Path awSecondProfileData = Paths.get(secondProfileData);
    public static final Path awThirdProfileData = Paths.get(thirdProfileData);

    public static final GameInfos AW_INFOS = new GameInfos("Astrauworld", awGameFilesFolder, new GameVersion(mcVersion, GameType.V1_13_HIGHER_FORGE.setNFVD(new NewForgeVersionDiscriminator(forgeVersion, mcVersion, "20211210.034407"))), new GameTweak[] {GameTweak.FORGE});

    private static AuthInfos authInfos;
    private static Thread updateThread;

    static boolean maximumSetted = false;

    private static CrashReporter crashReporter = new CrashReporter("Astrauworld Launcher", awCrashFolder);

    public static void microsoftAuth(String username, String password) throws MicrosoftAuthenticationException {
        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        MicrosoftAuthResult result = authenticator.loginWithCredentials(username, password);

        System.out.println("Connecté avec: " + result.getProfile().getName() + " (compte Microsoft)");
        authInfos = new AuthInfos(result.getProfile().getName(), result.getAccessToken(), result.getProfile().getId(), "", "");
    }

    public static void launch() throws Exception{
        NoFramework noFramework = new NoFramework(awGameFilesFolder, authInfos, GameFolder.FLOW_UPDATER);
        // noFramework.getAddtionalArgs().addAll(Arrays.asList("--server", "207.180.196.61", "--port", "33542"));

        LauncherFrame.getInstance().setVisible(false);

        noFramework.launch(mcVersion, forgeVersion);
        System.exit(0);
    }

    public enum StepInfo {

        INTEGRATION("Chargement de l’integration..."),
        MOD_PACK("Téléchargement du pack de mods..."),
        DL_LIBS("Téléchargement des librairies..."),
        DL_ASSETS("Téléchargement des assets..."),
        EXTRACT_NATIVES("Extraction des natives..."),
        FORGE("Installation de Forge..."),
        FABRIC("Installation de Fabric..."),
        MODS("Téléchargement des mods..."),
        EXTERNAL_FILES("Téléchargement des fichiers externes..."),
        POST_EXECUTIONS("Post-executions..."),
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

            public void onFileDownloads(Path path) {
                LauncherPanel.fileLabel.setEtxt(path.getFileName().toString());
            }

            @Override
            public void update(DownloadList.DownloadInfo info) {
                int progress = (int) info.getDownloadedBytes();
                int maximum = (int) info.getTotalToDownloadBytes();

                LauncherPanel.percentLabel.setText(decimalFormat.format((progress / maximum) * 100) + "%");
                LauncherPanel.progressBar.setValue(progress);
                LauncherPanel.progressNar.setMaximum(maximum);
            }
        };

        final VanillaVersion vanillaVersion = new VanillaVersion.VanillaVersionBuilder()
                .withName(mcVersion)
                .build();

        final List<CurseFileInfo> modInfos = new ArrayList<>();

        final List<Mod> mods = new ArrayList<>();

        final AbstractForgeVersion forge = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW)
                .withForgeVersion(mcVersion + "-" + forgeVersion)
                .withCurseMods(modInfos)
                .withMods(mods)
                .build();

        final FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder()
                .withVanillaVersion(vanillaVersion)
                .withLogger(logger)
                .withProgressCallback(callback)
                .withModLoaderVersion(forge)
                .build();
        updater.update(awGameFilesFolder);
    }

    public static void installOtherFiles(String wantedResources) {
        final FlowUpdater installer = new FlowUpdater.FlowUpdaterBuilder()
                .build();
        // installer.update();
    }

    public static void interruptThread() {
        updateThread.interrupt();
    }

    public static CrashReporter getCrashReporter() {
        return crashReporter;
    }

}
