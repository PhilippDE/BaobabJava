package GUI;

import javax.swing.*;
import java.awt.*;

import static GUI.GraphicsConstants.getScaleFactor;

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
    private JPanel contentPanel;
    private JScrollPane contentScroll;

    private static final int frameWidth=880;
    private static final int frameHeight=530;

    private static final int textHeight=200;


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
                HelpPanel.this.setMinimumSize(new Dimension(((int)getScaleFactor()*frameWidth),((int)getScaleFactor()*frameHeight)));

            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    private void createUIComponents() {
        rootPanel = new JPanel();
        rootPanel.setMinimumSize(new Dimension(((int)getScaleFactor()*frameWidth),((int)getScaleFactor()*frameHeight)));

        contentPanel=new JPanel();
        contentScroll=new JScrollPane();

        headLabel = new JLabel();
        headLabel.setFont(GraphicsConstants.labelFontItalic);

        treeviewTitleLabel = new JLabel();
        treeviewTitleLabel.setFont(GraphicsConstants.titleFont);
        ImageIcon image=new ImageIcon();
        try {
            try {
                image =  new ImageIcon(getClass().getClassLoader().getResource("res/TreeViewScreenshot.jpg"));
            }catch(NullPointerException ignored){
            }
        } finally {
            treeviewImageLabel = new JLabel(image);
        }


        treeviewTextArea = new JTextArea();
        treeviewTextArea.setMinimumSize(new Dimension(((int)getScaleFactor()*textHeight),((int)getScaleFactor()*100)));
        treeviewTextArea.setPreferredSize(new Dimension(((int)getScaleFactor()*textHeight),-1));
        treeviewTextArea.setEditable(false);
        treeviewTextArea.setFont(GraphicsConstants.standardFont);
        treeviewTextArea.setWrapStyleWord(true);
        treeviewTextArea.setLineWrap(true);
        treeviewTextArea.setBackground(rootPanel.getBackground());
        treeviewTextArea.setText("The tree view can be compared to a normal file browser. The difference is that you have a bar on the right which shows " +
                                 "you how much space a folder uses of the upper folder. The bar of a folder has the same color as the folder has in the sun view. " +
                                 "\n\nA right click on a tree item opens a context menu with more options.");

        sunviewTitleLabel = new JLabel();
        sunviewTitleLabel.setFont(GraphicsConstants.titleFont);


        try {
            try {
                image =  new ImageIcon(getClass().getClassLoader().getResource("res/SunViewScreenshot.jpg"));
            }catch(NullPointerException ignored){
            }
        } finally {
            sunviewImageLabel = new JLabel(image);
        }

        sunviewTextArea = new JTextArea();
        sunviewTextArea.setMinimumSize(new Dimension(((int)getScaleFactor()*textHeight),((int)getScaleFactor()*100)));
        treeviewTextArea.setPreferredSize(new Dimension(((int)getScaleFactor()*textHeight),-1));
        sunviewTextArea.setEditable(false);
        sunviewTextArea.setFont(GraphicsConstants.standardFont);
        sunviewTextArea.setWrapStyleWord(true);
        sunviewTextArea.setLineWrap(true);
        sunviewTextArea.setBackground(rootPanel.getBackground());
        sunviewTextArea.setText("The sun view is a sunburst diagram, where the most an inner layer represents the parent directory of the layers outside of it. The larger the layer, the larger is the directory represented by it.  When the mouse goes over an item the property view " +
                                "on the left gives information about the selected folder. \n\nA left click on an item opens the path of it in the tree view and " +
                                "a right click opens a context menu with more options.");
    }
}
