package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import static GUI.GraphicsConstants.getScaleFactor;

/**
 * Created by Philipp on 09.04.2017.
 */
public class HelpPanel extends JFrame {

    private JPanel rootPanel;
    private JTextArea treeviewTextArea;
    private JTextArea headLabel;
    private JLabel treeviewTitleLabel;
    private JLabel treeviewImageLabel;
    private JLabel sunviewTitleLabel;
    private JLabel sunviewImageLabel;
    private JTextArea sunviewTextArea;
    private JPanel contentPanel;
    private JScrollPane contentScroll;
    private JLabel helptitlelabel;
    private JTextArea helpIntroductionArea;
    private JTextArea helpIntroductionFirstSectionLabel;
    private JTextArea helpIntroductionSecondSectionLabel;
    private JTextArea helpIntroductionThirdSectionLabel;
    private JButton gotoFirstSectionButton;
    private JButton gotoSecondSectionButton;
    private JButton gotoThirdSectionButton;
    private JLabel diagrammsectionTitle;
    private JLabel howtoSectionHeader;
    private JLabel screenshotprogramlabel;

    Image programscreenshot=new BufferedImage(860,458,BufferedImage.TYPE_INT_RGB);
    Image treeviewscreenshot=new BufferedImage(400,458,BufferedImage.TYPE_INT_RGB);
    Image sunviewscreenshot=new BufferedImage(600,458,BufferedImage.TYPE_INT_RGB);


    private static final int frameWidth=880;
    private static final int frameHeight=530;

    private static final int textHeight=200;


    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
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
        contentScroll.getVerticalScrollBar().setUnitIncrement(16);

        helptitlelabel =new JLabel();
        helptitlelabel.setFont(GraphicsConstants.titleFontLarge);
        helptitlelabel.setText("Overview");

        ImageIcon image=new ImageIcon();
        try {
            try {
                image =  new ImageIcon(getClass().getClassLoader().getResource("res/ProgramScreenshot.PNG"));
                programscreenshot=getFittingImage(image.getImage(),860,458);
            }catch(NullPointerException ignored){
            }
        } finally {
            screenshotprogramlabel = new JLabel(new ImageIcon(getFittingImage(image.getImage(),800,458)));
        }

        helpIntroductionArea=new JTextArea();
        initiliazeArea(helpIntroductionArea);
        helpIntroductionArea.setText(
                "This is the help of the program. In here the program, its usage and diagrams and its features will be explained." +
                        "\n\n Below you will see the the parts of this help. By clicking on the fields below you will directly get to the corresponding part of the help" );

        helpIntroductionFirstSectionLabel = new JTextArea();
        initiliazeArea(helpIntroductionFirstSectionLabel);
        helpIntroductionFirstSectionLabel.setText("The first section is the \"How to use\" section. In this section the basic functionality of this program will be explained and the basics of the program will be covered" +
                "If you are new to this software you should read that section");

