
/** All special gems extend this class. 
  * This defines the necessary aspects of a gem:
  * the pictures that represent it,
  * its point value,
  * its color,
  * and its location.
  */ 

public class Gem {
  public static int value=1;
  public static int style=0;
  public static String[] styles={"Images 50x46"};
  public static String[] colorNames={"","red","blue","green","purple","orange","yellow","white","black","gray","brown"};
  int color;
  
  public Gem() { //Empty constructors are not necessary.
    //We can actually just leave this out
  }
  
  public int value(){ //??? What the heck dude
    return value; //It's the same damn thing. 
    //A primitive that returns the value or a method that returns the value
  } //I saw this in every different gem file for some reason?
  
  /** This method returns the name of a file that can represent this gem.
    * The name can then be called from the correct folder.
    */
  
  public String image(){
    return styles[style]+"/"+colorNames[color]+"Gem.png";
  }
  
  /** Constructor with declared color.
    */
  
  public Gem(int color) { 
    this.color=color;
  }
  
  /** This spawns a gem of a random color.
    */
  
  public static Gem spawn(){
    Gem gem=new Gem();
    gem.color=(int)(Math.random()*(colorNames.length-1))+1;
    return gem;
  }
  
  /** This handles the position of the gem on the grid.
    */
  
  public int pop(Square[][] grid,boolean[][] disappearMap,int x,int y,int colorTrigger){
    if(!disappearMap[x][y]){
      disappearMap[x][y]=true;
      return 1;
    }
    return 0;
  }
}
