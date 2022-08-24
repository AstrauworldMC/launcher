package fr.timeto.astrauworld.launcher;

import fr.flowarg.openlauncherlib.NewForgeVersionDiscriminator;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.util.CrashReporter;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    static final String forgeVerion = "40.1.73";

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

    public static final GameInfos AW_INFOS = new GameInfos("Astrauworld", awGameFilesFolder, new GameVersion(mcVersion, GameType.V1_13_HIGHER_FORGE.setNFVD(new NewForgeVersionDiscriminator(forgeVerion, mcVersion, "20211210.034407"))), new GameTweak[] {GameTweak.FORGE});

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

    }

}
