/** A Star gem is formed whenever two mathced lines intersect.
 * It destroys every gem in a vertical and horizontal line.
 */

public class StarGem extends Gem {
  public static int value=4;
  public StarGem() { 
    
  }
  
  public int value(){
    return value;
  }
  
  public StarGem(int color) { 
    super(color);
  }
  
  public String image(){
    return styles[style]+"/"+colorNames[color]+"StarGem.png";
  }
  
  public int pop(Square[][] grid,boolean[][] disapearMap,int x,int y,int colorTrigger){
    int count=new Gem(color).pop(grid,disapearMap,x,y,colorTrigger);
    for(int x2=0;x2<Grid.sizeX;x2++){
      if(!disapearMap[x2][y]){
        count+=grid[x2][y].gem.pop(grid,disapearMap,x2,y,color);
      }
    }
    for(int y2=0;y2<Grid.sizeY;y2++){
      if(!disapearMap[x][y2]){
        count+=grid[x][y2].gem.pop(grid,disapearMap,x,y2,color);
      }
    }
    
    return count; 
  }
}
