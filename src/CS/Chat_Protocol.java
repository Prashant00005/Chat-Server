package CS;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map.Entry;


import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


public class Chat_Protocol {

	
	public static void Load_Configurations() {
		Properties Attributes = new Properties();

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
		
		Configuration_Server.PORT = Attributes.getProperty("PORT");
	}
	
	public Protocol_Messages Parse_Chat_Messages(String Msg1, String Msg2, String Msg3, String Msg4, PrintStream printStream) {
	
	Protocol_Messages Message = new Protocol_Messages();
	
		if(Msg1.startsWith("CHAT") && Msg2.startsWith("JOIN_ID") && Msg3.startsWith("CLIENT_NAME") && Msg4.startsWith("MESSAGE"))
		{
			
			String Split_Msg[] = Msg1.split(": ");
			String Msg1Val = Split_Msg[1];
			Split_Msg = Msg2.split(": ");
			String Msg2Val = Split_Msg[1];
			Split_Msg = Msg3.split(": ");
			String Msg3Val = Split_Msg[1];
			Split_Msg = Msg4.split(": ");
			String Msg4Val = Split_Msg[1];
	
			
			if(!ChatRoom_Client_Info.Name_Chat_Room.containsValue(Msg1Val)) 
			{
				Message.setErrorCode(1);
				Message.setErrorDescription("Not valid Message");
				return Message;
			}
			PrintStream PS;
			String str=null;
			str = "CHAT: "+Msg1Val +"\n"
					+"CLIENT_NAME: "+Msg3Val +"\n"
					+"MESSAGE: "+ Msg4Val +"\n\n";
			
			for (Entry<Integer, PrintStream> entry : ChatRoom_Client_Info.Message_Send_Client.entrySet()) {
		      
		        if(String.valueOf(ChatRoom_Client_Info.CLient_ID_Chatroom.get(entry.getKey()))==Msg1Val) 
		        {	
			        	PS = entry.getValue();
			        	if(PS!=printStream)	// check to make your the message is not sent to the same client
			        		PS.println(str);
		        }
		    }
	
			return Message;
		}
		else 
		{
			Message.setErrorCode(1);
			Message.setErrorDescription("Not valid Message");
			return Message;
		}
	}

	public void Func_HelloMsg(String str, PrintStream printStream) 
	{
		String string=null;
		System.out.println("In Func_HelloMsg thread start ID"+Thread.currentThread().getId());
		
		string = str + "\nIP: 134.226.50.55\nPort: 8099\nStudentID: 17317559";
		printStream.print(string);
		
		System.out.println("thread end ID "+Thread.currentThread().getId());
	}
	
