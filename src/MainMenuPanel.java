import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    GameFrame panelCaller = new GameFrame();

    public  static  int GAME_WIDTH =600, GAME_HEIGHT = 300;
    public MainMenuPanel()
    {
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        JLabel title = new JLabel("Welcome to Vegetable Shinobi!");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JButton playButton = new JButton("Play Game");
        JButton helpButton = new JButton("Instructions");
        JButton exitButton = new JButton("Exit");

        playButton.setPreferredSize(new Dimension(150, 50));
        helpButton.setPreferredSize(new Dimension(150, 50));
        exitButton.setPreferredSize(new Dimension(150, 50));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComponent comp = (JComponent) e.getSource();
                Window win = SwingUtilities.getWindowAncestor(comp);
                win.dispose();
                panelCaller.openGamePanel();
            }
        });



        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComponent comp = (JComponent) e.getSource();
                Window win = SwingUtilities.getWindowAncestor(comp);
                win.dispose();
                panelCaller.openHelpPanel();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //Spaces title from the top
        add(Box.createVerticalGlue());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
        add(Box.createVerticalStrut(20)); // Add some vertical space


        JPanel buttonControl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonControl.add(playButton);
        buttonControl.add(helpButton);
        buttonControl.add(exitButton);
        add(buttonControl);

        add(Box.createVerticalGlue());

    }

}
