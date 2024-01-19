import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpPanel extends JPanel {
    public  static  int GAME_WIDTH = 600, GAME_HEIGHT = 300;
    GameFrame panelCaller = new GameFrame();

    public HelpPanel()
    {
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label1 = new JLabel("Hello!");
        JLabel label2 = new JLabel("Salut!");
        JLabel label3 = new JLabel("Bruh City 32");
        JButton playButton = new JButton("Play Game");


        playButton.setPreferredSize(new Dimension(150, 50));

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComponent comp = (JComponent) e.getSource();
                Window win = SwingUtilities.getWindowAncestor(comp);
                win.dispose();
                panelCaller.openGamePanel();
            }
        });

        label1.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label1);

        add(Box.createVerticalStrut(10));

        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label2);

        add(Box.createVerticalStrut(10));

        label3.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label3);

        add(Box.createVerticalStrut(10));

        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(playButton);


    }
}
