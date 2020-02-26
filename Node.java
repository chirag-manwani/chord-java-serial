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

    public Node findSuccessor(BigInteger id) {
        Node pred = findPredecessor(id);
        return pred.findSuccessor();
    }

    public Node findPredecessor(BigInteger id) {
        Node pred = this;
        BigInteger predId = pred.nodeId;
        BigInteger succId = pred.findSuccessor().nodeId;
        while(!Util.in(id, predId, succId)) {
            pred = closestPrecedingFinger(id);
            predId = pred.nodeId;
            succId = pred.findSuccessor().nodeId;
        }

        return pred;
    }

    public Node closestPrecedingFinger(BigInteger id) {
        for(int i=Chord.m-1; i>=0; --i) {
            Finger f = fingerTable.get(i);
            BigInteger fId = f.node.getNodeId();
            if(Util.in(fId, nodeId, id)) {
                return f.node;
            }
        }
        return this;
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