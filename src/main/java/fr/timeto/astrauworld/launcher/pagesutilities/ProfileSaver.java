package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.flowarg.flowupdater.download.json.CurseFileInfo;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.timeto.astrauworld.launcher.customelements.ShadersSwitchButton;
import fr.timeto.astrauworld.launcher.main.Launcher;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
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
    private static String selectedProfile = "";
    /**
     * Le Saver sélectionné
     */
    private static Saver selectedSaver;

    public static void setSelectedSaver(Saver saver) {
        selectedSaver = saver;
        if (saver == firstProfileSaver) {
            selectedProfile = "1";
        } else if (saver == secondProfileSaver) {
            selectedProfile = "2";
        } else if (saver == thirdProfileSaver) {
            selectedProfile = "3";
        } else {
            throw new ExceptionInInitializerError("Saver non reconnu");
        }
    }

    public static Saver getSelectedSaver() {return selectedSaver;}

    public static void setSelectedProfile(String profile) {
        selectedProfile = profile;
        if (Objects.equals(profile, "1")) {
            selectedSaver = firstProfileSaver;
        } else if (Objects.equals(profile, "2")) {
            selectedSaver = secondProfileSaver;
        } else if (Objects.equals(profile, "3")) {
            selectedSaver = thirdProfileSaver;
        }
    }

    public static String getSelectedProfile() {
        if (selectedProfile == null) {
            throw new NullPointerException("selectedProfile est nul");
        }
        return selectedProfile;
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

    public static Saver getSaver(String profile) {
        Saver saver = null;
        if (Objects.equals(profile, "1")) {
            saver = firstProfileSaver;
        } else if (Objects.equals(profile, "2")) {
            saver = secondProfileSaver;
        } else if (Objects.equals(profile, "3")) {
            saver = thirdProfileSaver;
        }
        return saver;
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

    public static String getActualMainProfile() {
        if (Objects.equals(thirdProfileSaver.get(KEY.SETTINGS_MAINPROFILE.get()), "true")) {
            return "3";
        } else if (Objects.equals(secondProfileSaver.get(KEY.SETTINGS_MAINPROFILE.get()), "true")) {
            return "2";
        } else {
            return "1";
        }
    }

    /**
     * Initialise le fichier de données du profil sélectionné s'il n'a pas déjà été créé
     * @param saver Le profil sélectionné
     * @see ProfileSaver#initializeDataFiles()
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void initializeDataFiles(Saver saver) {
        KEY[] keysList = new KEY[]{
                KEY.INFOS_NAME,
                KEY.INFOS_EMAIL,
                KEY.INFOS_UUID,
                KEY.INFOS_ACCESSTOKEN,
                KEY.INFOS_REFRESHTOKEN,
                KEY.MOD_OPTIFINE,
                KEY.MOD_FPSMODEL,
                KEY.MOD_BETTERTPS,
                KEY.MOD_FALLINGLEAVES,
                KEY.MOD_APPLESKIN,
                KEY.MOD_SOUNDPHYSICS,
                KEY.MOD_WAVEYCAPES,
                KEY.MOD_3DSKINLAYERS,
                KEY.SETTINGS_PROFILENAME,
                KEY.SETTINGS_HELMICON,
                KEY.SETTINGS_RAM,
                KEY.SETTINGS_MAINPROFILE,
        };

        int i = 0;
        boolean modified = false;
        while (i != keysList.length) {
            if (saver.get(keysList[i].get()) == null) {
                saver.set(keysList[i].get(), keysList[i].getDefaultValue());
                modified = true;
            }
            i++;
        }

        if (modified) Launcher.println("Fichier de données pour le saver du profil " + getSelectedProfile(saver) + " initialisé");
    }

    public static void resetDataFiles(Saver saver) {
        KEY[] keysList = new KEY[]{
                KEY.INFOS_NAME,
                KEY.INFOS_EMAIL,
                KEY.INFOS_UUID,
                KEY.INFOS_ACCESSTOKEN,
                KEY.INFOS_REFRESHTOKEN,
                KEY.MOD_OPTIFINE,
                KEY.MOD_FPSMODEL,
                KEY.MOD_BETTERTPS,
                KEY.MOD_FALLINGLEAVES,
                KEY.MOD_APPLESKIN,
                KEY.MOD_SOUNDPHYSICS,
                KEY.MOD_WAVEYCAPES,
                KEY.MOD_3DSKINLAYERS,
                KEY.SETTINGS_PROFILENAME,
                KEY.SETTINGS_HELMICON,
                KEY.SETTINGS_RAM,
                KEY.SETTINGS_MAINPROFILE,
        };

        int i = 0;
        while (i != keysList.length) {
            saver.set(keysList[i].get(), keysList[i].getDefaultValue());
            i++;
        }

        Launcher.println("Fichier de données pour le saver du profil " + getSelectedProfile(saver) + " réinitialisé");
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
        if (saver.get(KEY.SETTINGS_PROFILENAME.get()).toLowerCase().replaceAll(" ", "").equals("vide") || saver.get(KEY.SETTINGS_PROFILENAME.get()).toLowerCase().replaceAll(" ", "").equals("")) {
            url = "https://user-images.githubusercontent.com/97166376/214735612-abc155df-6535-4852-aad5-cd97901f5e86.png";
        } else if (saver.get(KEY.SETTINGS_PROFILENAME.get()).toLowerCase().replaceAll(" ", "").equals("frisk")) {
            url = "https://user-images.githubusercontent.com/97166376/209479948-9077d6d4-1254-4423-914b-d8b7ecf895d0.png";
            EasterEggs.setEatereggAsFound(EasterEggs.friskName);
        } else if (saver.get(KEY.SETTINGS_PROFILENAME.get()).toLowerCase().replaceAll(" ", "").equals("chara")) {
            url = "https://user-images.githubusercontent.com/97166376/209479945-0b181aaa-f3bd-436c-8274-83f68302c93e.png";
            EasterEggs.setEatereggAsFound(EasterEggs.charaName);
        } else if ((saver.get(KEY.SETTINGS_PROFILENAME.get()).toLowerCase().replaceAll(" ", "").equals("asriel")) && (saver.get(KEY.SETTINGS_HELMICON.get()).contains("true"))) {
            url = "https://user-images.githubusercontent.com/97166376/214740385-07f6ded5-fdca-44b9-87ae-bd0446557c7f.png";
            EasterEggs.setEatereggAsFound(EasterEggs.asrielName);
        } else if ((saver.get(KEY.SETTINGS_PROFILENAME.get()).toLowerCase().replaceAll(" ", "").equals("asriel")) && (saver.get(KEY.SETTINGS_HELMICON.get()).contains("false"))) {
            url = "https://user-images.githubusercontent.com/97166376/214734249-9d5e1055-c68f-4ee3-8d93-1a19b37c9410.png";
            EasterEggs.setEatereggAsFound(EasterEggs.trueAsrielName);
        } else if ((saver.get(KEY.SETTINGS_PROFILENAME.get()).toLowerCase().replaceAll(" ", "").equals("flowey")) && (saver.get(KEY.SETTINGS_HELMICON.get()).contains("true"))) {
            url = "https://user-images.githubusercontent.com/97166376/209479944-e76fbf8f-6aba-462f-afc5-08829af3f9c8.png";
            EasterEggs.setEatereggAsFound(EasterEggs.floweyName);
        } else if ((saver.get(KEY.SETTINGS_PROFILENAME.get()).toLowerCase().replaceAll(" ", "").equals("flowey")) && (saver.get(KEY.SETTINGS_HELMICON.get()).contains("false"))) {
            url = "https://user-images.githubusercontent.com/97166376/209480184-79318022-8ba0-46c9-9773-504a63c2ee47.png";
            EasterEggs.setEatereggAsFound(EasterEggs.cursedFloweyName);
        } else if (saver.get(KEY.SETTINGS_HELMICON.get()).contains("true")) {
            url = "https://minotar.net/helm/" + saver.get(KEY.INFOS_UUID.get()) + "/35.png";
        } else {
            url = "https://minotar.net/avatar/" + saver.get(KEY.INFOS_UUID.get()) + "/35.png";
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
     * Ajoute les addons clients à la liste spécifiée d'après le Saver sélectionné
     * @param selectedSaver Le Saver où les données seront cherchées
     * @param modList La liste où les addons seront ajoutés
     */
    public static void initClientMods(Saver selectedSaver, List modList) {

        if (Objects.equals(selectedSaver.get(KEY.MOD_FPSMODEL.get()), "true")) {
            modList.add(new CurseFileInfo(333287, 4327736)); // First Peron Model 2.2.2 - Forge
        }

        if (Objects.equals(selectedSaver.get(KEY.MOD_BETTERTPS.get()), "true")) {
            modList.add(new CurseFileInfo(435044, 4177087)); // Better Third Person 1.9.0
        }

        if (Objects.equals(selectedSaver.get(KEY.MOD_FALLINGLEAVES.get()), "true")) {
            modList.add(new CurseFileInfo(463155, 3705945)); // Falling Leaves 1.3.2
        }

        if (Objects.equals(selectedSaver.get(KEY.MOD_APPLESKIN.get()), "true")) {
            modList.add(new CurseFileInfo(248787, 3927564)); // Apple Skin 2.4.1
        }

        if (Objects.equals(selectedSaver.get(KEY.MOD_SOUNDPHYSICS.get()), "true")) {
            modList.add(new CurseFileInfo(535489, 3775919)); // Sound Physics Remastered 1.0.6
        }

    }

    /*  Liste des fichiers custom à sauvegarder dans GameFiles
     *
     * ./saves
     * ./resourcepacks
     * ./shaderpacks
     * ./music_sheets
     * ./schematics
     * ./config
     * options.txt
     * optionsof.txt
     * optionsshaders.txt
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
    public static final File optionsTextfile = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "options.txt");
    /**
     * Le fichier des options Optifine général
     */
    public static final File optionsOFTextfile = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "optionsof.txt");

    /**
     * Le fichier des options des shaders d'Optifine général
     */
    public static final File optionsShadersTextfile = new File(Launcher.gameFilesFolder + Launcher.separatorChar + "optionsshaders.txt");

    /**
     * Le dossier des saves des profils, initialisé plus tard
     */
    private static File savesProfileFolder = null;
    /**
     * Le dossier des resource packs des profils, initialisé plus tard
     */
    public static File resourcepacksProfileFolder = null;
    /**
     * Le dossier des shaders des profils, initialisé plus tard
     */
    public static File shaderpacksProfileFolder = null;
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
    public static File optionsProfileTextfile = null;
    /**
     * Le fichier des options d'Optifine des profils, initialisé plus tard
     */
    public static File optionsOFProfileTextfile = null;

    /**
     * Le fichier des options des shaders d'Optifine des profils, initialisé plus tard
     */
    public static File optionsShadersProfileTextfile = null;

    public static enum Shader {
        CHOCAPICV6_LITE("Chocapic13_V6_Lite.zip"),
        CHOCAPICV6_LOW("Chocapic13_V6_Low.zip"),
        CHOCAPICV6_MEDIUM("Chocapic13_V6_Medium.zip"),
        CHOCAPICV6_ULTRA("Chocapic13_V6_Ultra.zip"),
        CHOCAPICV6_EXTREME("Chocapic13_V6_Extreme.zip"),

        CHOCAPICV7_1_TOASTER("Chocapic13_V7.1.1_Toaster_Edition.zip"),
        CHOCAPICV7_1_LITE("Chocapic13_V7.1.1_Lite.zip"),
        CHOCAPICV7_1_LOW("Chocapic13_V7.1_Low.zip"),
        CHOCAPICV7_1_MEDIUM("Chocapic13_V7.1_Medium.zip"),
        CHOCAPICV7_1_ULTRA("Chocapic13_V7.1_Ultra.zip"),
        CHOCAPICV7_1_EXTREME("Chocapic13_V7.1_Extreme.zip"),

        CHOCAPICV9_LOW("Chocapic13_V9_Low.zip"),
        CHOCAPICV9_MEDIUM("Chocapic13_V9_Medium.zip"),
        CHOCAPICV9_HIGH("Chocapic13_V9_High.zip"),
        CHOCAPICV9_EXTREME("Chocapic13_V9_Extreme.zip"),
        CHOCAPICV9_1_EXTREMEBETA5("Chocapic13_V9.1_Extreme_beta_5.zip"),

        SEUS_RENEWED("SEUS-Renewed-v1.0.1.zip");

        private final String name;

        Shader(String name) {this.name = name;}

        public String get() {
            return name;
        }
    }

    public static final ArrayList<ShadersSwitchButton> shadersButtonsList = new ArrayList<ShadersSwitchButton>();

    /**
     * Initialise les dossiers customs
     * @param saver le Saver concerné
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void initCustomFilesFolder(Saver saver) {
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
        optionsShadersProfileTextfile = new File(customFilesFolder + Launcher.separatorChar + "optionsshaders.txt");
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
                if (savesFolder.exists()) {savesProfileFolder.mkdir(); copyFiles(savesFolder, savesProfileFolder, false);}
                if (resourcepacksFolder.exists()) {resourcepacksProfileFolder.mkdir(); copyFiles(resourcepacksFolder, resourcepacksProfileFolder, false);}
                if (shaderpacksFolder.exists()) {shaderpacksProfileFolder.mkdir(); copyFiles(shaderpacksFolder, shaderpacksProfileFolder, false);}
                if (musicsheetsFolder.exists()) {musicsheetsProfileFolder.mkdir(); copyFiles(musicsheetsFolder, musicsheetsProfileFolder, false);}
                if (schematicsFolder.exists()) {schematicsProfileFolder.mkdir(); copyFiles(schematicsFolder, schematicsProfileFolder, false);}
                if (configFolder.exists()) {configProfileFolder.mkdir(); copyFiles(configFolder, configProfileFolder, false);}
            }
        } catch (IOException ignored) {}

        try {
            copyFile(optionsTextfile, optionsProfileTextfile, true);
        } catch (IOException e) {
            Launcher.println("Failed copy options file");
        }

        try {
            copyFile(optionsOFTextfile, optionsOFProfileTextfile, true);
        } catch (IOException e) {
            Launcher.println("Failed copy optifine options file");
        }

        try {
            copyFile(optionsShadersTextfile, optionsShadersProfileTextfile, true);
        } catch (IOException e) {
            Launcher.println("Failed copy options shader file");
        }

    }

    /**
     * Charge les fichiers customs des dossiers des profils aux dossiers généraux
     * @param saver Le Saver concerné
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void loadCustomFiles(Saver saver) {
        initCustomFilesFolder(saver);

        try {
            deleteDirectory(savesFolder, false);
            savesFolder.mkdir();
        } catch (NullPointerException ignored) {}
        try {
            deleteDirectory(musicsheetsFolder, false);
            musicsheetsFolder.mkdir();
        } catch (NullPointerException ignored) {}
        try {
            deleteDirectory(schematicsFolder, false);
            schematicsFolder.mkdir();
        } catch (NullPointerException ignored) {}
        try {
            deleteDirectory(configFolder, false);
            configFolder.mkdir();
        } catch (NullPointerException ignored) {}
        try {
            deleteDirectory(resourcepacksFolder, false);
            resourcepacksFolder.mkdir();
        } catch (NullPointerException ignored) {}
        try {
            deleteDirectory(shaderpacksFolder, false);
            shaderpacksFolder.mkdir();
        } catch (NullPointerException ignored) {}

        try {
            if (saver != null) {
                if (savesProfileFolder.exists()) {savesFolder.mkdir(); copyFiles(savesProfileFolder, savesFolder, false);}
                if (resourcepacksProfileFolder.exists()) {resourcepacksFolder.mkdir(); copyFiles(resourcepacksProfileFolder, resourcepacksFolder, false);}
                if (shaderpacksProfileFolder.exists()) {shaderpacksFolder.mkdir(); copyFiles(shaderpacksProfileFolder, shaderpacksFolder, false);}
                if (musicsheetsProfileFolder.exists()) {musicsheetsFolder.mkdir(); copyFiles(musicsheetsProfileFolder, musicsheetsFolder, false);}
                if (schematicsProfileFolder.exists()) {schematicsFolder.mkdir(); copyFiles(schematicsProfileFolder, schematicsFolder, false);}
                if (configProfileFolder.exists()) {configFolder.mkdir(); copyFiles(configProfileFolder, configFolder, false);}
            }
        } catch (IOException ignored) {}

        try {
            copyFile(optionsProfileTextfile, optionsTextfile, true);
        } catch (IOException e) {
            Launcher.println("Failed copy options file");
        }

        try {
            copyFile(optionsOFProfileTextfile, optionsOFTextfile, true);
        } catch (IOException e) {
            Launcher.println("Failed copy optifine options file");
        }

        try {
            copyFile(optionsShadersProfileTextfile, optionsShadersTextfile, true);
        } catch (IOException e) {
            Launcher.println("Failed copy options shader file");
        }

    }

    /**
     * La classe qui regroupe les key des savers
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static enum KEY {
        /**
         * Le pseudo du compte enregistré
         * <br> A ne pas confondre avec {@link KEY#SETTINGS_PROFILENAME}
         */
        INFOS_NAME("infos|name", ""),
        /**
         * L'email du compte enregistré
         */
        INFOS_EMAIL("infos|email", "none"),
        /**
         * L'UUID du compte enregistré
         */
        INFOS_UUID("infos|UUID", "none"),
        /**
         * L'access token du compte enregistré
         */
        INFOS_ACCESSTOKEN("infos|accessToken", "none"),
        /**
         * Le refresh token du compte enregistré
         */
        INFOS_REFRESHTOKEN("infos|refreshToken", "none"),

        /**
         * Optifine
         */
        MOD_OPTIFINE("mod|Optifine", "false"),
        /**
         * Mod client 'First Person Model'
         */
        MOD_FPSMODEL("mod|FirstPersonModel", "false"),
        /**
         * Mod client 'Better Third Person'
         */
        MOD_BETTERTPS("mod|BetterThirdPerson", "false"),
        /**
         * Mod client 'Falling Leaves'
         */
        MOD_FALLINGLEAVES("mod|FallingLeaves", "false"),
        /**
         * Mod client 'Apple Skin'
         */
        MOD_APPLESKIN("mod|AppleSkin", "false"),
        /**
         * Mod client 'Sound Physics Remastered'
         */
        MOD_SOUNDPHYSICS("mod|SoundPhysicsRemastered", "false"),
        MOD_WAVEYCAPES("mod|WaveyCapes", "false"),
        MOD_3DSKINLAYERS("mod|3DSkinLayers", "false"),

        /**
         * Le nom du profil
         * <br> A ne pas confondre avec {@link KEY#INFOS_NAME}
         */
        SETTINGS_PROFILENAME("settings|ProfileName", "Vide"),
        /**
         * La seconde couche sur l'icône du profil
         */
        SETTINGS_HELMICON("settings|HelmIcon", "true"),
        /**
         * La RAM allouée
         */
        SETTINGS_RAM("settings|AllowedRam", "3.0"),
        SETTINGS_MAINPROFILE("settings|MainProfile", "false");

        private final String key;
        private final String defaultValue;

        KEY(String key, String defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        public String get() {
            return key;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }
}
