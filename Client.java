import java.io.*; //BufferedReader, PrintWriter, InputStreamReader
import java.net.*; //Socket, ServerSocket

public class Client{
	public static void main(String[] fudge) throws Exception {
		if(fudge.length < 3) {
			System.out.println("Usage: java Client [ip] [port] [username]");
			System.exit(1);
		}

		// Proccess the runtime arguments given by the user
		// and other necessary variables
		Socket socket = new Socket(fudge[0], Integer.parseInt(fudge[1]));
		String username = fudge[2];

		System.out.println("Connected to the Server... Waiting for players...");

		// create input and output streams for the client
		BufferedReader sin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter sout = new PrintWriter(socket.getOutputStream(), true);

		// the server needs the name of the client so it is sent here
    sout.println(username);

		// the server replies with a string that contains the board size and all the other players
		// it gets proccessed here, and it gets passed to the GUI after
		String msg = null;
		while((msg = sin.readLine()) == null){}
		System.out.println(msg);
		String[] clientInit = msg.split(",");

		// display GUI
		Gui gui = new Gui(clientInit, username, sout);

		// Start the receiver thread
		Receiver r = new Receiver(sin, gui, username);
		Thread receiveThread = new Thread(r);
		receiveThread.start();
	}
}
