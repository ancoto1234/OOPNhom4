package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Component;
import objects.*;
import powerups.PowerUp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import level.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import javax.imageio.ImageIO;
import java.io.File;
import java.nio.Buffer;



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

    // Quản lí ảnh
    private HashMap<String, BufferedImage> images;

    public GameManager(){
        this.score = 0;
        this.gameState = "START";
        
        if (this.gameState == "START"){
            loadImages();
            startGame();
        }
    }

    public void loadImages() {
        images = new HashMap<>();
        
        try {
            images.put("paddle", ImageIO.read(new File("ArkanoidGame/assets/paddle.png")));
            images.put("ball", ImageIO.read(new File("ArkanoidGame/assets/ball.png")));
            images.put("brick", ImageIO.read(new File("ArkanoidGame/assets/brick.png")));
            
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

            ball = new Ball(390, 530, 50, 50, 2, -2, 3);
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
    }

    public void updateGame() {
        HandleInput();
        ball.move();
        ball.bounceOff();
        checkCollisions();
        
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
        for (Brick brick : bricks) {
            if (ball.checkCollision(brick)) {
                ball.bounceOff();
                brick.takeHits();
                if (brick.isDestroyed()) {
                    bricks.remove(brick);
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
}