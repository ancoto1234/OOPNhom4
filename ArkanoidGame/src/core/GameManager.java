package core;

import java.awt.*;

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
    private Renderer renderer;

    // Quản lí ảnh
    private HashMap<String, BufferedImage> images;

    public GameManager(Renderer renderer){
        this.renderer = renderer;
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

            ball = new Ball(390, 380, 50, 50, 2, -2, 3);
            ball.setImage(getImage("ball"));
            
            loadLevel(1);
        } catch (Exception e) {
            System.out.println("Error initializing game objects " + e.getMessage());
        }
        this.gameState = "PLAYING";
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
       if (gameState.equals("PLAYING")){
           HandleInput();
           ball.move();
           ball.bounceOff();
           checkCollisions();

           if (allBricksDestroyed()){
               WinGame();
               return;
           }

           if (ball.getY() > Renderer.SCREEN_HEIGHT) {
               gameOver();
               return;
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

    @Override
    public void keyTyped(KeyEvent e) {

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

    public void gameOver() {
        gameState = "GAME OVER";
        if (renderer != null) {
            renderer.gameFinished("GAME OVER", score);
        }
    }
    public void WinGame(){
        gameState = "WIN";
        if (renderer != null) {
            renderer.gameFinished("CONGRATULATIONS!", score);
        }
    }

    public String getGameState() {
        return gameState;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


//    @Override
//    public void keyTyped(KeyEvent e) {
//
//    }
/*
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }*/
}