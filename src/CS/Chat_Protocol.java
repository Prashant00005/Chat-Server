package CS;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Map.Entry;

import java.util.Set;


public class Chat_Protocol {

	
	public static void Load_Configurations() {
		
		Configuration_Server.IP_Of_Server = "134.226.50.50";
		Configuration_Server.PORT = "8099";
		
		/*Properties Attributes = new Properties();

		try 
		{
			
			Attributes.load(new FileInputStream("Config_Pkg/Configuration_Server.properties"));
		
		}
		catch (IOException e) 
			{	
				System.out.println("Inside Catch :: Load_Configurations ");
				e.printStackTrace();
			}
		
		Configuration_Server.IP_Of_Server = Attributes.getProperty("IP_Of_Server");
		
		Configuration_Server.PORT = Attributes.getProperty("PORT");*/
	}
	
	public Protocol_Messages Parse_Chat_Messages(String Msg1, String Msg2, String Msg3, String Msg4, PrintStream printStream) {
	
	Protocol_Messages Message = new Protocol_Messages();
	
			if(Msg1.startsWith("CHAT") && Msg2.startsWith("JOIN_ID") && Msg3.startsWith("CLIENT_NAME") && Msg4.startsWith("MESSAGE"))
			{
				
				String sub_Msg[] = Msg1.split(": "); String Msg1Val = sub_Msg[1];
				sub_Msg = Msg2.split(": "); 
				sub_Msg = Msg3.split(": "); String Msg3Val = sub_Msg[1];
				sub_Msg = Msg4.split(": "); String Msg4Val = sub_Msg[1];
		
				
				if(!ChatRoom_Client_Info.Name_Chat_Room.containsValue(Msg1Val)) 
				{
					Message.setErrorDescription("Message Not valid");
					Message.setErrorCode(1);
					
					return Message;
				}
				PrintStream PS;
				String str=null;
				str = "CHAT: "+Msg1Val +"\n" +"CLIENT_NAME: "+Msg3Val +"\n" +"MESSAGE: "+ Msg4Val +"\n\n"; 
				for (Entry<Integer, PrintStream> iterate : ChatRoom_Client_Info.Message_Send_Client.entrySet())
				{
			      
			        if(String.valueOf(ChatRoom_Client_Info.CLient_ID_Chatroom.get(iterate.getKey()))==Msg1Val) 
			        {	
				        	PS = iterate.getValue();
				        	if(PS!=printStream)	// check to make your the message is not sent to the same client
				        		PS.println(str);
			        }
			    }
		
				return Message;
			}
			else 
			{
				Message.setErrorDescription("Message not Valid");
				Message.setErrorCode(1);
				
				return Message;
			}
	}

	public void Func_HelloMsg(String str, PrintStream printStream) 
	{
			System.out.println("In Func_HelloMsg thread start ID"+Thread.currentThread().getId());
			String string=null;
		
			
			string = str + "\nIP: 134.226.50.55\nPort: 8099\nStudentID: 17317559";
			printStream.print(string);
			
			System.out.println("thread end ID "+Thread.currentThread().getId());
	}
	
