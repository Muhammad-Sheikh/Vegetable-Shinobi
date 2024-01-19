/* PlayerBall class defines behaviours for the player-controlled ball  

child of Rectangle because that makes it easy to draw and check for collision

In 2D GUI, basically everything is a rectangle even if it doesn't look like it!
*/
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

public class Bomb extends Rectangle{

    int stepX = 0, spawnSeed;

    int  spawnCounter = 0;
    boolean mouseIntersects = false, hasBeenCut = false, startXSet = false, seedSet = false,resetBomb = false;
    public static final int BOMB_DIAMETER = 20; //size of ball

    //constructor creates ball at given location with given dimensions
    public Bomb(int x, int y){
        super(x, y, BOMB_DIAMETER, BOMB_DIAMETER);
    }


    public void updateStep() {
        if(!startXSet)
        {
            stepX = GamePanel.startX;
            startXSet = true;
        }
    }

    public boolean checkDeduction() {
        spawnCounter++;
        if(spawnCounter == 6) return true;
        else return false;
    }

    public void setSeed(int seed) {
        if(!seedSet)
        {
            spawnSeed = seed;
            seedSet = true;
        }
    }



    //called whenever the movement of the ball changes in the y-direction (up/down)

    //called whenever the movement of the ball changes in the x-direction (left/right)

    //called frequently from both PlayerBall class and GamePanel class
    //updates the current location of the ball

    public void move(int endX, double a, double h, double m){
        double xMidPoint;
        double yMidPoint;
        stepX++;
        if(stepX >= endX) stepX = endX;
        yMidPoint = (a*(Math.pow((stepX-h), 2))+m);
        xMidPoint = stepX;

        x = (int) xMidPoint;
        y = (int) yMidPoint;
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //called frequently from the GamePanel class
    //draws the current location of the ball to the screen
    public void draw(Graphics g){
        g.setColor(Color.red);
        g.fillRect(x, y, BOMB_DIAMETER, BOMB_DIAMETER);
    }

}