import javax.swing.ImageIcon;
import java.awt.Rectangle;

/** Enemy
  * ICS4U1-14
  * Description: Earth Defender
  *              Enemy sprite. Inherits from Sprite and implements Shared constants. 
  * 
  * @author Timothy Mui
  * @version May 27, 2014
  */

public class Enemy extends Sprite implements Shared {

    private final String enemy = "/images/aliens/1.png";
    private final int WIDTH = 32;
    private final int HEIGHT = 16;
    private int points;

    // Constructor
    public Enemy(int x, int y, int points) {
        this.x = x;
        this.y = y;
        this.points = points;

        ImageIcon ii = new ImageIcon(this.getClass().getResource(enemy));
        setImage(ii.getImage());

    }
    
    // Moves the enemy
    public void move (String dir){
        if (dir == "left" && getX() > 32){
            setX(getX()-32);
        }
        else if (dir == "right" && getX()<FIELD_WIDTH -32){
            setX(getX()+32);
        }
        else if (dir == "down"){
            setY(getY()+16);
        }
    } 
    
    // Collision
    public boolean collision (Rectangle bullet){
        return bullet.intersects(getRect());
    }
    
    // Collision with Earth
    public boolean invasion (){
        return EARTH.intersects(getRect());
    }
    
    // Get Rectangle
    public Rectangle getRect (){
        return new Rectangle(getX(), getY(), WIDTH, HEIGHT);
    }
    
    // Enemy is hit
    public void hit (){
        die();
    }
    
    // Sets point value of the enemy
    private void setPoints (int points){
        this.points=points;
    }
    
    // Returns the point value of the enemy
    public int getPoints (){
        return points;
    }
}