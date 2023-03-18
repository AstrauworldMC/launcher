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
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.main.LauncherFrame;
import fr.timeto.astrauworld.launcher.main.LauncherSystemTray;
import fr.timeto.astrauworld.launcher.main.Main;
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

import static fr.timeto.astrauworld.launcher.main.Launcher.postExecutions;
import static fr.timeto.astrauworld.launcher.main.LauncherFrame.getInstance;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.loadingBar;
import static fr.timeto.astrauworld.launcher.main.LauncherSystemTray.getJava;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;

public class Server extends ArrayList<Mod> {

    public static final int MODLOADER_FORGE = 1;
    public static final int MODLOADER_FABRIC = 2;

    protected final String serverName;
    protected final boolean isWhitelist;
    protected final String mcVersion;
    protected final int modLoader;
    protected final String modLoaderVersion;
    protected final MCPingOptions mcPingOptions;
    protected final String optifineVersion;
    protected final Path path;

    protected AuthInfos authInfos;
    protected ArrayList<MinecraftProfile> whitelist;

    public Server(String serverName, boolean whitelist, String mcVersion, MCPingOptions mcPingOptions, Path pathFromAstrauworldFolder) {
        this(serverName, whitelist, mcVersion, 0, null, mcPingOptions, pathFromAstrauworldFolder, null);
    }

    public Server(String serverName, boolean whitelist, String mcVersion, int modLoader, String modLoaderVersion, MCPingOptions mcPingOptions, Path pathFromAstrauworldFolder, String optifineVersion) {
        this.serverName = serverName;
        this.isWhitelist = whitelist;
        this.mcVersion = mcVersion;
        this.modLoader = modLoader;
        this.modLoaderVersion = modLoaderVersion;
        this.mcPingOptions = mcPingOptions;
        this.path = pathFromAstrauworldFolder;
        this.optifineVersion = optifineVersion;
    }

