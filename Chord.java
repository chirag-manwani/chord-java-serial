import java.math.BigInteger;
import java.util.ArrayList;

public class Chord {
    // Some list for maintaining nodes
    // Some debug options
    // lookup(id)
    public ArrayList<Node> nodeList;

    Chord() {
        nodeList = new ArrayList<>();
    }

    public void addNode(String nodeName, BigInteger i){
        Node node = new Node(nodeName, i);

        if(nodeList.isEmpty()) {
            node.join(null);
        }
        else {
            node.join(nodeList.get(0));
        }
        nodeList.add(node);
    }

    public void removeNode(int nodeId) {

    }

    public void removeRandomNode() {
        // generate nodeID 
        // Use removeNode(nodeID)
    }
}