	public Boolean Func_JoinMsg(String Msg1,String Msg2,String Msg3,String Msg4, PrintStream printStream) {
		System.out.println("Inside Chat_Protocol Join Msg");
		Protocol_Messages proto_msg = new Protocol_Messages();
		
		if(Msg1.startsWith("JOIN_CHATROOM") && Msg2.startsWith("CLIENT_IP") && Msg3.startsWith("PORT") && Msg4.startsWith("CLIENT_NAME")) 
		{

			String[] sub_Msg = Msg1.split(": "); String Msg1Val = sub_Msg[1]; 
			sub_Msg = Msg2.split(": ");		String Msg2Val = sub_Msg[1];   
			sub_Msg = Msg3.split(": ");		String Msg3Val = sub_Msg[1];   
			sub_Msg = Msg4.split(": "); 	String Msg4Val = sub_Msg[1];   

		
			if(!ChatRoom_Client_Info.Name_Chat_Room.containsKey(Msg1Val))
			{
				ChatRoom_Client_Info.Name_Chat_Room.put(Msg1Val, ChatRoom_Client_Info.Unique_ChatRoom_ID);
				ChatRoom_Client_Info.Inv_Name_Chat_Room.put(ChatRoom_Client_Info.Unique_ChatRoom_ID, Msg1Val);
				ChatRoom_Client_Info.Unique_ChatRoom_ID++;
			}
			
			//System.out.println("Values are::" +Msg1Val + "second value is " + Msg2Val + "Third value is" +  Msg3Val + " Fourth value is "+ Msg4Val);
			proto_msg.setJOIN_CHATROOM(Msg1Val);
			proto_msg.setPORT(Msg3Val);
			proto_msg.setCLIENT_IP(Msg2Val);
			
			proto_msg.setCLIENT_NAME(Msg4Val);

			
			Set<Integer> room_RefSet = new HashSet<Integer>();
			
			if(ChatRoom_Client_Info.CLient_ID_Chatroom.get(Integer.parseInt(String.valueOf(Thread.currentThread().getId()))) != null)
			{
				room_RefSet = ChatRoom_Client_Info.CLient_ID_Chatroom.get(Integer.parseInt(String.valueOf(Thread.currentThread().getId())));
			}
			
			
			room_RefSet.add(ChatRoom_Client_Info.Name_Chat_Room.get(Msg1Val));

			ChatRoom_Client_Info.Message_Send_Client.put(Integer.parseInt(String.valueOf(Thread.currentThread().getId())),printStream);
			ChatRoom_Client_Info.CLient_ID_Chatroom.put(Integer.parseInt(String.valueOf(Thread.currentThread().getId())), room_RefSet);
			
			Set<Integer> chat_IdList = new HashSet<Integer>();
			if(null!= ChatRoom_Client_Info.CLient_Names_Chatroom.get(Msg4Val)&& !ChatRoom_Client_Info.CLient_Names_Chatroom.get(Msg4Val).isEmpty())
				
				chat_IdList = ChatRoom_Client_Info.CLient_Names_Chatroom.get(Msg4Val);
			//End If
			
			chat_IdList.add(Integer.parseInt(String.valueOf(Thread.currentThread().getId())));
			
			ChatRoom_Client_Info.CLient_Names_Chatroom.put(Msg4Val,chat_IdList);
			
			String outMessage = Join_Reply_Msg(proto_msg);
			printStream.print(outMessage);
			
			System.out.println("Check ouput"+printStream+" join chatroom \n" +  outMessage);
			
			PrintStream obj2;
			
			String string = "CHAT: "+ ChatRoom_Client_Info.Name_Chat_Room.get(Msg1Val) + "\nCLIENT_NAME: "+Msg4Val+ "\nMESSAGE: "+ Msg4Val;
			
				for (Entry<Integer, PrintStream> iterate : ChatRoom_Client_Info.Message_Send_Client.entrySet())
				{
					
					if(ChatRoom_Client_Info.CLient_ID_Chatroom.get(iterate.getKey()).contains(ChatRoom_Client_Info.Name_Chat_Room.get(Msg1Val))) 
					
					{
						obj2 = iterate.getValue();
						if(printStream!=obj2)
						
						{
							obj2.println(string);
							System.out.println("JOINED_CHATROOMS\n" +  string);
						}
					}
				}
			printStream.println(string);
			
			return true;
		}
		
		else 
		
		{
				System.out.println("Join Message");
				proto_msg.setErrorDescription("Input Message not valid");
				proto_msg.setErrorCode(1);
				
				return false;
		}

	}
	
	public String Join_Reply_Msg(Protocol_Messages input) {
		System.out.println("In Join_Reply_Msg ChatProtocol Thread Start ID "+Thread.currentThread().getId());


		Protocol_Messages proto_msg = new Protocol_Messages();
		proto_msg.setJoinedChatroom(input.JOIN_CHATROOM);
		
		proto_msg.setIP_Of_Server(Configuration_Server.IP_Of_Server);
		
		proto_msg.setPORT(Configuration_Server.PORT);
		
		proto_msg.setRoomRef(Integer.parseInt(ChatRoom_Client_Info.Name_Chat_Room.get(input.JOIN_CHATROOM).toString()));
		
		proto_msg.setJoinId(Integer.parseInt(String.valueOf(Thread.currentThread().getId())));		
		
		String join_reply = proto_msg.Join_ReplyTo_Client();
		System.out.println("In Join_Reply_Msg ChatProtocol Thread end ID"+Thread.currentThread().getId());
		return join_reply;

	}
	
	public Boolean Func_LeaveMsg(String Msg1, String Msg2, String Msg3, PrintStream printStream) 
	
