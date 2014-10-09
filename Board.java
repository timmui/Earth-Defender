import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

/** Board
  * Description: Earth Defender
  *              Main JFrame for the game. Instantiates and handles GUI. Implements Shared constants.
  * 
  * @author Timothy Mui
  * @version May 27, 2014
  */

public class Board extends JFrame implements Shared {
    private JLabel score;
    private JLabel lives;
    private JPanel statusPanel;
        
    public Board ()
    {               
        // Initializes the Score and Lives labels
        score = new JLabel ("Score: ");
        lives = new JLabel ("Lives: ");
        
        // Initializes the Field
        add (new Field(score, lives), BorderLayout.CENTER);
        
        setTitle("Earth Defender");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        
        // statusPanel
        statusPanel = new JPanel ();
        
        statusPanel.add (score);
        statusPanel.add (lives);
        
        add (statusPanel,BorderLayout.NORTH);
        
        
    }
}