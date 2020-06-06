/** This describes the game grid.
  */

public class Grid {
  public static int sizeX=8,sizeY=8;
  public Square[][] grid=new Square[sizeX][sizeY];
  public static double comboBonus= 1.25;
  public static double levelBonus= 1.125;
  public static int levelInterval=100;
  public double combo=1.0;
  public int score=0;
  public int gemsMatched=0;
  public Gui2.Game display;
  public boolean notLost=true;
  public int[] spawnHistory=new int[Gem.colorNames.length];
  public static final int minimumColors=4, maximumColors=Gem.colorNames.length-1, colorAdd=5;
  
  /** Basic constructor
    */
  
  public Grid() { 
    for(int x=0;x<sizeX;x++){
      for(int y=0;y<sizeY;y++){
        grid[x][y]=new Square();
      }
    }
  }
  
  /** For testing purposes, prints text-based grid
    */
  
  public void printGrid(){
    String out="";
    for(int y=0;y<sizeY;y++){
      for(int x=0;x<sizeX;x++){
        out+=grid[x][y].gem.color;
      }
      out+="\n";
    }
    System.out.print(out);
  }
  
  /** Returns the number of empty squares
    */
  
  public int empty(){
    int count=0;
    for(int y=0;y<sizeY;y++){
      for(int x=0;x<sizeX;x++){
        if(grid[x][y].gem==null){
          count++;
        }
      }
    }
    return count;
  }
  
  //returns true if the game is over, otherwise returns false.
  public boolean loose(){
    //check for hyper cubes
    for(int y=0;y<sizeY;y++){
      for(int x=0;x<sizeX;x++){
        if(grid[x][y].gem instanceof HyperCube){
          return false;
        }
      }
    }
    Grid simGrid=new Grid();
    for(int y=0;y<sizeY;y++){
      for(int x=0;x<sizeX;x++){
        simGrid.grid[x][y].gem=grid[x][y].gem;
      }
    }
    //vertical possible swap check
    Gem temp=null;
    for(int y=1;y<sizeY;y++){
      for(int x=0;x<sizeX;x++){
        temp=simGrid.grid[x][y].gem;
        simGrid.grid[x][y].gem=simGrid.grid[x][y-1].gem;
        simGrid.grid[x][y-1].gem=temp;
        if(simGrid.match()){
          return false;
        }else{
          simGrid.grid[x][y].gem=grid[x][y].gem;
          simGrid.grid[x][y-1].gem=grid[x][y-1].gem;
        }
      }
    }
    //horizontal possible swap check
    for(int y=0;y<sizeY;y++){
      for(int x=1;x<sizeX;x++){
        temp=simGrid.grid[x][y].gem;
        simGrid.grid[x][y].gem=simGrid.grid[x-1][y].gem;
        simGrid.grid[x-1][y].gem=temp;
        if(simGrid.match()){
          return false;
        }else{
          simGrid.grid[x][y].gem=grid[x][y].gem;
          simGrid.grid[x-1][y].gem=grid[x-1][y].gem;
        }
      }
    }
    return true; 
  }
  
  //returns the amount of different colors that can spawn at the current level
  public int colorsForLevel(){
    int colors=((level()-1)/colorAdd)+minimumColors;
    if(colors>maximumColors){
      return maximumColors;
    }
    return colors;
  }
  
  //returns a new Gem of a semi-randomly chosen color
  public Gem spawn(){
    int max=spawnHistory[1];
    for(int c=1;c<=colorsForLevel();c++){
      if(max<spawnHistory[c]){
        max=spawnHistory[c];
      }
    }
    int slots=0;
    for(int c=1;c<=colorsForLevel();c++){
      slots+=max-spawnHistory[c]+1;
    }
    
    int random=(int)(Math.random()*slots)+1;
    //System.out.println(random);
    for(int c=1;c<=colorsForLevel();c++){
      if(random<=max-spawnHistory[c]+1){
        spawnHistory[c]++;
        if(spawnHistory[c]==1){
          for(int i=1;i<=colorsForLevel();i++){
            if(spawnHistory[i]==0){
              //System.out.println(c);
              return new Gem(c);
            }
          }
          for(int i=1;i<=colorsForLevel();i++){
            spawnHistory[i]--;
          }
        }
        return new Gem(c);
      }else{
        random-=max-spawnHistory[c]+1;
      }
    }
//    System.out.println("AAAAAAAAAAAAAAHHHHHHHHHHHH");
    return new Gem(0);
  }
  
