import javax.swing.ImageIcon;
import java.util.Random;

/** Mothership
  * ICS4U1-14
  * Description: Earth Defender
  *             Mothership sprite. Inherits from Enemy and implements Shared constants. This enemy picks a random
  *             point value when instantiated.
  * 
  * @author Timothy Mui
  * @version May 27, 2014
  */

public class Mothership extends Enemy implements Shared {

    private final String enemy = "/images/aliens/mothership.png";
    private final int WIDTH = 28;
    private final int HEIGHT = 14;
    private int points = (new Random().nextInt(8000))+2000; // Sets random point value for the mothership
        
    // Constructor
    public Mothership () {
        //Initialize parent
        super(0,32,0);
       
        ImageIcon ii = new ImageIcon(this.getClass().getResource(enemy));
        setImage(ii.getImage());

    }
    
    // Mothership gets hit
    public void hit (){
        setX (0);
        die();
    }
    
    // Returns point value of the mothership
    public int getPoints(){
        return points;
    }
    
    // Moves mothership
    public void move (){
        if (getX() < FIELD_WIDTH)
            setX(getX()+3);
        else{
            die();
            setX(-30);
        }
    } 
}