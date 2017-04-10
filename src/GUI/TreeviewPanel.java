package GUI;

import Data.Node;
import Data.OSDependingData;
import Data.Settings;
import Data.Threading.NotifyingThread;
import Data.Threading.ThreadFinishedListener;
import Data.Threading.Threadmanager;
import com.sun.istack.internal.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static GUI.SunviewPanel.scale;

/**
 *
 * Created by Marcel on 06.03.2017.
 */
public class TreeviewPanel implements DataVisualizer {
    private JPanel rootPanel;
    private Tree tree;

    private Node clickedNode;

    private Treepopup treepopup;

    private boolean run=false;
    private BufferedImage bufferAnimation;
    private AnimationThread at;

    private class AnimationThread extends Thread{
        private int count=0;

        @Override
        public void run(){
            run=true;
            while(run) {
                if(Thread.interrupted()){
                    return;
                }
                bufferAnimation = new BufferedImage(rootPanel.getWidth(), rootPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = (Graphics2D) bufferAnimation.getGraphics();
                g.setColor(Color.darkGray);
                g.setFont(new Font("Arial", Font.BOLD, 35));
                String draw="Calculating";
                switch (count) {
                    case 0:
                        count++;
                        break;
                    case 1:
                        draw+=" . ";
                        count++;
                        break;
                    case 2:
                        draw+=" . . ";
                        count++;
                        break;
                    case 3:
                        draw+=" . . .";
                        count = 0;
                        break;
                }
                if(Thread.interrupted()){
                    return;
                }
                g.drawString(draw, rootPanel.getWidth() / 2 - 100, rootPanel.getHeight() / 2 - 70);
                g.dispose();
                if(Thread.interrupted()){
                    return;
                }
                rootPanel.repaint();
                if(Thread.interrupted()){
                    return;
                }

                try {
                    Thread.sleep(700);
                } catch (InterruptedException exit) {
                    return;
                }
            }

        }
    }

    private class Treepopup extends JPopupMenu{
        @Override
        public void show(Component c,int x,int y){
            if(tree!=null)
                clickedNode=tree.getNodeFromXY(x,y);
            super.show(c,x,y);
        }
    }

    private class TreeThread extends NotifyingThread{

        private int askedcount=0;
        private boolean finished=false;
        private Node own;
        private DefaultMutableTreeNode dfm;

        TreeThread(Node node, DefaultMutableTreeNode dfm){
            this.own=node;
            this.dfm=dfm;
        }

        @Override
        public void task(){
            try {
                if (own.getSubNodes().length > 0) {
                    addSubnodesToTreeInner(this.own, this.dfm);
                }
            }catch (Exception unexspected){
                unexspected.printStackTrace();
            }finally {
                finished = true;
            }
        }

