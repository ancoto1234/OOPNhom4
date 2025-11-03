package core;

import effects.ExplosionEffects;
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
    private static GameManager instance;
    private List<Ball> balls;
    private Paddle paddle;
    private List<Brick> bricks;
    private List<PowerUp> powerUps;
    private int score;
    private HighScoreManager highScoreManager = new HighScoreManager();
    private int lives;
    private int maxLives;
    private int choosedLevel;
    private int so_in_ra_ma_hinh;
    private long thoiGianBatDauDem;
    private final long Khoang_cach_2_lan_dem = 1000;
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
    private List<ExplosionEffects> explosions = new ArrayList<>();
    private boolean isPaused = false;
    private MenuManager menuManager;
    //Image
    private BufferedImage heart;
    private BufferedImage damage;

    //Sound
    private sound.Sound hitbrickSound;
    private sound.Sound hitpaddleSound;
    private sound.Sound powerupSound;
    private sound.Sound wingameSound;
    private sound.Sound gameoverSound;
    private sound.Sound explosiveSound;
    private sound.Sound powerupBrickSound;

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

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public void loadImages() {
        images = new HashMap<>();


        try {
            images.put("paddle", ImageIO.read(new File("ArkanoidGame/assets/paddle.png")));
            images.put("ball", ImageIO.read(new File("ArkanoidGame/assets/ball.png")));
            images.put("brick", ImageIO.read(new File("ArkanoidGame/assets/brick.png")));
            images.put("powerup_brick",ImageIO.read(new File ("ArkanoidGame/assets/powerupbrick.png")));
            images.put("bonus1_brick",ImageIO.read(new File ("ArkanoidGame/assets/bonusbrick1.png")));
            images.put("bonus2_brick",ImageIO.read(new File ("ArkanoidGame/assets/bonusbrick2.png")));
            images.put("bonus3_brick",ImageIO.read(new File ("ArkanoidGame/assets/bonusbrick3.png")));
            images.put("explosion_brick",ImageIO.read(new File ("ArkanoidGame/assets/explosionbrick.png")));
            images.put("unbreak_brick",ImageIO.read(new File ("ArkanoidGame/assets/unbreakbrick.png")));



            //Load Hearts
            heart = ImageIO.read(new File("ArkanoidGame/assets/heart.png"));
            damage = ImageIO.read(new File("ArkanoidGame/assets/damage.png"));

            System.out.println(heart == null);

            //images.put("paddle_expand", ImageIO.read(new File("ArkanoidGame/assets/expanded_paddle.png")));
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

        //sound
        try {
            hitbrickSound = new sound.Sound("ArkanoidGame/sound/hit_brick.wav");
            hitpaddleSound = new sound.Sound("ArkanoidGame/sound/hit_paddle.wav");
            powerupSound = new sound.Sound("ArkanoidGame/sound/powerup.wav");
            wingameSound = new sound.Sound("ArkanoidGame/sound/wingame.wav");
            gameoverSound = new sound.Sound("ArkanoidGame/sound/gameover.wav");
            explosiveSound = new sound.Sound("ArkanoidGame/sound/explosion.wav");
            powerupBrickSound = new sound.Sound("ArkanoidGame/sound/PowerUpBrick.wav");

            hitbrickSound.setVolume(-10.0f);
            hitpaddleSound.setVolume(-15.0f);
        } catch (Exception e) {
            System.out.println("Error loading sound: " + e.getMessage());
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

            Ball mainBall = new Ball(ballX, ballY, ballWidth, ballHeight, 0, -2, 3);
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
        for (ExplosionEffects e : explosions) {
            e.render((Graphics2D) g);
        }

        if (so_in_ra_ma_hinh > 0) {
            g2d = (Graphics2D) g;
            g2d.setColor(Color.YELLOW);
            g2d.setFont(font.deriveFont(72f));

            String soDemNguoc = String.valueOf(so_in_ra_ma_hinh);
            FontMetrics fm = g2d.getFontMetrics();
            int x = (Renderer.SCREEN_WIDTH - fm.stringWidth(soDemNguoc)) / 2;
            int y = (Renderer.SCREEN_HEIGHT - fm.getHeight()) / 2 + fm.getAscent();

            g2d.drawString(soDemNguoc, x, y);
        }
    }

    public void updateGame() {
        if(so_in_ra_ma_hinh > 0) {
            long currentTime = System.currentTimeMillis();
            long thoigianDaQua = currentTime - thoiGianBatDauDem;

            if (thoigianDaQua >= Khoang_cach_2_lan_dem) {
                so_in_ra_ma_hinh--;
                thoiGianBatDauDem = currentTime;

                if (so_in_ra_ma_hinh == 0) {
                    isSpaced = true;
                }
            }
            return;
        }
        if (isPaused) return;
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

                    Ball newBall = new Ball(ballX, ballY, ballWidth, ballHeight, 2, -2, 3);
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
        for (ExplosionEffects e : explosions) {
            e.update();
        }

        explosions.removeIf(e -> !e.isActive());
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
                powerupSound.play();
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
                    hitpaddleSound.play();
                }
                Iterator <Brick> it = bricks.iterator();
                while (it.hasNext()){
                    Brick brick = it.next();
                    if (b.checkCollision(brick)) {
                        b.bounceOffBrick(brick);
                        this.score += brick.scoreValue;
                        brick.takeHits();
                        if (brick.isDestroyed()) {
                            hitbrickSound.play();
                            if (brick instanceof PowerUpBrick) {
                                brick.dropPowerUp(this);
                            }

                            if (brick instanceof ExplosiveBrick) {
                                brick.onDestroy(this);
                                explosiveSound.play();
                            }
                            it.remove();
                            particleSystem.spawnParticles(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight(), Color.GRAY);
                            break;
                        } else {
                            hitpaddleSound.play();
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
        this.isPaused = false;

        this.leftPressed = false;
        this.rightPressed = false;

        clearAllEffects();
        initGame();
    }


    public void gameOver(){
        gameoverSound.play();
        highScoreManager.checkAndUpdate(score);
        gameState = "GAME OVER";

    }



    public void WinGame(){
        gameState = "COMPLETE LEVEL";
        if (choosedLevel < totalLevel) {
            wingameSound.play();
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

            Ball newBall = new Ball(ballX, ballY, ballWidth, ballHeight, 2, -2, 3);
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
            wingameSound.play();
        }
    }

    public SaveGame createSaveData() {
        SaveGame data = new SaveGame();
        data.score = this.score;
        data.lives = this.lives;
        data.currentLevel = this.choosedLevel;
        data.isSpaced = this.isSpaced;

        //Paddle
        SaveGame.PaddleData pd = new SaveGame.PaddleData();
        pd.x = paddle.getX();
        pd.y = paddle.getY();
        pd.width = paddle.getWidth();
        pd.height = paddle.getHeight();
        data.paddle = pd;

        //Balls
        data.balls = new ArrayList<>();
        for (Ball b : balls) {
            SaveGame.BallData bd = new SaveGame.BallData();
            bd.x = b.getX();
            bd.y = b.getY();
            bd.width = b.getWidth();
            bd.height = b.getHeight();
            bd.dx = b.getDx();
            bd.dy = b.getDy();
            bd.speed = b.getSpeed();
            data.balls.add(bd);

        }

        // Bricks 
        data.bricks = new ArrayList<>();
        for (Brick brick : bricks) {
            SaveGame.BrickData bd = new SaveGame.BrickData();
            bd.x = brick.getX();
            bd.y = brick.getY();
            bd.width = brick.getWidth();
            bd.height = brick.getHeight();
            bd.type = brick.getType();
            data.bricks.add(bd);
        }

        // PowerUps
        data.powerups = new ArrayList<>();
        for (PowerUp pu : powerUps) {
            SaveGame.PowerUpData pud = new SaveGame.PowerUpData();
            pud.x = pu.getX();
            pud.y = pu.getY();
            pud.width = pu.getWidth();
            pud.height = pu.getHeight();
            pud.type = pu.getType();
            data.powerups.add(pud);
        }

        return data;
    }

    public void saveCurrentGame() {
        SaveGameManager.saveGame(createSaveData());
    }

    public void loadFromSave(SaveGame data) {
        if (data == null) return;

        this.score = data.score;
        this.lives = data.lives;
        this.choosedLevel = data.currentLevel;
        this.gameState = "START";
        this.isSpaced = data.isSpaced; // Tiếp tục di chuyển
        this.isPaused = false;

        // Paddle
        paddle = new Paddle(data.paddle.x, data.paddle.y, data.paddle.width, data.paddle.height, 15);
        paddle.setImage(getImage("paddle"));

        //Balls
        balls = new ArrayList<>();
        for (SaveGame.BallData bd : data.balls) {
            Ball b = new Ball(bd.x, bd.y, bd.width, bd.height, bd.dx, bd.dy, bd.speed);
            b.setImage(getImage("ball"));
            balls.add(b);
        }

        //Bricks
        bricks = new ArrayList<>();
        for  (SaveGame.BrickData bd : data.bricks) {
            Brick brick;
            switch (bd.type) {
                case 1:
                    brick = new NormalBrick(bd.x, bd.y, bd.width, bd.height);
                    brick.setImage(getImage("brick"));;
                    break;
                case 2: 
                    brick = new PowerUpBrick(bd.x, bd.y, bd.width, bd.height);
                    brick.setImage(getImage("powerup_brick"));
                    break;
                case 3:
                    brick = new BonusBrick1(bd.x, bd.y, bd.width, bd.height);
                    brick.setImage(getImage("bonus1_brick"));
                    break;
                case 4:
                    brick = new ExplosiveBrick(bd.x, bd.y, bd.width, bd.height);
                    brick.setImage(getImage("explosion_brick"));
                    break;
                case 5: 
                    brick = new BonusBrick2(bd.x, bd.y, bd.width, bd.height);
                    brick.setImage(getImage("bonus2_brick"));
                    break;
                case 6: 
                    brick = new BonusBrick3(bd.x, bd.y, bd.width, bd.height);
                    brick.setImage(getImage("bonus3_brick"));
                    break;
                case 7:
                     brick = new UnBreakBrick(bd.x, bd.y, bd.width, bd.height);
                    brick.setImage(getImage("unbreak_brick"));
                    break;
                default:
                    throw new AssertionError();
            }
            bricks.add(brick);
        }

        // PowerUps
        powerUps = new ArrayList<>();
        for (SaveGame.PowerUpData pud : data.powerups) {
            PowerUp pu;
            switch (pud.type) {
                case 3:
                    pu = new ExpandPadllePowerUp(pud.x, pud.y, pud.width, pud.height, getImage("powerup_expand"));
                    break;
                case 2: 
                    pu = new FastBallPowerUp(pud.x, pud.y, pud.width, pud.height, getImage("powerup_fastball"));
                    break;
                case 1:
                    pu = new ExpandPadllePowerUp(pud.x, pud.y, pud.width, pud.height, getImage("powerup_expand"));
                    break;
                default:
                    pu = null;
            }
            if (pu != null) {
                powerUps.add(pu);
            }

        }
    }



    public boolean allBricksDestroyed() {
        for (Brick b : bricks) {
            if (!(b instanceof UnBreakBrick) && !b.isDestroyed()) {
                return false;
            }
        }

        return true;
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

    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    public boolean isPause() {
        return isPaused;
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

    public List<Brick> getBricks() {
        return bricks;
    }

    public BufferedImage getOriginalPaddleImage() {
        return originalPaddleImage;
    }

    public int getOriginalPaddleWidth() {
        return originalPaddleWidth;
    }

    public HighScoreManager getHighScoreManager() {
        return highScoreManager;
    }

    public void addExplosion(ExplosionEffects e) {
        explosions.add(e);
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

    public void GamePause() {
        if (!isPaused) {
            isPaused = true;
            if (menuManager != null) {
                menuManager.showPauseMenu();
                System.out.println("Show Pause Panel");
            }
            else {
                System.out.println("MenuManager = null");
            }
        } else {
            isPaused = false;
            startCountdown();
            if (menuManager != null) {
                menuManager.resumeGame();
            }
        }
    }

    public void startCountdown() {
        so_in_ra_ma_hinh = 3;
        thoiGianBatDauDem = System.currentTimeMillis();
        this.isPaused = false;
        gameState = "START";
        isSpaced = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }

    public void clearAllEffects() {
        if (powerUps != null) powerUps.clear();
        if (activeEffects != null) activeEffects.clear();
        if (explosions != null) explosions.clear();
        if (particleSystem != null) particleSystem.clearParticles();
        if (balls != null) balls.clear();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    public boolean isPaused() {
        return isPaused;
    }

}
