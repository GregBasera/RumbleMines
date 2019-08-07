import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Gui {
  protected JFrame frame;
  protected JPanel boardPanel = new JPanel();
  protected JPanel infoPanel = new JPanel();
  protected JButton[][] tiles;
  protected JLabel[] scoreBoard;
  protected PrintWriter sout;

  public Gui(String[] clientInit, String username, PrintWriter sout) {
    this.sout = sout;

    frame = new JFrame("RumbleMines [" + username + "]");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new GridBagLayout());
    frame.setResizable(false);

    int xTiles = Integer.valueOf(clientInit[0]);
		int yTiles = Integer.valueOf(clientInit[1]);
    boardPanel.setLayout(new GridLayout(xTiles, yTiles));
    boardPanel.setPreferredSize(new Dimension(900, 700));

    tiles = new JButton[xTiles][yTiles];
    for(int q = 0; q < tiles.length; q++){
      for(int w = 0; w < tiles[q].length; w++){
        tiles[q][w] = new JButton();
        tiles[q][w].setBackground(Color.GRAY);
        tiles[q][w].setMargin(new Insets(1, 1, 1, 1));
        final int xCoor = q;
        final int yCoor = w;
        tiles[q][w].addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            sout.println(username + "," + xCoor + "," + yCoor);
          }
        });
        boardPanel.add(tiles[q][w]);
      }
    }
    frame.add(boardPanel);

    infoPanel.setPreferredSize(new Dimension(300, 700));
    scoreBoard = new JLabel[clientInit.length-2];
    for(int q = 0; q < (clientInit.length-2); q++){
      scoreBoard[q] = new JLabel(clientInit[q+2] + " - 0", SwingConstants.CENTER);
      scoreBoard[q].setFont(new Font("Serif", Font.PLAIN, 20));
      scoreBoard[q].setPreferredSize(new Dimension(300, 30));
      infoPanel.add(scoreBoard[q]);
    }
    frame.add(infoPanel);

    frame.pack();
    frame.setVisible(true);
  }
}
