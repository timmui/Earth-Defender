import javax.swing.ImageIcon;
import java.awt.Rectangle;

/** Fortress
  * ICS4U1-14
  * Description: Earth Defender
  *              Bullet sprite for player and enemy. Inherits from Sprite and implements Shared constants. 
  * 
  * @author Timothy Mui
  * @version May 27, 2014
  */

public class Fortress extends Sprite implements Shared {

    private String fortress = "/images/fortress/1.png";
    private final int WIDTH = 32;
    private final int HEIGHT = 32;
    private int damage = 0;
    
    // Constructor
    public Fortress(int x, int y) {
        this.x = x;
        this.y = y;

        ImageIcon ii = new ImageIcon(this.getClass().getResource(fortress));
        setImage(ii.getImage());
    }
    
    // Fortress gets hit
    public void hit (){
        damage++;
        
        // Change image based on damage
        if (damage == 2){
            fortress = "/images/fortress/2.png";
            ImageIcon ii = new ImageIcon(this.getClass().getResource(fortress));
            setImage(ii.getImage());
        }
        else if (damage == 4){
            fortress = "/images/fortress/3.png";
            ImageIcon ii = new ImageIcon(this.getClass().getResource(fortress));
            setImage(ii.getImage());
        }
        else if (damage >= 6){
            die();
        }
    } 
    
    // Collision
    public boolean collision (Rectangle bullet){
        return bullet.intersects(getRect());
    }
    
    // Get Rectangle
    public Rectangle getRect (){
        return new Rectangle(getX(), getY(), WIDTH, HEIGHT);
    }
       
}