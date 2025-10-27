package core;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import level.*;
import objects.*;
import powerups.ExpandPadllePowerUp;
import powerups.FastBallPowerUp;
import powerups.PowerUp;


public class GameManager implements KeyListener, ActionListener{
    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    private List<PowerUp> powerUps;
    private int score;
    private int lives;
    private int maxLives;
    private int choosedLevel;
    private int totalLevel = 3;
    private String gameState;
    private Level currentLevel;
    private boolean leftPressed, rightPressed = false;
    private List<ActiveEffect> activeEffects;
    private int originalPaddleWidth;
    private BufferedImage originalPaddleImage;
    private Random rand;
    private boolean isBallOnPaddle;
    private Font font;

    //Image
    private BufferedImage heart;
    private BufferedImage damage;

    public HashMap<String, BufferedImage> getImages() {
        return images;
    }
    private boolean isSpaced;

    // Quản lí ảnh
    private HashMap<String, BufferedImage> images;

    public GameManager(){
        this.score = 0;
        this.lives = 3;
        this.maxLives = 3;
        this.isSpaced = false;
        this.gameState = "START";
        
        if (this.gameState.equals("START")){
            loadImages();
            initGame();

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

            //Load Hearts
            heart = ImageIO.read(new File("ArkanoidGame/assets/heart.png"));
            damage = ImageIO.read(new File("ArkanoidGame/assets/damage.png"));

            System.out.println(heart == null);

            // images.put("paddle_expand", ImageIO.read(new File("ArkanoidGame/assets/expanded_paddle.png")));
            images.put("powerup_expand", ImageIO.read(new File("ArkanoidGame/assets/experience.png")));
            /// images.put("powerup_fastball", ImageIO.read(new File("ArkanoidGame/assets/power_fastball.png")));

            //Font
            font = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("ArkanoidGame/assets/font.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (Exception e) {
            System.out.println("Error loading images: " + e.getMessage());
            font = new Font("Arial", Font.PLAIN, 20 );
            e.printStackTrace();
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

            this.originalPaddleWidth = paddleWidth;
            this.originalPaddleImage = paddle.getImage();

            int ballWidth = 50;
            int ballHeight = 50;
            int ballX = paddleX + paddleWidth / 2 - ballWidth / 2;
            int ballY = paddleY - ballHeight - 5;

            ball = new Ball(ballX, ballY, ballWidth, ballHeight, 2, -2, 4);
            ball.setImage(getImage("ball"));

            loadLevel(choosedLevel);
        } catch (Exception e) {
            System.out.println("Error initializing game objects " + e.getMessage());

        }
    }

    public void resetGame() {
        this.score = 0;
        this.lives = 3;
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
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString("Score: " + score, 10, 25);
        g2d.drawString("Lives: ", 600,25);

        for (int i = 0;i < maxLives;i++) {
            int x = (600 + 65) + i * (heart.getWidth() - 6);
            int y = 30 - heart.getHeight() + 14;
            if (i < lives) {
                g.drawImage(heart, x, y, 20, 20, null);
            }

            else {
                g.drawImage(damage, x, y, 20, 20, null);
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
    }

    public void updateGame() {
        HandleInput();
        if (gameState.equals("START")){
            if (isSpaced == false) {
                ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2);
                ball.setY(paddle.getY() - ball.getHeight());
                return;
                
            }
            
            ball.move();
            ball.bounceOffWall();
            checkCollisions();
            updatePowerUps();

            if (ball.getY() > Renderer.SCREEN_HEIGHT) {
                lives -= 1;
                powerUps.clear();
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

        updateActiveEffects();
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
            int powerUpSize = 23;

            if (rand.nextBoolean()) {
                powerUps.add(new ExpandPadllePowerUp(x, y, powerUpSize, powerUpSize, getImage("powerup_expand")));
            } else {
                powerUps.add(new FastBallPowerUp(x, y, powerUpSize, powerUpSize, getImage("powerup_expand")));
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
            ball.bounceOffPaddle(paddle);
        }

        Iterator <Brick> it = bricks.iterator();
        while (it.hasNext()){
            Brick brick = it.next();
            if (ball.checkCollision(brick)) {
                ball.bounceOffBrick(brick);
                this.score += 10;
                brick.takeHits();
                if (brick.isDestroyed()) {
                    it.remove();
                    spawnPowerUp(brick.getX(), brick.getY());
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