import javax.swing.ImageIcon;
import java.awt.Rectangle;

/** Bullet
  * Description: Earth Defender
  *              Bullet sprite for player and enemy. Inherits from Sprite and implements Shared constants. 
  * 
  * @author Timothy Mui
  * @version May 27, 2014
  */

public class Bullet extends Sprite implements Shared {

    private final String bullet  = "/images/bullet.png";
    private final int H_SPACE = 6;
    private final int V_SPACE = 1;
    private final int WIDTH = 10;
    private final int HEIGHT = 10;
    private String dir = "up";
    
    // Constructor
    public Bullet (){
        ImageIcon ii = new ImageIcon(this.getClass().getResource(bullet));
        setImage(ii.getImage());
    }
    
    // Bullet is shot
    public void shot (int x, int y, String dir){
        
        setVisible (true);
        this.dir = dir;
        
        setX (x);
        setY (y);
    }
    
    // Move Bullet
    public void move (){
        if (getY() > 0 && getY() < GROUND){
            setY(getY()+(dir == "up"?-10:10));
        }
        else {
            setVisible (false);
        }
    }
    
    // Returns the bullet's rectangle
    public Rectangle getRect (){
        return new Rectangle(getX(), getY(), WIDTH, HEIGHT);
    }
}