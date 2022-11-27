package fr.timeto.astrauworld.launcher;

import fr.theshark34.openlauncherlib.util.Saver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Objects;

import static fr.timeto.astrauworld.launcher.LauncherPanel.*;

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
