package fr.timeto.astrauworld.launcher.pagesutilities;

import br.com.azalim.mcserverping.MCPingOptions;
import fr.flowarg.flowlogger.ILogger;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.DownloadList;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.Step;
import fr.flowarg.flowupdater.download.json.CurseFileInfo;
import fr.flowarg.flowupdater.download.json.OptiFineInfo;
import fr.flowarg.flowupdater.utils.ModFileDeleter;
import fr.flowarg.flowupdater.versions.*;
import fr.flowarg.openlauncherlib.NoFramework;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.litarvan.openauth.microsoft.model.response.MinecraftProfile;
import fr.theshark34.openlauncherlib.JavaUtil;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.timeto.astrauworld.launcher.main.*;
import fr.timeto.astrauworld.launcher.secret.DiscordManager;
import fr.timeto.timutilslib.PopUpMessages;
import fr.timeto.timutilslib.TimFilesUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

import static fr.timeto.astrauworld.launcher.main.Launcher.*;
import static fr.timeto.astrauworld.launcher.main.LauncherFrame.getInstance;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.loadingBar;
import static fr.timeto.astrauworld.launcher.main.LauncherSystemTray.getJava;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;
import static fr.timeto.timutilslib.PopUpMessages.errorMessage;

public class Server {
    public static boolean playButtonsCanBeEnabled = true;

    public static final int MODLOADER_FORGE = 1;
    public static final int MODLOADER_FABRIC = 2;

    protected String serverName;
    protected boolean isWhitelist;
    protected final String mcVersion;
    protected final int modLoader;
    protected final String modLoaderVersion;
    protected MCPingOptions mcPingOptions;
    protected final String optifineVersion;
    protected final Path path;
    protected boolean usePort;

    protected AuthInfos authInfos;
    protected ArrayList<Mod> mods = new ArrayList<>();
    protected ArrayList<MinecraftProfile> whitelist = new ArrayList<>();
    protected String ipOnConnect = null;

    public Server(String serverName, boolean whitelist, String mcVersion, MCPingOptions mcPingOptions, boolean usePort, Path pathFromAstrauworldFolder) {
        this(serverName, whitelist, mcVersion, 0, null, mcPingOptions, usePort, pathFromAstrauworldFolder, null);
    }

    public Server(String serverName, boolean whitelist, String mcVersion, int modLoader, String modLoaderVersion, MCPingOptions mcPingOptions, boolean usePort, Path pathFromAstrauworldFolder, String optifineVersion) {
        this.serverName = serverName;
        this.isWhitelist = whitelist;
        this.mcVersion = mcVersion;
        this.modLoader = modLoader;
        this.modLoaderVersion = modLoaderVersion;
        this.mcPingOptions = mcPingOptions;
        this.usePort = usePort;
        this.path = Path.of(awFilesFolder.toFile() + File.separator + pathFromAstrauworldFolder.toString());
        this.optifineVersion = optifineVersion;
    }

