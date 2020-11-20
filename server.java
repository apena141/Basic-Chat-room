import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

public class server {

	private static int PORT = 9090;
	private static ArrayList<ClientHandler> clients = new ArrayList<>();
	public static ArrayList<String> names = new ArrayList<>();
	private static int maxClients = 5;
	private static ExecutorService pool = Executors.newFixedThreadPool(maxClients);
	private static int idNum = 0;
	private static Lock lock;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket listener = new ServerSocket(PORT);
		
		while(true) {
			System.out.println("Waiting to establish a connection to client!");
			
			Socket client = listener.accept();
			System.out.println("Established a connection to client!");
			ClientHandler clientThread = new ClientHandler(client, clients, idNum);
			idNum++;
			clients.add(clientThread);
			pool.execute(clientThread);
		}

	}
	
	public static void RemoveClient(int position, String name) {
		int i = 0;
		for(String s : names) {
			if(names.get(i) == name) {
				names.remove(i);
				break;
			}
			i++;
		}
		clients.remove(position);
	}
	
	public static void AddName(String name) {
		names.add(name);
	}
}
