public class Main {
    private static boolean debug = false;
    private static int numData = 10000;
    public static void main(String args[]) {
        if(args.length < 2) {
            System.out.println("Need two arguments");
            return;
        }

        int numNodes = Integer.parseInt(args[0]);
        if(args[1].compareTo("on") == 0) {
            debug = true;
        }

        experiment(numNodes, debug);
    }

    public static void experiment(int numNodes, boolean debug) {
        Chord chord = new Chord();
        for(int i=0; i<numNodes; ++i) {
            String nodeName = "Node-" + i;
            chord.addNode(nodeName);
        }
        for(int i=1; i<=numData; ++i) {
            String key = "Key-" + i;
            String val = "Value-" + i;
            chord.addKeyVal(key, val);
        }
    }
}