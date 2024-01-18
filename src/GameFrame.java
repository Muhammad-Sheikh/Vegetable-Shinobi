/* GameFrame class establishes the frame (window) for the game
It is a child of JFrame because JFrame manages frames
Runs the constructor in GamePanel class

*/ 
import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame{

  GamePanel GamePanel;
  MainMenuPanel MenuPanel;
  HelpPanel HelpPanel;
  static boolean gamePanel, helpPanel;
  private boolean panelFound = false;
  public GameFrame(){
    MenuPanel = new MainMenuPanel(); //run GamePanel constructor
    this.add(MenuPanel);
    this.setTitle("Vegetable Shinobi Title Screen"); //set title for frame
    this.setResizable(false); //frame can't change size
    this.setBackground(Color.white);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X button will stop program execution
    this.pack();//makes components fit in window - don't need to set JFrame size, as it will adjust accordingly
    this.setVisible(true); //makes window visible to user
    this.setLocationRelativeTo(null);//set window in middle of screen

    stateChecker();

  }

  public void stateChecker()
  {
    while (!panelFound) {
      System.out.println("sww");

      if (gamePanel)
      {
        openGamePanel();
      }

      if (helpPanel)
      {
        openHelpPanel();
      }

    }
  }

  public void openGamePanel()
  {
    GamePanel = new GamePanel(); //run GamePanel constructor
    this.add(GamePanel);
    this.setTitle("Vegetable Shinobi"); //set title for frame
    this.setResizable(false); //frame can't change size
    this.setBackground(Color.white);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X button will stop program execution
    this.pack();//makes components fit in window - don't need to set JFrame size, as it will adjust accordingly
    this.setVisible(true); //makes window visible to user
    this.setLocationRelativeTo(null);//set window in middle of screen
    panelFound = true;
  }

  public void openHelpPanel()
  {
    HelpPanel = new HelpPanel(); //run GamePanel constructor
    this.add(HelpPanel);
    this.setTitle("Vegetable Shinobi : Instructions"); //set title for frame
    this.setResizable(false); //frame can't change size
    this.setBackground(Color.white);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X button will stop program execution
    this.pack();//makes components fit in window - don't need to set JFrame size, as it will adjust accordingly
    this.setVisible(true); //makes window visible to user
    this.setLocationRelativeTo(null);//set window in middle of screen
    panelFound = true;

  }
  
}