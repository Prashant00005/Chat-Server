package CS;
 import java.io.BufferedReader;
import java.net.Socket;


import java.io.PrintStream;

import java.net.ServerSocket;
import java.io.IOException;
import java.io.InputStreamReader;


public class ChatServer {

	// Socket connection of Server
		private static ServerSocket server_socket = null;
// Socket connection of Client
		private static Socket client_socket = null;

	
//Default value of the number of clients that can connect to our server is 15
	private static final int Max_Client = 15;
	
private static final Thread_Client[] threads = new Thread_Client[Max_Client];

	public static void main(String args[]) {

		Chat_Protocol.Load_Configurations(); //To set our Server IP and Port Number
		ChatRoom_Client_Info.Unique_ChatRoom_ID = 0;
		String Arg_IP = "127.0.0.1";
		//Default PPort Number
		int Arg_Port = 8099;
		
		if (args.length < 1)  //Checks whether command line argument has been passed or not
		{  //
			System.out.println("Inside Main :: Port Number=" + Arg_Port +"\n And IP = "+ Arg_IP);
		} 
		
		else 
		
		{
			Arg_Port = Integer.valueOf ( args[0] ).intValue();
		}

		
		try 
		{
			server_socket = new ServerSocket(Arg_Port);
			
		} 
		catch (IOException e) 
		{
			System.out.println("Inside Catch :: Main");
			System.out.println(e);
		}

		//Creating a client socket for each connection
		
		while (true) 
		{
			try {
					client_socket = server_socket.accept();
					int i = 0;
					for (i = 0; i < Max_Client; i++) 
					{
						if (threads[i] == null) 
						{
							(threads[i] = new Thread_Client(client_socket, threads)).start();
							break;
						}
					}
					
					if (i == Max_Client) 
					{
						PrintStream printStream = new PrintStream(client_socket.getOutputStream());
						printStream.println("The Server has reached its limit. You can try again later.");
						printStream.close();
						client_socket.close();
					}
				}
				
				catch (IOException e) 
				{
					System.out.println("Inside Catch :: Main Client Connection");
					System.out.println(e);
				}
		}
	}
}


class Thread_Client extends Thread {

//	private String Name_Of_Client = null;
	private BufferedReader inputStream = null;
	private PrintStream printStream = null;
	private Socket client_socket = null;
//	private final clientThread[] Client_Thread;
//	private int Max_Client;


	public Thread_Client(Socket client_socket, Thread_Client[] threads) {
		this.client_socket = client_socket;

	}

	public void run() 
	{
			String Accept_String[] = new String[100] ;
			
		try 
			{
			
			inputStream = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
			printStream = new PrintStream(client_socket.getOutputStream());
			while (true)
			{
				Accept_String[0]=inputStream.readLine();
				  if(Accept_String[0].startsWith("HELO ")) 
				  {
					System.out.println("Client first inputs hello msg with wrong spelling :)" + Accept_String[0]);
				  }
				  
				  else if(Accept_String[0].startsWith("JOIN_CHATROOM: ")) 
				  
				  {
					  Accept_String[1] = inputStream.readLine();
					  Accept_String[2] = inputStream.readLine();
					  Accept_String[3] = inputStream.readLine();
					  System.out.println("Join message from client is \n"+Accept_String[0]+Accept_String[1]+Accept_String[2]+Accept_String[3]);
				  }
				  
				  else if(Accept_String[0].startsWith("LEAVE_CHATROOM: ")) 
				  
				  {
					  Accept_String[1] = inputStream.readLine();
					  Accept_String[2] = inputStream.readLine();
						System.out.println("Inside MAin :: Leave Chatroom");
				  }
				  else if(Accept_String[0].startsWith("CHAT: ")) 
				  
				  {
					  Accept_String[1] = inputStream.readLine();
					  Accept_String[2] = inputStream.readLine();

						int i = 3;
						while(true){
							Accept_String[i] = inputStream.readLine();
							if(Accept_String[i].isEmpty()){
								break;
							}
							i++;
						}
						System.out.println("Inside Main CHAT and msg received is :\n"+Accept_String[0]+Accept_String[1]+Accept_String[2]+Accept_String[3]+Accept_String[4]);
					}
				  else if(Accept_String[0].startsWith("DISCONNECT: ")){
					  Accept_String[1] = inputStream.readLine();
					  Accept_String[2] = inputStream.readLine();
					  Chat_Protocol cp = new Chat_Protocol();
						cp.Func_DisconnectMsg(Accept_String[0],Accept_String[1],Accept_String[2],printStream);
						printStream.close();
						inputStream.close();
						client_socket.close();
						return;
					}
				  else if(Accept_String[0].startsWith("KILL_SERVICE")) {
						
					  System.out.println("Inside Kill service  MAIN ::"+Accept_String[0]);
							inputStream.close();
							printStream.close();
							client_socket.close();
							System.exit(0);
					}
				  
				  else
				  
				  {
					  System.out.println("ERRRORRRR :::"+Accept_String[0]);
				  }
				  
				  new SameClient_WriteThread(printStream,Accept_String).start();
				
				
			}	
				//We need to make the current thread to zero / null, and now our server can accept new clients
			/*synchronized (this) 
			{
				for (int i = 0; i < Max_Client; i++) 
				{
					if (Client_Thread[i] == this) 
					{
						Client_Thread[i] = null;
					}
				}
			}*/
			
			
			
		} 
		catch (IOException MTE) 
			{
				System.out.println("Inside Catch ::Main Thread Run " + MTE);
				MTE.printStackTrace();
			}
		
		finally{
			try {
				//Closing all the open connections
				System.out.println("In finally :: main class ");
				inputStream.close();
				printStream.close();
				client_socket.close();
			} 
			
			catch (Exception IOE)
			{
					System.out.println("Main thread catching IO exception" + IOE);
					IOE.printStackTrace();
			}
			
			

		}
	}


}