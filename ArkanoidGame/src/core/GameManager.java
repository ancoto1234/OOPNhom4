package core;

import java.awt.*;

import objects.*;
import powerups.ExpandPadllePowerUp;
import powerups.FastBallPowerUp;
import powerups.PowerUp;

import java.util.*;

import level.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import javax.imageio.ImageIO;
import java.io.File;
import java.nio.Buffer;
import java.util.List;


public class GameManager implements KeyListener, ActionListener{
    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    private List<PowerUp> powerUps;
    private int score;
    private int lives;
    private String gameState;
    private Level currentLevel;
    private boolean leftPressed, rightPressed = false;
    private List<ActiveEffect> activeEffects;
    private int originalPaddleWidth;
    private BufferedImage originalPaddleImage;
    private Random rand;
    private boolean isBallOnPaddle;

    public HashMap<String, BufferedImage> getImages() {
        return images;
    }

    // Quản lí ảnh
    private HashMap<String, BufferedImage> images;

    public GameManager(){
        this.score = 0;
        this.gameState = "START";
        
        if (this.gameState.equals("START")){
            loadImages();
            startGame();

            powerUps = new ArrayList<>();
            activeEffects = new ArrayList<>();
            rand = new Random();
        }
    }

    public void loadImages() {
        images = new HashMap<>();
        
        try {
            images.put("paddle", ImageIO.read(new File("ArkanoidGame/assets/paddle.png")));
            images.put("ball", ImageIO.read(new File("ArkanoidGame/assets/ball.png")));
            images.put("brick", ImageIO.read(new File("ArkanoidGame/assets/brick.png")));
            images.put("paddle_expand", ImageIO.read(new File("ArkanoidGame/assets/expanded_paddle.png")));
            images.put("powerup_expand", ImageIO.read(new File("ArkanoidGame/assets/power_expand.png")));
            images.put("powerup_fastball", ImageIO.read(new File("ArkanoidGame/assets/power_fastball.png")));
        } catch (Exception e) {
            System.out.println("Error loading images: " + e.getMessage());
        }

    }

    public BufferedImage getImage(String key) {
        return images.get(key);
    }

    public void startGame(){

        try {
            int paddleWidth = Renderer.SCREEN_WIDTH / 6;
            int paddleHeight = Renderer.SCREEN_HEIGHT / 27;
            int paddleX = (Renderer.SCREEN_WIDTH - paddleWidth) / 2;
            int paddleY = Renderer.SCREEN_HEIGHT - paddleHeight - 60;
            paddle = new Paddle(paddleX, paddleY, paddleWidth, paddleHeight, 15);
            paddle.setImage(getImage("paddle"));

            this.originalPaddleWidth = paddleWidth;
            this.originalPaddleImage = paddle.getImage();

            ball = new Ball(390, 380, 50, 50, 2, -2, 3);
            ball.setImage(getImage("ball"));
            
            loadLevel(1);
        } catch (Exception e) {
            System.out.println("Error initializing game objects " + e.getMessage());
        }   
    }

    public void loadLevel(int index) {
        switch (index) {
            case 1:
                currentLevel = new Level1(this);
                break;
            default:
                System.out.println("Level not found!");
                return;
        }

        bricks = currentLevel.getBricks();
    }

