import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
public class Gui2 {
  public static final Window frame=new Window();
  public static JPanel mainMenu=mainMenu(frame);
  public static int delay=100;
//  public static final CustomListener START_GAME=new CustomListener(frame){
//    @Override
//    public void actionPerformed(ActionEvent ae){
//      frame.swap(new Game());
//      frame.setSize(500,500);
//    }
//  };
  
  /** This function checks if two coordinates are adjacent to each other.
   */
  
  public static boolean adjasent(int x1,int y1,int x2,int y2){
    int x=Math.abs(x1-x2),y=Math.abs(y1-y2);
    if(x>1||y>1||x==y){
      return false;
    }
    return true;
  }
  
  /** Main GUI class.
   */
  
  public static class Game extends JPanel{
    JLabel score=new JLabel("Score: 0");
    JLabel level=new JLabel("Level: 1");
    JLabel levelProgress=new JLabel("Level complete: 0%");
    Grid game=Grid.newGame(this);
    JButton[][] buttons=new JButton[Grid.sizeX][Grid.sizeY];
    
    Image img=getImage();
    public Image getImage()
    {
      // Loads the background image and stores in img object.
      Image i=null;
      try{
      i = ImageIO.read(new File("BackGrounds/porky.png"));//not this one
      }catch(Exception e){}
      //img = Toolkit.getDefaultToolkit().createImage("BackGrounds/porky.png");
      //super.paint();
      return i;
    }
    
    /** @Override
     */
     
    protected void paintComponent(Graphics g) {
      try{
      img = ImageIO.read(new File("BackGrounds/cave1.png"));
      }catch(Exception e){}
    super.paintComponent(g);
    g.drawImage(img, 0, 0, null);
    }
  
    /** Draws the img to the BackgroundPanel.
     */
    
    public void g2(Graphics g)
    {
      g.drawImage(img, 0, 0, null);
    }
    
    /** Constructor.
     */
    
    public Game(){
      
      super();
      //g();
      //g2(getGraphics());//does not work, fix
      setLayout(new GridLayout(0,1));
      JPanel tempPanel=new JPanel();
      tempPanel.setBackground(new java.awt.Color(0, 0, 0,0));
      //score.setBackground(new java.awt.Color(255, 255, 255,50));
      //System.out.println("AAAAAAHHHHHHHHH");
      JPanel temp2=new JPanel();
      temp2.setBackground(new java.awt.Color(255, 255, 255,150));
      temp2.add(score);
      tempPanel.add(temp2);
      add(tempPanel);
      temp2=new JPanel();
      temp2.setBackground(new java.awt.Color(255, 255, 255,150));
      temp2.add(level);
      tempPanel.add(temp2);
      add(tempPanel);
      temp2=new JPanel();
      temp2.setBackground(new java.awt.Color(255, 255, 255,150));
      temp2.add(levelProgress);
      tempPanel.add(temp2);
      add(tempPanel);
      boolean backGround=true;
      JButton temp=null;
      Bool step=new Bool();
      //gridPanel.setLayout(new GridLayout(Grid.sizeY+1,1));
      for(int y=Grid.sizeY-1;y>-1;y--){
        tempPanel=new JPanel();
        tempPanel.setBackground(new java.awt.Color(0, 0, 0,0));
        tempPanel.setLayout(new GridLayout(1,0));
        backGround=!backGround;
        for(int x=0;x<Grid.sizeX;x++){
          temp=new JButton();
          backGround=!backGround;
          if(backGround){//backGroundTint
            temp.setBackground(new java.awt.Color(255, 255, 255,150));
          }else{
            temp.setBackground(new java.awt.Color(0, 0, 0,150));
          }
          temp.addActionListener(new GridButton(x,y,step,this));
          tempPanel.add(temp);
          buttons[x][y]=temp;
        }
        add(tempPanel);
      }
      updateDisplay();
      repaint();
    }
    
    /** This is run whenever the display needs to be updated.
     * It changes the score, level, progress and images.
     */
    
    public void updateDisplay(){
      score.setText("Score: "+game.score);
      level.setText("Level: "+game.level());
      levelProgress.setText("Level complete: "+((int)(100*((game.gemsMatched%Grid.levelInterval)/(Grid.levelInterval*1.0))))+"%");
      for(int x=0;x<Grid.sizeX;x++){
        for(int y=0;y<Grid.sizeY;y++){
          //buttons[x][y].setText(""+game.grid[x][y].gem.color);
          try{
          buttons[x][y].setIcon(new javax.swing.ImageIcon(getClass().getResource(game.grid[x][y].gem.image())));
          }catch(Exception e){
            buttons[x][y].setIcon(null);
          }
          frame.repaint();
        }
      }
    }
    
  }
  
  /** This class covers all buttons on the grid.
   */
  
  public static class GridButton implements ActionListener{
    int x,y;
    Bool step;
    Game game;
    public GridButton(int x,int y,Bool step,Game game){
      super();
      this.x=x;
      this.y=y;
      this.step=step;
      this.game=game;
    }
    @Override
    public void actionPerformed(ActionEvent ae){
      if(!GemMatch.busy){
        GemMatch.busy=true;
        GemMatch.pass=new Bool();
        ((Gui2.Bool)GemMatch.pass).x1=x;
        ((Gui2.Bool)GemMatch.pass).y1=y;
        GemMatch.eventNumber=0;
        GemMatch.event=true;
      }
    }
  }
  
  /** Information storage concerning whether a square has been selected or not
   */
  
  public static class Bool{
    boolean step=true;
    int x1, y1, x2, y2;
  }
  
  /** JPanel class for the main menu
   */
  
  public static JPanel mainMenu(Window frame){
    JPanel menu=new JPanel();
    menu.setLayout(new BoxLayout(menu,BoxLayout.PAGE_AXIS));
    JLabel title=new JLabel("Gem Match");
    JButton newGame=new JButton("New Game");
    newGame.addActionListener(new CustomListener(frame){
      @Override
      public void actionPerformed(ActionEvent ae){
        Game game=new Gui2.Game();
        frame.swap(game);
        frame.setSize(517,550);
        GemMatch.game=game;
      }
    });
    JButton resumeGame=new JButton("Resume Game");
    JButton highScores=new JButton("High Scores");
    JPanel temp=new JPanel();
    temp.add(title);
    menu.add(temp);
    temp=new JPanel();
    temp.add(newGame);
    menu.add(temp);
    temp=new JPanel();
    temp.add(resumeGame);
    menu.add(temp);
    temp=new JPanel();
    temp.add(highScores);
    menu.add(temp);
    return menu;
  }
  
  /** JPanel class for the window
   */
  
  public static class Window extends JFrame{
    JPanel panel;
    public Window(){
      super();
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
      setResizable(true);
      setSize(0,0);
    }
    
    public void add(JPanel panel){
      this.panel=panel;
      super.getContentPane().add(panel);
      repaint();
    }
    
    public void swap(JPanel panel){
      super.getContentPane().remove(this.panel);
      this.panel=panel;
      super.getContentPane().add(panel);
      repaint();
    }
  }
  
  /** Override functionality of ActionListener to make new ones
   */
  
  public static class CustomListener implements ActionListener{
    public Window frame;
    CustomListener(){
      super();
    }
    CustomListener(Window f){
      super();
      frame=f;
    }
    @Override
    public void actionPerformed(ActionEvent ae){//is never called
      
    }
  }
}
