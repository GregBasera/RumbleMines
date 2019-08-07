import java.io.*; //BufferedReader, PrintWriter, InputStreamReader
import java.net.*; //Socket, ServerSocket
import java.util.*;

public class Server {
  private static int[][] board;
  final static int bomb = 10;

  private static void generateBoard(int row, int col){
    // This function initializes the board, fills the board
    // with the bombs (randomly placed) and the indicator
    // numbers of the tiles adjacent to bombs
    Random rand = new Random();

    board = new int[row][col];
    double minePercent = 0.10;
    int numOfMines = (int) Math.ceil((row*col)*minePercent);
    for(int q = 0; q < numOfMines; q++){
      board[rand.nextInt(row)][rand.nextInt(col)] = bomb;
    }
    for(int q = 0; q < board.length; q++){
      for(int w = 0; w < board[q].length; w++){
        pokeEnvi(q, w);
      }
    }
  }

  private static void pokeEnvi(int q, int w){
    // this function helps generateBoard() in filling up the board
    // with the required elements. it takes a specific coordinate
    // in the board and fills it with the required indicator number
    if(board[q][w] != bomb){
      if(isBomb(q-1, w)) board[q][w]++; //n
      if(isBomb(q-1, w-1)) board[q][w]++; //nw
      if(isBomb(q-1, w+1)) board[q][w]++; //ne
      if(isBomb(q, w-1)) board[q][w]++; //w
      if(isBomb(q, w+1)) board[q][w]++; //e
      if(isBomb(q+1, w)) board[q][w]++; //s
      if(isBomb(q+1, w-1)) board[q][w]++; //sw
      if(isBomb(q+1, w+1)) board[q][w]++; //se
    }
  }

  private static boolean isBomb(int q, int w){
    // this function helps pokeEnvi() in filling up the board
    // it returns if the given tile is a bomb or not (true or false)
    try {
      return (board[q][w] == bomb) ? true : false;
    }catch(ArrayIndexOutOfBoundsException e){
      return false;
    }
  }

  public static void main(String[] fudge) throws Exception{
    if(fudge.length < 3) {
			System.out.println("Usage: java Server [port] [row] [col] [numOfPlayers]");
			System.exit(1);
		}

    // Proccess the runtime arguments given by the user
    // and other necessary variables
    int port = Integer.parseInt(fudge[0]);
    int row = Integer.parseInt(fudge[1]);
    int col = Integer.parseInt(fudge[2]);
    board = new int[row][col];
    generateBoard(row, col);
    int players = Integer.parseInt(fudge[3]);
    String names = "";

    ServerSocket server = new ServerSocket(Integer.parseInt(fudge[0]));
    ArrayList<PrintWriter> souts = new ArrayList<PrintWriter>();

    // Update the user of the process state
    System.out.println("Listening to port " + port);
    System.out.println("Waiting for " + players + " players...");

    while(players > 0){
      // Accept players
			Socket socket = server.accept();

      // create input and output streams for the client
			BufferedReader sin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter sout = new PrintWriter(socket.getOutputStream(), true);

      // when a client successfuly connects to the server it first sends the username
      // of that client. that message gets processed here.
      String msg = null;
      while((msg = sin.readLine()) == null){}
      System.out.println(msg + " joined");
      // a string of all client names.
      // this gets passed to all clients afterwards so they can display it in the GUI
      names = names + msg + ",";

      players--;
      System.out.println("Waiting for " + players + " more players...");

      // add the output stream of the client to a ArrayList
      // this array list gets passed to every client thread
      // spawned by the server.
      souts.add(sout);

      // spawn a client thread for the client
      Tick tick = new Tick(sin, souts, board);
      Thread tik = new Thread(tick);
      tik.start();
		}

    // when all players have joined the server will send a message to all the clients
    // containing the Dimensions of the board and the names of the players
    // so the client could represent it in the GUI
    System.out.println("\nplayer loop done... Game starting...");
    for(int q = 0; q < souts.size(); q++){
      souts.get(q).println(row + "," + col + "," + names);
    }

    // socket.close();
  }
}
