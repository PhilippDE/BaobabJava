package GUI;

import Data.Node;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;

/**
 * The custom JTree that will be used to display the tree hierarchy of the nodes
 * Created by Philipp on 17.03.2017.
 */
public class Tree extends JTree {

    Tree(DefaultMutableTreeNode node) {
        super(node);
        this.setRowHeight(22);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 0; i < getRowCount(); i++) {
            TreePath path = getPathForRow(i);
            Node node = getNode(path);
            if(node != null) {
                Rectangle bounds = getRowBounds(i);
                double percent = node.getUsagePercentOfParent();
                g.setColor(node.getOwnColor());
                int boxWidth = (int)((getWidth()/4) * percent);
                g.fillRect((getWidth()-boxWidth)-3, (int)bounds.getY()+5, boxWidth, (int)bounds.getHeight()-9);
                //g.setColor(Color.BLACK);
                //g.drawLine((int)(clipBounds.getWidth()*0.75-3), (int)bounds.getY(),(int)(clipBounds.getWidth()*0.75-3),(int)(bounds.getY()+bounds.getHeight()-5));
                g.setColor(new Color(210,210,210));
                g.drawRect((int)((getWidth())*0.75)-5, (int)bounds.getY()+3,
                        (int)((getWidth())*0.25)+4, (int)bounds.getHeight()-6);
            }
        }
    }

    /**
     * Returns the Node object that is in that given TreePath
     * @param path the path that will be checked
     * @return the node at that position; May return null if there is no node object at the given path
     */
    private Node getNode(TreePath path) {
        DefaultMutableTreeNode tn = (DefaultMutableTreeNode)path.getLastPathComponent();
        Node node;
        try {
            node = (Node)tn.getUserObject();
            return node;
        } catch(ClassCastException e) {
            return null;
        }
    }


    /**
     * Returns the node tath is at the given X and Y coordinate in the tree
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the node that is at that position
     */
    Node getNodeFromXY(int x, int y){
        return getNode(this.getPathForLocation(x,y));
    }
}