	{
		System.out.println("Inside Chat Protocol :: ");
		
		if(Msg1.startsWith("LEAVE_CHATROOM") && Msg2.startsWith("JOIN_ID") && Msg3.startsWith("CLIENT_NAME")) 
		{		
			String[] sub_Msg = Msg1.split(": "); 	String Msg1Val = sub_Msg[1];
			sub_Msg = Msg2.split(": "); 			String Msg2Val = sub_Msg[1];
			sub_Msg = Msg3.split(": ");				String Msg3Val = sub_Msg[1];


			
			if(!ChatRoom_Client_Info.Inv_Name_Chat_Room.containsKey(Integer.parseInt(Msg1Val))) 
			
			{ 
				
				System.out.println("Error in leave message");
				return false;
			}
			
			PrintStream obj2;
			String str1=null;
			
			str1 = "LEFT_CHATROOM: "+Msg1Val +"\n" 	+"JOIN_ID: "+Msg2Val+"\n"; 

			
			String str2 = "CHAT: "+Msg1Val +"\n" +"CLIENT_NAME: "+ Msg3Val +"\n" +"MESSAGE: "+ Msg3Val;
			
			str1 = str1 + str2;
			
			System.out.println("checking output"+printStream+" leave chatroom : \n" +  str1);
			
			printStream.println(str1);

			
			Set<Integer> temp_Set = ChatRoom_Client_Info.CLient_ID_Chatroom.get(Integer.parseInt(Msg2Val));
			
			temp_Set.remove(Msg1Val);
			
			ChatRoom_Client_Info.CLient_ID_Chatroom.remove(Integer.parseInt(Msg2Val));
			
			ChatRoom_Client_Info.CLient_ID_Chatroom.put(Integer.parseInt(Msg2Val),temp_Set);
			ChatRoom_Client_Info.Message_Send_Client.remove(Integer.parseInt(Msg2Val));


			//Scan all the output messages and see if they are from this specific chat group*/
			
			
			int key=-1;
			
			
			try{

				for (Entry<Integer, PrintStream> iterate : ChatRoom_Client_Info.Message_Send_Client.entrySet()) 
				
				{
					key=-1;

					
					if(ChatRoom_Client_Info.CLient_ID_Chatroom.get(iterate.getKey()).contains((Integer.parseInt(Msg1Val)))) 
					{	
						obj2 = iterate.getValue();
						key = iterate.getKey();
						if(obj2!=printStream) 
						{
							obj2.println(str2);
						}
					}
				}
			}
			catch(Exception exc)
			{
				System.out.println("Exception in processing leave message" + exc);
				exc.printStackTrace();
			}
			
			finally
			{
				if(key > -1)
				
				{
					Set<Integer> temp_Set1 = ChatRoom_Client_Info.CLient_ID_Chatroom.get(key);
					ChatRoom_Client_Info.CLient_ID_Chatroom.remove(key);
					if(temp_Set1!=null)
					
					{
						temp_Set1.remove(Msg1Val);
						ChatRoom_Client_Info.CLient_ID_Chatroom.put(key,temp_Set1);
					}
					
					ChatRoom_Client_Info.Message_Send_Client.remove(key);


				}
			}
			
			return true;
		}
		
		else 
		{
			System.out.println("Error in Func_LeaveMsg");
		
			return false;
		}

	}
	
	
	public boolean Func_ChatMsg(String Msg1, String Msg2, String Msg3, String Msg4, PrintStream printStream) {
		
		System.out.println("In Func_ChatMsg :: ");
		
		Protocol_Messages proto_msg = new Protocol_Messages();
		if(Msg1.startsWith("CHAT") && Msg2.startsWith("JOIN_ID") && Msg3.startsWith("CLIENT_NAME") && Msg4.startsWith("MESSAGE")) 
		
		{
				
				String[] sub_Msg = Msg1.split(": ");      String Msg1Val = sub_Msg[1];
				sub_Msg = Msg2.split(": ");				  sub_Msg = Msg3.split(": ");
				String Msg3Val = sub_Msg[1];				  sub_Msg = Msg4.split(": ");
				String Msg4Val = sub_Msg[1];				  
		
		
				if(!ChatRoom_Client_Info.Inv_Name_Chat_Room.containsKey(Integer.parseInt(Msg1Val))) { 
					proto_msg.setErrorCode(1);
					proto_msg.setErrorDescription("Input Message not valid");
					System.out.println("****ERROR 1:  Processing Chat Message*****");
					return false;
				}
				PrintStream obj2;
				String str1=null;
		
				str1 = "CHAT: "+Msg1Val +"\n"  +"CLIENT_NAME: "+Msg3Val +"\n" +"MESSAGE: "+ Msg4Val +"\n\n"; printStream.print(str1);
				
				
				for (Entry<Integer, PrintStream> iterate : ChatRoom_Client_Info.Message_Send_Client.entrySet()) {
					
					
					if(ChatRoom_Client_Info.CLient_ID_Chatroom.get(iterate.getKey()).contains(Integer.parseInt(Msg1Val))) 
					
					{
						obj2 = iterate.getValue();
						if(obj2 != printStream)	
							
							obj2.print(str1);
		
					}
				}
		
				return true;
		}
		
		else 
		{
				System.out.println("Error in Func_ChatMsg");
				
				proto_msg.setErrorDescription("Message not Valid");
				proto_msg.setErrorCode(1);
				
				return false;
		}
	}



}