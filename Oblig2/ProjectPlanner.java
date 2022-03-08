import java.io.FileNotFoundException;
import java.io.*;
import java.util.*;

public class ProjectPlanner {

  int tasksNr, projectTime;
  ArrayList<Task> taskList = new ArrayList<Task>();
  ArrayList<Integer> theLoop = new ArrayList<Integer>();
  Boolean projectHasLoop = false;

  public ProjectPlanner(File f) throws FileNotFoundException {
    Scanner fileInput = new Scanner(f);

    //oppretter alle tasks forst slik at man kan refere til dem naar man skal sette in-/outEdges
    this.GraphLists(Integer.parseInt(fileInput.nextLine()));

    int setTasknr = 0;
    Task tmpTask = null;

    while(fileInput.hasNext()){
      String line = fileInput.nextLine();
      if(!line.equals("")){
        // \\s+ for aa fjerne tomrom
        String[] s = line.split("\\s+");
        tmpTask = taskList.get(setTasknr);
        tmpTask.taskSet(Integer.parseInt(s[0]), s[1], Integer.parseInt(s[2]), Integer.parseInt(s[3]), (s.length - 5));

        //legger til in-/outEdges
        for(int i = 4; i < s.length; i++){
          if(!(s[i]).equals("0")){
            tmpTask.addDependency(taskList.get(Integer.parseInt(s[i])-1));
            taskList.get(Integer.parseInt(s[i])-1).addOutEdge(tmpTask);
          }
        }
        setTasknr++;

      }
    }
    fileInput.close();
  }

  public void run(){
    this.findESEF();
    this.findLSLF();

    try {
      //this.createFile();
      this.createFile();
      System.out.println("Opprettet fil: 'output.txt'");
    } catch (IOException e) {
      System.out.println("Fikk ikke opprettet fil");
    }
  }

  //opprette alle n antall tasks
  private void GraphLists(int n){
    tasksNr = n;
    for(int i = 0; i < tasksNr; i++){
      taskList.add(new Task());
    }
  }

  //sjekker om det finnes en lokke
  public boolean isRealizable(){
    ArrayList<Task> running = new ArrayList<Task>();

    for(Task t : taskList){
      this.hasCycle(t, running);
    }
    if(!projectHasLoop){
      return true;
    }
    return false;
  }

  private void hasCycle(Task t, ArrayList<Task> running){
    ArrayList<Task> runlist = running;
    Boolean loop = false;
    //sjekker om tasken kjorer alt
    if(t.status.equals("running")){
      System.out.println("Lokke funnet----------------");
      for(Task ta : runlist){
        if(ta.getID() == t.getID()){
          loop = true;
        }
        if(loop){
          theLoop.add(ta.getID());
        }
      }
      theLoop.add(t.getID());
      System.out.println(theLoop);
      projectHasLoop = true;
    }

    //hvis ikke, saa settes den til running
    else if(t.status.equals("unseen")) {
      t.status = "running";
      runlist.add(t);

      //kjorer metoden rekursivt med t sin outedges
      for(Task neighbTask : t.getOutEdges()){
        hasCycle(neighbTask, runlist);
      }
    }

    runlist.remove(t);
    t.status = "done";
  }

  //finner earliest start og finish
  private void findESEF(){
    for(Task t : taskList){
      findESEF(t);
    }
  }

  private void findESEF(Task t){
    ArrayList<Task> depon = t.getDependentOn();

    //hvis t ikke har noen inedges kan den starte ved tid 0
    if(depon.size() == 0){
      t.earliestStart = 0;
      t.earliestFinish = t.earliestStart + t.time;
      t.esefCalculated = true;
    }

    else {
      int tmpES = 0;
      //sjekker hver task den er dependent on og setter ESEF
      for(Task tmpT : depon){
        //sjekker om taskene er kalkulert og gaar rekursivt igjennom
        if(!tmpT.esefCalculated){
          findESEF(tmpT);
          tmpT.esefCalculated = true;
        }

        //sjekker hvilken av task som bruker lengst tid og setter til ES
        if(tmpES < tmpT.earliestFinish){
          tmpES = tmpT.earliestFinish;
        }
      }
      t.earliestStart = tmpES;
      t.earliestFinish = (tmpES + t.time);
    }
  }

