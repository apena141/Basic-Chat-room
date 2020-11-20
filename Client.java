import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	
	private static final String SERVER_IP = "192.168.1.187";
	private static final int SERVER_PORT = 9090;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Socket server = new Socket(SERVER_IP, SERVER_PORT);
		ServerConnection serverConnection = new ServerConnection(server);
		
		// Outputs to server
		// We get the chatID and send it to the server
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(server.getOutputStream(), true);
		
		
		//String chatid = keyboard.readLine();
		//out.println(chatid);
		Thread clientThread = new Thread(serverConnection);
		clientThread.start();
		
		while(true) {
			System.out.println("> ");
			String message = keyboard.readLine();
			
			if(message.toUpperCase().equals("LOGOUT")) {
				out.println(message);
				System.out.println("Loging you out...");
				break;
			}
			
			// TEST DELETE ME
			// send to server
			out.println(message);
			// print response
			//servermsg = input.readLine();
			//System.out.println(servermsg);
			/////////////////////

			// HERE WE MAY WANT TO ADD IN THE FUTURE THAT WE NEED TO SEND THIS MESSAGE ACROSS ALL CLIENTS
		}
		
	}
}
