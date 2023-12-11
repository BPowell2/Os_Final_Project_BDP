import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;


public class ClientHandler implements Runnable {
	

	
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private PrintWriter printWriter;
	public int player1Hp;
	public int player2Hp;
	public int whosTurn;
	public int player1Wins;
	public int player2Wins;
	
	public  ClientHandler(Socket socket) {
		try {
		this.socket = socket;
		this.bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(socket.getInputStream())));
		this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.printWriter = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()),true);
		
		
		}//end of try
		catch(IOException e) {
			e.printStackTrace();
		}//end of catch
		
	}//end of clientHandler constructor

	
	public void run() {
		printWriter.println("would u like to join the game type join");
		
		
		while(socket.isConnected()) {
			
			try {
				
				
				String messageFromClient = bufferedReader.readLine();
				if(messageFromClient.contains("join")) {
					startGame();
					printGame();
				}//end of if 
				if(whosTurn == 1) {
					if(messageFromClient.contains("1")) {
					player1Attack();
					printGame();
				}//end of player1Attack if
				
				if(messageFromClient.contains("2")){
					player1Heal();
					printGame();
				}//end of player1Heal if
				
				if(messageFromClient.contains("3")) {
					player1Magic();
					printGame();
				}//end of player1Magic if
				}//end of player 1 turns
				
				if(whosTurn == 2) {
					if(messageFromClient.contains("1")) {
					player2Attack();
					printGame();
				}//end of player2Attack if
				
				if(messageFromClient.contains("2")) {
					player2Heal();
					printGame();
				}//end of player2heal if 
				
				if(messageFromClient.contains("3")) {
					player2Magic();
					printGame();
				}//end of player2Magic if
				
				}//end of player 2 turns
				
				if(player2Hp <= 0) {
					player1Wins++;
					endGame();
				}//end of endgame if player 1 victory
				
				if(player1Hp <= 0 ) {
					player2Wins++;
					endGame();
				}//end of endgame if for player 2 victory
			}//end of try
			catch(IOException e) {
				e.printStackTrace();
				break;
			
			}//end of catch
			
		}//end of while
		
	}//end of run
	
	public void startGame() {
		
		player1Hp = 100;
		player2Hp = 100;
		whosTurn = 1;
		printWriter.println("the startgame method has run thank god");
	}//end of startGame
	
	public void testMethod() {
		printWriter.println("the tester method has worked thank god");
	}//end of testMethod
	
	public void printGame(){
		
		printWriter.println("the current Hp for player 1 is : "+player1Hp);
		printWriter.println("-----------------------------------------");
		printWriter.println("the current Hp for player 2 is : "+player2Hp);
		printWriter.println("-----------------------------------------");
		printWriter.println("player"+whosTurn+" pick your move");
		printWriter.println("(1) Attack ");
		printWriter.println();
		printWriter.println("(2) Heal ");
		printWriter.println();
		printWriter.println("(3) Use Magic ");
		printWriter.println();
		
		
		
		
		
	}//end of printGame
	
	public void player1Attack() {
		Random rand = new Random();
		int hpRemoval = rand.nextInt(21);
		
		
			player2Hp = player2Hp - hpRemoval;
			printWriter.println("player 1 attacked player 2 for "+hpRemoval+" damage");
			changeTurn(whosTurn);
	
		
	
		
	}//end of player1Attack
	
	public void player1Heal() {
		int hpRecover = 20;
			player1Hp = player1Hp + hpRecover;
			printWriter.println("player 1 has healed themselves for "+hpRecover);
			changeTurn(whosTurn);
		
		
	}//end of player1Heal
	
	public void player1Magic() {
		Random rand = new Random();
		int hit = rand.nextInt(1);
		int hpRemoval = rand.nextInt(40);
		if(hit==0) {
			
		
			player2Hp = player2Hp - hpRemoval;
			printWriter.println("player 1 has hit a magic attack for "+hpRemoval+" damage");
			changeTurn(whosTurn);
	
		}//end of if
		
		
	}//end of player1 Magic
	
	public void player2Attack(){
		Random rand = new Random();
		int hpRemoval = rand.nextInt(21);

			player1Hp = player1Hp - hpRemoval;
			printWriter.println("player 2 has attacked player 1 for "+hpRemoval+" damage");
			changeTurn(whosTurn);
	
	}//end of player2Attack
	
	public void player2Heal() {
		int hpRecover = 20;
	
			player2Hp = player2Hp + hpRecover;
			printWriter.println("player 2 has healed themselves for 20 hp");
			changeTurn(whosTurn);
	
	}//end of player2Heal
	
	public void player2Magic() {
		Random rand = new Random();
		int hit = rand.nextInt(1);
		int hpRemoval = rand.nextInt(40);
		if(hit==0) {
			player1Hp = player1Hp - hpRemoval;
			printWriter.println("player 2 has hit player 1 for "+hpRemoval+" magic damaga");
			changeTurn(whosTurn);
		}//end of if
	}//end of player2Magic
	
	public int changeTurn(int whosTurn) {
	
		if(whosTurn==1) {
			whosTurn = 2;
			printWriter.println(" it is now player 2's turn");
		}//end of if
		if(whosTurn==2) {
			whosTurn = 1;
			printWriter.println(" it is now player 1's turn");
		}//end of if
		return whosTurn;
	}//end of changeTurn
	
	public void endGame() {
		printWriter.println("the game has ended and will now be reset");
		startGame();
		totalWins();
	}//end of endGame
	
	public synchronized void totalWins() {
		printWriter.println("player 1's currently have "+player1Wins);
		printWriter.println("--------------------------------------");
		printWriter.println("player 2's currently have "+player2Wins);
		
	}//end of totalWins
	
	
}//end of ClientHandler
