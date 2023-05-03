package fr.timeto.astrauworld.launcher.main;

import br.com.azalim.mcserverping.MCPingOptions;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;
import net.harawata.appdirs.AppDirsFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static fr.timeto.astrauworld.launcher.main.LauncherFrame.getInstance;
import static fr.timeto.astrauworld.launcher.main.LauncherFrame.launcherProperties;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;

@SuppressWarnings("unused")
public class Launcher {

    public enum CUSTOM_COLORS {
        MAIN_COLOR(Color.RED, KEY.GLOBALSETTINGS_MAINCOLOR),
        TEXT_COLOR(Color.WHITE, KEY.GLOBALSETTINGS_TEXTCOLOR),
        SECONDTEXT_COLOR(new Color(153, 153, 153), KEY.GLOBALSETTINGS_SECONDTEXTCOLOR),
        DARKER_BACKGROUND_COLOR(Color.BLACK, KEY.GLOBALSETTINGS_DARKERBACKGROUNDCOLOR),
        MID_BACKGROUND_COLOR(new Color(9, 9, 9), KEY.GLOBALSETTINGS_MIDBACKGROUNDCOLOR),
        BASE_BACKGROUND_COLOR(new Color(18, 18, 18), KEY.GLOBALSETTINGS_BASEBACKGROUNDCOLOR),
        ELEMENTS_COLOR(new Color(30, 30, 30), KEY.GLOBALSETTINGS_ELEMENTSCOLOR);

        private Color color;
        private final KEY key;

        CUSTOM_COLORS(Color color, KEY key) {
            this.color = color;
            this.key = key;
        }

        public Color get() {
            return color;
        }

        public void set(Color color) {
            this.color = color;
            try {
                LauncherPanel container = (LauncherPanel) LauncherFrame.getInstance().getContentPane();
                container.recolor();
            } catch (ClassCastException | NullPointerException ignored) {}
            setFileColor(color);
        }

        public void setWithoutRecolor(Color color) {
            this.color = color;
            setFileColor(color);
        }

        public Color getFileColor() {
            String[] split = globalSettingsSaver.get(key.get()).split("-");
            int r = Integer.parseInt(split[0]);
            int g = Integer.parseInt(split[1]);
            int b = Integer.parseInt(split[2]);

            return new Color(r, g, b);
        }

        private void setFileColor(Color color) {
            int r = color.getRed();
            int g = color.getGreen();
            int b = color.getBlue();
            globalSettingsSaver.set(key.get(), r + "-" + g + "-" + b);
        }

        /**
         * Doit être en format 'rouge-vert-bleu'
         */
        private void setFileColor(String color) {
            globalSettingsSaver.set(key.get(), color);
        }

        public void reset() {
            setFileColor(key.getDefaultValue());
            set(getFileColor());
        }

        public Color getDefault() {
            String[] split = key.getDefaultValue().split("-");
            int r = Integer.parseInt(split[0]);
            int g = Integer.parseInt(split[1]);
            int b = Integer.parseInt(split[2]);

            return new Color(r, g, b);
        }

        public KEY getKey() {
            return key;
        }

    }

    public static final String separatorChar = File.separator;

    public static final String afterMcExitArg = "--afterMcExit";
    public static final String devEnvArg = "--dev";

    // String des les path dont on a besoin
    public static final String filesFolder =  AppDirsFactory.getInstance().getUserDataDir("Astrauworld Launcher", null, null, true);

    // Version de Minecraft et de Forge utilisée
    public static final String mcVersion = launcherProperties.getProperty("mcVersion");
    public static final String forgeVersion = launcherProperties.getProperty("forgeVersion");
    public static final String optifineVersion = launcherProperties.getProperty("optifineVersion"); // FIXME Bug certaines textures sont unies
    public static MCPingOptions serverOptions = MCPingOptions.builder()
            .hostname(launcherProperties.getProperty("serverHostname")) // 207.180.196.61
            .port(Integer.parseInt(launcherProperties.getProperty("serverPort"))) //33542
            .build();

    // Version du launcher
    public static final String version = launcherProperties.getProperty("launcherVersion");

    // File des dont on a besoin
    public static final File AW_DIR = new File(filesFolder);
    public static final File AW_CRASH_FOLDER = new File(AW_DIR, "crashes");
    public static final File AW_GAMEFILES_FOLDER = new File(AW_DIR, "GameFiles");
    public static final File AW_DATA_FOLDER = new File(AW_DIR, "data");
    public static final File AW_DISCORD_DATA_FOLDER = new File(AW_DATA_FOLDER, "discord");
    public static final File AW_SETTINGS_DATA = new File(AW_DATA_FOLDER, "settings.properties");

    public static final File AW_DISCORD_AVATAR_ICON = new File(AW_DISCORD_DATA_FOLDER, "discordUserAvatar.png");
    public static final File AW_DISCORD_BIGIMAGE_ICON = new File(AW_DISCORD_DATA_FOLDER, "discordBigImage.png");
    public static final File AW_DISCORD_SMALLIMAGE_ICON = new File(AW_DISCORD_DATA_FOLDER, "discordSmallImage.png");

