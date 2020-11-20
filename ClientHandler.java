import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ClientHandler implements Runnable{
	
	private String chatid = "";
	private Socket clientSocket;
	private BufferedReader input;
	private PrintWriter output;
	private ArrayList<ClientHandler> clients;
	private int id;
	private Lock lock = new ReentrantLock();
	private Condition lockCondition = lock.newCondition();
	
	
	public ClientHandler(Socket socket, ArrayList<ClientHandler> client, int idnum) throws IOException {
		this.clientSocket = socket;
		this.clients = client;
		this.id = idnum;
		input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		output = new PrintWriter(clientSocket.getOutputStream(), true);
	}
	
	public void run() {
		// we may want to use a lock here to add the name in the arraylist the server holds
		lock.lock();
		try {
			this.chatid = getID();
			SendEntryMessageToAll(chatid);
			server.AddName(chatid);
		}finally {
			lock.unlock();
		}
		
		output.println("Welcome " + chatid + "!");
		
		try {
			while(true){
				String clientMessage = input.readLine();
				if(clientMessage.toUpperCase().equals("LOGOUT")) {
					// This is where we need to use locks so that when we try to remove
					// we need to make sure the arraylist isnt in use so that we can remove
					// without any errors
					lock.lock();
					try {
						server.RemoveClient(this.id);
						SendExitMessageToAll(chatid);
					}finally {
						lock.unlock();
					}
					//
					break;
				}
				else if(clientMessage.equals("/list")) {
					lock.lock();
					try{
						ListClientNames(this.id);
					}finally {
						lock.unlock();
					}
				}
				else {
					// Here we want to send the message to everyone
						SendMessageToAll(clientMessage, chatid);
				}	
			}
		}catch (IOException e) {
			System.err.println("IOException in client handler!");
			e.printStackTrace();
		} finally {
			output.close();
			try {
				System.out.println("Closing input!");
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void SendMessageToAll(String message, String cid) {
		for(ClientHandler client: clients){
			client.output.println("[" + cid + "]: " + message);
		}
	}
	
	private void SendExitMessageToAll(String cid) {
		for(ClientHandler client: clients){
			client.output.println(cid + " is leaving the chat room");
		}
	}
	
	private void ListClientNames(int id) {
		output.println("---------- List ----------");
		for(String name: server.names) {
			output.println(name + " ");
		}
	}
	
	private void SendEntryMessageToAll(String cid) {
		for(ClientHandler client: clients){
			client.output.println(cid + " has entered the chat room.");
		}
	}
	
	private String getID() {
		output.println("Enter a chat id: ");
		String chatid = "";
		try {
			chatid = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(server.names.contains(chatid)) {
			output.println("ChatID is in use, enter a unique ID: ");
			try {
				chatid = input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return chatid;
	}
}