    public void render(Graphics g, Component observer) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);

        if (paddle != null) {
            paddle.render(g, observer);
        }

        if (ball != null) {
            ball.render(g, observer);
        }

        for (Brick brick : bricks) {
            if (brick != null) {
                brick.render(g, observer);
            }
        }

        if (powerUps != null) {
            // Dùng try-catch để tránh lỗi nếu vừa thêm/xóa đồng thời
            try {
                for (PowerUp pu : powerUps) {
                    if (pu != null) {
                        pu.render(g, observer);
                    }
                }
            } catch (java.util.ConcurrentModificationException e) {
                // Bỏ qua lỗi, frame sau sẽ vẽ đúng
            }
        }
        //notif WIN or LOSE
        if (gameState.equals("WIN")){
            g.setColor(Color.GREEN);
            g.setFont(new java.awt.Font("Arial", Font.BOLD, 50));
            g.drawString("YOU WIN!", 300, 300);
        } else if (gameState.equals("GAME OVER")){
            g.setColor(Color.RED);
            g.setFont(new java.awt.Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 300, 300);
        }
    }

    public void updateGame() {
       if (gameState.equals("START")){
           HandleInput();
           ball.move();
           ball.bounceOff();
           checkCollisions();
           updatePowerUps();

           updateActiveEffects();

           if (allBricksDestroyed()){
               WinGame();
           }
       }
    }

    private class ActiveEffect {
        public String type; //type of powerups
        public long expiryTime;  //Time that powerup expired
        public Runnable revertAction;

        public ActiveEffect(String type, long duration, Runnable revertAction){
            this.type = type;
            this.expiryTime = System.currentTimeMillis() + duration;  //expired = now + duration
            this.revertAction = revertAction;
        }
    }

    public void updatePowerUps(){
        if (powerUps == null) return;

        Iterator<PowerUp> it = powerUps.iterator();
        while (it.hasNext()){
            PowerUp pu = it.next();
            pu.update();

            if (paddle.getBounds().intersects(pu.getBounds())) {
                pu.activate(this);
                it.remove();
            }

            else if (pu.getY() > Renderer.SCREEN_HEIGHT){
                it.remove();
            }
        }
    }

    public void updateActiveEffects() {
        if (activeEffects == null) return;

        long currentTime = System.currentTimeMillis();
        Iterator<ActiveEffect> it = activeEffects.iterator();

        while (it.hasNext()) {
            ActiveEffect effect = it.next();

            if (currentTime > effect.expiryTime) {
                effect.revertAction.run();
                it.remove();
            }
        }
    }


    public void addActiveEffect(String type, long duration, Runnable revertAction){
        for (ActiveEffect effect : activeEffects){
            if (effect.type.equals(type)){
                effect.expiryTime = System.currentTimeMillis() + duration;
                return;
            }
        }

        activeEffects.add(new ActiveEffect(type, duration, revertAction));
    }

    public void spawnPowerUp(int x, int y){
        if (rand.nextFloat() < 0.3){
            int powerUpSize = 30;

            if (rand.nextBoolean()) {
                powerUps.add(new ExpandPadllePowerUp(x, y, powerUpSize, powerUpSize, getImage("powerup_expand")));
            } else {
                powerUps.add(new FastBallPowerUp(x, y, powerUpSize, powerUpSize, getImage("powerup_fastball")));
            }
        }
    }

    public void HandleInput(){
        if (leftPressed && !rightPressed){
            paddle.move_Left();
        }
        if (rightPressed && !leftPressed){
            paddle.move_Right();
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }

    public void checkCollisions(){
        if (ball.checkCollision(paddle)){
            ball.bounceOff();
        }

        Iterator <Brick> it = bricks.iterator();
        while (it.hasNext()){
            Brick brick = it.next();
            if (ball.checkCollision(brick)) {
                ball.bounceOff();
                brick.takeHits();
                if (brick.isDestroyed()) {
                    it.remove();
                    spawnPowerUp(brick.getX(), brick.getY());
                    break;
                }
            }

        }
    }


    public boolean allBricksDestroyed() {
        return bricks.isEmpty();
    }

    public void gameOver(){
        gameState = "GAME OVER";
    }

    public void WinGame(){
        gameState = "WIN";
    }

    public String getGameState() {
        return gameState;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public Paddle getPaddle() {
        return paddle;
    }

    public Ball getBall() {
        return ball;
    }

    public BufferedImage getOriginalPaddleImage() {
        return originalPaddleImage;
    }

    public int getOriginalPaddleWidth() {
        return originalPaddleWidth;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}