  /** Returns level number.
    */
  
  public int level(){
    return (gemsMatched/levelInterval)+1;
  }
  
  /** Returns level bonus score multiplier
    */
  
  public double levelBonus(){
    return Math.pow(levelBonus,level());
  }
  
  /** This method generates a grid for a new game and spawns gems in it.
    * It ensures that the initial position will not have three-in-a-row.
    */
  
  public static Grid newGame(Gui2.Game display){
    Grid game=new Grid();
    game.display=display;
    boolean reTry=true;
    Gem spawn=null;
    for(int x=0;x<sizeX;x++){
      for(int y=0;y<sizeY;y++){
        reTry=true;
        while(reTry){
          reTry=false;
          spawn=game.spawn();
          game.grid[x][y].gem=spawn;
          try{
            if(game.grid[x-2][y].gem.color==game.grid[x][y].gem.color){
              if(game.grid[x-1][y].gem.color==game.grid[x][y].gem.color){
                reTry=true;
              }
            }
          }catch(Exception e){}
          if(!reTry){
            try{
              if(game.grid[x][y-2].gem.color==game.grid[x][y].gem.color){
                if(game.grid[x][y-1].gem.color==game.grid[x][y].gem.color){
                  reTry=true;
                }
              }
            }catch(Exception e){}
          }
          if(reTry){
            game.spawnHistory[spawn.color]--;
            if (game.spawnHistory[spawn.color]==-1){
              for(int c=1;c<=game.colorsForLevel();c++){
                game.spawnHistory[c]++;
              }
            }
          }
        }
        //System.out.println(spawn);
      }
    }
    return game;
  }
  
  /** This method causes the gems with space underneath to fall down.
    * It also generates new gems to fall from the tops and take their places.
    */
  
  public void applyGravity(){
    display.updateDisplay();
    try{Thread.sleep(Gui2.delay);}catch(Exception e){}
    boolean cont=true;
    while(cont){
      cont=false;
      for(int x=0;x<sizeX;x++){
        for(int y=0;y<sizeY;y++){
          if(grid[x][y].gem==null){
            cont=true;
            try{
              grid[x][y].gem=grid[x][y+1].gem;
              grid[x][y+1].gem=null;
            }catch(Exception e){
              grid[x][y].gem=spawn();
            }
          }
        }
      }
      try{Thread.sleep(Gui2.delay);}catch(Exception e){}
      display.updateDisplay();
      //try{Thread.sleep(Gui2.delay);}catch(Exception e){}
    }
  }
  
  /** This swaps gems of the inputed cordinates.
    * and if the swap is not valid then it will display an animation of swaping and then swaping back to original position.
    */
  
