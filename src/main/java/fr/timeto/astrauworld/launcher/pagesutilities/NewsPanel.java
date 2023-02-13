package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredButton;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.timeto.astrauworld.launcher.customelements.CustomScrollBarUI;
import fr.timeto.timutilslib.CustomFonts;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;

public class NewsPanel extends JScrollPane {
    JPanel panel = new JPanel();

    public static News[] newsList = {
            new News("test", "Ceci est un article de test", "TimEtOff", "24/01/2023")
    };

    static Box[] boxes;
    static JPanel container;

    public NewsPanel() {
        CustomFonts.initFonts();

        setOpaque(false);

        container = new JPanel();
        LayoutManager layout = new BoxLayout(container, BoxLayout.Y_AXIS);
        container.setBorder(new EmptyBorder(25, 0, 25, 0));
        container.setPreferredSize(new Dimension(822, 260 * (newsList.length / 2))); // FIXME dernier article pas voyant si le nombre d'articles est impair
        container.setBackground(Swinger.getTransparentWhite(10));
        container.setOpaque(false);

        setViewportView(container);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getVerticalScrollBar().setUI(new CustomScrollBarUI());
        getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        getVerticalScrollBar().setUnitIncrement(14);

        getViewport().setOpaque(false);
        setBorder(null);

        int i = 0;
        boxes = new Box[newsList.length];

        for (int ii = i; ii < newsList.length; ii++) {
            boxes[ii] = Box.createHorizontalBox();
            boxes[ii].createGlue();
            container.add(boxes[ii]);
            if (ii % 2 == 0 && ii != newsList.length - 1) {
                container.add(Box.createHorizontalStrut(52));
            } else {
                container.add(Box.createRigidArea(new Dimension(1000, 30)));
            }
            JPanel panel1 = new NewsButton(ii);
            boxes[ii].add(((NewsButton) panel1).getButton());
        }

    }
}

class NewsButton extends JPanel implements SwingerEventListener {
    final News news;
    SColoredButton button = new SColoredButton(new Color(70, 70, 70));
    JLabel thumbnail = new JLabel();
    JLabel title = new JLabel();
    JLabel authorDate = new JLabel();

    public NewsButton(int newsNumber) {
        setLayout(null);

        news = NewsPanel.newsList[newsNumber];

        setPreferredSize(new Dimension(330, 212));
        setBackground(new Color(70, 70, 70));
    //    setBorder(new RoundedBorder(50));
        setOpaque(false);

        thumbnail.setIcon(new ImageIcon(news.getThumbnail()));
        thumbnail.setBounds(0, 0, 330, 140);
        add(thumbnail);

        authorDate.setBounds(10, 195, 315, 16);
        authorDate.setText(news.getAuthor() + " - " + news.getStringDate());
        authorDate.setFont(CustomFonts.kollektifBoldFont.deriveFont(14f));
        authorDate.setForeground(Color.WHITE);
        authorDate.setOpaque(false);
        authorDate.setVerticalAlignment(SwingConstants.BOTTOM);
        authorDate.setHorizontalAlignment(SwingConstants.RIGHT);
        add(authorDate);

        title.setBounds(10, 148, 310, 20);
        title.setText(news.getTitle());
        title.setFont(CustomFonts.kollektifBoldFont.deriveFont(18f));
        title.setForeground(Color.WHITE);
        title.setOpaque(false);
        title.setVerticalAlignment(SwingConstants.TOP);
        add(title);

        button.setBounds(0, 0, 330, 212);
        button.setBorder(this.getBorder());
        button.addEventListener(this);
        add(button);
    }

    public JPanel getButton() {
        return this;
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        Object src = swingerEvent.getSource();

        if (src == button) {
            newsOpenScrollPanel.setNewsPage(news);
            newsOpenScrollPanel.setVisible(true);
            newsScrollPanel.setVisible(false);
        }

    }
}

class News {
    private String newsId;
    private String title;
    private String author;
    private Date date;
    private BufferedImage thumbnail;
    private BufferedImage image;
    private String text;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    public News(String newsId, String title, String author, String date) {
        this.newsId = newsId;
        this.title = title;
        this.author = author;
        try {
            this.date = dateFormat.parse(date.replaceAll("-", "/"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        BufferedImage tempThumbnail = getResourceIgnorePath("/assets/launcher/newsPage/" + newsId + "/thumbnail.png");
        if (tempThumbnail.getWidth() != 330 && tempThumbnail.getHeight() != 140) {
            throw new RuntimeException("Thumbnail dimensions not supported");
        }
        this.thumbnail = tempThumbnail;

        BufferedImage tempImage = getResourceIgnorePath("/assets/launcher/newsPage/" + newsId + "/image.png");
        if (tempImage.getWidth() != 822 && tempImage.getHeight() != 213) {
            throw new RuntimeException("Image dimensions not supported");
        }
        this.image = tempImage;

        String fileSep = System.getProperty("file.separator");
        InputStream is = getFileFromResourceAsStream("assets/launcher/newsPage/" + newsId + "/text.md");
    /*    Parser parser = Parser.builder().build();
        Node document = parser.parse(getStringFromInputStream(is));
        HtmlRenderer renderer = HtmlRenderer.builder().build(); */
        this.text = getStringFromInputStream(is); // renderer.render(document)
    }

    // get a file from the resources folder
    // works everywhere, IDEA, unit test and JAR file.
    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    private static String getStringFromInputStream(InputStream is) {
        String str = "";

        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                str += line;
                str += System.getProperty("line.separator");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;

    }

    public String getNewsId() {
        return newsId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public String getStringDate() {
        return dateFormat.format(date);
    }

    public BufferedImage getThumbnail() {
        return thumbnail;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getText() {
        return text;
    }
}