    public void update(Saver saver) throws Exception {
        LauncherPanel.enablePlayButtons(false);
        playButtonsCanBeEnabled = false;

        path.toFile().mkdirs();

        loadingBar.setVisible(true);

        IProgressCallback callback = new IProgressCallback() {
            private final DecimalFormat decimalFormat = new DecimalFormat("#.#");

            @Override
            public void init(ILogger logger) {
            }

            @Override
            public void step(Step step) {
                infosLabel.setText("[" + serverName + "] " + Launcher.StepInfo.valueOf(step.name()).getDetails());

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

        modInfos.addAll(getModsCurseFileInfoArrayList());

        initClientMods(saver, modInfos, mcVersion);

        AbstractForgeVersion forge;
        FabricVersion fabric;
        FlowUpdater.FlowUpdaterBuilder updaterBuilder = new FlowUpdater.FlowUpdaterBuilder();

        if (modLoader == MODLOADER_FORGE) {
            if (Integer.parseInt(mcVersion.split("\\.")[1]) >= 12) {
                ForgeVersionBuilder forge_temp = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW)
                        .withForgeVersion(mcVersion + "-" + modLoaderVersion)
                        .withCurseMods(modInfos)
                        .withFileDeleter(new ModFileDeleter(true));
                if (Objects.equals(saver.get(ProfileSaver.KEY.MOD_OPTIFINE.get()), "true")) {
                    Launcher.println(" ------------------------------------------- ");
                    Launcher.println(optifineVersion);
                    OptiFineInfo opti=new OptiFineInfo(optifineVersion);
                    Launcher.println(opti.getVersion());
                    forge_temp.withOptiFine(opti);
                    Launcher.println(" ------------------------------------------- ");
                }
                forge = forge_temp.build();
            } else {
                ForgeVersionBuilder forge_temp = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.OLD)
                        .withForgeVersion(mcVersion + "-" + modLoaderVersion)
                        .withCurseMods(modInfos)
                        .withFileDeleter(new ModFileDeleter(true));
                if (Objects.equals(saver.get(ProfileSaver.KEY.MOD_OPTIFINE.get()), "true")) {
                    forge_temp.withOptiFine(new OptiFineInfo(optifineVersion));
                }
                forge = forge_temp.build();
            }

            updaterBuilder.withModLoaderVersion(forge);
        } else if (modLoader == MODLOADER_FABRIC) {
            Thread t = new Thread(() -> {
                System.exit(1);
            });
            PopUpMessages.errorMessage("Erreur serveurs", "L'API Fabric n'est pas encore disponible", t);
        }

        final FlowUpdater updater = updaterBuilder
                .withVanillaVersion(vanillaVersion)
                .withProgressCallback(callback)
                .withPostExecutions(Collections.singletonList(postExecutions))
                .build();

        if (!serverName.contains("AstrauworldMC")) {
            File whitelistResourcePacksFolder = new File(path.toString(), "resourcePacks");
            File whitelistShaderPacksFolder = new File(path.toString(), "shaderpacks");
            File whitelistOptionsOFFile = new File(path.toString(), "optionsof.txt");
            File whitelistOptionsShadersFile = new File(path.toString(), "optionsshaders.txt");

            initCustomFilesFolder(saver);

            whitelistResourcePacksFolder.mkdirs();
            whitelistShaderPacksFolder.mkdirs();
            TimFilesUtils.deleteDirectory(whitelistResourcePacksFolder, false);
            TimFilesUtils.deleteDirectory(whitelistShaderPacksFolder, false);
            whitelistResourcePacksFolder.mkdirs();
            TimFilesUtils.copyFiles(resourcepacksProfileFolder, whitelistResourcePacksFolder, false);

            if (modLoader != 0 && Objects.equals(saver.get(KEY.MOD_OPTIFINE.get()), "true")) {
                whitelistShaderPacksFolder.mkdirs();
                TimFilesUtils.copyFiles(shaderpacksProfileFolder, whitelistShaderPacksFolder, false);
                whitelistShaderPacksFolder.mkdir();
                whitelistOptionsOFFile.createNewFile();
                whitelistOptionsShadersFile.createNewFile();
                TimFilesUtils.copyFile(optionsOFProfileTextfile, whitelistOptionsOFFile, false);
                TimFilesUtils.copyFile(optionsShadersProfileTextfile, whitelistOptionsShadersFile, false);
            }
        }

        updater.update(path);

        playButtonsCanBeEnabled = true;
        LauncherPanel.enablePlayButtons(true);
    }

