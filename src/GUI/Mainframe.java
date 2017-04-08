package GUI;

import Data.Node;
import Data.Threading.NotifyingThread;
import Data.Threading.ThreadFinishedListener;
import Data.Threading.Threadmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * The main GUI of this program
 * Created by Marcel on 06.03.2017.
 */
public class Mainframe extends JFrame {


    private JButton chooseDirectoy;
    private JPanel rootPanel;
    private TreeviewPanel treeview;
    private SunviewPanel sunview;
    private JButton analyzeButton;
    private JLabel currentPathLabel;
    private JLabel progressLabel;
    private JPanel displayPanel;
    private JButton settingsButton;
    private JToolBar toolBar;
    private JToolBar.Separator chooseanalyzeSeperator;
    private JToolBar.Separator analyzesettingsSeperator;
    private JLabel pathLabelStatic;

    private static Node supernode;

    private static Thread background;

    private boolean threadStarted = false;
    private boolean pathChangedSinceLastAnalysis = false;

    private static Mainframe instance;

    private Mainframe() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(this.rootPanel);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(800, 540));
        this.setPreferredSize(new Dimension(960, 540));
        this.setSize(new Dimension(960, 540));
        this.setTitle("Jaobab");
        this.pack();
        this.setVisible(true);
        instance=this;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    private void createUIComponents() {
        rootPanel = new JPanel();
        chooseDirectoy = new JButton();
        chooseDirectoy.setFont(GraphicsConstants.standardFontLarger);
        chooseDirectoy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fs = new JFileChooser(new File("c:"));
                fs.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fs.setDialogTitle("save");
                //fs.setFileFilter(new FileNameExtensionFilter("Image", "jpeg","png"));
                int returnVal = fs.showSaveDialog(null);
                switch (returnVal) {
                    case JFileChooser.APPROVE_OPTION:
                        File input = fs.getSelectedFile();
                        if (input.exists()) {
                            Node n = new Node(input);
                            // check if the node hasn't changed
                            if (supernode != null) {
                                if (!n.getOwnPath().getAbsolutePath().equals(supernode.getOwnPath().getAbsolutePath())) {
                                    // path changed
                                    pathChangedSinceLastAnalysis = true;
                                }
                            } else {
                                // path changed
                                pathChangedSinceLastAnalysis = true;
                            }
                            supernode = new Node(input);
                        }
                        fs.setVisible(false);
                        break;
                    case JFileChooser.CANCEL_OPTION:
                        fs.setVisible(false);
                        break;
                }
                currentPathLabel.setText(supernode.getOwnPath().getAbsolutePath());
            }
        });

        analyzeButton = new JButton();
        analyzeButton.setFont(GraphicsConstants.standardFontLarger);
        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(analyzeButton.getText().equals("Analyze")) {
                    if (supernode == null) {
                        JFileChooser fs = new JFileChooser(new File("c:"));
                        fs.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        fs.setDialogTitle("save");
                        //fs.setFileFilter(new FileNameExtensionFilter("Image", "jpeg","png"));
                        int returnVal = fs.showSaveDialog(null);
                        switch (returnVal) {
                            case JFileChooser.APPROVE_OPTION:
                                File input = fs.getSelectedFile();
                                if (input.exists()) {
                                    supernode = new Node(input);
                                    pathChangedSinceLastAnalysis = true;
                                }
                                fs.setVisible(false);
                                break;
                            case JFileChooser.CANCEL_OPTION:
                                fs.setVisible(false);
                                break;
                        }
                    }
                    if (!pathChangedSinceLastAnalysis) return;
                    pathChangedSinceLastAnalysis = false;
                    currentPathLabel.setText(supernode.getOwnPath().getAbsolutePath());
                    processNode(supernode);
                }else if(analyzeButton.getText().equals("Cancel")){

                    //Code for restoring the default state after user chose to cancel calculating

                    while(background.isAlive()){
                        background.interrupt();
                    }

                    Threadmanager.stopThreads();
                    sunview.enable();
                    treeview.restore();
                    enableComponents();
                    threadStarted=false;
                    progressLabel.setText("Ready");
                }
            }
        });

        displayPanel=new JPanel();
        displayPanel.setPreferredSize(new Dimension(GraphicsConstants.treeviewPrefferedFULLHDX+GraphicsConstants.sunviewPrefferedFULLHDX,
                GraphicsConstants.treeviewPrefferedFULLHDY+GraphicsConstants.sunviewPrefferedFULLHDY));
        treeview = new TreeviewPanel();
        treeview.getRootPanel().setPreferredSize(new Dimension(GraphicsConstants.treeviewPrefferedFULLHDX,
                (GraphicsConstants.treeviewPrefferedFULLHDY)));

        sunview = new SunviewPanel(treeview);
        sunview.getRootPanel().setPreferredSize(new Dimension(GraphicsConstants.sunviewPrefferedFULLHDX,
                (GraphicsConstants.sunviewPrefferedFULLHDY)));
        currentPathLabel = new JLabel();
        currentPathLabel.setFont(GraphicsConstants.standardFont);

        progressLabel = new JLabel();
        this.progressLabel.setMinimumSize(new Dimension(this.rootPanel.getWidth() - 20, 15));
        this.progressLabel.setPreferredSize(new Dimension(this.rootPanel.getWidth() - 20, 15));
        this.progressLabel.setMaximumSize(new Dimension(this.rootPanel.getWidth() - 20, 15));
        progressLabel.setFont(GraphicsConstants.standardFont);
        progressLabel.setText("Ready");

        pathLabelStatic = new JLabel();
        pathLabelStatic.setFont(GraphicsConstants.standardFont);

        settingsButton=new JButton();
        settingsButton.setFont(GraphicsConstants.standardFont);
        settingsButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsPanel();
            }
        }));

        toolBar=new JToolBar();
        toolBar.setFloatable(false);

        chooseanalyzeSeperator=new JToolBar.Separator(new Dimension(10,20));
        toolBar.add(chooseanalyzeSeperator);

        analyzesettingsSeperator=new JToolBar.Separator(new Dimension(25,20));
        toolBar.add(analyzesettingsSeperator);

        pathLabelStatic=new JLabel();
        pathLabelStatic.setFont(GraphicsConstants.standardFont);
    }

    static void processNode(final Node node){
        instance.currentPathLabel.setText(supernode.getOwnPath().getAbsolutePath());
        background = new Thread(){
            @Override
            public void run() {
                System.out.println("Started");
                long start = System.currentTimeMillis();
                supernode = node;

                instance.sunview.disable();
                instance.treeview.disable();
                instance.disableComponents();


                instance.sunview.displayClaculatingMesssage();
                instance.sunview.setNodeInformation(supernode.getName(), "-calculating-", supernode.getOwnPath().getAbsolutePath());
                instance.treeview.displayClaculatingMesssage();
                long millis = System.currentTimeMillis() - start;
                if (supernode != null) {
                    System.out.println("Started caclulating");

                    //Checking if interrupted so thread stops
                    if(Thread.interrupted()) {
                        return;
                    }
                    supernode.calculateSubnodes(instance.progressLabel);
                    instance.progressLabel.setText("Calculating sizes");
                    System.out.println(String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes(millis),
                            TimeUnit.MILLISECONDS.toSeconds(millis) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                    ));

                    //Checking if interrupted so thread stops
                    if(Thread.interrupted()) {
                        return;
                    }
                    supernode.calculateSize();
                    instance.progressLabel.setText("Sorting nodes");
                    millis = System.currentTimeMillis() - start;
                    System.out.println(String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes(millis),
                            TimeUnit.MILLISECONDS.toSeconds(millis) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                    ));

                    //Checking if interrupted so thread stops
                    if(Thread.interrupted()) {
                        return;
                    }
                    supernode.sortNodesSizeReversed();
                    instance.progressLabel.setText("Preparing visualization");
                    millis = System.currentTimeMillis() - start;
                    System.out.println(String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes(millis),
                            TimeUnit.MILLISECONDS.toSeconds(millis) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                    ));

                    //Checking if interrupted so thread stops
                    if(Thread.interrupted()) {
                        return;
                    }
                    SunviewPanel.setColorsBasedOnAngle(supernode);
                    millis = System.currentTimeMillis() - start;
                    System.out.println(String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes(millis),
                            TimeUnit.MILLISECONDS.toSeconds(millis) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                    ));
                }
                //Checking if interrupted so thread stops
                if(Thread.interrupted()) {
                    return;
                }


                instance.sunview.enable();

                instance.progressLabel.setText("Creating diagramms");
                final long finalMillis = System.currentTimeMillis() - start;
                final long millisTree=System.currentTimeMillis();
                ThreadFinishedListener threadListener=new ThreadFinishedListener() {
                    @Override
                    public void notifyThreadFinished(NotifyingThread thread) {
                        long millTree=System.currentTimeMillis()-millisTree;
                        instance.progressLabel.setText("Finished in " + String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes(finalMillis+millTree),
                                TimeUnit.MILLISECONDS.toSeconds(finalMillis+millTree) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(finalMillis+millTree)))
                        );
                    }
                };
                instance.treeview.showNode(supernode,threadListener);
                instance.sunview.drawNode(supernode);
                instance.treeview.enable();


                instance.enableComponents();



                //Checking if interrupted so thread stops
                if(Thread.interrupted()) {
                    return;
                }
                instance.sunview.setNodeInformation(supernode.getName(), supernode.sizeFormated(), supernode.getOwnPath().getAbsolutePath());

                instance.threadStarted = false;


            }
        };
        if (!instance.threadStarted) {
            instance.threadStarted = true;
            background.start();
        }
    }

    private void disableComponents(){
        chooseDirectoy.setEnabled(false);
        settingsButton.setEnabled(false);
        analyzeButton.setText("Cancel");
    }
    private void enableComponents() {
        chooseDirectoy.setEnabled(true);
        settingsButton.setEnabled(true);
        analyzeButton.setText("Analyze");
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        new Mainframe();
    }

}
