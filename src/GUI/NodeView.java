package GUI;

import Data.Node;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Marcel on 05.04.2017.
 */
public class NodeView extends JFrame{
    private JPanel rootPanel;
    private JPanel infoPanel;
    private JLabel nameLabel;
    private JLabel sizeLabel;
    private JTextArea fullPathArea;
    private JLabel amountOfFiles;
    private static final Font labelFont=new Font("",Font.PLAIN,12);


    public NodeView(Node node){
        java.awt.EventQueue.invokeLater(() -> {
            NodeView.this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            NodeView.this.setContentPane(NodeView.this.rootPanel);
            NodeView.this.setMinimumSize(new Dimension(350,200));
            NodeView.this.setPreferredSize(new Dimension(350,600));
            NodeView.this.setSize(new Dimension(350,600));
            NodeView.this.setTitle("Folder details: "+node.getName());
            nameLabel.setText(node.getName());
            sizeLabel.setText(Node.sizeFormated(node.getSize()));
            fullPathArea.setText(node.getOwnPath().getAbsolutePath());
            amountOfFiles.setText(node.getFilesCountRecursively()+" Files");
            NodeView.this.pack();
            NodeView.this.setVisible(true);
        });

    }

    private void createUIComponents() {
        rootPanel=new JPanel();
        infoPanel=new JPanel();
        nameLabel=new JLabel();
        nameLabel.setFont(labelFont);
        nameLabel.setMinimumSize(new Dimension(180,35));
        nameLabel.setPreferredSize(new Dimension(180,35));
        nameLabel.setMaximumSize(new Dimension(180,35));

        sizeLabel=new JLabel();
        sizeLabel.setFont(labelFont);
        sizeLabel.setMinimumSize(new Dimension(180,35));
        sizeLabel.setPreferredSize(new Dimension(180,35));
        sizeLabel.setMaximumSize(new Dimension(180,35));

        fullPathArea=new JTextArea();
        fullPathArea.setEditable(false);
        fullPathArea.setLineWrap(true);
        fullPathArea.setBackground(infoPanel.getBackground());

        amountOfFiles=new JLabel();

    }
}
