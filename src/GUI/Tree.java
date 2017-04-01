package GUI;

import Data.Node;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;

/**
 * Created by Philipp on 17.03.2017.
 */
public class Tree extends JTree {

    private DefaultMutableTreeNode rootNode;

    public Tree(DefaultMutableTreeNode node) {
        super(node);
        rootNode = node;
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
                Rectangle clipBounds = g.getClipBounds();
                double percent = node.getUsagePercentOfParent();
                Color c = new Color(Color.HSBtoRGB((float)(1-percent)/3, 1, 1));
                g.setColor(node.getOwnColor());
                int boxWidth = (int)((clipBounds.getWidth()/4) * percent);
                g.fillRect((int)(clipBounds.getWidth()-boxWidth)-3, (int)bounds.getY()+5, boxWidth, (int)bounds.getHeight()-9);
                //g.setColor(Color.BLACK);
                //g.drawLine((int)(clipBounds.getWidth()*0.75-3), (int)bounds.getY(),(int)(clipBounds.getWidth()*0.75-3),(int)(bounds.getY()+bounds.getHeight()-5));
                g.setColor(new Color(210,210,210));
                g.drawRect((int)((clipBounds.getWidth())*0.75)-5, (int)bounds.getY()+3,
                        (int)((clipBounds.getWidth())*0.25)+4, (int)bounds.getHeight()-6);
            }
        }
    }

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
}