	public Boolean Func_JoinMsg(String Msg1,String Msg2,String Msg3,String Msg4, PrintStream printStream) {
		System.out.println("Inside Chat_Protocol Join Msg");
		Protocol_Messages proto_msg = new Protocol_Messages();
		
		if(Msg1.startsWith("JOIN_CHATROOM") &&
				Msg2.startsWith("CLIENT_IP") &&
				Msg3.startsWith("PORT") &&
				Msg4.startsWith("CLIENT_NAME")) {

			String[] parts = Msg1.split(": ");
			String Msg1Val = parts[1];
			parts = Msg2.split(": ");
			String Msg2Val = parts[1];
			parts = Msg3.split(": ");
			String Msg3Val = parts[1];
			parts = Msg4.split(": ");
			String Msg4Val = parts[1];


		
			if(!ChatRoom_Client_Info.Name_Chat_Room.containsKey(Msg1Val))
			{
				ChatRoom_Client_Info.Name_Chat_Room.put(Msg1Val, ChatRoom_Client_Info.Unique_ChatRoom_ID);
				ChatRoom_Client_Info.Inv_Name_Chat_Room.put(ChatRoom_Client_Info.Unique_ChatRoom_ID, Msg1Val);
				ChatRoom_Client_Info.Unique_ChatRoom_ID++;
			}
			proto_msg.setJOIN_CHATROOM(Msg1Val);
			proto_msg.setCLIENT_IP(Msg2Val);
			proto_msg.setPORT(Msg3Val);
			proto_msg.setCLIENT_NAME(Msg4Val);

			
			Set<Integer> roomRefSet = new HashSet<Integer>();
			if(ChatRoom_Client_Info.CLient_ID_Chatroom.get(Integer.parseInt(String.valueOf(Thread.currentThread().getId()))) != null){
				roomRefSet = ChatRoom_Client_Info.CLient_ID_Chatroom.get(Integer.parseInt(String.valueOf(Thread.currentThread().getId())));
			}
			roomRefSet.add(ChatRoom_Client_Info.Name_Chat_Room.get(Msg1Val));

			ChatRoom_Client_Info.Message_Send_Client.put(Integer.parseInt(String.valueOf(Thread.currentThread().getId())),printStream);
			ChatRoom_Client_Info.CLient_ID_Chatroom.put(Integer.parseInt(String.valueOf(Thread.currentThread().getId())), roomRefSet);
			
			Set<Integer> cidList = new HashSet<Integer>();
			if(null!= ChatRoom_Client_Info.CLient_Names_Chatroom.get(Msg4Val)&& !ChatRoom_Client_Info.CLient_Names_Chatroom.get(Msg4Val).isEmpty())
				cidList = ChatRoom_Client_Info.CLient_Names_Chatroom.get(Msg4Val);
			cidList.add(Integer.parseInt(String.valueOf(Thread.currentThread().getId())));
			ChatRoom_Client_Info.CLient_Names_Chatroom.put(Msg4Val,cidList);
			
			String outMessage = Join_Reply_Msg(proto_msg);
			printStream.print(outMessage);
			
			//System.out.println("Output  "+printStream+" JOIN_CHATROOM\n" +  outMessage);
			
			PrintStream obj2;
			String string = "CHAT: "+ ChatRoom_Client_Info.Name_Chat_Room.get(Msg1Val) +
					"\nCLIENT_NAME: "+Msg4Val+
					"\nMESSAGE: "+ Msg4Val;
			
			for (Entry<Integer, PrintStream> entry : ChatRoom_Client_Info.Message_Send_Client.entrySet()) {
				System.out.println(entry.getKey().toString());
				if(ChatRoom_Client_Info.CLient_ID_Chatroom.get(entry.getKey()).contains(ChatRoom_Client_Info.Name_Chat_Room.get(Msg1Val))) {
					obj2 = entry.getValue();
					if(printStream!=obj2){
						obj2.println(string);
						//System.out.println("JOINED_CHATROOMS\n" +  string);
					}
				}
			}
			printStream.println(string);
			System.out.println("Output  "+printStream+"  JOIN_CHATROOM\n" +  string);
			System.out.println("******End  "+Thread.currentThread().getId()+" : Processing Join Message******");
			return true;
		}
		else {
			System.out.println("****ERROR "+Thread.currentThread().getId()+" :  Processing Join Message*****");
			proto_msg.setErrorCode(1);
			proto_msg.setErrorDescription("Input Message not valid");
			return false;
		}

	}
	
	public String Join_Reply_Msg(Protocol_Messages input) {
		System.out.println("In Join_Reply_Msg ChatProtocol Thread Start ID "+Thread.currentThread().getId());


		Protocol_Messages proto_msg = new Protocol_Messages();
		proto_msg.setJoinedChatroom(input.JOIN_CHATROOM);
		proto_msg.setServerIp(Configuration_Server.IP_Of_Server);
		proto_msg.setPORT(Configuration_Server.PORT);
		proto_msg.setRoomRef(Integer.parseInt(ChatRoom_Client_Info.Name_Chat_Room.get(input.JOIN_CHATROOM).toString()));
		proto_msg.setJoinId(Integer.parseInt(String.valueOf(Thread.currentThread().getId())));		
		String join_reply = proto_msg.Join_ReplyTo_Client();
		System.out.println("In Join_Reply_Msg ChatProtocol Thread end ID"+Thread.currentThread().getId());
		return join_reply;

	}



}