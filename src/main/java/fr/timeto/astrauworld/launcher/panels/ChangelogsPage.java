package fr.timeto.astrauworld.launcher.panels;

import fr.timeto.astrauworld.launcher.customelements.*;
import fr.timeto.astrauworld.launcher.main.LauncherPanel;
import fr.timeto.astrauworld.launcher.main.LauncherSystemTray;
import fr.timeto.astrauworld.launcher.pagesutilities.Changelogs;
import fr.timeto.astrauworld.launcher.pagesutilities.PageChange;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static fr.timeto.astrauworld.launcher.main.Launcher.parseUnicode;

public class ChangelogsPage extends PageCreator implements ActionListener {

    // Changelogs components
    /**
     * La liste des versions des changelogs
     * @since Beta2.1.2
     * @see Changelogs#getChangelogsVersionsList()
     */
    public final String[] versionsArrayList = Changelogs.getChangelogsVersionsList();
    /**
     * La combo-box pour sélectionner la version du changelog voulu
     * @since Beta2.1.2
     * @see Changelogs
     */
    public final AWComboBox<String> versionComboBox = new AWComboBox<>(versionsArrayList);
    /**
     * La text area non éditable où apparait le texte du changelog
     * @since Beta2.1.2
     * @see Changelogs
     */
    public final AWTextArea textArea = new AWTextArea(14f, false);

    public ChangelogsPage() {
        super(PageName.CHANGELOGS, "Changelogs", "");

        setLayout(null);
        setOpaque(false);

        versionComboBox.setBounds(11, 10, 150, 24);
        versionComboBox.addActionListener(this);
        this.add(versionComboBox);

        textArea.setBounds(11, 44, 797, 460);
        this.add(textArea);

        add(getBg().getPanel());
    }

    @Override
    public void setVisible(boolean aFlag) {
        if (aFlag) {
            setSubtitle(versionComboBox.getSelectedItem().toString());
        }
        super.setVisible(aFlag);
    }

    public int verifyVersionChangelog() {
        String val = Objects.requireNonNull(versionComboBox.getSelectedItem()).toString();
        String[] T = versionsArrayList;

        int i;
        for(i = 0; i<T.length;i++){
            val = val.replaceAll("\\[", "").replaceAll("]", "");
            versionComboBox.setSelectedItem(val);
            if(val.contains(T[i]))
                //retourner la position courante
                return i;
        }
        return i-1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == versionComboBox) {
            int i = verifyVersionChangelog();
            textArea.setText(Changelogs.getChangelogsTextsList()[i]);
            if (PageChange.actualPage == pageName) {
                setSubtitle(versionComboBox.getSelectedItem().toString());
                LauncherPanel.Components.subTitleLabel.setText(parseUnicode(getSubtitle()));
                LauncherSystemTray.changeTrayTooltip();
            }
        }
    }
}
