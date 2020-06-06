/** A Power Gem is formed when four gems in a row are matched.
 * It destroys every gem in a one-square radius.
 */

public class PowerGem extends Gem {
  public static int value=3;
  public PowerGem() { 
    
  }
  
  public int value(){
    return value;
  }
  
  public PowerGem(int color) { 
    super(color);
  }
  
  public String image(){
    return styles[style]+"/"+colorNames[color]+"PowerGem.png";
  }
  
  public int pop(Square[][] grid,boolean[][] disapearMap,int x,int y,int colorTrigger){
    int count=new Gem(color).pop(grid,disapearMap,x,y,colorTrigger);
    for(int x2=-1;x2<2;x2++){
      for(int y2=-1;y2<2;y2++){
        try{
          if(!disapearMap[x+x2][y+y2]){
            count+=grid[x+x2][y+y2].gem.pop(grid,disapearMap,x+x2,y+y2,color);
          }
        }catch(Exception e){}
      }
    }
    
    return count; 
  }
}
