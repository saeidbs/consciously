import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * Created by saeidbahmani on 12/30/18.
 */
public class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node a,Node b ) {

        if(a.getTotalCost()<b.getTotalCost()){
            return -1;

        }
        if(a.getTotalCost()>b.getTotalCost()){
            return 1;
        }
        return 0;
    }
}
