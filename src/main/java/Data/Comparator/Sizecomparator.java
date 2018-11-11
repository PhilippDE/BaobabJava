package main.java.Data.Comparator;

import main.java.Data.Node;

import java.util.Comparator;

/**
 * Sizecomparator for Nodes; The first Node is the smallest
 * Created by Marcel on 06.03.2017.
 */
public class Sizecomparator implements Comparator<Node>{
    @Override
    public int compare(Node o1, Node o2) {
        if(o1.getSize()>o2.getSize())
            return 1;
        else if(o1.getSize()<o2.getSize())
            return -1;
        return 0;
    }
}
