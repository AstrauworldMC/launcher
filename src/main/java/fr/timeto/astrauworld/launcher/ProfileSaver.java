package fr.timeto.astrauworld.launcher;

import fr.flowarg.flowupdater.download.json.CurseFileInfo;
import fr.theshark34.openlauncherlib.util.Saver;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import static fr.timeto.astrauworld.launcher.LauncherPanel.*;
import static fr.timeto.timutilslib.PopUpMessages.*;
import static fr.timeto.timutilslib.CustomFonts.*;
import static fr.timeto.timutilslib.TimFilesUtils.*;

public class ProfileSaver {
    public static Saver firstProfileSaver = new Saver(Launcher.awFirstProfileData);
    public static Saver secondProfileSaver = new Saver(Launcher.awSecondProfileData);
    public static Saver thirdProfileSaver = new Saver(Launcher.awThirdProfileData);

    public static String selectedProfile = "";
    public static Saver selectedSaver;

    public static void initSelectedProfile() {
        if (tabLabel.toString().contains("1")) {
            selectedProfile = "1";
        } else if (tabLabel.toString().contains("2")) {
            selectedProfile = "2";
        } else if (tabLabel.toString().contains("3")) {
            selectedProfile = "3";
        }
    }
    public static void initSelectedSaver() {
        if (Objects.equals(selectedProfile, "1")) {
            selectedSaver = firstProfileSaver;
        } else if (Objects.equals(selectedProfile, "2")) {
            selectedSaver = secondProfileSaver;
        } else if (Objects.equals(selectedProfile, "3")) {
            selectedSaver = thirdProfileSaver;
        } else {
            initSelectedProfile();
            initSelectedSaver();
        }
    }

    public static void initializeDataFiles(Saver saver) {
        if(!Objects.equals(saver.get(KEY.FILECREATED), "true")) {
            // Informations générales
            saver.set(KEY.INFOS_NAME, "none");
            saver.set(KEY.INFOS_EMAIL, "none");
            saver.set(KEY.INFOS_UUID, "none");
            saver.set(KEY.INFOS_ACCESSTOKEN, "none");
            saver.set(KEY.INFOS_REFRESHTOKEN, "none");
            // Configuration de Minecraft, si la key commence par 'mod' -> mod client
            saver.set(KEY.MOD_OPTIFINE, "false");
            saver.set(KEY.MOD_FPSMODEL, "false");
            saver.set(KEY.MOD_BETTERTPS, "false");
            saver.set(KEY.MOD_FALLINGLEAVES, "false");
            saver.set(KEY.MOD_APPLESKIN, "false");
            saver.set(KEY.MOD_SOUNDPHYSICS, "false");

            saver.set(KEY.SETTINGS_PROFILENAME, "none");
            saver.set(KEY.SETTINGS_HELMICON, "true");
            saver.set(KEY.SETTINGS_RAM, "2");

            saver.set(KEY.FILECREATED, "true");
        }
    }

    public static void initializeDataFiles() {
        initializeDataFiles(firstProfileSaver);
        initializeDataFiles(secondProfileSaver);
        initializeDataFiles(thirdProfileSaver);
    }