    public static final File AW_FIRSTPROFILE_DATA = new File(AW_DATA_FOLDER, "firstProfile.properties");
    public static final File AW_FIRSTPROFILE_ICON = new File(AW_DATA_FOLDER, "firstProfile.png");
    public static final File AW_FIRSTPROFILE_CUSTOMFILES_FOLDER = new File(AW_DATA_FOLDER, "firstProfileCustomFiles");

    public static final File AW_SECONDPROFILE_DATA = new File(AW_DATA_FOLDER, "secondProfile.properties");
    public static final File AW_SECONDPROFILE_ICON = new File(AW_DATA_FOLDER, "secondProfile.png");
    public static final File AW_SECONDPROFILE_CUSTOMFILES_FOLDER = new File(AW_DATA_FOLDER, "secondProfileCustomFiles");

    public static final File AW_THIRDPROFILE_DATA= new File(AW_DATA_FOLDER, "thirdProfile.properties");
    public static final File AW_THIRDPROFILE_ICON = new File(AW_DATA_FOLDER, "thirdProfile.png");
    public static final File AW_THIRDPROFILE_CUSTOMFILES_FOLDER = new File(AW_DATA_FOLDER, "thirdProfileCustomFiles");

    // Path dont on a besoin
    public static final Path awFilesFolder = AW_DIR.toPath();
    public static final Path awCrashFolder = AW_CRASH_FOLDER.toPath();
    public static final Path awGameFilesFolder = AW_GAMEFILES_FOLDER.toPath();
    public static final Path awDataFolder = AW_DATA_FOLDER.toPath();
    public static final Path awSettingsData = AW_SETTINGS_DATA.toPath();

    public static final Path awFirstProfileData = AW_FIRSTPROFILE_DATA.toPath();
    public static final Path awFirstProfileIcon = AW_FIRSTPROFILE_ICON.toPath();
    public static final Path awFirstProfileCustomFilesFolder = AW_FIRSTPROFILE_CUSTOMFILES_FOLDER.toPath();

    public static final Path awSecondProfileData = AW_SECONDPROFILE_DATA.toPath();
    public static final Path awSecondProfileIcon = AW_SECONDPROFILE_ICON.toPath();
    public static final Path awSecondProfileCustomFilesFolder = AW_SECONDPROFILE_CUSTOMFILES_FOLDER.toPath();

    public static final Path awThirdProfileData = AW_THIRDPROFILE_DATA.toPath();
    public static final Path awThirdProfileIcon = AW_THIRDPROFILE_ICON.toPath();
    public static final Path awThirdProfileCustomFilesFolder = AW_THIRDPROFILE_CUSTOMFILES_FOLDER.toPath();

    private static AuthInfos authInfos;

    static boolean maximumSet = false;

    private static final CrashReporter crashReporter = new CrashReporter("Astrauworld Launcher", awCrashFolder);
    public static final AstrauworldMC ASTRAUWORLD_MC = new AstrauworldMC();

    public static Process process = null;

    public static void println(String str) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        
        System.out.println("[" + dtf.format(now) + "] [Astrauworld Launcher] " + str);
    }

    public static boolean colorsEquals(Color color1, Color color2) {
        if (color1 == null || color2 == null) {
            return false;
        }

        return color1.getRed() == color2.getRed()
                && color1.getGreen() == color2.getGreen()
                && color1.getBlue() == color2.getBlue();
    }

    public static BufferedImage takeAppScreenshot() {

        Robot r;
        try {
            r = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        // Used to get ScreenSize and capture image
        Rectangle capture = new Rectangle(
                getInstance().getX(),
                getInstance().getY(),
                getInstance().getWidth(),
                getInstance().getHeight());

        return r.createScreenCapture(capture);
    }

    public static String convertStringArrayToString(String[] strArr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String str : strArr)
            sb.append(str).append(delimiter);
        return sb.substring(0, sb.length() - 1);
    }

    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        return makeRoundedCorner(image, cornerRadius, Color.WHITE);
    }

    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius, Color color) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)

        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();

        return output;
    }

    public static void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {

        // append = false
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            final int DEFAULT_BUFFER_SIZE = 8192;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

    }

    public static BufferedImage getImageFromFile(File file) {
        // This time, you can use an InputStream to load
        try {
            // Grab the InputStream for the image.
            InputStream in = new FileInputStream(file);

            // Then read it.
            return ImageIO.read(in);
        } catch (IOException e) {
            Launcher.println("The image was not loaded.");
            return null;
        }

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

        if (result == null) {
            throw new MicrosoftAuthenticationException("Aucun résultat");
        }

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

    public static CrashReporter getCrashReporter() {
        return crashReporter;
    }

}
