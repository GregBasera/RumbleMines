import java.io.*; //BufferedReader, PrintWriter, InputStreamReader
import java.util.*;

public class Tick implements Runnable {
  private BufferedReader sin;
  private ArrayList<PrintWriter> souts;
  private int[][] board;

  // Constructor
  public Tick(BufferedReader sin, ArrayList<PrintWriter> souts, int[][] board){
    this.sin = sin;
    this.souts = souts;
    this.board = board;
  }

  public void run(){
    while(true){
      try {
        // tick.java is a thread whose task is to receive a message from a client
        // and forward a reply to all clients connected.
        String msg = null;
        while((msg = sin.readLine()) == null){}

        // when a client clicks a tile on the GUI, it sends the clients'
        // username and the coordinate of the tile clicked.
        String[] s = msg.split(",");

        // this is the value of the tile the client clicked ready to be sent back to the clients
        String boardValue = String.valueOf((board[Integer.valueOf(s[1])][Integer.valueOf(s[2])]));

        // the server replies by using the message the client sent previously and
        // appending the value of the tile specified in the message
        for(int q = 0; q < souts.size(); q++){
          souts.get(q).println(msg + "," + boardValue);
        }

        System.out.println(msg + "," + boardValue);
      } catch(IOException e) {
        // when a client tripped a mine the remote client proccess terminates.
        // this will cause a IOException here, in this thread. to prevent future errors
        // the thread is terminated using "return".
        System.out.println("IOException in Tick.java...");
        return;
      }
    }
  }
}