  //finner latest start og finish
  private void findLSLF(){
    projectTime = 0;
    for(Task t : taskList){
      if(t.earliestFinish > projectTime){
        projectTime = t.earliestFinish;
      }
    }
    for(Task t : taskList){
      findLSLF(t);
    }
  }

  private void findLSLF(Task t){
    ArrayList<Task> outEdges = t.getOutEdges();

    //sjekker om t ikke har noen outedges (dvs er en task som er i enden av grafen)
    //isaafall er lastest finish = projecttime
    if(outEdges.size() == 0){
      t.latestFinish = projectTime;
      t.latestStart = projectTime - t.time;
      t.lslfCalculated = true;
    } else {
      int tmpLS = projectTime;

      for(Task tmpT : outEdges){
        //sjekker om taskene er kalkulert og gaar rekursivt igjennom
        if(!tmpT.lslfCalculated){
          findLSLF(tmpT);
          tmpT.lslfCalculated = true;
        }

        //sjekker hvilken task som er lavest starte
        //slik at vi kan sette latest finish paa tasken for
        if(tmpLS > tmpT.latestStart){
          tmpLS = tmpT.latestStart;
        }
      }
      t.latestFinish = tmpLS;
      t.latestStart = (tmpLS - t.time);
    }

    //regner ut slack og setter critical til true hvis den er det.
    t.slack = (t.latestFinish - t.earliestFinish);
    if(t.slack == 0){
      t.critical = true;
    }
  }

  //skriver output fil
  private void createFile() throws IOException {
    FileWriter fileWriter  = new FileWriter("output.txt");
    PrintWriter writer = new PrintWriter(fileWriter);
    PriorityQueue<Action> pq = new PriorityQueue<Action>();

    for(Task t : taskList){
      writer.println("\nTaskID: " + t.getID());
      //kunne droppet inedges egt
      writer.print("  inEdges:  ");
      for(Task ta : t.dependentOn){
        writer.print(ta.getID() + " ");
      }
      writer.print("\n  outEdges:  ");
      for(Task ta : t.outEdges){
        writer.print(ta.getID() + " ");
      }

      writer.println();
      writer.println("  Time: " + t.time + "  Manpower:  " + t.staff);
      writer.println("  ES: " + t.earliestStart + "  EF:  " + t.earliestFinish);
      writer.println("  LS: " + t.latestStart + "  LF:  " + t.latestFinish);
      writer.println("  Slack: " + t.slack);
      writer.println("  Critical: " + t.critical);
      writer.println();
    }
    writer.println("\n Total project time: " + projectTime);
    writer.println("-----------------------------------------");
    writer.println();

    //-----------------------------------------------------
    // del 2 av output.txt

    Action tmpA;
    int time = -1;
    int manpower = 0;

    for(Task t : taskList){
      pq.add(new Action("Starting : ", t.earliestStart, t.id, t.staff));
      pq.add(new Action("Finished : ", t.earliestFinish, t.id, t.staff));
    }

    while(!pq.isEmpty()){
      tmpA = pq.poll();

      if(time != tmpA.getActionTime()){
        time = tmpA.getActionTime();
        writer.println();
        writer.println("Time: " + time);
      }

      writer.println(tmpA.getAction() + tmpA.getId());

      if(tmpA.getAction().equals(" Starting: ")){
        manpower += tmpA.getManPower();
      } else {
        manpower -= tmpA.getManPower();
      }

      if(pq.peek() != null){
        if(time != pq.peek().getActionTime()){
          writer.println(" -Manpower:  " + manpower);
        }
      } else {
        writer.println(" - Manpower: 0");
      }
    }
    writer.close();

  }
}
