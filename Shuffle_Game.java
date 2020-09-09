import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// We are going to create a Shuffle game with Java 8 and Swing
public class Shuffle_Game extends JPanel { // our grid will be drawn in a dedicated Panel

  
    private static final long serialVersionUID = 1L;
	// Size of our Game instance
    private int size;
    // Number of tiles
    public int nbTiles;
    // Grid UI Dimension
    private int dimension;
    // Foreground Color
    private static final Color FOREGROUND_COLOR = new Color(220, 20, 30); // we use arbitrary color
    // Random object to shuffle tiles
    private static final Random RANDOM = new Random();
    // Storing the tiles in a 1D Array of integers
    private int[] tiles;
    // Size of tile on UI
    private int tileSize;
    // Margin for the grid on the frame
    private int margin;
    // Grid UI Size
    private int gridSize;
    //private boolean gameOver;
    
    public static int n;

    public Shuffle_Game(int size, int dim, int mar) {
        this.size=size;
        dimension = dim;
        margin = mar;

        // init tiles 
        nbTiles = (size*size) - 1; // -1 because we don't count black tile
        tiles = new int[size*size];

        // calculate grid size and tile size
        gridSize = (dim - 2*margin);
        tileSize = gridSize / size;

        setPreferredSize(new Dimension(dimension, dimension + margin));
        setBackground(Color.WHITE);
        setForeground(Color.RED);
        setFont(new Font("SansSerif", Font.BOLD, 60));
    }
    
    private void drawGrid(Graphics2D g) {
        for (int i = 0; i < tiles.length-1; i++) {
            // we convert 1D coords to 2D coords given the size of the 2D Array
            int r = i / size;
            int c = i % size;
            // we convert in coords on the UI
            int x = margin + c * tileSize;
            int y = margin + r * tileSize;

            // for other tiles
            g.setColor(getForeground());
            g.fillRoundRect(x, y, tileSize, tileSize, 25, 25);
            g.setColor(Color.BLACK);
            g.drawRoundRect(x, y, tileSize, tileSize, 25, 25);
            g.setColor(Color.WHITE);

        }
    }
    

    // @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(g2D);
        g.setColor(FOREGROUND_COLOR);
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Shuffle Game");
            frame.setResizable(false);
            String box = (String) JOptionPane.showInputDialog(null,"Please Enter value");
            n = Integer.parseInt(box);
            frame.add(new Shuffle_Game(n, 600, 20), BorderLayout.CENTER);
            frame.pack();
            // center on the screen
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}