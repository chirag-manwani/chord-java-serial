import java.util.ArrayList;
import java.math.BigInteger;

public class Node {
    private String nodeName;
    private BigInteger nodeId;
    private ArrayList<Finger> fingerTable;

    Node(String nodeName) {
        this.nodeName = nodeName;
        this.fingerTable = new ArrayList<>(Chord.m);

        String hash = Util.hash(nodeName);
        this.nodeId = new BigInteger(hash, 16);
        // System.out.println(hash);
    }

    public BigInteger getNodeId() {
        return nodeId;
    }

    public Node findSuccessor() {
        return fingerTable.get(0).node;
    }
    
    public Node findSuccessor(int id) {
        Node pred = findPredecessor(id);
        return pred.findSuccessor();
    }

    public Node findPredecessor(int id) {
        return null;
    }

    public Node closestPrecedingFinger(int id) {
        return null;
    }

    public void join(Node n) {

    }

    private void initFingerTable(Node n) {

    }

    private void updateOthers() {

    }

    private void updateFingerTable(Node s, int i) {

    }

    private void stabilize() {

    }

    private void notify(Node n) {

    }

    private void fixFingers() {

    }
}