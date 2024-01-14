/* PlayerBall class defines behaviours for the player-controlled ball  

child of Rectangle because that makes it easy to draw and check for collision

In 2D GUI, basically everything is a rectangle even if it doesn't look like it!
*/
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

public class Vegetables extends Rectangle{

    int stepX = 0;
    static volatile boolean isInitialized = false;

    int callCount = 0;
    boolean mouseIntersects = false;
    public static final int VEG_DIAMETER = 20; //size of ball

    //constructor creates ball at given location with given dimensions
    public Vegetables(int x, int y){
        super(x, y, VEG_DIAMETER, VEG_DIAMETER);
    }

    //Makes the x speed factor negative

    private void initializationLogic() {
        callCount++;
    }

    synchronized void updateStep() {
        if (!isInitialized) {
            initializationLogic();
            isInitialized = true;
            stepX = GamePanel.startX;
        }
    }



    
    //called whenever the movement of the ball changes in the y-direction (up/down)

    //called whenever the movement of the ball changes in the x-direction (left/right)

    //called frequently from both PlayerBall class and GamePanel class
    //updates the current location of the ball

    public void move(double endX, double a, double h, double m){
        double xMidPoint;
        double yMidPoint;
        stepX = stepX +1;
        if(stepX >= endX)
        {
            System.out.println("glsa");
            stepX = (int) endX;
        }
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
        g.setColor(Color.black);
        g.fillRect(x, y, VEG_DIAMETER, VEG_DIAMETER);
    }

}