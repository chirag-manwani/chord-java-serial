import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

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
    private HashMap<String, String> map;

    Node(String nodeName) {
        String hash = Util.hash(nodeName);

        this.nodeName = nodeName;
        this.nodeId = new BigInteger(hash, 16);
        this.fingerTable = new ArrayList<>();

        for(int i=0; i<m; ++i) {
            fingerTable.add(new Finger());
            fingerTable.get(i).start = nodeId.add(shift.get(i)).mod(shift.get(m));
        }
        map = new HashMap<>();
    }

    Node(String nodeName, BigInteger nodeId) {
        this(nodeName);
        this.nodeId = nodeId;
        for(int i=0; i<m; ++i) {
            fingerTable.add(new Finger());
            fingerTable.get(i).start = nodeId.add(shift.get(i)).mod(shift.get(m));
        }
        map = new HashMap<>();
    }

    public Node findSuccessor() {
        return fingerTable.get(0).node;
    }

    public Node findSuccessor(BigInteger id, ArrayList<Node> path) {
        path.add(this);
        Node pred = findPredecessor(id, path);
        path.add(pred.findSuccessor());
        return pred.findSuccessor();
    }

    public Node findPredecessor() {
        return pred;
    }

    public Node findPredecessor(BigInteger id, ArrayList<Node> path) {
        Node pred = this;
        BigInteger predId = pred.nodeId;
        BigInteger succId = pred.findSuccessor().nodeId;

        while(!Util.in(id, predId, succId, 1)) {
            Node temp = pred.closestPrecedingFinger(id);
            if(temp.nodeId.compareTo(predId) == 0) {
                return pred;
            }
            pred = temp;
            predId = pred.nodeId;
            succId = pred.findSuccessor().nodeId;
            path.add(pred);
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
            updateOthers(n, false);
            int numKeys = moveKeys(findSuccessor(),
                                   pred.nodeId,
                                   nodeId);
            System.out.println("Moving Keys when adding " + numKeys);
        }
    }

    public void leave(Node s) {
        updateOthers(s, true);
        int numKeys = s.moveKeys(this,
                                 pred.nodeId,
                                 nodeId);
        // System.out.println("Moving Keys when adding " + numKeys);
    }

    private void initFingerTable(Node n) {
        // Path is a dummy variable here
        ArrayList<Node> path = new ArrayList<>();
        fingerTable.get(0).node = n.findSuccessor(fingerTable.get(0).start, path);
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
                fingerTable.get(i).node = n.findSuccessor(fStart, path);
            }
        }
    }

    private void updateOthers(Node n, boolean delete) {
        // Path is a dummy variable here
        ArrayList<Node> path = new ArrayList<>();
        for(int i=0; i<m; ++i) {
            BigInteger id = nodeId.subtract(shift.get(i)).mod(shift.get(m));
            Node p = n.findSuccessor(id, path);
            if(id.compareTo(p.nodeId) != 0) {
                p = n.findPredecessor(id, path);
            }

            if(delete) {
                p.updateFingerTableDel(this, i);
            }
            else{
                p.updateFingerTable(this, i);
            }
        }
    }

    private void updateFingerTable(Node s, int i) {
        BigInteger fId = fingerTable.get(i).node.nodeId;
        if(nodeId.compareTo(s.nodeId) == 0) {
            return;
        }
        if(Util.in(s.nodeId, nodeId, fId, 2)) {
            fingerTable.get(i).node = s;
            pred.updateFingerTable(s, i);
        }
    }

    private void updateFingerTableDel(Node s, int i) {
        BigInteger fId = fingerTable.get(i).node.nodeId;
        if(nodeId.compareTo(s.nodeId) == 0) {
            return;
        }
        if(fId.compareTo(s.nodeId) == 0) {
            fingerTable.get(i).node = s.findSuccessor();
            pred.updateFingerTableDel(s, i);
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

    public int moveKeys(Node from, BigInteger l, BigInteger u) {
        int numKeys = 0;
        HashMap<String, String> newMap = new HashMap<>();
        for(String key : from.map.keySet()) {
            // BigInteger hash = new BigInteger(key, 16);
            BigInteger hash = new BigInteger(key, 10);
            if(Util.in(hash, l, u, 1)) {
                System.out.println("Moving " + key + " from " + from.nodeId + " to " + nodeId);
                map.put(key, from.map.get(key));
                ++numKeys;
            }
            else {
                newMap.put(key, from.map.get(key));
            }
        }
        from.map = newMap;
        return numKeys;
    }

    public void printFingerTable() {
        System.out.println("\nRouting Table for " + nodeId);
        System.out.println("m\t\tstart\t\tNode");
        for(int i=0; i<m; ++i) {
            System.out.println(i + "\t\t" + fingerTable.get(i).start + "\t\t" + fingerTable.get(i).node.nodeId);
        }
    }

    public String find(String key) {
        if(map.containsKey(key)) {
            return map.get(key);
        }
        else {
            return "Key not Found";
        }
    }

    public void addKeyVal(String key, String val) {
        System.out.println("Adding " + key + " to " + nodeId);
        map.put(key, val);
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

    public void setSuccessor(Node succ) {
        fingerTable.get(0).node = succ;
    }

    public void setPredecessor(Node pred) {
        this.pred = pred;
    }
}