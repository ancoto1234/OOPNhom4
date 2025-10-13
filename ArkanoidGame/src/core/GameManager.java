package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Component;
import objects.*;
import powerups.PowerUp;

import java.util.ArrayList;
import java.util.List;
import level.Level;
import java.awt.event.*;
import java.awt.image.BufferedImage;
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

    public GameManager(){
        startGame();
    }

    public void startGame(){

        try {
            BufferedImage paddleImage = ImageIO.read(new File("ArkanoidGame/assets/paddle.png"));
            BufferedImage ballImage = ImageIO.read(new File("ArkanoidGame/assets/ball.png"));
            BufferedImage brickImage = ImageIO.read(new File("ArkanoidGame/assets/brick.png"));


            paddle = new Paddle(350, 550, 100, 20, 15);
            paddle.setImage(paddleImage);
            ball = new Ball(390, 530, 20, 20, 2, -2, 3);
            ball.setImage(ballImage);
            bricks = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 10; j++) {
                    Brick brick = new Brick(60 + j * 70, 50 + i * 30, 60, 20, 2);
                    brick.setImage(brickImage);
                    bricks.add(brick);
                }
            }

        } catch (Exception e) {
            System.out.println("Error loading paddle image: " + e.getMessage());
        }   
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
        if (leftPressed){
            paddle.move_Left();
        }
        if (rightPressed){
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

    public void gameOver(){
        gameState = "GAME OVER";
    }
}