        private boolean isFinished(){
            return askedcount > 300 || finished;
        }
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void createUIComponents() {

        rootPanel = new JPanel(new GridLayout(1, 1)){
            @Override
            public void paint(Graphics g){
                super.paint(g);
                int size;
                if (rootPanel.getWidth() == rootPanel.getHeight()) {
                    size = rootPanel.getWidth();
                } else if (rootPanel.getWidth() > rootPanel.getHeight()) {
                    size = rootPanel.getHeight();
                } else {
                    size = rootPanel.getWidth();
                }
                if(run){
                    g.drawImage(scale(bufferAnimation, size, (double) size / (double) bufferAnimation.getHeight()), 0, 0, null);
                }
            }
        };

        treepopup=new Treepopup();
        treepopup.setFont(GraphicsConstants.standardFont);
        JMenuItem m = new JMenuItem("Open in " + OSDependingData.getFileViewer());
        m.setFont(GraphicsConstants.standardFont);
        m.setHorizontalAlignment(JMenuItem.LEFT);
        m.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickedNode.openInOSFileviewer();
            }
        });
        treepopup.add(m);
        m = new JMenuItem("Detailed View ");
        m.setFont(GraphicsConstants.standardFont);
        m.setHorizontalAlignment(JMenuItem.LEFT);
        m.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickedNode.createDetailedViewWindow();
            }
        });
        treepopup.add(m);
        m = new JMenuItem("Analyze");
        m.setFont(GraphicsConstants.standardFont);
        m.setHorizontalAlignment(JMenuItem.LEFT);
        m.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mainframe.processNode(clickedNode);
            }
        });
        treepopup.add(m);
        rootPanel.setComponentPopupMenu(treepopup);

    }

    /***
     * Adds the subnodes and files of the given Node to the given DefaultMutableTreeNode.
     * When a subnode contains subnodes too these also get added.
     * @param node Node which subnodes should be added.
     * @param treeNode DefaultMutableTreeNode where the nodes should be added to.
     */
    private void addSubnodesToTree(Node node, DefaultMutableTreeNode treeNode) {
        // iterate through every subnode of the given node
        TreeThread[] threads = new TreeThread[node.getSubNodes().length];
        boolean[] processed=new boolean[node.getSubNodes().length];
        int count=0;
        if(Settings.multiThreadingTree) {
            for (Node n : node.getSubNodes()) {
                if (n.getUsagePercentOfParent() > 0.05) {
                    // create a DefaultMutableTreeNode object for the subnode
                DefaultMutableTreeNode newTreeNode = new DefaultMutableTreeNode(n);
                // add the DefaultMutableTreeNode to the upper tree node
                treeNode.add(newTreeNode);
                n.setTreePath(getPath(newTreeNode));
                // if the subnode contains subnodes add them too
                threads[count] = new TreeThread(n, newTreeNode);
                Threadmanager.addThreadTree(threads[count]);
                processed[count]=true;
                // add the information about the files in the subnode
                count++;
                }

            }
            count=0;
            for(Node n:node.getSubNodes()){
                if(!processed[count]) {
                    // create a DefaultMutableTreeNode object for the subnode
                    DefaultMutableTreeNode newTreeNode = new DefaultMutableTreeNode(n);
                    // add the DefaultMutableTreeNode to the upper tree node
                    treeNode.add(newTreeNode);
                    n.setTreePath(getPath(newTreeNode));
                    // if the subnode contains subnodes add them too
                    addSubnodesToTreeInner(n, newTreeNode);
                    // add the information about the files in the subnode
                    addFilesToTree(n, newTreeNode);
                }
                count++;
            }
        }else{
            for(Node n:node.getSubNodes()){
                // create a DefaultMutableTreeNode object for the subnode
                DefaultMutableTreeNode newTreeNode = new DefaultMutableTreeNode(n);
                // add the DefaultMutableTreeNode to the upper tree node
                treeNode.add(newTreeNode);
                n.setTreePath(getPath(newTreeNode));
                // if the subnode contains subnodes add them too
                addSubnodesToTreeInner(n,newTreeNode);
                // add the information about the files in the subnode
                addFilesToTree(n, newTreeNode);
            }
        }
        boolean flag=true;
        while(flag) {
            //Checking if interrupted so thread stops
            if(Thread.interrupted()) {
                return;
            }
            flag=false;
            for (TreeThread t : threads) {
                if (t!=null&&!t.isFinished()) {
                    if(t.getState()== Thread.State.TERMINATED){
                        System.out.println("Thread teriminated but did not finish!");
                    }
                    flag=true;
                    System.out.println(t.own.getName());
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    /***
     * Adds the subnodes and files of the given Node to the given DefaultMutableTreeNode.
     * When a subnode contains subnodes too these also get added.
     * @param node Node which subnodes should be added.
     * @param treeNode DefaultMutableTreeNode where the nodes should be added to.
     */
    private void addSubnodesToTreeInner(Node node, DefaultMutableTreeNode treeNode) {
        //Checks if thread is interrupted and will break if thread is interrupted
        if(Thread.interrupted()){
            return;
        }
        // iterate through every subnode of the given node
        for (Node n : node.getSubNodes()) {
            // create a DefaultMutableTreeNode object for the subnode
            DefaultMutableTreeNode newTreeNode = new DefaultMutableTreeNode(n);
            // add the DefaultMutableTreeNode to the upper tree node
            treeNode.add(newTreeNode);
            n.setTreePath(getPath(newTreeNode));
            // if the subnode contains subnodes add them too
            if (n.getSubNodes().length > 0) {
                addSubnodesToTreeInner(n, newTreeNode);
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
        treeNode.add(new DefaultMutableTreeNode(filesCount + " Files with size " + Node.sizeFormated(sizeOfFiles)));
    }

    @Override
    public void displayClaculatingMesssage() {
        rootPanel.removeAll();
        rootPanel.revalidate();
        rootPanel.repaint();
        at=new AnimationThread();
        at.start();
    }

    @Override
    public void disable() {
        run=false;
        for(Component j:rootPanel.getComponents()){
            j.setEnabled(false);
        }
    }

    @Override
    public void enable() {
        for(Component j:rootPanel.getComponents()){
            j.setEnabled(true);
        }
    }

    /**
     * Expands the tree so the given node's TreeNode is visible and expanded
     * @param node the node that's TreeNode will be visible and expanded in the tree
     */
    void expandPath(Node node) {
        if(node!=null){
        TreePath path = node.getTreePath();
        tree.expandPath(path);
        tree.scrollPathToVisible(path);
        }
    }

    /**
     * Returns the TreePath of the given TreeNode
     * @param treeNode the treenode to be checked
     * @return the treepath of the given treenode
     */
    private static TreePath getPath(TreeNode treeNode) {
        ArrayList<TreeNode> nodes = new ArrayList<>();
        if (treeNode != null) {
            nodes.add(treeNode);
            treeNode = treeNode.getParent();
            while (treeNode != null) {
                nodes.add(0, treeNode);
                treeNode = treeNode.getParent();
            }
        }

        return nodes.isEmpty() ? null : new TreePath(nodes.toArray());
    }

    /***
     * This method shows a JTree with the information from the given node.
     * @param node Node that should be shown.
     */
    void showNode(final Node node, @Nullable ThreadFinishedListener tfl) {
        NotifyingThread t=new NotifyingThread(){
            @Override
            public void task() {
                // clear rootPanel because there might be another JTree from an analysis before
                rootPanel.removeAll();
                rootPanel.revalidate();
                rootPanel.repaint();
                // create the first DefaultMutableTreeNode for the given supernode
                DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(node);
                // add the subnodes, their subnodes etc. and their files
                TreeviewPanel.this.addSubnodesToTree(node, treeNode);
                // add the files of the supernode
                TreeviewPanel.this.addFilesToTree(node, treeNode);
                // create the JTree with the DefaultMutableTreeNode of the supernode
                tree = new Tree(treeNode);
                tree.setFont(GraphicsConstants.standardFont);
                tree.setRowHeight(GraphicsConstants.treeRowHeight);
                tree.setComponentPopupMenu(treepopup);
                // it should be possible to scroll when the tree is too long
                JScrollPane scrollTree = new JScrollPane(tree);
                scrollTree.setViewportView(tree);

                //stopping animation
                try {
                    while (at != null && at.isAlive()) {
                        run = false;
                        at.interrupt();
                        if (!at.isAlive()) {
                            at = null;
                        }
                    }
                }catch (NullPointerException ignored){
                }

                // add the scrollable tree to the rootPanel
                rootPanel.add(scrollTree);
                rootPanel.validate();
            }
        };
        t.setListener(tfl);
        t.start();
    }

    /**
     * This method should be called to restore this panels default enabled state
     */
    void restore(){
        run=false;
        for(Component j:rootPanel.getComponents()){
            j.setEnabled(true);
        }
        at.interrupt();
        try {
            while (at != null && at.isAlive()) {
                run = false;
                at.interrupt();
                if (!at.isAlive()) {
                    at = null;
                }
            }
        }catch (NullPointerException ignored){
        }
        rootPanel.repaint();
    }
}