    private void connect(Saver saver) throws MicrosoftAuthenticationException {
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

    public void launch(Saver saver) throws Exception {
        launch(true, saver);
    }

    public void launch(boolean connectToServer, Saver saver) throws Exception {
        LauncherPanel.enablePlayButtons(false);
        playButtonsCanBeEnabled = false;

        try {
            connect(saver);
        } catch (MicrosoftAuthenticationException m) {
            playButtonsCanBeEnabled = true;
            LauncherPanel.enablePlayButtons(true);
            errorMessage("Erreur de connexion", "Erreur, impossible de se connecter");
            infosLabel.setText("Connexion \u00e9chou\u00e9e");
            loadingBar.setVisible(false);
            infosLabel.setVisible(false);
            return;
        }

        String javaCommand;
        final Path java = Paths.get(getJava().getAbsolutePath(), "bin", "java");
        if (System.getProperty("os.name").toLowerCase().contains("win"))
            javaCommand = "\"" + java + "\"";
        else javaCommand = java.toString();
        JavaUtil.setJavaCommand(javaCommand);
        infosLabel.setText("");

        System.out.println(authInfos.getUsername());
        System.out.println(authInfos.getUuid());
        System.out.println(authInfos.getAccessToken());
        System.out.println(authInfos.getAuthXUID());
        System.out.println(authInfos.getClientId());

        NoFramework noFramework= new NoFramework(path, authInfos, GameFolder.FLOW_UPDATER);
        noFramework.getAdditionalVmArgs().add("-Xmx" + Math.round(Double.parseDouble(getSelectedSaver().get(ProfileSaver.KEY.SETTINGS_RAM.get()))) + "G");
        if(connectToServer) {
            if (usePort) {
                noFramework.getAdditionalArgs().addAll(Arrays.asList("--server", mcPingOptions.getHostname(), "--port", Integer.toString(mcPingOptions.getPort())));
            } else {
                noFramework.getAdditionalArgs().addAll(Arrays.asList("--server", mcPingOptions.getHostname()));
            }
        }
        LauncherFrame.getInstance().setVisible(false);
        infosLabel.setVisible(false);
        percentLabel.setVisible(false);
        loadingBar.setVisible(false);
        barLabel.setVisible(false);

        ProfileSaver.loadCustomFiles(saver);

        if (modLoader == MODLOADER_FORGE) {
            Launcher.process = noFramework.launch(mcVersion, modLoaderVersion, NoFramework.ModLoader.FORGE);
        } else if (modLoader == MODLOADER_FABRIC) {
            Launcher.process = noFramework.launch(mcVersion, modLoaderVersion, NoFramework.ModLoader.FABRIC);
        } else {
            Launcher.process = noFramework.launch(mcVersion, null, NoFramework.ModLoader.VANILLA);
        }

        LauncherSystemTray.initGameSystemTray(getSelectedProfile(saver));
        getInstance().setName("AstrauworldMC");
        DiscordManager.setGamePresence(authInfos);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(Launcher.process.getInputStream()));

        String s;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
            if (s.contains("Connecting to ")) {
                String ip = getServerHostname();
                if (ipOnConnect != null) {
                    ip = ipOnConnect;
                }

                if (s.contains(ip) || s.contains(getServerHostname())) {
                    DiscordManager.setGamePresence(authInfos, this);
                    Launcher.println("Connexion à un serveur connu détecté");
                }
            } else if (s.contains("Clearing audio channels") || s.contains("Stopping JEI") || s.contains("Disconnecting voicechat") || s.contains("Stopping microphone thread")) {
                DiscordManager.setGamePresence(authInfos);
                Launcher.println("Déconnexion d'un serveur détecté");
            }
        }

        Launcher.println("");
        Launcher.println("");

        if (!serverName.contains("AstrauworldMC")) {
            File whitelistResourcePacksFolder = new File(path.toString(), "resourcePacks");
            File whitelistShaderPacksFolder = new File(path.toString(), "shaderpacks");
            File whitelistOptionsOFFile = new File(path.toString(), "optionsof.txt");
            File whitelistOptionsShadersFile = new File(path.toString(), "optionsshaders.txt");

            initCustomFilesFolder(saver);

            whitelistResourcePacksFolder.mkdirs();
            whitelistShaderPacksFolder.mkdirs();
            TimFilesUtils.deleteDirectory(whitelistResourcePacksFolder, false);
            TimFilesUtils.deleteDirectory(whitelistShaderPacksFolder, false);
            whitelistResourcePacksFolder.mkdirs();
            TimFilesUtils.copyFiles(whitelistResourcePacksFolder, resourcepacksProfileFolder, false);

            if (modLoader != 0 && Objects.equals(saver.get(KEY.MOD_OPTIFINE.get()), "true")) {
                whitelistShaderPacksFolder.mkdirs();
                TimFilesUtils.copyFiles(whitelistShaderPacksFolder, shaderpacksProfileFolder, false);
                whitelistShaderPacksFolder.mkdir();
                whitelistOptionsOFFile.createNewFile();
                whitelistOptionsShadersFile.createNewFile();
                TimFilesUtils.copyFile(whitelistOptionsOFFile, optionsOFProfileTextfile, false);
                TimFilesUtils.copyFile(whitelistOptionsShadersFile, optionsShadersProfileTextfile, false);
            }
        }

