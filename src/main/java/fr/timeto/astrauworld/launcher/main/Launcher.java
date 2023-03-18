package fr.timeto.astrauworld.launcher.main;

import br.com.azalim.mcserverping.MCPingOptions;
import fr.flowarg.flowlogger.ILogger;
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
import fr.theshark34.openlauncherlib.JavaUtil;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.timeto.astrauworld.launcher.secret.DiscordManager;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;
import net.harawata.appdirs.AppDirsFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static fr.timeto.astrauworld.launcher.main.LauncherFrame.getInstance;
import static fr.timeto.astrauworld.launcher.main.LauncherFrame.launcherProperties;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.astrauworld.launcher.main.LauncherSystemTray.getJava;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;

@SuppressWarnings("unused")
public class Launcher {

    public static final String separatorChar = File.separator;

    public static final String afterMcExitArg = "--afterMcExit";
    public static final String devEnvArg = "--dev";

    // String des les path dont on a besoin
    public static final String filesFolder =  AppDirsFactory.getInstance().getUserDataDir("Astrauworld Launcher", null, null, true);
    public static final String crashFolder = filesFolder + separatorChar + "crashes";
    public static final String gameFilesFolder = filesFolder + separatorChar + "GameFiles";
    public static final String dataFolder = filesFolder + separatorChar + "data";
    public static final String globalSettingsData = dataFolder + separatorChar + "settings.properties";

    public static final String firstProfileData = dataFolder + separatorChar + "firstProfile.properties";
    public static final String firstProfileIcon = dataFolder + separatorChar + "firstProfile.png";
    public static final String firstProfileCustomFilesFolder = dataFolder + separatorChar + "firstProfileCustomFiles";

    public static final String secondProfileData = dataFolder + separatorChar + "secondProfile.properties";
    public static final String secondProfileIcon = dataFolder + separatorChar + "secondProfile.png";
    public static final String secondProfileCustomFilesFolder = dataFolder + separatorChar + "secondProfileCustomFiles";

    public static final String thirdProfileData = dataFolder + separatorChar + "thirdProfile.properties";
    public static final String thirdProfileIcon = dataFolder + separatorChar + "thirdProfile.png";
    public static final String thirdProfileCustomFilesFolder = dataFolder + separatorChar + "thirdProfileCustomFiles";

    // Version de Minecraft et de Forge utilisée
    public static final String mcVersion = launcherProperties.getProperty("mcVersion");
    public static final String forgeVersion = launcherProperties.getProperty("forgeVersion");
    public static final String optifineVersion = launcherProperties.getProperty("optifineVersion"); // FIXME Bug certaines textures sont unies
    static MCPingOptions serverOptions = MCPingOptions.builder()
            .hostname(launcherProperties.getProperty("serverHostname")) // 207.180.196.61
            .port(Integer.parseInt(launcherProperties.getProperty("serverPort"))) //33542
            .build();

    // Version du launcher
    public static final String version = launcherProperties.getProperty("launcherVersion");
    private static final String voiceChatConnectServerIP = "????????????";

    // File des dont on a besoin
    public static final File AW_DIR = new File(filesFolder);
    public static final File AW_CRASH_FOLDER = new File(crashFolder);
    public static final File AW_GAMEFILES_FOLDER = new File(gameFilesFolder);
    public static final File AW_DATA_FOLDER = new File(dataFolder);
    public static final File AW_SETTINGS_DATA = new File(globalSettingsData);

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
    public static final Path awSettingsData = Paths.get(globalSettingsData);

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

    public static Process process = null;

