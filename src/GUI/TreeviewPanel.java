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
        // clear rootPanel
        rootPanel.removeAll();

        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(node.getName());
        addSubnodesToTree(node, treeNode);
        JTree tree = new JTree(treeNode);

        // it should be possible to scroll when the tree is too long
        JScrollPane scrollTree = new JScrollPane(tree);
        scrollTree.setViewportView(tree);

        rootPanel.add(scrollTree);
        rootPanel.validate();
    }

    /***
     * Adds the subnodes of the given Node to the given DefaultMutableTreeNode.
     * When a subnode contains subnodes too these also get added.
     * @param node Node which subnodes should be added.
     * @param treeNode DefaultMutableTreeNode where the nodes should be added to.
     */
    private void addSubnodesToTree(Node node, DefaultMutableTreeNode treeNode) {
        for(Node n : node.getSubNodes()) {
            DefaultMutableTreeNode newTreeNode = new DefaultMutableTreeNode(n.getName());
            treeNode.add(newTreeNode);
            if(n.getSubNodes().length > 0) {
                addSubnodesToTree(n, newTreeNode);
            }
        }
    }
}
