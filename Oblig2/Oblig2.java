import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;

public class Oblig2{
  public static void main(String[] args) {
    String filename = null;

    if(args.length > 0){
      filename = args[0];
    } else {
      System.out.println("Feil! Riktig bruk av programmet: " + "java Oblig2 <tekstfil>.txt");
    }

    File fil = new File(filename);

    try {
      ProjectPlanner project = new ProjectPlanner(fil);

      if(project.isRealizable()){
        System.out.println("Grafen inneholder ingen lokke");
        project.run();
      } else {
        System.out.println("Grafen inneholder lokke");
      }
    } catch (FileNotFoundException e) {
      System.out.printf("Feil: Kunne ikke lese fra '%s'\n", filename);
    }
  }
}