    public static void dlProfileIcon(String imageURL, int profile) throws IOException {
        String destinationFile = "";
        if(profile == 1){
            destinationFile = Launcher.firstProfileIcon;
        } else if(profile == 2){
            destinationFile = Launcher.secondProfileIcon;
        }else if(profile == 3){
            destinationFile = Launcher.thirdProfileIcon;
        }
        URL url = new URL(imageURL);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

    public static void initProfileIcon(Saver saver) throws IOException {
        String url;
        if (saver.get(KEY.SETTINGS_HELMICON).contains("true")) {
            url = "https://minotar.net/helm/" + saver.get(KEY.INFOS_UUID) + "/34.png";
        } else {
            url = "https://minotar.net/avatar/" + saver.get(KEY.INFOS_UUID) + "/34.png";
        }

        if (saver == firstProfileSaver) {
            dlProfileIcon(url, 1);
        } else if (saver == secondProfileSaver) {
            dlProfileIcon(url, 2);
        } else if (saver == thirdProfileSaver) {
            dlProfileIcon(url, 3);
        }
    }

    public static void initProfileIcon() throws IOException {
        initProfileIcon(firstProfileSaver);
        initProfileIcon(secondProfileSaver);
        initProfileIcon(thirdProfileSaver);
    }

    public static void initClientMods(Saver selectedSaver, List modList) {

        if (Objects.equals(selectedSaver.get(KEY.MOD_FPSMODEL), "true")) {
            modList.add(new CurseFileInfo(333287, 4018928)); // First Peron Model 2.2.0 - Forge
        }

        if (Objects.equals(selectedSaver.get(KEY.MOD_BETTERTPS), "true")) {
            modList.add(new CurseFileInfo(435044, 3834422)); // Better Third Person 1.8.1
        }

        if (Objects.equals(selectedSaver.get(KEY.MOD_FALLINGLEAVES), "true")) {
            modList.add(new CurseFileInfo(463155, 3965374)); // Falling Leaves 1.3.1
        }

        if (Objects.equals(selectedSaver.get(KEY.MOD_APPLESKIN), "true")) {
            modList.add(new CurseFileInfo(248787, 3872808)); // Apple Skin 2.4.2
        }

        if (Objects.equals(selectedSaver.get(KEY.MOD_SOUNDPHYSICS), "true")) {
            modList.add(new CurseFileInfo(535489, 4064927)); // Sound Physics Remastered v0.5.1
        }

    }

    public static void openMoreInfosUrl(String key) {
        if (Objects.equals(key, KEY.MOD_FPSMODEL)) {
            try {
                Desktop.getDesktop().browse(new URL("https://www.curseforge.com/minecraft/mc-mods/first-person-model").toURI());
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        } else if (Objects.equals(key, KEY.MOD_BETTERTPS)) {
            try {
                Desktop.getDesktop().browse(new URL("https://www.curseforge.com/minecraft/mc-mods/better-third-person").toURI());
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        } else if (Objects.equals(key, KEY.MOD_FALLINGLEAVES)) {
            try {
                Desktop.getDesktop().browse(new URL("https://www.curseforge.com/minecraft/mc-mods/falling-leaves-forge").toURI());
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        } else if (Objects.equals(key, KEY.MOD_APPLESKIN)) {
            try {
                Desktop.getDesktop().browse(new URL("https://www.curseforge.com/minecraft/mc-mods/appleskin").toURI());
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        } else if (Objects.equals(key, KEY.MOD_SOUNDPHYSICS)) {
            try {
                Desktop.getDesktop().browse(new URL("https://www.curseforge.com/minecraft/mc-mods/sound-physics-remastered").toURI());
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*  Liste des fichiers custom à sauvegarder dans GameFiles
     *
     * ./saves
     * ./resourcepacks (hors ceux du launcher) TODO <-
     * ./shaderpacks (hors ceux du launcher) TODO <-
     * ./music_sheets
     * ./shematics
     * ./config
     * options.txt
     *
     */

    private static final File savesFolder = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "saves");
    public static final File resourcespacksFolder = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "resourcepacks");
    public static final File shaderspacksFolder = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "shaderpacks");
    private static final File musicsheetsFolder = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "music_sheets");
    private static final File shematicsFolder = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "schematics");
    private static final File configFolder = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "config");
    private static final File optionsTextfile = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "options.txt");

    public static final File lastSavedProfileFilesText = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "lastSavedProfile.txt");
    private static String lastSavedProfile = null;

    private static File savesProfileFolder = null;
    private static File resourcespacksProfileFolder = null;
    private static File shaderspacksProfileFolder = null;
    private static File musicsheetsProfileFolder = null;
    private static File shematicsProfileFolder = null;
    private static File configProfileFolder = null;
    private static File optionsProfileTextfile = null;

    private static void initCustomFilesFolder(Saver saver) {
        File customFilesFolder = null;
        if (saver == firstProfileSaver) {
            customFilesFolder = Launcher.AW_FIRSTPROFILE_CUSTOMFILES_FOLDER;
            lastSavedProfile = "1";
        } else if (saver == secondProfileSaver) {
            customFilesFolder = Launcher.AW_SECONDPROFILE_CUSTOMFILES_FOLDER;
            lastSavedProfile = "2";
        } else if (saver == thirdProfileSaver) {
            customFilesFolder = Launcher.AW_THIRDPROFILE_CUSTOMFILES_FOLDER;
            lastSavedProfile = "3";
        }

        savesProfileFolder = new File(customFilesFolder + Launcher.separatorChar + "saves");
        resourcespacksProfileFolder = new File(customFilesFolder + Launcher.separatorChar + "resourcepacks");
        shaderspacksProfileFolder = new File(customFilesFolder + Launcher.separatorChar + "shaderpacks");
        musicsheetsProfileFolder = new File(customFilesFolder + Launcher.separatorChar + "music_sheets");
        shematicsProfileFolder = new File(customFilesFolder + Launcher.separatorChar + "schematics");
        configProfileFolder = new File(customFilesFolder + Launcher.separatorChar + "config");
        optionsProfileTextfile = new File(customFilesFolder + Launcher.separatorChar + "options.txt");
    }

