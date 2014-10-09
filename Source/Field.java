import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.lang.String;
import javax.swing.JOptionPane;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;

/** Field
  * Description: Earth Defender
  *              Field class instantiates all game variables and runs the animation cycle. Also handles all of the 
  *              keyboard input and GUI interactions. Implements Runnable (for threads) and Shared constants.
  * 
  * @author Timothy Mui
  * @version May 27, 2014
  */

public class Field extends JPanel implements Runnable, Shared { 
    
    // --------------- Instance Variables ---------------
    private Dimension d;
    private ArrayList enemies;
    private ArrayList fortresses;
    private Player player;
    private Bullet bullet;
    private Bullet enemyBullet;
    private Mothership mothership;

    private int enemyX = BORDER_LEFT;
    private int enemyY = 96;
    private int fortX = 110;
    private int fortY = FIELD_HEIGHT - 150;
    private int direction;
    
    private int score;
    private int lives;
    private JLabel scoreLabel;
    private JLabel livesLabel;
    
    private int frameCount;
    private int enemyMoveCount;

    private boolean ingame;
    private final String enemypix = "/images/aliens/1.png";
    
    private Thread runner;

    // --------------- Constructor ---------------
    public Field(JLabel scoreLabel, JLabel livesLabel) 
    {
        d = new Dimension(FIELD_WIDTH, FIELD_HEIGHT);
        setBackground(Color.black);
        
        this.scoreLabel = scoreLabel;
        this.livesLabel = livesLabel;
        
        // Opening dialog
        int result = JOptionPane.showConfirmDialog(this, String.format("<html><center><b>Welcome to Earth Defender</b></center><br><br>"+
                                                          "You must defend the earth from total annihilation.<br>Use the "+
                                                          "left and right keys to move your ship and press space to shoot.<br>"+
                                                          "Rack up points by killing the enemies and get a surprise point bonus "+ 
                                                          "if you hit the mothership.<p><center><i>Godspeed.</i></center><br>"+
                                                          String.format("Current High Score: %d <br>",readScore()) +
                                                          "Press OK when you are ready or press CANCEL if you are too scared.</html>")
                                          ,"Earth Defender",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (result == JOptionPane.OK_OPTION)
            // Call to game initializer
            gameInit();
        else if (result == JOptionPane.CANCEL_OPTION)
            System.exit(0);
        
        // Updates the labels.
        setStatus();
        
        // Keyboard Handler
        KeyHandler keyHandler = new KeyHandler ();
        addKeyListener (keyHandler);
        
        setFocusable(true);
        setDoubleBuffered(true);
    }
    
    // --------------- Game Initializer ---------------
    // Main Game Initializer. Also called when restarting game.
    public void gameInit() {
        // Stop Animation Cycle
        stop();
        
        //Initialize Instance Variables
        score = 0;
        lives = 3;
        frameCount = 0;
        enemyMoveCount = 0;
        direction = -1;
        ingame=true;
        
        //Make Sprites. Sprites are nullified to prevent contamination from previous game.
        enemies = null;
        makeEnemy();
        
        fortresses = null;
        makeFortress();
        
        player = null;
        player = new Player();
        
        bullet = null;
        bullet = new Bullet();
        
        enemyBullet = null;
        enemyBullet = new Bullet();
        
        mothership = null;
        mothership = new Mothership ();
        mothership.setVisible (false);
        
        // Start background music
        playSound();
        
        // Start animation cycle
        start ();
    }
    
    // Makes Enemy array
    private void makeEnemy (){
        enemies = new ArrayList();
        int points = 300;
        ImageIcon ii = new ImageIcon(this.getClass().getResource("/images/aliens/3.png"));
            
        for (int y=0; y < 6; y++) {
            // Changes image and point value depending on row
            if (y == 2){
                ii = new ImageIcon(this.getClass().getResource("/images/aliens/2.png"));
                points = 200;
            }
            else if (y == 4){
                ii = new ImageIcon(this.getClass().getResource("/images/aliens/1.png"));
                points = 100;
            }
            
            for (int x=0; x < 6; x++) {
                Enemy enemy = new Enemy (enemyX + 64*x, enemyY + 32*y, points);
                enemy.setImage(ii.getImage());
                enemies.add(enemy);
            }
        }
    }
    
    // Makes Fortress Array
    private void makeFortress (){
        fortresses = new ArrayList();
        ImageIcon ii = new ImageIcon(this.getClass().getResource("/images/fortress/1.png"));
        
        for (int x=0; x < 4; x++){
            Fortress fort = new Fortress (fortX + 100*x, fortY);
            fort.setImage(ii.getImage());
            fortresses.add(fort);
        }
    }
    
    // --------------- Key Handler ---------------
    private class KeyHandler extends KeyAdapter {
        public void keyPressed(KeyEvent event) {
            int keyCode = event.getKeyCode();
            
            if(keyCode == KeyEvent.VK_LEFT) {
                player.move (-1);  
            }
            
            else if(keyCode == KeyEvent.VK_RIGHT) {
                player.move (1);
            }
            else if(keyCode == KeyEvent.VK_SPACE) {
                if (!bullet.isVisible())
                    bullet.shot (player.getX()+8,GROUND-16,"up");
            }
            else if(keyCode == KeyEvent.VK_ESCAPE) {
                // End Game
                endGame(false);
            }
            else if(keyCode == KeyEvent.VK_W) {
                // Cheat code to win
                enemies.clear();
            }

        }
        
        public void keyReleased(KeyEvent event) {
            int keyCode = event.getKeyCode();
            
            if (keyCode == KeyEvent.VK_LEFT){
                player.move(0);
            }
            
            else if (keyCode == KeyEvent.VK_RIGHT){
                player.move(0);
            }
            
        }
    }
    // --------------- Animation Methods ---------------
    // Animation cycle
    private void animation (){
        frameCount ++;
             
        // Moving Enemies
        if (frameCount % 100 == 0)
            enemyMove();
        // Make Mothership
        if (frameCount % 500 == 0)
            mothership.setVisible (true);
        
        // Make enemy bullet
        if (frameCount% 100 ==0){
            enemyShoot();
        }
        
        // Move Mothership
        if (mothership.isVisible ()){
            mothership.move();
        }
        
        // Move Bullet
        if (bullet.isVisible ()){
            bullet.move();
        }
        
        // Move enemyBullet
        if (enemyBullet.isVisible()){
            enemyBullet.move();
        }
        
        //Collision Check
        if (bullet.isVisible()||enemyBullet.isVisible()){
            enemyCollision();
            fortressCollision();
            mothershipCollision();
            playerCollision();
        }
        invasion();
        
        //Check Win
        if (enemies.size()==0){
            player.die();
            endGame (true);
        }
        
        //Check Dead
        if (lives <= 0){
            player.die();
            endGame (false);
        }
        
        setStatus ();
    }
    
    // Starts animation thread
    public void start(){
        if (runner == null){
            runner = new Thread (this);
            runner.start ();
        }
    }
    
    // Stops animation thread
    public void stop (){
        if (runner != null){
            runner = null;
        }
    }
    
    // Runs animation thread
    public void run(){
        // Runs while in game (i.e. not in final or opening dialog)
        while (ingame){
            animation();
            
            // Updating
            repaint ();
            try {
                Thread.sleep (30);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            
            // Reset Frame Count
            if (frameCount == 1000)
                frameCount = 0;
        }
    }
    
    // Moves all the enemies together
    private void enemyMove (){ 
        enemyMoveCount ++;
            
        if (enemyMoveCount == 5 ||enemyMoveCount == 10){
            Iterator it = enemies.iterator();
                
            while (it.hasNext()) {
                Enemy enemy = (Enemy) it.next();
                
                enemy.move ("down");
            } 
            
            if (enemyMoveCount == 10)
                enemyMoveCount = 0;
        }
        else if (enemyMoveCount >= 1 && enemyMoveCount <= 4 ){
            Iterator it = enemies.iterator();
                
            while (it.hasNext()) {
                Enemy enemy = (Enemy) it.next();
                
                enemy.move ("right");
            }
        }
        else if (enemyMoveCount >= 6 && enemyMoveCount <= 9 ){
            Iterator it = enemies.iterator();
                
            while (it.hasNext()) {
                Enemy enemy = (Enemy) it.next();
                
                enemy.move ("left");
            }
        }          
    }
    // --------------- Collision Detection ---------------
    // Bullet on Enemies
    private void enemyCollision (){
        Iterator it = enemies.iterator();
                
        while (it.hasNext()) {
            Enemy enemy = (Enemy) it.next();
            
            if (enemy.collision(bullet.getRect())){
                score += enemy.getPoints();
                enemy.hit();
                bullet.die();
                it.remove();
            }
        }
    }
    
    // Bullet on Mothership
    private void mothershipCollision (){
        if (mothership.collision(bullet.getRect())){
            score += mothership.getPoints();
            bullet.die();
            mothership.hit();
        }
    }
    
    // Bullet on Fortress
    private void fortressCollision (){
        Iterator it = fortresses.iterator();
                
        while (it.hasNext()) {
            Fortress fort = (Fortress) it.next();
            
            // Player
            if (fort.collision(bullet.getRect())&& bullet.isVisible()){
                fort.hit();
                bullet.die();
            }
            
            // Enemy
            if (fort.collision(enemyBullet.getRect())&& enemyBullet.isVisible()){
                fort.hit();
                enemyBullet.die();
            }
            
            if (!fort.isVisible())
                    it.remove();
        }
    }
    
    // Bullet on Player
    private void playerCollision (){
        if (enemyBullet.isVisible()){
            if (player.collision(enemyBullet.getRect())){
                lives -= 1;
                enemyBullet.die();
            }
        }
    }
    
    // Enemy on Earth
    private void invasion (){
        Iterator it = enemies.iterator();
                
        while (it.hasNext()) {
            Enemy enemy = (Enemy) it.next();
            
            if (enemy.invasion()){
                endGame(false);
            }
        }
    }

    // --------------- Other Methods ---------------
    // Updates Score and Lives labels.
    private void setStatus (){
        scoreLabel.setText (String.format("Score: %d",score));
        livesLabel.setText (String.format("Lives: %d",lives));
    }
    
    // Game Over Screen
    private void endGame (boolean win){
        ingame = false;
        int result;
        
        // Winner Dialog
        if (win){
            // Highscore
            storeScore();
            
            result = JOptionPane.showConfirmDialog(this, String.format("<html><center><b>You have won!</b></center><br><br>"+
                                                          "Thank you for defending the Earth from the aliens. <br>"+
                                                          String.format("Final Score: %d <br>",score) +
                                                          String.format("Current High Score: %d <br>",readScore()) +
                                                          "<p><center><i>Godspeed.</i></center>"+
                                                          "<br><br>Press OK when you want to try again or press CANCEL if you are too scared.</html>")
                                          ,"Earth Defender",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        }
        
        // Loser Dialog
        else{
            result = JOptionPane.showConfirmDialog(this, String.format("<html><center><b>You have lost!</b></center><br><br>"+
                                                          "Darn. Those aliens have taken over. <br>"+
                                                          String.format("Final Score: %d <br>",score) +
                                                          String.format("Current High Score: %d <br>",readScore()) +
                                                          "<p><center><i>Godspeed.</i></center>"+
                                                          "<br><br>Press OK when you want to try again or press CANCEL if you are too scared.</html>")
                                          ,"Earth Defender",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        }
        
        if (result == JOptionPane.OK_OPTION){
            // Restart
            gameInit();
        }
        else if (result == JOptionPane.CANCEL_OPTION)
            System.exit(0); // Exit
    }
    
    // Enemy Shooting. Randomly selects one enemy and shoots.
    private void enemyShoot(){
        if (!enemyBullet.isVisible()&& enemies.size()> 1){
            int enemyInd = new Random ().nextInt(enemies.size()-1);
            
            Enemy enemy = (Enemy) enemies.get(enemyInd);
            enemyBullet.shot (enemy.getX()+8,enemy.getY()-16,"down");   
        }
        else if (!enemyBullet.isVisible()){
            Enemy enemy = (Enemy) enemies.get(0);
            enemyBullet.shot (enemy.getX()+8,enemy.getY()-16,"down"); 
        }
    }
    
    // Play Background Music
    public static synchronized void playSound() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(Field.class.getResourceAsStream(
                                                                                  "/sound/bg.wav"));
                    clip.open(inputStream);
                    clip.start(); 
                } catch (Exception e) {
                    System.out.println("err");
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
    
    // --------------- Highscore Methods --------------- 
    // Returns the last highscore
    private int readScore(){
        Scanner s = null;
        int value = 0;
        try {
            s = new Scanner(new BufferedReader(new FileReader("EarthDefender.highscore")));
            value = Integer.parseInt(s.next());
            s.close();
        }
        catch (IOException e) {
            System.err.println (e.getMessage());
        }
        
        return value;
        
    }
    
    // Stores the current score if it is greater than the last high score.
    private void storeScore (){
        
        if (readScore() < score){
            PrintWriter outputStream = null;
            System.out.println ("here");
            try{
                outputStream = new PrintWriter(new FileWriter("EarthDefender.highscore"));
                outputStream.println (score);
                outputStream.close();
            }
            catch (IOException e){
                System.err.println (e.getMessage());
            }
        }
    }



    // --------------- Draw Methods ---------------
    // Draws Enemy sprites
    public void drawEnemy(Graphics g) 
    {
        Iterator it = enemies.iterator();

        while (it.hasNext()) {
            Enemy enemy = (Enemy) it.next();

            if (enemy.isVisible()) {
                g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
            }

            if (enemy.isDying()) {
                enemy.die();
            }
        }
    }
    
    // Draws the Mothership sprite
    public void drawMothership(Graphics g) {

        if (mothership.isVisible()) {
            g.drawImage(mothership.getImage(), mothership.getX(), mothership.getY(), this);
        }
    }
    
    // Draws Fortress sprites
    public void drawFortress(Graphics g) 
    {
        Iterator it = fortresses.iterator();

        while (it.hasNext()) {
            Fortress fort = (Fortress) it.next();

            if (fort.isVisible()) {
                g.drawImage(fort.getImage(), fort.getX(), fort.getY(), this);
            }

            if (fort.isDying()) {
                fort.die();
            }
        }
    }
    
    // Draws the Player sprite
    public void drawPlayer(Graphics g) {

        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {
            player.die();
            endGame(false);
        }
    }
    
    // Draws the Player and Enemy bullet sprites
    public void drawBullet(Graphics g) {
        if (bullet.isVisible()){
            g.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
        }
        if (enemyBullet.isVisible()){
            g.drawImage(enemyBullet.getImage(), enemyBullet.getX(), enemyBullet.getY(), this);
        }
    }
    
    // --------------- Paint Method ---------------
    public void paint(Graphics g)
    {
      super.paint(g);

      g.setColor(Color.black);
      g.fillRect(0, 0, d.width, d.height);

      if (ingame) {

        drawEnemy(g);
        drawMothership(g);
        drawFortress(g);
        drawPlayer(g);
        drawBullet(g);
      }
            
      g.dispose();
    }
}