  public void swap(int x1, int y1, int x2, int y2){
    display.updateDisplay();
    try{Thread.sleep(Gui2.delay);}catch(Exception e){}
    if(grid[x1][y1].gem.color==0){
      if(grid[x2][y2].gem.color==0){
        for(int x=0;x<sizeX;x++){
          for(int y=0;y<sizeY;y++){
            grid[x][y].gem=null;
          }
        }
        double lvl=levelBonus();
        score+=lvl*sizeX*sizeY;
        gemsMatched+=sizeX*sizeY;
        combo=1.0;
        int temp=0;
        applyGravity();
        while(match()){
          combo*=comboBonus;
          temp=empty();
          gemsMatched+=temp;
          score+=combo*lvl*temp;
          applyGravity();
        }
        return;
      }else{
        boolean[][] disappearMap=new boolean[sizeX][sizeY];
        grid[x1][y1].gem.pop(grid,disappearMap,x1,y1,grid[x2][y2].gem.color);
        for(int x=0;x<sizeX;x++){
          for(int y=0;y<sizeY;y++){
            if(disappearMap[x][y]){
              grid[x][y].gem=null;
            }
          }
        }
        double lvl=levelBonus();
        int temp=empty();
        score+=lvl*temp;
        gemsMatched+=temp;
        combo=1.0;
        applyGravity();
        while(match()){
          combo*=comboBonus;
          temp=empty();
          gemsMatched+=temp;
          score+=combo*lvl*temp;
          applyGravity();
        }
        return;
      }
    }else if(grid[x2][y2].gem.color==0){
      boolean[][] disappearMap=new boolean[sizeX][sizeY];
      grid[x2][y2].gem.pop(grid,disappearMap,x2,y2,grid[x1][y1].gem.color);
      for(int x=0;x<sizeX;x++){
        for(int y=0;y<sizeY;y++){
          if(disappearMap[x][y]){
            grid[x][y].gem=null;
          }
        }
      }
      double lvl=levelBonus();
      int temp=empty();
      score+=lvl*temp;
      gemsMatched+=temp;
      combo=1.0;
      applyGravity();
      while(match()){
        combo*=comboBonus;
        temp=empty();
        gemsMatched+=temp;
        score+=combo*lvl*temp;
        applyGravity();
      }
      return;
    }
    Gem swap=grid[x1][y1].gem;
    grid[x1][y1].gem=grid[x2][y2].gem;
    grid[x2][y2].gem=swap;
    display.updateDisplay();
    try{Thread.sleep(Gui2.delay);}catch(Exception e){}
    if(match()){
      double lvl=levelBonus();
      int temp=empty();
      score+=lvl*temp;
      gemsMatched+=temp;
      combo=1.0;
      applyGravity();
      while(match()){
        combo*=comboBonus;
        temp=empty();
        gemsMatched+=temp;
        score+=combo*lvl*temp;
        applyGravity();
      }
      
      
    }else{//invalid swap
      swap=grid[x1][y1].gem;
      grid[x1][y1].gem=grid[x2][y2].gem;
      grid[x2][y2].gem=swap;
    }
    display.updateDisplay();
  }
  
  /** This boolean functions tells if a there is a match or not on the board while performing the matching needed if there are is anything to match
    */
  
