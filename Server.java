import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	//creation of the server Socket from import
	private ServerSocket serverSocket;

	//constructor for the server
	public Server(ServerSocket serverSocket) {
		this.serverSocket=serverSocket;
		
		
	}//end of Server Constructor
	
	//used to start the server
	public void startServer() {
		
		try {
			
			while(!serverSocket.isClosed()) {
				Socket socket = serverSocket.accept();
				System.out.println(" a new client has connected");
				ClientHandler clientHandler = new ClientHandler(socket);
				
				Thread thread = new Thread(clientHandler);
				thread.start();
			}//end of while
		
		//end of try 	
		} catch (IOException e) {
			
		}//end of catch

	}//end of startServer
	
	public static void main(String []args) throws IOException {
		
		ServerSocket serverSocket = new ServerSocket(8080);
		Server server = new Server(serverSocket);
		server.startServer();
		
		
	}//end of main 
	
}//end of Server 
