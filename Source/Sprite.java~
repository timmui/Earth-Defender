import java.awt.Image;

/** Sprite
  * ICS4U1-14
  * Description: Earth Defender
  *              Parent Sprite class. All sprites inherit from this class.
  * 
  * @author Timothy Mui
  * @version May 27, 2014
  */

public class Sprite {
    
        private boolean visible;
        private Image image;
        protected int x;
        protected int y;
        protected boolean dying;
        protected int dx;
        
        
        // Constructor
        public Sprite() {
            visible = true;
        }
        
        // Kills Sprite
        public void die() {
            visible = false;
        }
        
        // Accessor method for visable
        public boolean isVisible() {
            return visible;
        }
        
        // Mutator method for visable
        protected void setVisible(boolean visible) {
            this.visible = visible;
        }
        
        // Mutator method for image
        public void setImage(Image image) {
            this.image = image;
        }
        
        // Accessor method for image
        public Image getImage() {
            return image;
        }
        
        // Mutator method for x coordinate
        public void setX(int x) {
            this.x = x;
        }
        
        // Mutator method for y coordinate
        public void setY(int y) {
            this.y = y;
        }
        
        // Accessor method for y coordinate
        public int getY() {
            return y;
        }
        
        // Accessor method for x coordinate
        public int getX() {
            return x;
        }

        // Mutator method for dying
        public void setDying(boolean dying) {
            this.dying = dying;
        }
        
        // Accessor method for dying
        public boolean isDying() {
            return this.dying;
        }
}