  public boolean match(){
    //System.out.println("AAAAAAHHHHHHHHH");
    boolean match=false;
    int color=-1,row=0;
    int[][] matchColorMap=new int[sizeX][sizeY];
    int[][] matchMap=new int[sizeX][sizeY];
    //vertical match;
    for(int x=0;x<sizeX;x++){
      color=-1;
      row=0;
      for(int y=0;y<sizeY;y++){
        if(grid[x][y].gem.color==color){
          row++;
        }else{
          if(row>2){
            match=true;
            for(int c=0;c<row;c++){
              matchColorMap[x][y-1-c]=color;
              matchMap[x][y-1-c]++;
            }
          }
          row=1;
          color=grid[x][y].gem.color;
        }
      }
      if(row>2){
        match=true;
        for(int c=0;c<row;c++){
          matchColorMap[x][sizeY-1-c]=color;
          matchMap[x][sizeY-1-c]++;
        }
      }
    }
    
    
    
    //hoizontal match;
    for(int y=0;y<sizeY;y++){
      color=-1;
      row=0;
      for(int x=0;x<sizeX;x++){
        if(grid[x][y].gem.color==color){
          row++;
        }else{
          if(row>2){
            match=true;
            for(int c=0;c<row;c++){
              matchColorMap[x-1-c][y]=color;
              matchMap[x-1-c][y]++;
            }
          }
          row=1;
          color=grid[x][y].gem.color;
        }
      }
      if(row>2){
        match=true;
        for(int c=0;c<row;c++){
          matchColorMap[sizeX-1-c][y]=color;
          matchMap[sizeX-1-c][y]++;
        }
      }
    }
    
    if(match){
      //initialize map of gems to disappear
      boolean[][] disappearMap=new boolean[sizeX][sizeY];
      int count=0,prevCount=-1;
      while(count>prevCount){
        prevCount=count;
        for(int x=0;x<sizeX;x++){
          for(int y=0;y<sizeY;y++){
            
            if(matchMap[x][y]>0&&!disappearMap[x][y]){
              count+=grid[x][y].gem.pop(grid,disappearMap,x,y,grid[x][y].gem.color);
            }
          }
        }
      }
      
      //disappear
      for(int x=0;x<sizeX;x++){
        for(int y=0;y<sizeY;y++){
          if(disappearMap[x][y]){
            grid[x][y].gem=null;
          }
        }
      }
      
      //star gem
      for(int x=0;x<sizeX;x++){
        for(int y=0;y<sizeY;y++){
          if(matchMap[x][y]>1){
            grid[x][y].gem=new StarGem(matchColorMap[x][y]);
            score+=StarGem.value*combo;
            gemsMatched++;
          }
        }
      }
      
      //other special gem spawn
      
      //vertical
      
      for(int x=0;x<sizeX;x++){
        color=-1;
        row=0;
        for(int y=0;y<sizeY;y++){
          if(matchColorMap[x][y]==color&&color>0){
            row++;
          }else{
            spawnV(x,y-1,row,color);
            color=matchColorMap[x][y];
            row=1;
          }
        }
        spawnV(x,sizeY-1,row,color);
      }
      
      //horizontal
      for(int y=0;y<sizeY;y++){
        color=-1;
        row=0;
        for(int x=0;x<sizeX;x++){
          if(matchColorMap[x][y]==color&&color>0){
            row++;
          }else{
            spawnH(x-1,y,row,color);
            color=matchColorMap[x][y];
            row=1;
          }
        }
        spawnH(sizeX-1,y,row,color);
      }
    }
    //System.out.println(match);
    return match;
  }
  
  /** This spawns special gems vertically.
    */
  
  public void spawnV(int x,int y,int row,int color){
    if(row>3){
      boolean cont=true;
      int c=0;
      while(c<row-1&&cont){
        if(grid[x][y-c].gem==null){
          cont=false;
          c--;
        }
        c++;
      }
      if(c==row){
        c--;
      }
      if(row>4){
        if(row>5){
          //spawnSuperNovaGem
          grid[x][y-c].gem=new SuperNovaGem(color);
          score+=SuperNovaGem.value*(row-5)*combo;
          gemsMatched++;
        }else{
          //spawnHyperCube
          grid[x][y-c].gem=new HyperCube();
          score+=HyperCube.value*combo;
          gemsMatched++;
        }
      }else{
        //spawnPowerGem
        grid[x][y-c].gem=new PowerGem(color);
        score+=PowerGem.value*combo;
        gemsMatched++;
      }
    }
  }
  
  /** This spawns special gems horizontally.
    */
  
  public void spawnH(int x,int y,int row,int color){
    if(row>3){
      boolean cont=true;
      int c=0;
      while(c<row-1&&cont){
        if(grid[x-c][y].gem==null){
          cont=false;
          c--;
        }
        c++;
      }
      if(c==row){
        return;
      }
      if(row>4){
        if(row>5){
          //spawnSuperNovaGem
          grid[x-c][y].gem=new SuperNovaGem(color);
          score+=SuperNovaGem.value*(row-5)*combo;
          gemsMatched++;
        }else{
          //spawnHyperCube
          grid[x-c][y].gem=new HyperCube();
          score+=HyperCube.value*combo;
          gemsMatched++;
        }
      }else{
        //spawnPowerGem
        grid[x-c][y].gem=new PowerGem(color);
        score+=PowerGem.value*combo;
        gemsMatched++;
      }
    }
  }
}
