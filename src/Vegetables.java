/* PlayerBall class defines behaviours for the player-controlled ball  

child of Rectangle because that makes it easy to draw and check for collision

In 2D GUI, basically everything is a rectangle even if it doesn't look like it!
*/
import java.awt.*;
import java.awt.event.*;

public class Vegetables extends Rectangle{

    public int yVelocity;
    public int xVelocity;
    public final int SPEED = 5; //movement speed of ball
    public static final int VEG_DIAMETER = 20; //size of ball

    //constructor creates ball at given location with given dimensions
    public Vegetables(int x, int y){
        super(x, y, VEG_DIAMETER, VEG_DIAMETER);
    }


    //called whenever the movement of the ball changes in the y-direction (up/down)
    public void setYDirection(int yDirection){
        yVelocity = yDirection;
    }

    //called whenever the movement of the ball changes in the x-direction (left/right)
    public void setXDirection(int xDirection){
        xVelocity = xDirection;
    }

    //called frequently from both PlayerBall class and GamePanel class
    //updates the current location of the ball
    public void move(){
        y = y + yVelocity;
        x = x + xVelocity;
    }

    //called frequently from the GamePanel class
    //draws the current location of the ball to the screen
    public void draw(Graphics g){
        g.setColor(Color.black);
        g.fillRect(x, y, VEG_DIAMETER, VEG_DIAMETER);
    }

}