    public void update(Saver saver) throws Exception {
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

        initClientMods(saver, modInfos);

        AbstractForgeVersion forge;
        FabricVersion fabric;
        FlowUpdater.FlowUpdaterBuilder updaterBuilder = new FlowUpdater.FlowUpdaterBuilder();

        if (modLoader == MODLOADER_FORGE) {
            if (Integer.parseInt(mcVersion.split("\\.")[1]) >= 12) {
                if (Objects.equals(saver.get(ProfileSaver.KEY.MOD_OPTIFINE.get()), "true")){
                    forge = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW)
                            .withForgeVersion(mcVersion + "-" + modLoaderVersion)
                            .withOptiFine(new OptiFineInfo(optifineVersion))
                            .withCurseMods(this.getCurseFileInfoArrayList())
                            .withFileDeleter(new ModFileDeleter(true))
                            .build();
                } else {
                    forge = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW)
                            .withForgeVersion(mcVersion + "-" + modLoaderVersion)
                            .withCurseMods(this.getCurseFileInfoArrayList())
                            .withFileDeleter(new ModFileDeleter(true))
                            .build();
                }
            } else {
                if (Objects.equals(saver.get(ProfileSaver.KEY.MOD_OPTIFINE.get()), "true")){
                    forge = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.OLD)
                            .withForgeVersion(mcVersion + "-" + modLoaderVersion)
                            .withOptiFine(new OptiFineInfo(optifineVersion))
                            .withCurseMods(this.getCurseFileInfoArrayList())
                            .withFileDeleter(new ModFileDeleter(true))
                            .build();
                } else {
                    forge = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.OLD)
                            .withForgeVersion(mcVersion + "-" + modLoaderVersion)
                            .withCurseMods(this.getCurseFileInfoArrayList())
                            .withFileDeleter(new ModFileDeleter(true))
                            .build();
                }
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

        File whitelistResourcePacksFolder = Paths.get(path.toString(), "resourcePacks").toFile();
        File whitelistShaderPacksFolder = Paths.get(path.toString(), "shaderpacks").toFile();
        File whitelistOptionsFile = Paths.get(path.toString(), "options.txt").toFile();
        File whitelistOptionsOFFile = Paths.get(path.toString(), "optionsof.txt").toFile();
        File whitelistOptionsShadersFile = Paths.get(path.toString(), "optionsshaders.txt").toFile();

        initCustomFilesFolder(saver);

        TimFilesUtils.deleteDirectory(whitelistResourcePacksFolder, false);
        TimFilesUtils.deleteDirectory(whitelistShaderPacksFolder, false);
        whitelistResourcePacksFolder.mkdirs();
        TimFilesUtils.copyFiles(resourcepacksProfileFolder, whitelistResourcePacksFolder, false);
        TimFilesUtils.copyFile(optionsProfileTextfile, whitelistOptionsFile, false);

        if (modLoader != 0 && Objects.equals(saver.get(KEY.MOD_OPTIFINE.get()), "true")) {
            whitelistShaderPacksFolder.mkdirs();
            TimFilesUtils.copyFiles(shaderpacksProfileFolder, whitelistShaderPacksFolder, false);
            TimFilesUtils.copyFile(optionsOFProfileTextfile, whitelistOptionsOFFile, false);
            TimFilesUtils.copyFile(optionsShadersProfileTextfile, whitelistOptionsShadersFile, false);
        }

        updater.update(path);
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
        connect(saver);

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

        NoFramework noFramework= new NoFramework(path, authInfos, GameFolder.FLOW_UPDATER);
        noFramework.getAdditionalVmArgs().add("-Xmx" + Math.round(Double.parseDouble(getSelectedSaver().get(ProfileSaver.KEY.SETTINGS_RAM.get()))) + "G");
        noFramework.getAdditionalArgs().addAll(Arrays.asList("--server", mcPingOptions.getHostname(), "--port", Integer.toString(mcPingOptions.getPort())));

        LauncherFrame.getInstance().setVisible(false);

        Process process;
        if (modLoader == MODLOADER_FORGE) {
            process = noFramework.launch(mcVersion, modLoaderVersion, NoFramework.ModLoader.FORGE);
        } else if (modLoader == MODLOADER_FABRIC) {
            process = noFramework.launch(mcVersion, modLoaderVersion, NoFramework.ModLoader.FABRIC);
        } else {
            process = noFramework.launch(mcVersion, null, NoFramework.ModLoader.VANILLA);
        }

        LauncherSystemTray.initGameSystemTray(getSelectedProfile(saver));
        getInstance().setName("AstrauworldMC");
        DiscordManager.setGamePresence(authInfos);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String s;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        File whitelistResourcePacksFolder = Paths.get(path.toString(), "resourcePacks").toFile();
        File whitelistShaderPacksFolder = Paths.get(path.toString(), "shaderpacks").toFile();
        File whitelistOptionsFile = Paths.get(path.toString(), "options.txt").toFile();
        File whitelistOptionsOFFile = Paths.get(path.toString(), "optionsof.txt").toFile();
        File whitelistOptionsShadersFile = Paths.get(path.toString(), "optionsshaders.txt").toFile();

        initCustomFilesFolder(saver);

        TimFilesUtils.deleteDirectory(whitelistResourcePacksFolder, false);
        TimFilesUtils.deleteDirectory(whitelistShaderPacksFolder, false);
        resourcepacksProfileFolder.mkdirs();
        TimFilesUtils.copyFiles(whitelistResourcePacksFolder, resourcepacksProfileFolder, false);
        TimFilesUtils.copyFile(whitelistOptionsFile, optionsProfileTextfile, false);

        if (modLoader != 0 && Objects.equals(saver.get(KEY.MOD_OPTIFINE.get()), "true")) {
            shaderpacksProfileFolder.mkdirs();
            TimFilesUtils.copyFiles(whitelistShaderPacksFolder, shaderpacksProfileFolder, false);
            TimFilesUtils.copyFile(whitelistOptionsOFFile, optionsOFProfileTextfile, false);
            TimFilesUtils.copyFile(whitelistOptionsShadersFile, optionsShadersProfileTextfile, false);
        }

        Main.main(null);

    }

    public void setWhitelist(ArrayList<MinecraftProfile> whitelist) {
        this.whitelist = whitelist;
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

    public String[] getNameArray() {
        ArrayList<String> list = new ArrayList<>();
        int i = this.toArray().length;
        int ii = 0;
        while (ii != i) {
            list.add(this.get(ii).getName());
            ii ++;
        }
        return list.toArray(new String[0]);
    }

    public String[] getVersionArray() {
        ArrayList<String> list = new ArrayList<>();
        int i = this.toArray().length;
        int ii = 0;
        while (ii != i) {
            list.add(this.get(ii).getVersion());
            ii ++;
        }
        return list.toArray(new String[0]);
    }

    public String[] getNameAndVersionArray() {
        ArrayList<String> list = new ArrayList<>();
        int i = this.toArray().length;
        int ii = 0;
        while (ii != i) {
            list.add(this.get(ii).getNameAndVersion());
            ii ++;
        }
        return list.toArray(new String[0]);
    }

    public CurseFileInfo[] getCurseFileInfoArray() {
        ArrayList<CurseFileInfo> list = new ArrayList<>();
        int i = this.toArray().length;
        int ii = 0;
        while (ii != i) {
            list.add(this.get(ii).getCurseFileInfo());
            ii ++;
        }
        return list.toArray(new CurseFileInfo[0]);
    }

    public ArrayList<String> getNameArrayList() {
        ArrayList<String> list = new ArrayList<>();
        int i = this.toArray().length;
        int ii = 0;
        while (ii != i) {
            list.add(this.get(ii).getName());
            ii ++;
        }
        return list;
    }

    public ArrayList<String> getVersionArrayList() {
        ArrayList<String> list = new ArrayList<>();
        int i = this.toArray().length;
        int ii = 0;
        while (ii != i) {
            list.add(this.get(ii).getVersion());
            ii ++;
        }
        return list;
    }

    public ArrayList<String> getNameAndVersionArrayList() {
        ArrayList<String> list = new ArrayList<>();
        int i = this.toArray().length;
        int ii = 0;
        while (ii != i) {
            list.add(this.get(ii).getNameAndVersion());
            ii ++;
        }
        return list;
    }

    public ArrayList<CurseFileInfo> getCurseFileInfoArrayList() {
        ArrayList<CurseFileInfo> list = new ArrayList<>();
        int i = this.toArray().length;
        int ii = 0;
        while (ii != i) {
            list.add(this.get(ii).getCurseFileInfo());
            ii ++;
        }
        return list;
    }
}
