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
        
        chord.addNode("Node-1");
        chord.addNode("Node-2");
    }
}