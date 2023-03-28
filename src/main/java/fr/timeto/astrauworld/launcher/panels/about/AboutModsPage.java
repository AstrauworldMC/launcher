package fr.timeto.astrauworld.launcher.panels.about;

import fr.timeto.astrauworld.launcher.customelements.AWTextArea;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.astrauworld.launcher.pagesutilities.Server;
import fr.timeto.astrauworld.launcher.panels.PageCreator;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class AboutModsPage extends PageCreator implements MouseListener {
    private int index = 0;

    public final AWTextArea textArea1 = new AWTextArea(14f, false); // 27/textarea
    public final AWTextArea textArea = new AWTextArea(14f, false) {
        @Override
        public void setText(String str) {
            String[] split = str.split(System.getProperty("line.separator"));

            if (index >= split.length) {
                index = 0;
            }

            if (split.length >= index + 27) {
                ArrayList<String> arrayList1 = new ArrayList<>();
                int ii = index + 27;
                while (ii != split.length) {
                    if (ii == index + 53) {
                        arrayList1.add("[...]");
                    } else {
                        arrayList1.add(split[ii]);
                    }
                    ii++;
                }
                String[] array1 = arrayList1.toArray(new String[0]);
                textArea1.setText(Launcher.parseUnicode(Launcher.convertStringArrayToString(array1, System.getProperty("line.separator"))));
            } else {
                textArea1.setText("");
            }

            if (index == 53) {
                ArrayList<String> arrayList1 = new ArrayList<>();
                int ii = index;
                while (ii != split.length) {
                    arrayList1.add(split[ii]);
                    ii++;
                }
                String[] array1 = arrayList1.toArray(new String[0]);
                super.setText(Launcher.parseUnicode(Launcher.convertStringArrayToString(array1, System.getProperty("line.separator"))));
            } else {
                super.setText(Launcher.parseUnicode(str));
            }
        }
    };

    private Server actualServer;

    public AboutModsPage() {
        super(PageName.ABOUT_MODS, "À propos", "Mods");

        textArea.setBounds(11, 11, 399, 497);
        textArea.addMouseListener(this);
        add(textArea);

        textArea1.setBounds(410, 11, 398, 497);
        textArea1.addMouseListener(this);
        add(textArea1);
    }

    @Override
    public void setVisible(boolean e) {
        if (e) {
            textArea.setText(Launcher.convertStringArrayToString(actualServer.getModsNameAndVersionArray(), System.getProperty("line.separator")));
            setSubtitle("Mods - " + actualServer.getServerName());
        }
        super.setVisible(e);
    }

    public void setVisible(boolean e, Server server) {
        actualServer = server;
        setVisible(e);
    }

    public void setServer(Server server) {
        this.actualServer = server;
        textArea.setText(Launcher.convertStringArrayToString(server.getModsNameAndVersionArray(), System.getProperty("line.separator")));
    }

    public Server getServer() {return actualServer;}

    @Override
    public void mouseClicked(MouseEvent e) {
        if (index == 0) {
            index = 53;
        } else if (index == 53) {
            index = 0;
        }
        textArea.setText(Launcher.convertStringArrayToString(actualServer.getModsNameAndVersionArray(), System.getProperty("line.separator")));
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
