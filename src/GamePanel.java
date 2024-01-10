/* GamePanel class acts as the main "game loop" - continuously runs the game and calls whatever needs to be called

Child of JPanel because JPanel contains methods for drawing to the screen

Implements KeyListener interface to listen for keyboard input

Implements Runnable interface to use "threading" - let the game do two things at once

*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{

  //dimensions of window
  public static final int GAME_WIDTH = 1024;
  public static final int GAME_HEIGHT = 600;

  public Thread gameThread;
  public Image image;
  public Graphics graphics;
  public Vegetables veg;


  public GamePanel(){
    veg = new Vegetables(GAME_WIDTH/2, GAME_HEIGHT/2); //create a player controlled ball, set start location to middle of screen
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

  public void vegGravity()
  {
    veg.yVelocity = veg.yVelocity +1;
    if(veg.yVelocity > 2)
    {
      veg.yVelocity = 2;
    }

    veg.y = veg.y - veg.yVelocity;
    veg.move();
  }

  public void vegInverseGravity()
  {
    veg.yVelocity = veg.yVelocity - 1;
    if(veg.yVelocity > -2)
    {
      veg.yVelocity = -2;
    }

    veg.y = veg.y + veg.yVelocity;

    veg.move();
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
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.white);
    //g2d.rotate(Math.toRadians(45));
    veg.draw(g);
  }

  //call the move methods in other classes to update positions
  //this method is constantly called from run(). By doing this, movements appear fluid and natural. If we take this out the movements appear sluggish and laggy


  //handles all collision detection and responds accordingly
  public void checkCollision(){
    
    //force player to remain on screen
    if(veg.y<= 0){
      veg.y = 0;
    }
    if(veg.y >= GAME_HEIGHT - PlayerBall.BALL_DIAMETER){
      veg.y = GAME_HEIGHT-PlayerBall.BALL_DIAMETER;
    }
    if(veg.x <= 0){
      veg.x = 0;
    }
    if(veg.x + PlayerBall.BALL_DIAMETER >= GAME_WIDTH){
      veg.x = GAME_WIDTH-PlayerBall.BALL_DIAMETER;
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
        vegInverseGravity();
        sleep(20);
        vegGravity();
        System.out.println(veg.yVelocity);
        checkCollision();
        repaint();
        delta--;
      }
    }
  }

  //if a key is pressed, we'll send it over to the PlayerBall class for processing


}