    public static void saveCustomFiles(Saver saver)  {
        initCustomFilesFolder(saver);

        try {
            if (saver != null) {
                copyFiles(savesFolder, savesProfileFolder);
                copyFiles(resourcespacksFolder, resourcespacksProfileFolder);
                copyFiles(shaderspacksFolder, shaderspacksProfileFolder);
                copyFiles(musicsheetsFolder, musicsheetsProfileFolder);
                copyFiles(shematicsFolder, shematicsProfileFolder);
                copyFiles(configFolder, configProfileFolder);
            }
        } catch (IOException ignored) {}

        try {
            PrintWriter printwriter = new PrintWriter(new FileOutputStream(optionsProfileTextfile));
            printwriter.println("");

            printwriter.close();

            copyFile(optionsTextfile, optionsProfileTextfile);
        } catch (Exception ex) {
            System.out.println("Error clear file "+ optionsProfileTextfile);
        }

        try {
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(lastSavedProfileFilesText));
            printWriter.println(lastSavedProfile);

            printWriter.close();
        } catch (Exception ignored) {

        }

    }

    public static void loadCustomFiles(Saver saver) {
        initCustomFilesFolder(saver);

        try {
            copyFiles(savesProfileFolder, savesFolder);
            copyFiles(resourcespacksProfileFolder, resourcespacksFolder);
            copyFiles(shaderspacksProfileFolder, shaderspacksFolder);
            copyFiles(musicsheetsProfileFolder, musicsheetsFolder);
            copyFiles(shematicsProfileFolder, shematicsFolder);
            copyFiles(configProfileFolder, configFolder);
        } catch (IOException ignored) {}

        try {
            PrintWriter printwriter = new PrintWriter(new FileOutputStream(lastSavedProfileFilesText));
            printwriter.println("");

            printwriter.close();

            copyFile(optionsProfileTextfile, optionsTextfile);
        } catch (Exception ex) {
            System.out.println("Error clear file "+ optionsTextfile);
        }

    }

    public static Saver getLastSavedProfileSaver() throws IOException {
        Saver saver = null;

        FileReader fileReader = new FileReader(lastSavedProfileFilesText);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String strSaver = bufferedReader.readLine();

        if (Objects.equals(strSaver, "1")) {
            saver = firstProfileSaver;
        } else if (Objects.equals(strSaver, "2")) {
            saver = secondProfileSaver;
        } else if (Objects.equals(strSaver, "3")) {
            saver = thirdProfileSaver;
        }

        return saver;
    }

    public static class KEY {
        public static final String INFOS_NAME = "infos|name";
        public static final String INFOS_EMAIL = "infos|email";
        public static final String INFOS_UUID = "infos|UUID";
        public static final String INFOS_ACCESSTOKEN = "infos|accessToken";
        public static final String INFOS_REFRESHTOKEN = "infos|refreshToken";

        public static final String MOD_OPTIFINE = "mod|Optifine";
        public static final String MOD_FPSMODEL = "mod|FirstPersonModel";
        public static final String MOD_BETTERTPS = "mod|BetterThirdPerson";
        public static final String MOD_FALLINGLEAVES = "mod|FallingLeaves";
        public static final String MOD_APPLESKIN = "mod|AppleSkin";
        public static final String MOD_SOUNDPHYSICS = "mod|SoundPhysicsRemastered";

        public static final String SETTINGS_PROFILENAME = "settings|ProfileName";
        public static final String SETTINGS_HELMICON = "settings|HelmIcon";
        public static final String SETTINGS_RAM = "settings|AllowedRam";

        public static final String FILECREATED = "fileCreated";
    }
}
