package core;

import java.awt.*;

import objects.*;
import powerups.PowerUp;

import java.util.HashMap;
import java.util.List;
import level.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;


public class GameManager implements KeyListener, ActionListener{
    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    private List<PowerUp> powerUps;
    private int score;
    private int lives;
    private int choosedLevel;
    private int totalLevel = 3;
    private String gameState;
    private Level currentLevel;
    private boolean leftPressed, rightPressed = false;
    private boolean isSpaced;

    // Quản lí ảnh
    private HashMap<String, BufferedImage> images;

    public GameManager(){
        this.score = 0;
        this.lives = 2;
        this.isSpaced = false;
        this.gameState = "START";

        if (this.gameState == "START"){
            loadImages();
            initGame();
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

    public void initGame(){
        try {
            int paddleWidth = Renderer.SCREEN_WIDTH / 6;
            int paddleHeight = Renderer.SCREEN_HEIGHT / 27;
            int paddleX = (Renderer.SCREEN_WIDTH - paddleWidth) / 2;
            int paddleY = Renderer.SCREEN_HEIGHT - paddleHeight - 40;
            paddle = new Paddle(paddleX, paddleY, paddleWidth, paddleHeight, 15);
            paddle.setImage(getImage("paddle"));

            int ballWidth = 50;
            int ballHeight = 50;
            int ballX = paddleX + paddleWidth / 2 - ballWidth / 2;
            int ballY = paddleY - ballHeight - 5;

            ball = new Ball(ballX, ballY, ballWidth, ballHeight, 2, -2, 2);
            ball.setImage(getImage("ball"));

            loadLevel(choosedLevel);
        } catch (Exception e) {
            System.out.println("Error initializing game objects " + e.getMessage());
        }
    }

    public void resetGame() {
        this.score = 0;
        this.lives = 2;
        this.choosedLevel = 1;
        this.gameState = "START";
        this.isSpaced = false;
        initGame();
    }

    public void loadLevel(int index) {
        switch (index) {
            case 1:
                currentLevel = new Level1(this);
                break;
            case 2:
                currentLevel = new Level2(this);
                break;
            case 3:
                currentLevel = new Level3(this);
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
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 25);
        g.drawString("Lives: " + lives, 700,25);

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
            if (isSpaced == false) {
                return;
            }
            HandleInput();
            ball.move();
            ball.bounceOff();
            checkCollisions();

            if (ball.getY() > Renderer.SCREEN_HEIGHT) {
                lives--;
                if (lives <= 0) {
                    gameOver();
                    return;
                } else {
                    ball.resetBall(paddle);
                    paddle.resetPaddle();
                    isSpaced = false;
                    return;
                }
            }

            if (allBricksDestroyed()){
                WinGame();
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

    public void checkCollisions(){
        if (ball.checkCollision(paddle)){
            ball.bounceOff();
        }
        for (Brick brick : bricks) {
            if (ball.checkCollision(brick)) {
                this.score += 10;
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

    public void WinGame(){
        if (choosedLevel < totalLevel) {
            choosedLevel++;
            loadLevel(choosedLevel);
            ball.resetBall(paddle);
            paddle.resetPaddle();
            isSpaced = false;
            gameState = "START";
        } else {
            gameState = "WIN";
        }
    }

    public boolean allBricksDestroyed() {
        return bricks.isEmpty();
    }

    public String getGameState() {
        return gameState;
    }

    public void setLevel(int level) {
        this.choosedLevel = level;
    }

    public void setupLevel(int level) {
        loadLevel(level);
    }

    public int getCurrentLevel() {
        return this.choosedLevel;
    }

    public int getScore() {
        return this.score;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            isSpaced = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}