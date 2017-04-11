package GUI;

import Data.Node;

import javax.swing.*;
import java.awt.*;

import static GUI.GraphicsConstants.getScaleFactor;

/**
 * This window will show all information about the passed node object
 * Created by Marcel on 05.04.2017.
 */
public class NodeView extends JFrame {
    private JPanel rootPanel;
    private JPanel infoPanel;
    private JLabel nameLabel;
    private JLabel sizeLabel;
    private JTextArea fullPathArea;
    private JLabel amountOfFiles;
    private JLabel folderLabelStatic;
    private JLabel sizeLabelStatic;
    private JLabel fullPathLabelStatic;
    private JLabel filesPathStatic;


    public NodeView(final Node node) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                NodeView.this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                NodeView.this.setContentPane(NodeView.this.rootPanel);
                NodeView.this.setMinimumSize(new Dimension(GraphicsConstants.nodeViewerFULLHDX, GraphicsConstants.nodeViewerFULLHDY / 2));
                NodeView.this.setPreferredSize(new Dimension(GraphicsConstants.nodeViewerFULLHDX, GraphicsConstants.nodeViewerFULLHDY / 2));
                NodeView.this.setSize(new Dimension(GraphicsConstants.nodeViewerFULLHDX, GraphicsConstants.nodeViewerFULLHDY / 2));
                NodeView.this.setTitle("Folder details: " + node.getName());
                nameLabel.setText(node.getName());
                sizeLabel.setText(Node.sizeFormated(node.getSize()));
                fullPathArea.setText(node.getOwnPath().getAbsolutePath());
                amountOfFiles.setText(node.getFilesCountRecursively() + " Files");
                NodeView.this.pack();
                NodeView.this.setVisible(true);
            }
        });

    }

    private void createUIComponents() {
        rootPanel = new JPanel();
        infoPanel = new JPanel();

        nameLabel = new JLabel();
        nameLabel.setFont(GraphicsConstants.standardFont);
        nameLabel.setMinimumSize(new Dimension(GraphicsConstants.nameLabelX, GraphicsConstants.nameLabelY));
        nameLabel.setPreferredSize(new Dimension(GraphicsConstants.nameLabelX, GraphicsConstants.nameLabelY));
        nameLabel.setMaximumSize(new Dimension(GraphicsConstants.nameLabelX, GraphicsConstants.nameLabelY));

        sizeLabel = new JLabel();
        sizeLabel.setFont(GraphicsConstants.standardFont);
        sizeLabel.setMinimumSize(new Dimension(GraphicsConstants.nameLabelX, GraphicsConstants.nameLabelY));
        sizeLabel.setPreferredSize(new Dimension(GraphicsConstants.nameLabelX, GraphicsConstants.nameLabelY));
        sizeLabel.setMaximumSize(new Dimension(GraphicsConstants.nameLabelX, GraphicsConstants.nameLabelY));

        fullPathArea = new JTextArea();
        fullPathArea.setMinimumSize(new Dimension(((int)getScaleFactor()*150),-1));
        fullPathArea.setFont(GraphicsConstants.standardFont);
        fullPathArea.setEditable(false);
        fullPathArea.setLineWrap(true);
        fullPathArea.setWrapStyleWord(true);
        fullPathArea.setBackground(infoPanel.getBackground());

        amountOfFiles = new JLabel();
        amountOfFiles.setFont(GraphicsConstants.standardFont);
        amountOfFiles.setMinimumSize(new Dimension(GraphicsConstants.nameLabelX, GraphicsConstants.nameLabelY));
        amountOfFiles.setPreferredSize(new Dimension(GraphicsConstants.nameLabelX, GraphicsConstants.nameLabelY));
        amountOfFiles.setMaximumSize(new Dimension(GraphicsConstants.nameLabelX, GraphicsConstants.nameLabelY));


        folderLabelStatic = new JLabel();
        folderLabelStatic.setFont(GraphicsConstants.standardFont);

        sizeLabelStatic = new JLabel();
        sizeLabelStatic.setFont(GraphicsConstants.standardFont);

        fullPathLabelStatic = new JLabel();
        fullPathLabelStatic.setFont(GraphicsConstants.standardFont);

        filesPathStatic = new JLabel();
        filesPathStatic.setFont(GraphicsConstants.standardFont);

    }

}
