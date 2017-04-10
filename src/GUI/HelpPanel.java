package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Philipp on 09.04.2017.
 */
public class HelpPanel extends JFrame {

    private JPanel rootPanel;
    private JTextArea treeviewTextArea;
    private JLabel headLabel;
    private JLabel treeviewTitleLabel;
    private JLabel treeviewImageLabel;
    private JLabel sunviewTitleLabel;
    private JLabel sunviewImageLabel;
    private JTextArea sunviewTextArea;

    public static void main(String[] args) {
        new HelpPanel();
    }

    HelpPanel() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                HelpPanel.this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                HelpPanel.this.setContentPane(HelpPanel.this.rootPanel);
                HelpPanel.this.setTitle("Help");
                HelpPanel.this.pack();
                HelpPanel.this.setVisible(true);
            }
        });
    }

    private void createUIComponents() {
        rootPanel = new JPanel();

        headLabel = new JLabel();
        headLabel.setFont(GraphicsConstants.standardFontLarger);

        treeviewTitleLabel = new JLabel();
        treeviewTitleLabel.setFont(GraphicsConstants.standardFontLarger);

        try {
            BufferedImage image = ImageIO.read(new File("./res/TreeViewScreenshot.jpg"));
            treeviewImageLabel = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            treeviewImageLabel=new JLabel();
            e.printStackTrace();
        }


        treeviewTextArea = new JTextArea();
        treeviewTextArea.setEditable(false);
        treeviewTextArea.setFont(GraphicsConstants.standardFont);
        treeviewTextArea.setWrapStyleWord(true);
        treeviewTextArea.setLineWrap(true);
        treeviewTextArea.setBackground(rootPanel.getBackground());
        treeviewTextArea.setText("The tree view can be compared to a normal file browser. The difference is that you have a bar on the right which shows " +
                                 "you how much space a folder uses of the upper folder. The bar of a folder has the same color as the folder has in the sun view. " +
                                 "\nA right click on a tree item opens a context menu with more options.");

        sunviewTitleLabel = new JLabel();
        sunviewTitleLabel.setFont(GraphicsConstants.standardFontLarger);

        try {
            BufferedImage image = ImageIO.read(new File("./res/SunViewScreenshot.jpg"));
            sunviewImageLabel = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            sunviewImageLabel=new JLabel();
            e.printStackTrace();
        }

        sunviewTextArea = new JTextArea();
        sunviewTextArea.setEditable(false);
        sunviewTextArea.setFont(GraphicsConstants.standardFont);
        sunviewTextArea.setWrapStyleWord(true);
        sunviewTextArea.setLineWrap(true);
        sunviewTextArea.setBackground(rootPanel.getBackground());
        sunviewTextArea.setText("The sun view is a graph....(TODO). When the mouse goes over an item the property view " +
                                "on the left gives information about the selected folder. A left click on an item opens the path of it in the tree view and " +
                                "a right click opens a context menu with more options.");
    }
}
