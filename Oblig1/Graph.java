import java.util.*;
import java.util.ArrayList;


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
                nodes[tilfeldig1].addSuccessor(nodes[tilfeldig2]);
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
        //1A
        counter = 0;
        for(Node n : nodes){
            if(!n.isVisited()){
                counter++;
                DFS(n);
            }
        }
        return counter;
    }

    public Graph transformDirToUndir(){
        //oppgave 1B
        
        Node[] newNodes = new Node[nodes.length];
        int counter1 = 0;

        for(Node n : nodes){
            Node newNode = new Node(n.getLable());
            newNodes[counter1++] = newNode;
        }

        for(int i = 0; i < newNodes.length; i++){
            Node n = nodes[i];
            Node newN = newNodes[i];
            List<Node> naboliste = n.getNeighbors();

            for(Node nabo : naboliste){
                int pos = nabo.getLable();
                Node nyNabo = newNodes[pos];
                nyNabo.addNeighbor(newN);
            }
        }

        Graph newGraph = new Graph(newNodes);
        return newGraph; //returnerer ny
    }

    public boolean isConnected(){
        //oppgave 1C
        Graph graph = this.transformDirToUndir();

        if(graph.numberOfComponents() <= 1){
            return true;
        }
        return false; //for at prekoden skal kompielere
    }

    public Graph biggestComponent(){
        //oppgave 1D
        //hopper over denne grunnet ferdig oblig 0
        
        return null; //for at prekoden skal kompilere
    }

    public int[][] buildAdjancyMatrix(){
        //oppgave 1E

        int adjancyMatrix[][] = new int[nodes.length][nodes.length];
        int[] representasjon = new int[nodes.length];

        for(int i = 0; i < nodes.length; i++){
            representasjon[i] = nodes[i].getLable();
        }
        
        for(int j = 0; j < nodes.length; j++){
            List<Node> nabo = nodes[j].getNeighbors();
            for(Node n : nabo){
                adjancyMatrix[j][n.getLable()] = 1; 
            }
        }

        for(int m = 0; m < nodes.length; m++){
            for(int n = 0; n < nodes.length; n++){
                if(adjancyMatrix[m][n] != 1){
                    adjancyMatrix[m][n] = 0;
                }
                System.out.print(adjancyMatrix[m][n]);
            }
            System.out.println();
        }

        return adjancyMatrix; 
    } 


    public static void main(String[] args){
        Graph graph = buildExampleGraph();
        
        graph = buildRandomSparseGraph(11, 201909202359L);
        System.out.println("Naboer Sparse: ");
        graph.printNeighbors();
        System.out.println("Antall komponenter: " + graph.numberOfComponents());
        
        //for oppgave 1A
        System.out.println("Naboer Dense: ");
        graph = buildRandomDenseGraph(15, 201909202359L);
        graph.printNeighbors();
        System.out.println("Antall komponenter: " + graph.numberOfComponents());
        Graph graph1 = buildRandomDirGraph(11, 201909202359L);
        System.out.println("Naboer Dir:");
        graph1.printNeighbors();
        System.out.println("Antall komponenter: " + graph1.numberOfComponents());

        //for oppgave 1B
        Graph ng = graph1.transformDirToUndir();
        System.out.println("Ny uretta graf: ");
        ng.printNeighbors();

        //for oppgave 1C
        System.out.println(ng.isConnected());
        System.out.println(graph1.isConnected());

        //for oppgave 1E
        ng.buildAdjancyMatrix();
        
        

    }
}