        gotoFirstSectionButton=new JButton();
        gotoFirstSectionButton.setFont(GraphicsConstants.standardFont);
        gotoFirstSectionButton.setText("Jump to \"How to use\" section");
        gotoFirstSectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentScroll.getViewport().scrollRectToVisible(howtoSectionHeader.getBounds());
                contentScroll.getVerticalScrollBar().setValue((int)howtoSectionHeader.getBounds().getY());
            }
        });

        helpIntroductionSecondSectionLabel = new JTextArea();
        initiliazeArea(helpIntroductionSecondSectionLabel);
        helpIntroductionSecondSectionLabel.setText("In this  section the diagrams will be explained and you will learn how to interpret them. ");

        gotoSecondSectionButton=new JButton();
        gotoSecondSectionButton.setFont(GraphicsConstants.standardFont);
        gotoSecondSectionButton.setText("Jump to diagram section");
        gotoSecondSectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentScroll.getViewport().scrollRectToVisible(diagrammsectionTitle.getBounds());
                contentScroll.getVerticalScrollBar().setValue((int)diagrammsectionTitle.getBounds().getY());
            }
        });

        helpIntroductionThirdSectionLabel = new JTextArea();
        initiliazeArea(helpIntroductionThirdSectionLabel);
        helpIntroductionThirdSectionLabel.setText("Do we need that ?");


        gotoThirdSectionButton=new JButton();
        gotoThirdSectionButton.setFont(GraphicsConstants.standardFont);
        gotoThirdSectionButton.setText("Jump to \"How to use\" section");

        howtoSectionHeader=new JLabel();
        howtoSectionHeader.setFont(GraphicsConstants.titleFontLarge);
        howtoSectionHeader.setText("How to use this program");





        diagrammsectionTitle=new JLabel();
        diagrammsectionTitle.setFont(GraphicsConstants.titleFontLarge);
        diagrammsectionTitle.setText("Diagrams");

        headLabel = new JTextArea();
        initiliazeArea(headLabel);
        headLabel.setFont(GraphicsConstants.labelFontItalic);
        headLabel.setMinimumSize(new Dimension((int)(getScaleFactor()*frameWidth-100),(int)(getScaleFactor()*50)));
        headLabel.setText("BaobabJava uses two different diagrams to visualize the file structure of the chosen directory, the tree diagram on the left and the sunburst diagram on the right.");

        treeviewTitleLabel = new JLabel();
        treeviewTitleLabel.setFont(GraphicsConstants.titleFont);
        treeviewTitleLabel.setText("Tree view");

        image=new ImageIcon();
        try {
            try {
                image =  new ImageIcon(getClass().getClassLoader().getResource("res/TreeViewScreenshot.PNG"));
            }catch(NullPointerException ignored){
            }
        } finally {
            treeviewImageLabel = new JLabel(new ImageIcon(getFittingImage(image.getImage(),309,458)));
        }


        treeviewTextArea = new JTextArea();
        treeviewTextArea.setMinimumSize(new Dimension((int)(getScaleFactor()*200),(int)(getScaleFactor()*textHeight)));
        treeviewTextArea.setPreferredSize(new Dimension((int)(getScaleFactor()*200),(int)(getScaleFactor()*textHeight)));
        initiliazeArea(treeviewTextArea);
        treeviewTextArea.setText("The tree view can be compared to a normal file browser. The difference is that you have a bar on the right which shows " +
                                 "you how much space a folder uses of the upper folder. The bar of a folder has the same color as the folder has in the sun view. " +
                                 "\n\nA right click on a tree item opens a context menu with more options.");

        sunviewTitleLabel = new JLabel();
        sunviewTitleLabel.setFont(GraphicsConstants.titleFont);
        sunviewTitleLabel.setText("Sun view");

        try {
            try {
                image =  new ImageIcon(getClass().getClassLoader().getResource("res/SunViewScreenshot.PNG"));
            }catch(NullPointerException ignored){
            }
        } finally {
            sunviewImageLabel = new JLabel(new ImageIcon(getFittingImage(image.getImage(),700,526)));
        }

        sunviewTextArea = new JTextArea();
        sunviewTextArea.setMinimumSize(new Dimension((int)(getScaleFactor()*200),(int)(getScaleFactor()*textHeight)));
        sunviewTextArea.setPreferredSize(new Dimension((int)(getScaleFactor()*200),(int)(getScaleFactor()*textHeight)));
        initiliazeArea(sunviewTextArea);
        sunviewTextArea.setText("The sun view is a sunburst diagram, where the most an inner layer represents the parent directory of the layers outside of it. The larger the layer, the larger is the directory represented by it.  When the mouse goes over an item the property view " +
                                "on the left gives information about the selected folder. " +
                "\n\nA left click on an item opens the path of it in the tree view and " +
                                "a right click opens a context menu with more options.");
    }

    private void initiliazeArea(JTextArea area){
        area.setEditable(false);
        area.setFont(GraphicsConstants.standardFont);
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
        area.setBackground(rootPanel.getBackground());
    }


    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static Image getFittingImage(Image image,int width,int height){
        return image.getScaledInstance(width, height,
                Image.SCALE_SMOOTH);
    }

}
