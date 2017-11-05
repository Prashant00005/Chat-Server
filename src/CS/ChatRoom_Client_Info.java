package CS;

import java.io.PrintStream;
import java.util.HashMap;

public class ChatRoom_Client_Info 
{

	static  HashMap<String,Integer> Name_Chat_Room=new HashMap<String,Integer>();  
	
	static HashMap<Integer,PrintStream> Message_Send_Client = new HashMap<Integer,PrintStream>();	
	
	static HashMap<Integer,Integer> CLient_ID_Chatroom = new HashMap<Integer,Integer>();	
	static int Unique_ChatRoom_ID=0;	
}