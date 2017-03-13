package GUI;

import Data.Node;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

/**
 * Created by Marcel on 06.03.2017.
 */
public class TreeviewPanel {
    private JPanel rootPanel;

    private void createUIComponents() {
        rootPanel=new JPanel(new GridLayout(1, 1));
    }

    /***
     * This method shows a JTree with the information from the given node.
     * @param node Node that should be shown.
     */
    public void showNode(Node node) {
        // clear rootPanel because there might be another JTree from an analysis before
        rootPanel.removeAll();

        // create the first DefaultMutableTreeNode for the given supernode
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(node.getName());
        // add the subnodes, their subnodes etc. and their files
        addSubnodesToTree(node, treeNode);
        // add the files of the supernode
        addFilesToTree(node, treeNode);
        // create the JTree with the DefaultMutableTreeNode of the supernode
        JTree tree = new JTree(treeNode);

        // it should be possible to scroll when the tree is too long
        JScrollPane scrollTree = new JScrollPane(tree);
        scrollTree.setViewportView(tree);

        // add the scrollable tree to the rootPanel
        rootPanel.add(scrollTree);
        rootPanel.validate();
    }

    /***
     * Adds the subnodes and files of the given Node to the given DefaultMutableTreeNode.
     * When a subnode contains subnodes too these also get added.
     * @param node Node which subnodes should be added.
     * @param treeNode DefaultMutableTreeNode where the nodes should be added to.
     */
    private void addSubnodesToTree(Node node, DefaultMutableTreeNode treeNode) {
        // iterate through every subnode of the given node
        for(Node n : node.getSubNodes()) {
            // create a DefaultMutableTreeNode object for the subnode
            DefaultMutableTreeNode newTreeNode = new DefaultMutableTreeNode(n.getName());
            // add the DefaultMutableTreeNode to the upper tree node
            treeNode.add(newTreeNode);
            // if the subnode contains subnodes add them too
            if(n.getSubNodes().length > 0) {
                addSubnodesToTree(n, newTreeNode);
            }
            // add the information about the files in the subnode
            addFilesToTree(n, newTreeNode);
        }
    }

    /***
     * Counts the files of the node and gets the size of them.
     * A DefaultMutableTreeNode with information about the files get
     * added to the given DefaultMutableTreeNode.
     * @param node Node to get the information of the files.
     * @param treeNode DefaultMutableTreeNode to add the files information to.
     */
    private void addFilesToTree(Node node, DefaultMutableTreeNode treeNode) {
        int filesCount = Node.getFilesCount(node.getOwnPath());
        long sizeOfFiles = Node.getSizeofFiles(node.getOwnPath());
        treeNode.add(new DefaultMutableTreeNode(filesCount + " Files with size " + sizeOfFiles));
    }
}
