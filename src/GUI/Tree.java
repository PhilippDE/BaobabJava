package GUI;

import Data.Node;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;

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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Color c = new Color(Color.HSBtoRGB((float)Math.toRadians(95), 1, 1));
        //g.setColor(c);
        for(int i = 0; i < getRowCount(); i++) {
            TreePath path = getPathForRow(i);
            Node node = getNode(path);
            if(node != null) {
                //System.out.println(node.getName() + ": " + node.getUsagePercentOfParent());
                // get bounds of the row / cell
                Rectangle bounds = getRowBounds(i);
                Rectangle clipBounds = g.getClipBounds();
                double percent = node.getUsagePercentOfParent();
                //System.out.println((float)(percent*100));
                Color c = new Color(Color.HSBtoRGB((float)(1-percent)/3, 1, 1));
                // 1/3 -> Grün
                // 0/3 -> Rot

                // 0/3 -> Grün
                g.setColor(c);
                //System.out.println(percent);
                int boxWidth = (int)((clipBounds.getWidth()/4) * percent);
                g.fillRect((int)(clipBounds.getWidth()-boxWidth), (int)bounds.getY(), boxWidth, (int)bounds.getHeight());
            }
            //Rectangle bounds = getRowBounds(i);
            //g.drawRect((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth(), (int)bounds.getHeight());
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
