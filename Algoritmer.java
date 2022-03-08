import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.management.Query;

import com.sun.javafx.tk.Toolkit.Task;
import com.sun.org.apache.regexp.internal.recompile;

import org.w3c.dom.Node;

public class Algoritmer {
    private void sortTopologically() {

        //prioritetskø sortert på minDistanse til hverandre
        PriorityQueue<Task> queue = new PriorityQueue<Task>();

        for(Task t : tasks){
            if(!t.hasDependencies()){
                queue.add(t);
            }
        }

        while(!queue.isEmpty()){
            Task tmp = queue.remove(0);

            topSort.add(tmp);

            for(Edge edge = tmp.getOutedges(); edge != null; edge = edge.next){
                Task neighbour = edge.getTaskto();

                if(neighbour.decreasePredecessors() == 0){
                    queue.add(neighbour);
                }
            }
        }
    }

    public void shortestPathFrom(Node source){
        PriorityQueue<Node> queue = new PriorityQueue<Node>();

        queue.add(source);

        while(!queue.isEmpty()){
            Node node = queue.get(0);

            for(Edge e: node.adjlist){
                Node n = e.target();

                int weight = e.weight;
                int distanceThroughN = n.distance + weight;

                if(distanceThroughN < n.minDistanse){
                    queue.remove(n);
                    n.minDistanse = distanceThroughN;
                    n.previous = node;
                    queue.add(n);
                }

            }
        }
    }

    public void MinimalSpanningTree(Node source){
        PriorityQueue<Node> queue = new PriorityQueue<Node>();

        queue.add(source);

        while(!queue.isEmpty()){
            Node node = queue.get(0);

            for(Edge e : node.adjlist){
                Node n = e.target;

                int weight =   e.weight;

                if(weight < n.minDistanse){
                    queue.remove(n);
                    n.minDistanse = weight;
                    n.previous = node;
                    queue.add(n);
                }
            }
        }
    }

    //DFS for biconnectivity
    public void assignLow(Node v){

        v.low = v.number;

        for(Node w : v.neighbours){
            if(w.number > v.number){
                if(w.low >= v.number){
                    System.out.println("Articulation point");
                }
                v.low = min(v.low, w.low);
            } else {
                if(v.parent != w){
                    v.low = min(v.low, w.number);
                }
            }
        }

    }

    public Node merge(Node x, Node y){
        if(x == null) return y;
        if (y == null) return x;

        if(x.element.compareTo(y.element) > 0){
            Node tmp = x;
            x = y;
            y = tmp;
        }

        x.rightChild = merge(x.rightChild, y);

        if(x.leftChild == null){
            x.leftChild = x.rightChild;
            x.rightChild = null;
            x.s = 1;
        } else {
            if(x.leftChild.s < x.rightChild.s){
                Node temp = x.leftChild;
                x.leftChild = x.rightChild;
                x.rightChild = temp;
            }

            x.s = x.rightChild.s +1;
        }
        return x;
    }

