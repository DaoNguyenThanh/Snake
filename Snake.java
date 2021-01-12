package Snake;

import javax.swing.JFrame;
// Nguyen Thanh Dao lam game 
public class Snake extends JFrame {

    public Snake() {

        add(new Board());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1010, 1010);
        setLocationRelativeTo(null);
        setTitle("Snake");
        
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Snake();
        music.play("dao");
    }
}
