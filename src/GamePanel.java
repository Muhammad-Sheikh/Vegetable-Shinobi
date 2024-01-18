/* GamePanel class acts as the main "game loop" - continuously runs the game and calls whatever needs to be called

Child of JPanel because JPanel contains methods for drawing to the screen

Implements KeyListener interface to listen for keyboard input

Implements Runnable interface to use "threading" - let the game do two things at once

*/
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{

  //dimensions of window
  public static final int GAME_WIDTH = 1024;
  public static final int GAME_HEIGHT = 600;
  public static int startX = 0, MouseY = 0, MouseX = 0;

  public int lives = 3;

  public Thread gameThread;
  public Image image;
  public Graphics graphics;
  public Vegetables veg1, veg2, veg3, veg4 ,veg5;
  Point mousePoint;

  Vegetables[] vegetableList = {veg1, veg2};



  public GamePanel(){


    veg1 = new Vegetables(GAME_WIDTH/2, GAME_HEIGHT/2);
    veg2 = new Vegetables(GAME_WIDTH/2, GAME_HEIGHT/2);
    veg3 = new Vegetables(GAME_WIDTH/2, GAME_HEIGHT/2);
    veg4 = new Vegetables(GAME_WIDTH/2, GAME_HEIGHT/2);
    veg5 = new Vegetables(GAME_WIDTH/2, GAME_HEIGHT/2);
    Vegetables[] vegetables = {veg1, veg2, veg3, veg4, veg5};



    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        mousePoint = e.getPoint();
        for(Vegetables tempVeg : vegetables)
        {
          if(tempVeg.contains(mousePoint))
          {
            tempVeg.mouseIntersects = true;
          } else
          {
            tempVeg.mouseIntersects = false;
          }
        }
      }
    });



    this.setFocusable(true); //make everything in this class appear on the screen

    //add the MousePressed method from the MouseAdapter - by doing this we can listen for mouse input. We do this differently from the KeyListener because MouseAdapter has SEVEN mandatory methods - we only need one of them, and we don't want to make 6 empty methods
    this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

    //make this class run at the same time as other classes (without this each class would "pause" while another class runs). By using threading we can remove lag, and also allows us to do features like display timers in real time!
    gameThread = new Thread(this);
    gameThread.start();
  }



    //paint is a method in java.awt library that we are overriding. It is a special method - it is called automatically in the background in order to update what appears in the window. You NEVER call paint() yourself
  public void paint(Graphics g){
    //we are using "double buffering here" - if we draw images directly onto the screen, it takes time and the human eye can actually notice flashes of lag as each pixel on the screen is drawn one at a time. Instead, we are going to draw images OFF the screen, then simply move the image on screen as needed.
    image = createImage(GAME_WIDTH, GAME_HEIGHT); //draw off screen
    graphics = image.getGraphics();
    draw(graphics);//update the positions of everything on the screen
    g.drawImage(image, 0, 0, this); //move the image on the screen
  }


  public void sleep(int x)
  {
    try {
      Thread.sleep(x);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  //call the draw methods in each class to update positions as things move
  public void draw(Graphics g){
    g.setColor(Color.white);
    veg1.draw(g);
    veg2.draw(g);

  }

  //call the move methods in other classes to update positions
  //this method is constantly called from run(). By doing this, movements appear fluid and natural. If we take this out the movements appear sluggish and laggy


  //handles all collision detection and responds accordingly
  public void checkCollision(){
  Vegetables[] vegList = {veg1, veg2};
  for(Vegetables tempVeg : vegList)
  {
    if(tempVeg.y<= 0){
      tempVeg.y = 0;
      lives = lives -1;

    }
    if(tempVeg.y >= GAME_HEIGHT - tempVeg.VEG_DIAMETER){
      lives = lives -1;
      tempVeg.y = GAME_HEIGHT-tempVeg.VEG_DIAMETER;
    }
    if(tempVeg.x <= 0){
      tempVeg.x = 0;
      lives = lives -1;

    }
    if(tempVeg.x + tempVeg.VEG_DIAMETER >= GAME_WIDTH){
      tempVeg.x = GAME_WIDTH-tempVeg.VEG_DIAMETER;
      lives = lives -1;

    }
  }

  }


  public double[] getQuadratic(int spawnSeed){
  double[] quadraticFactor = {0, 0, 0, 0, 0};
  //A H M


  switch (spawnSeed){
    case 1:
      quadraticFactor[0] = 0.01;
      quadraticFactor[1] = 500;
      quadraticFactor[2] = 0;
      quadraticFactor[3] = 744;
      quadraticFactor[4] = 253;
      return quadraticFactor;
    case 2:
      quadraticFactor[0] = 0.01;
      quadraticFactor[1] = 750;
      quadraticFactor[2] = 0;
      quadraticFactor[3] = 994;
      quadraticFactor[4] = 505;
      return quadraticFactor;
    case 3:
      quadraticFactor[0] = 0.01;
      quadraticFactor[1] = 260;
      quadraticFactor[2] = 260;
      quadraticFactor[3] = 444;
      quadraticFactor[4] = 75;
      return quadraticFactor;
    case 4:
      quadraticFactor[0] = 0.01;
      quadraticFactor[1] = 650;
      quadraticFactor[2] = 350;
      quadraticFactor[3] = 772;
      quadraticFactor[4] = 744;
      return quadraticFactor;
    case 5:
      quadraticFactor[0] = 0.01;
      quadraticFactor[1] = 360;
      quadraticFactor[2] = 140;
      quadraticFactor[3] = 574;
      quadraticFactor[4] = 491;
      return quadraticFactor;
    case 6:
      quadraticFactor[0] = 0.01;
      quadraticFactor[1] = 530;
      quadraticFactor[2] = 460;
      quadraticFactor[3] = 648;
      quadraticFactor[4] = 411;
      return quadraticFactor;
  }


    return null;
  }

  public void removeVeg(Vegetables cutVeg)
  {
    hasBeenCut = true;
    System.out.println("cut!");
  }
  public double getA(int spawnSeed)
  {
    double[] tempArray = getQuadratic(spawnSeed);
    return tempArray[0];
  }

  public double getH(int spawnSeed)
  {
    double[] tempArray = getQuadratic(spawnSeed);
    return tempArray[1];
  }

  public double getM(int spawnSeed)
  {
    double[] tempArray = getQuadratic(spawnSeed);
    return tempArray[2];
  }


  public void setStartX(int spawnSeed, Vegetables veg)
  {
    double[] tempArray = getQuadratic(spawnSeed);
    startX = (int) tempArray[4];
    veg.updateStep();
  }

  public int getEndX(int spawnSeed)
  {
    double[] tempArray = getQuadratic(spawnSeed);
    return (int) tempArray[3];
  }
  boolean hasBeenCut = false;

  public void spawnVeg(int spawnSeed, Vegetables veg)
  {
    if(veg.mouseIntersects)
    {
      removeVeg(veg);
    } else if (!hasBeenCut)
    {
      setStartX(spawnSeed, veg);
      veg.move(getEndX(spawnSeed), getA(spawnSeed), getH(spawnSeed), getM(spawnSeed));
    }
  }




  //run() method is what makes the game continue running without end. It calls other methods to move objects,  check for collision, and update the screen
  public void run(){
    //the CPU runs our game code too quickly - we need to slow it down! The following lines of code "force" the computer to get stuck in a loop for short intervals between calling other methods to update the screen.
    long lastTime = System.nanoTime();
    double amountOfTicks = 60;
    double ns = 1000000000/amountOfTicks;
    double delta = 0;
    long now;

    while(true){ //this is the infinite game loop
      now = System.nanoTime();
      delta = delta + (now-lastTime)/ns;
      lastTime = now;

      //only move objects around and update screen if enough time has passed
      if(delta >= 1){
        //System.out.println("X: " + MouseX + "Y: " + MouseY);
        spawnVeg(1, veg1);
        spawnVeg(3, veg2);
        checkCollision();
        repaint();
        delta--;
      }
    }
  }

  //if a key is pressed, we'll send it over to the PlayerBall class for processing


}