    private void dijkstra(Node start){
        //1. sette avstand til alle noder lik uendelig
        for(Node n : nodeliste){
            n.avstand = Integer.MAX_VALUE;
        }
        start.avstand = 0; 

        //2. lage en PQ eller liste der Nodene sorteres etter avstand
        //legg til startnoden i listen
        ArrayList<Node> pq = new ArrayList<Node>();

        for(Node n : nodeliste){
            pq.add(n);
        }

        while(!pq.isEmpty()){
            Node tattUt = pq.remove(0);

            for(Kant k : tattUt.listeKanter){
                Node nabo = k.til;
                int avstand = tattUt.avstand + k.vekt;

                if(nabo.avstand > avstand){
                    nabo.avstand = avstand;

                    Collections.sort(pq);
                }
            }
        }
        for(Node n : naboliste){
            System.out.pritln(n);
        }
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    public boolean isVisited() {
        return visited;
    }

    public void visit() {
        visited = true;
    }

    private void DFS(Node n){
        s.visit();
        for(Node node : s.getNeigbourhs()){
            if(!node.isVisited()){
                this.DFS(node);
            }
        }
    }

    private void DFSfull(){
        int counter = 0;
        for(Node n : nodes){
            if(!n.isVisited()){
                counter++;
                this.DFS(n);
            }
        }
    }

    //telling av komponenter/finne ut om en graf er sammenhengende
    private int antComp(){
        int counter = 0;
        for(Node n : nodes){
            if(!n.isVisited()){
                counter++;
                DFS(n);
            }
        }
        return counter;
    }

    //gjøre om en rettet graf til en urettet graf
    public Graph transDirToUndir(){
        Node[] newNodes = new Node[nodes.length];
        int counter = 0; 

        for(Node n : nodes){
            Node newNode = new Node(n.getLabel());
            newNodes[counter++] = newNode;
        }

        for (int i = 0; i < newNodes.length; i++) {
            Node n = nodes[i];
            Node newN = newNodes[i];
            List<Node> naboliste = n.getNeighbors();

            for (Node nabo : naboliste) {
                int pos = nabo.getLable();
                Node nyNabo = newNodes[pos];
                nyNabo.addNeighbor(newN);
            }
        }

        Graph newGraph = new Graph(newNodes);
        return newGraph; // returnerer ny

    }

    class BSTree {
        class Node {
            Node node;
            Node left;
            Node right;
            int data;

            Node(int data) {
                this.data = data;
            }
        }

        Node root;

        public Node get(int i) {
            Node trav = root;
            
            while(true){
                if(trav.data == i){
                    return trav;
                }

                if(i < trav.data){
                    if(trav.left != null){
                        trav = trav.left;
                        continue;
                    } else {
                        return null;
                    }
                } else {
                    if(trav.right != null){
                        trav = trav.right;
                        continue;
                    } else {
                        return null;
                    }
                }
            }
        }

        public void add(int data) {
            System.out.println("add + (" + data + ")");
            if(root == null){
                root = new Node(data);
                return; 
            } 

            Node trav = root;

            while(true){
                if(data < trav.data){
                    if(trav.left != null){
                        trav = trav.left;
                        continue;
                    } else {
                        trav.left = new Node(data);
                        return;
                    }
                } else {
                    if(trav.right != null){
                        trav = trav.right;
                        continue;
                    } else {
                        trav.right = new Node(data);
                        return;
                    }
                }
            }

            
        }
    }

    private topologicalSort(Graph g){
        PriorityQueue<> queue = new PriorityQueue<>();

        //for each vertex u in G
        for(){
            incounter[u] = indeg[u];
            if(incounter[u] == 0){
                queue.push(u);
            }
        }
        int i = 1;

        while(!queue.isEmpty()){
            int u = queue.pop();

            i = i+1;

        }

        if(i > n){
            return "digraph has directed cycle";
        }
    }

    //finner en verdi i treet hvis den er der, pluss at den legger til stien
    void writePathToNode(int value, Node n, String s) {
        if (n == null) {
            System.out.println("Verdien " + n + " finnes ikke i treet");
        }
        else if (n.v == v) {
            System.out.println(s + " " + v);
        }
        else if (n.v < v) {
            findPath(v, node.r, s = s + "R");
        }
        else if(n.v > v){
            findPath(v, node.l, s = s + "L");
        }
    }

    //fjerner alle noder med mindre verdi enn value fra treet
    Node removeLessThan(Node n, int value){
        if(n == null){return null;}

        if(value <= n.v){
            n.l = removeLessThan(n.l, value);
            return n;
        } else {
            removeLessThan(n.r, value);
        }
    }

    //setter et element inn i hashtabellen
    boolean put(Element e){
        int i = e.k % hashTable.length;
        int firsti = i;

        while(hashTable[i] != null){
            if(((Element)hashTable[i]).k == e.k){
                //element med samme nøkkel lå der fra før
                //skifter med ny
                hashTable[i] = e;
            }
            i = (i+1) % hashTable.length;
            //sjekker at vi nå ikke har kommet helt rundt
            if(i == firsti) return false;
        }
        hashTable[i] = e;
        return true;
    }

    //sjekker om det finnes sykler i grafen
    boolean isCyclic(int v, boolean visited[], int parent){
        visited[v] = true;
        Integer i;

        Iterator<Integer> it = adj[v].iterator();

        while(it.hasNext()){
            i = it.next();
            if(!visited[i]){
                if(isCyclic(i, visited, v)){
                    return true;
                }
            } else if (i != parent){
                return true;
            }
        }
        return false;
    }

    //returns true if grpah is a tree
    boolean isTree(){
        Boolean isVisited[] = new Boolean[V];

        for(int i = 0; i < V; i++){
            visited[i] = false;
        }
        if(isCyclic(0, visited, -1)){
            return false;
        }

        for(int u = 0; u <V; u++){
            if(!visited[u]){
                return false;
            }
        }
        return true;
    }

    //topologisk sortering med modifikasjon
    int numberOfSemesters(){
        int inDegree[] = new int[V];

        for(int i = 0; i < V; i++){
            LinkedList<Integer> temp = (LinkedList<Integer>)adj[i];
            for(int node : temp){
                inDegree[node]++;
            }
        }
        Queue<Integer> q = new LinkedList<Integer>();
        for(int i = 0; i < V; i++){
            if(inDegree[i] == 0){
                q.add(i);
            }
        }

        int semesters = 0;
        while(!q.isEmpty()){
            int size = q.size();
            for(int i = 0; 3 > i && i <size; i++){
                int v = q.poll();
                for(int node: adj[v]){
                    if(--inDegree[node] == 0){
                        q.add(node);
                    }
                }
            }
            semesters++;
        }
        return semesters;
    }

    int[] hashArray, tableSize;

    Hash (int size){
        tableSize = size;
        hashArray = new int[tableSize];
        for(int i = 0; i < tableSize; i++){
            hashArray[i] = -1;
        }
    }
    public void insert(int key){
        hashArray[hash(key)] = key;
        currNr++;
        if(reHashNedded()) reHash();
    }
    
    public int get(int key){
        int index = key % tableSize;
        int count = 0;

        while(hashArray[key] != key){
            index += linearProbing(count++);
            index += quadricProbing(count++);
            index += hash2(key, count++);
        }
        return hashArray[index];
    }

    public int remove(int key){
        int index = key % tableSize;
        int count = 0;
        while (hashArray[key] != key) {
            index += linearProbing(count++);
            index += quadricProbing(count++);
            index += hash2(key, count++);
        }
        int removed = hashArray[index];
        hashArray[index] = -1;
        return removed;
    }

    int hash(int key){
        int index = key % tableSize;
        int count = 0;

        while (isOccupied(index)) {
            index += linearProbing(count++);
            index += quadricProbing(count++);
            index += hash2(count++);
        }
        return index;
    }

    private int hash2(int key, int count){
        int r = getLargestPrime();
        return count * (r- (key % r));
    }
    private int getLargestPrime(){
        int prime = 0;
        for(int i = 0; i < tableSize; i++){
            if(isPrime(i)){
                prime = i;
            }
        }
        return prime;
    }

    boolean isPrime(int n){
        for(int i = 2; 2 * i < n; i++){
            if(n%i == 0){
                return false;
            }
        }
        return true;
    }

    private int linearProbing(int count){
        return count;
    }

    private int quadricProbing(int count){
        return count * count;
    }

    private boolean reHashNedded(){
        return ((double) currNr / tableSize > 0.75);
    }

    private void reHash(){
        int[] newHashArray = new int[hashArray.size * 2];

        int[] oldHashArray = hashArray;
        hashArray = newHashArray;
        tableSize = tableSize*2;
        for(int i = 0; i < oldHashArray.length; i++){
            insert(oldHashArray[i]);
        }
    }


}