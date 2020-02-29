import java.math.BigInteger;
import java.util.ArrayList;

public class Chord {
    ArrayList<Node> nodeList;

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

    public void removeNode(Node nr) {
        // Node nr = nodeList.get(nodeName);
        Node s = nr.findSuccessor();
        Node p = nr.findPredecessor();

        System.out.println("Removing " + nr.getNodeId());
        nr.leave(s);
        s.setPredecessor(p);
        p.setSuccessor(s);
    }

    public void removeRandomNode() {
        // generate nodeID
        // Use random idx
        int idx = Util.getRandomNumberInRange(0, nodeList.size()-1);
        Node nr = nodeList.get(idx);
        removeNode(nr);
        nodeList.remove(idx);
        System.out.println("Nodes in Chord " + nodeList.size());
    }

    public void addKeyVal(String key, String val) {
        // BigInteger hash = new BigInteger(Util.hash(key), 16);
        BigInteger id = new BigInteger(key, 10);
        int idx = 0;
        Node n = nodeList.get(idx);
        ArrayList<Node> path = new ArrayList<>();
        Node s = n.findSuccessor(id, path);
        s.addKeyVal(key, val);
    }

    public void lookup(String key) {
        // BigInteger id = new BigInteger(Util.hash(key), 16);
        BigInteger id = new BigInteger(key, 10);
        Node succ = lookup(id);
        String value = succ.find(key);
        System.out.println(succ.getNodeId() + ".map[key] = " + value);
    }

    public Node lookup(BigInteger id) {
        ArrayList<Node> path = new ArrayList<>();
        // Use random idx
        int idx = Util.getRandomNumberInRange(0, nodeList.size()-1);
        Node n = nodeList.get(idx);
        Node s = n.findSuccessor(id, path);

        System.out.print("Lookup " + id + " : ");
        for(int i=0; i<path.size()-1; ++i) {
            Node pathNode = path.get(i);
            System.out.print(pathNode.getNodeId() + " -> ");
        }
        System.out.println(path.get(path.size()-1).getNodeId());
        return s;
    }
}