//import javax.swing.*;
//import java.awt.*;

/** This is the main program for handling the game loop.
  */

public class GemMatch {
  public static Object pass=null;
  public static Gui2.Game game=null;
  public static boolean event=false;
  public static boolean busy=false;
  public static int eventNumber=0;
  public static Gui2.Bool step=new Gui2.Bool();
  
  /** This method is separate from the main method so that it can be called in other files. 
    * It is a testing concern.
    * ARTOUR: fix before we're done
    */
  
  public static void mainMethod()throws Exception{
    Gui2.frame.add(Gui2.mainMenu);
    Gui2.frame.setSize(200,200);
    eventHandling(Gui2.frame); //It's spelled "handling", Artour
  }
  
  /** This method handles events for the frame. 
    * It does the heavy lifting of generating the GUI.
    */
  
  public static void eventHandling(Gui2.Window frame)throws Exception{
    while(true){
      while(game==null){
        Thread.sleep(10);
      }
      while(!event&&game.game.notLost){
        frame.repaint();
        Thread.sleep(10);
      }
      if(eventNumber==0){
        if(step.step){//first select
          step.x1=((Gui2.Bool)pass).x1;
          step.y1=((Gui2.Bool)pass).y1;
          step.step=false;
        }else{//second select
          if(Gui2.adjasent(((Gui2.Bool)pass).x1,((Gui2.Bool)pass).y1,step.x1,step.y1)){//if adjasent
            step.x2=((Gui2.Bool)pass).x1;
            step.y2=((Gui2.Bool)pass).y1;
            //do swap action
            game.game.swap(step.x1,step.y1,step.x2,step.y2);
            step.step=true;
          }else{
            step.x1=((Gui2.Bool)pass).x1;
            step.y1=((Gui2.Bool)pass).y1;
          }
        }
        game.game.notLost=!game.game.loose();
        if(!game.game.notLost){
          game=null;
          System.out.println("Game over.");
          frame.swap(Gui2.mainMenu);
          frame.setSize(200,200);
        }
        event=false;
        busy=false; 
      }else{
        System.out.println("No such event");
      }
      //determine event
      //execute event
    }
  }
  
  public static void main(String[] args)throws Exception{ 
    mainMethod();
  }
  
}
