import java.util.*;

public class Task {
  int id, time, staff, cntPredecessors;
  String name;
  int earliestStart, latestStart, earliestFinish, latestFinish, slack;
  ArrayList<Task> outEdges = new ArrayList<Task>();
  ArrayList<Task> dependentOn = new ArrayList<Task>();
  String status = "unseen";
  boolean esefCalculated, lslfCalculated, critical = false;

  Task (){
  }

  public void taskSet(int i, String n, int t, int s, int cp){
    id = i;
    name = n;
    time = t;
    staff = s;
    cntPredecessors = cp;
  }

  public int getID(){
    return this.id;
  }

  public void addDependency(Task t){
    dependentOn.add(t);
  }

  //inedges
  public ArrayList<Task> getDependentOn(){
    return dependentOn;
  }

  //outedges
  public void addOutEdge(Task t){
    outEdges.add(t);
  }

  public ArrayList<Task> getOutEdges(){
    return outEdges;
  }
}
