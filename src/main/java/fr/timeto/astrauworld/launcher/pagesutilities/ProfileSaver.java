package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.flowarg.flowupdater.download.json.CurseFileInfo;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.timeto.astrauworld.launcher.main.Launcher;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import static fr.timeto.timutilslib.TimFilesUtils.*;

/**
 * La classe qui regroupe tous les éléments en rapport avec les savers des profils
 * @author <a href="https://github.com/TimEtOff">TimEtO</a>
 */
public class ProfileSaver {
    /**
     * Le Saver du premier profil
     */
    public static Saver firstProfileSaver = new Saver(Launcher.awFirstProfileData);
    /**
     * Le Saver du deuxième profil
     */
    public static Saver secondProfileSaver = new Saver(Launcher.awSecondProfileData);
    /**
     * Le Saver du troisième profil
     */
    public static Saver thirdProfileSaver = new Saver(Launcher.awThirdProfileData);

    /**
     * Le profil sélectionné
     */
    public static String selectedProfile = "";
    /**
     * Le Saver sélectionné
     */
    public static Saver selectedSaver;

    /**
     * Initialise {@link ProfileSaver#selectedSaver} d'après le numéro de profil spécifié
     * @param profile Le numéro du profil spécifié en String
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     * @since Beta2.2.0
     */
    public static void initSelectedProfile(String profile) {
        selectedProfile = profile;
        if (Objects.equals(profile, "1")) {
            selectedSaver = firstProfileSaver;
        } else if (Objects.equals(profile, "2")) {
            selectedSaver = secondProfileSaver;
        } else if (Objects.equals(profile, "3")) {
            selectedSaver = thirdProfileSaver;
        }
    }

    /**
     * Initialise {@link ProfileSaver#selectedSaver} d'après le numéro de profil spécifié
     * @param profile Le numéro du profil spécifié en int
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     * @since Beta2.2.0
     */
    public static void initSelectedProfile(int profile) {
        selectedProfile = String.valueOf(profile);
        if (profile == 1) {
            selectedSaver = firstProfileSaver;
        } else if (profile == 2) {
            selectedSaver = secondProfileSaver;
        } else if (profile == 3) {
            selectedSaver = thirdProfileSaver;
        }
    }

    /**
     * Récupérer le Saver sélectionné (doit être initialisé d'abord)
     * @return Le Saver sélectionné
     * @since Beta2.2.0
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static String getSelectedProfile(Saver saver) {
        if (saver == firstProfileSaver) {
            return "1";
        } else if (saver == secondProfileSaver) {
            return "2";
        } else if (saver == thirdProfileSaver) {
            return "3";
        } else {
            return null;
        }
    }

    /**
     * Initialise le fichier de données du profil sélectionné s'il n'a pas déjà été créé
     * @param saver Le profil sélectionné
     * @see ProfileSaver#initializeDataFiles()
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void initializeDataFiles(Saver saver) {
        if(!Objects.equals(saver.get(KEY.FILECREATED), "true")) {
            // Informations générales
            saver.set(KEY.INFOS_NAME, "no");
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

    /**
     * Appelle {@link ProfileSaver#initializeDataFiles(Saver)} pour les trois Savers
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void initializeDataFiles() {
        initializeDataFiles(firstProfileSaver);
        initializeDataFiles(secondProfileSaver);
        initializeDataFiles(thirdProfileSaver);
    }

    /**
     * Télécharge l'image de profil depuis internet puis la stocke (../AppData/Roaming/Astrauworld Launcher/data)
     * @param imageURL L'URL de l'image
     * @param profile le numéro du profil
     * @throws IOException Si l'image n'est pas trouvée
     * @see ProfileSaver#initProfileIcon(Saver)
     * @see ProfileSaver#initProfileIcon()
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
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

    /**
     * Initialise l'image du profil sélectionné en définissant l'URL puis appelle {@link ProfileSaver#dlProfileIcon(String, int)}
     * @param saver Le Saver sélectionné
     * @throws IOException Si l'image n'est pas trouvée
     * @see ProfileSaver#initProfileIcon()
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void initProfileIcon(Saver saver) throws IOException {
        String url;
        if (saver.get(KEY.SETTINGS_PROFILENAME).toLowerCase().replaceAll(" ", "").equals("frisk")){
            url = "https://user-images.githubusercontent.com/97166376/209479948-9077d6d4-1254-4423-914b-d8b7ecf895d0.png";
            EasterEggs.setEatereggAsFound(EasterEggs.friskName);
        } else if (saver.get(KEY.SETTINGS_PROFILENAME).toLowerCase().replaceAll(" ", "").equals("chara")) {
            url = "https://user-images.githubusercontent.com/97166376/209479945-0b181aaa-f3bd-436c-8274-83f68302c93e.png";
            EasterEggs.setEatereggAsFound(EasterEggs.charaName);
        } else if (saver.get(KEY.SETTINGS_PROFILENAME).toLowerCase().replaceAll(" ", "").equals("asriel")) {
            url = "https://user-images.githubusercontent.com/97166376/209479946-e10c816c-6665-4347-9c8b-dd5165f42089.png";
            EasterEggs.setEatereggAsFound(EasterEggs.asrielName);
        } else if (saver.get(KEY.SETTINGS_PROFILENAME).toLowerCase().replaceAll(" ", "").equals("flowey")) {
            url = "https://user-images.githubusercontent.com/97166376/209479944-e76fbf8f-6aba-462f-afc5-08829af3f9c8.png";
            EasterEggs.setEatereggAsFound(EasterEggs.floweyName);
        } else if (saver.get(KEY.SETTINGS_PROFILENAME).toLowerCase().replaceAll(" ", "").equals("cursedflowey")) {
            url = "https://user-images.githubusercontent.com/97166376/209480184-79318022-8ba0-46c9-9773-504a63c2ee47.png";
            EasterEggs.setEatereggAsFound(EasterEggs.cursedFloweyName);
        } else if (saver.get(KEY.SETTINGS_HELMICON).contains("true")) {
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

    /**
     * Initialise les trois images des profils avec {@link ProfileSaver#initProfileIcon(Saver)}
     * @throws IOException Si une image n'est pas trouvée
     * @see ProfileSaver#dlProfileIcon(String, int)
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void initProfileIcon() throws IOException {
        initProfileIcon(firstProfileSaver);
        initProfileIcon(secondProfileSaver);
        initProfileIcon(thirdProfileSaver);
    }

    /**
     * Ajoute les mods clients à la liste spécifiée d'après le Saver sélectionné
     * @param selectedSaver Le Saver où les données seront cherchées
     * @param modList La liste où les mods seront ajoutés
     */
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

