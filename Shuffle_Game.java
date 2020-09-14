import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JOptionPane;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Shuffle_Game extends JPanel { // our grid will be drawn in a dedicated Panel
  
  // Size of our shuffle game instance
  private int size;
  // Number of tiles
  private int nbTiles;
  // Grid UI Dimension
  private int dimension;
  // Foreground Color
  private static final Color FOREGROUND_COLOR = new Color(239, 83, 80); // we use arbitrary color
  // Random object to shuffle tiles
  private static final Random RANDOM = new Random();
  // Storing the tiles in a 1D Array of integers
  private int[] tiles;
  // Size of tile on UI
  private int tileSize;
  // Position of the blank tile
  private int blankPos;
  // Margin for the grid on the frame
  private int margin;
  // Grid UI Size
  private int gridSize;
  // true if game over, false otherwise
  private boolean gameOver;
   public static int n;
  
  public Shuffle_Game(int size, int dim, int mar) {
    this.size = size;
    dimension = dim;
    margin = mar; 
  
    nbTiles = size * size - 1; // -1 because we don't count blank tile
    tiles = new int[size * size];
    
    // calculate grid size and tile size
    gridSize = (dim - 2 * margin);
    tileSize = gridSize / size;
    
    setPreferredSize(new Dimension(dimension, dimension + margin));
    setBackground(Color.WHITE);
    setForeground(FOREGROUND_COLOR);
    setFont(new Font("SansSerif", Font.BOLD, 60));
  
    newGame();
  }
  
  private void newGame() {
      reset(); // reset in intial state
      shuffle(); // shuffle
  }
  
  private void reset() {
    for (int i = 0; i < tiles.length; i++) {
      tiles[i] = (i + 1) % tiles.length;
    }
    
    // we set blank cell at the last
    blankPos = tiles.length - 1;
  }
  
  private void shuffle() {
    // don't include the blank tile in the shuffle, leave in the solved position
    int n = nbTiles;
    
    while (n > 1) {
      int r = RANDOM.nextInt(n--);
      int tmp = tiles[r];
      tiles[r] = tiles[n];
      tiles[n] = tmp;
    }
  }
  
  // Only half permutations o the puzzle are solvable
  // Whenever a tile is preceded by a tile with higher value it counts
  // as an inversion. In our case, with the blank tile in the solved position,
  // the number of inversions must be even for the puzzle to be solvable
  
  private void drawGrid(Graphics2D g) {
    for (int i = 0; i < tiles.length; i++) {
      // we convert 1D coords to 2D coords given the size of the 2D Array
      int r = i / size;
      int c = i % size;
      // we convert in coords on the UI
      int x = margin + c * tileSize;
      int y = margin + r * tileSize;
      
      // check special case for blank tile
      if(tiles[i] == 0) {
        if (gameOver) {
          g.setColor(FOREGROUND_COLOR);
        }
        continue;
      }
      
      // for other tiles
      g.setColor(getForeground());
      g.fillRoundRect(x, y, tileSize, tileSize, 25, 25);
      g.setColor(Color.BLACK);
      g.drawRoundRect(x, y, tileSize, tileSize, 25, 25);
      g.setColor(Color.WHITE);
      
      drawCenteredString(g, String.valueOf(tiles[i]), x , y);
    }
  }
  private void drawCenteredString(Graphics2D g, String s, int x, int y) {
    // center string s for the given tile (x,y)
    FontMetrics fm = g.getFontMetrics();
    int asc = fm.getAscent();
    int desc = fm.getDescent();
    g.drawString(s,  x + (tileSize - fm.stringWidth(s)) / 2, 
        y + (asc + (tileSize - (asc + desc)) / 2));
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2D = (Graphics2D) g;
    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    drawGrid(g2D);
    //drawStartMessage(g2D);
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
