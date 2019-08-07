import java.io.*; //BufferedReader, PrintWriter, InputStreamReader
import java.awt.*; // Color
import javax.swing.*;

public class Receiver implements Runnable{
	private BufferedReader sin;
	private Gui gui;
	private String username;

	public Receiver(BufferedReader sin, Gui gui, String username){
		this.sin = sin;
		this.gui = gui;
		this.username = username;
	}

	public void run(){
		try{
			while(true){
				// Receiver.java is a thread whose task is to receive a message from the server
        // process is and update the GUI as required.
				String msg = null;
        while((msg = sin.readLine()) == null){}
				String[] s = msg.split(",");

				// Split the string from the server into its segments
				int x = Integer.valueOf(s[1]);
				int y = Integer.valueOf(s[2]);
				gui.tiles[x][y].setBackground(Color.WHITE);
				gui.tiles[x][y].setFocusPainted(false);
				gui.tiles[x][y].setText(s[3]);
 				gui.tiles[x][y].setEnabled(false);

				// if the tile is bomb
				if(Integer.valueOf(s[3]) == 10){
					// if the client who tripped the bomb is this client, terminate the whole process
					if(s[0].equals(username)) {
						JOptionPane.showMessageDialog(gui.frame, "You tripped a bomb.", "Boom!", JOptionPane.WARNING_MESSAGE);
						System.exit(1);
					} else { // else, put a eliminated indicator on the player who tripped the bomb
						for(int q = 0; q < gui.scoreBoard.length; q++){
							String prevText = gui.scoreBoard[q].getText();
							String[] exploded = prevText.split(" - ");
							if(exploded[0].equals(s[0])){
								gui.scoreBoard[q].setText(exploded[0] + " - Boom!");
							}
						}
					}
				} else { // if the tile is not a bomb
					for(int q = 0; q < gui.scoreBoard.length; q++){
						// add 1 to the score of the player who didnt tripped a bomb
						String prevText = gui.scoreBoard[q].getText();
						String[] exploded = prevText.split(" - ");
						if(exploded[0].equals(s[0])){
							gui.scoreBoard[q].setText(exploded[0] + " - " + String.valueOf(Integer.valueOf(exploded[1]) +1));
						}
					}
				}

				// count the number of "Boom!" in the scoreBoard
				int elims = 0;
				for(int q = 0; q < gui.scoreBoard.length; q++){
					String w = gui.scoreBoard[q].getText();
					String[] e = w.split(" - ");
					elims = (e[1].equals("Boom!")) ? ++elims : elims;
				}
				// if there's only one left the client wins the game
				if(elims == gui.scoreBoard.length-1){
					JOptionPane.showMessageDialog(gui.frame, "You won.", "Chicken Dinner!", JOptionPane.WARNING_MESSAGE);
					System.exit(1);
				}

				// if(Integer.valueOf(s[3]) == 0){
				// 	gui.tiles[x-1][y].doClick();
				// 	gui.tiles[x][y-1].doClick();
				// 	gui.tiles[x][y+1].doClick();
				// 	gui.tiles[x+1][y].doClick();
				// }
			}
		}catch(IOException e){
			System.out.println("IOException in Receiver.java...");
		}
	}
}
