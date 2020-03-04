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
        ids.add(new BigInteger("4", 10));
        ids.add(new BigInteger("10", 10));
        ids.add(new BigInteger("11", 10));
        ids.add(new BigInteger("89", 10));
        ids.add(new BigInteger("100", 10));
        chord.addNode("Node-1", ids.get(0));
        // chord.addNode("Node-2", new BigInteger("1", 10));
        chord.addKeyVal("4", "4");
        chord.addKeyVal("0", "0");
        chord.addKeyVal("10", "10");
        chord.addKeyVal("9", "9");
        chord.addKeyVal("11", "11");
        chord.addKeyVal("59", "59");
        chord.addKeyVal("69", "69");
        chord.addKeyVal("100", "100");
        chord.addKeyVal("101", "101");

        for(int i=2; i<=numNodes; ++i) {
            String nodeName = "Node-" + i;
            chord.addNode(nodeName, ids.get(i-1));
        }
        for(int j=1; j<=chord.nodeList.size(); ++j) {
            chord.nodeList.get(j-1).printFingerTable();
        }

        // chord.addKeyVal("0", "0");
        // chord.addKeyVal("10", "10");
        // chord.addKeyVal("9", "9");
        // chord.addKeyVal("11", "11");
        // chord.addKeyVal("59", "59");
        // chord.addKeyVal("69", "69");
        // chord.addKeyVal("100", "100");
        // chord.addKeyVal("101", "101");

        chord.lookup("0");
        chord.lookup("10");
        chord.lookup("9");
        chord.lookup("11");
        chord.lookup("59");
        chord.lookup("69");
        chord.lookup("100");
        chord.lookup("101");

        chord.removeRandomNode();
        chord.removeRandomNode();
        chord.removeRandomNode();
        chord.removeRandomNode();
        // chord.lookup("11");
        BigInteger b = new BigInteger("4", 10);
        System.out.println("OUTPUT " + Util.in(b, b, b, 1));
    }
}