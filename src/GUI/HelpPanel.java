package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
    private JTextArea howtoFirstParagraph;
    private JLabel toolbarScreenshotLabel;
    private JLabel toolbarscreenshotdescriptionlabel;
    private JTextArea howtoSecondParagraph;
    private JLabel filechooserImage;
    private JLabel settingsScreenshotImage;
    private JTextArea howtoThirdParagraph;
    private JTextArea howtoFourthParagraph;
    private JLabel analyzingtoolbarscreenshotImage;

    Image programscreenshot=new BufferedImage(860,458,BufferedImage.TYPE_INT_RGB);

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
                HelpPanel.this.setSize(new Dimension(((int)getScaleFactor()*frameWidth),((int)getScaleFactor()*frameHeight)));
                HelpPanel.this.setPreferredSize(new Dimension(((int)getScaleFactor()*frameWidth),((int)getScaleFactor()*frameHeight)));
                HelpPanel.this.setMaximumSize(new Dimension(((int)getScaleFactor()*frameWidth),((int)getScaleFactor()*frameHeight)));

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
            screenshotprogramlabel.setBorder(new LineBorder(Color.lightGray,2));
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

        howtoFirstParagraph=new JTextArea();
        initiliazeArea(howtoFirstParagraph);
        howtoFirstParagraph.setText("To start analyzing a directory you first must choose a directory." +
                        " In order to choose a directory you need to click on the \"Choose directory\" button," +
                        " which is located in the toolbar in the top left corner of the program");

        image=new ImageIcon();
        try {
            try {
                image =  new ImageIcon(getClass().getClassLoader().getResource("res/ToolbarScreenshot.PNG"));
            }catch(NullPointerException ignored){
            }
        } finally {
            toolbarScreenshotLabel = new JLabel(new ImageIcon(getFittingImage(image.getImage(),643,94)));
            toolbarScreenshotLabel.setBorder(new LineBorder(Color.lightGray,2));
        }

        toolbarscreenshotdescriptionlabel=new JLabel();
        toolbarscreenshotdescriptionlabel.setFont(GraphicsConstants.labelFontItalic);
        toolbarscreenshotdescriptionlabel.setText("The toolbar of the program");

        howtoSecondParagraph=new JTextArea();
        initiliazeArea(howtoSecondParagraph);
        howtoSecondParagraph.setText("When you click on the button a menu, as seen on the right, will appear where you can choose the directory." +
                " After choosing a directory you can either start analyzing by pressing the \"Analyze\" button in the toolbar" +
                " or you can change the settings regarding multithreading during scan and creation of the tree diagram." +
                " It is highly recommended to read the settings section in order to get the most suiting configuration regarding system performance and processing time.");
        image=new ImageIcon();
        try {
            try {
                image =  new ImageIcon(getClass().getClassLoader().getResource("res/FilechooserScreenshot.PNG"));
            }catch(NullPointerException ignored){
            }
        } finally {
            filechooserImage = new JLabel(new ImageIcon(getFittingImage(image.getImage(),302,222)));
            filechooserImage.setBorder(new LineBorder(Color.lightGray,2));
        }
        image=new ImageIcon();
        try {
            try {
                image =  new ImageIcon(getClass().getClassLoader().getResource("res/SettingsScreenshot.PNG"));
            }catch(NullPointerException ignored){
            }
        } finally {
            settingsScreenshotImage = new JLabel(new ImageIcon(getFittingImage(image.getImage(),298,204)));
            settingsScreenshotImage.setBorder(new LineBorder(Color.lightGray,2));
        }

        howtoThirdParagraph=new JTextArea();
        initiliazeArea(howtoThirdParagraph);
        howtoThirdParagraph.setText("The settings menu can be seen in the image below. In the settings menu you can enable or disable the use of multiple threads in order to " +
                "complete the scan faster and create the tree diagram faster. By enabling multithreading the CPU usage may reach up to 100% for longer periods of time. " +
                "In order to prevent that you can edit the maximum amount of threads that will be allowed to run during this process, where 1 thread would be equal to disabling multithreading." +
                "The default settings chosen by the program represents the maximum amount of threads the processor is able to handle simultaneously while having each thread at maximum efficiency." +
                "If you want to minimize the impact of the scan on your system, it is recommended to turn of both multithreading for scanning and creation of tree");

        diagrammsectionTitle=new JLabel();
        diagrammsectionTitle.setFont(GraphicsConstants.titleFontLarge);
        diagrammsectionTitle.setText("Diagrams");

        howtoFourthParagraph=new JTextArea();
        initiliazeArea(howtoFourthParagraph);
        howtoFourthParagraph.setText("If you have chosen a directory and wish to scan the directory, you can simply press the analyze button. The progress can be controlled on the bottom of the program." +
                "During the scanning and analyzing the label at the bottom will be updated with the latest information. The scanning and analyzing is done in 4 steps:" +
                "\n     1: Scanning of directory structure" +
                "\n         During this step the label will be updated everytime a new directory is being scanned. This step usually takes up most of the time." +
                "\n     2: Calculation of directory sizes" +
                "\n         In this step the sizes of the directories will be calculated. This step is indicated by displaying \"Calculating sizes\" at the bottom. " +
                "\n         Usually this takes about 2-5 minutes depending on hard drive and size of directory" +
                "\n     3: Sorting of nodes" +
                "\n         This step is required for the proper creation of the diagrams. Usually this does not take more than a few seconds." +
                "\n          During this step \"Sorting nodes\" will be displayed." +
                "\n     4: Creating diagrams" +
                "\n         As the name indicates in here the diagrams will be prepared. The sunburst diagram will be displayed as soon as its ready." +
                "\n          Note that the tree diagram takes much longer to create. When finished the tree diagram will be shown and" +
                "\n          at the bottom of the program you will see a message saying \"Finished in \" and the amount of time it took to analyze the directory." +
                "\n\nDuring the scanning the toolbar looks like shown below. If you want to cancel the scanning you can simply click on the cancel button.");

        image=new ImageIcon();
        try {
            try {
                image =  new ImageIcon(getClass().getClassLoader().getResource("res/AnalyzingtoolbarScreenshot.PNG"));
            }catch(NullPointerException ignored){
            }
        } finally {
            analyzingtoolbarscreenshotImage = new JLabel(new ImageIcon(getFittingImage(image.getImage(),299,32)));
            analyzingtoolbarscreenshotImage.setBorder(new LineBorder(Color.lightGray,2));
        }

        headLabel = new JTextArea();
        initiliazeArea(headLabel);
        headLabel.setFont(GraphicsConstants.labelFontItalic);
        headLabel.setMinimumSize(new Dimension((int)(getScaleFactor()*frameWidth-100),(int) (getScaleFactor()*50)));
        headLabel.setText("Jaobab uses two different diagrams to visualize the file structure of the chosen directory, the tree diagram on the left and the sunburst diagram on the right.");

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
            treeviewImageLabel.setBorder(new LineBorder(Color.lightGray,2));

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
            sunviewImageLabel.setBorder(new LineBorder(Color.lightGray,2));

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
