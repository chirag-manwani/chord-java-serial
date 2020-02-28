import java.math.BigInteger;
import java.util.ArrayList;

public class Main {
    private static boolean debug = false;
    public static void main(String args[]) {
        Chord chord = new Chord();
        if(args.length < 2) {
            System.out.println("Need two arguments");
            return;
        }

        int numNodes = Integer.parseInt(args[0]);
        if(args[1].compareTo("on") == 0) {
            debug = true;
        }
        ArrayList<BigInteger> ids = new ArrayList<>();
        ids.add(new BigInteger("0", 10));
        ids.add(new BigInteger("1", 10));
        ids.add(new BigInteger("2", 10));
        ids.add(new BigInteger("3", 10));
        ids.add(new BigInteger("4", 10));
        ids.add(new BigInteger("5", 10));
        ids.add(new BigInteger("6", 10));
        ids.add(new BigInteger("7", 10));
        ids.add(new BigInteger("8", 10));
        ids.add(new BigInteger("9", 10));
        ids.add(new BigInteger("10", 10));
        ids.add(new BigInteger("11", 10));
        ids.add(new BigInteger("12", 10));
        for(int i=1; i<=numNodes; ++i) {
            String nodeName = "Node-" + i;
            System.out.println("Adding new node " + " " + ids.get(i-1));
            chord.addNode(nodeName, ids.get(i-1));
        }
        for(int j=1; j<=chord.nodeMap.size(); ++j) {
            String name = "Node-" + j;
            System.out.println("Routing Table for " + chord.nodeMap.get(name).getNodeId());
            chord.nodeMap.get(name).printFingerTable();
        }

        // Test Util.in() function
        // BigInteger n = new BigInteger("45", 10);
        // BigInteger l = new BigInteger("0", 10);
        // BigInteger u = new BigInteger("50", 10);

        // System.out.println(n + " " + l + " " + u);
        // System.out.println(Util.in(l, n, u));

    }
}