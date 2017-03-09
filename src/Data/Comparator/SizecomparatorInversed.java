package Data.Comparator;

import Data.Node;

import java.util.Comparator;

/**
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
