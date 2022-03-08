public class Action implements Comparable<Action> {

  String action;
  int actionTime, manpower, id;

  Action(String s, int at, int i, int mp){
    action = s;
    actionTime = at;
    id = i;
    manpower = mp;
  }

  public String getAction(){
    return action;
  }

  public int getActionTime(){
    return actionTime;
  }

  public int getId(){
    return id;
  }

  public int getManPower(){
    return manpower;
  }

  @Override
  public int compareTo(Action otherAction){
    if(this.actionTime == otherAction.actionTime){
      return 0;
    } else if(this.actionTime > otherAction.actionTime){
      return 1;
    } else {
      return -1;
    }
  }
}
