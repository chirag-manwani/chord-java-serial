import java.util.ArrayList;

public class Node {
    private int nodeId;
    private ArrayList<Node> routingTable; 

    Node() {
        nodeId = 0;
    }

    Node(int nodeId) {
        this.nodeId = nodeId;
    }

    public Node findSuccessor(int id) {
        return null;
    }

    public Node findSuccessor() {
        return null;
    }

    public Node findPredecessor(int id) {
        return null;
    }

    public Node closestPrecedingFinger(int id) {
        return null;
    }
}