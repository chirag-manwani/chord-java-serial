import java.util.ArrayList;

public class Chord {
    // Some list for maintaining nodes
    // Some debug options
    // lookup(id)
    public static int m;
    private ArrayList<Node> nodeList;

    Chord() {
        m = 160;
        nodeList = new ArrayList<>();
    }

    public void addNode(String nodeName) {
        Node node = new Node(nodeName);

        if(nodeList.size() == 0) {
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