    /**
     * Les différents URLs pour en savoir plus sur les mods clients puis l'ouvre dans une nouvelle page du navigateur par défault
     * @param key La key du mod client
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
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
     * ./saves // FIXME problème où les mondes sont pas vraiment que pour un profil
     * ./resourcepacks (hors ceux du launcher) TODO <-
     * ./shaderpacks (hors ceux du launcher) TODO <-
     * ./music_sheets
     * ./schematics
     * ./config
     * options.txt
     * optionsof.txt
     *
     */

    /**
     * Le dossier des saves général
     */
    private static final File savesFolder = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "saves");
    /**
     * Le dossier des resources packs général
     */
    public static final File resourcepacksFolder = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "resourcepacks");
    /**
     * Le dossier des shaders général
     */
    public static final File shaderpacksFolder = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "shaderpacks");
    /**
     * Le dossier des music sheets général
     */
    private static final File musicsheetsFolder = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "music_sheets");
    /**
     * Le dossier des shematics général
     */
    private static final File schematicsFolder = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "schematics");
    /**
     * Le dossier des configs général
     */
    private static final File configFolder = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "config");
    /**
     * Le fichier des options général
     */
    private static final File optionsTextfile = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "options.txt");
    /**
     * Le fichier des options Optifine général
     */
    private static final File optionsOFTextfile = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "optionsof.txt");

    /**
     * Le dossier des saves des profils, initialisé plus tard
     */
    private static File savesProfileFolder = null;
    /**
     * Le dossier des resource packs des profils, initialisé plus tard
     */
    private static File resourcepacksProfileFolder = null;
    /**
     * Le dossier des shaders des profils, initialisé plus tard
     */
    private static File shaderpacksProfileFolder = null;
    /**
     * Le dossier des music sheets des profils, initialisé plus tard
     */
    private static File musicsheetsProfileFolder = null;
    /**
     * Le dossier des schematics des profils, initialisé plus tard
     */
    private static File schematicsProfileFolder = null;
    /**
     * Le dossier des configs des profils, initialisé plus tard
     */
    private static File configProfileFolder = null;
    /**
     * Le fichier des options des profils, initialisé plus tard
     */
    private static File optionsProfileTextfile = null;
    /**
     * Le fichier des options d'Optifine des profils, initialisé plus tard
     */
    private static File optionsOFProfileTextfile = null;

    /**
     * Initialise les dossiers customs
     * @param saver le Saver concerné
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    private static void initCustomFilesFolder(Saver saver) {
        File customFilesFolder = null;
        if (saver == firstProfileSaver) {
            customFilesFolder = Launcher.AW_FIRSTPROFILE_CUSTOMFILES_FOLDER;
        } else if (saver == secondProfileSaver) {
            customFilesFolder = Launcher.AW_SECONDPROFILE_CUSTOMFILES_FOLDER;
        } else if (saver == thirdProfileSaver) {
            customFilesFolder = Launcher.AW_THIRDPROFILE_CUSTOMFILES_FOLDER;
        }

        savesProfileFolder = new File(customFilesFolder + Launcher.separatorChar + "saves");
        resourcepacksProfileFolder = new File(customFilesFolder + Launcher.separatorChar + "resourcepacks");
        shaderpacksProfileFolder = new File(customFilesFolder + Launcher.separatorChar + "shaderpacks");
        musicsheetsProfileFolder = new File(customFilesFolder + Launcher.separatorChar + "music_sheets");
        schematicsProfileFolder = new File(customFilesFolder + Launcher.separatorChar + "schematics");
        configProfileFolder = new File(customFilesFolder + Launcher.separatorChar + "config");
        optionsProfileTextfile = new File(customFilesFolder + Launcher.separatorChar + "options.txt");
        optionsOFProfileTextfile = new File(customFilesFolder + Launcher.separatorChar + "optionsof.txt");
    }

    /**
     * Sauvegarde les fichiers customs depuis les dossiers généraux aux dossiers des profils
     * @param saver Le Saver concerné
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void saveCustomFiles(Saver saver)  {
        initCustomFilesFolder(saver);

        try {
            if (saver != null) {
                copyFiles(savesFolder, savesProfileFolder);
                copyFiles(resourcepacksFolder, resourcepacksProfileFolder);
                copyFiles(shaderpacksFolder, shaderpacksProfileFolder);
                copyFiles(musicsheetsFolder, musicsheetsProfileFolder);
                copyFiles(schematicsFolder, schematicsProfileFolder);
                copyFiles(configFolder, configProfileFolder);
            }
        } catch (IOException ignored) {}

        try {
            copyFile(optionsTextfile, optionsProfileTextfile);
            copyFile(optionsOFTextfile, optionsOFProfileTextfile);
        } catch (IOException e) {
            Launcher.println("Failed copy options files");
        }

    }

    /**
     * Charge les fichiers customs des dossiers des profils aux dossiers généraux
     * @param saver Le Saver concerné
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void loadCustomFiles(Saver saver) {
        initCustomFilesFolder(saver);

        deleteDirectory(savesFolder);
        savesFolder.mkdir();
        deleteDirectory(resourcepacksFolder);
        resourcepacksFolder.mkdir();
        deleteDirectory(shaderpacksFolder);
        shaderpacksFolder.mkdir();
        deleteDirectory(musicsheetsFolder);
        musicsheetsFolder.mkdir();
        deleteDirectory(schematicsFolder);
        schematicsFolder.mkdir();
        deleteDirectory(configFolder);
        configFolder.mkdir();

        try {
            copyFiles(savesProfileFolder, savesFolder);
            copyFiles(resourcepacksProfileFolder, resourcepacksFolder);
            copyFiles(shaderpacksProfileFolder, shaderpacksFolder);
            copyFiles(musicsheetsProfileFolder, musicsheetsFolder);
            copyFiles(schematicsProfileFolder, schematicsFolder);
            copyFiles(configProfileFolder, configFolder);
        } catch (IOException ignored) {}

        try {
            copyFile(optionsProfileTextfile, optionsTextfile);
            copyFile(optionsOFProfileTextfile, optionsOFTextfile);
        } catch (IOException e) {
            Launcher.println("Failed copy options files");
        }

    }

    /**
     * La classe qui regroupe les key des savers
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static class KEY {
        /**
         * Le pseudo du compte enregistré
         * <br> A ne pas confondre avec {@link KEY#SETTINGS_PROFILENAME}
         */
        public static final String INFOS_NAME = "infos|name";
        /**
         * L'email du compte enregistré
         */
        public static final String INFOS_EMAIL = "infos|email";
        /**
         * L'UUID du compte enregistré
         */
        public static final String INFOS_UUID = "infos|UUID";
        /**
         * L'access token du compte enregistré
         */
        public static final String INFOS_ACCESSTOKEN = "infos|accessToken";
        /**
         * Le refresh token du compte enregistré
         */
        public static final String INFOS_REFRESHTOKEN = "infos|refreshToken";

        /**
         * Optifine
         */
        public static final String MOD_OPTIFINE = "mod|Optifine";
        /**
         * Mod client 'First Person Model'
         */
        public static final String MOD_FPSMODEL = "mod|FirstPersonModel";
        /**
         * Mod client 'Better Third Person'
         */
        public static final String MOD_BETTERTPS = "mod|BetterThirdPerson";
        /**
         * Mod client 'Falling Leaves'
         */
        public static final String MOD_FALLINGLEAVES = "mod|FallingLeaves";
        /**
         * Mod client 'Apple Skin'
         */
        public static final String MOD_APPLESKIN = "mod|AppleSkin";
        /**
         * Mod client 'Sound Physics Remastered'
         */
        public static final String MOD_SOUNDPHYSICS = "mod|SoundPhysicsRemastered";

        /**
         * Le nom du profil
         * <br> A ne pas confondre avec {@link KEY#INFOS_NAME}
         */
        public static final String SETTINGS_PROFILENAME = "settings|ProfileName";
        /**
         * La seconde couche sur l'icône du profil
         */
        public static final String SETTINGS_HELMICON = "settings|HelmIcon";
        /**
         * La RAM allouée
         */
        public static final String SETTINGS_RAM = "settings|AllowedRam";

        /**
         * Si le fichier est créé
         */
        public static final String FILECREATED = "fileCreated";
    }
}