    public static void println(String str) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        
        System.out.println("[" + dtf.format(now) + "] [Astrauworld Launcher] " + str);
    }

    public static String convertStringArrayToString(String[] strArr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String str : strArr)
            sb.append(str).append(delimiter);
        return sb.substring(0, sb.length() - 1);
    }

    public static void saveInfosWhenConnect(Saver saver, MicrosoftAuthResult result, String oldAccount){
        saver.set(KEY.INFOS_EMAIL.get(), profileAccountPage.textField.getText());
        saver.set(KEY.INFOS_NAME.get(), result.getProfile().getName());
        saver.set(KEY.INFOS_ACCESSTOKEN.get(), result.getAccessToken());
        saver.set(KEY.INFOS_REFRESHTOKEN.get(), result.getRefreshToken());
        saver.set(KEY.INFOS_UUID.get(), result.getProfile().getId());
        if (Objects.equals(oldAccount, "no")) {
            saver.set(KEY.SETTINGS_PROFILENAME.get(), result.getProfile().getName());
        }
    }

    public static void microsoftAuth(String email, String password, Saver saver) throws MicrosoftAuthenticationException {
        String oldAccount = saver.get(KEY.INFOS_NAME.get());
        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        MicrosoftAuthResult result = authenticator.loginWithCredentials(email, password);

        saveInfosWhenConnect(saver, result, oldAccount);

        Launcher.println("Compte enregistré " + result.getProfile().getName() + " (compte Microsoft)");
        authInfos = new AuthInfos(result.getProfile().getName(), result.getAccessToken(), result.getProfile().getId(), result.getXuid(), result.getClientId());
    }

    public static void microsoftAuthWebview(Saver saver) throws MicrosoftAuthenticationException {
        String oldAccount = saver.get(KEY.INFOS_NAME.get());
        Launcher.println("webview?");
        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        MicrosoftAuthResult result = authenticator.loginWithWebview();
        Launcher.println("webview");

        saveInfosWhenConnect(saver, result, oldAccount);

        Launcher.println("Compte enregistré : " + result.getProfile().getName() + " (compte Microsoft) via la webview");
        authInfos = new AuthInfos(result.getProfile().getName(), result.getAccessToken(), result.getProfile().getId(), result.getXuid(), result.getClientId());
    }

    /**
     * À utiliser seulement lorsque le jeu se lance après
     * @throws MicrosoftAuthenticationException quand la connexion échoue
     */
    public static void connect(Saver saver) throws MicrosoftAuthenticationException {
        infosLabel.setText("Connexion...");

        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        MicrosoftAuthResult result;
        if (Objects.equals(saver.get(ProfileSaver.KEY.INFOS_REFRESHTOKEN.get()), null)) {
            throw new MicrosoftAuthenticationException("Aucun compte connecté");
        } else {
            result = authenticator.loginWithRefreshToken(saver.get(ProfileSaver.KEY.INFOS_REFRESHTOKEN.get()));
        }

        authInfos = new AuthInfos(result.getProfile().getName(), result.getAccessToken(), result.getProfile().getId(), result.getXuid(), result.getClientId());
        Launcher.println("Connecté avec " + saver.get(ProfileSaver.KEY.INFOS_NAME.get()));
        infosLabel.setText("Connect\u00e9 avec " + saver.get(ProfileSaver.KEY.INFOS_NAME.get()));

    }

    public static void launch(boolean connectToServer, Saver saver) throws Exception{
        String javaCommand;
        final Path java = Paths.get(getJava().getAbsolutePath(), "bin", "java");
        if (System.getProperty("os.name").toLowerCase().contains("win"))
            javaCommand = "\"" + java + "\"";
        else javaCommand = java.toString();
        JavaUtil.setJavaCommand(javaCommand);

        System.out.println(authInfos.getUsername());
        System.out.println(authInfos.getUuid());
        System.out.println(authInfos.getAccessToken());
        System.out.println(authInfos.getAuthXUID());
        System.out.println(authInfos.getClientId());

        NoFramework noFramework= new NoFramework(awGameFilesFolder, authInfos, GameFolder.FLOW_UPDATER);
        noFramework.getAdditionalVmArgs().add("-Xmx" + Math.round(Double.parseDouble(getSelectedSaver().get(ProfileSaver.KEY.SETTINGS_RAM.get()))) + "G");
        if (connectToServer) {
            noFramework.getAdditionalArgs().addAll(Arrays.asList("--server", serverOptions.getHostname(), "--port", Integer.toString(serverOptions.getPort())));
        }

        LauncherFrame.getInstance().setVisible(false);

        process = noFramework.launch(mcVersion, forgeVersion, NoFramework.ModLoader.FORGE);
        LauncherSystemTray.initGameSystemTray(getSelectedProfile(saver));
        getInstance().setName("AstrauworldMC");
        DiscordManager.setGamePresence(authInfos);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String s;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
            if (voiceChatConnectServerIP != null) {
                if (s.contains("Connecting to server:")) {
                    String[] split = s.split("\'");
                    if (Objects.equals(split[s.length() - 2], voiceChatConnectServerIP)) {
                        DiscordManager.setGamePresence(authInfos, "AstrauworldMC");
                    }
                } else if (s.contains("Disconnecting voicechat")) {
                    DiscordManager.setGamePresence(authInfos);
                }
            }
        }

        Launcher.println("");
        Launcher.println("");

        String[] args = new String[] {afterMcExitArg, getSelectedProfile(saver)};
        Main.main(args);

    }

    public static void localLaunch(Saver saver) throws Exception {
        String javaCommand;
        final Path java = Paths.get(getJava().getAbsolutePath(), "bin", "java");
        if (System.getProperty("os.name").toLowerCase().contains("win"))
            javaCommand = "\"" + java + "\"";
        else javaCommand = java.toString();
        JavaUtil.setJavaCommand(javaCommand);

        NoFramework noFramework= new NoFramework(awGameFilesFolder, authInfos, GameFolder.FLOW_UPDATER);
        noFramework.getAdditionalArgs().addAll(Arrays.asList("--Xmx", saver.get(ProfileSaver.KEY.SETTINGS_RAM.get()) + "G"));

        getInstance().setVisible(false);

        ProfileSaver.saveCustomFiles(saver);

        process = noFramework.launch(mcVersion, forgeVersion, NoFramework.ModLoader.FORGE);
        LauncherSystemTray.initGameSystemTray(getSelectedProfile(saver));
        getInstance().setName("AstrauworldMC");
        DiscordManager.setGamePresence(authInfos);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String s;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        String[] args = new String[] {afterMcExitArg, getSelectedProfile(saver)};
        Main.main(args);

    }

    public static String parseUnicode(String oldString) {
        return oldString
                .replaceAll("é", "\u00e9")
                .replaceAll("è", "\u00e8")
                .replaceAll("ê", "\u00ea")
                .replaceAll("É", "\u00c9")
                .replaceAll("È", "\u00c8")
                .replaceAll("Ê", "\u00ca")
                .replaceAll("à", "\u00e0")
                .replaceAll("á", "\u00e1")
                .replaceAll("â", "\u00e2")
                .replaceAll("À", "\u00c0")
                .replaceAll("Â", "\u00c2");
    }

    public static String unparseUnicode(String oldString) {
        return oldString
                .replaceAll("\u00e9", "é")
                .replaceAll("\u00e8", "è")
                .replaceAll("\u00ea", "ê")
                .replaceAll("\u00c9", "É")
                .replaceAll("\u00c8", "È")
                .replaceAll("\u00ca", "Ê")
                .replaceAll("\u00e0", "à")
                .replaceAll("\u00e1", "á")
                .replaceAll("\u00e2", "â")
                .replaceAll("\u00c0", "À")
                .replaceAll("\u00c2", "Â");
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

    private static Saver updateSaver;

    public static Runnable postExecutions = () -> {
        loadingBar.setValue(0);
        loadingBar.setVisible(false);
        barLabel.setText("");
        percentLabel.setText("");
        infosLabel.setText("");

        Saver saver = updateSaver;
        ProfileSaver.loadCustomFiles(saver);
        ProfileSaver.saveCustomFiles(saver);
    };


    public static void update(Saver saver) throws Exception {
        updateSaver = saver;

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
        modInfos.add(new CurseFileInfo(352835, 3923041)); // Backpacked 2.1.10
        modInfos.add(new CurseFileInfo(416811, 3858618)); // Medieval Craft 1.18.2 weapons only
        modInfos.add(new CurseFileInfo(509041, 4393487)); // Epic Knights [...] 7.1
        modInfos.add(new CurseFileInfo(419699, 4384391)); //    |_> Architectury API v4.11.89
        modInfos.add(new CurseFileInfo(237307, 4016730)); // Cosmetic Armor Reworked 1.18.2-v2a
        modInfos.add(new CurseFileInfo(238222, 3940240)); // JEI forge 1.18.1-9.7.1.255
        modInfos.add(new CurseFileInfo(416089, 4372207)); // Simple Voice Chat [FORGE] 1.18.2-2.3.28
    //    modInfos.add(new CurseFileInfo(274259, 3674344)); // Carry On 1.18.2-1.17.0.8
        modInfos.add(new CurseFileInfo(426558, 3853078)); // Alex's Mobs 1.18.6
        modInfos.add(new CurseFileInfo(331936, 3783096)); //    |_> Citadel 1.11.3-1.18.2
        modInfos.add(new CurseFileInfo(421377, 4388478)); // HT's TreeChop 1.18.2-0.17.4
        modInfos.add(new CurseFileInfo(433969, 3912070)); // HT's TreePlant 1.18.2-0.3.1
        modInfos.add(new CurseFileInfo(316582, 3694258)); // Corpse 1.18.2-1.0.1
        modInfos.add(new CurseFileInfo(225738, 3820503)); // MmmMmmMmmMmm (Target Dummy) 1.18.2-1.5.2
        modInfos.add(new CurseFileInfo(350727, 4119502)); // Joy of Painting (xerapaint) 1.18.2-1.0.1
        modInfos.add(new CurseFileInfo(341448, 4131736)); // Music Maker Mod (xeramusic) 1.18.2-1.0.2
        modInfos.add(new CurseFileInfo(328085, 3864525)); // Create v0.5.0
        modInfos.add(new CurseFileInfo(486392, 3864518)); //    |_> Flywheel forge 1.18.2-0.6.3
        modInfos.add(new CurseFileInfo(360203, 3823106)); // Guard Villagers 1.18.2-1.4.3
        modInfos.add(new CurseFileInfo(581854, 4346452)); // InvMove v0.8.2 (1.18. [Forge]
        modInfos.add(new CurseFileInfo(348521, 3972426)); //    |_> Cloth Config API v6.4.90
        modInfos.add(new CurseFileInfo(441647, 3850130)); // FramedBlocks 5.4.0
        modInfos.add(new CurseFileInfo(558126, 4341453)); // This Rocks! 1.18.2-1.0.4
        modInfos.add(new CurseFileInfo(377051, 3807788)); // Bed Benefits 1.18.2-6.0.2
        modInfos.add(new CurseFileInfo(228525, 4351251)); //    |_> Bookshelf 1.18.2-13.2.52
        modInfos.add(new CurseFileInfo(608235, 3919398)); // YDM's Weapon Master Forge - Multiplayer - 1.18.x - v3.0.3
        modInfos.add(new CurseFileInfo(260262, 4124030)); // Tool Belt 1.18.2-1.18.9
        modInfos.add(new CurseFileInfo(570319, 4279118)); // Human Companions 1.18.2-1.7.3
        modInfos.add(new CurseFileInfo(450659, 3908056)); // Small Ships 1.18.2-2.0.0-Alpha-0.4
        modInfos.add(new CurseFileInfo(223852, 3807626)); // Storage Drawers 1.18.2-10.2.1
        modInfos.add(new CurseFileInfo(542110, 3682173)); // Jumpy Boats 1.18.2-0.1.0.3
        modInfos.add(new CurseFileInfo(64760 , 3921270)); // SecurityCraft v1.9.3.1
        modInfos.add(new CurseFileInfo(398521, 3999153)); // Farmer's Delight 1.2 - 1.18.2
        modInfos.add(new CurseFileInfo(55438 , 4374992)); // MrCrayfish's Furniture Mod 7.0.0-pre35
        modInfos.add(new CurseFileInfo(482378, 3969410)); // ParCool! 1.18.2-2.0.0.3-R
        modInfos.add(new CurseFileInfo(60028 , 4183848)); // Aquaculture 2 1.18.2-2.3.10
        modInfos.add(new CurseFileInfo(243121, 3840125)); // Quark 3.2-358
        modInfos.add(new CurseFileInfo(250363, 3642382)); //   |_> AutoRegLib 1.7-53
        modInfos.add(new CurseFileInfo(579403, 3778254)); // Calemi's Economy 1.0.1
        modInfos.add(new CurseFileInfo(573646, 3778251)); //   |_> Calemi Core 1.0.14
        modInfos.add(new CurseFileInfo(351725, 4178158)); // Macaw's Bridges v2.0.6
        modInfos.add(new CurseFileInfo(359540, 4018178)); //   "     Furniture v3.0.2
        modInfos.add(new CurseFileInfo(352039, 4205653)); //   "     Roofs v2.2.2
        modInfos.add(new CurseFileInfo(378646, 4381503)); //   "     Doors v1.0.8
        modInfos.add(new CurseFileInfo(629153, 4126518)); //   "     Path and Pavings v1.0.2
        modInfos.add(new CurseFileInfo(502372, 4358260)); //   "     Lights and Lamps v1.0.5
        modInfos.add(new CurseFileInfo(453925, 4204541)); //   "     Fences and Walls v1.0.7
        modInfos.add(new CurseFileInfo(400933, 4181456)); //   "     Trapdoors v1.0.8
        modInfos.add(new CurseFileInfo(438116, 3922996)); //   "     Paintings v1.0.4
        modInfos.add(new CurseFileInfo(363569, 4203418)); //   "     Windows v2.1.1
        modInfos.add(new CurseFileInfo(373774, 3669561)); // Rare Ice v0.4.1
        modInfos.add(new CurseFileInfo(280200, 4087911)); // Colytra 1.18.1-5.2.0.4
        modInfos.add(new CurseFileInfo(308989, 3650485)); //   |_> Caelus API 1.18.1-3.0.0.2
        modInfos.add(new CurseFileInfo(517167, 3760573)); // Flash's NPCs 1.18.1-1.1.4v2
    //    modInfos.add(new CurseFileInfo(273771, 4367403)); // AstikorCarts 1.1.2
        modInfos.add(new CurseFileInfo(403422, 3955900)); // EmoteCraft 2.2.5-forge
        modInfos.add(new CurseFileInfo(658587, 4418152)); //   |_> PlayerAnimator 1.0.2
        modInfos.add(new CurseFileInfo(623373, 3955900)); //         |_> Bendy-lib 2.1.1

        initClientMods(saver, modInfos, mcVersion);

        AbstractForgeVersion forge;

        if (Objects.equals(saver.get(KEY.MOD_OPTIFINE.get()), "true")) {
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
