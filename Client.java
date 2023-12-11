import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String lobbyName;
	
	public Client(Socket socket, String lobbyName) {
		
		try {
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.lobbyName = lobbyName;
			
			
		//end of try	
		}catch(IOException e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
		}//end of catch
		
	}//end of Client Constructor
	
	public void sendMessage() {
		try {
			bufferedWriter.write(lobbyName);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			
			Scanner scan = new Scanner(System.in);
			while (socket.isConnected()) {
				String messageToSend = scan.nextLine();
				bufferedWriter.write(lobbyName+ ": "+messageToSend);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			}//end of while
			
			//end of try
		}catch(IOException e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
		}//end of catch
	}//end of sendMessage
	
	public void listenForMessage() {
		new Thread(	new Runnable() {
			public void run() {
				
				String messageFromServer;
				
				while(socket.isConnected()) {
					try {
						messageFromServer = bufferedReader.readLine();
						System.out.println(messageFromServer);
						
						//end of try
					}catch(IOException e) {
						closeEverything(socket, bufferedReader, bufferedWriter);
					}//end of catch
				}//end of while
			
		}//end of run
	}).start();
	}//end of listenForMessage
	
	public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		try {
			if(bufferedReader != null) {
				bufferedReader.close();
			}//end of if
			if(bufferedWriter != null) {
				bufferedWriter.close();
			}//end of second if
			if(socket != null) {
				socket.close();
			}//end of third if
			
			//end of try
		}catch(IOException e) {
			e.printStackTrace();
		}//end of catch
	}//end of close Everything

	public static void main(String []args) throws  IOException {
		Scanner scan = new Scanner(System.in);
		System.out.println("enter your name for the game lobby:");
		String lobbyName = scan.nextLine();
		Socket socket = new Socket("localhost", 8080);
		Client client = new Client(socket, lobbyName);
		client.listenForMessage();
		client.sendMessage();
		
	}//end of main
}//end of Client 
