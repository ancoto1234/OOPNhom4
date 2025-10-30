package core;

import effects.ParticleSystem;
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
import powerups.MultiBallPowerUp;
import powerups.PowerUp;



public class GameManager implements KeyListener, ActionListener{
    private List<Ball> balls;
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
    private ParticleSystem particleSystem = new ParticleSystem();

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


            powerUps = new ArrayList<>();
            activeEffects = new ArrayList<>();
            rand = new Random();
            balls = new ArrayList<>();

            initGame();
        }
    }

    public void loadImages() {
        images = new HashMap<>();
        

        try {
            images.put("paddle", ImageIO.read(new File("ArkanoidGame/assets/paddle.png")));
            images.put("ball", ImageIO.read(new File("ArkanoidGame/assets/ball.png")));
            images.put("brick", ImageIO.read(new File("ArkanoidGame/assets/brick.png")));
            images.put("powerup_brick",ImageIO.read(new File ("ArkanoidGame/assets/powerupbrick.png")));
            images.put("bonus1_brick",ImageIO.read(new File ("ArkanoidGame/assets/bonusbrick1.png")));


            //Load Hearts
            heart = ImageIO.read(new File("ArkanoidGame/assets/heart.png"));
            damage = ImageIO.read(new File("ArkanoidGame/assets/damage.png"));

            System.out.println(heart == null);

            // images.put("paddle_expand", ImageIO.read(new File("ArkanoidGame/assets/expanded_paddle.png")));
            images.put("powerup_expand", ImageIO.read(new File("ArkanoidGame/assets/power_expand.png")));
            images.put("powerup_fastball", ImageIO.read(new File("ArkanoidGame/assets/power_fastball.png")));
            images.put("powerup_multiball", ImageIO.read(new File("ArkanoidGame/assets/power_multiball.png")));

            //Font
            font = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("ArkanoidGame/assets/font.ttf")).deriveFont(17f);
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

            if (balls != null){
                balls.clear();
            }

            int ballWidth = 25;
            int ballHeight = 25;
            int ballX = paddleX + paddleWidth / 2 - ballWidth / 2;
            int ballY = paddleY - ballHeight - 5;

            Ball mainBall = new Ball(ballX, ballY, ballWidth, ballHeight, 2, -2, 4);
            mainBall.setImage(getImage("ball"));

            balls.add(mainBall);

            loadLevel(choosedLevel);
        } catch (Exception e) {
            System.out.println("138Error initializing game objects " + e.getMessage());

        }
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

        if (balls != null) {
            try {
                for (Ball b : balls){
                    b.render(g, observer);
                }
            } catch (ConcurrentModificationException e){

            }
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
            int x = (600 + 67) + i * (heart.getWidth() - 10);
            int y = 30 - heart.getHeight() + 16;
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
        particleSystem.render((Graphics2D) g);
    }

    public void updateGame() {
        HandleInput();
        
        if (gameState.equals("START")){
            if (isSpaced == false) {

                if (balls != null){
                   Ball mainBall = balls.get(0);
                    mainBall.setX(paddle.getX() + paddle.getWidth() / 2 - mainBall.getWidth() / 2);
                    mainBall.setY(paddle.getY() - mainBall.getHeight());
                }

                
            }

            if (isSpaced == true && balls != null){
                Iterator<Ball> it = balls.iterator();
                while (it.hasNext()){
                    Ball b = it.next();

                    b.move();
                    b.bounceOffWall();

                    if (b.getY() > Renderer.SCREEN_HEIGHT) {
                        it.remove();
                        break;
                    }
                }
            }

            checkCollisions();
            updatePowerUps();

            if (balls.isEmpty() && isSpaced == true){
                lives -= 1;
                powerUps.clear();
                particleSystem.clearParticles();

                if (lives <= 0) {
                    gameOver();
                } else {
                    int paddleWidth = Renderer.SCREEN_WIDTH / 6;
                    int paddleHeight = Renderer.SCREEN_HEIGHT / 27;
                    int paddleX = (Renderer.SCREEN_WIDTH - paddleWidth) / 2;
                    int paddleY = Renderer.SCREEN_HEIGHT - paddleHeight - 40;

                    int ballWidth = 25;
                    int ballHeight = 25;
                    int ballX = paddleX + paddleWidth / 2 - ballWidth / 2;
                    int ballY = paddleY - ballHeight - 5;

                    Ball newBall = new Ball(ballX, ballY, ballWidth, ballHeight, 2, -2, 4);
                    newBall.setImage(getImage("ball"));

                    newBall.resetBall(paddle);
                    balls.add(newBall);
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
        particleSystem.update();
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

    public void spawnPowerUp(int x, int y) {
        int powerUpSize = 30; // Kích thước của Power-Up (ví dụ)
        float chance = rand.nextFloat(); // Lấy 1 số ngẫu nhiên từ 0.0 đến 1.0

        // 30%  ExpandPaddle
        if (chance < 0.3) {
            powerUps.add(new ExpandPadllePowerUp(x, y, powerUpSize, powerUpSize, getImage("powerup_expand")));
        }
        // 30%  FastBall
        else if (chance < 0.3) {
            powerUps.add(new FastBallPowerUp(x, y, powerUpSize, powerUpSize, getImage("powerup_fastball")));
        }
        // 40% MultiBall
        else if (chance < 1) {
            // ĐÂY LÀ CODE SINH RA POWERUP BẠN MUỐN
            powerUps.add(new MultiBallPowerUp(x, y, powerUpSize, powerUpSize, getImage("powerup_multiball")));
        }
    }

    public void activateMultiBall(){
        if (balls.isEmpty()) return;

        Ball primaryBall = balls.get(0);

        int x = primaryBall.getX();
        int y = primaryBall.getY();
        int w = primaryBall.getWidth();
        int h = primaryBall.getHeight();
        double currentDx = primaryBall.getDx();
        double currentDy = primaryBall.getDy();

        // Tính toán góc xoay (45 độ = PI / 4)
        double rad45 = Math.PI / 4.0;
        double cos45 = Math.cos(rad45);
        double sin45 = Math.sin(rad45);

        // --- TÍNH TOÁN BÓNG 2 (Xoay +45 độ) ---
        // dx_new = dx*cos - dy*sin
        // dy_new = dx*sin + dy*cos
        double dx2 = currentDx * cos45 - currentDy * sin45;
        double dy2 = currentDx * sin45 + currentDy * cos45;

        // --- TÍNH TOÁN BÓNG 3 (Xoay -45 độ) ---
        // (cos(-45) = cos45, sin(-45) = -sin45)
        // dx_new = dx*cos - dy*(-sin) = dx*cos + dy*sin
        // dy_new = dx*(-sin) + dy*cos
        double dx3 = currentDx * cos45 + currentDy * sin45;
        double dy3 = -currentDx * sin45 + currentDy * cos45;

        // Làm tròn về int để truyền vào constructor
        int speed = primaryBall.getSpeed();

        Ball ball2 = new Ball(x, y, w, h, dx2, dy2, speed);
        ball2.setImage(getImage("ball"));

        Ball ball3 = new Ball(x, y, w, h, dx3, dy3, speed);
        ball3.setImage(getImage("ball"));

        balls.add(ball2);
        balls.add(ball3);
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
        if (balls == null) return;

        try {
            for (Ball b : balls){
                if (b.checkCollision(paddle)){
                    b.bounceOffPaddle(paddle);
                }
                Iterator <Brick> it = bricks.iterator();
                while (it.hasNext()){
                    Brick brick = it.next();
                    if (b.checkCollision(brick)) {
                        b.bounceOffBrick(brick);
                        this.score += brick.scoreValue;
                        brick.takeHits();
                        if (brick.isDestroyed()) {
                            if (brick instanceof PowerUpBrick) {
                                brick.dropPowerUp(this);
                            }
                            it.remove();
                            particleSystem.spawnParticles(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight(), Color.GRAY);
                            break;
                        }
                    }
                }
            }
        }catch (ConcurrentModificationException e){

        }
    }

    public void resume() {
        gameState = "START";
        
    }

    public void resetGame() {
        this.score = 0;
        this.lives = 3;
        this.choosedLevel = 1;
        this.gameState = "START";
        this.isSpaced = false;

        this.leftPressed = false;
        this.rightPressed = false;
        initGame();
    }


    public void gameOver(){
        gameState = "GAME OVER";
        
    }



    public void WinGame(){
        gameState = "COMPLETE LEVEL";
        if (choosedLevel < totalLevel) {
            choosedLevel++;
            loadLevel(choosedLevel);
            balls.clear();

            int paddleWidth = Renderer.SCREEN_WIDTH / 6;
            int paddleHeight = Renderer.SCREEN_HEIGHT / 27;
            int paddleX = (Renderer.SCREEN_WIDTH - paddleWidth) / 2;
            int paddleY = Renderer.SCREEN_HEIGHT - paddleHeight - 40;

            int ballWidth = 25;
            int ballHeight = 25;
            int ballX = paddleX + paddleWidth / 2 - ballWidth / 2;
            int ballY = paddleY - ballHeight - 5;

            Ball newBall = new Ball(ballX, ballY, ballWidth, ballHeight, 2, -2, 2);
            newBall.setImage(getImage("ball"));

            newBall.resetBall(paddle);
            balls.add(newBall);

            paddle.resetPaddle();
            powerUps.clear();
            activeEffects.clear();
            isSpaced = false;
            particleSystem.clearParticles();

            this.leftPressed = false;
            this.rightPressed = false;
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

    public void setGameState(String gameState) {
        this.gameState = gameState;
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

    public List<Ball> getBalls() {
        return balls;
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