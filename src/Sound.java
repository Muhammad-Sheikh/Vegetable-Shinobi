
import java.awt.*;


public class Sound{

    //dimensions of window
    public static final int GAME_WIDTH = 1024;
    public static final int GAME_HEIGHT = 600;
    public static int startX = 0, MouseY = 0, MouseX = 0;

    public int lives = 3;

    public Thread musicThread;
    public Image image;
    public Graphics graphics;


    public Sound(){
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

            }
        }
    }

    //if a key is pressed, we'll send it over to the PlayerBall class for processing


}
