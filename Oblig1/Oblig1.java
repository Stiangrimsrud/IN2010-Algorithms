import java.util.*;


class Node {
    
    private int label;
    private boolean visited = false;
    private List<Node> neighbors = new LinkedList<Node>();

    public Node(int label){
        this.label = label;
    }

    public int getLable(){
        return label;
    }

    public List<Node> getNeighbors(){
        return neighbors;
    }

    public boolean isVisited(){
        return visited;
    }

    public void visit(){
        visited = true;
    }

    public void addNeighbor(Node n){
        //legger til en uretta kant fra this til n
        if(!neighbors.contains(n)){
            neighbors.add(n);
            n.addNeighbor(this);
        }
    }

    public void addSuccessor(Node n){
        //legger til en retta kant fra this til n
        if(!neighbors.contains(n)){
            neighbors.add(n);
        }
    }

    public String toString(){
        return Integer.toString(label);
    }
}

class Graph{
    private Node[] nodes;
    public int counter; 

    public Graph(Node[] nodes){
        this.nodes = nodes;
    }

    public void printNeighbors(){
        for(Node n1 : nodes){
            String s = n1.toString() + ": ";
            for(Node n2 : n1.getNeighbors()){
                s += n2.toString() + " ";
            }
            System.out.println(s.substring(0, s.length() - 1));
        }
    }

    private static Graph buildExampleGraph(){
        //ukeoppgave
        Node[] nodes = new Node[7];
        for(int i = 0; i < 7; i++){
            nodes[i] = new Node(i);
        }
        nodes[0].addNeighbor(nodes[1]);
        nodes[0].addNeighbor(nodes[2]);
        nodes[1].addNeighbor(nodes[2]);
        nodes[2].addNeighbor(nodes[3]);
        nodes[2].addNeighbor(nodes[5]);
        nodes[3].addNeighbor(nodes[4]);
        nodes[4].addNeighbor(nodes[5]);
        nodes[5].addNeighbor(nodes[6]);

        return new Graph(nodes);
    }

    private static Graph buildRandomSparseGraph(int numberofV, long seed){
        //seed brukes av java.util.Random for å generere samme sekvens for samme frø
        //(seed) og numberofV
        java.util.Random tilf = new java.util.Random(seed);
        int tilfeldig1 = 0, tilfeldig2 = 0;
        Node[] nodes = new Node[numberofV];

        for(int i = 0; i < numberofV; i++){
            nodes[i] = new Node(i);
        }

        for(int i = 0; i < numberofV; i++){
            tilfeldig1 = tilf.nextInt(numberofV);
            tilfeldig2 = tilf.nextInt(numberofV);
            if(tilfeldig1 != tilfeldig2){
                nodes[tilfeldig1].addNeighbor(nodes[tilfeldig2]);
            }
        }
        return new Graph(nodes);
    }

    private static Graph buildRandomDenseGraph(int numberofV, long seed){
        java.util.Random tilf = new java.util.Random(seed);
        int tilfeldig1 = 0, tilfeldig2 = 0;
        Node[] nodes = new Node[numberofV];

        for(int i = 0; i < numberofV; i++){
            nodes[i] = new Node(i);
        }

        for(int i = 0; i < numberofV * numberofV; i++){
            tilfeldig1 = tilf.nextInt(numberofV);
            tilfeldig2 = tilf.nextInt(numberofV);
            if(tilfeldig1 != tilfeldig2){
                nodes[tilfeldig1].addNeighbor(nodes[tilfeldig2]);
            }
        }
        return new Graph(nodes);
    }

    private static Graph buildRandomDirGraph(int numberofV, long seed){
        java.util.Random tilf = new java.util.Random(seed);
        int tilfeldig1 = 0, tilfeldig2 = 0;
        Node[] nodes = new Node[numberofV];

        for(int i = 0; i < numberofV; i++){
            nodes[i] = new Node(i);
        }

        for(int i = 0; i < 2 * numberofV; i++){
            tilfeldig1 = tilf.nextInt(numberofV);
            tilfeldig2 = tilf.nextInt(numberofV);
            if(tilfeldig1 != tilfeldig2){
                nodes[tilfeldig1].addNeighbor(nodes[tilfeldig2]);
            }
        }
        return new Graph(nodes);
    }

    public void DFS(Node s){
        s.visit();
        for(Node n : s.getNeighbors()){
            if(n.isVisited() == false){

                this.DFS(n);
            }
        }
    }

    public void DFSFull(){
        for(Node n : nodes){
            if(n.isVisited() == false){
                this.DFS(n);
            }
        }
    }

    public int numberOfComponents(){
        counter = 0;
        for(Node n : nodes){
            if(!n.isVisited()){
                counter++;
                DFS(n);
            }
        }
        System.out.println("Antall komponenter: " + counter);
        return -1;
    }

    public Graph transformDirToUndir(){
        //oppgave 1B
        //TODO
        return null; //returnerer en NY graf
    }

    public boolean isConnected(){
        //oppgave 1C
        //TODO
        return false; //for at prekoden skal kompielere
    }

    public Graph biggestComponent(){
        //oppgave 1D
        //TODO
        return null; //for at prekoden skal kompilere
    }

    public int[][] buildAdjancyMatrix(){
        //oppgave 1E
        //TODO
        return null; //for at koden skal kompilere
    } 

    public static void main(String[] args){
        Graph graph = buildExampleGraph();
        graph = buildRandomSparseGraph(11, 201909202359L);
        System.out.println("Naboer Sparse: ");
        graph.printNeighbors();
        graph.numberOfComponents();
        System.out.println("Naboer Dense: ");
        graph = buildRandomDenseGraph(15, 201909202359L);
        graph.printNeighbors();
        graph.numberOfComponents();
        Graph graph1 = buildRandomDirGraph(11, 201909202359L);
        System.out.println("DirGraph:");
        graph1.printNeighbors();
        graph1.numberOfComponents();
       
    }
}