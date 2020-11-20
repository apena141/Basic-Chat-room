import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection implements Runnable {
	
	private Socket server;
	private BufferedReader input;
	
	public ServerConnection(Socket socket) throws IOException {
		this.server = socket;
		input = new BufferedReader(new InputStreamReader(server.getInputStream()));
	}
	
	public void run() {
		try {
			while(true) {
				String serverResponse = input.readLine();
				if(serverResponse == null) {
					break;
				}
				System.out.println(serverResponse);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}	
}
