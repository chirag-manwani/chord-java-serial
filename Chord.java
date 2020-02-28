import java.math.BigInteger;
import java.util.HashMap;

public class Chord {
    HashMap<String, Node> nodeMap;

    Chord() {
        nodeMap = new HashMap<>();
    }

    public void addNode(String nodeName, BigInteger i){
        Node node = new Node(nodeName, i);

        if(nodeMap.isEmpty()) {
            node.join(null);
        }
        else {
            node.join(nodeMap.get("Node-1"));
        }
        nodeMap.put(nodeName, node);
    }

    public void removeNode(String nodeName) {
        Node nr = nodeMap.get(nodeName);
        Node s = nr.findSuccessor();
        Node p = nr.findPredecessor();

        s.moveKeys(nr, p.getNodeId().add(BigInteger.ONE), nr.getNodeId());

        s.setPredecessor(p);
        p.setSuccessor(s);


    }

    public void removeRandomNode() {
        // generate nodeID
        // Use removeNode(nodeID)
    }
}