import java.util.ArrayList;
import java.math.BigInteger;

public class Node {
    private static ArrayList<BigInteger> shift;
    public static int m;

    static {
        m = 7;
        shift = new ArrayList<>();
        shift.add(BigInteger.ONE);
        BigInteger TWO = new BigInteger("2", 10);
        for(int i=1; i<=m; ++i) {
            shift.add(shift.get(i-1).multiply(TWO));
        }
    }

    private String nodeName;
    private BigInteger nodeId;
    private ArrayList<Finger> fingerTable;
    private Node pred;

    Node(String nodeName) {
        String hash = Util.hash(nodeName);

        this.nodeName = nodeName;
        this.nodeId = new BigInteger(hash, 16);
        this.fingerTable = new ArrayList<>();

        for(int i=0; i<m; ++i) {
            fingerTable.add(new Finger());
            fingerTable.get(i).start = nodeId.add(shift.get(i)).mod(shift.get(m));
        }
    }

    Node(String nodeName, BigInteger nodeId) {
        this(nodeName);
        this.nodeId = nodeId;
        for(int i=0; i<m; ++i) {
            fingerTable.add(new Finger());
            fingerTable.get(i).start = nodeId.add(shift.get(i)).mod(shift.get(m));
        }
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

        while(!Util.in(id, predId, succId, 1)) {
            pred = pred.closestPrecedingFinger(id);
            predId = pred.nodeId;
            succId = pred.findSuccessor().nodeId;
        }
        return pred;
    }

    public Node closestPrecedingFinger(BigInteger id) {
        for(int i=m-1; i>=0; --i) {
            Finger f = fingerTable.get(i);
            BigInteger fId = f.node.getNodeId();
            if(Util.in(fId, nodeId, id, 0)) {
                return f.node;
            }
        }
        return this;
    }

    public void join(Node n) {
        if(n == null) {
            for(int i=0; i<m; ++i) {
                fingerTable.get(i).node = this;
            }
            pred = this;
        }
        else {
            initFingerTable(n);
            updateOthers(n);
            // moveKeys();
        }
    }

    private void initFingerTable(Node n) {
        fingerTable.get(0).node = n.findSuccessor(fingerTable.get(0).start);
        pred = findSuccessor().pred;
        findSuccessor().pred = this;
        pred.fingerTable.get(0).node = this;

        for(int i=1; i<m; ++i) {
            BigInteger fStart = fingerTable.get(i).start;
            BigInteger prevId = fingerTable.get(i-1).node.nodeId;
            if(Util.in(fStart, nodeId, prevId, 2)) {
                fingerTable.get(i).node = fingerTable.get(i-1).node;
            }
            else {
                fingerTable.get(i).node = n.findSuccessor(fStart);
            }
        }
        // System.out.println("--------------------Newly added node --------------");
        // printFingerTable();
        // System.out.println("---------------------------------------------------");
    }

    private void updateOthers(Node n) {
        for(int i=0; i<m; ++i) {
            BigInteger id = nodeId.subtract(shift.get(i)).mod(shift.get(m));
            Node p = n.findPredecessor(id);
            if(p.nodeId.compareTo(nodeId) == 0) {
                continue;
            }
            p.updateFingerTable(this, i);
        }
    }

    private void updateFingerTable(Node s, int i) {
        BigInteger fId = fingerTable.get(i).node.nodeId;

        if(Util.in(s.nodeId, nodeId, fId, 2)) {
            fingerTable.get(i).node = s;
            if(pred.nodeId.compareTo(s.nodeId) != 0) {
                pred.updateFingerTable(s, i);
            }
        }
    }

    private void stabilize() {

    }

    private void notify(Node n) {

    }

    private void fixFingers() {

    }

    /*
    *   Utility Functions
    */

    public void printFingerTable() {
        System.out.println("\nRouting Table for " + nodeId);
        System.out.println("m\t\tstart\t\tNode");
        for(int i=0; i<m; ++i) {
            System.out.println(i + "\t\t" + fingerTable.get(i).start + "\t\t" + fingerTable.get(i).node.nodeId);
        }
    }

    /*
    * Getters and Setters
    */

    public String getName() {
        return this.nodeName;
    }

    public BigInteger getNodeId() {
        return nodeId;
    }
}