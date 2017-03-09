package GUI;

import Data.Node;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Marcel on 06.03.2017.
 */
public class Mainframe extends JFrame{
    private JButton chooseDirectoy;
    private JPanel rootPanel;
    private TreeviewPanel treeview;
    private SunviewPanel sunview;
    private JButton analyzeButton;

    private Node supernode;

    public Mainframe(){
        this.setContentPane(this.rootPanel);
        this.setSize(1920,1080);
        this.pack();
        this.setVisible(true);
    }


    private void createUIComponents() {
        rootPanel=new JPanel();
        chooseDirectoy=new JButton();
        chooseDirectoy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fs = new JFileChooser(new File("c:\\documents"));
                fs.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fs.setDialogTitle("save");
                //fs.setFileFilter(new FileNameExtensionFilter("Image", "jpeg","png"));
                int returnVal = fs.showSaveDialog(null);
                switch (returnVal) {
                    case JFileChooser.APPROVE_OPTION:
                        File input = fs.getSelectedFile();
                        if (input.exists()) {
                            supernode=new Node(input);
                        } else {
                        }
                        fs.setVisible(false);
                        break;
                    case JFileChooser.CANCEL_OPTION:
                        fs.setVisible(false);
                        break;
                }
            }
        });

        analyzeButton=new JButton();
        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(supernode==null) {
                    JFileChooser fs = new JFileChooser(new File("c:\\documents"));
                    fs.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    fs.setDialogTitle("save");
                    //fs.setFileFilter(new FileNameExtensionFilter("Image", "jpeg","png"));
                    int returnVal = fs.showSaveDialog(null);
                    switch (returnVal) {
                        case JFileChooser.APPROVE_OPTION:
                            File input = fs.getSelectedFile();
                            if (input.exists()) {
                                supernode = new Node(input);
                            } else {
                            }
                            fs.setVisible(false);
                            break;
                        case JFileChooser.CANCEL_OPTION:
                            fs.setVisible(false);
                            break;
                    }
                }
                if(supernode!=null){
                    supernode.calculateSubnodes();
                    supernode.calculateSize();
                    supernode.sortNodesSizeReversed();
                }
                sunview.drawNode(supernode);
            }
        });

        treeview=new TreeviewPanel();

        sunview=new SunviewPanel();
    }

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Mainframe mf=new Mainframe();
        //mf.sunview.drawArc(45,0,0);
        //mf.sunview.drawArc(45,30,1);
        //mf.sunview.drawArc(45,60,2);
        //mf.sunview.drawArc(45,90,3);
        //mf.sunview.drawArc(45,120,4);
        return;
    }

}
