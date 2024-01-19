/* GamePanel class acts as the main "game loop" - continuously runs the game and calls whatever needs to be called

Child of JPanel because JPanel contains methods for drawing to the screen

Implements KeyListener interface to listen for keyboard input

Implements Runnable interface to use "threading" - let the game do two things at once

*/
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{

  //dimensions of window
  public static final int GAME_WIDTH = 1024;
  public static final int GAME_HEIGHT = 600;

  public static int lives = 5, score = 1, combo = 1, difficulty = 0, startX = 0;

  public Thread gameThread;
  public Image image;
  public Graphics graphics;
  public Vegetables veg1, veg2, veg3, veg4 ,veg5;
  public Bomb bomb1;
  Point mousePoint;
  BufferedImage test = new BufferedImage(20, 20,2);





  public GamePanel()  {
    bomb1 = new Bomb(GAME_WIDTH/2, GAME_HEIGHT/2);
    veg1 = new Vegetables(GAME_WIDTH/2, GAME_HEIGHT/2);
    veg2 = new Vegetables(GAME_WIDTH/2, GAME_HEIGHT/2);
    veg3 = new Vegetables(GAME_WIDTH/2, GAME_HEIGHT/2);
    veg4 = new Vegetables(GAME_WIDTH/2, GAME_HEIGHT/2);
    veg5 = new Vegetables(GAME_WIDTH/2, GAME_HEIGHT/2);
    Vegetables[] vegetables = {veg1, veg2, veg3, veg4, veg5};

    try
    {
      test = ImageIO.read(getClass().getResourceAsStream("/2.png"));
    }catch (IOException e)
    {
      System.out.println("lol");
    }


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

        if(bomb1.contains(mousePoint))
        {
          bomb1.mouseIntersects = true;
        } else
        {
          bomb1.mouseIntersects = false;
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

  private void loadImage(String imagePath) {
    ImageIcon icon = new ImageIcon(imagePath);
    image = icon.getImage();
  }



    //paint is a method in java.awt library that we are overriding. It is a special method - it is called automatically in the background in order to update what appears in the window. You NEVER call paint() yourself
  public void paint(Graphics g){

    Graphics2D g2d = (Graphics2D)g;

    //we are using "double buffering here" - if we draw images directly onto the screen, it takes time and the human eye can actually notice flashes of lag as each pixel on the screen is drawn one at a time. Instead, we are going to draw images OFF the screen, then simply move the image on screen as needed.

    image = createImage(GAME_WIDTH, GAME_HEIGHT); //draw off screen
    graphics = image.getGraphics();
    draw(graphics);//update the positions of everything on the screen
    g2d.drawImage(test, 0, 0,null);

    //g.drawImage(image, 0, 0, this); //move the image on the screen

  }


  //call the draw methods in each class to update positions as things move
  public void draw(Graphics g) {
    Vegetables[] Diff1vegetables = {veg1};
    Vegetables[] Diff2vegetables = {veg1, veg2, veg3};
    Vegetables[] Diff3vegetables = {veg1, veg2, veg3, veg4, veg5};

    g.setColor(Color.white);
    difficultyLevel();
    if (difficulty == 1) {
      for (Vegetables tempVeg : Diff1vegetables) {
        tempVeg.draw(g);
      }
    }

    if (difficulty == 2) {
      for (Vegetables tempVeg : Diff2vegetables) {
        tempVeg.draw(g);
      }
      if(!bomb1.hasBeenCut)
      {
        bomb1.draw(g);
      }

    }

    if (difficulty == 3) {
      for (Vegetables tempVeg : Diff3vegetables) {
        tempVeg.draw(g);
      }
      if(!bomb1.hasBeenCut)
      {
        bomb1.draw(g);
      }
    }

  }


  //call the move methods in other classes to update positions
  //this method is constantly called from run(). By doing this, movements appear fluid and natural. If we take this out the movements appear sluggish and laggy


  //handles all collision detection and responds accordingly
  public void checkCollision(){

    Vegetables[] vegetables = {veg1,veg2,veg3,veg4,veg5};
  for(Vegetables tempVeg : vegetables)
  {
    if(tempVeg.y <= 0){
      tempVeg.y = 0;
    }
    if(tempVeg.y >= GAME_HEIGHT - Vegetables.VEG_DIAMETER){
      tempVeg.y = GAME_HEIGHT - Vegetables.VEG_DIAMETER;
    }

  }

    if(bomb1.y <= 0){
      bomb1.y = 0;
    }
    if(bomb1.y >= GAME_HEIGHT - Bomb.BOMB_DIAMETER){
      bomb1.y = GAME_HEIGHT - Bomb.BOMB_DIAMETER;
    }

  }


  public double[] getQuadratic(int spawnSeed){
  double[] quadraticFactor = {0, 0, 0, 0, 0};
  //A H M endx startx


  switch (spawnSeed){
    case 1:
      quadraticFactor[0] = 0.01;
      quadraticFactor[1] = 500;
      quadraticFactor[2] = 0;
      quadraticFactor[3] = 744;
      quadraticFactor[4] = 255;
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
      quadraticFactor[3] = 808;
      quadraticFactor[4] = 492;
      return quadraticFactor;
    case 5:
      quadraticFactor[0] = 0.01;
      quadraticFactor[1] = 360;
      quadraticFactor[2] = 140;
      quadraticFactor[3] = 575;
      quadraticFactor[4] = 145;
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
    cutVeg.hasBeenCut = true;
    combo = combo + 1;
    if(combo >= 10) combo = 10;
    score = score + (100*combo);
    resetVeg(cutVeg);
  }


  public void bombTrigger(Bomb bomb)
  {
    bomb.hasBeenCut = true;
    lives = lives -1;
    combo = 0;
    resetBomb(bomb);
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

  public int getEndX(int spawnSeed)
  {
    double[] tempArray = getQuadratic(spawnSeed);
    return (int) tempArray[3];
  }


  public void getStartX(int spawnSeed)
  {
    double[] tempArray = getQuadratic(spawnSeed);
    startX = (int) tempArray[4];
  }

  public void spawnVeg(int spawnSeed, Vegetables veg)
  {
    if(veg.mouseIntersects)
    {
      removeVeg(veg);
    } else if (!veg.hasBeenCut)
    {
      getStartX(spawnSeed);
      veg.updateStep();
      veg.move(getEndX(spawnSeed), getA(spawnSeed), getH(spawnSeed), getM(spawnSeed));
      notBeenCut(veg);
    }
  }

  public void spawnBomb(int spawnSeed, Bomb bomb)
  {
    if(bomb.mouseIntersects)
    {
      bombTrigger(bomb);
    } else if (!bomb.hasBeenCut)
    {
      getStartX(spawnSeed);
      bomb.updateStep();
      bomb.move(getEndX(spawnSeed), getA(spawnSeed), getH(spawnSeed), getM(spawnSeed));
      bombEdge(bomb);
    }
  }

  public void resetVeg(Vegetables veg)
  {
    veg.stepX = 0;
    veg.spawnCounter = 0;
    veg.mouseIntersects = false;
    veg.hasBeenCut = false;
    veg.startXSet = false;
    veg.seedSet = false;
  }

  public void resetBomb(Bomb bomb)
  {
    bomb.stepX = 0;
    bomb.spawnCounter = 0;
    bomb.mouseIntersects = false;
    bomb.hasBeenCut = false;
    bomb.startXSet = false;
    bomb.seedSet = false;
  }

 public void notBeenCut(Vegetables veg)
 {
   if(veg.y == GAME_HEIGHT - 20){
     veg.checkDeduction();
   }
 }

  public void bombEdge(Bomb bomb)
  {
    if(bomb.y >= GAME_HEIGHT - 20){
      if(bomb.checkBombFloor())
      {
        resetBomb(bomb);
      }
    }
  }



 public void difficultyLevel() {
   if (score >= 1 && score <= 1000) {
     difficulty = 1;
   } else if (score > 1000 && score <= 2500) {
     difficulty = 2;
   } else if (score > 2500 && score <= 3000) {
     difficulty = 3;
   }
 }

  Random random = new Random();


  public void setSeed()
  {
    if(random.nextBoolean()) veg1.setSeed(1);
    else veg1.setSeed(2);

    if(random.nextBoolean()) veg2.setSeed(2);
    else veg2.setSeed(3);

    if(random.nextBoolean()) veg3.setSeed(4);
    else veg3.setSeed(5);




  }


  //run() method is what makes the game continue running without end. It calls other methods to move objects,  check for collision, and update the screen
  public void run(){
    //the CPU runs our game code too quickly - we need to slow it down! The following lines of code "force" the computer to get stuck in a loop for short intervals between calling other methods to update the screen.
    long lastTime = System.nanoTime();
    double amountOfTicks = 60;
    double ns = 1000000000/amountOfTicks;
    double delta = 0;
    long now;
    int i2 = 0;
    while(true){ //this is the infinite game loop
      now = System.nanoTime();
      delta = delta + (now-lastTime)/ns;
      lastTime = now;

      //only move objects around and update screen if enough time has passed
      if (delta >= 1) {
        spawnVeg(1, veg1);


        /*
        setSeed();
        difficultyLevel();
        if(difficulty==1)
          spawnVeg(veg1.spawnSeed, veg1);
        if(difficulty==2)
        {
        spawnVeg(veg1.spawnSeed, veg1);
        spawnVeg(veg2.spawnSeed, veg2);
        spawnBomb(1, bomb1);
        }
        if(difficulty==3)
        {
          spawnVeg(veg1.spawnSeed, veg1);
          spawnVeg(veg2.spawnSeed, veg2);
          spawnBomb(1, bomb1);
        }
        System.out.println(lives);

        //System.out.println(bomb1.stepX);

         */


        checkCollision();
        repaint();
        delta--;
      }
    }
  }

  //if a key is pressed, we'll send it over to the PlayerBall class for processing


}
