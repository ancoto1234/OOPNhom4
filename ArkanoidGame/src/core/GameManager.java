package core;

import objects.Ball;
import objects.Brick;
import objects.Paddle;
import powerups.PowerUp;

import java.util.ArrayList;
import java.util.List;
import level.Level;
import java.awt.event.*;

public class GameManager implements KeyListener, ActionListener{
    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    private List<PowerUp> powerUps;
    private int score;
    private int lives;
    private String gameState;
    private Level currentLevel;

    public GameManager(){
        this.ball = new Ball();
        this.paddle = new Paddle();
        this.bricks = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        this.score = 0;
        this.lives = 5;
        this.gameState = "MENU";
        this.currentLevel = new Level();
    }

    public void startGame(){
        if (gameState.equals("MENU")){
            gameState = "PLAYING";
            score = 0;
            lives = 3;
            currentLevel.loadLevel();
            bricks.addAll(currentLevel.getBricks());
        }
    }

    public void updateGame(){

    }

    public void HandleInput(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            paddle.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            paddle.moveRight();
        }
    }

    public void checkCollisions(){
        if (gameState.equals("PLAYING")){
            if (ball.checkCollision(paddle)){
                ball.bounceOff();
            }
            for (int i = bricks.size() - 1; i >= 0; i++){
                Brick brick = bricks.get(i);
                if (!brick.isDestroyed() && ball.checkCollision(brick)){
                    brick.takeHit();
                    ball.bounceOff();
                    score += brick.getHitPoint();
                    if (brick.isDestroyed()) {
                        bricks.remove(i);
                        if (Math.random() < 0.3) {
                            powerUps.add(new PowerUp(brick.getX(), brick.getY()));
                        }
                    }
                }
            }
        }
    }

    public void gameOver(){
        gameState = "GAME OVER";
    }
}