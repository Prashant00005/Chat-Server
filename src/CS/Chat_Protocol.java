package CS;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map.Entry;
import java.util.Properties;


public class Chat_Protocol {

	
	public Protocol_Messages Parse_Client_Msg(String Msg1,String Msg2,String Msg3,String Msg4, PrintStream printStream) {
		Protocol_Messages Message = new Protocol_Messages();
		if(Msg1.startsWith("JOIN_CHATROOM") && Msg2.startsWith("CLIENT_IP") && Msg3.startsWith("PORT") && Msg4.startsWith("CLIENT_NAME")) 
		
		{

			String Split_Msg[] = Msg1.split(": ");
			String Msg1Val = Split_Msg[1];
			Split_Msg = Msg2.split(": ");
			String Msg2Val = Split_Msg[1];
			Split_Msg = Msg3.split(": ");
			String Msg3Val = Split_Msg[1];
			Split_Msg = Msg4.split(": ");
			String Msg4Val = Split_Msg[1];


			//To check if chat room is there or not
			
			if(!ChatRoom_Client_Info.Name_Chat_Room.containsKey(Msg1Val))
			{
				ChatRoom_Client_Info.Name_Chat_Room.put(Msg1Val, ChatRoom_Client_Info.Unique_ChatRoom_ID);
				ChatRoom_Client_Info.Unique_ChatRoom_ID++;
			}
			Message.setJOIN_CHATROOM(Msg1Val);
			Message.setCLIENT_IP(Msg2Val);
			Message.setPORT(Msg3Val);
			Message.setCLIENT_NAME(Msg4Val);

		
				ChatRoom_Client_Info.Message_Send_Client.put(Integer.parseInt(String.valueOf(Thread.currentThread().getId())),printStream);
			
				ChatRoom_Client_Info.CLient_ID_Chatroom.put(Integer.parseInt(String.valueOf(Thread.currentThread().getId())), ChatRoom_Client_Info.Name_Chat_Room.get(Msg1Val));

			return Message;
		}
		else 
		{
			
			Message.setErrorCode(1);
			Message.setErrorDescription("Not valid Message");
			return Message;
		}
	}
	public String Reply_Client_Msg(Protocol_Messages Put_In_Bundle) {



	Protocol_Messages Output_Message = new Protocol_Messages();
	Output_Message.setJoinedChatroom(Put_In_Bundle.JOIN_CHATROOM);
	Output_Message.setServerIp(Configuration_Server.IP_Of_Server);
	Output_Message.setPORT(Configuration_Server.PORT);
	Output_Message.setRoomRef(ChatRoom_Client_Info.Name_Chat_Room.get(Put_In_Bundle.JOIN_CHATROOM));
	
	Output_Message.setJoinId(Integer.parseInt(String.valueOf(Thread.currentThread().getId())));		//We are using Thread ID as JOIN_ID
	String Client_Reply = Output_Message.Join_ReplyTo_Client();

		return Client_Reply;

	}
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
}