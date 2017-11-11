package CS;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoom_Client_Info 
{

	static  HashMap<String,Integer> Name_Chat_Room=new HashMap<String,Integer>();  
	static  HashMap<Integer,String> Inv_Name_Chat_Room=new HashMap<Integer,String>();
	
	static HashMap<Integer,PrintStream> Message_Send_Client = new HashMap<Integer,PrintStream>();	
	
	static ConcurrentHashMap<Integer,Set<Integer>> CLient_ID_Chatroom = new ConcurrentHashMap<Integer,Set<Integer>>();	
	static ConcurrentHashMap<String,Set<Integer>> CLient_Names_Chatroom = new ConcurrentHashMap<String,Set<Integer>>();
	
	static int Unique_ChatRoom_ID=0;
	static int number=0;
}