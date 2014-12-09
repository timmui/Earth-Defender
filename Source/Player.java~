import javax.swing.ImageIcon;
import java.awt.Rectangle;

/** Player
  * ICS4U1-14
  * Description: Earth Defender
  *              Player sprite. Inherits from Sprite and implements Shared constants. 
  * 
  * @author Timothy Mui
  * @version May 27, 2014
  */

public class Player extends Sprite implements Shared{

    private final int START_Y = GROUND; 
    private final int START_X = FIELD_WIDTH/2;
    private final int WIDTH = 16;
    private final int HEIGHT = 8;    
    private final String player = "/images/player.png";
    
    // Constructor
    public Player() {

        ImageIcon ii = new ImageIcon(this.getClass().getResource(player));

        setImage(ii.getImage());
        setX(START_X);
        setY(START_Y);
    }
    
    // Moves Player in a specified direction
    public void move (int dir){
        if (dir == -1 && getX() > BORDER_LEFT+10){
            setX(getX()-10);
        }
        else if (dir == 1 && getX()< BORDER_RIGHT-10){
            setX(getX()+10);
        }
    } 
    
    // Gets player's rectangle
    public Rectangle getRect (){
        return new Rectangle(getX(), getY(), WIDTH, HEIGHT);
    }
    
    // Collision
    public boolean collision (Rectangle bullet){
        return bullet.intersects(getRect());
    }
}