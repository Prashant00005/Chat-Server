package CS;
import java.io.DataInputStream;
import java.net.Socket;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.io.IOException;


public class ChatServer {

	// Socket connection of Server
		private static ServerSocket server_socket = null;
// Socket connection of Client
		private static Socket client_socket = null;

	
//Default value of the number of clients that can connect to our server is 15
	private static final int Max_Client = 15;
	
private static final clientThread[] threads = new clientThread[Max_Client];

	public static void main(String args[]) {

		Chat_Protocol.Load_Configurations(); //To set our Server IP and Port Number
		ChatRoom_Client_Info.Unique_ChatRoom_ID = 0;
		String Arg_IP = "localhost";
		//Default PPort Number
		int Arg_Port = 2235;
		
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
			InetAddress Inet_Address = InetAddress.getByName(Arg_IP);
			server_socket = new ServerSocket(Arg_Port,50,Inet_Address);
			
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
							(threads[i] = new clientThread(client_socket, threads)).start();
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


class clientThread extends Thread {

	private String Name_Of_Client = null;
	private DataInputStream inputStream = null;
	private PrintStream printStream = null;
	private Socket client_socket = null;
	private final clientThread[] Client_Thread;
	private int Max_Client;


	public clientThread(Socket client_socket, clientThread[] threads) {
		this.client_socket = client_socket;
		this.Client_Thread = threads;
		Max_Client = threads.length;

	}

	public void run() 
	{
			int Max_Client = this.Max_Client;
			clientThread[] Client_Thread = this.Client_Thread;
			Chat_Protocol CP_Obj = new Chat_Protocol();
			Protocol_Messages Put_In_Bundle = new Protocol_Messages();
			String Output_Message =null;
			
		try 
			{
				
					//Creating Data Input Stream and Output Stream for client
					
					inputStream = new DataInputStream(client_socket.getInputStream());
					
					printStream = new PrintStream(client_socket.getOutputStream());
					
		
					String Msg1= inputStream.readLine();
					String Msg2= inputStream.readLine();
					String Msg3= inputStream.readLine();
					String Msg4= inputStream.readLine();
					
				if(Msg1.startsWith("JOIN_CHATROOM: ")) 
				{
					Put_In_Bundle = CP_Obj.Parse_Client_Msg(Msg1,Msg2,Msg3,Msg4,printStream);
					Output_Message = CP_Obj.Reply_Client_Msg(Put_In_Bundle);
					printStream.print(Output_Message);
					System.out.println("\nAfter decoding: "+Output_Message);
				}
				
				else if(Msg1.startsWith("LEFT_CHATROOM: ")) 
				{
		
				}
				else if(Msg1.startsWith("CHAT: ")) 
				
				{
					
					Put_In_Bundle = CP_Obj.Parse_Chat_Messages(Msg1,Msg2,Msg3,Msg4,printStream);
					printStream.print(Output_Message);
					System.out.println("\nAfter decoding: "+Output_Message);
				}
		
		
				//We need to make the current thread to zero / null, and now our server can accept new clients
			synchronized (this) 
			{
				for (int i = 0; i < Max_Client; i++) 
				{
					if (Client_Thread[i] == this) 
					{
						Client_Thread[i] = null;
					}
				}
			}
			
			//Closing all the open connections
			inputStream.close();
			printStream.close();
			client_socket.close();
		} 
		catch (IOException e) 
			{
				System.out.println("Inside Catch ::Main Thread Run ");
			}
	}


}