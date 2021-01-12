package Snake;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener {

    private final int WIDTH = 1000;
    private final int HEIGHT = 1000;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 100;
    private final int scrsize = WIDTH * HEIGHT;
    private int DELAY = 300;
    private int score = 0;
    
    private int x[] = new int[ALL_DOTS];
    private int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    
    Font smallfont = new Font("Times New Roman", Font.BOLD, 18);
    Dimension d;
    JLabel statusbar;
    
    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;


    public Board() {
        d = new Dimension(10000, 10000);
        
        addKeyListener(new TAdapter());
        setBackground(Color.white);

        ImageIcon iid = new ImageIcon(this.getClass().getResource("dot.png"));
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon(this.getClass().getResource("apple.png"));
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon(this.getClass().getResource("head.png"));
        head = iih.getImage();

        setFocusable(true);
        initGame();
    }
     public void addNotify() {
        super.addNotify();
        initGame();
    }
//    public void showIntroScreen(Graphics g1) {
//
//        g1.setColor(new Color(0, 32, 48));
//        g1.fillRect(50, scrsize / 2 - 30, scrsize - 100, 50);
//        g1.setColor(Color.white);
//        g1.drawRect(50, scrsize / 2 - 30, scrsize - 100, 50);
//
//        String s = "Press S to start.";
//        Font small = new Font("Times New Roman", Font.BOLD, 18);
//        FontMetrics metr = this.getFontMetrics(small);
//
//        g1.setColor(Color.white);
//        g1.setFont(small);
//        g1.drawString(s, (scrsize - metr.stringWidth(s)) / 2, scrsize / 2);
//    }

    public void initGame() {

        dots = 3;
        score =0;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z*20;
            y[z] = 50;
        }
        
        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }


    public void paint(Graphics g) {
        super.paint(g);
        Graphics g2 = (Graphics) g;

        g.setColor(Color.black);
        g2.fillRect(0, 0, d.width, d.height);
        if (inGame) {
            Font small = new Font("Helvetica", Font.BOLD, 20);
            FontMetrics metr = this.getFontMetrics(small);
            g.setFont(small);
            String strScore = "Score: " + score;
            g.drawString("Score: " + score, WIDTH-metr.stringWidth(strScore)-5, 20);

            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0)
                     g.drawImage(head, x[z], y[z], this);
                else g.drawImage(ball, x[z], y[z], this);
            }
            
            Toolkit.getDefaultToolkit().sync();
            g.dispose();

        } else {
            gameOver(g2);
        }
    }

//    public void DrawScore(Graphics g)
//	{
//		g.drawString("Score: " + score, 0,HEIGHT * HEIGHT + 10);
//                g.setColor(Color.red);
//                g.setFont(new Font("Helvetica", Font.BOLD, 38));
//                
//	}
    public void gameOver( Graphics g2) {
        
        g2.setColor(Color.black);
        g2.fillRect(0, 0,WIDTH,HEIGHT);

        g2.setColor(new Color(0, 32, 48));
        g2.fillRect(50, WIDTH/2 - 30, WIDTH-100, 50);
        g2.setColor(Color.white);
        g2.drawRect(50, WIDTH/2 - 30,WIDTH-100, 50);
        String msg = "Game Over and press S if you want play to again.";
        String score_msg = "Score: " + score;
        String re_msg = "Press R to restart or E to exit.";
        Font small = new Font("Helvetica", Font.BOLD, 38);
        FontMetrics metr = this.getFontMetrics(small);

        g2.setColor(Color.red);
        g2.setFont(small);
        g2.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2,HEIGHT / 2);
        g2.drawString(score_msg, (WIDTH - metr.stringWidth(score_msg)) / 2, (HEIGHT / 2) + 24);
        g2.drawString(re_msg, (WIDTH - metr.stringWidth(re_msg)) / 2, (HEIGHT / 2) + 48);
        timer.stop();
    }

    public void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            countScore();
            locateApple();
        }
    }


    public void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (left) {
            x[0] -= DOT_SIZE;
        }

        if (right) {
            x[0] += DOT_SIZE;
        }

        if (up) {
            y[0] -= DOT_SIZE;
        }

        if (down) {
            y[0] += DOT_SIZE;
        }
    }


    public void checkCollision() {
     
          for (int z = dots; z > 0; z--) {

              if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                  inGame = false;
                  
              }
          }

        if (y[0] > HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] > WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }
    }

    public void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));
        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }
    @Override
    public void update(Graphics g){
        paint(g);
    }
    public class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (inGame){
                
                if ((key == KeyEvent.VK_LEFT) && (!right)) {
                left = true;
                up = false;
                down = false;
                }

                if ((key == KeyEvent.VK_RIGHT) && (!left)) {
                right = true;
                up = false;
                down = false;
            }

                if ((key == KeyEvent.VK_UP) && (!down)) {
                up = true;
                right = false;
                left = false;
            }

                if ((key == KeyEvent.VK_DOWN) && (!up)) {
                down = true;
                right = false;
                left = false;
                }
            }
            else {
            if (key == 's' || key == 'S' )
            {  
              inGame=true;
              move();
              initGame();
            }
            if (key == KeyEvent.VK_R){
                restartGame();
            }
            if (key == KeyEvent.VK_E){
                System.exit(0);
            }
            
          }
        }
    }
    public void countScore(){
        score += 10;
    }
    public void actionPerformed(ActionEvent e) {

        if (inGame) {
            
            checkApple();
            checkCollision();
            move();
        } 
        repaint();
    }
    public void restartGame(){
        timer.stop();
        left = false;
        right = true;
        up = false;
        down = false;
        inGame = true;
        initGame();
    }
}