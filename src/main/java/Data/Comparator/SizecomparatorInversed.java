package main.java.Data.Comparator;

import main.java.Data.Node;

import java.util.Comparator;

/**
 * Sizecomparator for Node; Nodes will be sorted reversed where the first is the largest Node
 * Created by Marcel on 09.03.2017.
 */
public class SizecomparatorInversed implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        if(o1.getSize()>o2.getSize())
        return -1;
        else if(o1.getSize()<o2.getSize())
        return 1;
        return 0;
        }
}
