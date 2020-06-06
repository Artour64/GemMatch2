/** A Hypercube forms when five gems in a line are matched.
 * When mathced with a gem, the Hypercube destroys every gem of the same color.
 */

public class HyperCube extends Gem {
  public static int value=5;
  public HyperCube() { 
    color=0;
  }
  
  public int value(){
    return value;
  }
  
  public String image(){
    return styles[style]+"/cube.png";
  }
  
    public int pop(Square[][] grid,boolean[][] disapearMap,int x,int y,int colorTrigger){
    int count=new Gem(color).pop(grid,disapearMap,x,y,colorTrigger);
    for(int x2=0;x2<Grid.sizeX;x2++){
      for(int y2=0;y2<Grid.sizeY;y2++){
        if(!disapearMap[x2][y2]&&grid[x2][y2].gem.color==colorTrigger){
          count+=grid[x2][y2].gem.pop(grid,disapearMap,x2,y2,colorTrigger);
        }
      }
    }
    
    return count; 
  }
}
