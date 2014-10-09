import java.awt.Rectangle;

/** Shared
  * Description: Earth Defender
  *              Repository for all shared constants between classes. 
  * 
  * @author Timothy Mui
  * @version May 27, 2014
  */

public interface Shared {
    // Shared Constants
    public static final int BOARD_WIDTH = 540;
    public static final int BOARD_HEIGHT = 600;
    public static final int FIELD_WIDTH = 540;
    public static final int FIELD_HEIGHT = 550;
    public static final int GROUND = BOARD_HEIGHT - 100;
    public static final int BORDER_LEFT = 30;
    public static final int BORDER_RIGHT = BOARD_WIDTH - BORDER_LEFT;
    public static final Rectangle EARTH = new Rectangle (0, FIELD_HEIGHT-150, FIELD_WIDTH,1); // Rectangle of the Earth
}