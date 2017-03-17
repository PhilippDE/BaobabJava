package Data;

import Data.Comparator.Sizecomparator;
import Data.Comparator.SizecomparatorInversed;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Marcel on 05.03.2017.
 */
public class Node{
    private final String name;
    private long size=-1;
    private final Node[] subNodes;
    private final File ownPath;
    private Color ownColor;
    private Node parent;


    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Node[] getSubNodes() {
        return subNodes;
    }

    public File getOwnPath() {
        return ownPath;
    }

    public Color getOwnColor(){return ownColor;}

    public void setOwnColor(Color ownColor){this.ownColor=ownColor;}

    public Node(File file){
        this.name=file.getName();
        this.subNodes=new Node[getSubdirectoryCount(file)];
        this.ownPath=file;
    }

    public Node(File file, Node parent) {
        this(file);
        this.parent = parent;
    }


    public void calculateSubnodes(){
        File[] subDirectories=getSubdirectories(this.ownPath);
        for(int i=0;i<subNodes.length;i++){
            subNodes[i]=new Node(subDirectories[i], this);
        }
        if(this.subNodes.length<1){
            this.size=getSizefromPath(this.ownPath);
        }else{
            for(int i=0;i<subNodes.length;i++){
                subNodes[i].calculateSubnodes();
            }
        }
    }

    public void calculateSize() {
        if (this.size == -1){
            this.size = 0;
            for (Node n : subNodes) {
                if (n.getSize() == -1) {
                     n.calculateSize();
                }
                this.size += n.getSize();
            }
            this.size += getSizeofFiles(this.ownPath);
        }
    }

    public static int getSubdirectoryCount(File f){
        try {
            return f.listFiles(File::isDirectory).length;
        }catch(NullPointerException ignored){
            return 0;
        }
    }

    public static File[] getSubdirectories(File f){
        return f.listFiles(file -> file.isDirectory());
    }

    public static long getSizefromPath(File f) {
        final AtomicLong size = new AtomicLong(0);
        Path path=f.toPath();
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    size.addAndGet(attrs.size());
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new AssertionError("walkFileTree will not throw IOException if the FileVisitor does not");
        }

        return size.get();
    }

    public static long getSizeofFiles(File f){
        long size=0;
        for(File cur:f.listFiles()){
            if(cur.isFile()){
                try {
                    size+=Files.size(cur.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return size;
    }

    /***
     * Checks how many files the folder contains.
     * @param folder Folder to get the count of files from.
     * @return Count of files in the given folder.
     */
    public static int getFilesCount(File folder) {
        int files = 0;
        for(File f : folder.listFiles()) {
            if(f.isFile()) {
                files++;
            }
        }
        return files;
    }

    public static void main(String[] args){
        JFileChooser fs = new JFileChooser(new File("c:\\documents"));
        fs.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fs.setDialogTitle("save");
        //fs.setFileFilter(new FileNameExtensionFilter("Image", "jpeg","png"));
        int returnVal = fs.showSaveDialog(null);
        switch (returnVal) {
            case JFileChooser.APPROVE_OPTION:
                File input = fs.getSelectedFile();
                if (input.exists()) {
                    Node node=new Node(input);
                    node.calculateSubnodes();
                    node.calculateSize();
                    for(Node n:node.getSubNodes()){
                        System.out.println(n.getOwnPath());
                    }
                    System.out.println(node.getSize());
                } else {
                }
                fs.setVisible(false);
                break;
            case JFileChooser.CANCEL_OPTION:
                fs.setVisible(false);
                break;
        }
    }

    public void sortNodesSize(){
        Sizecomparator sc=new Sizecomparator();
        Arrays.sort(subNodes,sc);
        for(Node n:subNodes){
            n.sortNodes(sc);
        }
    }

    public void sortNodes(Comparator sc){
        Arrays.sort(subNodes,sc);
        if(subNodes.length!=0) {
            for (Node n : subNodes) {
                n.sortNodes(sc);
            }
        }else{
            return;
        }
    }

    public void sortNodesSizeReversed() {
        SizecomparatorInversed sc=new SizecomparatorInversed();
        Arrays.sort(subNodes,sc);
        for(Node n:subNodes) {
            n.sortNodes(sc);
        }
    }

    public double getUsagePercentOfParent() {
        if(parent == null) return 1;
        long parentSize = parent.getSize();
        long ownSize = getSize();
        return (double)ownSize/parentSize;
    }

    /***
     * Needed for the DefaultMutableTreeNodes in TreeviewPanel.
     * @return Name to display for the tree.
     */
    @Override
    public String toString() {
        return getName();
    }
}