        playButtonsCanBeEnabled = true;
        LauncherPanel.enablePlayButtons(false);

        if (!serverName.contains("AstrauworldMC")) {
            String[] args = new String[] {afterMcExitArg, getSelectedProfile(saver)};
            Main.main(args);
        } else {
            Main.main(new String[]{afterMcExitArg, "0"});
        }

    }

    public void setIpOnConnect(String ipOnConnect) {
        this.ipOnConnect = ipOnConnect;
    }

    public String getIpOnConnect() {
        return ipOnConnect;
    }

    public String getServerName() {
        return serverName;
    }

    public MCPingOptions getMcPingOptions() {
        return mcPingOptions;
    }

    public String getServerHostname() {
        return mcPingOptions.getHostname();
    }

    public int getServerPort() {
        return mcPingOptions.getPort();
    }

    public ArrayList<MinecraftProfile> getWhitelist() {
        return whitelist;
    }

    public boolean isWhitelist() {
        return isWhitelist;
    }

    public int getModLoader() {
        return modLoader;
    }

    public Path getPath() {
        return path;
    }

    public String getMcVersion() {
        return mcVersion;
    }

    public String getModLoaderVersion() {
        return modLoaderVersion;
    }

    public String getOptifineVersion() {
        return optifineVersion;
    }

    public Mod[] getModsArray() {
        ArrayList<Mod> list = new ArrayList<>();
        int i = mods.toArray().length;
        int ii = 0;
        while (ii != i) {
            list.add(mods.get(ii));
            ii ++;
        }
        return list.toArray(new Mod[0]);
    }

    public String[] getModsNameArray() {
        ArrayList<String> list = new ArrayList<>();
        int i = mods.toArray().length;
        int ii = 0;
        while (ii != i) {
            list.add(mods.get(ii).getName());
            ii ++;
        }
        return list.toArray(new String[0]);
    }

    public String[] getModsVersionArray() {
        ArrayList<String> list = new ArrayList<>();
        int i = mods.toArray().length;
        int ii = 0;
        while (ii != i) {
            list.add(mods.get(ii).getVersion());
            ii ++;
        }
        return list.toArray(new String[0]);
    }

    public String[] getModsNameAndVersionArray() {
        ArrayList<String> list = new ArrayList<>();
        int i = mods.toArray().length;
        int ii = 0;
        while (ii != i) {
            list.add(mods.get(ii).getNameAndVersion());
            ii ++;
        }
        return list.toArray(new String[0]);
    }

    public CurseFileInfo[] getModsCurseFileInfoArray() {
        ArrayList<CurseFileInfo> list = new ArrayList<>();
        int i = mods.toArray().length;
        int ii = 0;
        while (ii != i) {
            list.add(mods.get(ii).getCurseFileInfo());
            ii ++;
        }
        return list.toArray(new CurseFileInfo[0]);
    }

    public ArrayList<Mod> getModsList() {
        return mods;
    }

    public ArrayList<String> getModsNameArrayList() {
        ArrayList<String> list = new ArrayList<>();
        int i = mods.toArray().length;
        int ii = 0;
        while (ii != i) {
            list.add(mods.get(ii).getName());
            ii ++;
        }
        return list;
    }

    public ArrayList<String> getModsVersionArrayList() {
        ArrayList<String> list = new ArrayList<>();
        int i = mods.toArray().length;
        int ii = 0;
        while (ii != i) {
            list.add(mods.get(ii).getVersion());
            ii ++;
        }
        return list;
    }

    public ArrayList<String> getModsNameAndVersionArrayList() {
        ArrayList<String> list = new ArrayList<>();
        int i = mods.toArray().length;
        int ii = 0;
        while (ii != i) {
            list.add(mods.get(ii).getNameAndVersion());
            ii ++;
        }
        return list;
    }

    public ArrayList<CurseFileInfo> getModsCurseFileInfoArrayList() {
        ArrayList<CurseFileInfo> list = new ArrayList<>();
        int i = mods.toArray().length;
        int ii = 0;
        while (ii != i) {
            list.add(mods.get(ii).getCurseFileInfo());
            ii ++;
        }
        return list;
    }
}
